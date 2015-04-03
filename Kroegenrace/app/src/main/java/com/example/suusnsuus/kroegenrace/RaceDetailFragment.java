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

    private TextView    m_cTextView;
    private Button      m_cShowToastButton;
    private Button      m_cShowDialogButton;
    private String      m_sText = "";

    public RaceDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_race_detail, container, false);

        m_cTextView         = (TextView ) view.findViewById(R.id.textView);
        m_cShowToastButton  = (Button)    view.findViewById(R.id.button_toast);
        m_cShowDialogButton = (Button)    view.findViewById(R.id.button_dialog);

        m_cShowToastButton.setOnClickListener(new View.OnClickListener() {
            public void onClick( View view )
            {
                Toast.makeText(RaceDetailFragment.this.getActivity(), m_sText, Toast.LENGTH_LONG).show();
            }
        });

        m_cShowDialogButton.setOnClickListener(new View.OnClickListener() {
            public void onClick( View view )
            {
                SharedPreferences preferences = getActivity().getSharedPreferences("een naam...", Context.MODE_PRIVATE);

                AlertDialog.Builder builder = new AlertDialog.Builder(RaceDetailFragment.this.getActivity());
                builder.setTitle("de tekst");
                builder.setMessage(preferences.getString(SELECTED, "hmmmm"));
                builder.setPositiveButton("Okidoki "+preferences.getInt(TEST_INT, -27), new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                    }
                });
                builder.create().show();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        if ( bundle != null ) {
            m_sText = bundle.getString("text");
            showText(m_sText);
        }

        testPreferences();
    }

    public void showText( String text )
    {
        m_cTextView.setText( text );
    }

    public void testPreferences()
    {
        SharedPreferences preferences = getActivity().getSharedPreferences("een naam...", Context.MODE_PRIVATE);

        int myintvalue       = preferences.getInt   (TEST_INT, -27);
        String mystringvalue = preferences.getString(SELECTED, "hmmmm");

        SharedPreferences.Editor edit = preferences.edit();

        edit.putInt(TEST_INT, 38);
        edit.putString(SELECTED, m_sText);
        edit.commit();
    }
}
