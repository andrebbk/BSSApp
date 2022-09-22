package com.example.bssapp.ui.classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bssapp.ClassItemDao;
import com.example.bssapp.ClassStudentItemDao;
import com.example.bssapp.DaoSession;
import com.example.bssapp.MainApplication;
import com.example.bssapp.MenuActivity;
import com.example.bssapp.R;
import com.example.bssapp.databinding.FragmentClassesBinding;
import com.example.bssapp.db.models.ClassItem;
import com.example.bssapp.db.models.SportItem;
import com.example.bssapp.db.models.SpotItem;
import com.example.bssapp.ui.professors.ProfessorListItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ClassesFragment extends Fragment {

    private FragmentClassesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentClassesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LoadControllers(root);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void LoadControllers(View root) {
        FloatingActionButton fabAddClass = root.findViewById(R.id.fabAddClass);
        fabAddClass.setOnClickListener(v -> ((MenuActivity) requireActivity()).changeToAddClass(null));

        ListView listViewClasses;

        //Db
        DaoSession daoSession = ((MainApplication) requireActivity().getApplication()).getDaoSession();
        ClassItemDao classItemDao = daoSession.getClassItemDao();
        List<ClassItem> classesData = classItemDao.queryBuilder()
                .where(ClassItemDao.Properties.Deleted.eq(false))
                .orderDesc(ClassItemDao.Properties.ClassDateTime)
                .list();

        if(classesData != null && classesData.size() > 0)
        {
            listViewClasses = root.findViewById(R.id.listViewClasses);

            ArrayList<ClassListItem> classesList = new ArrayList<>();

            for (ClassItem object : classesData) {

                //sport
                SportItem sportItem = daoSession.getSportItemDao().load(object.getSportId());

                //spot
                SpotItem spotItem = daoSession.getSpotItemDao().load(object.getSpotId());

                //date
                SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                String classDateStr = (sdFormat.format(object.getClassDateTime())).replace(":", "h");

                //registered students number
                long nStudents = daoSession.getClassStudentItemDao().queryBuilder()
                        .where(ClassStudentItemDao.Properties.ClassId.eq(object.getClassId()))
                        .count();

                classesList.add(new ClassListItem(object.getClassId(), sportItem.getSportName(), object.getSportId(), spotItem.getSpotName(),
                        classDateStr, String.valueOf(nStudents), null));
            }

            //Last empty row
            classesList.add(new ClassListItem(true));

            //Costume adapter
            ClassAdapter adapter = new ClassAdapter(this.requireActivity(), R.layout.list_class_row, classesList);
            listViewClasses.setAdapter(adapter);

            listViewClasses.setOnItemClickListener((parent, view, position, id) -> {
                ClassListItem selectedClass = (ClassListItem) parent.getItemAtPosition(position);

                if(selectedClass != null){
                    ((MenuActivity) requireActivity()).changeToClassFragment(selectedClass);
                }
            });
        }

    }

}