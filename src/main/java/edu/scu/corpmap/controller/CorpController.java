package edu.scu.corpmap.controller;

import edu.scu.corpmap.entity.mysql.HotCorp;
import edu.scu.corpmap.entity.neo4j.Corp;
import edu.scu.corpmap.entity.neo4j.GraphElement.Graph;
import edu.scu.corpmap.result.BriefCorp;
import edu.scu.corpmap.result.FuzzyHintCorp;
import edu.scu.corpmap.service.CorpService;
import edu.scu.corpmap.service.HotCorpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static edu.scu.corpmap.service.CorpService.*;

/**
 * Created by Vicent_Chen on 2018/6/23.
 */
@Controller
public class CorpController {
    @Autowired
    private CorpService corpService;

    @Autowired
    private HotCorpService hotCorpService;

    /**
     * 根据用户输入返回输入提示
     *
     * @param keyword 用户输入
     * @return 输入提示列表
     */
    @RequestMapping("/hint")
    @ResponseBody
    public List<FuzzyHintCorp> getHint(String keyword) {
        List<FuzzyHintCorp> list = corpService.getQueryHint(keyword);
        return list;
    }

    /**
     * 返回热搜榜，数量固定为5个
     *
     * @return 搜索量最高的5家企业
     */
    @RequestMapping("/hot_corps")
    @ResponseBody
    public List<HotCorp> getHotCorps() {
        List<HotCorp> list = hotCorpService.getTopNHotCorp(3);
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
        } else {
            // 关键词包含中文
            list = corpService.fuzzyQuery(keyword);
        }
        return list;
    }

    /**
     * 根据图节点id“模仿” search()
     *
     * @param graphId
     * @return
     */
    @RequestMapping("/search-graphid")
    @ResponseBody
    public List<BriefCorp> searchGraphId(Long graphId) {
        List<BriefCorp> list = new ArrayList<>();
        BriefCorp briefCorp = corpService.queryBriefCorpByGraphId(graphId);
        list.add(briefCorp);
        return list;
    }

    /**
     * 根据节点ID查找企业
     *
     * @param graphId 节点ID
     * @return 企业信息
     */
    @RequestMapping("/corp")
    @ResponseBody
    public Corp getCorp(Long graphId) {
        Corp corp = corpService.queryCorpByGraphId(graphId);
        // 添加至热搜榜
        if (corp != null)
            hotCorpService.plusOne(graphId + "", corp.getName());
        return corp;
    }

    /**
     * 返回企业族谱
     *
     * @param graphId
     * @return
     */
    @RequestMapping("/corp-map")
    @ResponseBody
    public Graph getCorpmap(Long graphId) {
        Graph graph = corpService.constructGraph(graphId, REL_BOTH);
        return graph;
    }

    /**
     * 返回投资族谱
     *
     * @param graphId
     * @return
     */
    @RequestMapping("/inverst-map")
    @ResponseBody
    public Graph getInverstMap(Long graphId) {
        Graph graph = corpService.constructGraph(graphId, INVESTMENT);
        return graph;
    }

    /**
     * 返回任职族谱
     *
     * @param graphId
     * @return
     */
    @RequestMapping("/staff-map")
    @ResponseBody
    public Graph getStaffMap(Long graphId) {
        Graph graph = corpService.constructGraph(graphId, STAFF);
        return graph;
    }
}
