package com.example.bssapp.ui.students;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bssapp.R;
import com.example.bssapp.UtilsClass;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends ArrayAdapter<StudentListItem> implements Filterable {

    private final Context mContext;
    private final int mResource;

    private final List<StudentListItem> itemsModelsl;
    private List<StudentListItem> itemsModelListFiltered;
    private boolean ShowClassesCount;

    public StudentAdapter(@NonNull Context context, int resource, @NonNull ArrayList<StudentListItem> objects, boolean showClassesCount) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;

        this.itemsModelsl = objects;
        this.itemsModelListFiltered = objects;
        this.ShowClassesCount = showClassesCount;
    }

    @Override
    public int getCount() {
        if(itemsModelListFiltered != null) {
            return itemsModelListFiltered.size();
        }

        return 0;
    }

    @Override
    public StudentListItem getItem(int position) {
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

        StudentListItem studentItem = getItem(position);
        if(studentItem != null){
            imageView.setImageResource(studentItem.getStudentImage());
            textView.setText(studentItem.getStudentName());

            if(ShowClassesCount){
                LinearLayout llCC = convertView.findViewById(R.id.layoutCountClasses);
                TextView textViewClasses = convertView.findViewById(R.id.textClasses);

                textViewClasses.setText(String.valueOf(studentItem.getStudentClassesCount()));
                llCC.setVisibility(View.VISIBLE);
            }
        }

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
                    List<StudentListItem> resultsModel = new ArrayList<>();
                    String searchStr = utilsClass.NormalizeString(constraint.toString().toLowerCase());

                    for(StudentListItem itemsModel:itemsModelsl){
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
                itemsModelListFiltered = (List<StudentListItem>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
