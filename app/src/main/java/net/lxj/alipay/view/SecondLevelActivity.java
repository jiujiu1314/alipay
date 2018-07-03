package net.lxj.alipay.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.google.gson.Gson;

import net.lxj.alipay.model.ApplicationImageModel;
import net.lxj.alipay.tool.CommonTool;
import net.lxj.alipay.adapter.MyAdapter;
import net.lxj.alipay.model.entity.MySection;
import net.lxj.alipay.adapter.MysApplicationAdapter;
import net.lxj.alipay.adapter.MysectionAdapter;
import net.lxj.alipay.R;
import net.lxj.alipay.TopLayoutManager;
import net.lxj.alipay.cache.SharedPref;

import java.util.ArrayList;
import java.util.List;

/**
 * 二级页面
 */
public class SecondLevelActivity extends AppCompatActivity implements OnItemDragListener {
    private static final int MYLIVE_MODE_CHECK = 0;//未编辑状态
    private static final int MYLIVE_MODE_EDIT = 1;//编辑状态
    private static final String TAG = "SecondLevelActivity";
    private boolean editorStatus = false;//状态
    private int mEditMode = MYLIVE_MODE_CHECK;//默认为未编辑的状态
    private ItemTouchHelper mItemTouchHelper;
    private ItemDragAndSwipeCallback mItemDragAndSwipeCallback;

    private MyAdapter myAdapter;//
    private MysApplicationAdapter mysApplicationAdapter;
    private MysectionAdapter mysectionAdapter;
    List<Bitmap> bitmapList = new ArrayList<>();
    List<MySection> mySections = new ArrayList<>();
    List<ApplicationImageModel> list = new ArrayList<>();
    private int index = 0;


    RecyclerView recyclerViewMy, recyclerViewTitle, recyclerViewContent;
    TextView rightButton;
    ImageView backicon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_application_layout);
        recyclerViewMy = findViewById(R.id.recycle_my);
        recyclerViewTitle = findViewById(R.id.recycle_title_content);
        recyclerViewContent = findViewById(R.id.recycle_content);
        rightButton = findViewById(R.id.right_button);
        backicon = findViewById(R.id.back_department_re);
        try {
            Log.d(TAG,SharedPref.getInstance(this).getString("myapplication","222"));

            initView();
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void initView() throws Exception {
        rightButton.setVisibility(View.VISIBLE);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updataEditMode();
            }
        });
        backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setfinsh();
            }
        });
        //第一个列表
        final TopLayoutManager manager1 = new TopLayoutManager(this, 4);
        //第二个列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewMy.setLayoutManager(linearLayoutManager);
        recyclerViewContent.setLayoutManager(manager1);



        //第三个列表
        final GridLayoutManager manager = new GridLayoutManager(this, 4);
        recyclerViewTitle.setLayoutManager(manager);
        getChooseAdapter();
        //可拖拽实现
        mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(myAdapter);
        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerViewTitle);
//        getTopAdapter();
        myAdapter.enableDragItem(mItemTouchHelper);
        myAdapter.setOnItemDragListener(this);
