package nimbus.ec.nap;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class NewsPFActivity extends ListActivity {

	private ProgressDialog pDialog;

	private static final String READ_COMMENTS_URL = "http://nimbus.pusku.com/news.php";
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_TITLE = "title";
	private static final String TAG_POSTS = "posts";
	private static final String TAG_NEW_ID = "new_id";
	private static final String TAG_NEW_DATE = "new_date";
	private static final String TAG_NEW = "new";
	private JSONArray mComments = null;
	private ArrayList<HashMap<String, String>> mCommentList = new ArrayList<HashMap<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newspf);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// loading the comments via AsyncTask
		new LoadComments().execute();
	}





	public void updateJSONdata() {
        JSONParser jParser = new JSONParser();
		JSONObject json = jParser.getJSONFromUrl(READ_COMMENTS_URL);
		try {
			mComments = json.getJSONArray(TAG_POSTS);
			for (int i = 0; i < mComments.length(); i++) {
				JSONObject c = mComments.getJSONObject(i);
				String title = c.getString(TAG_TITLE);
				String content = c.getString(TAG_NEW);
				String username = c.getString(TAG_NEW_DATE);
				HashMap<String, String> map = new HashMap<String, String>();

				map.put(TAG_TITLE, title);
				map.put(TAG_NEW, content);
				map.put(TAG_NEW_DATE, username);
				mCommentList.add(map);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void updateList() {
		ListAdapter adapter;
        adapter = new SimpleAdapter(this, mCommentList,
                R.layout.single_post, new String[] { TAG_TITLE, TAG_NEW,
                        TAG_NEW_DATE }, new int[] { R.id.title, R.id.message,
                        R.id.username });

        setListAdapter(adapter);

		ListView lv = getListView();	
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {


			}
		});
	}

	public class LoadComments extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(NewsPFActivity.this);
			pDialog.setMessage("Cargando noticias...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			updateJSONdata();
			return null;

		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			pDialog.dismiss();
			updateList();
		}
	}
}
