package ren.solid.ganhuoio.module.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SpinnerAdapter;

import ren.solid.ganhuoio.R;
import ren.solid.library.activity.base.BaseActivity;
import ren.solid.library.utils.SnackBarUtils;

/**
 * Created by _SOLID
 * Date:2016/11/22
 * Time:10:08
 * Desc:
 */

public class SearchActivity extends BaseActivity {

    private Toolbar mToolbar;
    private String mKeyWord;
    private SearchResultListFragment mSearchResultListFragment;
    private EditText mKeywordText;
    private String[] mCategorys;
    private String mSelectCategory;

    public static void start(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_search;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void setUpView() {
        mKeywordText = $(R.id.et_keyword);
        mToolbar = $(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//决定左上角的图标是否可以点击
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//决定左上角图标的右侧是否有向左的小箭头
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    protected void setUpData() {
        mCategorys = getResources().getStringArray(R.array.search_category);
        mSelectCategory = mCategorys[0];
        SpinnerAdapter adapter = ArrayAdapter.createFromResource(this, R.array.search_category,
                android.R.layout.simple_spinner_dropdown_item);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getSupportActionBar().setListNavigationCallbacks(adapter, new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                mSelectCategory = mCategorys[itemPosition];
                return true;
            }
        });
        mSearchResultListFragment = SearchResultListFragment.newInstance("android");
        mSearchResultListFragment.setUserVisibleHint(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_result, mSearchResultListFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {

            mKeyWord = mKeywordText.getText().toString();
            if (!TextUtils.isEmpty(mKeyWord)) {
                mSearchResultListFragment.refresh(mSelectCategory, mKeyWord);
            } else {
                SnackBarUtils.makeShort(mToolbar, getString(R.string.tips_keyword_cannot_null)).info();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
