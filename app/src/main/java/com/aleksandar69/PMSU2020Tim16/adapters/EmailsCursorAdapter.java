package com.aleksandar69.PMSU2020Tim16.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.aleksandar69.PMSU2020Tim16.R;

import org.w3c.dom.Text;

public class EmailsCursorAdapter extends CursorAdapter {

    public EmailsCursorAdapter(Context context, Cursor cursor){
        super(context,cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_layout_emails, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView columnTo = (TextView) view.findViewById(R.id.columnFrom);
        TextView columnSubject = (TextView) view.findViewById(R.id.columnSubject);
        TextView columnContent = (TextView) view.findViewById(R.id.columnContent);
        TextView columnDate = (TextView) view.findViewById(R.id.columnDate);

        String messageTo = cursor.getString(cursor.getColumnIndex("messageto"));
        String messageSubject = cursor.getString(cursor.getColumnIndex("subject"));
        String messageContent = cursor.getString(cursor.getColumnIndex("content"));
        String messageDate = cursor.getString(cursor.getColumnIndex("datetime"));

        columnTo.setText(messageTo);
        columnSubject.setText(messageSubject);
        columnContent.setText(messageContent);
        columnDate.setText(messageDate);

    }
}
