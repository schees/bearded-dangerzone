package com.example.suusnsuus.kroegenrace;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Suzanne on 3-4-2015.
 */
public class RaceDetailFragment extends Fragment {
    private static final String SELECTED = "selected value";
    private static final String TEST_INT = "test int";

    private TextView                raceNameView;
    private TextView                raceDescriptionView;
    private TextView                loading;
    private String                  raceName = "";
    private String                  raceDescription = "";
    private String                  raceId = "";
    private Context                 context;

    private ExpandableListView      getActivityListView;
    private HashMap<String, RaceActivity> activitiesArray = new HashMap<String, RaceActivity>();
    private ListActivitiesAdapter   activitiesListAdapter;
    private View                    view;
    List<String> activitynames = new ArrayList<String>();

    public RaceDetailFragment() {
    }

    public void updateListView(List<RaceActivity> activities) {
        activitiesListAdapter = new ListActivitiesAdapter(context, activitynames, activitiesArray, this);
        activitiesListAdapter.notifyDataSetChanged();
        loading.setText("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_race_detail, container, false);
        raceNameView = (TextView) view.findViewById(R.id.raceDetailName);
        raceDescriptionView = (TextView) view.findViewById(R.id.raceDetailDescription);
        loading = (TextView) view.findViewById(R.id.gettingActivities);
        getActivityListView = (ExpandableListView) view.findViewById(R.id.ActivityListView);
        context = view.getContext();
        getActivityListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;
            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousGroup)
                    getActivityListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if ( bundle != null ) {
            System.out.println("Created Activity");
            raceName = bundle.getString("text");
            raceDescription = bundle.getString("description");
            raceId = bundle.getString("raceId");
            System.out.println("OnCreated raceId: " + raceId);
            showText(raceName, raceDescription);
            GetAllActivitiesTask getActivities = new GetAllActivitiesTask(this);
            getActivities.execute();
        }
    }

    public void showText(String text, String description)
    {
        raceNameView.setText(text);
        raceDescriptionView.setText(description);
    }

    public class GetAllActivitiesTask extends AsyncTask<String, Integer, List<RaceActivity>> {
        private List<RaceActivity> allRaces;
        private RaceDetailFragment context;

        public GetAllActivitiesTask(RaceDetailFragment v) {
            context = v;
        }
        @Override
        protected List<RaceActivity> doInBackground(String... params) {
            System.out.println("In task");
            List<RaceActivity> finalActivities = new ArrayList<RaceActivity>();
            String url = "http://restrace-api.herokuapp.com/race/" + raceId + "/activities";
            System.out.println(url);
            HttpGet httpGet = new HttpGet(url);
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
                        JSONArray convertedResponse = new JSONArray(response);
                        int length = convertedResponse.length();
                        List<RaceActivity> activities = new ArrayList<RaceActivity>(length);
                        for (int i = 0; i < length; i++)
                        {
                            RaceActivity foundActivity = convertJsonToActivity(convertedResponse.getJSONObject(i));
                            activities.add(foundActivity);
                        }
                        finalActivities = activities;
                        return activities;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return finalActivities;
        }

        @Override
        protected void onPostExecute(List<RaceActivity> activities) {
            super.onPostExecute(activities);
            context.updateListView(activities);
            for(int i = 0; i < activities.size(); i++) {
                String description = activities.get(i).getDescription();
                activitynames.add(description);
                activitiesArray.put(description, activities.get(i));
            }

            ListActivitiesAdapter adapter = new ListActivitiesAdapter(getActivityListView.getContext(), activitynames, activitiesArray, getOuter());
            getActivityListView.setAdapter(adapter);

        }

        public RaceDetailFragment getOuter() {
            return RaceDetailFragment.this;
        }

        private RaceActivity convertJsonToActivity(JSONObject json) throws JSONException {
            if (json != null) {
                RaceActivity activity = new RaceActivity(
                        (String) json.get("_id"),
                        (String) json.get("google_id"),
                        (String) json.get("description"),
                        (JSONArray) json.get("tags")
                );
                return activity;
            }
            return null;
        }
    }

}
