package com.cjj.mousepaint.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cjj.http.Http;
import com.cjj.listener.CallbackListener;
import com.cjj.mousepaint.MosuePaintAppication;
import com.cjj.mousepaint.R;
import com.cjj.mousepaint.WebActivity;
import com.cjj.mousepaint.customview.FavorLayout;
import com.cjj.mousepaint.customview.SimpleTagImageView;
import com.cjj.mousepaint.dao.AppDao;
import com.cjj.mousepaint.events.UpdateHomeDataEvent;
import com.cjj.mousepaint.model.AllBookModels;
import com.cjj.mousepaint.model.SubscribeModel;
import com.cjj.mousepaint.url.HttpUrl;
import com.cjj.mousepaint.utils.SPUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2015/10/17.
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    ArrayList<AllBookModels.ReturnClazz.AllBook> mList;
    public HomeRecyclerViewAdapter(Context context,ArrayList<AllBookModels.ReturnClazz.AllBook> List)
    {
        this.mContext = context;
        this.mList = List;
    }

    public void updateData(ArrayList<AllBookModels.ReturnClazz.AllBook> List)
    {
        this.mList = List;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.grid_item, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final AllBookModels.ReturnClazz.AllBook allBook = mList.get(position);

        if(allBook.LastChapter == null) return;
            holder.tv_lastChapter.setText(allBook.LastChapter.Title);
        holder.tv_time.setText(allBook.LastChapter.RefreshTimeStr);
        holder.tv_title.setText(allBook.Title + "  " + allBook.LastChapter.ChapterNo + " 话");
        holder.tv_explain.setText(allBook.Explain);

        Glide.with(mContext).load(allBook.LastChapter.FrontCover).centerCrop().into(holder.iv_cover);

        if(SPUtils.getObject(MosuePaintAppication.UserInfo.email+"num"+allBook.Id,String.class,"-1").equals(allBook.LastChapter.ChapterNo))
        {
            holder.iv_cover.setTagEnable(false);
        }else
        {
            holder.iv_cover.setTagEnable(true);
        }

        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                handClick(HttpUrl.URL_IMG_CHAPTER + allBook.LastChapter.Id, allBook.LastChapter.Title);

                SPUtils.saveObject(MosuePaintAppication.UserInfo.email + "num" +allBook.Id, allBook.LastChapter.ChapterNo);

            }
        });
        if(SPUtils.getObject(MosuePaintAppication.UserInfo.email+"id"+allBook.Id,String.class,"-1").equals(allBook.Id))
        {
            holder.tv_item_subscribe.setText(allBook.Author);
        }else
        {
            holder.tv_item_subscribe.setText("+订阅");
        }

        holder.tv_item_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppDao.getInstance().subscribeBook(allBook.LastChapter.Id,true,new CallbackListener<SubscribeModel>()
                {
                    @Override
                    public void onStringResult(String result) {
                        super.onStringResult(result);
                        Log.i("sub","res------>"+result);
                    }

                    @Override
                    public void onSuccess(SubscribeModel result) {
                        super.onSuccess(result);
                        Toast.makeText(mContext,"您的喜欢，"+allBook.Author+"已经知道了",Toast.LENGTH_SHORT).show();
                        SPUtils.saveObject(MosuePaintAppication.UserInfo.email + "id" + allBook.Id, allBook.Id);
                        holder.tv_item_subscribe.setText(allBook.Author);
                        holder.favor.addFavor();
                    }

                    @Override
                    public void onError(Exception e) {
                        super.onError(e);
                        Toast.makeText(mContext,"订阅失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }



    private void handClick(String url,String title) {
        Intent intent = new Intent(mContext, WebActivity.class);
        intent.putExtra(WebActivity.EXTRA_URL, url);
        intent.putExtra(WebActivity.EXTRA_TITLE,title);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        @Bind(R.id.iv_cover)
        SimpleTagImageView iv_cover;
        @Bind(R.id.title_LastChapter)
        TextView tv_lastChapter;
        @Bind(R.id.tv_item_title)
        TextView tv_title;
        @Bind(R.id.tv_item_time)
        TextView tv_time;
        @Bind(R.id.tv_item_explain)
        TextView tv_explain;
        @Bind(R.id.ll_item)
        LinearLayout ll_item;
        @Bind(R.id.tv_item_subscribe)
        TextView tv_item_subscribe;
        @Bind(R.id.favor)
        FavorLayout favor;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


    }
}
