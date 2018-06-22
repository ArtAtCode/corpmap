package edu.scu.corpmap.entity.neo4j;

public class IrgOperation {
    private String irgReason;//列入经营异常名录原因
    private String irgDate;//列入日期
    private String irgAuth;//作出决定机关(列入)
    private String deIrgReason;//移出经营异常名录原因
    private String deIrgDate;//移出日期
    private String deIrgAuth;//作出决定机关(移出)

    public String getIrgReason() {
        return irgReason;
    }

    public void setIrgReason(String irgReason) {
        this.irgReason = irgReason;
    }

    public String getIrgDate() {
        return irgDate;
    }

    public void setIrgDate(String irgDate) {
        this.irgDate = irgDate;
    }

    public String getIrgAuth() {
        return irgAuth;
    }

    public void setIrgAuth(String irgAuth) {
        this.irgAuth = irgAuth;
    }

    public String getDeIrgReason() {
        return deIrgReason;
    }

    public void setDeIrgReason(String deIrgReason) {
        this.deIrgReason = deIrgReason;
    }

    public String getDeIrgDate() {
        return deIrgDate;
    }

    public void setDeIrgDate(String deIrgDate) {
        this.deIrgDate = deIrgDate;
    }

    public String getDeIrgAuth() {
        return deIrgAuth;
    }

    public void setDeIrgAuth(String deIrgAuth) {
        this.deIrgAuth = deIrgAuth;
    }
}
