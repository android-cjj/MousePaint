package com.cjj.mousepaint;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cjj.listener.CallbackListener;
import com.cjj.mousepaint.adapter.DetialBookAdapter;
import com.cjj.mousepaint.dao.AppDao;
import com.cjj.mousepaint.model.DetialComicBookModel;
import com.cjj.mousepaint.model.SubscribeModel;
import com.cjj.mousepaint.url.HttpUrl;
import com.cjj.mousepaint.utils.SPUtils;
import com.cjj.mousepaint.utils.StatusBarCompat;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/10.
 */
public class DetialActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.backdrop)
    ImageView mImageView;
    @Bind(R.id.lv_detial)
    GridView mListView;
    @Bind(R.id.ns_view)
    NestedScrollView mNestedScrollView;
    @Bind(R.id.fab_sub)
    FloatingActionButton fab_sub;

    private boolean isHaveBottom = false;
    private String id ;
    private DetialBookAdapter mDetialBookAdapter;
    private boolean isLoadMore = false;
    private List<DetialComicBookModel.ReturnEntity.ListEntity> list;
    private int PageIndex = 0;
    private boolean isSub = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detial);
        ButterKnife.bind(this);
        StatusBarCompat.compat(this, getResources().getColor(R.color.brownness));
        id = getIntent().getStringExtra("bookId");
        mNestedScrollView.setOnTouchListener(new TouchListenerImpl());
        setSupportActionBar(mToolbar);
        fab_sub.setOnClickListener(this);
        mListView.setOnItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getBookComicData();


    }
    private void getBookComicData() {
        AppDao.getInstance().getBookComicData(id, String.valueOf(PageIndex), new CallbackListener<DetialComicBookModel>() {
            @Override
            public void onSuccess(DetialComicBookModel result) {
                super.onSuccess(result);
                showView(result);
            }
        });
    }

    private void showView(DetialComicBookModel result) {
        if(!isLoadMore)
        {
            list = result.Return.List;
            mCollapsingToolbarLayout.setTitle(result.Return.ParentItem.Title);
            Glide.with(this).load(result.Return.ParentItem.FrontCover).centerCrop().into(mImageView);
            mDetialBookAdapter = new DetialBookAdapter(DetialActivity.this,list );
            mListView.setAdapter(mDetialBookAdapter);

        }else
        {
            list.addAll(result.Return.List);
            mDetialBookAdapter.updateData(list);
            isLoadMore = false;
            isHaveBottom = false;
        }

        if(SPUtils.getObject(MosuePaintAppication.UserInfo.email+"id"+id,String.class,"-1").equals(id))
        {
            fab_sub.setImageResource(R.drawable.ic_done);
            isSub = false;
        }else
        {
            isSub = true;
            fab_sub.setImageResource(R.drawable.ic_add_white_24dp);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fab_sub:
                    subComicBook();
                break;
        }
    }

    private void subComicBook() {
        if(id==null)
        {
            return;
        }
        AppDao.getInstance().subscribeBook(id,isSub,new CallbackListener<SubscribeModel>()
        {

            @Override
            public void onSuccess(SubscribeModel result) {
                super.onSuccess(result);
                if(isSub)
                {
                    Toast.makeText(DetialActivity.this,"订阅成功",Toast.LENGTH_SHORT).show();
                    SPUtils.saveObject(MosuePaintAppication.UserInfo.email + "id" + id, id);
                    fab_sub.setImageResource(R.drawable.ic_done);
                    isSub = false;
                }else
                {
                    Toast.makeText(DetialActivity.this,"已取消订阅",Toast.LENGTH_SHORT).show();
                    SPUtils.saveObject(MosuePaintAppication.UserInfo.email + "id" + id, "-2");
                    fab_sub.setImageResource(R.drawable.ic_add_white_24dp);
                    isSub = true;
                }
            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                Toast.makeText(DetialActivity.this,"操作失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        handClick(HttpUrl.URL_IMG_CHAPTER + mDetialBookAdapter.getItem(position).Id, "123");

    }

    private void handClick(String url,String title) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra(WebActivity.EXTRA_URL, url);
        intent.putExtra(WebActivity.EXTRA_TITLE,title);
        startActivity(intent);
    }

    private class TouchListenerImpl implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    break;
                case MotionEvent.ACTION_MOVE:
                    int scrollY=view.getScrollY();
                    int height=view.getHeight();
                    int scrollViewMeasuredHeight=mNestedScrollView.getChildAt(0).getMeasuredHeight();
                    if(scrollY==0){
                        System.out.println("滑动到了顶端 view.getScrollY()="+scrollY);
                    }
                    if((scrollY+height)==scrollViewMeasuredHeight){
                        if(!isHaveBottom)
                        {
                            isLoadMore = true;
                            PageIndex += 1;
                            getBookComicData();
                        }
                        isHaveBottom = true;

                    }
                    break;

                default:
                    break;
            }
            return false;
        }

    };
}
