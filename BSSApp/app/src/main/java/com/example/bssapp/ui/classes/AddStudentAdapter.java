package com.example.bssapp.ui.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bssapp.R;
import com.example.bssapp.UtilsClass;

import java.util.ArrayList;
import java.util.List;

public class AddStudentAdapter extends ArrayAdapter<AddStudentListItem> implements Filterable {

    private final Context mContext;
    private final int mResource;
    private final AddStudentFragment mCurrentActivity;

    private final List<AddStudentListItem> itemsModelsl;
    private List<AddStudentListItem> itemsModelListFiltered;

    public AddStudentAdapter(@NonNull Context context, int resource, @NonNull ArrayList<AddStudentListItem> objects, @NonNull AddStudentFragment currentActivity) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.mCurrentActivity = currentActivity;

        this.itemsModelsl = objects;
        this.itemsModelListFiltered = objects;
    }

    @Override
    public int getCount() {
        return itemsModelListFiltered.size();
    }

    @Override
    public AddStudentListItem getItem(int position) {
        return itemsModelListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource, parent, false);

        ImageView imageView = convertView.findViewById(R.id.imageStudent);
        TextView textView = convertView.findViewById(R.id.textStudent);
        CheckBox checkBox = convertView.findViewById(R.id.checkBoxAddStudent);

        imageView.setImageResource(getItem(position).getStudentImage());
        textView.setText(getItem(position).getStudentName());
        checkBox.setChecked(getItem(position).getIsRegistred());

        checkBox.setOnClickListener(view -> {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);

            getItem(position).setIsRegistred(checkBox.isChecked()); //update list data
            if(checkBox.isChecked())
            {
                ((AddStudentFragment) mCurrentActivity).AddClassStudent(getItem(position).getStudentId());
                builder.setMessage("O(a) aluno(a) " + getItem(position).getStudentName() + " foi adicionado(a) à aula!")
                        .setPositiveButton("Ok", (dialog, id) -> {
                        });
                android.app.AlertDialog dialog = builder.create();
                dialog.show();
            }
            else
            {
                ((AddStudentFragment) mCurrentActivity).RemoveClassStudent(getItem(position).getStudentId());
                builder.setMessage("O(a) aluno(a) " + getItem(position).getStudentName() + " foi removido(a) da aula!")
                        .setPositiveButton("Ok", (dialog, id) -> {
                        });
                android.app.AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                UtilsClass utilsClass = new UtilsClass();
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = itemsModelsl.size();
                    filterResults.values = itemsModelsl;

                }else{
                    List<AddStudentListItem> resultsModel = new ArrayList<>();
                    String searchStr = utilsClass.NormalizeString(constraint.toString().toLowerCase());

                    for(AddStudentListItem itemsModel:itemsModelsl){
                        if(utilsClass.NormalizeString(itemsModel.getStudentName().toLowerCase()).contains(searchStr)){
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                itemsModelListFiltered = (List<AddStudentListItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
