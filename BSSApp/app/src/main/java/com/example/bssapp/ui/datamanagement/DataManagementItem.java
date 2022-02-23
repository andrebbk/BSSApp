package com.example.bssapp.ui.datamanagement;

import java.io.Serializable;

public class DataManagementItem implements Serializable {

    Long ItemId;
    String OptionName;
    boolean IsSport;

    public DataManagementItem()
    {

    }

    public DataManagementItem(Long id, String name, boolean isSport)
    {
        this.ItemId = id;
        this.OptionName = name;
        this.IsSport = isSport;
    }

    public Long getItemId() {
        return ItemId;
    }

    public void setItemId(Long itemId) {
        ItemId = itemId;
    }

    public String getOptionName() {
        return OptionName;
    }

    public void setOptionName(String optionName) {
        OptionName = optionName;
    }

    public boolean getIsSport() {
        return IsSport;
    }

    public void setIsSport(boolean isSport) { IsSport = isSport; }

}
