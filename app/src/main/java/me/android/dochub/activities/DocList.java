package me.android.dochub.activities;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import me.android.dochub.R;
import me.android.dochub.adapter.DocListAdapter;
import me.android.dochub.app.AppController;
import me.android.dochub.models.DocItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

public class DocList extends Activity implements SwipeRefreshLayout.OnRefreshListener{
	private static final String TAG = DocList.class.getSimpleName();
    private SwipeRefreshLayout mSwipeLayout;
	private ListView listView;
	private DocListAdapter listAdapter;
	private List<DocItem> docItems;
	private String URL_DOC = "https://dhub.herokuapp.com/docs.json";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doc_list);

        listView = (ListView) findViewById(R.id.my_list);

        docItems = new ArrayList<DocItem>();

        listAdapter = new DocListAdapter(this, docItems);

        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(listView.getContext(), Reader.class);
                i.putExtra("url", docItems.get(position).getFilepicker_url());
                startActivity(i);
            }
        });
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Entry entry_doc = cache.get(URL_DOC);

		if (entry_doc != null){
            // fetch the data from cache
            try {
                String data_doc = new String(entry_doc.data, "UTF-8");
                try {
                    parseJsonDoc(new JSONObject(data_doc));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        else{
            refetch();
        }
    }

	/**
	 * Parsing json reponse and passing the data to doc view list adapter
	 * */
	private void parseJsonDoc(JSONObject response) {
        try {
            JSONArray docArray = response.getJSONArray("docs");

            for (int i = 0; i < docArray.length(); i++) {
                JSONObject docObj = (JSONObject) docArray.get(i);

                DocItem item = new DocItem();
                item.setId(docObj.getInt("id"));
                item.setTitle(docObj.getString("title"));
                item.setDescription(docObj.getString("description"));
                item.setLicense(docObj.getString("license"));
                item.setCreated_at(docObj.getString("created_at"));

                // url might be null sometimes
                String url = docObj.isNull("filepicker_url") ? null : docObj
                        .getString("filepicker_url");
                item.setFilepicker_url(url);

                //Image url might be null sometimes
                String image = docObj.isNull("filepicker_url") ? null : docObj
                        .getString("filepicker_url") + "/convert?h=299&w=299";
                item.setImage(image);

                docItems.add(item);
            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void refetch() {
        final int no_before = docItems.size();
        Log.d("no1", (String.valueOf(no_before)));
        // making fresh volley request and getting json
        JsonObjectRequest jsonReq_doc = new JsonObjectRequest(Method.GET,
                URL_DOC, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, "Response: " + response.toString());
                if (response != null) {
                    docItems.clear();
                    parseJsonDoc(response);
                    int no_after = docItems.size();
                    Log.d("no2", (String.valueOf(no_after)));
                    Context context = getApplicationContext();
                    CharSequence text = (no_after-no_before)+" feeds updated!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(jsonReq_doc);
    }

    public void onRefresh() {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.setRefreshing(false);
                refetch();
            }
        }, 2000);
    }
/*    private class AsyncCaller extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog pdLoading = new ProgressDialog(DocList.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
//            pdLoading.setMessage("Loading...");
//            pdLoading.show();
        }
        @Override
        protected Void doInBackground(Void... params) {

            //this method will be running on background thread so don't update UI from here
            //do your long running http tasks here,you don't want to pass argument and u can access the parent class' variable url over here
            // We first check for cached request


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            refetch();
            recreate();

            //this method will be running on UI thread
            pdLoading.dismiss();
        }

    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    public void exit(MenuItem item)
    {
        finish();
    }
}
