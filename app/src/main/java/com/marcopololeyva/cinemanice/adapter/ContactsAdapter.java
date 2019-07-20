package com.marcopololeyva.cinemanice.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.marcopololeyva.cinemanice.R;
import com.marcopololeyva.cinemanice.model.ResultMovie;

import java.util.List;

import static com.marcopololeyva.cinemanice.constant.Constant.BASE_IMAGE_URL;


public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    private Context context;
    private List<ResultMovie> contactList;
    //private ContactsAdapterListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle, txtData;
        public ImageView imgThumbnail;

        public MyViewHolder(View view) {
            super(view);
          //  txtTitle = (TextView) view.findViewById(R.id.txtTitle);
         //   txtData = (TextView) view.findViewById(R.id.txtData);
            imgThumbnail = (ImageView) view.findViewById(R.id.imgThumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    //   listener.onContactSelected(contactList.get(getAdapterPosition()));
                }
            });
        }
    }
    /*

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, phone;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);


            name = view.findViewById(R.id.name);
            phone = view.findViewById(R.id.phone);
            thumbnail = view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                 //   listener.onContactSelected(contactList.get(getAdapterPosition()));
                }
            });
        }
    }
*/

//    public ContactsAdapter(Context context, List<ResultMovie> contactList, ContactsAdapterListener listener) {
    public ContactsAdapter(Context context, List<ResultMovie> contactList) {
        this.context = context;
        //this.listener = listener;
        this.contactList = contactList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ResultMovie movie = contactList.get(position);

      //  holder.txtTitle.setText(movie.getTitle());
    //    holder.txtData.setText(movie.getVoteCount() + " votes");

        Glide.with(context).load( BASE_IMAGE_URL + movie.getPoster_path()).into(holder.imgThumbnail);

        holder.imgThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //view detail interface
            }
        });
    }



/*

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Contact contact = contactList.get(position);
        holder.name.setText(contact.getName());
        holder.phone.setText(contact.getPhone());

        Glide.with(context)
                .load(contact.getProfileImage())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.thumbnail);
    }*/

    @Override
    public int getItemCount() {
         if (contactList == null) return 0; else return contactList.size();
    }
/*
    public interface ContactsAdapterListener {
        void onContactSelected(Contact contact);
    }

    */
}