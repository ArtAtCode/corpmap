package edu.scu.corpmap.service;

import edu.scu.corpmap.entity.neo4j.*;
import edu.scu.corpmap.entity.neo4j.GraphElement.Graph;
import edu.scu.corpmap.entity.neo4j.GraphElement.GraphNode;
import edu.scu.corpmap.result.BriefCorp;
import edu.scu.corpmap.result.FuzzyHintCorp;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.graphdb.traversal.Uniqueness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CorpServiceTest {
    @Autowired
    private CorpService corpService;
    @Autowired
    GraphDatabaseService graphDatabaseService;

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
        List<BriefCorp> list = corpService.fuzzyQuery("华为");
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
    @Test
    public void trvNodeTest(){

        TraversalDescription traversalDescription ;
        try(Transaction tx = graphDatabaseService.beginTx()){
           Node foundNode = graphDatabaseService.getNodeById(0);
//            traversalDescription =
//                    graphDatabaseService.traversalDescription().
//                            relationships(MyRelationship.合伙人,Direction.BOTH)
//                            .relationships(MyRelationship.股东,Direction.BOTH)
//                            .relationships(MyRelationship.任职,Direction.BOTH)
//                            .breadthFirst()//广度优先
//                            .uniqueness(Uniqueness.NONE)//所有节点仅被访问一次,节点编号是从0开始的
//                            .evaluator(Evaluators.includingDepths(0,1));
///** 两个todepth的结果是第二个不会遍历到，
// * 两个atDepth的结果是一个都没有
// *两个includingDepths的结果是一个都没有
// *
// * **/
//            Traverser traverser = traversalDescription.traverse(foundNode);
//            for(Node node:traverser.nodes()){
//                System.out.println(node.getProperty("id","").toString());
//                System.out.println(node.getProperty("name","").toString());
//            }

            traversalDescription =
                    graphDatabaseService.traversalDescription().
                            relationships(MyRelationship.合伙人,Direction.BOTH)
                            .relationships(MyRelationship.股东,Direction.BOTH)
                            .relationships(MyRelationship.任职,Direction.BOTH)
                            .breadthFirst()//广度优先
                            .uniqueness(Uniqueness.NODE_GLOBAL)//所有节点仅被访问一次,节点编号是从0开始的
                            .evaluator(Evaluators.includingDepths(2,3));
            Traverser traverser = traversalDescription.traverse(foundNode);
            traverser = traversalDescription.traverse(foundNode);
            for(Node node:traverser.nodes()){
                System.out.println(node.getProperty("id","").toString());
                System.out.println(node.getProperty("name","").toString());
            }
        }
    }
    @Test
    public void contrustGraphTest(){
        corpService.constructGraph(205,0);
    }

}