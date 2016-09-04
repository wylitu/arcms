package com.suniusoft.pay.vo;

import com.suniusoft.pay.PayUtils;

/**
 * @author wywangyong
 */
public class BaseVo {

    String name;
    String price;
    Long objectId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceStr(){
        if(price != null){
            return PayUtils.toYuan(price);
        }
        return "0";
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }
}
