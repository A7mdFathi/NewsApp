package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{
    private TextView mEmptyState;
    private static final int NEWS_LOADER_ID = 1;
    private static final String NEWS_URL = "https://content.guardianapis.com/search?";
    private static final String LOG_TAG = MainActivity.class.getName();
    private NewsAdapter madabter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager conManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();


        View loadIndicator = findViewById(R.id.progress);
        mEmptyState = (TextView)findViewById(R.id.empty_view);

        if(networkInfo!=null&&networkInfo.isConnected()){

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        }else {

            loadIndicator.setVisibility(View.GONE);
            mEmptyState.setText(R.string.no_internet);
        }

        // Find a reference to the {@link ListView} in the layout
        ListView newsListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        madabter = new NewsAdapter(this, new ArrayList<News>());


        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(madabter);


        mEmptyState = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyState);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News currentNews = madabter.getItem(i);
                Uri newsUri = Uri.parse(currentNews.getnUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                startActivity(websiteIntent);
            }
        });
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String minNews = sharedPrefs.getString(getString(R.string.settings_min_news_key),
                getString(R.string.settings_min_news_default));

        String orderBy = sharedPrefs.getString(getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

        String section = sharedPrefs.getString(getString(R.string.settings_section_news_key),
                getString(R.string.settings_section_news_default));

        Uri baseUri = Uri.parse(NEWS_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("api-key", "test");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("page-size", minNews);
        uriBuilder.appendQueryParameter("order-by", orderBy);

        if (!section.equals(getString(R.string.settings_section_news_default))) {
            uriBuilder.appendQueryParameter("section", section);
        }

        return new NewsLoader(this, uriBuilder.toString());

    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        View loadingIndicator =findViewById(R.id.progress);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyState.setText(R.string.no_news);

        madabter.clear();
        if (news != null && !news.isEmpty()) {
            madabter.addAll(news);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        madabter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
