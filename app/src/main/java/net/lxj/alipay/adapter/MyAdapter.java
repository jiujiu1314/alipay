package net.lxj.alipay.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.lxj.alipay.R;
import net.lxj.alipay.model.ApplicationImageModel;
import net.lxj.alipay.tool.CommonTool;

import java.util.List;

public class MyAdapter extends BaseItemDraggableAdapter<ApplicationImageModel,BaseViewHolder> {
    private Context context;
    public MyAdapter(int layoutResId, @Nullable List<ApplicationImageModel> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }
    @Override
    protected void convert(BaseViewHolder helper, ApplicationImageModel item) {
        helper.setImageResource(R.id.setting, R.drawable.delet);
        helper.setText(R.id.service_text,item.getContent());
        try {
//        helper.set(R.id.pic_service, DataSample.getJson(video.getImg(), context));
            if(item.getApplication()==111){
                helper.getView(R.id.setting).setVisibility(View.GONE);
                helper.getView(R.id.re_background).setVisibility(View.GONE);
                if(getData().size() < 8) {
                    helper.getView(R.id.coupon_popup).setVisibility(View.VISIBLE);
                }else {
//                    helper.getView(R.id.re_background).setVisibility(View.GONE);
                    helper.getView(R.id.coupon_popup).setVisibility(View.GONE);
                }
//            helper.setImageBitmap(R.id.pic_service, DataSample.getImageFromAssetsFile(item.getMipmap(),context));
            }else {
                helper.getView(R.id.re_background).setVisibility(View.VISIBLE);
                helper.getView(R.id.coupon_popup).setVisibility(View.GONE);
                helper.setImageBitmap(R.id.pic_service, CommonTool.getImageFromAssetsFile(item.getMipmap(),context));
            }

        }catch (Exception e){
          e.printStackTrace();
        }
        helper.addOnClickListener(R.id.setting);
    }
    }

