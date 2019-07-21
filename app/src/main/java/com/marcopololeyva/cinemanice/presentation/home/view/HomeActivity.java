package com.marcopololeyva.cinemanice.presentation.home.view;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import com.marcopololeyva.cinemanice.platform.network.Conexion;
import com.marcopololeyva.cinemanice.R;
import com.marcopololeyva.cinemanice.adapter.PagerAdapter;
import com.marcopololeyva.cinemanice.presentation.base.BaseActivity;
import com.marcopololeyva.cinemanice.presentation.moviehome.view.PopularFragment;
import com.marcopololeyva.cinemanice.root.App;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


import static com.marcopololeyva.cinemanice.constant.Constant.BUNDLES_CONEXION_NETWORK;

public class HomeActivity extends BaseActivity {


    private CompositeDisposable disposable = new CompositeDisposable();
    private int posTab;

    @BindView(R.id.input_search)
    EditText inputSearch;

    private Unbinder unbinder;
    PagerAdapter adapter;

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private PopularFragment fragment;
    private Conexion conexionNetwork;


    public Conexion getConexionNetwork() {
        return Conexion.getInstance(this);
    }

    public void setConexionNetwork(Conexion conexionNetwork) {
        this.conexionNetwork = conexionNetwork;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        unbinder=ButterKnife.bind(this);
        conexionNetwork = Conexion.getInstance(this);
        setupViewPager();
        App.getmPreference().setBooleanData("First",true);

    }


    private void setupViewPager() {
        adapter = new PagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();

        bundle.putSerializable(BUNDLES_CONEXION_NETWORK,conexionNetwork);
        fragment = PopularFragment.newInstance(1,bundle);
        adapter.addFragment(fragment,getString(R.string.Popular));
        adapter.addFragment(PopularFragment.newInstance(2,bundle),getString(R.string.TopRated));
        adapter.addFragment(PopularFragment.newInstance(3,bundle),getString(R.string.Upcoming));

        viewPager.setAdapter(adapter);

        tabs.setupWithViewPager(viewPager);

        disposable.add(RxTextView.textChangeEvents(inputSearch)
                .skipInitialValue()
                .debounce(800, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(searchMoviesTextWatcher()));






        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition()) ;
                posTab=tab.getPosition();
                ((PopularFragment) adapter.getItem(posTab)).getData(posTab);


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });


        inputSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getX() >= ((TextView)v).getWidth() - ((TextView)v).getCompoundPaddingEnd()) {
                        ((TextView)v).setText("");
                        ((PopularFragment) adapter.getItem(0)).getData(0);
                        return true;
                    }
                }
                return false;
            }
        });





    }


    public int getTabSelected(){
        return tabs.getSelectedTabPosition();
    }

    public int getViewPagerItem(){
        return viewPager.getCurrentItem();
    }
    @Override
    protected void onDestroy() {
        disposable.clear();
        unbinder.unbind();
        super.onDestroy();
    }

    private DisposableObserver<TextViewTextChangeEvent> searchMoviesTextWatcher() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                if (!textViewTextChangeEvent.text().toString().equals("")) {

                    TabLayout.Tab tab = tabs.getTabAt(0);
                    tab.select();

                    ((PopularFragment) App.getWhichFragment()).getDataSearch(textViewTextChangeEvent.text().toString());



                }

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(HomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        };
    }

}
