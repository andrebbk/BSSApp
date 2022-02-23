package com.example.bssapp.ui.classes;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.example.bssapp.DaoSession;
import com.example.bssapp.MainApplication;
import com.example.bssapp.ProfessorItemDao;
import com.example.bssapp.R;
import com.example.bssapp.SportItemDao;
import com.example.bssapp.SpotItemDao;
import com.example.bssapp.databinding.FragmentAddClassBinding;
import com.example.bssapp.db.models.ProfessorItem;
import com.example.bssapp.db.models.SportItem;
import com.example.bssapp.db.models.SpotItem;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class AddClassFragment extends Fragment {

    private FragmentAddClassBinding binding;

    private DaoSession daoSession;

    AutoCompleteTextView autoCompleteTextView;
    AutoCompleteTextView autoCompleteLocal;
    Calendar date;
    TextInputEditText calendarText;

    // initialize variables to instructors controller
    TextInputEditText textViewProfs;
    boolean[] selectedProf;
    ArrayList<Integer> profsList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddClassBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Db
        daoSession = ((MainApplication) requireActivity().getApplication()).getDaoSession();

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
        SportItemDao sportItemDao = daoSession.getSportItemDao();
        ArrayList<DropdownViewModel> sportsList = new ArrayList<>();

        List<SportItem> sportsData = sportItemDao.queryBuilder()
                .where(SportItemDao.Properties.Deleted.eq(false))
                .orderAsc(SportItemDao.Properties.SportName)
                .list();

        int p = 0, surfPos = 0;
        for (SportItem object : sportsData) {
            sportsList.add(new DropdownViewModel(object.getSportId(), object.getSportName()));

            if(object.getSportName().contains("Surf")) surfPos = p;
            p++;
        }

        ArrayAdapter<DropdownViewModel> arrayAdapter = new ArrayAdapter<>(requireContext(),  R.layout.options_sports_item, sportsList);
        autoCompleteTextView.setText(arrayAdapter.getItem(surfPos).toString(), false);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener((parent, view1, position, id) -> {
            DropdownViewModel m=(DropdownViewModel) parent.getItemAtPosition(position);
            String name = m.getText();
            Long _id = m.getId();
        });

        calendarText = view.findViewById(R.id.calendarEditText);
        date = Calendar.getInstance();

        SimpleDateFormat sdFormat = new SimpleDateFormat("dd MMMM yyyy  HH:mm", Locale.getDefault());
        calendarText.setText(sdFormat.format(date.getTime()));

        ImageView calendarIcon = view.findViewById(R.id.imageViewCalendar);
        calendarIcon.setOnClickListener(view1 -> showDateTimePicker(sdFormat));

        autoCompleteLocal = view.findViewById(R.id.autoCompleteLocal);
        SpotItemDao spotItemDao = daoSession.getSpotItemDao();
        ArrayList<DropdownViewModel> localizationsList = new ArrayList<>();

        List<SpotItem> spotsData = spotItemDao.queryBuilder()
                .where(SpotItemDao.Properties.Deleted.eq(false))
                .orderAsc(SpotItemDao.Properties.SpotName)
                .list();

        for (SpotItem object : spotsData) {
            localizationsList.add(new DropdownViewModel(object.getSpotId(), object.getSpotName()));
        }

        ArrayAdapter<DropdownViewModel> arrayAdapterLocal = new ArrayAdapter<>(requireContext(),  R.layout.options_sports_item, localizationsList);
        autoCompleteLocal.setText(arrayAdapterLocal.getItem(0).toString(), false);
        autoCompleteLocal.setAdapter(arrayAdapterLocal);
        autoCompleteLocal.setOnItemClickListener((parent, view1, position, id) -> {
            DropdownViewModel m=(DropdownViewModel) parent.getItemAtPosition(position);
            String name = m.getText();
            Long _id = m.getId();
        });

        // assign variable
        textViewProfs = view.findViewById(R.id.profsEditText);
        textViewProfs.setFocusableInTouchMode(false);

        String[] loadProfsArray = LoadInstructors();

        // initialize selected instructors array
        selectedProf = new boolean[loadProfsArray.length];

        textViewProfs.setOnClickListener(view12 -> {

            textViewProfs.setFocusable(false);

            // Initialize alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(requireContext(), R.style.MyAlertDialogTheme));

            // set title
            builder.setTitle("Selecionar instrutores");

            // set dialog non cancelable
            builder.setCancelable(false);

            builder.setMultiChoiceItems(loadProfsArray, selectedProf, (dialogInterface, i, b) -> {
                // check condition
                if (b) {
                    // when checkbox selected
                    // Add position  in lang list
                    profsList.add(i);
                    // Sort array list
                    Collections.sort(profsList);
                } else {
                    // when checkbox unselected
                    // Remove position from langList
                    profsList.remove(Integer.valueOf(i));
                }
            });

            builder.setPositiveButton("Ok", (dialogInterface, i) -> {
                // Initialize string builder
                StringBuilder stringBuilder = new StringBuilder();
                // use for loop
                for (int j = 0; j < profsList.size(); j++) {
                    // concat array value
                    stringBuilder.append(loadProfsArray[profsList.get(j)]);
                    // check condition
                    if (j != profsList.size() - 1) {
                        // When j value  not equal
                        // to lang list size - 1
                        // add comma
                        stringBuilder.append(", ");
                    }
                }
                // set text on textView
                textViewProfs.setText(stringBuilder.toString());
            });

            builder.setNegativeButton("Cancelar", (dialogInterface, i) -> {
                // dismiss dialog
                dialogInterface.dismiss();
            });
            builder.setNeutralButton("Limpar tudo", (dialogInterface, i) -> {
                // use for loop
                for (int j = 0; j < selectedProf.length; j++) {
                    // remove all selection
                    selectedProf[j] = false;
                    // clear profs list
                    profsList.clear();
                    // clear text view value
                    textViewProfs.setText("  ");
                }
            });
            // show dialog
            builder.show();
        });
    }

    private void showDateTimePicker(SimpleDateFormat sdFormat) {
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

    private String[] LoadInstructors()
    {
        ProfessorItemDao professorItemDao = daoSession.getProfessorItemDao();
        List<ProfessorItem> professorsData = professorItemDao.queryBuilder()
                .where(ProfessorItemDao.Properties.Deleted.eq(false))
                .orderAsc(ProfessorItemDao.Properties.FirstName, ProfessorItemDao.Properties.LastName)
                .list();

        if(professorsData != null)
        {
            String[] arrayOutput = new String[professorsData.size()];

            for (int i = 0; i < professorsData.size(); i++) {
                arrayOutput[i] = professorsData.get(i).getFirstName() + " " + professorsData.get(i).getLastName();
            }
            return arrayOutput;
        }

        return new String[]{ };
    }

}