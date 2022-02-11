package com.example.bssapp.ui.students;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.bssapp.DaoSession;
import com.example.bssapp.MainApplication;
import com.example.bssapp.MenuActivity;
import com.example.bssapp.R;
import com.example.bssapp.StudentItemDao;
import com.example.bssapp.databinding.FragmentStudentsBinding;
import com.example.bssapp.db.models.StudentItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class StudentsFragment extends Fragment {

    private FragmentStudentsBinding binding;

    private ListView listViewStudents;
    private SearchView searchViewStudents;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentStudentsBinding.inflate(inflater, container, false);
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
        FloatingActionButton fabAddStudent = root.findViewById(R.id.fabAddStudent);
        fabAddStudent.setOnClickListener(v -> ((MenuActivity) requireActivity()).changeFragment());

        //Db
        DaoSession daoSession = ((MainApplication) requireActivity().getApplication()).getDaoSession();
        StudentItemDao studentItemDao = daoSession.getStudentItemDao();
        List<StudentItem> studentsData = studentItemDao.queryBuilder()
                .where(StudentItemDao.Properties.Deleted.eq(false))
                .orderAsc(StudentItemDao.Properties.FirstName, StudentItemDao.Properties.LastName)
                .list();

        listViewStudents = root.findViewById(R.id.listViewStudents);

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
        });
    }
}