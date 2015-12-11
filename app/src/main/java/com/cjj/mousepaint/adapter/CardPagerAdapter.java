package com.cjj.mousepaint.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.cjj.mousepaint.fragment.CardFragment;
import com.cjj.mousepaint.model.Card;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CardPagerAdapter extends FragmentStatePagerAdapter {
    private List<Card> mPostList;
    private List<Fragment> mFragments = new ArrayList();

    public CardPagerAdapter(FragmentManager paramFragmentManager, List<Card> paramList) {
        super(paramFragmentManager);
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext()) {
            Card localAppModel = (Card) localIterator.next();
            this.mFragments.add(CardFragment.getInstance(localAppModel));
        }
        this.mPostList = paramList;
    }

    public void addCardList(List<Card> cardList) {
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = cardList.iterator();
        while (localIterator.hasNext())
            localArrayList.add(CardFragment.getInstance((Card) localIterator.next()));
        if (this.mFragments == null)
            this.mFragments = new ArrayList();
        this.mFragments.addAll(localArrayList);
        this.mPostList.addAll(cardList);
    }

    public List<Card> getCardList() {
        return this.mPostList;
    }

    public int getCount() {
        return this.mFragments.size();
    }

    public List<Fragment> getFragments() {
        return this.mFragments;
    }

    public Fragment getItem(int paramInt) {
        return this.mFragments.get(paramInt);
    }

    public void setCardList(List<Card> cardList) {
        ArrayList localArrayList = new ArrayList();
        Iterator localIterator = cardList.iterator();
        while (localIterator.hasNext())
            localArrayList.add(CardFragment.getInstance((Card) localIterator.next()));
        this.mFragments = localArrayList;
        this.mPostList = cardList;
    }

    public void setFragments(List<Fragment> paramList) {
        this.mFragments = paramList;
    }
}