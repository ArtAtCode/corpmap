package edu.scu.corpmap.controller;

import edu.scu.corpmap.entity.mysql.NewsDetail;
import edu.scu.corpmap.result.NewsDetailPreview;
import edu.scu.corpmap.service.NewsDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Vicent_Chen on 2018/7/26.
 */
@Controller
public class NewsDetailController {
    @Autowired
    private NewsDetailService newsDetailService;

    @RequestMapping("news")
    public List<NewsDetailPreview> getNewsList(int N) {
        List<NewsDetailPreview> list = newsDetailService.getLastestNNewsDetailPreview(N);
        return list;
    }

    @RequestMapping("news_count")
    public long getNewsCount() {
        long newsCount = newsDetailService.getNewsCount();
        return newsCount;
    }

    @RequestMapping("news_detail")
    public NewsDetail getNewsDetail(int id) {
        NewsDetail newsDetail = newsDetailService.getNewsDetailById(id);
        return newsDetail;
    }
}
