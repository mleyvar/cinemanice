package com.marcopololeyva.cinemanice.platform.custom;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.marcopololeyva.cinemanice.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailDialog extends DialogFragment {

    private String txtTitle;
    private String txtOverview;
    private String urlImage;
    private String txtVote;


    @BindView(R.id.imgMovie)
    ImageView imgMovie;

    @BindView(R.id.tx_vote)
    TextView tx_vote;

    @BindView(R.id.tx_title)
    TextView tx_title;

    @BindView(R.id.tx_overview)
    TextView tx_overview;




    public static DetailDialog newInstance(){

        return  new DetailDialog();
    }


    public void setData(String title, String overview, String urlImage, String vote){

        this.txtTitle=title;
        this.txtOverview=overview;
        this.urlImage=urlImage;
        this.txtVote = vote;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NORMAL;
        int theme = android.R.style.Theme_Holo;
        setStyle(style,theme);


    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.detail_movie, null);
        ButterKnife.bind(this, rootView);

        tx_title.setText(txtTitle);
        tx_overview.setText(txtOverview);
        Glide.with(getActivity()).load( urlImage).into(imgMovie);
        tx_vote.setText(txtVote);

        builder.setView(rootView);
        return builder.create();
    }


    @OnClick(R.id.btn_closeWindow)
    public void onDialogButtonClick(){
        this.getDialog().dismiss();

    }



}
