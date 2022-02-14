package com.example.bssapp.ui.professors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bssapp.DaoSession;
import com.example.bssapp.MainApplication;
import com.example.bssapp.MenuActivity;
import com.example.bssapp.ProfessorItemDao;
import com.example.bssapp.R;
import com.example.bssapp.StudentItemDao;
import com.example.bssapp.databinding.FragmentProfessorsBinding;
import com.example.bssapp.db.models.ProfessorItem;
import com.example.bssapp.db.models.StudentItem;
import com.example.bssapp.ui.students.StudentAdapter;
import com.example.bssapp.ui.students.StudentListItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProfessorsFragment extends Fragment {

    private FragmentProfessorsBinding binding;

    private ListView listViewProfessors;
    private SearchView searchViewProfessors;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfessorsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LoadControllers(root);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void LoadControllers(View root){
        FloatingActionButton fabAddProfessor = root.findViewById(R.id.fabAddProfessor);
        fabAddProfessor.setOnClickListener(v -> ((MenuActivity) requireActivity()).changeToNewProfessor());

        //Db
        DaoSession daoSession = ((MainApplication) requireActivity().getApplication()).getDaoSession();
        ProfessorItemDao professorItemDao = daoSession.getProfessorItemDao();
        List<ProfessorItem> professorsData = professorItemDao.queryBuilder()
                .where(ProfessorItemDao.Properties.Deleted.eq(false))
                .orderAsc(ProfessorItemDao.Properties.FirstName, ProfessorItemDao.Properties.LastName)
                .list();

        listViewProfessors = root.findViewById(R.id.listViewProfessors);

        ArrayList<ProfessorListItem> professorsList = new ArrayList<>();

        for (ProfessorItem object : professorsData) {
            professorsList.add(new ProfessorListItem(object.getProfessorId(), object.getFirstName() + " " + object.getLastName(),
                    R.drawable.professor_icon));
        }

        //Costume adapter
        ProfessorAdapter adapter = new ProfessorAdapter(this.requireActivity(), R.layout.list_student_row, professorsList);
        listViewProfessors.setAdapter(adapter);

        searchViewProfessors = root.findViewById(R.id.searchViewProfessors);
        searchViewProfessors.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String text) {

                ((ProfessorAdapter) listViewProfessors.getAdapter()).getFilter().filter(text);
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String text) {

                return false;
            }

        });

        searchViewProfessors.setOnCloseListener(() -> {
            ((StudentAdapter) listViewProfessors.getAdapter()).getFilter().filter("");
            searchViewProfessors.onActionViewCollapsed();
            return false;
        });

        searchViewProfessors.setOnClickListener(v -> searchViewProfessors.onActionViewExpanded());

        searchViewProfessors.setOnQueryTextFocusChangeListener((view, b) -> {
            if(!b)
            {
                if(searchViewProfessors.getQuery().toString().length() < 1)
                {
                    searchViewProfessors.onActionViewCollapsed();
                }

                searchViewProfessors.clearFocus();

            }
        });

        listViewProfessors.setOnItemClickListener((parent, view, position, id) -> {
            ProfessorListItem selectedStudent = (ProfessorListItem) parent.getItemAtPosition(position);

            if(selectedStudent != null){
                //((MenuActivity) requireActivity()).changeToEditStudentFragment(selectedStudent);
            }
        });
    }
}