package com.example.bssapp.ui.datamanagement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bssapp.DaoSession;
import com.example.bssapp.MainApplication;
import com.example.bssapp.MenuActivity;
import com.example.bssapp.R;
import com.example.bssapp.SportItemDao;
import com.example.bssapp.databinding.FragmentDataManagementBinding;
import com.example.bssapp.db.models.SportItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DataManagementFragment extends Fragment {

    private FragmentDataManagementBinding binding;

    private SportItemDao sportItemDao;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDataManagementBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LoadControllers(root);

        LoadSports(root);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @SuppressLint("SetTextI18n")
    private void LoadControllers(View view)
    {
        //Db
        DaoSession daoSession = ((MainApplication) requireActivity().getApplication()).getDaoSession();
        sportItemDao = daoSession.getSportItemDao();

        //SPORTS
        ImageView addSports = view.findViewById(R.id.imageViewAddSport);
        addSports.setOnClickListener(view1 -> {
            View sportsDialog = LayoutInflater.from(requireActivity()).inflate(R.layout.costum_insert_dialog,null);

            //initialize alert builder.
            AlertDialog.Builder alert = new AlertDialog.Builder(requireActivity());

            //set our custom alert dialog to tha alertdialog builder
            alert.setView(sportsDialog);

            TextView titleText = sportsDialog.findViewById(R.id.textViewTitle);
            if(titleText != null) titleText.setText("Nova modalidade");

            Button saveButton = sportsDialog.findViewById(R.id.buttonSave);
            Button cancelButton = sportsDialog.findViewById(R.id.buttonCancel);
            EditText newSport = sportsDialog.findViewById(R.id.editTextData);

            final AlertDialog dialog = alert.create();

            //this line removed app bar from dialog and make it transparent and you see the image is like floating outside dialog box.
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialog.show();

            cancelButton.setOnClickListener(view2 -> dialog.dismiss());

            saveButton.setOnClickListener(view22 -> {
                if(TextUtils.isEmpty(newSport.getText())){
                    dialog.dismiss();
                    ((MenuActivity) requireActivity()).ShowSnackBar("A designação da modalidade é obrigatória!");
                    return;
                }

                SaveNewSport(view, dialog, newSport.getText().toString().trim());
            });

        });
    }

    private void LoadSports(View view)
    {
        ListView listViewSports = view.findViewById(R.id.listViewSports);
        ArrayList<DataManagementItem> sportsList = new ArrayList<>();

        List<SportItem> sportsData = sportItemDao.queryBuilder()
                .where(SportItemDao.Properties.Deleted.eq(false))
                .orderAsc(SportItemDao.Properties.SportName)
                .list();

        for (SportItem object : sportsData) {
            sportsList.add(new DataManagementItem(object.getSportId(), object.getSportName()));
        }

        //Costume adapter
        DataManagementAdapter adapter = new DataManagementAdapter(this.requireActivity(), R.layout.list_data_option_row, sportsList);
        listViewSports.setAdapter(adapter);

        listViewSports.setClickable(true);
        listViewSports.setOnItemClickListener((arg0, arg1, position, arg3) -> {
            DataManagementItem selectedSport = (DataManagementItem) adapter.getItem(position);

            if(selectedSport != null){
                View sportsDialog = LayoutInflater.from(requireActivity()).inflate(R.layout.costum_insert_dialog,null);

                //initialize alert builder.
                AlertDialog.Builder alert = new AlertDialog.Builder(requireActivity());

                //set our custom alert dialog to tha alertdialog builder
                alert.setView(sportsDialog);

                TextView titleText = sportsDialog.findViewById(R.id.textViewTitle);
                if(titleText != null) titleText.setText(getResources().getString(R.string.edit_sport_label));

                EditText editSport = sportsDialog.findViewById(R.id.editTextData);
                editSport.setText(selectedSport.getOptionName());

                Button saveButton = sportsDialog.findViewById(R.id.buttonSave);
                Button cancelButton = sportsDialog.findViewById(R.id.buttonCancel);

                final AlertDialog dialog = alert.create();

                //this line removed app bar from dialog and make it transparent and you see the image is like floating outside dialog box.
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();

                cancelButton.setOnClickListener(view2 -> dialog.dismiss());

                saveButton.setOnClickListener(view22 -> {
                    if(TextUtils.isEmpty(editSport.getText())){
                        dialog.dismiss();
                        ((MenuActivity) requireActivity()).ShowSnackBar("A designação da modalidade é obrigatória!");
                        return;
                    }

                    EditSport(view, dialog, selectedSport.getItemId(), editSport.getText().toString().trim());
                });

            }
        });
    }

    private void SaveNewSport(View view, AlertDialog dialog, String newSport)
    {
        //create new sport
        SportItem newS = new SportItem();
        newS.setSportName(newSport);
        newS.setCreateDate(Calendar.getInstance().getTime());
        newS.setDeleted(false);

        sportItemDao.save(newS);

        LoadSports(view);

        dialog.dismiss();
        ((MenuActivity) requireActivity()).ShowSnackBar("Nova modalidade criada com sucesso!");
    }

    private void EditSport(View view, AlertDialog dialog, Long sportId, String editSport)
    {
        SportItem sportItem = sportItemDao.load(sportId);
        if(sportItem != null)
        {
            sportItem.setSportName(editSport);
            sportItemDao.update(sportItem);

            LoadSports(view);

            dialog.dismiss();
            ((MenuActivity) requireActivity()).ShowSnackBar("A modalidade "  + sportItem.getSportName() + " foi editada com sucesso!");
        }
    }

    public static void DeleteSport(@NonNull Context context, DataManagementItem toDelete, DataManagementAdapter adapter, int position)
    {
        if(toDelete == null) return;

        try{
            //Db
            DaoSession daoSession = ((MainApplication) context.getApplicationContext()).getDaoSession();

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
            builder.setMessage("Tem a certeza que pretende remover a modalidade " + toDelete.getOptionName() + "?")
                    .setPositiveButton("Sim", (dialog, id) -> {
                        SportItem sportItem = daoSession.getSportItemDao().load(toDelete.getItemId());
                        if(sportItem != null)
                        {
                            sportItem.setDeleted(true);
                            daoSession.getSportItemDao().update(sportItem);

                            adapter.RemoveItem(position);
                            ((MenuActivity) context).ShowSnackBar("A modalidade "  + toDelete.getOptionName() + " foi removida com sucesso!");
                        }
                    })
                    .setNegativeButton("Não", (dialog, id) -> {});

            android.app.AlertDialog dialog = builder.create();
            dialog.show();
        }
        catch (Exception ex){
            Log.i("Error", ex.toString());
        }
    }
}