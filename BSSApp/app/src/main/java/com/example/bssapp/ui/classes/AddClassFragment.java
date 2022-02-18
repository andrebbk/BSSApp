package com.example.bssapp.ui.classes;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.example.bssapp.R;
import com.example.bssapp.databinding.FragmentAddClassBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddClassFragment extends Fragment {

    private FragmentAddClassBinding binding;

    AutoCompleteTextView autoCompleteTextView;
    AutoCompleteTextView autoCompleteLocal;
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
        sportsList.add(new DropdownViewModel(2, "Yoga"));
        sportsList.add(new DropdownViewModel(3, "Paddle"));
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
        date = Calendar.getInstance();

        SimpleDateFormat sdFormat = new SimpleDateFormat("dd MMMM yyyy  HH:mm", Locale.getDefault());
        calendarText.setText(sdFormat.format(date.getTime()));

        ImageView calendarIcon = view.findViewById(R.id.imageViewCalendar);
        calendarIcon.setOnClickListener(view1 -> showDateTimePicker(sdFormat));

        autoCompleteLocal = view.findViewById(R.id.autoCompleteLocal);

        ArrayList<DropdownViewModel> localizationsList = new ArrayList<>();
        localizationsList.add(new DropdownViewModel(1, "Barrinha"));
        localizationsList.add(new DropdownViewModel(2, "Praia Velha"));
        localizationsList.add(new DropdownViewModel(3, "Esmoriz"));
        localizationsList.add(new DropdownViewModel(4, "Cangas"));
        localizationsList.add(new DropdownViewModel(5, "Douro"));
        localizationsList.add(new DropdownViewModel(6, "Maceda"));

        ArrayAdapter<DropdownViewModel> arrayAdapterLocal = new ArrayAdapter<>(requireContext(),  R.layout.options_sports_item, localizationsList);
        autoCompleteLocal.setText(arrayAdapterLocal.getItem(0).toString(), false);
        autoCompleteLocal.setAdapter(arrayAdapterLocal);
        autoCompleteLocal.setOnItemClickListener((parent, view1, position, id) -> {
            DropdownViewModel m=(DropdownViewModel) parent.getItemAtPosition(position);
            String name = m.getText();
            int _id = m.getId();
        });
    }
    public void showDateTimePicker(SimpleDateFormat sdFormat) {
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.TimePickerDialogStyle, (view, year, monthOfYear, dayOfMonth) -> {
            date.set(year, monthOfYear, dayOfMonth);
            calendarText.setText(sdFormat.format(date.getTime()));
            new TimePickerDialog(getActivity(), R.style.TimePickerDialogStyle, (view1, hourOfDay, minute) -> {
                date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                date.set(Calendar.MINUTE, minute);
                calendarText.setText(sdFormat.format(date.getTime()));
            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true)
                    .show();
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE));

        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialog.show();
    }

}