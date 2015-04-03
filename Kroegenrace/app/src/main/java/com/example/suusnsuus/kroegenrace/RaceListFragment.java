package com.example.suusnsuus.kroegenrace;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Suzanne on 3-4-2015.
 */
public class RaceListFragment extends Fragment {

    private ListView                      m_cListView;
    private ArrayList<String>             m_sStringArray;
    private ArrayAdapter<String>          m_saAdapter;
    private OnFragmentInteractionListener m_pListener;

    public RaceListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_race_list, container, false);

        m_cListView = (ListView) view.findViewById(R.id.listView);
        m_sStringArray  = new ArrayList<String>();

        for ( int n=0; n<10; n++ )
            m_sStringArray.add( "text " + (10-n) );

        m_saAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, m_sStringArray);

        m_cListView.setAdapter(m_saAdapter);
        m_cListView.setClickable(true);
        m_cListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                m_pListener.onFragmentInteraction(m_sStringArray.get(position));
            }
        });
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
}
