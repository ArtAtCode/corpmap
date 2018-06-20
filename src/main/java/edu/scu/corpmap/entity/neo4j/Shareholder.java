package edu.scu.corpmap.entity.neo4j;

public class Shareholder {
    private String sh_id; // 股东证件号码
    private String sh_name; // 股东名称
    private String method; // 认缴出资方式
    private String subscp_date; // 认缴出资日期
    private String actual_subscp_date; // 实缴出资日期
    private String actual_subscription; // 实缴额
    private String subscription; // 认缴额
    private String sh_type; // 股东类型

    public String getSh_id() {
        return sh_id;
    }

    public void setSh_id(String sh_id) {
        this.sh_id = sh_id;
    }

    public String getSh_name() {
        return sh_name;
    }

    public void setSh_name(String sh_name) {
        this.sh_name = sh_name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSubscp_date() {
        return subscp_date;
    }

    public void setSubscp_date(String subscp_date) {
        this.subscp_date = subscp_date;
    }

    public String getActual_subscp_date() {
        return actual_subscp_date;
    }

    public void setActual_subscp_date(String actual_subscp_date) {
        this.actual_subscp_date = actual_subscp_date;
    }

    public String getActual_subscription() {
        return actual_subscription;
    }

    public void setActual_subscription(String actual_subscription) {
        this.actual_subscription = actual_subscription;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getSh_type() {
        return sh_type;
    }

    public void setSh_type(String sh_type) {
        this.sh_type = sh_type;
    }
}
