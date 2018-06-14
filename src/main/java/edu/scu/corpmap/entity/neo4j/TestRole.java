package edu.scu.corpmap.entity.neo4j;


import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vicent_Chen on 2018/6/14.
 */
@RelationshipEntity(type = "ACTED_IN")
public class TestRole {
    @Id @GeneratedValue private Long id;
    private List<String> roles = new ArrayList<>();

    @StartNode
    private TestPerson person;

    @EndNode
    private TestMovie movie;
}
