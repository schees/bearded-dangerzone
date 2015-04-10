package com.example.suusnsuus.kroegenrace;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Suzanne on 9-4-2015.
 */
public class ListActivitiesAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _activityNames;
    private HashMap<String, RaceActivity> _activities;
    private String currentActivityId;
    private RaceDetailFragment _view;

    public ListActivitiesAdapter(Context context, List<String> activityNames,
                                 HashMap<String, RaceActivity> activities, RaceDetailFragment fragment) {
        this._context = context;
        this._activityNames = activityNames;
        this._activities = activities;
        this._view = fragment;
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
    public RaceActivity getChild(int groupPosition, int childPosition) {
        String key = this._activityNames.get(groupPosition);
        return this._activities.get(key);
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
        RaceActivity activity = getChild(groupPosition, childPosition);
        currentActivityId = activity.get_id();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.activity_item, null);
        }

        GetActivityInfoTask ait = new GetActivityInfoTask(_view, convertView);
        ait.execute();

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class GetActivityInfoTask extends AsyncTask<String, Integer, RaceActivity> {
        private RaceDetailFragment context;
        private View convertView;

        public GetActivityInfoTask(RaceDetailFragment v, View convertView) {
            this.convertView = convertView;
            context = v;
        }
        @Override
        protected RaceActivity doInBackground(String... params) {
            RaceActivity activityInfo;
            HttpGet httpGet = new HttpGet("http://restrace-api.herokuapp.com/activity/" + currentActivityId);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = null;
            try {
                httpResponse = httpClient.execute(httpGet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                try {
                    String response = EntityUtils.toString(httpResponse.getEntity());
                    try {
                        JSONArray activity = new JSONArray(response);
                        return convertJsonToActivity(activity);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(RaceActivity activity) {
            final RaceActivity fActivity = activity;
            super.onPostExecute(activity);
            TextView txtListChild = (TextView) convertView.findViewById(R.id.activityLocationName);
            txtListChild.setText(activity.getPlaceName());
            txtListChild = (TextView) convertView.findViewById(R.id.activityLocationAddress);
            txtListChild.setText(activity.getPlaceAdress());
            txtListChild = (TextView) convertView.findViewById(R.id.activityLocationPhone);
            txtListChild.setText(activity.getPhoneNumber());
            txtListChild.setOnClickListener(new View.OnClickListener() {
                public void onClick(View clickedView) {
                    Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                    phoneIntent.setData(Uri.parse("tel:" + fActivity.getPhoneNumber()));
                    try {
                        _context.startActivity(phoneIntent);
                        Activity activity = (Activity) _context;
                        activity.finish();
                        Log.i("Finished making a call", "");
                    } catch (android.content.ActivityNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }

        private RaceActivity convertJsonToActivity(JSONArray json) throws JSONException {
            if (json != null) {
                JSONObject basicInfo = (JSONObject)json.get(0);
                JSONObject rawGoogleInfo = (JSONObject)json.get(1);
                RaceActivity activity = new RaceActivity(
                        (String) basicInfo.get("_id"),
                        (String) basicInfo.get("google_id"),
                        (String) basicInfo.get("description"),
                        (JSONArray) basicInfo.get("tags")
                );
                JSONObject googleInfo = (JSONObject) rawGoogleInfo.get("result");
                activity.setPhoneNumber((String) googleInfo.get("international_phone_number"));
                activity.setPlaceName((String) googleInfo.get("name"));
                activity.setPlaceAdress((String) googleInfo.get("formatted_address"));
                return activity;
            }
            return null;
        }
    }

}
