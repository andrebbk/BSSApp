package com.example.bssapp.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.example.bssapp.ClassItemDao;
import com.example.bssapp.DaoSession;
import com.example.bssapp.MainApplication;
import com.example.bssapp.R;
import com.example.bssapp.SportItemDao;
import com.example.bssapp.databinding.FragmentCalendarBinding;
import com.example.bssapp.db.models.ClassItem;
import com.example.bssapp.db.models.SportItem;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void LoadControllers(View view){
        daoSession = ((MainApplication) requireActivity().getApplication()).getDaoSession();

        calendarBSSView = (CalendarView) view.findViewById(R.id.calendarBBSView);
        calendarBSSView.setOnDayClickListener(eventDay -> {
            if(eventDay != null){

                //open popup with all the classes form event day
                Calendar pickedDay = eventDay.getCalendar();
                System.out.println(pickedDay.getTime());
            }
        });

        LoadCalendarClasses();
    }

    private void LoadCalendarClasses(){
        //get all next classes
        ClassItemDao classItemDao = daoSession.getClassItemDao();
        List<ClassItem> classesData = classItemDao.queryBuilder()
                .where(ClassItemDao.Properties.Deleted.eq(false),
                        ClassItemDao.Properties.ClassDateTime.ge(Calendar.getInstance().getTime()))
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
                else if(sport.contains("SUPPaddle"))
                {
                    return R.drawable.class_icon_sup;
                }
            }
        }

        return R.drawable.person_surfing;
    }

    public SportItem findSportUsingEnhancedForLoop(
            long sportId, List<SportItem> sportsData) {

        for (SportItem sport : sportsData) {
            if (sport.getSportId().equals(sportId)) {
                return sport;
            }
        }
        return null;
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