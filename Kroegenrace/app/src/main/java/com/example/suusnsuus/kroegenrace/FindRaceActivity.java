package com.example.suusnsuus.kroegenrace;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class FindRaceActivity extends Activity implements RaceListFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_race);

        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentByTag("list") == null)
        {
            fm.beginTransaction().add(R.id.container, new RaceListFragment(), "list").commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_race, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String text) {
        Fragment m_cFragement;

        m_cFragement = new RaceDetailFragment();

        m_cFragement.setArguments(new Bundle());
        m_cFragement.getArguments().putString("text", text);

        getFragmentManager().beginTransaction().replace(R.id.container, m_cFragement, "detail").addToBackStack(null).commit();
    }
}
