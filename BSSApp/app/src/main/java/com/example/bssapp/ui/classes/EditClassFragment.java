package com.example.bssapp.ui.classes;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import com.example.bssapp.ClassItemDao;
import com.example.bssapp.ClassProfessorItemDao;
import com.example.bssapp.DaoSession;
import com.example.bssapp.MainApplication;
import com.example.bssapp.MenuActivity;
import com.example.bssapp.ProfessorItemDao;
import com.example.bssapp.R;
import com.example.bssapp.SportItemDao;
import com.example.bssapp.SpotItemDao;
import com.example.bssapp.databinding.FragmentEditClassBinding;
import com.example.bssapp.db.models.ClassItem;
import com.example.bssapp.db.models.ClassProfessorItem;
import com.example.bssapp.db.models.ProfessorItem;
import com.example.bssapp.db.models.SportItem;
import com.example.bssapp.db.models.SpotItem;
import com.google.android.material.textfield.TextInputEditText;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class EditClassFragment extends Fragment {

    private FragmentEditClassBinding binding;

    private DaoSession daoSession;
    private ClassItemDao classItemDao;
    private ClassProfessorItemDao classProfessorItemDao;
    private ProfessorItemDao professorItemDao;

    AutoCompleteTextView autoCompleteTextView;
    AutoCompleteTextView autoCompleteLocal;
    Calendar date;
    TextInputEditText calendarText;
    TextInputEditText obsText;

    //reset form
    ArrayAdapter<DropdownViewModel> arrayAdapter;
    ArrayAdapter<DropdownViewModel> arrayAdapterLocal;
    int surfPos = 0;
    String sportName = "";

    // initialize variables to instructors controller
    TextInputEditText textViewProfs;
    boolean[] selectedProf;
    ArrayList<Integer> profsList = new ArrayList<>();
    Long[] profIdsList = new Long[] {};

    private ClassListItem currentClass;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentEditClassBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (getArguments() != null) {
            currentClass = (ClassListItem) getArguments().getSerializable("SelectedClass");
        }

        //Db
        daoSession = ((MainApplication) requireActivity().getApplication()).getDaoSession();
        classItemDao = daoSession.getClassItemDao();
        classProfessorItemDao = daoSession.getClassProfessorItemDao();
        professorItemDao = daoSession.getProfessorItemDao();

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
    private void LoadControllers(View view)
    {
        AtomicReference<Long> sportIdValue = new AtomicReference<>();
        AtomicReference<Long> spotIdValue = new AtomicReference<>();

        autoCompleteTextView = view.findViewById(R.id.autoCompleteSport);
        SportItemDao sportItemDao = daoSession.getSportItemDao();
        ArrayList<DropdownViewModel> sportsList = new ArrayList<>();

        List<SportItem> sportsData = sportItemDao.queryBuilder()
                .where(SportItemDao.Properties.Deleted.eq(false))
                .orderAsc(SportItemDao.Properties.SportName)
                .list();

        int p = 0;
        for (SportItem object : sportsData) {
            sportsList.add(new DropdownViewModel(object.getSportId(), object.getSportName()));

            if(object.getSportName().contains("Surf")) surfPos = p;
            p++;
        }

        arrayAdapter = new ArrayAdapter<>(requireContext(),  R.layout.options_sports_item, sportsList);
        autoCompleteTextView.setText(arrayAdapter.getItem(surfPos).toString(), false);
        sportName = arrayAdapter.getItem(surfPos).toString();
        sportIdValue.set(arrayAdapter.getItem(surfPos).getId());
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener((parent, view1, position, id) -> {
            DropdownViewModel m=(DropdownViewModel) parent.getItemAtPosition(position);
            sportIdValue.set(m.getId());
            sportName = m.getText();
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

        arrayAdapterLocal = new ArrayAdapter<>(requireContext(),  R.layout.options_sports_item, localizationsList);
        autoCompleteLocal.setText(arrayAdapterLocal.getItem(0).toString(), false);
        spotIdValue.set(arrayAdapterLocal.getItem(0).getId());
        autoCompleteLocal.setAdapter(arrayAdapterLocal);
        autoCompleteLocal.setOnItemClickListener((parent, view1, position, id) -> {
            DropdownViewModel m=(DropdownViewModel) parent.getItemAtPosition(position);
            spotIdValue.set(m.getId());
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

        obsText = view.findViewById(R.id.obsEditText);

        Button editClassBtn = view.findViewById(R.id.buttonEditSaveClass);
        editClassBtn.setOnClickListener(view13 -> {

            //ProfsIds
            ArrayList<Long> prodIds = new ArrayList<>();
            for (int j = 0; j < profsList.size(); j++) {
                prodIds.add(profIdsList[profsList.get(j)]);
            }
            //SaveNewClass(sportIdValue, spotIdValue, prodIds);
        });

        if(currentClass != null)
        {
            //Sport
            autoCompleteTextView.setText(currentClass.getSportName(), false);

            //Class date
            calendarText.setText(currentClass.getClassDate());

            //Spot
            autoCompleteLocal.setText(currentClass.getSpotName(), false);

            //Load other data from DB
            ClassItem classItem = daoSession.getClassItemDao().load(currentClass.getClassId());
            if(classItem != null)
            {
                //Instructors
                QueryBuilder<ProfessorItem> queryBuilder = professorItemDao.queryBuilder();
                queryBuilder.join(ClassProfessorItem.class, ClassProfessorItemDao.Properties.ProfessorId)
                        .where(ClassProfessorItemDao.Properties.ClassId.eq(currentClass.getClassId()));
                List<ProfessorItem> profsLoadDB = queryBuilder.list();

                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < profsLoadDB.size(); j++) {

                    ProfessorItem prof = profsLoadDB.get(j);
                    String profDesignation = prof.getFirstName() + " " + prof.getLastName();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        profsList.add(Math.toIntExact(prof.getProfessorId()));
                    }

                    for (int i = 0; i < loadProfsArray.length; i++) {
                        if(loadProfsArray[i].equals(profDesignation))
                            selectedProf[i] = true;
                    }

                    stringBuilder.append(profDesignation);
                    if (j != profsLoadDB.size() - 1) {
                        stringBuilder.append(", ");
                    }
                }

                textViewProfs.setText(stringBuilder.toString());

                //Observations
                String observationsText = classItem.getObservations();
                if(TextUtils.isEmpty(observationsText)) observationsText = " ";
                obsText.setText(observationsText);
            }
        }
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
            Long[] arrayIds = new Long[professorsData.size()];

            for (int i = 0; i < professorsData.size(); i++) {
                arrayIds[i] = professorsData.get(i).getProfessorId();
                arrayOutput[i] = professorsData.get(i).getFirstName() + " " + professorsData.get(i).getLastName();
            }

            profIdsList = arrayIds;
            return arrayOutput;
        }
        return new String[]{ };
    }

    private void SaveNewClass(AtomicReference<Long> sportIdValue, AtomicReference<Long> spotIdValue, ArrayList<Long> profIds)
    {
        if(isNewClassValid(sportIdValue, spotIdValue, profIds))
        {
            //create new class
            ClassItem newClass = new ClassItem();
            newClass.setSportId(sportIdValue.get());
            newClass.setClassDateTime(date.getTime());
            newClass.setSpotId(spotIdValue.get());
            newClass.setObservations(Objects.requireNonNull(obsText.getText()).toString().trim());
            newClass.setCreateDate(Calendar.getInstance().getTime());
            newClass.setDeleted(false);

            long newClassId = classItemDao.insert(newClass);

            //associate instructors
            for (Long profId : profIds) {
                ClassProfessorItem newInstructor = new ClassProfessorItem();
                newInstructor.setClassId(newClassId);
                newInstructor.setProfessorId(profId);
                newInstructor.setCreateDate(Calendar.getInstance().getTime());

                classProfessorItemDao.save(newInstructor);
            }

            ClearFromAddClass();

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
            builder.setMessage("A aula de " + sportName + " foi registada com sucesso!")
                    .setPositiveButton("Ok", (dialog, id) -> {
                    });

            android.app.AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private boolean isNewClassValid(AtomicReference<Long> sportIdValue, AtomicReference<Long> spotIdValue, ArrayList<Long> profIds)
    {
        boolean isValid = true;

        if(sportIdValue.get() == null)
        {
            ((MenuActivity) requireActivity()).ShowSnackBar("A modalidade ?? obrigat??ria!");
            isValid = false;
        }
        else if(calendarText.getText() == null || TextUtils.isEmpty(calendarText.getText()))
        {
            ((MenuActivity) requireActivity()).ShowSnackBar("A data/hora ?? obrigat??ria!");
            isValid = false;
        }
        else if(spotIdValue.get() == null)
        {
            ((MenuActivity) requireActivity()).ShowSnackBar("O local ?? obrigat??rio!");
            isValid = false;
        }
        else if(profIds.size() < 1)
        {
            ((MenuActivity) requireActivity()).ShowSnackBar("Pelo menos 1 instrutor ?? obrigat??rio!");
            isValid = false;
        }

        return isValid;
    }

    private void ClearFromAddClass()
    {
        //Modalidade
        autoCompleteTextView.setText(arrayAdapter.getItem(surfPos).toString(), false);

        //Data & Hora
        date = Calendar.getInstance();
        SimpleDateFormat sdFormat = new SimpleDateFormat("dd MMMM yyyy  HH:mm", Locale.getDefault());
        calendarText.setText(sdFormat.format(date.getTime()));

        //Local
        autoCompleteLocal.setText(arrayAdapterLocal.getItem(0).toString(), false);

        //Instrutores
        for (int j = 0; j < selectedProf.length; j++) {
            // remove all selection
            selectedProf[j] = false;
            // clear profs list
            profsList.clear();
            // clear text view value
            textViewProfs.setText("  ");
        }

        //Obs
        obsText.clearFocus();
        obsText.setText(" ");
    }

}