package edu.scu.corpmap.entity.neo4j;

public class Partner {
    private String partner_id;
    private String pt_name;
    private String subscription;
    private String actual_subscription;
    private String method;
    private String partnerType;
    private String subscp_date;
    private String actual_subscp_date;

    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }

    public String getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }

    public String getPt_name() {
        return pt_name;
    }

    public void setPt_name(String pt_name) {
        this.pt_name = pt_name;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getActual_subscription() {
        return actual_subscription;
    }

    public void setActual_subscription(String actual_subscription) {
        this.actual_subscription = actual_subscription;
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
}

