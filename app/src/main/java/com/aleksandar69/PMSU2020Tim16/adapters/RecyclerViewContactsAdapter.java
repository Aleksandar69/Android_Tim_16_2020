package com.aleksandar69.PMSU2020Tim16.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aleksandar69.PMSU2020Tim16.R;
import com.aleksandar69.PMSU2020Tim16.activities.ContactActivity;
import com.aleksandar69.PMSU2020Tim16.models.Contact;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewContactsAdapter extends RecyclerView.Adapter<RecyclerViewContactsAdapter.ViewHolder> {

    private Context context;
    private List<Contact> contactList;

    public RecyclerViewContactsAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    public RecyclerViewContactsAdapter() {

    }

    //stack overflow
    /*
    public RecyclerViewAdapter(Context context, List<Model> model) {
    this.context = context;
    this.model = model;
}

Then in onActivityResult do like this:

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 4) {
        listModel.clear();
        listModel.addAll(repository.consDataBase(context));
        recyclerViewAdapter.notifyDataSetChanged();
    }
}

     */

    public void notify(List<Contact> contactL) {
        if (contactList != null) {
            contactList.clear();
            contactList.addAll(contactL);

        } else {
            contactList = contactL;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewContactsAdapter.ViewHolder holder, int position) {
        Contact contact = contactList.get(position);

        holder.contactName.setText(contact.getFirst());
        //holder.iconButton.setImageResource(contact.getImageSourceID());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView contactName;
        public ImageView iconButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            contactName = itemView.findViewById(R.id.Contact_name);
            iconButton = itemView.findViewById(R.id.imageViewButton);

            iconButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            Contact contact = contactList.get(position);
            String first = contact.getFirst();
            String last = contact.getLast();
            String display = contact.getDisplay();
            String email = contact.getEmail();
            //int photoId = contact.getImageSourceID();

            Toast.makeText(context, "The position is " + String.valueOf(position) +
                    ",First name is " + first +
                    ",Last name is " + last +
                    ",Display " + display +
                    ",Email is " + email,Toast.LENGTH_LONG).show();

            Intent intent = new Intent(context, ContactActivity.class);
            intent.putExtra("RFirst" ,first);
            intent.putExtra("RLast",last);
            intent.putExtra("RDisplay", display);
            intent.putExtra("REmail",email);
            //intent.putExtra("RPhoto", photoId);

            context.startActivity(intent);
        }
    }
}
