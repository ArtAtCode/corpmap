package edu.scu.corpmap.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by Vicent_Chen on 2018/6/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestServiceTest {

    GraphDatabaseService graphDatabaseService;


    @Autowired
    TestService testService;

    @Test
    public void jdbcCount() {
        String s = testService.testEmb();
        System.out.println(s);
        // 真正的单元测试还需要使用assertEquals()方法
    }
}