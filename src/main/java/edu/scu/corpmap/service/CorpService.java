package edu.scu.corpmap.service;


import edu.scu.corpmap.entity.neo4j.*;
import edu.scu.corpmap.entity.neo4j.GraphElement.Graph;
import edu.scu.corpmap.entity.neo4j.GraphElement.GraphEdge;
import edu.scu.corpmap.entity.neo4j.GraphElement.GraphNode;
import edu.scu.corpmap.result.BriefCorp;
import edu.scu.corpmap.result.FuzzyHintCorp;
import edu.scu.corpmap.utils.IKAnalyzer5x;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.graphdb.traversal.Uniqueness;
import org.neo4j.helpers.collection.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/***
 * create by LMJ
 * 2018/6/20
 */
@Service
public class CorpService {
    @Autowired
    private GraphDatabaseService graphDatabaseService;
    public static final int INVESTMENT = 0;
    public static final int STAFF = 1;
    public static final int REL_BOTH = 2;
    /**
     * 根据用户当前输入返回输入提示
     *
     * @param partialKeyword 用户当前输入
     * @return 输入提示列表
     */
    @Transactional
    public List<FuzzyHintCorp> getQueryHint(String partialKeyword) {
        List hintList = new ArrayList<FuzzyHintCorp>();
        try (Transaction tx = graphDatabaseService.beginTx()) {
            IndexManager index = graphDatabaseService.index();
            Index<Node> corpNodeFullTextIndex =
                    index.forNodes("NodeFullTextIndex", MapUtil.stringMap(IndexManager.PROVIDER, "lucene", "analyzer", IKAnalyzer5x.class.getName()));
            Term term = new Term("name", partialKeyword);
            Query query = new TermQuery(term);

            IndexHits<Node> foundNodes = corpNodeFullTextIndex.query(query);
            while (hintList.size() < 5 && foundNodes.hasNext()) {
                Node node = foundNodes.next();
                FuzzyHintCorp fuzzyHintCorp = new FuzzyHintCorp();
                fuzzyHintCorp.setCorpName(node.getProperty("name") + "");
                fuzzyHintCorp.setGraphId(node.getId());//图id
                if (node.getProperty("type", "").equals("")) continue;

                hintList.add(fuzzyHintCorp);
            }
            tx.success();
        }
        return hintList;
    }

    /**
     * 根据模糊搜索返回匹配的企业简要信息列表
     *
     * @param fuzzyName 搜索名称
     * @return 企业简要信息列表
     */
    @Transactional
    public List<BriefCorp> fuzzyQuery(String fuzzyName) {

        List briefCorpInfoList = new ArrayList<BriefCorp>();
        try (Transaction tx = graphDatabaseService.beginTx()) {

            IndexManager index = graphDatabaseService.index();
            Index<Node> corpNodeFullTextIndex =
                    index.forNodes("NodeFullTextIndex", MapUtil.stringMap(IndexManager.PROVIDER, "lucene", "analyzer", IKAnalyzer5x.class.getName()));
            IndexHits<Node> foundNodes = corpNodeFullTextIndex.query("name", fuzzyName);

            for (int i = 0; i < 100 && foundNodes.hasNext(); i++) {
                Node node = foundNodes.next();
                BriefCorp briefCorp = new BriefCorp();
                try {
                    briefCorp.setGraphId(node.getId());//获得图底层节点id
                    briefCorp.setType(node.getProperty("type").toString());
                    briefCorp.setName(node.getProperty("name").toString());
                    briefCorp.setReg_auth(node.getProperty("reg_auth").toString());
                    briefCorp.setReg_date(node.getProperty("reg_date").toString());
                    briefCorp.setId(node.getProperty("id").toString());
                } catch (NotFoundException e) {
                    continue; //有的企业为边缘节点，即，只有名字，没有其他信息
                }
                briefCorpInfoList.add(briefCorp);
            }
            tx.success();
        }
        return briefCorpInfoList;
    }

    /**
     * 根据统一社会信用码查询企业简要信息
     *
     * @param id 统一社会信用码
     * @return 企业简要信息
     */
    @Transactional
    public BriefCorp queryBriefCorpById(String id) {
        Corp corp = queryCorpById(id);
        BriefCorp briefCorp = BriefCorp.constructFromCorp(corp);
        return briefCorp;
    }

