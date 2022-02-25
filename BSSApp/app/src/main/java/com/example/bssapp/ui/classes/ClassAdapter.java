package com.example.bssapp.ui.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bssapp.R;
import com.example.bssapp.ui.professors.ProfessorListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ClassAdapter extends ArrayAdapter<ClassListItem>
{
    private final Context mContext;
    private final int mResource;

    private final List<ClassListItem> itemsModelsl;
    private List<ClassListItem> itemsModelListFiltered;

    public ClassAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ClassListItem> objects) {
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
    public ClassListItem getItem(int position) {
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

        ClassListItem currentItem = getItem(position);

        //Last empty row
        if(currentItem.isDummyRow())
        {
            convertView = layoutInflater.inflate(R.layout.list_class_row_empty, parent, false);
            return convertView;
        }

        convertView = layoutInflater.inflate(mResource, parent, false);

        ImageView imageSport = convertView.findViewById(R.id.imageSport);
        String sportName = currentItem.getSportName();
        if(sportName.trim().contains("Surf")) { imageSport.setImageResource(R.drawable.class_icon_surf);}
        if(sportName.trim().contains("Paddle")) { imageSport.setImageResource(R.drawable.class_icon_sup);}
        if(sportName.trim().contains("Yoga")) { imageSport.setImageResource(R.drawable.class_icon_yoga);}
        if(sportName.trim().contains("Canoagem")) { imageSport.setImageResource(R.drawable.class_icon_kayak);}

        TextView textSport = convertView.findViewById(R.id.textSport);
        textSport.setText(currentItem.getSportName().toUpperCase(Locale.ROOT));

        TextView textSpot = convertView.findViewById(R.id.textSpot);
        textSpot.setText(currentItem.getSpotName());

        TextView textDate = convertView.findViewById(R.id.textDate);
        textDate.setText(currentItem.getClassDate());

        TextView textRegNum = convertView.findViewById(R.id.textRegStudents);
        textRegNum.setText(currentItem.getRegisteredNum());

        return convertView;
    }
}
