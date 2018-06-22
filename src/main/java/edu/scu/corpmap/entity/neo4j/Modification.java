package edu.scu.corpmap.entity.neo4j;

public class Modification {
    private String title;//变更事项
    private String priorContent;//变更前内容
    private String laterContent;//变更后内容
    private String date;//变更日期

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriorContent() {
        return priorContent;
    }

    public void setPriorContent(String priorContent) {
        this.priorContent = priorContent;
    }

    public String getLaterContent() {
        return laterContent;
    }

    public void setLaterContent(String laterContent) {
        this.laterContent = laterContent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
