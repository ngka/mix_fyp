package com.router.ViewActivity.ReservationViewActivity.ReservationViewActivity;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.router.Core.Base.Adapter.BaseCommonAdapter2;
import com.router.Core.Base.Adapter.ItemClickListener;
import com.router.Core.Base.Adapter.ViewHolder;
import com.router.Model.PhotoModel;
import com.router.R;
import com.router.Widget.ChooseImageView;

import java.util.List;


/**
 * Description: 圖片適配器
 */
public class ImageAdapter extends BaseCommonAdapter2<PhotoModel> {


    /**
     * 構造方法
     *
     * @param context  Activity上下文
     * @param datas    數據源
     * @param layoutID 布局文件ID
     */
    public ImageAdapter(Activity context, List<PhotoModel> datas, int layoutID) {
        super(context, datas, layoutID);
    }

    public void addModel(PhotoModel model) {
        listDatas.add(model);
        notifyDataSetChanged();
    }

    @Override
    protected void showGetView(ViewHolder holder, ViewGroup parent, final int position) {
        final ChooseImageView mImageView = holder.getView(R.id.image_imageview);
        setImage(mImageView, position);
    }

    private void setImage(ImageView mImageView, int position) {
        final PhotoModel photoModel = (PhotoModel) getItem(position);
        Glide.with(context).load(photoModel.getPath()).error(R.mipmap.common_default_image_icon).into(mImageView);

    }

}
