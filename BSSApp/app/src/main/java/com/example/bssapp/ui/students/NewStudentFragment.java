package com.example.bssapp.ui.students;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.bssapp.DaoSession;
import com.example.bssapp.MainApplication;
import com.example.bssapp.MenuActivity;
import com.example.bssapp.R;
import com.example.bssapp.StudentItemDao;
import com.example.bssapp.UtilsClass;
import com.example.bssapp.databinding.FragmentNewStudentBinding;
import com.example.bssapp.db.models.StudentItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class NewStudentFragment extends Fragment {

    private FragmentNewStudentBinding binding;

    private EditText textFirstName;
    private EditText textLastName;
    private CheckBox checkAdult;
    private CheckBox checkChild;

    //Db
    private DaoSession daoSession;
    private StudentItemDao studentItemDao;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNewStudentBinding.inflate(inflater, container, false);
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
        textFirstName = (EditText) view.findViewById(R.id.editTextFirstName);
        textLastName = (EditText) view.findViewById(R.id.editTextLastName);

        checkAdult = (CheckBox) view.findViewById(R.id.checkBoxAdult);
        checkChild = (CheckBox) view.findViewById(R.id.checkBoxChild);
        Button buttonCreateStudent = (Button) view.findViewById(R.id.buttonCreateStudent);

        checkAdult.setOnCheckedChangeListener((buttonView, isChecked) -> checkChild.setChecked(!isChecked));

        checkChild.setOnCheckedChangeListener((compoundButton, b) -> checkAdult.setChecked(!b));

        buttonCreateStudent.setOnClickListener(view1 -> {
            if(ValidateFormCreateStudent(textFirstName.getText().toString())){
                RegisterStudent(textFirstName.getText().toString(), textLastName.getText().toString(), checkAdult.isChecked());
            }
        });

        daoSession = ((MainApplication) requireActivity().getApplication()).getDaoSession();
        studentItemDao = daoSession.getStudentItemDao();
    }

    private boolean ValidateFormCreateStudent(String firstName)
    {
        boolean isValid = true;

        if(TextUtils.isEmpty(firstName)){
            ((MenuActivity) requireActivity()).ShowSnackBar("O primeiro nome é obrigatório!");
            isValid = false;
        }

        return isValid;
    }

    private void RegisterStudent(String firstName, String lastName, boolean isAdult)
    {
        //create new student
        StudentItem newStudent = new StudentItem();
        newStudent.setFirstName(firstName);
        newStudent.setLastName(lastName);
        newStudent.setIsAdult(isAdult);
        newStudent.setCreateDate(Calendar.getInstance().getTime());
        newStudent.setDeleted(false);

        studentItemDao.save(newStudent);

        //UpdateStudentsBackup();

        ClearFormCreateStudent();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("O(a) aluno(a) foi registado(a) com sucesso!")
                .setPositiveButton("Ok", (dialog, id) -> {
                });
                /*.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });*/

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void ClearFormCreateStudent()
    {
        textFirstName.getText().clear();
        textLastName.getText().clear();
        checkAdult.setChecked(true);
        checkChild.setChecked(false);
    }

    private void UpdateStudentsBackup(){
        List<StudentItem> studentsData = studentItemDao.queryBuilder()
                .where(StudentItemDao.Properties.Deleted.eq(false))
                .orderAsc(StudentItemDao.Properties.FirstName, StudentItemDao.Properties.LastName)
                .list();

        ArrayList<String> studentsList = new ArrayList<>();

        for (StudentItem std : studentsData) {
            studentsList.add(std.getFirstName() + "#" + std.getLastName() + "#" + std.getIsAdult());
        }

        UtilsClass.WriteToBackUp(studentsList, getActivity());
    }
}