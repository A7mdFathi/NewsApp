package com.example.android.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Created by ahmed on 6/21/18.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listview = convertView;

        if (listview == null) {
            listview = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        News currentNew = getItem(position);


        TextView newstitle = (TextView) listview.findViewById(R.id.title);
        newstitle.setText(currentNew.getnTitle());

        TextView category = (TextView) listview.findViewById(R.id.section_name);
        category.setText(currentNew.getnCategory());

        TextView date = (TextView) listview.findViewById(R.id.date);
        date.setText("Date: " + currentNew.getnPublishDate());

        TextView auther = (TextView)listview.findViewById(R.id.auther);
        auther.setText("written by : "+currentNew.getnAuthor());

        return listview;
    }


}
