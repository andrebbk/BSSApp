package com.example.bssapp.ui.classes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bssapp.MenuActivity;
import com.example.bssapp.R;
import com.example.bssapp.databinding.FragmentClassBinding;

import java.util.Locale;
import java.util.Objects;


public class ClassFragment extends Fragment {

    private FragmentClassBinding binding;

    private ClassListItem currentClass;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (getArguments() != null) {
            currentClass = (ClassListItem) getArguments().getSerializable("SelectedClass");
        }

        binding = FragmentClassBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LoadControllers(root);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void LoadControllers(View view)
    {
        ImageView imageSport = view.findViewById(R.id.imageSport);
        String sportName = currentClass.getSportName();

        Objects.requireNonNull(((MenuActivity) requireActivity()).getSupportActionBar()).setTitle(sportName);

        if(sportName.trim().contains("Surf")) { imageSport.setImageResource(R.drawable.class_icon_surf);}
        if(sportName.trim().contains("Paddle")) { imageSport.setImageResource(R.drawable.class_icon_sup);}
        if(sportName.trim().contains("Yoga")) { imageSport.setImageResource(R.drawable.class_icon_yoga);}
        if(sportName.trim().contains("Canoagem")) { imageSport.setImageResource(R.drawable.class_icon_kayak);}

        TextView textSport = view.findViewById(R.id.textSport);
        textSport.setText(currentClass.getSportName().toUpperCase(Locale.ROOT));

        TextView textSpot = view.findViewById(R.id.textSpot);
        textSpot.setText(currentClass.getSpotName());

        TextView textDate = view.findViewById(R.id.textDate);
        textDate.setText(currentClass.getClassDate());

        TextView textRegNum = view.findViewById(R.id.textRegStudents);
        textRegNum.setText(currentClass.getRegisteredNum());
    }
}