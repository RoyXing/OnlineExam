package com.onlineexamination.bean;

/**
 * Created by 庞品 on 2017/2/11.
 */

public class PersonItemBean {
    private String itemName;
    private int itemImgId;

    public PersonItemBean(String itemName, int itemImgId) {
        this.itemName = itemName;
        this.itemImgId = itemImgId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemImgId() {
        return itemImgId;
    }

    public void setItemImgId(int itemImgId) {
        this.itemImgId = itemImgId;
    }
}
