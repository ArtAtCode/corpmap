package edu.scu.corpmap.service;

import edu.scu.corpmap.entity.neo4j.*;
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
    @Test
    public void queryCorpByGraphId(){
        BasicCorp basicCorp = corpService.queryCropByGraphId(0);//0
        System.out.println(basicCorp.getModifications());
        for(Partner partner:basicCorp.getPartners()){
            System.out.println(partner.getPartner_id());

        }
        for(Shareholder shareholder : basicCorp.getShareholders()){
            System.out.println(shareholder.getSh_name());
            System.out.println(shareholder.getSh_type());
            System.out.println(shareholder.getActual_subscription());
            System.out.println(shareholder.getSubscription());
        }
        for(IrgOperation irg : basicCorp.getIrgOpts()){
            System.out.println(irg.getIrgReason());
            System.out.println(irg.getDeIrgReason());
            System.out.println(irg.getIrgAuth());
        }

    }

}