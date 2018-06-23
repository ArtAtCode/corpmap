package edu.scu.corpmap.entity.neo4j.GraphElement;

public class PartnerGraphEdge extends GraphEdge {

    private String subscription;
    private String actual_subscription;
    private String method;
    private String partnerType;
    private String subscp_date;
    private String actual_subscp_date;
    private String relation="合伙人";

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

    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
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

    @Override
    public String getRelation() {
        return relation;
    }

    @Override
    public void setRelation(String relation) {
        this.relation = relation;
    }
}