//        recyclerViewTitle.setAdapter(myAdapter);
    }

    private void initData() {
        list =CommonTool.addFooter(CommonTool.getdatas(this));
        mySections = CommonTool.getSampleData(this,CommonTool.getList(list));
        bitmapList = CommonTool.getImage(this,CommonTool.getList(list));
        index = list.size()-1;
        recyclerViewTitle.setVisibility(View.GONE);
        recyclerViewMy.setVisibility(View.VISIBLE);
        recyclerViewContent.setVisibility(View.VISIBLE);
        recyclerViewTitle.setHasFixedSize(true);
        getContentAdapter().setNewData(mySections);
        getChooseAdapter().setNewData(list);
        getTopAdapter().setNewData(bitmapList);
    }

    private BaseQuickAdapter getTopAdapter() {
        if (mysApplicationAdapter == null) {
            mysApplicationAdapter = new MysApplicationAdapter(R.layout.item_image, bitmapList);
            recyclerViewMy.setAdapter(mysApplicationAdapter);
        }

        return mysApplicationAdapter;
    }

    private BaseQuickAdapter getContentAdapter() {

        if (mysectionAdapter == null) {
            mysectionAdapter = new MysectionAdapter(R.layout.item_content, R.layout.item_head, mySections, this);
            recyclerViewContent.setAdapter(mysectionAdapter);
        }
        mysectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                try {
                    ApplicationImageModel video = mySections.get(position).t;
                    if (editorStatus) {
                        //这里是编辑状态的点击事件
                        boolean isSelect = video.isSelect();
//                        if(view.getId() == R.id.setting)
                        if (!isSelect) {
                            if (index < 7) {
                                index++;
                                video.setSelect(true);
                                mySections.get(position).t.setSelect(true);
                                list.add(list.size()-1,video);
                                getChooseAdapter().setNewData(list);
                                Log.d("tag", "" + position);
                            } else {
                                Toast.makeText(SecondLevelActivity.this, "应用最多只能添加7个", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            mySections.get(position).t.setSelect(false);
                            for (int i = 0; i < list.size() - 1; i++) {
                                if (mySections.get(position).t.getApplication() == list.get(i).getApplication()) {
                                    list.remove(i);
                                    index--;
                                }
                            }

                        }
                        getContentAdapter().notifyDataSetChanged();
                        getChooseAdapter().notifyDataSetChanged();
                    } else {
                        //这里是未编辑状态你的点击事件
                        Toast.makeText(SecondLevelActivity.this, mysectionAdapter.getData().get(position).t.getContent(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        return mysectionAdapter;
    }

    private BaseItemDraggableAdapter getChooseAdapter() {
        if (myAdapter == null) {
            myAdapter = new MyAdapter(R.layout.item_content, list, this);
            recyclerViewTitle.setAdapter(myAdapter);
        }
        myAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                try {
                    ApplicationImageModel video = list.get(position);
//                    if(view.getId() == R.id.setting)
                    if (editorStatus) {
                        if (video.isSelect()) {
                            list.remove(position);
                            getChooseAdapter().setNewData(list);
                            mySections = CommonTool.getSampleData(SecondLevelActivity.this,CommonTool.getList(list));
                            index--;
                            getContentAdapter().setNewData(mySections);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return myAdapter;
    }

    private void updataEditMode() {

        mEditMode = mEditMode == MYLIVE_MODE_CHECK ? MYLIVE_MODE_EDIT : MYLIVE_MODE_CHECK;
        if (mEditMode == MYLIVE_MODE_EDIT) {
            rightButton.setText("完成");
            recyclerViewTitle.setVisibility(View.VISIBLE);
            recyclerViewMy.setVisibility(View.GONE);
            editorStatus = true;
            mysectionAdapter.setEditMode(mEditMode);
        } else {
            if (editorStatus) {
                if (CommonTool.getEqual(CommonTool.getList(list), "myapplication", this)) {
                    recyclerViewTitle.setVisibility(View.GONE);
                    recyclerViewMy.setVisibility(View.VISIBLE);
                    rightButton.setText("自定义");
                    editorStatus = false;
                    mysectionAdapter.setEditMode(mEditMode);
                } else {
                    sendChange();
                }
            }
        }

    }

    private void sendChange() {
        recyclerViewTitle.setVisibility(View.GONE);
        recyclerViewMy.setVisibility(View.VISIBLE);
        rightButton.setText("自定义");
        editorStatus = false;
        SharedPref.getInstance(this).putString("myapplication", new Gson().toJson(CommonTool.getList(list)));
        Toast.makeText(this, "编辑成功", Toast.LENGTH_SHORT).show();
        mEditMode = MYLIVE_MODE_CHECK;
        mysectionAdapter.setEditMode(mEditMode);
    }

    //展示弹窗
    private void showDialog() {
        final MaterialDialog dialog =
                new MaterialDialog.Builder(this)
                        .customView(R.layout.outland_layout, true)
                        .build();
        TextView tips = (TextView) dialog.findViewById(R.id.text_tips);
        final TextView cancle = (TextView) dialog.findViewById(R.id.cancle);
        TextView confirm = (TextView) dialog.findViewById(R.id.confirm);
        tips.setText("是否保存已编辑的内容");
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendChange();
                dialog.dismiss();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewTitle.setVisibility(View.GONE);
                recyclerViewMy.setVisibility(View.VISIBLE);
                rightButton.setText("自定义");
                editorStatus = false;
                mEditMode = MYLIVE_MODE_CHECK;
                mysectionAdapter.setEditMode(mEditMode);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setfinsh();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setfinsh() {
        if (editorStatus) {
            if (!CommonTool.getEqual(CommonTool.getList(list), "myapplication", this)) {
                showDialog();
            } else {
                recyclerViewTitle.setVisibility(View.GONE);
                recyclerViewMy.setVisibility(View.VISIBLE);
                rightButton.setText("自定义");
                editorStatus = false;
                mEditMode = MYLIVE_MODE_CHECK;
                mysectionAdapter.setEditMode(mEditMode);
            }
        } else {
            setResult(300);
            finish();
        }
    }

    @Override
    public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {

    }

    @Override
    public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

    }

    @Override
    public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {

    }
}
