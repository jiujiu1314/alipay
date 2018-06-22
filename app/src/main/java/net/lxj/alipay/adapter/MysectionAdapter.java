package net.lxj.alipay.adapter;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.lxj.alipay.tool.CommonTool;
import net.lxj.alipay.model.entity.MySection;
import net.lxj.alipay.R;
import net.lxj.alipay.model.ApplicationImageModel;

import java.util.List;

public class MysectionAdapter extends BaseSectionQuickAdapter<MySection, BaseViewHolder> {
    private Context context;
    private static final int MYLIVE_MODE_CHECK = 0;
    int mEditMode = MYLIVE_MODE_CHECK;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public MysectionAdapter(int layoutResId, int sectionHeadResId, List<MySection> data, Context context) {
        super(layoutResId, sectionHeadResId, data);
        this.context = context;
    }

    @Override
    protected void convertHead(BaseViewHolder helper, MySection item) {
        helper.setText(R.id.header, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, MySection item) {
        final ApplicationImageModel video = item.t;
        try {
            if (mEditMode == MYLIVE_MODE_CHECK) {
                helper.getView(R.id.setting).setVisibility(View.GONE);
                helper.getView(R.id.re_background).setBackgroundResource(R.color.white);
            } else {
                helper.getView(R.id.setting).setVisibility(View.VISIBLE);
                helper.getView(R.id.re_background).setBackgroundResource(R.color.back_groud);
                if (video.isSelect()) {
                    helper.setImageResource(R.id.setting, R.drawable.choose);
                } else {
                    helper.setImageResource(R.id.setting, R.drawable.adds);
                }
            }
            helper.setImageBitmap(R.id.pic_service, CommonTool.getImageFromAssetsFile(video.getMipmap(), context));
//            Glide.with(context).load(CommonTool.getImageFromAssetsFile(video.getMipmap(), context)).into(R.id.pic_service);
            helper.setText(R.id.service_text, video.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }
}
