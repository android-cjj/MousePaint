package com.cjj.mousepaint;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.cjj.mousepaint.constants.Constant;
import com.cjj.mousepaint.fragment.CardViewPagerFragment;
import com.cjj.mousepaint.fragment.CategoryFragment;
import com.cjj.mousepaint.fragment.HomeFragment;
import com.cjj.mousepaint.fragment.SubscribeFragment;
import com.cjj.mousepaint.utils.ScreenUtil;
import com.cjj.mousepaint.utils.StatusBarCompat;
import com.cjj.mousepaint.utils.VersionUtil;
import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.BlurEffect;
import com.mingle.sweetpick.Delegate;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import io.codetail.animation.arcanimator.ArcAnimator;
import io.codetail.animation.arcanimator.Side;

public class HomeActivity extends AppCompatActivity implements OnClickListener {

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private String hideTag;

    private HomeFragment mHomeFragment;
    private CategoryFragment mCategoryFragment;
    private SubscribeFragment mSubscribeFragment;
    private SupportAnimator mAnimator;

    public static final String TAG_HOME = "Home";
    public static final String TAG_CATEGORY = "Category";
    public static final String TAG_SUBSCRIBE = "Subscribe";

    private SweetSheet mSweetSheet;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.fl_content_home)
    FrameLayout fl_home;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.card_search)
    CardView mCardView;
    @Bind(R.id.iv_bottom_search)
    ImageView iv_bottom_search;
    @Bind(R.id.edit_text_search)
    EditText edit_text_search;
    @Bind(R.id.view_hide)
    View view_hide;
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;


    private Intent mIntent;

    private boolean isFabHide = false;

    private boolean isCategory = false;

    private boolean isHome = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        StatusBarCompat.compat(this, getResources().getColor(R.color.brownness));
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void setupRecyclerView() {

        ArrayList<MenuEntity> list = new ArrayList<>();
        //添加假数据
        MenuEntity menuEntity1 = new MenuEntity();
        menuEntity1.iconId = R.drawable.recommend;
        menuEntity1.title = "最新推荐";
        MenuEntity menuEntity2 = new MenuEntity();
        menuEntity2.iconId = R.drawable.seven;
        menuEntity2.title = "本周更新";
        MenuEntity menuEntity3 = new MenuEntity();
        menuEntity3.iconId = R.drawable.mouse;
        menuEntity3.title = "所有漫画";
        list.add(menuEntity1);
        list.add(menuEntity2);
        list.add(menuEntity3);

        // SweetSheet 控件,根据 rl 确认位置
        mSweetSheet = new SweetSheet(fl_home);

        //设置数据源 (数据源支持设置 list 数组,也支持从菜单中获取)
        mSweetSheet.setMenuList(list);
        //根据设置不同的 Delegate 来显示不同的风格.
        mSweetSheet.setDelegate(new RecyclerViewDelegate(false));
        //根据设置不同Effect 来显示背景效果BlurEffect:模糊效果.DimEffect 变暗效果
        mSweetSheet.setBackgroundEffect(new BlurEffect(8));
        //设置点击事件
        mSweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
            @Override
            public boolean onItemClick(int position, MenuEntity menuEntity1) {
                showFab();

//                //根据返回值, true 会关闭 SweetSheet ,false 则不会.
//                Toast.makeText(HomeActivity.this, menuEntity1.title + "  " + position, Toast.LENGTH_SHORT).show();
                handClick(position);
                return true;
            }
        });
        mSweetSheet.setBgListener(new Delegate.BgListener() {
            @Override
            public void onClick() {
                closeMenu();
            }
        });

        fab.setOnClickListener(this);

    }

    private void handClick(int position) {
        switch (position) {
            case Constant.RECOMMENT:

                mIntent.setClass(this, RecommendActivity.class);

                break;

            case Constant.WEEK:

                mIntent.setClass(this, WeekActivity.class);

                break;


            case Constant.ALLCOMMIC:
                mNavigationView.setCheckedItem(R.id.nav_category);
                setCategoryFragment();

                return;

        }
        startActivity(mIntent);
    }


    private void init() {
        view_hide.setOnClickListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        if (mNavigationView != null) {
            setupDrawerContent(mNavigationView);
            mNavigationView.setCheckedItem(R.id.nav_home);
        }

        mHomeFragment = HomeFragment.newInstance();
        switchFragment(TAG_HOME, mHomeFragment);

        setupRecyclerView();

        mIntent = new Intent();

        handFabPathAndSearch();
    }

    /**
     * 选择不同的Frament
     *
     * @param tag
     * @param mFragment
     */
    public void switchFragment(String tag, Fragment mFragment) {
        if (hideTag == tag) return;

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        Fragment tagFragment = mFragmentManager.findFragmentByTag(tag);

        if (tagFragment == null) {
            mFragmentTransaction.add(R.id.fl_content_home, mFragment, tag);
        } else {
            mFragmentTransaction.show(tagFragment);
        }

        tagFragment = mFragmentManager.findFragmentByTag(hideTag);

        if (tagFragment != null) {
            mFragmentTransaction.hide(tagFragment);
        }

        hideTag = tag;
        mFragmentTransaction.commit();
    }

    public void setTitle(String str)
    {
        if(toolbar != null)
        {
            toolbar.setTitle(str);
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        // 关闭搜索框、选择器
                        if (view_hide.isShown()) {
                            view_hide.performClick();
                        }
                        closeMenu();

                        switch (menuItem.getItemId()) {
                            case R.id.nav_home:
                                setTitle("最新漫画");
                                handFab(false);
                                fab.setImageResource(R.drawable.ic_add_white_24dp);
                                isHome = true;
                                isCategory = false;

                                if (mHomeFragment == null) {
                                    mHomeFragment = HomeFragment.newInstance();
                                }
                                switchFragment(TAG_HOME, mHomeFragment);
                                break;
                            case R.id.nav_category:

                                setCategoryFragment();

                                break;
                            case R.id.nav_subscribe:
//                                if (mSubscribeFragment == null) {
//                                    mSubscribeFragment = SubscribeFragment.newInstance();
//                                }
                                setTitle("精选漫画");
                                handFab(true);
                                switchFragment(TAG_SUBSCRIBE, new CardViewPagerFragment());
                                break;
                            case R.id.help:
                                startActivity(new Intent(HomeActivity.this,AboutActivity.class));
                                break;
                            case R.id.setting:
                                Toast.makeText(HomeActivity.this,"待开发...",Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(HomeActivity.this,SettingActivity.class));
                                break;
                        }
                        return true;
                    }
                });
    }

    public void setCategoryFragment()
    {
        setTitle("漫画分类");
        isHome = false;
        fab.setImageResource(R.drawable.ic_action_search);
        isCategory = true;
        handFab(false);
        if (mCategoryFragment == null) {
            mCategoryFragment = CategoryFragment.newInstance();
        }
        switchFragment(TAG_CATEGORY, mCategoryFragment);
    }

    public void handFab(boolean isFabHide)
    {
        this.isFabHide = isFabHide;
        animFab();
    }

    private void animFab() {
        if(isFabHide)
        {
            hideFab();
        }else
        {
            showFab();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            closeMenu();
            startActivity(new Intent(HomeActivity.this,AboutActivity.class));
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                closeMenu();
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if(isHome)
                {
                    menuAndBtnAnim();
                }else
                {
                    view_hide.setVisibility(View.VISIBLE);
                    ArcAnimator.createArcAnimator(fab, ScreenUtil.getScreenWidth()/2, ScreenUtil.getStatusHeight()+(toolbar.getHeight()/2), 45.0f, Side.LEFT)
                            .setDuration(500)
                            .start();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mCardView.setVisibility(View.VISIBLE);
                            fab.setVisibility(View.GONE);
                            iv_bottom_search.performClick();
                        }
                    }, 600);


