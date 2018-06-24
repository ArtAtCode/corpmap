package edu.scu.corpmap.entity.neo4j;

import java.util.List;

public class Corp {
    private String introduction;//
    private String name;
    private String mail;
    private String Eng_name;
    private String address;
    private String type;//企业类型
    private String legal_person;  //法人或者（个体工商户的）经营者
    private String reg_date;//注册日期
    private String reg_auth;//注册机关
    private String field;//经营范围
    private String id;//统一社会信用代码
    private long graph_id;//图中的id（不显示)
    private String state;//经营状态
    private String reg_capt;//注册资本
    private String checkDate; //核准日期
    private String startDate;//营业期限自
    private String stopDate;//营业期限至

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStopDate() {
        return stopDate;
    }

    public void setStopDate(String stopDate) {
        this.stopDate = stopDate;
    }

    public String getReg_capt() {
        return reg_capt;
    }

    public void setReg_capt(String reg_capt) {
        this.reg_capt = reg_capt;
    }


    private String corpController;

    public String getCorpController() {
        return corpController;
    }

    public void setCorpController(String corpController) {
        this.corpController = corpController;
    }

    private List<Shareholder> shareholders; //  List类型会被映射为jsonarray
    private List<IrgOperation> irgOpts;//经营异常信息
    private List<Partner> partners;//合伙人
    private String modifications;//变更信息，存在节点中，是一个jsonarray的形式

    public String getModifications() {
        return modifications;
    }

    public void setModifications(String modifications) {
        this.modifications = modifications;
    }

    public long getGraph_id() {
        return graph_id;
    }

    public void setGraph_id(long graph_id) {
        this.graph_id = graph_id;
    }
    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getEng_name() {
        return Eng_name;
    }

    public void setEng_name(String eng_name) {
        Eng_name = eng_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLegal_person() {
        return legal_person;
    }

    public void setLegal_person(String legal_person) {
        this.legal_person = legal_person;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getReg_auth() {
        return reg_auth;
    }

    public void setReg_auth(String reg_auth) {
        this.reg_auth = reg_auth;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
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

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }


    public List<Shareholder> getShareholders() {
        return shareholders;
    }

    public void setShareholders(List<Shareholder> shareholders) {
        this.shareholders = shareholders;
    }

    public List<IrgOperation> getIrgOpts() {
        return irgOpts;
    }

    public void setIrgOpts(List<IrgOperation> irgOpts) {
        this.irgOpts = irgOpts;
    }

    public List<Partner> getPartners() {
        return partners;
    }

    public void setPartners(List<Partner> partners) {
        this.partners = partners;
    }
}
