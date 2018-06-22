package edu.scu.corpmap.entity.neo4j;

public class BriefCorp {
    private String type;//公司类型或者个体工商户
    private String reg_auth;//注册机关
    private String id;//统一社会信用代码
    private String state;//经营状态
    private String reg_date;//成立日期
    private String name;
    private long graphId;

    public long getGraphId() {
        return graphId;
    }

    public void setGraphId(long graphId) {
        this.graphId = graphId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReg_auth() {
        return reg_auth;
    }

    public void setReg_auth(String reg_auth) {
        this.reg_auth = reg_auth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }


}
