package com.example.bssapp.ui.calendar;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.example.bssapp.ClassItemDao;
import com.example.bssapp.ClassStudentItemDao;
import com.example.bssapp.DaoSession;
import com.example.bssapp.MainApplication;
import com.example.bssapp.MenuActivity;
import com.example.bssapp.R;
import com.example.bssapp.SportItemDao;
import com.example.bssapp.StudentItemDao;
import com.example.bssapp.UtilsClass;
import com.example.bssapp.databinding.FragmentCalendarBinding;
import com.example.bssapp.db.models.ClassItem;
import com.example.bssapp.db.models.ClassStudentItem;
import com.example.bssapp.db.models.SportItem;
import com.example.bssapp.db.models.SpotItem;
import com.example.bssapp.db.models.StudentItem;
import com.example.bssapp.ui.classes.ClassAdapter;
import com.example.bssapp.ui.classes.ClassListItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding binding;

    //Db
    private DaoSession daoSession;

    //View controllers
    private CalendarView calendarBSSView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LoadControllers(root);

        if (getArguments() != null) {
            LoadDayClassDialog((Calendar) getArguments().getSerializable("ClassDate"));
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void LoadControllers(View view){
        daoSession = ((MainApplication) requireActivity().getApplication()).getDaoSession();

        calendarBSSView = view.findViewById(R.id.calendarBBSView);
        calendarBSSView.setOnDayClickListener(eventDay -> {
            if(eventDay != null){
                LoadDayClassDialog(eventDay.getCalendar());
            }
        });

        LoadCalendarClasses();
    }

    private void LoadCalendarClasses(){

        Calendar currentDate = Calendar.getInstance();
        currentDate.set(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_WEEK), 0, 0, 0);

        //get all next classes
        ClassItemDao classItemDao = daoSession.getClassItemDao();
        List<ClassItem> classesData = classItemDao.queryBuilder()
                .where(ClassItemDao.Properties.Deleted.eq(false),
                        ClassItemDao.Properties.ClassDateTime.ge(currentDate.getTime()))
                .orderDesc(ClassItemDao.Properties.ClassDateTime)
                .list();

        //get all sports
        SportItemDao sportItemDao = daoSession.getSportItemDao();
        List<SportItem> sportsData = sportItemDao.queryBuilder()
                .where((SportItemDao.Properties.Deleted.eq(false)))
                .orderAsc(SportItemDao.Properties.SportId)
                .list();

        if(classesData != null && !classesData.isEmpty())
        {
            List<EventDay> events = new ArrayList<>();

            for (ClassItem classItem : classesData) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(classItem.getClassDateTime());
                events.add(new EventDay(cal, GetClassIcon(classItem.getSportId(), sportsData)));
            }

            if(!events.isEmpty()){
                calendarBSSView.setEvents(events);
            }
        }
    }

    @DrawableRes
    private int GetClassIcon(long sportId, List<SportItem> sportsData){

        if(sportsData != null && !sportsData.isEmpty()){

            SportItem classSport = findSportUsingEnhancedForLoop(sportId, sportsData);
            if(classSport != null)
            {
                String sport = classSport.getSportName();
                if(sport.contains("Surf"))
                {
                    return R.drawable.person_surfing;
                }
                else if(sport.contains("Yoga"))
                {
                    return R.drawable.class_icon_yoga;
                }
                else if(sport.contains("Canoagem"))
                {
                    return R.drawable.class_icon_kayak;
                }
                else if(sport.contains("SUPaddle"))
                {
                    return R.drawable.class_icon_sup;
                }
                else if(sport.contains("Evento"))
                {
                    return R.drawable.bss_transparent;
                }
            }
        }

        return R.drawable.person_surfing;
    }

    private SportItem findSportUsingEnhancedForLoop(
            long sportId, List<SportItem> sportsData) {

        for (SportItem sport : sportsData) {
            if (sport.getSportId().equals(sportId)) {
                return sport;
            }
        }
        return null;
    }

    @SuppressLint("SetTextI18n")
    private void LoadDayClassDialog(Calendar pickedDay){
        View dayClassesDialog = LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_day_classes,null);

        AlertDialog.Builder alert = new AlertDialog.Builder(requireActivity());
        alert.setView(dayClassesDialog);

        TextView dayText = dayClassesDialog.findViewById(R.id.textViewDay);
        if(dayText != null) {
            SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String classDateStr = sdFormat.format(pickedDay.getTime());
            dayText.setText(classDateStr);
        }

        final AlertDialog dialog = alert.create();

        FloatingActionButton fabAddClass = dayClassesDialog.findViewById(R.id.dialog_fabAddClass);

        Integer timeFrameDate = UtilsClass.CompareDates(pickedDay, Calendar.getInstance());

        if(timeFrameDate != 0){
            fabAddClass.setOnClickListener(v -> {
                //Update current time
                pickedDay.set(Calendar.HOUR_OF_DAY, Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                pickedDay.set(Calendar.MINUTE, Calendar.getInstance().get(Calendar.MINUTE));
                pickedDay.set(Calendar.SECOND, Calendar.getInstance().get(Calendar.SECOND));

                //SimpleDateFormat sdFormat = new SimpleDateFormat("dd MMMM yyyy  HH:mm", Locale.getDefault());
                ((MenuActivity) requireActivity()).changeToAddClass(pickedDay);

                dialog.dismiss();
            });
        }
        else if(timeFrameDate == 0){
            //past date
            fabAddClass.setVisibility(View.INVISIBLE);
        }


        ListView listViewDialogClasses = dayClassesDialog.findViewById(R.id.listViewDayClasses);
        TextView textViewNoScheduledClasses = dayClassesDialog.findViewById(R.id.textViewNoScheduledClassesMsg);
        LoadDialogClasses(listViewDialogClasses, pickedDay, dialog, textViewNoScheduledClasses);

        //this line removed app bar from dialog and make it transparent and you see the image is like floating outside dialog box.
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
    }

    private void LoadDialogClasses(ListView listViewClasses, Calendar pickedDate, AlertDialog dialog, TextView textViewNoScheduledClasses) {

        Calendar startDate = Calendar.getInstance();
        startDate.set(Calendar.YEAR, pickedDate.get(Calendar.YEAR));
        startDate.set(Calendar.MONTH, pickedDate.get(Calendar.MONTH));
        startDate.set(Calendar.DAY_OF_YEAR, pickedDate.get(Calendar.DAY_OF_YEAR));
        startDate.set(Calendar.HOUR_OF_DAY, 0);
        startDate.set(Calendar.MINUTE, 0);
        startDate.set(Calendar.SECOND, 0);

        Calendar endDate = Calendar.getInstance();
        endDate.set(Calendar.YEAR, pickedDate.get(Calendar.YEAR));
        endDate.set(Calendar.MONTH, pickedDate.get(Calendar.MONTH));
        endDate.set(Calendar.DAY_OF_YEAR, pickedDate.get(Calendar.DAY_OF_YEAR));
        endDate.set(Calendar.HOUR_OF_DAY, 23);
        endDate.set(Calendar.MINUTE, 59);
        endDate.set(Calendar.SECOND, 59);

        System.out.println(startDate.getTime());
        System.out.println(endDate.getTime());
        ClassItemDao classItemDao = daoSession.getClassItemDao();
        List<ClassItem> classesData = classItemDao.queryBuilder()
                .where(ClassItemDao.Properties.Deleted.eq(false),
                        ClassItemDao.Properties.ClassDateTime.ge(startDate.getTime()),
                        ClassItemDao.Properties.ClassDateTime.le(endDate.getTime()))
                .orderAsc(ClassItemDao.Properties.ClassDateTime)
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
                SimpleDateFormat sdFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String classDateStr = (sdFormat.format(object.getClassDateTime())).replace(":", "h");

                //save complete date format
                SimpleDateFormat completeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                String classDateCompleteStr = (completeFormat.format(object.getClassDateTime())).replace(":", "h");

                Calendar classDateValue = Calendar.getInstance();
                classDateValue.setTime(object.getClassDateTime());

                //registered students number
                long nStudents = 0;
                QueryBuilder<StudentItem> queryBuilder = daoSession.getStudentItemDao().queryBuilder();
                queryBuilder.join(ClassStudentItem.class, ClassStudentItemDao.Properties.StudentId)
                        .where(ClassStudentItemDao.Properties.ClassId.eq(object.getClassId()));
                nStudents = queryBuilder.where(StudentItemDao.Properties.Deleted.eq(false)).count();

                classesList.add(new ClassListItem(object.getClassId(), sportItem.getSportName(), object.getSportId(), spotItem.getSpotName(), object.getSpotId(),
                        classDateStr, classDateValue, String.valueOf(nStudents), classDateCompleteStr));
            }

            //Last empty row
            classesList.add(new ClassListItem(true));

            //Costume adapter
            ClassAdapter adapter = new ClassAdapter(this.requireActivity(), R.layout.dialog_list_class_row, classesList);
            listViewClasses.setAdapter(adapter);

            listViewClasses.setOnItemClickListener((parent, view, position, id) -> {
                ClassListItem selectedClass = (ClassListItem) parent.getItemAtPosition(position);
                selectedClass.setClassDate(selectedClass.getCompleteClassDate()); // fix date presentation label (not only time but date)

                if(selectedClass != null && selectedClass.getClassId() != 0){
                    ((MenuActivity) requireActivity()).changeToClassFragment(selectedClass);
                    dialog.dismiss();
                }
            });
        }
        else
        {
            textViewNoScheduledClasses.setVisibility(View.VISIBLE);
        }
    }

    //AskPermissions
    /*private void AskPermission()
    {
        String[] perms = { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE };
        if (!EasyPermissions.hasPermissions(requireContext(), perms))
        {
            // Ask for both permissions
            EasyPermissions.requestPermissions(this, "We need permissions because this and that",
                    123, perms);
        }
    }*/
}