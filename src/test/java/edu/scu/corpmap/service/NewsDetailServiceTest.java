package edu.scu.corpmap.service;

import edu.scu.corpmap.entity.mysql.NewsDetail;
import edu.scu.corpmap.result.NewsDetailPreview;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Vicent_Chen on 2018/7/23.
 *
 * 测试已完全通过 7/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NewsDetailServiceTest {

    @Autowired
    NewsDetailService newsDetailService;

    @Test
    public void test001_getAllNewsDetailPreview() {
        List<NewsDetailPreview> list = newsDetailService.getAllNewsDetailPreview();
        assertEquals(20, list.size());
    }

    @Test
    public void test002_getLastestNNewsDetailPreview() {
        List<NewsDetailPreview> list = newsDetailService.getLastestNNewsDetailPreview(10);
        assertEquals(10, list.size());
        assertEquals(20, (int)list.get(0).getId());
    }

    @Test
    public void test003_getNewsDetailById() {
        NewsDetail newsDetail = newsDetailService.getNewsDetailById(20);
        assertEquals("NULL", newsDetail.getPreviewImage());
    }
}