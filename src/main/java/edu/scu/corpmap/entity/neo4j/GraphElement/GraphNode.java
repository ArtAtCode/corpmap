package edu.scu.corpmap.entity.neo4j.GraphElement;

public  class GraphNode {
    private String name;//企业或者人的名字
    private String image;
    private String id;//企业统一社会信用代码

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GraphNode) {
            if (this.getId().equals(((GraphNode) obj).getId())) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
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