    /**
     * 根据节点ID查询企业简要信息
     *
     * @param graphId 节点ID
     * @return 企业简要信息
     */
    @Transactional
    public BriefCorp queryBriefCorpByGraphId(long graphId) {
        Corp corp = queryCorpByGraphId(graphId);
        BriefCorp briefCorp = BriefCorp.constructFromCorp(corp);
        return briefCorp;
//        BriefCorp briefCorp = new BriefCorp();
//        try (Transaction tx = graphDatabaseService.beginTx()) {
//            Node foundNode = graphDatabaseService.getNodeById(graphId);
//            briefCorp.setGraphId(foundNode.getId());//获得图底层节点id
//            briefCorp.setType(foundNode.getProperty("type").toString());
//            briefCorp.setName(foundNode.getProperty("name").toString());
//            briefCorp.setReg_auth(foundNode.getProperty("reg_auth").toString());
//            briefCorp.setReg_date(foundNode.getProperty("reg_date").toString());
//            briefCorp.setId(foundNode.getProperty("id").toString());
//            briefCorp.setGraphId(graphId);
//        }
//        return briefCorp;
    }

    /**
     * 根据节点ID查询企业信息
     *
     * @param graphId 节点ID
     * @return 企业信息或null
     */
    @Transactional
    public Corp queryCorpByGraphId(long graphId) {

        try (Transaction tx = graphDatabaseService.beginTx()) {

            Corp corp = new Corp();
            corp.setGraph_id(graphId);
            Node foundNode = graphDatabaseService.getNodeById(graphId);


            Iterable<Relationship> partnerRelationships = foundNode.getRelationships(Direction.INCOMING, MyRelationship.合伙人);//不存在关系则会返回空
            Iterable<Relationship> stockRelationships = foundNode.getRelationships(Direction.INCOMING, MyRelationship.股东);//不存在关系则会返回空
            Iterable<Relationship> irgRelationships = foundNode.getRelationships(Direction.OUTGOING, MyRelationship.经营异常);
            List shareholderList = new ArrayList<Shareholder>();
            List partnerList = new ArrayList<Partner>();
            List irgOptList = new ArrayList<IrgOperation>();

            getBasicInfo(corp, foundNode);
            getStockStructure(stockRelationships, shareholderList);
            getPartnerStructure(partnerRelationships, partnerList);
            getIrgOpts(irgRelationships, irgOptList);

            corp.setShareholders(shareholderList);
            corp.setPartners(partnerList);
            corp.setIrgOpts(irgOptList);

            tx.success();

            return corp;
        }
        catch (NotFoundException e) {
            // 返回null方便前端进行判断
            return null;
        }
    }

    /**
     * 根据统一社会信用码查询企业信息，可能返回一个空的corp
     *
     * @param id 统一社会信用码
     * @return 企业信息
     */
    @Transactional
    public Corp queryCorpById(String id) { //记得要先建立模式索引索引
        Corp corp = new Corp();
        try (Transaction tx = graphDatabaseService.beginTx()) {

            IndexManager indexManager = graphDatabaseService.index();
            Index<Node> corpIndex = indexManager.forNodes("corpId");
            IndexHits<Node> indexHits = corpIndex.get("id", id);
            Node foundNode = indexHits.getSingle(); //没有返回null
            if(foundNode==null) return corp;
                Iterable<Relationship> partnerRelationships = foundNode.getRelationships(Direction.INCOMING, MyRelationship.合伙人);//不存在关系则会返回空
                Iterable<Relationship> stockRelationships = foundNode.getRelationships(Direction.INCOMING, MyRelationship.股东);//不存在关系则会返回空
                Iterable<Relationship> irgRelationships = foundNode.getRelationships(Direction.OUTGOING, MyRelationship.经营异常);
                List shareholderList = new ArrayList<Shareholder>();
                List partnerList = new ArrayList<Partner>();
                List irgOptList = new ArrayList<IrgOperation>();

                getBasicInfo(corp, foundNode);
                getStockStructure(stockRelationships, shareholderList);
                getPartnerStructure(partnerRelationships, partnerList);
                getIrgOpts(irgRelationships, irgOptList);

                corp.setShareholders(shareholderList);
                corp.setPartners(partnerList);
                corp.setIrgOpts(irgOptList);


            tx.success();
        }
        return corp;
    }

