package com.example.bssapp.ui.classes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.example.bssapp.DaoSession;
import com.example.bssapp.R;
import com.example.bssapp.databinding.FragmentAddClassBinding;
import com.example.bssapp.databinding.FragmentEditProfessorBinding;
import com.example.bssapp.db.models.ProfessorItem;
import com.example.bssapp.ui.professors.ProfessorListItem;

import java.util.ArrayList;

public class AddClassFragment extends Fragment {

    private FragmentAddClassBinding binding;

    AutoCompleteTextView autoCompleteTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAddClassBinding.inflate(inflater, container, false);
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
        autoCompleteTextView = view.findViewById(R.id.autoCompleteSport);

        ArrayList<DropdownViewModel> sportsList = new ArrayList<>();
        sportsList.add(new DropdownViewModel(1, "Surf"));
        sportsList.add(new DropdownViewModel(3, "Yoga"));
        sportsList.add(new DropdownViewModel(2, "Paddle"));
        sportsList.add(new DropdownViewModel(4, "Canoagem"));

        ArrayAdapter<DropdownViewModel> arrayAdapter = new ArrayAdapter<>(requireContext(),  R.layout.options_sports_item, sportsList);
        autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(), false);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setOnItemClickListener((parent, view1, position, id) -> {
            DropdownViewModel m=(DropdownViewModel) parent.getItemAtPosition(position);
            String name = m.getText();
            int _id = m.getId();
        });
    }
}