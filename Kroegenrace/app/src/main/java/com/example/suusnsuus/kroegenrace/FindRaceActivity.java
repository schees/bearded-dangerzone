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

        if(fm.findFragmentById(R.id.large_race_list_fragment) != null) {
            //We're in a large layout
            System.out.println("Large layout found");
        }
        else if (fm.findFragmentByTag("list") == null)
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
    public void onFragmentInteraction(String name, String description) {
        Fragment m_cFragment;

        m_cFragment = new RaceDetailFragment();

        m_cFragment.setArguments(new Bundle());
        m_cFragment.getArguments().putString("text", name);
        m_cFragment.getArguments().putString("description", description);

        if (getFragmentManager().findFragmentById(R.id.large_race_list_fragment) == null) {
            getFragmentManager().beginTransaction().replace(R.id.container, m_cFragment, "detail").addToBackStack(null).commit();
        } else if (findViewById(R.id.detail_fragment_layout) == null){
            System.out.println("Adding fragment");
            getFragmentManager().beginTransaction().add(R.id.detailContainer, m_cFragment, "detail").addToBackStack(null).commit();
        } else {
            System.out.println("Replacing fragment");
            getFragmentManager().beginTransaction().replace(R.id.detailContainer, m_cFragment, "detail").addToBackStack(null).commit();
        }

    }
}
