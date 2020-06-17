package com.aleksandar69.PMSU2020Tim16.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleksandar69.PMSU2020Tim16.R;
import com.aleksandar69.PMSU2020Tim16.models.Contact;

import java.util.ArrayList;

public class ContactsBaseAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Contact> contactsList;

    public ContactsBaseAdapter(Context context, int layout, ArrayList<Contact> contactsList) {
        this.context = context;
        this.layout = layout;
        this.contactsList = contactsList;
    }

    @Override
    public int getCount() {
        return contactsList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtFirst, txtLast, txtDisplay, txtEmail;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null ) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout,null);

            holder.txtFirst = (TextView) row.findViewById(R.id.row_first);
            holder.imageView = (ImageView) row.findViewById(R.id.imageViewUpload);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Contact contact = contactsList.get(position);
        holder.txtFirst.setText(contact.getFirst());
        byte[] contactImage= contact.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(contactImage,0,contactImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
