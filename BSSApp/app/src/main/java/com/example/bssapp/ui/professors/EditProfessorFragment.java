package com.example.bssapp.ui.professors;

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

import com.example.bssapp.ClassItemDao;
import com.example.bssapp.ClassProfessorItemDao;
import com.example.bssapp.DaoSession;
import com.example.bssapp.MainApplication;
import com.example.bssapp.MenuActivity;
import com.example.bssapp.ProfessorItemDao;
import com.example.bssapp.R;
import com.example.bssapp.databinding.FragmentEditProfessorBinding;
import com.example.bssapp.databinding.FragmentEditStudentBinding;
import com.example.bssapp.db.models.ClassItem;
import com.example.bssapp.db.models.ClassProfessorItem;
import com.example.bssapp.db.models.ProfessorItem;
import com.example.bssapp.db.models.StudentItem;
import com.example.bssapp.ui.students.StudentListItem;

import java.util.List;

public class EditProfessorFragment extends Fragment {

    private FragmentEditProfessorBinding binding;

    private ProfessorListItem professor;

    private EditText textFirstName;
    private EditText textLastName;

    //Db
    private DaoSession daoSession;
    private ProfessorItem professorItem;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentEditProfessorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (getArguments() != null) {
            professor = (ProfessorListItem) getArguments().getSerializable("SelectedProfessor");
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
        Button buttonEditProfessor = view.findViewById(R.id.buttonEditProfessor);
        Button buttonRemoveProfessor = view.findViewById(R.id.buttonDeleteProfessor);

        if(professor != null)
        {
            textFirstName = view.findViewById(R.id.editTextFirstName);
            textLastName = view.findViewById(R.id.editTextLastName);

            buttonEditProfessor.setOnClickListener(view1 -> {
                if(ValidateFormEditProfessor(textFirstName.getText().toString())){
                    EditProfessor(textFirstName.getText().toString(), textLastName.getText().toString());
                }
            });

            daoSession = ((MainApplication) requireActivity().getApplication()).getDaoSession();
            professorItem = daoSession.getProfessorItemDao().load(professor.getProfessorId());

            if(professorItem != null){
                textFirstName.setText(professorItem.getFirstName().trim());
                textLastName.setText(professorItem.getLastName().trim());
            }

            buttonRemoveProfessor.setOnClickListener(view1 -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(getResources().getString(R.string.remove_professor_validation))
                        .setPositiveButton("Sim", (dialog, id) -> DeleteProfessor())
                        .setNegativeButton("Não", (dialog, id) -> {});

                AlertDialog dialog = builder.create();
                dialog.show();
            });

            //SHOW DELETE BUTTON
            boolean hideButtonDelete = daoSession.getClassProfessorItemDao().queryBuilder()
                    .where(ClassProfessorItemDao.Properties.ProfessorId.eq(professorItem.getProfessorId()))
                    .count() > 0;

            if(hideButtonDelete){
                buttonRemoveProfessor.setVisibility(View.INVISIBLE);
            }

        }else{
            buttonEditProfessor.setVisibility(View.INVISIBLE);
            ((MenuActivity) requireActivity()).ShowSnackBar("Ocorreu um erro! Contactar administrador");
        }

    }

    private boolean ValidateFormEditProfessor(String firstName)
    {
        boolean isValid = true;

        if(TextUtils.isEmpty(firstName)){
            ((MenuActivity) requireActivity()).ShowSnackBar("O primeiro nome é obrigatório!");
            isValid = false;
        }

        return isValid;
    }

    private void EditProfessor(String firstName, String lastName)
    {
        //edit student
        professorItem.setFirstName(firstName.trim());
        professorItem.setLastName(lastName.trim());

        daoSession.getProfessorItemDao().update(professorItem);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("O(a) treinador(a) foi editado(a) com sucesso!")
                .setPositiveButton("Ok", (dialog, id) -> {
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        ((MenuActivity) requireActivity()).changeToProfessorsFromEditFragment();
    }

    private void DeleteProfessor()
    {
        //edit student
        professorItem.setDeleted(true);
        daoSession.getProfessorItemDao().update(professorItem);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("O(a) treinador(a) foi removido(a) com sucesso!")
                .setPositiveButton("Ok", (dialog, id) -> {
                });

        AlertDialog dialog = builder.create();
        dialog.show();

        ((MenuActivity) requireActivity()).changeToProfessorsFromEditFragment();
    }
}