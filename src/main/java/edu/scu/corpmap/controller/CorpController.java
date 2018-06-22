package edu.scu.corpmap.controller;

import edu.scu.corpmap.entity.neo4j.Corp;
import edu.scu.corpmap.result.BriefCorp;
import edu.scu.corpmap.result.CorpResult;
import edu.scu.corpmap.result.FuzzyHintCorp;
import edu.scu.corpmap.service.CorpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vicent_Chen on 2018/6/23.
 */
@Controller
public class CorpController {
    @Autowired
    private CorpService corpService;

    /**
     * 根据用户输入返回输入提示
     *
     * @param keyword 用户输入
     * @return 输入提示列表
     */
    @RequestMapping("/getHint")
    @ResponseBody
    public List<FuzzyHintCorp> getHint(String keyword) {
        List<FuzzyHintCorp> list = corpService.getQueryHint(keyword);
        return list;
    }

    /**
     * 响应点击查询按钮
     * 倘若查询关键字为 统一社会信用码， 则返回仅包含0个或1个结果的列表
     * 否则返回包含0个或多个结果的列表
     *
     * @param keyword 关键词
     * @return 查询结果列表
     */
    @RequestMapping("/search")
    @ResponseBody
    public List<BriefCorp> search(String keyword) {

        List<BriefCorp> list;

        if (keyword.matches("[a-zA-Z0-9]*")) {
            // 关键词由字母+数字构成
            BriefCorp briefCorp = corpService.queryBriefCorpById(keyword);
            list = new ArrayList<>();
            list.add(briefCorp);
        }
        else {
            // 关键词包含中文
            list = corpService.fuzzyQuery(keyword);
        }
        return list;
    }
}
