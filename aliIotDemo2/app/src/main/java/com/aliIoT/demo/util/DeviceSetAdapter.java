package com.aliIoT.demo.util;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aliIoT.demo.R;
import com.aliIoT.demo.model.TitleItemBean;

/**
 * Created by hjt on 2020/6/15
 */
public class DeviceSetAdapter extends BaseAdapter<TitleItemBean, DeviceSetAdapter.OnItemClick> {
    public interface OnItemClick {
        void OnItemViewClick(TitleItemBean t, int postion);
    }

    private long timeInterval = 300L;
    private long mLastClickTime;
    final int VIEW_TYPE_TITLE = 0;
    final int VIEW_TYPE_ITEM = 1;

    @Override
    public int getLayoutId(int viewType) {
        if (viewType == VIEW_TYPE_TITLE) {
            return R.layout.device_set_title_layout;
        } else {
            return R.layout.device_set_item_layout;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getItemType() == ConstUtil.TITLE_ITEM_TYPE_ITEM_TITLE) {
            return VIEW_TYPE_TITLE;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @Override
    protected void setBindViewHolder(SmipleViewHolder holder, int position) {
        TitleItemBean itemBean = list.get(position);
        if (holder.getViewType() == VIEW_TYPE_TITLE) {
            return;
        }
        if (itemBean != null) {
            ((TextView) holder.getView(R.id.device_set_item_layout_tv)).setText(itemBean.getItemName());
            TextView rightTv = holder.getView(R.id.device_set_item_layout_tv_right);
            ImageView rigthImage = holder.getView(R.id.device_set_item_layout_select);
            rightTv.setText(itemBean.getItemRightInfo());

            View device_set_item_layout_sign = holder.getView(R.id.device_set_item_layout_sign);//固件升级标志
            if (itemBean.isUpgradeStatus() == true) {
                device_set_item_layout_sign.setVisibility(View.VISIBLE);
            } else {
                device_set_item_layout_sign.setVisibility(View.GONE);
            }

            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) rightTv.getLayoutParams();
            if (itemBean.getRightImageResId() != 0) {
                if (rigthImage.getVisibility() != View.VISIBLE)
                    rigthImage.setVisibility(View.VISIBLE);
                if (itemBean.getRightImIsSelect()) {
                    if (itemBean.getSelectStatus()) {
                        rigthImage.setBackgroundResource(R.mipmap.arrow_left_white);
                    } else {
                        rigthImage.setBackgroundResource(R.mipmap.arrow_left_white);
                    }
                } else {
                    rigthImage.setBackgroundResource(itemBean.getRightImageResId());
                }
                lp.rightToRight = ConstraintLayout.LayoutParams.UNSET;
                lp.rightToLeft = R.id.device_set_item_layout_select;
                lp.rightMargin = 0;
                rightTv.setLayoutParams(lp);
            } else {
                if (rigthImage.getVisibility() == View.VISIBLE)
                    rigthImage.setVisibility(View.GONE);
                lp.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
                lp.rightToLeft = ConstraintLayout.LayoutParams.UNSET;
                lp.rightMargin = 20;
                rightTv.setLayoutParams(lp);
            }
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        long nowTime = System.currentTimeMillis();
                        if (nowTime - mLastClickTime > timeInterval) {
                            // 单次点击事件
                            listener.OnItemViewClick(itemBean, position);
                            mLastClickTime = nowTime;
                        }

                        if (itemBean.getRightImIsSelect()) {
                            itemBean.setSelectStatus(!itemBean.getSelectStatus());
                            if (itemBean.getSelectStatus()) {
                                itemBean.setRightImageResId(R.mipmap.arrow_left_white);
                            } else {
                                itemBean.setRightImageResId(R.mipmap.arrow_left_white);
                            }
                            notifyItemChanged(position);
                        }
                    }
                }
            });
        }
    }


}
