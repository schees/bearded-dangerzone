package com.example.suusnsuus.kroegenrace;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Suzanne on 8-4-2015.
 */
public class ListRacesAdapter extends BaseAdapter {
    Context context;
    protected List<Race> listRaces;
    LayoutInflater inflater;

    public ListRacesAdapter(Context context, List<Race> listRaces) {
        this.listRaces = listRaces;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return listRaces.size();
    }

    @Override
    public Race getItem(int position) {
        return listRaces.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder holder;
        if (convertView == null) {
            holder = new Viewholder();
            convertView = this.inflater.inflate(R.layout.race_menu_item, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.raceTitle);
            holder.description = (TextView) convertView.findViewById(R.id.raceDescription);
            convertView.setTag(holder);
        } else {
            holder = (Viewholder) convertView.getTag();
        }
        Race race = listRaces.get(position);
        holder.title.setText(race.getName());
        holder.description.setText(race.getDescription());
        return convertView;
    }

    private class Viewholder {
        TextView title;
        TextView description;
    }
}
