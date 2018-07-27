package edu.scu.corpmap.service;

import edu.scu.corpmap.dao.NewsDetailMapper;
import edu.scu.corpmap.entity.mysql.NewsDetail;
import edu.scu.corpmap.entity.mysql.NewsDetailExample;
import edu.scu.corpmap.result.NewsDetailPreview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Vicent_Chen on 2018/7/23.
 */
@Service
public class NewsDetailService {

    @Autowired
    private NewsDetailMapper newsDetailMapper;

    /**
     * 获取所有新闻列表
     * @return
     */
    public List<NewsDetailPreview> getAllNewsDetailPreview() {
        NewsDetailExample newsDetailExample = new NewsDetailExample();
        newsDetailExample.setOrderByClause("time DESC");
        List<NewsDetail> newsDetailList = newsDetailMapper.selectByExample(newsDetailExample);
        List<NewsDetailPreview> newsDetailPreviewList = NewsDetailPreview.constructFromNewsDetailList(newsDetailList);
        return newsDetailPreviewList;
    }

    /**
     * 分页获取新闻列表
     * @param offset
     * @param limit
     * @return
     */
    public List<NewsDetailPreview> getAllNewsDetailPreview(int offset, int limit) {
        NewsDetailExample newsDetailExample = new NewsDetailExample();
        newsDetailExample.setOrderByClause("time DESC");
        newsDetailExample.setOffset(offset);
        newsDetailExample.setLimit(limit);
        List<NewsDetail> newsDetailList = newsDetailMapper.selectByExampleUsingLimit(newsDetailExample);
        List<NewsDetailPreview> newsDetailPreviewList = NewsDetailPreview.constructFromNewsDetailList(newsDetailList);
        return newsDetailPreviewList;
    }

    /**
     * 获取新闻总数
     * @return
     */
    public long getNewsCount() {
        return newsDetailMapper.countByExample(new NewsDetailExample());
    }

    /**
     * 根据id获取最新的N条新闻列表
     *
     * @param N
     * @return
     */
    @Transactional(readOnly = true)
    public List<NewsDetailPreview> getLastestNNewsDetailPreview(int N) {
        List<NewsDetailPreview> newsDetailPreviewList = getAllNewsDetailPreview(0, N);
        return newsDetailPreviewList;
    }

    /**
     * 根据id获取新闻详情
     * 新闻详情中，<b>previewImage尽量不要使用</b>
     * <b>使用previewImage时应注意，若为空，其值为字符串"NULL"</b>
     *
     * @param id
     * @return 新闻详情
     */
    @Transactional(readOnly = true)
    public NewsDetail getNewsDetailById(int id) {
        NewsDetail newsDetail = newsDetailMapper.selectByPrimaryKey(id);
        String content = newsDetail.getContent();
        newsDetail.setContent(content.substring(1, content.length()-1));
        String title = newsDetail.getTitle();
        newsDetail.setTitle(title.substring(1, title.length()-1));
        return newsDetail;
    }
}
