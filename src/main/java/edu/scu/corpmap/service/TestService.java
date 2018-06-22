package edu.scu.corpmap.service;

import org.neo4j.graphdb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Vicent_Chen on 2018/6/13.
 */
// Service类必须添加Service注解
// Service类一般用于与数据库交互
@Service
public class TestService {
    @Autowired
    private GraphDatabaseService graphDatabaseService;

    @Transactional
    public String testEmb() {
        try(Transaction tx = graphDatabaseService.beginTx()){
            Result result = graphDatabaseService.execute("MATCH (n) RETURN count(n) AS c;");
            return result.resultAsString();
        }
    }
}
