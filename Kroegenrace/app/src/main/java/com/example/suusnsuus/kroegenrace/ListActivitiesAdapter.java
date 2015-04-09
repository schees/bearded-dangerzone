package com.example.suusnsuus.kroegenrace;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Suzanne on 9-4-2015.
 */
public class ListActivitiesAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _activityNames;
    private HashMap<String, RaceActivity> _activities;

    public ListActivitiesAdapter(Context context, List<String> activityNames,
                                 HashMap<String, RaceActivity> activities) {
        this._context = context;
        this._activityNames = activityNames;
        this._activities = activities;
    }

    @Override
    public int getGroupCount() {
        return this._activityNames.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._activityNames.get(groupPosition);
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return this._activities.get(this._activityNames.get(groupPosition)).getDescription();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(_context).inflate(R.layout.activity_header, parent, false);
        }

        TextView activityListHeader = (TextView) convertView.findViewById(R.id.activityListHeader);
        activityListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.activity_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.activityListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
