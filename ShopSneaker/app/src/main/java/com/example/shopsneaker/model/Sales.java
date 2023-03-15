package com.example.shopsneaker.model;

import java.io.Serializable;
import java.util.Date;

public class Sales implements Serializable {

    Integer salesid;
    Date startday;
    Date endday;
    String salesname;
    Date createdate;
    String content;
    Integer percent;

    public Sales() {
    }

    public Sales(Integer salesid, Date startday, Date endday, String salesname, Date createdate, String content, Integer percent) {
        this.salesid = salesid;
        this.startday = startday;
        this.endday = endday;
        this.salesname = salesname;
        this.createdate = createdate;
        this.content = content;
        this.percent = percent;
    }

    public Integer getSalesid() {
        return salesid;
    }

    public void setSalesid(Integer salesid) {
        this.salesid = salesid;
    }

    public Date getStartday() {
        return startday;
    }

    public void setStartday(Date startday) {
        this.startday = startday;
    }

    public Date getEndday() {
        return endday;
    }

    public void setEndday(Date endday) {
        this.endday = endday;
    }

    public String getSalesname() {
        return salesname;
    }

    public void setSalesname(String salesname) {
        this.salesname = salesname;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

}
