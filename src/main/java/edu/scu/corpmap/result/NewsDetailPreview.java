package edu.scu.corpmap.result;

import edu.scu.corpmap.entity.mysql.NewsDetail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Vicent_Chen on 2018/7/23.
 */
public class NewsDetailPreview {
    private Integer id;
    private String title;
    private String previewImage;
    private Date time;

    public static NewsDetailPreview constructFromNewsDetail(NewsDetail newsDetail) {
        NewsDetailPreview newsDetailPreview = new NewsDetailPreview();
        newsDetailPreview.setId(newsDetail.getId());

        newsDetailPreview.setTitle(newsDetail.getTitle());
        newsDetailPreview.setTime(newsDetail.getTime());

        String previewImage = newsDetail.getPreviewImage();
        if (previewImage == null || previewImage.equals("NULL"))
            newsDetailPreview.setPreviewImage(null);
        else
            newsDetailPreview.setPreviewImage(previewImage);

        return newsDetailPreview;
    }

    public static List<NewsDetailPreview> constructFromNewsDetailList(List<NewsDetail> newsDetailList) {
        List<NewsDetailPreview> newsDetailPreviewList = new ArrayList<>();
        for (NewsDetail newsDetail : newsDetailList) {
            NewsDetailPreview newsDetailPreview = constructFromNewsDetail(newsDetail);
            newsDetailPreviewList.add(newsDetailPreview);
        }
        return newsDetailPreviewList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
