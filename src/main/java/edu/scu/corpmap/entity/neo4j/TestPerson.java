package edu.scu.corpmap.entity.neo4j;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vicent_Chen on 2018/6/14.
 */
@NodeEntity
public class TestPerson {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private int born;

    @Relationship(type = "ACTED_IN")
    private List<TestMovie> movies = new ArrayList<>();
}
