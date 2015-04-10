package com.example.suusnsuus.kroegenrace;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LoginActivity extends ActionBarActivity {
    private Button loginButton;
    private EditText username;
    private EditText password;

    SharedPreferences settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        settings = PreferenceManager.getDefaultSharedPreferences(this);

        if (settings.getString("userId", "").equals("")) {
            loginButton = (Button) findViewById(R.id.loginButton);
            username = (EditText) findViewById(R.id.username);
            password = (EditText) findViewById(R.id.password);

            loginButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    String usernameText = username.getText().toString();
                    String passwordText = password.getText().toString();

                    if(usernameText.equals("") && passwordText.equals("")) {
                        //Show toast je moet gegevens invullen
                        System.out.println("lege inlog");
                    } else {
                        LoginTask loginTask = new LoginTask(view.getContext());
                        loginTask.execute(usernameText, passwordText);
                    }
                }
            });
        } else {
            Intent intent = new Intent(this, FindRaceActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public class LoginTask extends AsyncTask<String, Integer, String> {
        private Context context;

        public LoginTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String password = params[1];

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://restrace-api.herokuapp.com/login?returnType=json");

            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("email", username));
                nameValuePairs.add(new BasicNameValuePair("password", password));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse httpresponse = httpclient.execute(httppost);
                if(httpresponse.getStatusLine().getStatusCode() == 503) {
                    return "Invalid Credentials";
                }
                String response = EntityUtils.toString(httpresponse.getEntity());
                System.out.println("response login: " + response);
                return response;

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s == null || s.equals("Invalid Credentials")) {
                username.setText("");
                password.setText("");
                return;
            }
            String userId = getUserIdFromResponse(s);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("userId", userId);
            editor.commit();
            Intent intent = new Intent(context, FindRaceActivity.class);
            startActivity(intent);
        }

        private String getUserIdFromResponse(String response) {
            try {
                JSONObject responseObject = new JSONObject(response);
                String userId = (String) responseObject.get("_id");
                return userId;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return "";
        }
    }
}