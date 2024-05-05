package com.router.Core.Base.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/***************************************************************************
 * Description:通用基礎轉接器,簡化轉接器程式碼,子類只需要在重寫的showGetView()方法中進行內容展示即可,無需再去重寫getCount()、getItem()、getItemId()方法。
 ***************************************************************************/
public abstract class BaseCommonAdapter2<T> extends BaseAdapter {
    /**
     * 上下文
     */
    protected Context context;
    /**
     * 資料來源
     */
    protected List<T> listDatas;
    /**
     * 佈局ID
     */
    protected int layoutID;

    /**
     * 構造方法
     *
     * @param context  Activity上下文
     * @param datas    資料來源
     * @param layoutID 佈局檔案ID
     */
    public BaseCommonAdapter2(Context context, List<T> datas, int layoutID) {
        this.context = context;
        this.listDatas = new ArrayList<>(datas);
        this.layoutID = layoutID;
    }

    public void clearAdapterData() {
        if (listDatas != null) {
            listDatas.clear();
            listDatas = null;
        }
    }

    @Override
    public int getCount() {
        return listDatas == null ? 0 : listDatas.size();
    }

    @Override
    public T getItem(int position) {
        return listDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.getViewHolder(context, convertView,
                parent, layoutID);
        try {
            showGetView(holder, parent, position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return holder.getMConvertView();
    }

    /**
     * 轉接器子類需實現此方法,完成ListView或GridView中每個條目內容的顯示
     *
     * @param holder   ViewHolder視圖緩存
     * @param position 當前位置
     */
    protected abstract void showGetView(ViewHolder holder, ViewGroup parent, int position);
}
