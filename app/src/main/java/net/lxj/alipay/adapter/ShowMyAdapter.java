package net.lxj.alipay.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.lxj.alipay.R;
import net.lxj.alipay.model.ApplicationImageModel;
import net.lxj.alipay.tool.CommonTool;

import java.util.List;

public class ShowMyAdapter extends BaseQuickAdapter<ApplicationImageModel, BaseViewHolder> {
    private Context context;

    public ShowMyAdapter(int layoutResId, @Nullable List<ApplicationImageModel> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ApplicationImageModel item) {
        helper.getView(R.id.setting).setVisibility(View.GONE);
        if (item.getApplication() == 111) {
            helper.setText(R.id.service_text, "更多");
            helper.setImageResource(R.id.pic_service, R.drawable.load_more);
        } else {
            try {
                helper.setText(R.id.service_text, item.getContent());
                helper.setImageBitmap(R.id.pic_service, CommonTool.getImageFromAssetsFile(item.getMipmap(), context));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
