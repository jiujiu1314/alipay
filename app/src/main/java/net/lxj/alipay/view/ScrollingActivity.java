package net.lxj.alipay.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMSdkConfig;

import net.lxj.alipay.R;
import net.lxj.alipay.adapter.ShowMyAdapter;
import net.lxj.alipay.cache.SharedPref;
import net.lxj.alipay.model.ApplicationImageModel;
import net.lxj.alipay.tool.CommonTool;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 */
public class ScrollingActivity extends AppCompatActivity {
    private static final String TAG = "ScrollingActivity";
    private CollapsingToolbarLayoutState state;
    private RecyclerView recyclerViewist;
    AppBarLayout appBarLayout;
    RelativeLayout toolbarone;
    CollapsingToolbarLayout collapsingToolbarLayout;
    RelativeLayout toolbartwo;
    List<ApplicationImageModel> list = new ArrayList<>();
    private ShowMyAdapter showMyAdapter;

    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    public static final int ACCOUNT_TYPE = 792;
    //sdk appid 由腾讯分配
    public static final int SDK_APPID = 1400001533;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout = findViewById(R.id.app_bar);
        collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        recyclerViewist = findViewById(R.id.recycle_list);
        initIm();
        initView();
        toolbarone = findViewById(R.id.toolbarone);
        toolbartwo = findViewById(R.id.toolbartwo);
        try {
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initIm() {
        //初始化 SDK 基本配置
        TIMSdkConfig config = new TIMSdkConfig(SDK_APPID)
                .enableCrashReport(false)
                .enableLogPrint(true)
                .setLogLevel(TIMLogLevel.DEBUG)
                .setLogPath(Environment.getExternalStorageDirectory().getPath() + "/justfortest/");

//初始化 SDK
        TIMManager.getInstance().init(getApplicationContext(), config);
    }

    private void initData() throws Exception {
        list = CommonTool.addFooter(CommonTool.getdatas(this));//初始化默认展示7个
        getAdaptermy().setNewData(list);
        SharedPref.getInstance(this).putString("myapplication", new Gson().toJson(CommonTool.getdatas(this)));
    }

    private void initView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        recyclerViewist.setLayoutManager(gridLayoutManager);
        recyclerViewist.setNestedScrollingEnabled(false);
        state = CollapsingToolbarLayoutState.EXPANDED;
        collapsingToolbarLayout.setTitleEnabled(false);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    //全展开
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                        toolbartwo.setVisibility(View.GONE);
                        toolbarone.setVisibility(View.VISIBLE);

                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                        toolbarone.setVisibility(View.GONE);
                        toolbartwo.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                            //由折叠变为中间状态时隐藏播放按钮
                        }
                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                        toolbartwo.setVisibility(View.GONE);
                        toolbarone.setVisibility(View.VISIBLE);
                    }
                }
            }

        });
    }


    private BaseQuickAdapter getAdaptermy() throws Exception {
        if (showMyAdapter == null) {
            showMyAdapter = new ShowMyAdapter(R.layout.item_content, list, this);
            recyclerViewist.setAdapter(showMyAdapter);
        }
        showMyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (showMyAdapter.getData().get(position).getApplication() == 111) {
                    //点击更多跳转
                    Intent intent = new Intent();
                    intent.setClass(ScrollingActivity.this, SecondLevelActivity.class);
                    startActivityForResult(intent, 100);
                } else {
                    //应用跳转
                    Toast.makeText(ScrollingActivity.this, showMyAdapter.getData().get(position).getContent(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return showMyAdapter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 300 && CommonTool.getTopData(SharedPref.getInstance(this).getString("myapplication", ""), this).size() != 1) {
            showMyAdapter.getData().clear();
            showMyAdapter.notifyDataSetChanged();
            Log.d(TAG, SharedPref.getInstance(this).getString("myapplication", "222"));
            list = CommonTool.getTopData(SharedPref.getInstance(this).getString("myapplication", ""), this);
            showMyAdapter.setNewData(list);
        }
    }
}
