package com.aliIoT.demo.util;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aliIoT.demo.AlarmActivity;
import com.aliIoT.demo.R;
import com.aliIoT.demo.model.PushMessageBean;

/**
 * Created by hjt on 2020/7/18
 */
public class AlarmMessageAdapter extends BaseAdapter<PushMessageBean, AlarmMessageAdapter.AlarmMessageAdapterLongClick> {
    Map<String, PushMessageBean> map = new HashMap();
    int showType;

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.alarm_message_item_layout;
    }

    public void setClick(AlarmMessageAdapterLongClick mClick) {
        this.mClick = mClick;
    }

    AlarmMessageAdapterLongClick mClick;

    public void setType(int type) {
        map.clear();
        showType = type;
        notifyDataSetChanged();
    }

    public void setData(List<PushMessageBean> list) {
        super.setData(list);
        map.clear();
        if (showType == ConstUtil.TYPE_EDIT && mClick != null) {
            mClick.selsectChange();
        }
    }

    public List<String> getSelectList() {
        ArrayList<String> strings = new ArrayList<>(map.keySet());
        //map.clear();
        return strings;
    }

    public void setSelectAll() {
        if (list != null) {
            for (PushMessageBean bean : list) {
                map.put(bean.getKeyId(), bean);
            }
            notifyDataSetChanged();
        }
    }

    public boolean checkIsSelectAll() {
        if (map != null && list != null && list.size() != 0 && map.size() == list.size()) {
            return true;
        } else {
            return false;
        }

    }

    public void setSelectAllNo() {
        map.clear();
        notifyDataSetChanged();
    }

    public interface AlarmMessageAdapterLongClick {
        void selsectChange();

        void adapeterClick(PushMessageBean mPushMessageBean);
    }

    @Override
    protected void setBindViewHolder(SmipleViewHolder holder, int position) {
        PushMessageBean alarmMessageBean = list.get(position);
        if (alarmMessageBean != null) {
            TextView tv = holder.getView(R.id.alarm_item_layout_message_from);
            TextView tv2 = holder.getView(R.id.alarm_item_layout_message_time);
            TextView tv3 = holder.getView(R.id.alarm_item_layout_message_type);
            ImageView im = holder.getView(R.id.alarm_item_layout_message_im);
            ImageView read = holder.getView(R.id.alarm_item_layout_message_read);
            ImageView imPaly = holder.getView(R.id.alarm_item_layout_message_play_im);
            if (alarmMessageBean.getIsRead() == 0) {
                read.setVisibility(View.VISIBLE);
            } else {
                read.setVisibility(View.GONE);
            }
            imPaly.setVisibility(View.GONE);
            tv.setText(alarmMessageBean.getExtData().getProductName());

            tv2.setText(TimeUtils.millisecondToTime(alarmMessageBean.getGmtCreate()));
            tv3.setText(alarmMessageBean.getTitle());//mPushMessageBean.getExtData().getEventId()
            if (alarmMessageBean.getExtData() != null &&AlarmActivity.mAlarmEventIdPicBeanMap.get(alarmMessageBean.getExtData().getEventId())!=null) {
                GlideUtils.loadImageCache(MyApplication.getInstance()
                        , AlarmActivity.mAlarmEventIdPicBeanMap.get(alarmMessageBean.getExtData().getEventId()).getPicUrl()
                        , GlideUtils.optionsAddDefultErrorImage(GlideUtils.creatRequestOptions(), R.mipmap.ic_launcher, R.mipmap.ic_launcher), im);
            } else if (alarmMessageBean.getExtData() != null && !TextUtils.isEmpty(alarmMessageBean.getExtData().getIcon())) {
                GlideUtils.loadImage(MyApplication.getInstance(), alarmMessageBean.getExtData().getIcon(), GlideUtils.creatRequestOptions(), im);
            } else {
                GlideUtils.loadImage(MyApplication.getInstance(), R.mipmap.ic_launcher, GlideUtils.creatRequestOptions(), im);
            }
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (showType == ConstUtil.TYPE_SHOW) {
                        if (mClick != null) {
                            mClick.adapeterClick(alarmMessageBean);
                        }
                    }
                }
            });
            if (showType == ConstUtil.TYPE_SHOW) {
                holder.getView(R.id.alarm_item_layout_message_select).setVisibility(View.GONE);
            } else {
                ImageView view = holder.getView(R.id.alarm_item_layout_message_select);
                view.setVisibility(View.VISIBLE);
                if (map.get(alarmMessageBean.getKeyId()) != null) {
                    view.setBackgroundResource(R.mipmap.check_select_true);
                } else {
                    view.setBackgroundResource(R.mipmap.check_select_false);
                }
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (map.get(alarmMessageBean.getKeyId()) != null) {
                            view.setBackgroundResource(R.mipmap.check_select_false);
                            map.remove(alarmMessageBean.getKeyId());
                        } else {
                            view.setBackgroundResource(R.mipmap.check_select_true);
                            map.put(alarmMessageBean.getKeyId(), alarmMessageBean);
                        }
                        if (mClick != null) {
                            mClick.selsectChange();
                        }
                    }

                });
            }
        }
    }

}
