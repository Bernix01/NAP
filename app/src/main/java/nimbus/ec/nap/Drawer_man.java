package nimbus.ec.nap;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


public class Drawer_man extends Activity {

    protected LinearLayout fullLayout;
    protected FrameLayout actContent;

    @Override
    public void setContentView(final int layoutResID) {
        fullLayout= (LinearLayout) getLayoutInflater().inflate(R.layout.layout_drawer_man, null); // Your base layout here
        actContent= (FrameLayout) fullLayout.findViewById(R.id.main_content);
        getLayoutInflater().inflate(layoutResID, actContent, true); // Setting the content of layout your provided to the act_content frame
        super.setContentView(fullLayout);
        // here you can get your drawer buttons and define how they should behave and what must they do, so you won't be needing to repeat it in every activity class
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
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
}
