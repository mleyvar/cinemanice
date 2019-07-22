package com.marcopololeyva.cinemanice.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.marcopololeyva.cinemanice.R;
import com.marcopololeyva.cinemanice.data.repository.DataRepository;
import com.marcopololeyva.cinemanice.model.ResultMovie;
import com.marcopololeyva.cinemanice.platform.custom.DetailDialog;
import com.marcopololeyva.cinemanice.platform.network.Conexion;

import java.util.List;

import static com.marcopololeyva.cinemanice.constant.ConstantService.BASE_IMAGE_URL;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private Context mContext;
    private List<ResultMovie> movieList;
    Activity activity;
    FragmentManager fm;
    DataRepository localDB;


    public void setMovieList(List<ResultMovie> movieList){
        this.movieList=movieList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle, txtData;
        public ImageView imgThumbnail;
        public ProgressBar progressBar;

        public ViewHolder(View view) {
            super(view);
         //   txtTitle = (TextView) view.findViewById(R.id.txtTitle);
         //   txtData = (TextView) view.findViewById(R.id.txtData);
            imgThumbnail = view.findViewById(R.id.imgThumbnail);
            progressBar =  view.findViewById(R.id.progressBar);

        }
    }


    public MoviesAdapter(Activity activity,FragmentManager fm, Context mContext, List<ResultMovie> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
        this.activity=activity;
        this.fm=fm;
        localDB = new DataRepository();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ResultMovie movie = movieList.get(position);

       // holder.txtTitle.setText(movie.getTitle());
      //  holder.txtData.setText(movie.getVoteCount() + " votes");


        if (Conexion.getInstance(activity).isConnected()){

            holder.progressBar.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(BASE_IMAGE_URL + movie.getPoster_path())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            holder.imgThumbnail.setImageDrawable(resource);
                            return false;
                        }

                    })
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.logo_header_b)
                            .error(R.drawable.error_image)
                            .override(800,900)
                    )
                    .into(holder.imgThumbnail);



        }else{
            holder.progressBar.setVisibility(View.VISIBLE);

            Glide.with(mContext)
                    .load(movie.getImage() )
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            holder.progressBar.setVisibility(View.GONE);
                            return false;
                        }

                    })
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.logo_header_b)
                            .error(R.drawable.error_image)
                            .override(800,900)
                    )
                    .into(holder.imgThumbnail);
        }



        holder.imgThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //view detail interface

                activity.runOnUiThread(() ->{
                    DetailDialog genericDialog = DetailDialog.newInstance();
                    genericDialog.setData(movie.getTitle(), movie.getOverview(),BASE_IMAGE_URL + movie.getPoster_path(),""+movie.getVote_count());
                    genericDialog.show(fm,"");
                });



            }
        });
    }



    @Override
    public int getItemCount() {
        if (movieList == null) return 0;
        return movieList.size();
    }
}