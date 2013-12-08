package org.mobyman.scrumPoker;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MarkAdapter extends ArrayAdapter<ScrumMark> {

    private final Context context;
    private final ArrayList<ScrumMark> itemsArrayList;

    public MarkAdapter(Context context, ArrayList<ScrumMark> itemsArrayList) {

        super(context, R.layout.row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row, parent, false);
        TextView valueView = (TextView) rowView.findViewById(R.id.value);
        valueView.setText(String.valueOf(itemsArrayList.get(position).getValue()));
        return rowView;
    }
}