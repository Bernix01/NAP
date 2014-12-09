package nimbus.ec.nap;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;





public class LoginActivity extends Activity implements OnClickListener{
    private EditText user, pass;
    private Button mSubmit;
    private ProgressDialog pDialog;
    private static final String LOGIN_URL = "http://nimbus.pusku.com/login.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    JSONParser jsonParser = new JSONParser();
    private static final String SP_URL = "http://nimbus.pusku.com/sp.php";
    private static final String TAG_NAME = "name";
    private static final String TAG_CID = "cid";
    private static final String TAG_COURSE = "course";
    private static final String TAG_PL = "pl";
    private static final String TAG_ID = "id";
    private static String unames = "";
    private static String cids = "";
    private static String courses = "";
    private static String pls = "";
    private String ids = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Show the Up button in the action bar.
        setupActionBar();
        user = (EditText)findViewById(R.id.user);
        pass = (EditText)findViewById(R.id.password);
        mSubmit = (Button)findViewById(R.id.login);
        mSubmit.setOnClickListener(this);
    }


    public void onClick(View v) {
        // determine which button was pressed:
        switch (v.getId()) {
            case R.id.login:
                new AttemptLogin().execute();
                break;
            default:
                break;
        }
    }

    //AsyncTask is a seperate thread than the thread that runs the GUI
    //Any type of networking should be done with asynctask.
    class AttemptLogin extends AsyncTask<String, String, String> {


        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Iniciando sesión...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;

            String username = user.getText().toString();
            String password = pass.getText().toString();
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);

                // check your log for json response
                Log.d("Login attempt", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Login Successful!", json.toString());
                    List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                    params2.add(new BasicNameValuePair("username", username));
                    JSONObject json2 = jsonParser.makeHttpRequest(SP_URL, "POST", params2);
                    unames = json2.getString(TAG_NAME);
                    cids = json2.getString(TAG_CID);
                    courses = json2.getString(TAG_COURSE);
                    pls = json2.getString(TAG_PL);
                    ids = json2.getString(TAG_ID);

                    SharedPreferences sp = PreferenceManager

                            .getDefaultSharedPreferences(LoginActivity.this);

                    SharedPreferences.Editor edit = sp.edit();

                    edit.putString("username", username);
                    edit.putString("course",courses);
                    edit.putString("cid", cids);
                    edit.putString("uname",unames);
                    edit.putString("id", ids);
                    edit.putString("pl", pls);

                    edit.commit();

                    Intent i = new Intent(LoginActivity.this, ModEActivity.class);
                    finish();
                    startActivity(i);
                    return json.getString(TAG_MESSAGE);
                }else{
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(LoginActivity.this, file_url, Toast.LENGTH_SHORT).show();
            }

        }

    }




    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar() {
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
