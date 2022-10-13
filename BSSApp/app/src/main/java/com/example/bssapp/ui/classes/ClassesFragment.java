package com.example.bssapp.ui.classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.msm.rangedatepicker.DateRangePickerFragment;
import com.example.bssapp.ClassItemDao;
import com.example.bssapp.ClassStudentItemDao;
import com.example.bssapp.DaoSession;
import com.example.bssapp.MainApplication;
import com.example.bssapp.MenuActivity;
import com.example.bssapp.R;
import com.example.bssapp.StudentItemDao;
import com.example.bssapp.databinding.FragmentClassesBinding;
import com.example.bssapp.db.models.ClassItem;
import com.example.bssapp.db.models.ClassStudentItem;
import com.example.bssapp.db.models.SportItem;
import com.example.bssapp.db.models.SpotItem;
import com.example.bssapp.db.models.StudentItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ClassesFragment extends Fragment {

    private FragmentClassesBinding binding;

    private ListView listViewClasses;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentClassesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listViewClasses = root.findViewById(R.id.listViewClasses);

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

        Calendar twoWeeksTime = Calendar.getInstance();
        twoWeeksTime.add(Calendar.WEEK_OF_MONTH, -2);

        //Db
        DaoSession daoSession = ((MainApplication) requireActivity().getApplication()).getDaoSession();
        ClassItemDao classItemDao = daoSession.getClassItemDao();
        List<ClassItem> classesData = classItemDao.queryBuilder()
                .where(ClassItemDao.Properties.Deleted.eq(false),
                        ClassItemDao.Properties.ClassDateTime.ge(twoWeeksTime.getTime()))
                .orderDesc(ClassItemDao.Properties.ClassDateTime)
                .list();

        if(classesData != null && classesData.size() > 0)
        {
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
                long nStudents = 0;
                QueryBuilder<StudentItem> queryBuilder = daoSession.getStudentItemDao().queryBuilder();
                queryBuilder.join(ClassStudentItem.class, ClassStudentItemDao.Properties.StudentId)
                        .where(ClassStudentItemDao.Properties.ClassId.eq(object.getClassId()));
                nStudents = queryBuilder.where(StudentItemDao.Properties.Deleted.eq(false)).count();

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

                if(selectedClass != null && selectedClass.ClassId != null){
                    ((MenuActivity) requireActivity()).changeToClassFragment(selectedClass);
                }
            });
        }

        Calendar maxDay = Calendar.getInstance();
        maxDay.add(Calendar.YEAR, 1);

        ((MenuActivity)getActivity()).calendarRangeFilter.setOnClickListener(view -> {
            DateRangePickerFragment rangePickerFragment = new DateRangePickerFragment();
            rangePickerFragment.initialize((startDay, startMonth, startYear, endDay, endMonth, endYear) -> {
                //work your method here
                DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
                Calendar startDate = Calendar.getInstance();
                Calendar endDate = Calendar.getInstance();
                startDate.set(startYear, startMonth, startDay, 0, 0, 0);
                endDate.set(endYear, endMonth, endDay, 23, 59, 59);

                ReloadListViewClasses(startDate, endDate);
            }, true);

            //optional,set names for tabHost
            rangePickerFragment.setNameTabHost("Data Início", "Data Fim");

            //optional,to display the selected dates in the RangeDatePicker
            rangePickerFragment.showPerido(true);
            rangePickerFragment.show(getChildFragmentManager(), "MenuActivity");
        });
    }

    private void ReloadListViewClasses(Calendar startDate, Calendar endDate){
        //Db
        DaoSession daoSession = ((MainApplication) requireActivity().getApplication()).getDaoSession();
        ClassItemDao classItemDao = daoSession.getClassItemDao();
        List<ClassItem> classesData = classItemDao.queryBuilder()
                .where(ClassItemDao.Properties.Deleted.eq(false),
                        ClassItemDao.Properties.ClassDateTime.ge(startDate.getTime()),
                        ClassItemDao.Properties.ClassDateTime.le(endDate.getTime()))
                .orderDesc(ClassItemDao.Properties.ClassDateTime)
                .list();

        if(classesData != null)
        {
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