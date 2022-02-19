package com.example.bssapp.ui.datamanagement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bssapp.R;
import com.example.bssapp.databinding.FragmentCalendarBinding;
import com.example.bssapp.databinding.FragmentDataManagementBinding;
import com.example.bssapp.db.models.ProfessorItem;
import com.example.bssapp.ui.professors.ProfessorAdapter;
import com.example.bssapp.ui.professors.ProfessorListItem;
import com.example.bssapp.ui.students.StudentAdapter;

import java.util.ArrayList;

public class DataManagementFragment extends Fragment {

    private FragmentDataManagementBinding binding;

    private ListView listViewSports;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDataManagementBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LoadSports(root);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void LoadSports(View view)
    {
        listViewSports = view.findViewById(R.id.listViewSports);

        ArrayList<DataManagementItem> sportsList = new ArrayList<>();
        sportsList.add(new DataManagementItem(1L, "Surf"));
        sportsList.add(new DataManagementItem(2L, "Canoagem"));
        sportsList.add(new DataManagementItem(3L, "Yoga"));
        sportsList.add(new DataManagementItem(4L, "Paddle"));

        //Costume adapter
        DataManagementAdapter adapter = new DataManagementAdapter(this.requireActivity(), R.layout.list_data_option_row, sportsList);
        listViewSports.setAdapter(adapter);
    }
}