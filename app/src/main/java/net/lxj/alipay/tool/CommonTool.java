package net.lxj.alipay.tool;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.lxj.alipay.model.entity.MySection;
import net.lxj.alipay.cache.SharedPref;
import net.lxj.alipay.model.ApplicationImageModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 工具类
 */
public class CommonTool {

    public static Bitmap getImageFromAssetsFile(String fileName, Context context) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;

    }

    public static List<ApplicationImageModel> getStringlist(List<ApplicationImageModel> list) {
            List<ApplicationImageModel> myapp = new ArrayList<>();
        for (ApplicationImageModel myapps :list) {
            if(myapps.getApplication()!=111) {
                myapp.add(myapps);
            }
        }
        return myapp;
    }

    public static Boolean getEqual(List<ApplicationImageModel> list, String name, Context context) {

        return new Gson().toJson(list).equals(SharedPref.getInstance(context).getString(name, ""));
    }


    //在图标没有满足7个（自定义）的情况下有一个加号图标在列表中
    public static List<ApplicationImageModel> getVideo(List<ApplicationImageModel> list) {

        list.add(new ApplicationImageModel("111", ""));
        return list;
    }

    //获取个人选择的应用图标列表
    public static List<Bitmap> getImage(Context context, List<ApplicationImageModel> model) {
        List<Bitmap> list = new ArrayList<>();
        for (int i = 0; i < model.size(); i++) {
            list.add(getImageFromAssetsFile(model.get(i).getMipmap(), context));
        }
        return list;
    }

    //获取所有的二级应用图标
    public static List<MySection> getSampleData(Context context, List<ApplicationImageModel> data) {
        Type listType = new TypeToken<List<ApplicationImageModel>>() {
        }.getType();
        List<ApplicationImageModel> nodel = new Gson().fromJson(getJson("applicationjson.json", context), listType);
        //对于已经选择的图标应用给定不同的select
        //对于图标的位置就是图标的application的数值这里这样设置不用遍历整个图标列表提高效率
        if (data.size() != 0) {
            for (int i = 0; i < data.size(); i++) {
                nodel.set(data.get(i).getApplication(), data.get(i));
            }
        }
        List<MySection> list = new ArrayList<>();
        list.add(new MySection(true, "医疗服务", false));
        for (int i = 0; i < 7; i++) {
            ApplicationImageModel video = new ApplicationImageModel();
            video.setApplication(nodel.get(i).getApplication());
            video.setMipmap(nodel.get(i).getMipmap());
            video.setContent(nodel.get(i).getContent());
            video.setSelect(nodel.get(i).isSelect());
            list.add(new MySection(video));
        }

        list.add(new MySection(true, "医疗保障", false));
        for (int j = 7; j < 10; j++) {
            ApplicationImageModel video = new ApplicationImageModel();
            video.setApplication(nodel.get(j).getApplication());
            video.setMipmap(nodel.get(j).getMipmap());
            video.setContent(nodel.get(j).getContent());
            video.setSelect(nodel.get(j).isSelect());
            list.add(new MySection(video));
        }
        list.add(new MySection(true, "公共卫生服务", false));
        for (int k = 10; k < 18; k++) {
            ApplicationImageModel video = new ApplicationImageModel();
            video.setApplication(nodel.get(k).getApplication());
            video.setMipmap(nodel.get(k).getMipmap());
            video.setContent(nodel.get(k).getContent());
            video.setSelect(nodel.get(k).isSelect());
            list.add(new MySection(video));
        }
        list.add(new MySection(true, "计生服务", false));
        for (int k = 18; k < 22; k++) {
            ApplicationImageModel video = new ApplicationImageModel();
            video.setApplication(nodel.get(k).getApplication());
            video.setMipmap(nodel.get(k).getMipmap());
            video.setContent(nodel.get(k).getContent());
            video.setSelect(nodel.get(k).isSelect());
            list.add(new MySection(video));
        }
        list.add(new MySection(true, "药品管理", false));
        for (int k = 22; k < 23; k++) {
            ApplicationImageModel video = new ApplicationImageModel();
            video.setApplication(nodel.get(k).getApplication());
            video.setMipmap(nodel.get(k).getMipmap());
            video.setContent(nodel.get(k).getContent());
            video.setSelect(nodel.get(k).isSelect());
            list.add(new MySection(video));
        }
        list.add(new MySection(true, "综合管理", false));
        for (int k = 23; k < 28; k++) {
            ApplicationImageModel video = new ApplicationImageModel();
            video.setApplication(nodel.get(k).getApplication());
            video.setMipmap(nodel.get(k).getMipmap());
            video.setContent(nodel.get(k).getContent());
            video.setSelect(nodel.get(k).isSelect());
            list.add(new MySection(video));
        }
        return list;
    }


    public static String getJson(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static List<ApplicationImageModel> getTopData(String data, Context context) {

        List<ApplicationImageModel> datas = new ArrayList<>();
        if (TextUtils.isEmpty(data)) {
            datas.add(new ApplicationImageModel("111", ""));
            return datas;
        }
        Type listType = new TypeToken<List<ApplicationImageModel>>() {
        }.getType();
        datas = new Gson().fromJson(data.toString(), listType);
        ApplicationImageModel applicationImageModel = new ApplicationImageModel();
        applicationImageModel.setApplication(111);
        datas.add(applicationImageModel);
        return datas;
    }

    public static List<ApplicationImageModel> getList(List<ApplicationImageModel> list) {

        List<ApplicationImageModel> newlist = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i++) {
            ApplicationImageModel applicationImageModel = new ApplicationImageModel();
            applicationImageModel.setApplication(list.get(i).getApplication());
            applicationImageModel.setContent(list.get(i).getContent());
            applicationImageModel.setMipmap(list.get(i).getMipmap());
            applicationImageModel.setSelect(list.get(i).isSelect());
            newlist.add(applicationImageModel);
        }
        return newlist;
    }


    public static List<ApplicationImageModel> getdatas(Context context) {
        List<ApplicationImageModel> datas = new ArrayList<>();
        Type listType = new TypeToken<List<ApplicationImageModel>>() {
        }.getType();
        List<ApplicationImageModel> nodelss = new Gson().fromJson(CommonTool.getJson("applicationjson.json", context), listType);
        for (int k = 0; k < 7; k++) {
            ApplicationImageModel video = new ApplicationImageModel();
            video.setSelect(true);
            video.setMipmap(nodelss.get(k).getMipmap());
            video.setApplication(nodelss.get(k).getApplication());
            video.setContent(nodelss.get(k).getContent());
            datas.add(video);
        }
        return datas;
    }

    public static List<ApplicationImageModel> addFooter( List<ApplicationImageModel> datas){
        ApplicationImageModel applicationImageModel = new ApplicationImageModel();
        applicationImageModel.setApplication(111);
        applicationImageModel.setSelect(true);
        datas.add(applicationImageModel);

        return datas;
    }
}
