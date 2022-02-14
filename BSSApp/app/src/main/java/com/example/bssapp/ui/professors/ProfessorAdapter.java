package com.example.bssapp.ui.professors;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bssapp.R;
import com.example.bssapp.UtilsClass;
import com.example.bssapp.ui.students.StudentListItem;

import java.util.ArrayList;
import java.util.List;

public class ProfessorAdapter extends ArrayAdapter<ProfessorListItem> implements Filterable {

    private final Context mContext;
    private final int mResource;

    private final List<ProfessorListItem> itemsModelsl;
    private List<ProfessorListItem> itemsModelListFiltered;

    public ProfessorAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ProfessorListItem> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;

        this.itemsModelsl = objects;
        this.itemsModelListFiltered = objects;
    }

    @Override
    public int getCount() {
        return itemsModelListFiltered.size();
    }

    @Override
    public ProfessorListItem getItem(int position) {
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

        imageView.setImageResource(getItem(position).getProfessorImage());
        textView.setText(getItem(position).getProfessorName());

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
                    List<ProfessorListItem> resultsModel = new ArrayList<>();
                    String searchStr = utilsClass.NormalizeString(constraint.toString().toLowerCase());

                    for(ProfessorListItem itemsModel:itemsModelsl){
                        if(utilsClass.NormalizeString(itemsModel.getProfessorName().toLowerCase()).contains(searchStr)){
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
                itemsModelListFiltered = (List<ProfessorListItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
