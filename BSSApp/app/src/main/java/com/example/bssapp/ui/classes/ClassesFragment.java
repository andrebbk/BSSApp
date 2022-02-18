package com.example.bssapp.ui.classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bssapp.MenuActivity;
import com.example.bssapp.R;
import com.example.bssapp.databinding.FragmentClassesBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ClassesFragment extends Fragment {

    private FragmentClassesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentClassesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LoadControllers(root);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void LoadControllers(View root) {
        FloatingActionButton fabAddClass = root.findViewById(R.id.fabAddClass);
        fabAddClass.setOnClickListener(v -> ((MenuActivity) requireActivity()).changeToAddClass());
    }

}