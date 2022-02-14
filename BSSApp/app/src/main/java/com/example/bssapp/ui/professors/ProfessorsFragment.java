package com.example.bssapp.ui.professors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bssapp.DaoSession;
import com.example.bssapp.MainApplication;
import com.example.bssapp.MenuActivity;
import com.example.bssapp.R;
import com.example.bssapp.StudentItemDao;
import com.example.bssapp.databinding.FragmentProfessorsBinding;
import com.example.bssapp.db.models.StudentItem;
import com.example.bssapp.ui.students.StudentAdapter;
import com.example.bssapp.ui.students.StudentListItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProfessorsFragment extends Fragment {

    private FragmentProfessorsBinding binding;

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
        StudentItemDao studentItemDao = daoSession.getStudentItemDao();
        List<StudentItem> studentsData = studentItemDao.queryBuilder()
                .where(StudentItemDao.Properties.Deleted.eq(false))
                .orderAsc(StudentItemDao.Properties.FirstName, StudentItemDao.Properties.LastName)
                .list();

        /*listViewStudents = root.findViewById(R.id.listViewStudents);

        ArrayList<StudentListItem> studentsList = new ArrayList<>();

        for (StudentItem object : studentsData) {
            studentsList.add(new StudentListItem(object.getStudentId(), object.getFirstName() + " " + object.getLastName(),
                    (object.getIsAdult() ?  R.drawable.user : R.drawable.cute_baby)));
        }

        //Costume adapter
        StudentAdapter adapter = new StudentAdapter(this.requireActivity(), R.layout.list_student_row, studentsList);
        listViewStudents.setAdapter(adapter);

        searchViewStudents = root.findViewById(R.id.searchViewStudents);
        searchViewStudents.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String text) {

                ((StudentAdapter) listViewStudents.getAdapter()).getFilter().filter(text);
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String text) {

                return false;
            }

        });

        searchViewStudents.setOnCloseListener(() -> {
            ((StudentAdapter) listViewStudents.getAdapter()).getFilter().filter("");
            searchViewStudents.onActionViewCollapsed();
            return false;
        });

        searchViewStudents.setOnClickListener(v -> searchViewStudents.onActionViewExpanded());

        searchViewStudents.setOnQueryTextFocusChangeListener((view, b) -> {
            if(!b)
            {
                if(searchViewStudents.getQuery().toString().length() < 1)
                {
                    searchViewStudents.onActionViewCollapsed();
                }

                searchViewStudents.clearFocus();

            }
        });

        listViewStudents.setOnItemClickListener((parent, view, position, id) -> {
            StudentListItem selectedStudent = (StudentListItem) parent.getItemAtPosition(position);

            if(selectedStudent != null){
                ((MenuActivity) requireActivity()).changeToEditStudentFragment(selectedStudent);
            }
        });*/
    }
}