package edu.scu.corpmap.service;

import edu.scu.corpmap.entity.neo4j.TestMovie;
import edu.scu.corpmap.repositories.TestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by Vicent_Chen on 2018/6/13.
 */
// Service类必须添加Service注解
// Service类一般用于与数据库交互
@Service
public class TestService {

    private final TestRepository testRepository;
    public TestService(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Transactional(readOnly = true)
    public TestMovie findByTitle(String title) {
        return testRepository.findByTitle(title);
    }

    @Transactional(readOnly = true)
    public Collection<TestMovie> findByTitleLike(String title) {
        return testRepository.findByTitleLike(title);
    }

    @Transactional(readOnly = true)
    public Integer countAll() {
        return testRepository.countAll();
    }
}
