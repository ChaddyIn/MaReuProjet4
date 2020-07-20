package com.chaddy.mareu.reunion_list;

import android.content.res.Configuration;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.chaddy.mareu.R;
import com.chaddy.mareu.di.DI;
import com.chaddy.mareu.events.DeleteReunionEvent;
import com.chaddy.mareu.model.Reunion;
import com.chaddy.mareu.service.ReunionApiService;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListReunionActivity extends AppCompatActivity {

    // UI Components

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    //bindview
    @BindView(R.id.list_reunion)
    RecyclerView mRecyclerView;


    private ReunionApiService mApiService;

    private List<Reunion> mReunion;


    private MyReunionRecyclerViewAdapter mReunionR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reunion);
        ButterKnife.bind(this);


        mApiService = DI.getReunionApiService();
        setSupportActionBar(mToolbar);


        mReunion = mApiService.getReunion();

        mReunionR = new MyReunionRecyclerViewAdapter(mReunion);
        mRecyclerView.setAdapter(mReunionR);

        mReunionR.notifyDataSetChanged();

        EventBus.getDefault().register(this);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filtre, menu);

        MenuItem searchItem = menu.findItem(R.id.filtreParSalle);
        MenuItem filtreParDate = menu.findItem(R.id.filtreParDate);
        android.support.v7.widget.SearchView searchView1 = (android.support.v7.widget.SearchView) searchItem.getActionView();
        android.support.v7.widget.SearchView searchView2 = (android.support.v7.widget.SearchView) filtreParDate.getActionView();


        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }


            @Override
            public boolean onQueryTextChange(String s) {

                mReunionR.getFilter().filter(s);
                mReunionR.notifyDataSetChanged();
                return false;
            }
        });

        searchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                mReunionR.getmFilter2().filter(s);
                mReunionR.notifyDataSetChanged();
                return false;
            }
        });

        return true;
    }


    @Subscribe
    public void onDeleteReunion(DeleteReunionEvent event) {
        mApiService.deleteReunion(event.reunion);
        mRecyclerView.setAdapter(mReunionR);
        mReunionR.notifyDataSetChanged();


    }

    @Override
    protected void onResume() {
        super.onResume();
        mReunionR.notifyDataSetChanged();
    }

    @OnClick(R.id.add_reunion)
    void addReunion() {
        AddReunionActivity.navigate(this);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mReunionR.notifyDataSetChanged();


    }

}




