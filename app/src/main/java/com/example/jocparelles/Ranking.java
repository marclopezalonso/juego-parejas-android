package com.example.jocparelles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class Ranking extends ArrayAdapter<String> {
    public Ranking(Context context, List<String> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        String item = getItem(position);
        textView.setText(item);

        textView.setTextColor(0xFFE1D8F0);

        if (position == 0 && !item.equals("Aún no hay jugadores")) {
            textView.setTextColor(0xFFFFD700);
            textView.setTextSize(16);
        } else if (position == 1) {
            textView.setTextColor(0xFFC0C0C0);
        } else if (position == 2) {
            textView.setTextColor(0xFFCD7F32);
        }

        return convertView;
    }
}