package com.example.bssapp.ui.students;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bssapp.DaoSession;
import com.example.bssapp.MainApplication;
import com.example.bssapp.MenuActivity;
import com.example.bssapp.R;
import com.example.bssapp.StudentItemDao;
import com.example.bssapp.UtilsClass;
import com.example.bssapp.databinding.FragmentEditStudentBinding;
import com.example.bssapp.db.models.StudentItem;

import java.util.ArrayList;
import java.util.List;

public class EditStudentFragment extends Fragment {

    private FragmentEditStudentBinding binding;

    private StudentListItem student;

    private EditText textFirstName;
    private EditText textLastName;
    private CheckBox checkAdult;
    private CheckBox checkChild;

    //Db
    private DaoSession daoSession;
    private StudentItem studentItem;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentEditStudentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (getArguments() != null) {
            student = (StudentListItem) getArguments().getSerializable("SelectedStudent");
        }

        LoadControllers(root);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void LoadControllers(View view)
    {
        Button buttonEditStudent = view.findViewById(R.id.buttonEditStudent);
        Button buttonRemoveStudent = view.findViewById(R.id.buttonDeleteStudent);

        if(student != null)
        {
            textFirstName = view.findViewById(R.id.editTextFirstName);
            textLastName = view.findViewById(R.id.editTextLastName);
            checkAdult = view.findViewById(R.id.checkBoxAdult);
            checkChild = view.findViewById(R.id.checkBoxChild);

            checkAdult.setOnCheckedChangeListener((buttonView, isChecked) -> checkChild.setChecked(!isChecked));

            checkChild.setOnCheckedChangeListener((compoundButton, b) -> checkAdult.setChecked(!b));

            buttonEditStudent.setOnClickListener(view1 -> {
                if(ValidateFormEditStudent(textFirstName.getText().toString())){
                    EditStudent(textFirstName.getText().toString(), textLastName.getText().toString(), checkAdult.isChecked());
                }
            });

            daoSession = ((MainApplication) requireActivity().getApplication()).getDaoSession();
            studentItem = daoSession.getStudentItemDao().load(student.getStudentId());

            if(studentItem != null){
                textFirstName.setText(studentItem.getFirstName().trim());
                textLastName.setText(studentItem.getLastName().trim());

                if(studentItem.getIsAdult())
                    checkAdult.setChecked(true);
                else
                    checkChild.setChecked(true);
            }

            buttonRemoveStudent.setOnClickListener(view1 -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(getResources().getString(R.string.remove_student_validation))
                        .setPositiveButton("Sim", (dialog, id) -> DeleteStudent())
                        .setNegativeButton("Não", (dialog, id) -> {});

                AlertDialog dialog = builder.create();
                dialog.show();
            });
        }else{
            buttonEditStudent.setVisibility(View.INVISIBLE);
            ((MenuActivity) requireActivity()).ShowSnackBar("Ocorreu um erro! Contactar administrador");
        }

    }

    private boolean ValidateFormEditStudent(String firstName)
    {
        boolean isValid = true;

        if(TextUtils.isEmpty(firstName)){
            ((MenuActivity) requireActivity()).ShowSnackBar("O primeiro nome é obrigatório!");
            isValid = false;
        }

        return isValid;
    }

    private void EditStudent(String firstName, String lastName, boolean isAdult)
    {
        //edit student
        studentItem.setFirstName(firstName.trim());
        studentItem.setLastName(lastName.trim());
        studentItem.setIsAdult(isAdult);

        daoSession.getStudentItemDao().update(studentItem);

        UpdateStudentsBackup();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("O(a) aluno(a) foi editado(a) com sucesso!")
                .setPositiveButton("Ok", (dialog, id) -> {
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        ((MenuActivity) requireActivity()).changeToStudentsFromEditFragment();
    }

    private void DeleteStudent()
    {
        //edit student
        studentItem.setDeleted(true);
        daoSession.getStudentItemDao().update(studentItem);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("O(a) aluno(a) foi removido(a) com sucesso!")
                .setPositiveButton("Ok", (dialog, id) -> {
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        ((MenuActivity) requireActivity()).changeToStudentsFromEditFragment();
    }

    private void UpdateStudentsBackup(){
        List<StudentItem> studentsData = daoSession.getStudentItemDao().queryBuilder()
                .where(StudentItemDao.Properties.Deleted.eq(false))
                .orderAsc(StudentItemDao.Properties.FirstName, StudentItemDao.Properties.LastName)
                .list();

        ArrayList<String> studentsList = new ArrayList<>();

        for (StudentItem std : studentsData) {
            studentsList.add(std.getFirstName() + "#" + std.getLastName() + "#" + std.getIsAdult());
        }

        UtilsClass.WriteToBackUp(studentsList);
    }
}