package com.example.market.entity.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Author:ZhuQing
 * Date:2017/11/28 15:32
 */
public class CustomerVegeAddForm {

    @NotNull(message = "客户不能为空")
    private String custmerId;

    @NotNull(message = "菜单不以为空")
    private String vegeId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendDate;

    public String getCustmerId() {
        return custmerId;
    }

    public void setCustmerId(String custmerId) {
        this.custmerId = custmerId;
    }

    public String getVegeId() {
        return vegeId;
    }

    public void setVegeId(String vegeId) {
        this.vegeId = vegeId;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
}
