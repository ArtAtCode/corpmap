package edu.scu.corpmap.service;

import edu.scu.corpmap.entity.neo4j.BriefCorp;
import edu.scu.corpmap.entity.neo4j.FuzzyCorp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CorpServiceTest {
    @Autowired
    private CorpService corpService;

    @Test
    public void quickFuzzyQuery() {

        List<FuzzyCorp> list = corpService.quickFuzzyQuery("腾讯");
       for(int i=0;i<list.size();i++){
           System.out.println(list.get(i).getCorpName());
           System.out.println(list.get(i).getGraphId());
       }

    }

    @Test
    public void fuzzyQuery() {
        List<BriefCorp> list = corpService.fuzzyQuery("公司");
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i).getName());
            System.out.println(list.get(i).getGraphId());
        }
    }

}