package edu.scu.corpmap.configuration;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * Created by Vicent_Chen on 2018/6/17.
 */
@Configuration
public class TestConfiguration {

    @Value("${neo4j.embedded-path}")
    private String dbPath;

    @Bean(destroyMethod = "shutdown")
    public GraphDatabaseService graphDatabaseService() {
        GraphDatabaseService graphDatabaseService = new GraphDatabaseFactory().newEmbeddedDatabase(new File(dbPath));
        return graphDatabaseService;
    }

}
