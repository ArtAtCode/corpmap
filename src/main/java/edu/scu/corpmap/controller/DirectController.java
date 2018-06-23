package edu.scu.corpmap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Vicent_Chen on 2018/6/23.
 */
@Controller
public class DirectController {
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
}
