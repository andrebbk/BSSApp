package com.example.bssapp.ui.datamanagement;

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

import java.util.ArrayList;
import java.util.List;

public class DataManagementAdapter extends ArrayAdapter<DataManagementItem> {

    private final Context mContext;
    private final int mResource;

    private final List<DataManagementItem> itemsModelsl;
    private final List<DataManagementItem> itemsModelListFiltered;

    public DataManagementAdapter(@NonNull Context context, int resource, @NonNull ArrayList<DataManagementItem> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;

        this.itemsModelsl = objects;
        this.itemsModelListFiltered = objects;
    }

    @Override
    public int getCount() {
        if(itemsModelListFiltered != null) {
            return itemsModelListFiltered.size();
        }

        return 0;
    }

    @Override
    public DataManagementItem getItem(int position) {
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

        TextView textView = convertView.findViewById(R.id.textItem);
        ImageView imageViewClear = convertView.findViewById(R.id.imageClearItem);

        DataManagementItem item = getItem(position);
        if(item != null){
            textView.setText(item.getOptionName());

            if(item.getIsRemovable())
            {
                imageViewClear.setOnClickListener(view -> {
                    DataManagementItem selectedToDelete = getItem(position);
                    if(selectedToDelete != null){
                        if(selectedToDelete.getIsSport()) DataManagementFragment.DeleteSport(mContext, selectedToDelete, this, position);
                        else DataManagementFragment.DeleteSpot(mContext, selectedToDelete, this, position);
                    }
                });
            }
            else
            {
                imageViewClear.setVisibility(View.INVISIBLE);
            }

        }

        return convertView;
    }

    public void RemoveItem(int position){
        itemsModelsl.remove(position);
        this.notifyDataSetChanged();
    }

}
