package com.example.zero.pixivclient.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.zero.pixivclient.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String[] tabTitles = new String[]{"今日", "本周",
            "本月", "新人", "原创", "男性人气作品", "女性人气作品"};
    private static final String[] tabIds = new String[]{"daily", "weekly",
            "monthly", "rookie", "original", "male", "female"};

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.indicator);  //修改返回箭头 ←
        ab.setDisplayHomeAsUpEnabled(true);      //显示图标 ←

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        final TextView textView = (TextView)findViewById(R.id.text);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab2.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab2.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
            }
        });

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabs);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        if (mViewPager != null) {
            setupViewPager(mViewPager);
            mTabLayout.setupWithViewPager(mViewPager);   //将TabLayout和ViewPager关联起来。
        }
    }

    private void setupViewPager(ViewPager mViewPager) {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        int mCount = tabTitles.length;
        for (int i = 0; i < mCount; i++) {
            adapter.addFragment(PageSectionFragment.newInstance(tabIds[i]), tabTitles[i]);
        }

        mViewPager.setAdapter(adapter);
    }

    public class PagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
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
        switch (id) {
            case R.id.action_statement:
                Snackbar.make(mToolbar, "本APP仅为学习开发使用,所有图片抓取自http://www.pixiv.net/,版权归原作者所有", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                break;

            case R.id.action_about:
                Snackbar.make(mToolbar, "Any suggestions,  Email to: 623368886@qq.com", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}