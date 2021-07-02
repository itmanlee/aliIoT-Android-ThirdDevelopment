package com.aliIoT.demo.model;

import com.aliIoT.demo.util.ConstUtil;

/**
 * Created by hjt on 2020/6/15
 */
public class TitleItemBean<T> {
    private String itemName;
    private String itemRightInfo;


    private int rightImageResId;
    private boolean rightImIsSelect;
    private boolean selectStatus;
    private int itemType;
    private T t;

    private boolean isUpgradeStatus;//true有更新，false无

    /**
     * creatTypeItemTitle 用来处理布局中出现大间隔行的情况
     *
     * @return
     */
    public static TitleItemBean<Object> creatTypeItemTitle() {
        TitleItemBean<Object> objectTitleItemBean = new TitleItemBean<>();
        objectTitleItemBean.itemType = ConstUtil.TITLE_ITEM_TYPE_ITEM_TITLE;
        return objectTitleItemBean;
    }

    public void setRightImageResId(int rightImageResId) {
        this.rightImageResId = rightImageResId;
    }

    public void setItemRightInfo(String itemRightInfo) {
        this.itemRightInfo = itemRightInfo;
    }

    public void setSelectStatus(boolean selectStatus) {
        this.selectStatus = selectStatus;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemRightInfo() {
        return itemRightInfo;
    }

    public int getRightImageResId() {
        return rightImageResId;
    }

    public boolean getRightImIsSelect() {
        return rightImIsSelect;
    }

    public boolean getSelectStatus() {
        return selectStatus;
    }

    public int getItemType() {
        return itemType;
    }

    public T getT() {
        return t;
    }

    public boolean isUpgradeStatus() {
        return isUpgradeStatus;
    }

    public void setUpgradeStatus(boolean upgradeStatus) {
        isUpgradeStatus = upgradeStatus;
    }


    public void init() {
        init("", 0, -1);
    }

    public void init(String itemName, int rightImageResId, int itemType) {
        init(itemName, rightImageResId, "", itemType);
    }

    public void init(String itemName, int rightImageResId, int itemType,boolean isUpgradeStatus) { //固件升级专用
        init(itemName, rightImageResId, itemType, t, "", false, false,isUpgradeStatus);
    }

    public void init(String itemName, int rightImageResId, int itemType, T t) {
        init(itemName, rightImageResId, itemType, t, "", false, false,false);
    }

    public void init(String itemName, int rightImageResId, String itemRightInfo, int itemType) {
        init(itemName, rightImageResId, itemRightInfo, itemType, t);
    }

    public void init(String itemName, int rightImageResId, String itemRightInfo, int itemType, T t) {
        init(itemName, rightImageResId, itemType, t, itemRightInfo, false, false,false);
    }

    public void init(String itemName, int rightImageResId, int itemType, boolean rightImIsSelect, boolean selectStatus) {
        init(itemName, rightImageResId, itemType, null, "", rightImIsSelect, selectStatus,false);

    }

    public void init(String itemName, int rightImageResId, int itemType, T t, String itemRightInfo, boolean rightImIsSelect, boolean selectStatus, boolean isUpgradeStatus) {
        this.itemName = itemName;
        this.itemRightInfo = itemRightInfo;
        this.rightImageResId = rightImageResId;
        this.rightImIsSelect = rightImIsSelect;
        this.selectStatus = selectStatus;
        this.itemType = itemType;
        this.t = t;
        this.isUpgradeStatus = isUpgradeStatus;
    }
}
