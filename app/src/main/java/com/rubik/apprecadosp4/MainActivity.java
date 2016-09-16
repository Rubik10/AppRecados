package com.rubik.apprecadosp4;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;

import com.rubik.apprecadosp4.app.MyRecycleAdapter;
import com.rubik.apprecadosp4.app.UtilsApp;
import com.rubik.apprecadosp4.app.volley.RequestVolley;

/*
        HAy un problema, en casos muy concretos, con el formateo de la hora para la ordenacion.
            con HH - pasa de 12 a 01 en lugar de 13 (el 01 va ir siempre antes)
            con KK - pasa de 23 a 00
   1- 01:51:46
   2- 02:51:46
   3- 03:21:46
   4- 12:51:46
 */
public class MainActivity extends AppCompatActivity {

    public static MyRecycleAdapter myAdapter;
    private boolean isOrderByDate = false; // control the order of showing data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRequestData();
        initToolbar();
        initCollapsingToolbar();
        iniRecycleView();
    }

    private void initRequestData () {
        RequestVolley requestVolley = new RequestVolley(this);
        requestVolley.JSONArrayRequest(this);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void iniRecycleView () {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        myAdapter = new MyRecycleAdapter();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1, GridLayout.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(myAdapter);
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("El Recadero Master");
                } else {
                    collapsingToolbar.setTitle("");
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
                case R.id.action_settings:

                    if (isOrderByDate) {
                        isOrderByDate = false;
                        UtilsApp.showSnackBar(findViewById(R.id.action_settings), "Order per Date");
                        UtilsApp.sortByDate(RequestVolley.listRecados);
                        MainActivity.myAdapter.notifyDataSetChanged();
                    } else {
                        isOrderByDate = true;
                        UtilsApp.showSnackBar(findViewById(R.id.action_settings), "Order per User");
                        UtilsApp.sortByName(RequestVolley.listRecados);
                        MainActivity.myAdapter.notifyDataSetChanged();
                    }
                    return true;

            case R.id.action_refresh:
                UtilsApp.showSnackBar(findViewById(R.id.action_refresh), "Refresh Data");
                initRequestData();
                isOrderByDate = false;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
