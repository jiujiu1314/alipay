package net.lxj.alipay.adapter;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.lxj.alipay.R;

import java.util.List;

public class MysApplicationAdapter extends BaseQuickAdapter<Bitmap,BaseViewHolder> {
    public MysApplicationAdapter(int layoutResId, @Nullable List<Bitmap> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Bitmap item) {
        helper.setImageBitmap(R.id.my_image,item);
    }
    @Override
    public int getItemCount() {

        return 7;
    }
}
