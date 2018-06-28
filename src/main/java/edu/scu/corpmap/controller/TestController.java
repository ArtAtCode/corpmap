package edu.scu.corpmap.controller;


import edu.scu.corpmap.entity.neo4j.Corp;
import edu.scu.corpmap.entity.neo4j.GraphElement.Graph;
import edu.scu.corpmap.result.BriefCorp;
import edu.scu.corpmap.service.CorpService;
import edu.scu.corpmap.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Vicent_Chen on 2018/6/13.
 */

// Controller 注解：标记为Controller的类能够接收HTTP请求
@Controller
public class TestController {
    // AutoWired注解:使用这个注解就不用自己new,统一交由Spring管理
    @Autowired
    private TestService testService;
    @Autowired
    private CorpService corpService;

    // RequestMapping 注解：标记为RequestMapping的方法能够接收特定的HTTP请求
    // 如这一方法接收 http://localhost:8080/
    @RequestMapping("/")
    // ResponseBody 注解：返回对象本身，除基础数据类型(int,float,String等)均返回JSON格式对象
    @ResponseBody
    public String Hello() {
        return "如果你能看见这段话，证明中文显示正常，请继续访问<a href='http://localhost:8080/Welcome'>这个网址</a>";
    }

    // 没有标记ResponseBody的将会返回网页，下面的方法会
    @RequestMapping("Welcome")
    public String Welcome(ModelMap map) {
        String result = testService.testEmb();

        // 将数据传递至页面,具体接收方式看resources/template/welcome.html
        map.addAttribute("info", "如果你能看到这段话证明Spirng运行正常+Thymeleaf运行正常");
        map.addAttribute("count", result);
        // 返回网页,默认不加上 .html,加上后反而有可能报404
        return "welcome";
    }
    @RequestMapping("testFuzzy")
    @ResponseBody
    public List<BriefCorp> testFuzzy(String keyword ){
        return corpService.fuzzyQuery(keyword);
    }

    @RequestMapping("fullMap")
    @ResponseBody
    public Graph fullMap(long id ){
        return corpService.constructGraph(id,1);
    }
    @RequestMapping("testC")
    @ResponseBody
    public Corp coprInfo(long graphId){
        return corpService.queryCorpByGraphId(graphId);
    }



}
