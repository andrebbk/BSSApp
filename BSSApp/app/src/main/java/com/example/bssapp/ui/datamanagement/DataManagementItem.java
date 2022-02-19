package com.example.bssapp.ui.datamanagement;

import java.io.Serializable;

public class DataManagementItem implements Serializable {

    Long ItemId;
    String OptionName;

    public DataManagementItem()
    {

    }

    public DataManagementItem(Long id, String name)
    {
        this.ItemId = id;
        this.OptionName = name;
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

}
