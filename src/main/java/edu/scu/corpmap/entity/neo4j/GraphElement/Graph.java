package edu.scu.corpmap.entity.neo4j.GraphElement;

import java.util.List;

public class Graph {

    private List<GraphNode> nodes;
    private List<GraphEdge> edges;

    public List<GraphEdge> getEdges() {
        return edges;
    }

    public void setEdges(List<GraphEdge> edges) {
        this.edges = edges;
    }

    public List<GraphNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<GraphNode> nodes) {
        this.nodes = nodes;
    }
}
