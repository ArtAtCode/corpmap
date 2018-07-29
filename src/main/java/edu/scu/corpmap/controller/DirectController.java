package edu.scu.corpmap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Vicent_Chen on 2018/6/23.
 */
@Controller
public class DirectController {
    @RequestMapping("/")
    public String init() { return "index"; }

    @RequestMapping("index.html")
    public String index() {
        return "index";
    }

    @RequestMapping("search_list.html")
    public String searchList() {
        return "search_list";
    }

    @RequestMapping("info.html")
    public String info() {
        return "info";
    }

    @RequestMapping("relation.html")
    public String relation() {
        return "relation";
    }

    @RequestMapping("test.html")
    public String test() {
        return "test";
    }

    @RequestMapping("investment.html")
    public String inverstment() { return "investment"; }

    @RequestMapping("staff.html")
    public String staff() { return "staff"; }

    @RequestMapping("corp.html")
    public String corp() { return "corp"; }

    @RequestMapping("about-us.html")
    public String aboutus(){
        return "about-us";
    }

    @RequestMapping("news_detail.html")
    public String newsDetail() {
        return "news_detail";
    }
}
