package com.example.cardview;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.load;

/**
 * Created by THUNDER on 12/2/2016.
 */

public class Description extends AppCompatActivity {

    ImageView img;
    TextView title;
    private String urlJsonObj = "https://developer.eventshigh.com/events/bangalore?key=ev3nt5h1ghte5tK3y";
    private static String TAG = MainActivity.class.getSimpleName();
    int pos;
    TextView description;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);
        description = (TextView) findViewById(R.id.description);
        title = (TextView) findViewById(R.id.title);

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/mirza.ttf");
        img=(ImageView)findViewById(R.id.img);
        description.setTypeface(face);

        int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
        Typeface robotoBoldCondensedItalic = Typeface.createFromAsset(getAssets(), "fonts/royalacid.ttf");
        if(actionBarTitleView != null){
            actionBarTitleView.setTypeface(robotoBoldCondensedItalic);
        }
        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            String name = bd.getString("position");
            int datapos = Integer.parseInt(name);
            pos=datapos;

        }

       makeJsonObjectRequest();

    }


        private void makeJsonObjectRequest() {


            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    urlJsonObj, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, response.toString());

                    try {
                        parsejson(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                    // hide the progress dialog
                }
            });

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq);
        }

    private void parsejson(JSONObject response) throws JSONException {

        List<ModelData> data=new ArrayList<>();
        JSONArray jsonarray=response.getJSONArray("events");
        for(int i=0;i<jsonarray.length();i++){
            JSONObject json_data = jsonarray.getJSONObject(i);
            ModelData modelData = new ModelData();
            modelData.title= json_data.getString("title");
            modelData.city= json_data.getString("city");
            modelData.description=json_data.getString("description");
            modelData.img_url=json_data.getString("img_url");
            JSONObject obj=json_data.getJSONObject("venue");
            modelData.Venue=obj.getString("name");
            data.add(modelData);
        }

        description.setText(Html.fromHtml(data.get(pos).getDescription()).toString());
        title.setText(data.get(pos).getTitle());
        String img_url = data.get(pos).getImgurl();
        if (img_url != null) {
            Picasso.with(getBaseContext())
                    .load(img_url).fit()
                    .into(img);
        }


    }
}