    @Transactional
    public Graph constructGraph(long graphId,int req){
        TraversalDescription traversalDescription;
        Graph graph = new Graph();
        List<GraphNode> graphNodeList = new ArrayList<>();
        List<GraphEdge> graphEdgeList = new ArrayList();
        try(Transaction tx = graphDatabaseService.beginTx()){
            Node foundNode ;
            try{
                 foundNode = graphDatabaseService.getNodeById(graphId);
            }catch(Exception e){
                System.out.println("无法根据图id找到节点");
                return graph;
            }
            //思路：先把所有的节点加进去(利用遍历器），再把所有的关系与节点匹配
            if(req ==INVESTMENT){
                 traversalDescription =
                        graphDatabaseService.traversalDescription().
                                relationships(MyRelationship.合伙人,Direction.BOTH)
                                .relationships(MyRelationship.股东,Direction.BOTH)
                                .breadthFirst()//广度优先
                                .uniqueness(Uniqueness.NODE_GLOBAL)//所有节点仅被访问一次
                                .evaluator(Evaluators.toDepth(5));//最大深度为5
            }else if(req == STAFF){
                 traversalDescription =
                        graphDatabaseService.traversalDescription().
                                relationships(MyRelationship.任职,Direction.BOTH)
                                .breadthFirst()//广度优先
                                .uniqueness(Uniqueness.NODE_GLOBAL)//所有节点仅被访问一次
                                .evaluator(Evaluators.toDepth(5));//最大深度为5

            }else{
                 traversalDescription =
                        graphDatabaseService.traversalDescription().
                                relationships(MyRelationship.任职,Direction.BOTH).
                                relationships(MyRelationship.合伙人,Direction.BOTH)
                                .relationships(MyRelationship.股东,Direction.BOTH)
                                .breadthFirst()//广度优先
                                .uniqueness(Uniqueness.NODE_GLOBAL)//所有节点仅被访问一次
                                .evaluator(Evaluators.toDepth(5));//最大深度为5
            }
            Traverser traverser = traversalDescription.traverse(foundNode);
            trvNode(graphNodeList,traverser);
            trvEdge(graphEdgeList,graphNodeList,traverser);

            tx.success();
        }
        graph.setNodes(graphNodeList);
        graph.setEdges(graphEdgeList);
        return graph;
    }

    public void trvNode(List<GraphNode> graphNodeList,Traverser traverser){
        for(Node node:traverser.nodes()){
            GraphNode graphNode = new GraphNode();
            graphNode.setId(node.getProperty("id","").toString());
            graphNode.setName(node.getProperty("name","").toString());
            if (node.hasLabel(MyNodeLabel.人))  graphNode.setImage("..//images//person.png");
            else graphNode.setImage("..//images//enterprise.png");
            graphNodeList.add(graphNode);
        }
        graphNodeList.get(0).setImage("..//images//enterprise-main.png");
    }

    public void trvEdge(List<GraphEdge> graphEdgeList,List<GraphNode> graphNodeList, Traverser traverser){
        for(Relationship r:traverser.relationships()) {
            GraphEdge graphEdge = new GraphEdge();
            graphEdge.setSource(graphNodeList.indexOf(r.getStartNode()));
            graphEdge.setTarget(graphNodeList.indexOf(r.getEndNode()));

            graphEdge.setRelation(r.getType().name());
            try{
                //如果有"position"属性，说明关系为任职关系，将relation由“任职”改为具体的职位名称，并且不再往下执行
                graphEdge.setRelation(r.getProperty("position").toString());
                graphEdgeList.add(graphEdge);
                continue;
            }catch(Exception e){

            }
            graphEdge.setActual_subscp_date(r.getProperty("actual_subscp_date","").toString());
            graphEdge.setActual_subscription(r.getProperty("actual_subscription","").toString());
            graphEdge.setSubscription(r.getProperty("subscription","").toString());
            graphEdge.setMethod(r.getProperty("method","").toString());
            graphEdge.setSubscp_date(r.getProperty("subscp_date","").toString());

            graphEdgeList.add(graphEdge);
        }
    }

