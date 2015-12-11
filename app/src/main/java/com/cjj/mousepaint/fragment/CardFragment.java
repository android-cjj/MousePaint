package com.cjj.mousepaint.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjj.mousepaint.DetialActivity;
import com.cjj.mousepaint.R;
import com.cjj.mousepaint.customview.HeartLayout;
import com.cjj.mousepaint.customview.HtmlTextView;
import com.cjj.mousepaint.model.Card;
import com.cjj.mousepaint.utils.AppUtils;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class CardFragment extends AbsBaseFragment implements View.OnClickListener{
    protected Card mCard;
    protected TextView mAuthorText;
    protected ImageView mBottomEdgeImageView;
    protected TextView mBravoNumText;
    protected RelativeLayout mCardLayout;
    protected ImageView mCoverImageView;
    protected HtmlTextView mDigestText;
    protected TextView mSubTitleText;
    protected TextView mTitleText;
    public static CardFragment getInstance(Card card) {
        CardFragment localCardFragment = new CardFragment();
        Bundle localBundle = new Bundle();
        localBundle.putSerializable("card", card);
        localCardFragment.setArguments(localBundle);
        return localCardFragment;
    }



    protected View initViews(LayoutInflater paramLayoutInflater) {
        View view = paramLayoutInflater.inflate(R.layout.fragment_card, null);
        mCardLayout = ((RelativeLayout) view.findViewById(R.id.box_card));
        mCardLayout.setOnClickListener(this);
        mBottomEdgeImageView = ((ImageView) view.findViewById(R.id.image_bottom_edge));
        mCoverImageView = ((ImageView) view.findViewById(R.id.image_cover));
        mTitleText = ((TextView) view.findViewById(R.id.text_title));
        mSubTitleText = ((TextView) view.findViewById(R.id.text_subtitle));
        mDigestText = ((HtmlTextView) view.findViewById(R.id.text_digest));
        mAuthorText = ((TextView) view.findViewById(R.id.text_author));
        mBravoNumText = ((TextView) view.findViewById(R.id.text_bravos));

        mTitleText.setText(this.mCard.getTitle());
        mSubTitleText.setText(this.mCard.getSubTitle());
        this.mBravoNumText.setText("No." + this.mCard.getUpNum());
        this.mDigestText.setTextViewHtml(mCard.getDigest());
        this.mAuthorText.setText(Html.fromHtml("<B>" + this.mCard.getAuthorName() + "</B>"));
        initAndDisplayCoverImage();



        return view;
    }

    protected void initAndDisplayCoverImage() {
        int coverWidth = AppUtils.getScreenDisplayMetrics(getActivity()).widthPixels - 2 * getResources().getDimensionPixelSize(R.dimen.card_margin);
        int coverHeight = (int) (180.0F * (coverWidth / 320.0F));
        ViewGroup.LayoutParams localLayoutParams = this.mCoverImageView.getLayoutParams();
        localLayoutParams.height = Float.valueOf(coverHeight).intValue();

        Glide.with(getActivity()).load(mCard.getCoverImgerUrl()).centerCrop().into(mCoverImageView);
    }

    protected void initData() {
        this.mCard = (Card) getArguments().getSerializable("card");
    }

    protected void initActions(View paramView) {
    }



    public void onDestroy() {
        this.mCoverImageView.setImageBitmap(null);
        super.onDestroy();
    }

    public void onDestroyView() {
        this.mCoverImageView.setImageBitmap(null);
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.box_card:
                Intent intent = new Intent(
                        getActivity(), DetialActivity.class);
                intent.putExtra("bookId",mCard.getId());
                startActivity(intent);
                break;
        }
    }
}