package edu.scu.corpmap.repositories;

import edu.scu.corpmap.entity.neo4j.TestMovie;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Collection;

/**
 * Created by Vicent_Chen on 2018/6/14.
 */
public interface TestRepository extends Neo4jRepository<TestMovie, Long> {
    TestMovie findByTitle(@Param("title") String title);

    Collection<TestMovie> findByTitleLike(@Param("title") String title);

    @Query("MATCH (m:Movie)<-[r:ACTED_IN]-(a:Person) RETURN m,rr,a LIMIT {limit}")
    Collection<TestMovie> graph(@Param("limit") int limit);

    @Query("MATCH (m:Movie) RETURN count(m)")
    Integer countAll();
}
