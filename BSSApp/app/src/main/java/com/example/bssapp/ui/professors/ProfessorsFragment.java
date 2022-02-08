package com.example.bssapp.ui.professors;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bssapp.databinding.FragmentProfessorsBinding;

public class ProfessorsFragment extends Fragment {

    private FragmentProfessorsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfessorsViewModel studentsViewModel =
                new ViewModelProvider(this).get(ProfessorsViewModel.class);

        binding = FragmentProfessorsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textProfessors;
        studentsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}