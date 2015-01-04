/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package nimbus.ec.napdemo;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import nimbus.ec.napdemo.library.DatabaseHandler;
import nimbus.ec.napdemo.library.UserFunctions;

public class LoginActivity extends Activity {
	Button btnLogin;
	Button btnLinkToRegister;
	EditText inputEmail;
	EditText inputPassword;
	TextView loginErrorMsg;

	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_CURSO = "curso";
    private static final String KEY_PARALELO = "paralelo";
    private static final String KEY_BIO = "bio";
    private static final String KEY_CPRES = "colegio_presedencia";
    private static final String KEY_PIMAGE = "p_image";
    private static final String KEY_REPRESENTANTES = "representantes";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// Importing all assets like buttons, text fields
		inputEmail = (EditText) findViewById(R.id.etxt_usr);
		inputPassword = (EditText) findViewById(R.id.etxt_pw);
		btnLogin = (Button) findViewById(R.id.do_login);
		btnLinkToRegister = (Button) findViewById(R.id.go_register);
		loginErrorMsg = (TextView) findViewById(R.id.error_etxt);


		// Login button Click Event
		btnLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
                new do_login().execute(inputEmail.getText().toString(),inputPassword.getText().toString());
			}
		});

		// Link to Register Screen
		btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						RegisterActivity.class);
				startActivity(i);
				finish();
			}
		});
	}
    private class do_login extends AsyncTask<String,Void,Boolean> {

        UserFunctions userFunction;
        JSONObject json;
        private ProgressDialog loading;
        protected void onPreExecute(){
            loading = new ProgressDialog(getApplicationContext());
            /*getApplicationContext(),"","Iniciando sesión, por favor espere..."*/
        }
        protected Boolean doInBackground(String... params){
            userFunction = new UserFunctions();
                json = userFunction.loginUser(params[0], params[1]);
                Log.d("JSON_login",json.toString());
            try {
                if (json.getString(KEY_SUCCESS) != null) {
                    String res = json.getString(KEY_SUCCESS);
                    if(Integer.parseInt(res) == 1){
                        // user successfully logged in
                        // Store user details in SQLite Database
                    return true;
                        // Close Login Screen
                    }else{
                        // Error in login
                        return false;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }
        protected void onPostExecute( Boolean result){
            loading.dismiss();
            if(result) {
                try {
                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    JSONObject json_user = json.getJSONObject("user");

                    // Clear all previous data in database
                    userFunction.logoutUser(getApplicationContext());
                    db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT),json_user.getString(KEY_CURSO),json_user.getString(KEY_PARALELO),json_user.getString(KEY_BIO),json_user.getString(KEY_CPRES),json_user.getString(KEY_PIMAGE),json_user.getString(KEY_REPRESENTANTES) );

                    // Launch Dashboard Screen
                    Intent dashboard = new Intent(getApplicationContext(), MainActivity.class);

                    // Close all views before launching Dashboard
                    dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(dashboard);
                    finish();
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
            else{
                loginErrorMsg.setText("Incorrect username/password");
            }
        }
    }

}
