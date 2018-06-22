package net.lxj.alipay.model;

/**
 * Created by Administrator on 2017/12/18 0018.
 */

public class ApplicationImageModel  {

    /**
     * application : 10000
     * mipmap : icon_appointment.png
     * content : 预约挂号
     */

    private int application;
    private String mipmap;
    private String content;
    public boolean isSelect;
    public ApplicationImageModel(String img, String name) {
        this.mipmap = img;
        this.content = name;
    }

    public ApplicationImageModel(){

    }
    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getApplication() {
        return application;
    }

    public void setApplication(int application) {
        this.application = application;
    }

    public String getMipmap() {
        return mipmap;
    }

    public void setMipmap(String mipmap) {
        this.mipmap = mipmap;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
