package com.example.bssapp.ui.students;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.bssapp.MenuActivity;
import com.example.bssapp.R;
import com.example.bssapp.databinding.FragmentStudentsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class StudentsFragment extends Fragment {

    private FragmentStudentsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StudentsViewModel studentsViewModel =
                new ViewModelProvider(this).get(StudentsViewModel.class);

        binding = FragmentStudentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LoadControllers(root);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void LoadControllers(View root){
        FloatingActionButton fabAddStudent = (FloatingActionButton) root.findViewById(R.id.fabAddStudent);
        fabAddStudent.setOnClickListener(v -> { ((MenuActivity) getActivity()).changeFragment(); });
    }
}