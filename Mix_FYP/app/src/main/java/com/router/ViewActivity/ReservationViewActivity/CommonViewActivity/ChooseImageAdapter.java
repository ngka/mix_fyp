package com.router.ViewActivity.ReservationViewActivity.CommonViewActivity;

import android.app.Activity;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
public class ChooseImageAdapter extends BaseCommonAdapter2<PhotoModel> {

    private ItemClickListener<PhotoModel> delListener;
    private ItemClickListener addListener;

    public void setDelListener(ItemClickListener<PhotoModel> delListener) {
        this.delListener = delListener;
    }

    public void setAddListener(ItemClickListener<PhotoModel> addListener) {
        this.addListener = addListener;
    }

    /**
     * 構造方法
     *
     * @param context  Activity上下文
     * @param datas    數據源
     * @param layoutID 布局文件ID
     */
    public ChooseImageAdapter(Activity context, List<PhotoModel> datas, int layoutID) {
        super(context, datas, layoutID);
    }

    public void addModel(PhotoModel model) {
        listDatas.add(model);
        notifyDataSetChanged();
    }

    private String getPhotoPath(PhotoModel photoModel) {
        String filePath = null;
        if (photoModel != null) {
            filePath = photoModel.getPath();
        }
        return filePath;
    }

    public List<PhotoModel> getPhotoList() {
        return listDatas;
    }

    @Override
    protected void showGetView(ViewHolder holder, ViewGroup parent, final int position) {
        final ChooseImageView mImageView = holder.getView(R.id.image_imageview);
        final ImageView mImageDel = holder.getView(R.id.img_del);
        mImageView.setOnClickListener(null);
        if (listDatas == null || listDatas.size() == 0) {
            setUploadImage(mImageView, mImageDel);
        } else if (listDatas.size() == 3) {
            setImage(mImageView, mImageDel, position);
        } else {
            if (position > listDatas.size() - 1) {
                setUploadImage(mImageView, mImageDel);
            } else {
                setImage(mImageView, mImageDel, position);
            }
        }

    }

    private void setImage(ImageView mImageView, ImageView mImageDel, int position) {
        mImageDel.setVisibility(View.VISIBLE);
        final PhotoModel photoModel = (PhotoModel) getItem(position);
        Glide.with(context).load(photoModel.getUri()).error(R.mipmap.common_default_image_icon).into(mImageView);
        mImageDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delListener != null) {
                    listDatas.remove(photoModel);
                    notifyDataSetChanged();
                    delListener.onClick(v, photoModel);
                }
            }
        });

    }

    private void setUploadImage(ImageView mImageView, ImageView mImageDel) {
        mImageDel.setVisibility(View.GONE);
        Glide.with(context).load(R.mipmap.upload_img).into(mImageView);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addListener != null) {
                    addListener.onClick(v, null);
                }
            }
        });
    }

    @Override
    public int getCount() {
        if (listDatas == null) {
            return 1;
        } else if (listDatas.size() == 3) {
            return listDatas.size();
        } else {
            return listDatas.size() + 1;
        }
    }
}
