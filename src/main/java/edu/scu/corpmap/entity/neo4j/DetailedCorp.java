package edu.scu.corpmap.entity.neo4j;

import java.util.List;

public class DetailedCorp {
    private String id; // 统一社会信用代码
    private String name; // 企业名称 / 名称
    private String type; // 类型
    private String legal_person; // 房顶代表人
    private String reg_date; // 成立日期
    private String reg_auth; // 登记机关
    private String state; // 登记状态
    private String field; // 经营范围
    private String address; // 住所
    private String mail; // 邮箱
    private String checkdate; // 核准日期
    private String Eng_name; // 英文名称
    private String introduction; // 简介

    private int mark; //1-股份制公司 2-合伙制公司 3-个体工商户
    private List shareholders; // 股东信息
    private List staff; // 任职人员
    private List partner; // 合伙人


}
