package com.router.Core.Base.Adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/***************************************************************************
 * Description:ViewHolder的通用化處理
 ***************************************************************************/
public class ViewHolder {
    /**
     * View容器,用於存放Holer中的View
     * SparseArray是Android推薦使用的一個優化容器,相當於一個Map<integer,View>
     */
    private SparseArray<View> mViews;
    /**
     * Item佈局View convertView
     */
    private View mConvertView;
    private Context mContext;

    public ViewHolder(Context context, ViewGroup parent, int layoutId) {
        mContext = context;
        mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }

    /**
     * 獲取ViewHolder
     *
     * @param context     上下文
     * @param convertView
     * @param parent
     * @param layoutId    佈局layout Id
     * @return
     */
    public static ViewHolder getViewHolder(Context context, View convertView, ViewGroup parent, int layoutId) {
        if (convertView == null)
            return new ViewHolder(context, parent, layoutId);
        return (ViewHolder) convertView.getTag();
    }

    /**
     * 獲取Holder中的ItemView
     *
     * @param viewId 視圖控制項ID
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View item = mViews.get(viewId);
        if (item == null) {
            item = mConvertView.findViewById(viewId);
            mViews.put(viewId, item);
        }
        return (T) item;
    }

    /**
     * 獲取convertView
     *
     * @return
     */
    public View getMConvertView() {
        return mConvertView;
    }
}