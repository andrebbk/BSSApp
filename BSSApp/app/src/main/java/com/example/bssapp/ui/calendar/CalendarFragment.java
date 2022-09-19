package com.example.bssapp.ui.calendar;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.bssapp.R;
import com.example.bssapp.databinding.FragmentCalendarBinding;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.EasyPermissions;

public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding binding;

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
        List<EventDay> events = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, R.drawable.person_surfing));

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2022, 9, 4);
        events.add(new EventDay(calendar2, R.drawable.surf_spot));

        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(2022, 8, 14);
        events.add(new EventDay(calendar3, R.drawable.class_icon_sup));

        CalendarView calendarBSSView = (CalendarView) view.findViewById(R.id.calendarBBSView);
        calendarBSSView.setEvents(events);

        calendarBSSView.setOnDayClickListener(new OnDayClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDayClick(EventDay eventDay) {
                if(eventDay != null){

                    //open popup with all the classes form event day
                    Calendar pickedDay = eventDay.getCalendar();
                    System.out.println(pickedDay.getTime());
                }
            }
        });
    }

    private void AskPermission()
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
    }
}