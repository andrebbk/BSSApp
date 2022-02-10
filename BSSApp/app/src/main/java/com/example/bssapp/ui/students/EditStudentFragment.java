package com.example.bssapp.ui.students;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.bssapp.DaoSession;
import com.example.bssapp.R;
import com.example.bssapp.StudentItemDao;
import com.example.bssapp.databinding.FragmentEditStudentBinding;
import com.example.bssapp.databinding.FragmentNewStudentBinding;

public class EditStudentFragment extends Fragment {

    private FragmentEditStudentBinding binding;

    private EditText textFirstName;
    private EditText textLastName;
    private CheckBox checkAdult;
    private CheckBox checkChild;

    //Db
    private DaoSession daoSession;
    private StudentItemDao studentItemDao;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentEditStudentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        StudentListItem student = (StudentListItem) getArguments().getSerializable("SelectedStudent");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}