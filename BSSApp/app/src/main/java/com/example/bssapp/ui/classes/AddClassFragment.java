package com.example.bssapp.ui.classes;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.example.bssapp.R;
import com.example.bssapp.databinding.FragmentAddClassBinding;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddClassFragment extends Fragment {

    private FragmentAddClassBinding binding;

    AutoCompleteTextView autoCompleteTextView;

    Calendar date;
    TextInputEditText calendarText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddClassBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LoadControllers(root);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void LoadControllers(View view)
    {
        autoCompleteTextView = view.findViewById(R.id.autoCompleteSport);

        ArrayList<DropdownViewModel> sportsList = new ArrayList<>();
        sportsList.add(new DropdownViewModel(1, "Surf"));
        sportsList.add(new DropdownViewModel(3, "Yoga"));
        sportsList.add(new DropdownViewModel(2, "Paddle"));
        sportsList.add(new DropdownViewModel(4, "Canoagem"));

        ArrayAdapter<DropdownViewModel> arrayAdapter = new ArrayAdapter<>(requireContext(),  R.layout.options_sports_item, sportsList);
        autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(), false);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setOnItemClickListener((parent, view1, position, id) -> {
            DropdownViewModel m=(DropdownViewModel) parent.getItemAtPosition(position);
            String name = m.getText();
            int _id = m.getId();
        });

        calendarText = view.findViewById(R.id.calendarEditText);

        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();

        SimpleDateFormat sdformat = new SimpleDateFormat("dd MMMM yyyy   HH:mm", Locale.getDefault());
        calendarText.setText(sdformat.format(date.getTime()));

        ImageView calendarIcon = view.findViewById(R.id.imageViewCalendar);
        calendarIcon.setOnClickListener(view1 -> { showDateTimePicker(sdformat); });
    }
    public void showDateTimePicker(SimpleDateFormat sdformat) {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.TimePickerDialogStyle, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                calendarText.setText(sdformat.format(date.getTime()));
                new TimePickerDialog(getActivity(), R.style.TimePickerDialogStyle, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        calendarText.setText(sdformat.format(date.getTime()));
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true)
                        .show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE));

        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialog.show();
    }

}