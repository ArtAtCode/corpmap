package edu.scu.corpmap.entity.neo4j.GraphElement;

public  class GraphNode {
    private String name;//企业或者人的名字
    private String image;
    private String id;//企业统一社会信用代码
    private int layer;

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
