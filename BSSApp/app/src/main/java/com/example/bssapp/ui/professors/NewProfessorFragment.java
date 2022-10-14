package com.example.bssapp.ui.professors;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.bssapp.DaoSession;
import com.example.bssapp.MainApplication;
import com.example.bssapp.MenuActivity;
import com.example.bssapp.ProfessorItemDao;
import com.example.bssapp.R;
import com.example.bssapp.databinding.FragmentNewProfessorBinding;
import com.example.bssapp.db.models.ProfessorItem;

import java.util.Calendar;

public class NewProfessorFragment extends Fragment {

    private FragmentNewProfessorBinding binding;

    private EditText textFirstName;
    private EditText textLastName;

    private ProfessorItemDao professorItemDao;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNewProfessorBinding.inflate(inflater, container, false);
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

        Button buttonCreateStudent = (Button) view.findViewById(R.id.buttonCreateProfessor);

        buttonCreateStudent.setOnClickListener(view1 -> {
            if(ValidateFormCreateProfessor(textFirstName.getText().toString())){
                RegisterProfessor(textFirstName.getText().toString(), textLastName.getText().toString());
            }
        });

        //Db
        DaoSession daoSession = ((MainApplication) requireActivity().getApplication()).getDaoSession();
        professorItemDao = daoSession.getProfessorItemDao();
    }

    private boolean ValidateFormCreateProfessor(String firstName)
    {
        boolean isValid = true;

        if(TextUtils.isEmpty(firstName)){
            ((MenuActivity) requireActivity()).ShowSnackBar("O primeiro nome é obrigatório!");
            isValid = false;
        }

        return isValid;
    }

    private void RegisterProfessor(String firstName, String lastName)
    {
        //create new student
        ProfessorItem newProfessor = new ProfessorItem();
        newProfessor.setFirstName(firstName);
        newProfessor.setLastName(lastName);
        newProfessor.setCreateDate(Calendar.getInstance().getTime());
        newProfessor.setDeleted(false);

        professorItemDao.save(newProfessor);

        ClearFormCreateProfessor();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("O(a) instrutor(a) foi registado(a) com sucesso!")
                .setPositiveButton("Ok", (dialog, id) -> {
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(textLastName.getWindowToken(), 0);
                    ((MenuActivity) requireActivity()).changeToProfessorsFragment();
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void ClearFormCreateProfessor()
    {
        textFirstName.getText().clear();
        textLastName.getText().clear();
    }
}