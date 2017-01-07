package com.example.cardview;


/**
 * Created by Surya on 12/1/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {




    Context context;
    public ArrayList<ModelData> data=new ArrayList<>();


    public void setData(ArrayList<ModelData> data){
        this.data = data;
        notifyItemRangeChanged(0, data.size());
    }
    public CardAdapter(Context context) {
   this.context=context;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView itemimage;
        public TextView itemdesc;
        public TextView itemtitle;

        public ViewHolder(View itemView) {
            super(itemView);
            itemimage = (ImageView)itemView.findViewById(R.id.item_image);
            itemtitle = (TextView)itemView.findViewById(R.id.item_title);
            itemdesc = (TextView)itemView.findViewById(R.id.item_desc);
            Typeface typeface=Typeface.createFromAsset(context.getAssets(), "fonts/royalacid.ttf");
           itemdesc.setTypeface(typeface);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position  = getAdapterPosition();
                    Snackbar.make(view, "Clicked on event "+ (position+1), Snackbar.LENGTH_SHORT)
                            .setAction("Action",null).show();

                       description(position);

                }


            });

        }


    }

    private void description(int position) {

        String hello= Integer.toString(position);


        Intent intent=new Intent(context,Description.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("position", hello);
        context.startActivity(intent);


    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup,int i)  {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout,viewGroup,false);
        RecyclerView.ViewHolder viewHolder = new ViewHolder(v);

        return (ViewHolder) viewHolder;
    }

    public static ArrayList<ModelData> parsejson(JSONObject response) throws JSONException {

        Description descriptio =new Description();
        ArrayList<ModelData> data=new ArrayList<>();
        JSONArray jsonarray=response.getJSONArray("events");
        for(int i=0;i<jsonarray.length();i++){
            JSONObject json_data = jsonarray.getJSONObject(i);
            ModelData modelData = new ModelData();
            modelData.title= json_data.getString("title");
            modelData.city= json_data.getString("city");
            modelData.description=json_data.getString("description");
            modelData.img_url = json_data.getString("img_url");
//  modelData.id= json_data.getString("id");
            data.add(modelData);

        }
        return data;
    }




    @Override
    public void onBindViewHolder(final CardAdapter.ViewHolder viewHolder, int position) {
        ModelData currentData = data.get(position);
        viewHolder.itemtitle.setText(currentData.getTitle());
        viewHolder.itemdesc.setText(currentData.getCity());
        viewHolder.itemimage.setImageResource(R.drawable.img);
        String img_url = currentData.getImgurl();
        if (img_url != null) {
            Picasso.with(viewHolder.itemimage.getContext())
                    .load(img_url).resize(700, 300)
                    .into(viewHolder.itemimage);
            }
        }



    @Override
    public int getItemCount() {
        return data.size();
    }
}
