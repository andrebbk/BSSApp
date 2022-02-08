package com.example.bssapp.ui.students;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.bssapp.MenuActivity;
import com.example.bssapp.R;
import com.example.bssapp.databinding.FragmentNewStudentBinding;

public class NewStudentFragment extends Fragment {

    private FragmentNewStudentBinding binding;

    private EditText textFirstName;
    private EditText textLastName;
    private CheckBox checkAdult;
    private CheckBox checkChild;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //StudentsViewModel studentsViewModel = new ViewModelProvider(this).get(StudentsViewModel.class);

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

        checkAdult.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                checkChild.setChecked(!isChecked);
            }
        });

        checkChild.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                checkAdult.setChecked(!b);
            }
        });

        buttonCreateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ValidateFormCreateStudent(view, textFirstName.getText().toString(), textLastName.getText().toString(), checkAdult.isChecked())){
                    RegisterStudent(textFirstName.getText().toString(), textLastName.getText().toString(), checkAdult.isChecked());
                }
            }
        });

    }

    private boolean ValidateFormCreateStudent(View view, String firstName, String lastName, boolean isAdult)
    {
        boolean isValid = true;

        if(TextUtils.isEmpty(firstName)){
            ((MenuActivity) getActivity()).ShowSnackBar("O primeiro nome é obrigatório!");
            isValid = false;
        }

        return isValid;
    }

    private void RegisterStudent(String firstName, String lastName, boolean isAdult)
    {
        ClearFormCreateStudent();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("O(a) aluno(a) foi registado(a) com sucesso!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
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
}