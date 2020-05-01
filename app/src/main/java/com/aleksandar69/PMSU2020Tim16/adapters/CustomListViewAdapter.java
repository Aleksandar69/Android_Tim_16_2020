package com.aleksandar69.PMSU2020Tim16.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aleksandar69.PMSU2020Tim16.R;

public class CustomListViewAdapter extends ArrayAdapter<String> {

    private String[] ime;
    private String[] prezime;
    private String[] display;
    private String[] email;
    private Integer[] imageID;
    private Activity context;

    public CustomListViewAdapter(Activity context, String[] ime,String[] prezime, String[] display,String[] email, Integer[] imageID) {
        super(context, R.layout.activity_contact,ime);
        this.context = context;
        this.ime = ime;
        this.prezime  = prezime;
        this.display = display;
        this.email = email;
        this.imageID = imageID;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r  = convertView;
        ViewHolder viewHolder = null;
        if(r == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            r= inflater.inflate(R.layout.activity_contact, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        } else {
            viewHolder=(ViewHolder) r.getTag();
        }
        viewHolder.ivw.setImageResource(imageID[position]);
        viewHolder.tv1.setText(ime[position]);
        viewHolder.tv2.setText(prezime[position]);
        viewHolder.tv3.setText(display[position]);
        viewHolder.tv4.setText(email[position]);
        return r;
    }

    class ViewHolder {

        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        ImageView ivw;

        ViewHolder(View v) {

            tv1= (TextView) v.findViewById(R.id.tv_ime);
            tv2= (TextView) v.findViewById(R.id.tv_prezime);
            tv3 = (TextView) v.findViewById(R.id.tv_display);
            tv4 = (TextView) v.findViewById(R.id.tv_email);
            ivw = (ImageView) v.findViewById(R.id.maci_android);
        }

    }
}
