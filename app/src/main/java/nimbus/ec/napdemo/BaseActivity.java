package nimbus.ec.napdemo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;

import it.neokree.materialnavigationdrawer.MaterialAccount;
import it.neokree.materialnavigationdrawer.MaterialAccountListener;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.MaterialSection;
import nimbus.ec.napdemo.library.DatabaseHandler;


public class BaseActivity extends MaterialNavigationDrawer implements MaterialAccountListener {


    MaterialAccount account;
    MaterialSection section1, section2, recorder, night, last, settingsSection;

    DatabaseHandler dbhan;
    HashMap<String,String> user_details;

    @Override
    public void init(Bundle savedInstanceState) {
        dbhan = new DatabaseHandler(getApplicationContext());
        user_details = dbhan.getUserDetails();
        // add first account
        account = new MaterialAccount(user_details.get("name"),user_details.get("email"),new ColorDrawable(Color.parseColor("#ff9800")),this.getResources().getDrawable(R.drawable.pano));
        this.addAccount(account);

        this.setAccountListener(this);

        // create sections
        section1 = this.newSection(this.getResources().getString(R.string.title_activity_main),new News_fragment());
        section2 = this.newSection(this.getResources().getString(R.string.title_activity_grades),new GradesActivity());
        // recorder section with icon and 10 notifications
        recorder = this.newSection("Recorder", this.getResources().getDrawable(R.drawable.abc_ic_voice_search_api_mtrl_alpha), new FragmentIndex()).setNotifications(10);
        // night section with icon, section color and notifications
        night = this.newSection("Night Section", this.getResources().getDrawable(R.drawable.abc_ic_menu_copy_mtrl_am_alpha), new FragmentIndex())
                .setSectionColor(Color.parseColor("#2196f3")).setNotifications(150);
        // night section with section color
        last = this.newSection("Last Section", new FragmentIndex()).setSectionColor(Color.parseColor("#ff9800"),Color.parseColor("#ef6c00"));

        Intent i = new Intent(this,ProfileActivity.class);
        settingsSection = this.newSection(this.getResources().getString(R.string.title_activity_settings),this.getResources().getDrawable(R.drawable.ic_settings),i);

        // add your sections to the drawer
        this.addSection(section1);
        this.addSection(section2);
        this.addDivisor();
        this.addSection(recorder);
        this.addSection(night);
        this.addDivisor();
        this.addSection(last);
        this.addBottomSection(settingsSection);

        this.addMultiPaneSupport();

        // start thread
        t.start();

    }


    @Override
    public void onAccountOpening(MaterialAccount account) {
        // open profile activity
        Intent i = new Intent(this,ProfileActivity.class);
        startActivity(i);
    }

    @Override
    public void onChangeAccount(MaterialAccount newAccount) {
        // when another account is selected
    }
    Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);

                account.setPhoto(Picasso.with(getApplicationContext()).load("http://attx.nimbus.ec/nap_demo/"+user_details.get("p_image")).get());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyAccountDataChanged();
                        Toast.makeText(getApplicationContext(), "Loaded 'from web' user image", Toast.LENGTH_SHORT).show();
                    }
                });
                //Log.w("PHOTO","user account photo setted");


            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
}
