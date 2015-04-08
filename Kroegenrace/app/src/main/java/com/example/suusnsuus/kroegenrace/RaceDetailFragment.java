package com.example.suusnsuus.kroegenrace;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Suzanne on 3-4-2015.
 */
public class RaceDetailFragment extends Fragment {
    private static final String SELECTED = "selected value";
    private static final String TEST_INT = "test int";

    private TextView    raceNameView;
    private TextView    raceDescriptionView;
    private String      raceName = "";
    private String      raceDescription = "";

    public RaceDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_race_detail, container, false);
        raceNameView = (TextView) view.findViewById(R.id.raceDetailName);
        raceDescriptionView = (TextView) view.findViewById(R.id.raceDetailDescription);
        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if ( bundle != null ) {
            raceName = bundle.getString("text");
            raceDescription = bundle.getString("description");
            showText(raceName, raceDescription);
        }
    }

    public void showText(String text, String description)
    {
        raceNameView.setText( text );
        raceDescriptionView.setText(description);
    }
}
