package com.sfl.pagination;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements Pagination.Callbacks {
    private RecyclerView recyclerView;
    private RecyclerPersonAdapter adapter;
    private boolean loading = false;
    private int page = 1;
    private Handler handler;
    private Pagination pagination;
    private int itemsPerPage=10;
    private int totalPages=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        handler = new Handler();
        setupPagination();
    }

    private void setupPagination() {
        if (pagination != null) {
            pagination.unbind();
        }
        handler.removeCallbacks(fakeCallback);
        adapter = new RecyclerPersonAdapter(DataProvider.getRandomData(20));
        loading = false;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        pagination = Pagination.with(recyclerView, this)
                .setLoadingTriggerThreshold(0)
                .addLoadingListItem(true)
                .build();
    }
    @Override
    public synchronized void onLoadMore() {
        Log.d("Paginate", "onLoadMore");
        loading = true;
        // Fake asynchronous loading that will generate page of random data after some delay
        handler.postDelayed(fakeCallback, 1000);
    }

    @Override
    public synchronized boolean isLoading() {
        return loading; // Return boolean weather data is already loading or not
    }

    @Override
    public boolean hasLoadedAllItems() {
        return page == totalPages; // If all pages are loaded return true
    }

    private Runnable fakeCallback = new Runnable() {
        @Override
        public void run() {
            page++;
            adapter.add(DataProvider.getRandomData(itemsPerPage));
            loading = false;
        }
    };

}