//                    handFabPathAndSearch();
                }
//

//                //set up clipView and coordinates where clipView will move


                break;

            case R.id.view_hide:
                edit_text_search.setText("");
                iv_bottom_search.performClick();
                break;
        }
    }

    private void handFabPathAndSearch() {
        iv_bottom_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnimator != null && !mAnimator.isRunning()) {
                    mAnimator = mAnimator.reverse();
                    float curTranslationX = iv_bottom_search.getTranslationX();
                    final ObjectAnimator animator = ObjectAnimator.ofFloat(iv_bottom_search, "translationX", curTranslationX, 0);
                    animator.setDuration(600);
                    mAnimator.addListener(new SupportAnimator.AnimatorListener() {
                        @Override
                        public void onAnimationStart() {
                            animator.start();
                        }

                        @Override
                        public void onAnimationEnd() {
                            mAnimator = null;
                            fab.setVisibility(View.VISIBLE);
                            mCardView.setVisibility(View.GONE);
                            if (VersionUtil.checkVersionIntMoreThan19()) {
                                ArcAnimator.createArcAnimator(fab, ScreenUtil.getScreenWidth() - fab.getWidth() / 2 - ScreenUtil.dip2px(16), ScreenUtil.getScreenHeight() - fab.getHeight() - ScreenUtil.dip2px(16), 45.0f, Side.LEFT)
                                        .setDuration(500)
                                        .start();
                            } else {
                                ArcAnimator.createArcAnimator(fab, ScreenUtil.getScreenWidth() - fab.getWidth() / 2 - ScreenUtil.dip2px(16), ScreenUtil.getScreenHeight() - fab.getHeight() / 2 - ScreenUtil.dip2px(16), 45.0f, Side.LEFT)
                                        .setDuration(500)
                                        .start();
                            }
                            view_hide.setVisibility(View.GONE);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    searchData();
                                }
                            }, 500);

                        }

                        @Override
                        public void onAnimationCancel() {

                        }

                        @Override
                        public void onAnimationRepeat() {

                        }
                    });
                } else if (mAnimator != null) {
                    mAnimator.cancel();
                    return;
                } else {
                    int cx = mCardView.getRight();
                    int cy = mCardView.getBottom();
                    float curTranslationX = iv_bottom_search.getTranslationX();
                    final ObjectAnimator animator = ObjectAnimator.ofFloat(iv_bottom_search, "translationX", curTranslationX, cx / 2 - ScreenUtil.dip2px(24));
                    animator.setDuration(600);
                    float radius = r(mCardView.getWidth(), mCardView.getHeight());
                    mAnimator = ViewAnimationUtils.createCircularReveal(mCardView, cx / 2, cy - ScreenUtil.dip2px(32), ScreenUtil.dip2px(20), radius);
                    mAnimator.addListener(new SupportAnimator.AnimatorListener() {
                        @Override
                        public void onAnimationStart() {
                            animator.start();
                        }

                        @Override
                        public void onAnimationEnd() {

                        }

                        @Override
                        public void onAnimationCancel() {

                        }

                        @Override
                        public void onAnimationRepeat() {

                        }
                    });
                }

                mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                mAnimator.setDuration(600);
                mAnimator.start();
            }
        });
    }

    private void searchData() {
        String result = edit_text_search.getText().toString().trim();
        edit_text_search.setText("");

        if(!TextUtils.isEmpty(result))
        {
            Intent intent = new Intent(HomeActivity.this,SearchActivity.class);
            intent.putExtra("search",result);
            startActivity(intent);
        }else
        {
//            Toast.makeText(HomeActivity.this, "请输入漫画名字", Toast.LENGTH_SHORT).show();
        }

    }

    public void menuAndBtnAnim() {
        mSweetSheet.toggle();
        if (mSweetSheet.isShow()) {
            hideFab();
        }
    }

    public void showFab() {
        if (fab != null) {
//            ObjectAnimator animatorTX = ObjectAnimator.ofFloat(fab,"translationX",fab.getTranslationX(),0);
////          ObjectAnimator animatorSX = ObjectAnimator.ofFloat(fab, "scaleX", 0, 1);
////          ObjectAnimator animatorSY = ObjectAnimator.ofFloat(fab, "scaleY", 0, 1);
//            ObjectAnimator animatorA = ObjectAnimator.ofFloat(fab,"alpha",0,1);
//            AnimatorSet animSet = new AnimatorSet();
//            animSet.play(animatorA).with(animatorTX);
//            animSet.setDuration(500);
//            animSet.start();
            fab.animate().scaleX(1.0f);
            fab.animate().scaleY(1.0f);
            fab.animate().translationX(0);
        }
    }

    public void hideFab() {
        if (fab != null) {
//            ObjectAnimator animatorTX = ObjectAnimator.ofFloat(fab,"translationX",fab.getTranslationX(),200);
////          ObjectAnimator animatorSX = ObjectAnimator.ofFloat(fab, "scaleX", 1, 0);
////          ObjectAnimator animatorSY = ObjectAnimator.ofFloat(fab, "scaleY", 1,0);
//            ObjectAnimator animatorA = ObjectAnimator.ofFloat(fab,"alpha",1,0);
//            AnimatorSet animSet = new AnimatorSet();
//            animSet.play(animatorA).with(animatorTX);
//            animSet.setDuration(500);
//            animSet.start();
            fab.animate().scaleX(0.1f);
            fab.animate().scaleY(0.1f);
            fab.animate().translationX(200);
        }
    }


    public void closeMenu() {
        if (mSweetSheet.isShow()) {
            showFab();
            mSweetSheet.dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            // 先隐藏搜索框
            if (view_hide.isShown()) {
                view_hide.performClick();
            } else {
                showCloseDialog();
            }
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }

    private void showCloseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("温馨提示");
        builder.setMessage("小伙伴,您确定要离开我吗");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }


    static float r(int a, int b) {
        return (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }
}
