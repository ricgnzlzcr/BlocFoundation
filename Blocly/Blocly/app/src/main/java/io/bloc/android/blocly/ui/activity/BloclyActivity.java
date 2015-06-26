package io.bloc.android.blocly.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import io.bloc.android.blocly.R;
import io.bloc.android.blocly.api.model.RssFeed;
import io.bloc.android.blocly.ui.adapter.ItemAdapter;
import io.bloc.android.blocly.ui.adapter.NavigationDrawerAdapter;

/**
 * Created by ricardo on 5/18/15.
 */
public class BloclyActivity extends ActionBarActivity implements NavigationDrawerAdapter.NavigationDrawerAdapterDelegate, ItemAdapter.ItemAdapterDelegate {

    private ItemAdapter itemAdapter;

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private NavigationDrawerAdapter navigationDrawerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocly);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_activity_blocly);
        setSupportActionBar(toolbar);

        itemAdapter = new ItemAdapter();
        itemAdapter.setDelegate(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_activity_blocly);
        // #12
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.dl_activity_blocly);
        // #6
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0);
        drawerLayout.setDrawerListener(drawerToggle);

        navigationDrawerAdapter = new NavigationDrawerAdapter();
        navigationDrawerAdapter.setDelegate(this);
        RecyclerView navigationRecyclerView = (RecyclerView) findViewById(R.id.rv_nav_activity_blocly);
        navigationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        navigationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        navigationRecyclerView.setAdapter(navigationDrawerAdapter);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    // #7b
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    // #7c
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
      * NavigationDrawerAdapterDelegate
      */

    @Override
    public void didSelectNavigationOption(NavigationDrawerAdapter adapter, NavigationDrawerAdapter.NavigationOption navigationOption) {
        // #3a
        drawerLayout.closeDrawers();
        Toast.makeText(this, "Show the " + navigationOption.name(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void didSelectFeed(NavigationDrawerAdapter adapter, RssFeed rssFeed) {
        // #3b
        drawerLayout.closeDrawers();
        Toast.makeText(this, "Show RSS items from " + rssFeed.getTitle(), Toast.LENGTH_SHORT).show();
    }

    /*
    * ItemAdapterDelegate Methods
     */

    @Override
    public void didUserExpandContent(ItemAdapter adapter, boolean contentExpanded) {
        Toast.makeText(this.getBaseContext(), "Content expanded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void didUserContractContent(ItemAdapter adapter, boolean contentExpanded) {
        Toast.makeText(this.getBaseContext(), "Content contracted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void didUserVisitSite(ItemAdapter adapter, TextView visitSite) {
        View v = (View)visitSite;
        Toast.makeText(v.getContext(), "Visit Site!!!!!" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void didUserFavoriteItem(ItemAdapter adapter, CheckBox favoriteCheckbox) {
        Toast.makeText(this.getBaseContext(), "Favorite checked...awesome", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void didUserArchiveItem(ItemAdapter adapter, CheckBox archiveCheckbox) {
        Toast.makeText(this.getBaseContext(), "Archived checked...gnarly", Toast.LENGTH_SHORT).show();
    }
}
