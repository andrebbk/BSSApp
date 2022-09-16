package com.example.bssapp.ui.classes;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bssapp.ClassProfessorItemDao;
import com.example.bssapp.ClassStudentItemDao;
import com.example.bssapp.DaoSession;
import com.example.bssapp.MainApplication;
import com.example.bssapp.R;
import com.example.bssapp.StudentItemDao;
import com.example.bssapp.databinding.FragmentAddStudentBinding;
import com.example.bssapp.db.models.ClassProfessorItem;
import com.example.bssapp.db.models.ClassStudentItem;
import com.example.bssapp.db.models.StudentItem;
import com.example.bssapp.ui.students.StudentAdapter;
import com.example.bssapp.ui.students.StudentListItem;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddStudentFragment extends Fragment {

    private FragmentAddStudentBinding binding;

    private ListView listViewAddStudents;
    private SearchView searchViewAddStudents;

    private ClassListItem currentClass;
    private ClassStudentItemDao classStudentItemDao;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddStudentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (getArguments() != null) {
            currentClass = (ClassListItem) getArguments().getSerializable("SelectedClass");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LoadControllers(root);
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void LoadControllers(View root){

        //Db
        DaoSession daoSession = ((MainApplication) requireActivity().getApplication()).getDaoSession();
        StudentItemDao studentItemDao = daoSession.getStudentItemDao();
        List<StudentItem> studentsData = studentItemDao.queryBuilder()
                .where(StudentItemDao.Properties.Deleted.eq(false))
                .orderAsc(StudentItemDao.Properties.FirstName, StudentItemDao.Properties.LastName)
                .list();

        classStudentItemDao = daoSession.getClassStudentItemDao();
        List<ClassStudentItem> classStudentsData = classStudentItemDao.queryBuilder()
                .where(ClassStudentItemDao.Properties.ClassId.eq(currentClass.getClassId()))
                .list();

        listViewAddStudents = root.findViewById(R.id.listViewAddStudents);

        ArrayList<AddStudentListItem> studentsList = new ArrayList<>();

        for (StudentItem object : studentsData) {
            boolean isRegistred = false;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                isRegistred = classStudentsData.stream().filter(o -> o.getStudentId().equals(object.getStudentId())).findFirst().isPresent();
            }
            studentsList.add(new AddStudentListItem(object.getStudentId(), object.getFirstName() + " " + object.getLastName(),
                    (object.getIsAdult() ?  R.drawable.user : R.drawable.cute_baby), isRegistred));
        }

        //Costume adapter
        AddStudentAdapter adapter = new AddStudentAdapter(this.requireActivity(), R.layout.list_add_student_row, studentsList, this);
        listViewAddStudents.setAdapter(adapter);

        searchViewAddStudents = root.findViewById(R.id.searchViewAddStudents);
        searchViewAddStudents.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String text) {

                ((StudentAdapter) listViewAddStudents.getAdapter()).getFilter().filter(text);
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String text) {

                return false;
            }

        });

        searchViewAddStudents.setOnCloseListener(() -> {
            ((StudentAdapter) listViewAddStudents.getAdapter()).getFilter().filter("");
            searchViewAddStudents.onActionViewCollapsed();
            return false;
        });

        searchViewAddStudents.setOnClickListener(v -> searchViewAddStudents.onActionViewExpanded());

        searchViewAddStudents.setOnQueryTextFocusChangeListener((view, b) -> {
            if(!b)
            {
                if(searchViewAddStudents.getQuery().toString().length() < 1)
                {
                    searchViewAddStudents.onActionViewCollapsed();
                }

                searchViewAddStudents.clearFocus();

            }
        });
    }

    public void AddClassStudent(long studentId){

        //create new class student
        ClassStudentItem newClassStudent = new ClassStudentItem();
        newClassStudent.setStudentId(studentId);
        newClassStudent.setClassId(currentClass.getClassId());
        newClassStudent.setCreateDate(Calendar.getInstance().getTime());

        classStudentItemDao.save(newClassStudent);
    }

    public void RemoveClassStudent(long studentId){

        //select * from class & student
        QueryBuilder<ClassStudentItem> classStudentData = classStudentItemDao.queryBuilder()
                .where(ClassStudentItemDao.Properties.ClassId.eq(currentClass.getClassId()),
                        ClassStudentItemDao.Properties.StudentId.eq(studentId));

        //delete current class student
        for(ClassStudentItem classStudent : classStudentData.list()){
            classStudentItemDao.delete(classStudent);
        }
    }
}