package com.example.bssapp.ui.students;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bssapp.R;
import com.example.bssapp.databinding.FragmentStudentsBinding;

public class StudentsFragment extends Fragment {

    private FragmentStudentsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StudentsViewModel studentsViewModel =
                new ViewModelProvider(this).get(StudentsViewModel.class);

        binding = FragmentStudentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LoadControllers(root);

        //final TextView textView = binding.textGallery;
        //studentsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void LoadControllers(View root){
        //Button buttonTest = root.findViewById(R.id.buttonTest);

        //buttonTest.setOnClickListener(view -> Toast.makeText(getContext(), "Teste ok", Toast.LENGTH_LONG).show());
    }
}