package edu.scu.corpmap.service;


import edu.scu.corpmap.entity.neo4j.*;
import edu.scu.corpmap.utils.IKAnalyzer5x;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.bouncycastle.math.raw.Mod;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.IndexManager;
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


    @Transactional
    //根据关键字提示用户输入
    public List<FuzzyCorp> quickFuzzyQuery(String fuzzyName){
        /*
         *只返回名称
         */
        List fuzzyCorpList =new ArrayList<FuzzyCorp>();
     //   IndexTools.createFullTextIndex(graphDatabaseService);//建立全文索引
        try(Transaction tx = graphDatabaseService.beginTx()){
            IndexManager index = graphDatabaseService.index();
            Index<Node> corpNodeFullTextIndex =
                    index.forNodes( "NodeFullTextIndex", MapUtil.stringMap(IndexManager.PROVIDER, "lucene", "analyzer",  IKAnalyzer5x.class.getName()));
            Term term = new Term("name",fuzzyName);
            Query query = new TermQuery(term);

            IndexHits<Node> foundNodes = corpNodeFullTextIndex.query(query);
            while(fuzzyCorpList.size()<5&&foundNodes.hasNext()){
                Node node = foundNodes.next();
                FuzzyCorp fuzzyCorp = new FuzzyCorp();
                fuzzyCorp.setCorpName(node.getProperty("name")+"");
                fuzzyCorp.setGraphId(node.getId());//图id
                if(node.getProperty("type","").equals("")) continue;
                fuzzyCorpList.add(fuzzyCorp);

            }
            tx.success();
        }
        return fuzzyCorpList;
    }

    @Transactional
    // 根据模糊搜索返回匹配的企业简要信息列表
    public List<BriefCorp> fuzzyQuery(String fuzzyName) {

        List briefCorpInfoList = new ArrayList<BriefCorp>();
        try(Transaction tx = graphDatabaseService.beginTx()) {

            IndexManager index = graphDatabaseService.index();
            Index<Node> corpNodeFullTextIndex =
                    index.forNodes("NodeFullTextIndex", MapUtil.stringMap(IndexManager.PROVIDER, "lucene", "analyzer", IKAnalyzer5x.class.getName()));
            IndexHits<Node> foundNodes = corpNodeFullTextIndex.query("name", fuzzyName);

            for (int i = 0; i < 100 && foundNodes.hasNext(); i++) {
                Node node = foundNodes.next();
                BriefCorp briefCorp = new BriefCorp();
                try{
                    briefCorp.setGraphId(node.getId());//获得图底层节点id
                    briefCorp.setType(node.getProperty("type").toString());
                    briefCorp.setName(node.getProperty("name").toString());
                    briefCorp.setReg_auth(node.getProperty("reg_auth").toString());
                    briefCorp.setReg_date(node.getProperty("reg_date").toString());
                    briefCorp.setId(node.getProperty("id").toString());
                }catch (Exception e){
                    continue; //有的企业为边缘节点，即，只有名字，没有其他信息
                }


                briefCorpInfoList.add(briefCorp);
            }
            tx.success();
        }
        return briefCorpInfoList;
    }

    @Transactional
    //返回简要信息
    public BriefCorp queryCorpByHint(long graphId){
        BriefCorp briefCorp = new BriefCorp();
        try(Transaction tx = graphDatabaseService.beginTx()){
            Node foundNode = graphDatabaseService.getNodeById(graphId);
            briefCorp.setGraphId(foundNode.getId());//获得图底层节点id
            briefCorp.setType(foundNode.getProperty("type").toString());
            briefCorp.setName(foundNode.getProperty("name").toString());
            briefCorp.setReg_auth(foundNode.getProperty("reg_auth").toString());
            briefCorp.setReg_date(foundNode.getProperty("reg_date").toString());
            briefCorp.setId(foundNode.getProperty("id").toString());
            briefCorp.setGraphId(graphId);
        }
        return briefCorp;
    }
    @Transactional
    //返回详细信息
    public BasicCorp queryCropByGraphId(long graphId){
        BasicCorp basicCorp = new BasicCorp();

        try(Transaction tx = graphDatabaseService.beginTx()){

            Node foundNode = graphDatabaseService.getNodeById(graphId);//根据底层id找到节点
            String modifications = foundNode.getProperty("modification","").toString();//节点里的异常信息是以字符串存的
            basicCorp.setModifications(modifications);
            basicCorp.setAddress(foundNode.getProperty("address","").toString());
            basicCorp.setCheckdate(foundNode.getProperty("checkdate").toString());
            basicCorp.setEng_name(foundNode.getProperties("Eng_name").toString());
            basicCorp.setField(foundNode.getProperty("field").toString());
            basicCorp.setId(foundNode.getProperty("id").toString());
            basicCorp.setGraph_id(graphId);
            basicCorp.setIntroduction(foundNode.getProperty("introduction").toString());
            basicCorp.setMail(foundNode.getProperty("mail").toString());
            basicCorp.setLegal_person(foundNode.getProperty("legal_person").toString());
            basicCorp.setName(foundNode.getProperty("name").toString());
            basicCorp.setState(foundNode.getProperty("state").toString());
            basicCorp.setReg_auth(foundNode.getProperty("reg_auth").toString());
            basicCorp.setReg_date(foundNode.getProperty("reg_date").toString());
            basicCorp.setType(foundNode.getProperty("type").toString());
            //mark还没加
            Iterable<Relationship> partnerRelationships = foundNode.getRelationships(Direction.INCOMING,MyRelationship.合伙人);//不存在关系则会返回空
            Iterable<Relationship> stockRelationships = foundNode.getRelationships(Direction.INCOMING,MyRelationship.股东);//不存在关系则会返回空
            Iterable<Relationship> irgRelationships = foundNode.getRelationships(Direction.OUTGOING,MyRelationship.经营异常);
            List shareholderList =new ArrayList<Shareholder>();
            List partnerList = new ArrayList<Partner>();
            List irgOptList = new ArrayList<IrgOperation>();


            for(Relationship r :stockRelationships){
                Shareholder shareholder = new Shareholder();
                Node startNode = r.getStartNode();
                shareholder.setSh_id(startNode.getProperty("id","").toString());//人没有id
                shareholder.setGraphId(startNode.getId());
                shareholder.setSh_name(startNode.getProperty("name").toString());
                shareholder.setMethod(r.getProperty("method","现金").toString());
                shareholder.setActual_subscp_date(r.getProperty("actual_subscrp_date","").toString());
                shareholder.setSubscription(r.getProperty("subscription","").toString());
                shareholder.setSh_type(r.getProperty("sh_type","").toString());
                shareholder.setSubscp_date(r.getProperty("subscp_date","").toString());
                shareholder.setActual_subscription(r.getProperty("actual_subscription","").toString());
                shareholderList.add(shareholder);
            }
            for(Relationship r :partnerRelationships){
                Partner partner = new Partner();
                Node startNode = r.getStartNode();
                partner.setPartner_id(startNode.getProperty("id","").toString());//人没有id
                partner.setGraphId(startNode.getId());
                partner.setPartner_name(startNode.getProperty("name").toString());
                partner.setMethod(r.getProperty("method","现金").toString());
                partner.setActual_subscp_date(r.getProperty("actual_subscrp_date","").toString());
                partner.setSubscription(r.getProperty("subscription","").toString());
                partner.setPartnerType(r.getProperty("sh_type","").toString());
                partner.setSubscp_date(r.getProperty("subscp_date","").toString());
                partner.setActual_subscription(r.getProperty("actual_subscription","").toString());
                partnerList.add(partner);
            }
            for(Relationship r:irgRelationships){
                IrgOperation irgOpt = new IrgOperation();
                Node endNode = r.getEndNode();
                irgOpt.setIrgReason(endNode.getProperty("irgReason","").toString());
                irgOpt.setDeIrgAuth(endNode.getProperty("deIrgAuth","").toString());
                irgOpt.setIrgAuth(endNode.getProperty("irgAuth","").toString());
                irgOpt.setIrgDate(endNode.getProperty("irgDate","").toString());
                irgOpt.setDeIrgDate(endNode.getProperty("deIrgDate","").toString());
                irgOpt.setDeIrgReason(endNode.getProperty("deIrgReason","").toString());
                irgOptList.add(irgOpt);
            }
            basicCorp.setShareholders(shareholderList);
            basicCorp.setPartners(partnerList);
            basicCorp.setIrgOpts(irgOptList);




            tx.success();
        }
        return basicCorp;
    }


}
