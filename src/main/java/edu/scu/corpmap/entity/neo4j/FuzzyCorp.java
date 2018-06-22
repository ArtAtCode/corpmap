package edu.scu.corpmap.entity.neo4j;

public class FuzzyCorp {
    private String corpName;
    private long graphId;//节点在数据库中的底层id（唯一的），非“统一社会信用代码”

    public long getGraphId() {
        return graphId;
    }

    public void setGraphId(long graphId) {
        this.graphId = graphId;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }
}
