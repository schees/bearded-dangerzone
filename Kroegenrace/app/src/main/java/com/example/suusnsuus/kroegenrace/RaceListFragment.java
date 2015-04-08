package com.example.suusnsuus.kroegenrace;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suzanne on 3-4-2015.
 */
public class RaceListFragment extends Fragment {

    private ListView                    m_cListView;
    private List<Race> m_sRaceArray;
    private ListRacesAdapter          m_saAdapter;
    private OnFragmentInteractionListener m_pListener;
    private View view;

    public RaceListFragment() {
        // Required empty public constructor
        m_sRaceArray = new ArrayList<Race>();

    }

    public void updateListView(List<Race> races) {
        m_sRaceArray.addAll(races);
        m_saAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_race_list, container, false);
        this.view = view;
        m_cListView = (ListView) view.findViewById(R.id.listView);
        m_saAdapter = new ListRacesAdapter(view.getContext(), m_sRaceArray);
        GetAllRacesTask getAllRaces = new GetAllRacesTask(this);
        getAllRaces.execute();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            m_pListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        m_pListener = null;
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String text);
    }

    public class GetAllRacesTask extends AsyncTask<String, Integer, List<Race>> {
        private List<Race> allRaces;
        private RaceListFragment context;

        public GetAllRacesTask(RaceListFragment v) {
            context = v;
        }
        @Override
        protected List<Race> doInBackground(String... params) {
            List<Race> finalRaces = new ArrayList<Race>();
            System.out.println("doInBackground");
            HttpGet httpGet = new HttpGet("http://restrace-api.herokuapp.com/race");
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
                        List<Race> races = new ArrayList<Race>(length);
                        for (int i = 0; i < length; i++)
                        {
                            Race foundRace = convertJsonToRace(convertedResponse.getJSONObject(i));
                            races.add(foundRace);
                        }
                        finalRaces = races;

                        return races;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return finalRaces;
        }

        @Override
        protected void onPostExecute(List<Race> races) {
            super.onPostExecute(races);
            context.updateListView(races);

            ListRacesAdapter adapter = new ListRacesAdapter(m_cListView.getContext(), m_sRaceArray);
            m_cListView.setAdapter(adapter);

            TextView empty = (TextView) view.findViewById(R.id.empty);
            if (m_sRaceArray.size() == 0) {
                empty.setText("No races found");
            } else {
                empty.setText("");
            }
        }

        private Race convertJsonToRace(JSONObject json) throws JSONException {
            if (json != null) {
                Race race = new Race(
                        (String) json.get("_id"),
                        (String) json.get("name"),
                        (String) json.get("description"),
                        (String) json.get("owner"),
                        (String) json.get("startDateTime"),
                        (String) json.get("endDateTime"),
                        (JSONArray) json.get("activities"),
                        (JSONArray) json.get("users")
                );
                return race;
            }
            return null;
        }
    }

}
