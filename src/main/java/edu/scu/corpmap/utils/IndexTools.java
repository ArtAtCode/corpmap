package edu.scu.corpmap.utils;

import edu.scu.corpmap.entity.neo4j.MyNodeLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.helpers.collection.MapUtil;

public class IndexTools {

    public static void createFullTextIndex(GraphDatabaseService db){
        try (Transaction tx = db.beginTx()) {
            ResourceIterator<Node> corpIterator = db.findNodes(MyNodeLabel.企业);
            ResourceIterator<Node> indBusiIterator = db.findNodes(MyNodeLabel.个体工商户);
            IndexManager index = db.index();
            Index<Node> corpNodeFullTextIndex =
                    index.forNodes( "NodeFullTextIndex", MapUtil.stringMap(IndexManager.PROVIDER, "lucene", "analyzer",  IKAnalyzer5x.class.getName()));
            while (corpIterator.hasNext()) {
                Node node = corpIterator.next();
                //对企业节点，name字段新建全文索引
                Object corp = node.getProperty( "name", null);
                corpNodeFullTextIndex.add(node, "name", corp);
            }
            while (indBusiIterator.hasNext()){
                Node node = indBusiIterator.next();
                //对个体工商户节点，name字段新建全文索引
                Object corp = node.getProperty( "name", null);
                corpNodeFullTextIndex.add(node, "name", corp);
            }
            tx.success();
        }

    }
    /**
     * 对含有id的企业和个体工商户的id属性建立模式索引（cypher建立的索引无法在neo4j java api里调用）
     * 模式索引名字为 "corpId"
     * @param  db 单例GraphDatabaseService
     * @return void
     */
    public  void createSchemaIndex(GraphDatabaseService db){
        try (Transaction tx = db.beginTx()) {
            ResourceIterator<Node> corpIterator = db.findNodes(MyNodeLabel.企业);
            ResourceIterator<Node> indBusiIterator = db.findNodes(MyNodeLabel.个体工商户);
            IndexManager indexManager = db.index();
            Index<Node> schemaIndex = indexManager.forNodes("corpId");
            trvNodeCreateSchemaIndex(corpIterator,schemaIndex);
            trvNodeCreateSchemaIndex(indBusiIterator,schemaIndex);
            tx.success();
        }
    }
    private void trvNodeCreateSchemaIndex( ResourceIterator<Node>  iterator,Index<Node> schemaIndex){
        while (iterator.hasNext()){
            Node node = iterator.next();
            //对个体工商户节点，id字段新建全文索引
            Object corpId = node.getProperty( "id", null);
            if(corpId==null) continue;
            schemaIndex.add(node, "id", corpId.toString());
        }
    }
}
