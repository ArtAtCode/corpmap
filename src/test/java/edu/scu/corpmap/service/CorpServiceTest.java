package edu.scu.corpmap.service;

import edu.scu.corpmap.entity.neo4j.*;
import edu.scu.corpmap.entity.neo4j.GraphElement.Graph;
import edu.scu.corpmap.result.BriefCorp;
import edu.scu.corpmap.result.FuzzyHintCorp;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
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

        List<FuzzyHintCorp> list = corpService.getQueryHint("端州区");
       for(int i=0;i<list.size();i++){
           System.out.println(list.get(i).getCorpName());
           System.out.println(list.get(i).getGraphId());
       }

    }

    @Test
    public void fuzzyQuery() {
        List<BriefCorp> list = corpService.fuzzyQuery("端州区家得宝置业服务部");
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i).getName());
            System.out.println(list.get(i).getGraphId());
        }
    }
    @Test
    public void queryCorpByGraphId(){
        Corp corp = corpService.queryCorpByGraphId(229);//0
        System.out.println(corp.getName());
        System.out.println(corp.getModifications());
        for(Partner partner: corp.getPartners()){
            System.out.println(partner.getPartner_id());

        }
        for(Shareholder shareholder : corp.getShareholders()){
            System.out.println(shareholder.getSh_name());
            System.out.println(shareholder.getSh_type());
            System.out.println(shareholder.getActual_subscription());
            System.out.println(shareholder.getSubscription());
        }
        for(IrgOperation irg : corp.getIrgOpts()){
            System.out.println(irg.getIrgReason());
            System.out.println(irg.getDeIrgReason());
            System.out.println(irg.getIrgAuth());
        }

    }
    @Test
    public void quickCorpById(){
        Corp corp = new Corp();
        corp = corpService.queryCorpById("92441802MA51ELDA5K");
        System.out.println(corp.getName());

    }
    @Test
    public void constructGraph(){
        Graph graph = corpService.constructGraph(231,0);//91
        for(int i=0;i<graph.getNodes().size();i++){
            System.out.println(graph.getNodes().get(i).getName());
            System.out.println(graph.getNodes().get(i).getId());
        }
        for(int i=0;i<graph.getEdges().size();i++){
            System.out.println(graph.getEdges().get(i).getRelation());
            System.out.println(graph.getEdges().get(i).getActual_subscp_date());
            System.out.println(graph.getEdges().get(i).getActual_subscription());
            System.out.println(graph.getEdges().get(i).getMethod());
            System.out.println(graph.getEdges().get(i).getSubscription());
            System.out.println(graph.getEdges().get(i).getSubscp_date());

        }
    }

}