    public void getBasicInfo(Corp corp, Node foundNode) {
        String modifications = foundNode.getProperty("modification", "").toString();//节点里的异常信息是以字符串存的
        corp.setModifications(modifications);
        corp.setAddress(foundNode.getProperty("address","非公示项").toString());
        corp.setCheckdate(foundNode.getProperty("checkdate","非公示项").toString());
        corp.setEng_name(foundNode.getProperties("Eng_name","非公示项").toString());
        corp.setField(foundNode.getProperty("field","非公示项").toString());
        corp.setId(foundNode.getProperty("id","非公示项").toString());
        corp.setIntroduction(foundNode.getProperty("introduction","非公示项").toString());
        corp.setMail(foundNode.getProperty("mail","非公示项").toString());
        corp.setLegal_person(foundNode.getProperty("legal_person","非公示项").toString());
        corp.setName(foundNode.getProperty("name").toString());
        corp.setState(foundNode.getProperty("state").toString());
        corp.setReg_auth(foundNode.getProperty("reg_auth","非公示项").toString());
        corp.setReg_date(foundNode.getProperty("reg_date","非公示项").toString());
        corp.setType(foundNode.getProperty("type").toString());
    }

    public void getStockStructure(Iterable<Relationship> stockRelationships, List shareholderList) {
        for (Relationship r : stockRelationships) {
            Shareholder shareholder = new Shareholder();
            Node startNode = r.getStartNode();
            shareholder.setSh_id(startNode.getProperty("id", "").toString());//人没有id
            shareholder.setGraphId(startNode.getId());
            shareholder.setSh_name(startNode.getProperty("name").toString());
            shareholder.setMethod(r.getProperty("method", "货币").toString());
            shareholder.setActual_subscp_date(r.getProperty("actual_subscrp_date", "").toString());
            shareholder.setSubscription(r.getProperty("subscription", "").toString());
            shareholder.setSh_type(r.getProperty("sh_type", "").toString());
            shareholder.setSubscp_date(r.getProperty("subscp_date", "").toString());
            shareholder.setActual_subscription(r.getProperty("actual_subscription", "").toString());
            shareholderList.add(shareholder);
        }
    }

    public void getPartnerStructure(Iterable<Relationship> partnerRelationships, List partnerList) {
        for (Relationship r : partnerRelationships) {
            Partner partner = new Partner();
            Node startNode = r.getStartNode();
            partner.setPartner_id(startNode.getProperty("id", "").toString());//人没有id
            partner.setGraphId(startNode.getId());
            partner.setPartner_name(startNode.getProperty("name").toString());
            partner.setMethod(r.getProperty("method", "现金").toString());
            partner.setActual_subscp_date(r.getProperty("actual_subscrp_date", "").toString());
            partner.setSubscription(r.getProperty("subscription", "").toString());
            partner.setPartnerType(r.getProperty("sh_type", "").toString());
            partner.setSubscp_date(r.getProperty("subscp_date", "").toString());
            partner.setActual_subscription(r.getProperty("actual_subscription", "").toString());
            partnerList.add(partner);
        }
    }

    public void getIrgOpts(Iterable<Relationship> irgRelationships, List irgOptList) {
        for (Relationship r : irgRelationships) {
            IrgOperation irgOpt = new IrgOperation();
            Node endNode = r.getEndNode();
            irgOpt.setIrgReason(endNode.getProperty("irgReason", "").toString());
            irgOpt.setDeIrgAuth(endNode.getProperty("deIrgAuth", "").toString());
            irgOpt.setIrgAuth(endNode.getProperty("irgAuth", "").toString());
            irgOpt.setIrgDate(endNode.getProperty("irgDate", "").toString());
            irgOpt.setDeIrgDate(endNode.getProperty("deIrgDate", "").toString());
            irgOpt.setDeIrgReason(endNode.getProperty("deIrgReason", "").toString());
            irgOptList.add(irgOpt);
        }
    }
}
