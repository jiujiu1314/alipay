package net.lxj.alipay.model.entity;

import com.chad.library.adapter.base.entity.SectionEntity;

import net.lxj.alipay.model.ApplicationImageModel;


/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class MySection extends SectionEntity<ApplicationImageModel> {
    private boolean isMore;
    public MySection(boolean isHeader, String header, boolean isMroe) {
        super(isHeader, header);
        this.isMore = isMroe;
    }

    public MySection(ApplicationImageModel t) {
        super(t);
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean mroe) {
        isMore = mroe;
    }

}
