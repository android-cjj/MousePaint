package com.cjj.mousepaint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cjj.listener.CallbackListener;
import com.cjj.mousepaint.adapter.CategoryDetialAdapter;
import com.cjj.mousepaint.adapter.SearchAdapter;
import com.cjj.mousepaint.dao.AppDao;
import com.cjj.mousepaint.model.CategoryModel;
import com.cjj.mousepaint.model.SearchComicModel;
import com.cjj.mousepaint.url.HttpUrl;
import com.cjj.mousepaint.utils.StatusBarCompat;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/9.
 */
public class SearchActivity extends AppCompatActivity {
    @Bind(R.id.lv_search)
    ListView mListView;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    String search = null;
    private SearchAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        StatusBarCompat.compat(this, getResources().getColor(R.color.brownness));

        search = getIntent().getStringExtra("search");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("搜索结果");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSearchData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void getSearchData() {
        AppDao.getInstance().searchComicData(search, new CallbackListener<SearchComicModel>() {
            @Override
            public void onSuccess(SearchComicModel result) {
                super.onSuccess(result);
                if (result != null) {
                    List<SearchComicModel.ReturnEntity.ListEntity> list = result.Return.List;
                    mAdapter = new SearchAdapter(SearchActivity.this, list);
                    mListView.setAdapter(mAdapter);
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(SearchActivity.this, DetialActivity.class);
                            intent.putExtra("bookId",String.valueOf(mAdapter.getItem(position).Id));
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
