package edu.scu.corpmap.service;

import edu.scu.corpmap.entity.mysql.HotCorp;
import edu.scu.corpmap.exception.HotCorpException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Vicent_Chen on 2018/6/22.
 * 当且仅当数据库为空时测试能够完全通过，若不为空则有可能因为其他数据干扰导致测试失败
 * 测试已完全通过 6/22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HotCorpServiceTest {

    // illegal
    HotCorp nullHotCorp, idNullHotCorp, nameNullHotCorp, countNullHotCorp;
    // legal
    HotCorp toInsert, toUpdate;
    HotCorp[] list;

    @Autowired
    HotCorpService hotCorpService;

    public HotCorpServiceTest() {
        // illegal
        nullHotCorp = null;

        idNullHotCorp = new HotCorp();
        idNullHotCorp.setId(null); idNullHotCorp.setName("TEST"); idNullHotCorp.setCount(10L);

        nameNullHotCorp = new HotCorp();
        nameNullHotCorp.setId("TEST"); nameNullHotCorp.setName(null); nameNullHotCorp.setCount(10L);

        countNullHotCorp = new HotCorp();
        countNullHotCorp.setId("TEST"); countNullHotCorp.setName("TEST"); countNullHotCorp.setCount(null);

        // legal
        toInsert = new HotCorp();
        toInsert.setId("toInsert"); toInsert.setName("toInsert"); toInsert.setCount(0L);

        toUpdate = new HotCorp();
        toUpdate.setId("toInsert"); toUpdate.setName("toUpdate"); toUpdate.setCount(3L);

        list = new HotCorp[10];
        for(int i = 0 ; i < 10; i++) {
            list[i] = new HotCorp();
            list[i].setId("id" + i); list[i].setName("name" + i); list[i].setCount(new Long(i));
        }
    }


    @Test(expected = HotCorpException.class)
    public void test001_insertNullHotCorp() {
        hotCorpService.insertHotCorp(nullHotCorp);
    }

    @Test(expected = HotCorpException.class)
    public void test002_insertNullIdHotCorp() {
        hotCorpService.insertHotCorp(idNullHotCorp);
    }

    @Test(expected = HotCorpException.class)
    public void test003_insertNullNameHotCorp() {
        hotCorpService.insertHotCorp(nameNullHotCorp);
    }

    @Test
    public void test0040_insertHotCorp() {
        hotCorpService.insertHotCorp(toInsert);

        for (HotCorp hotCorp : list) {
            hotCorpService.insertHotCorp(hotCorp);
        }
    }

    @Test(expected = HotCorpException.class)
    public void test0041_insertHotCorpDuplicate() {
        hotCorpService.insertHotCorp(toInsert);
    }

    @Test(expected = HotCorpException.class)
    public void test005_getHotCorpByIdNull() {
        hotCorpService.getHotCoprById(null);
    }

    @Test
    public void test006_getHotCorpById() {
        HotCorp hotCorp = hotCorpService.getHotCoprById(toInsert.getId());
        assertEquals(hotCorp.getName(), toInsert.getName());
    }

    @Test(expected = HotCorpException.class)
    public void test007_updateHotCorpNull() {
        hotCorpService.updateHotCorp(nullHotCorp);
    }

    @Test
    public void test008_updateHotCorpNullId() {
        hotCorpService.updateHotCorp(idNullHotCorp);
    }

    @Test
    public void test0081_updateHotCorp() {
        hotCorpService.updateHotCorp(toUpdate);
        HotCorp hotCorp = hotCorpService.getHotCoprById(toInsert.getId());
        assertEquals(toUpdate.getName(), hotCorp.getName());
        for (HotCorp h: list)
            hotCorpService.updateHotCorp(h);
    }

    @Test
    public void test0090_updateHotCorpNotExist() {
        hotCorpService.updateHotCorp(nameNullHotCorp);
    }

    @Test(expected = HotCorpException.class)
    public void test0091_plusOneNullId() {
        hotCorpService.plusOne(null, toUpdate.getName());
    }

    @Test(expected = HotCorpException.class)
    public void test0091_plusOneNullName() {
        hotCorpService.plusOne(toUpdate.getId(), null);
    }

    @Test
    public void test0092_plusOne() {
        hotCorpService.plusOne(toUpdate.getId(), toUpdate.getName());
        HotCorp hotCorp = hotCorpService.getHotCoprById(toUpdate.getId());
        assertEquals(new Long(toUpdate.getCount() + 1), hotCorp.getCount());
    }

    @Test(expected = HotCorpException.class)
    public void test010_deleteHotCorpNull() {
        hotCorpService.deleteHotCorp(idNullHotCorp.getId());
    }

    @Test
    public void test011_deleteHotCorp() {
        hotCorpService.deleteHotCorp(toInsert.getId());
        HotCorp hotCorp = hotCorpService.getHotCoprById(toInsert.getId());
        assertEquals(null, hotCorp );
    }

    @Test
    public void test012_getTop5HotCorp() {
        List<HotCorp> list = hotCorpService.getTop5HotCorp();
        assertEquals(5, list.size());
        assertEquals(new Long(9), list.get(0).getCount());
    }

    @Test
    public void test100_shut() {
        for (HotCorp hotCorp : list)
            hotCorpService.deleteHotCorp(hotCorp.getId());
    }
}