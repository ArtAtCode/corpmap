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
}
