package edu.scu.corpmap.service;

import edu.scu.corpmap.dao.HotCorpMapper;
import edu.scu.corpmap.entity.mysql.HotCorp;
import edu.scu.corpmap.entity.mysql.HotCorpExample;
import edu.scu.corpmap.exception.HotCorpException;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static edu.scu.corpmap.exception.HotCorpException.*;


/**
 * Created by Vicent_Chen on 2018/6/20.
 */
@Service
public class HotCorpService {

    @Autowired
    private HotCorpMapper hotCorpMapper;

    /**
     * 返回热搜榜前5名
     * @return
     */
    @Transactional(readOnly = true)
    public List<HotCorp> getTop5HotCorp() {
        HotCorpExample hotCorpExample = new HotCorpExample();
        hotCorpExample.setOrderByClause("count DESC");
        hotCorpExample.setOffset(0);
        hotCorpExample.setLimit(5);
        List<HotCorp> list = hotCorpMapper.selectByExampleUsingLimit(hotCorpExample);
        return list;
    }

    /**
     * 根据ID返回热搜榜上的企业，可能返回null
     * @param id
     */
    @Transactional(readOnly = true)
    public HotCorp getHotCoprById(String id) {
        if (id == null) {
            throw new HotCorpException(EMPTY_ID_MESSAGE);
        }
        HotCorp hotCorp = hotCorpMapper.selectByPrimaryKey(id);
        return hotCorp;
    }

    /**
     * 搜索次数+1
     * 若热搜榜中不存在该公司，则自动插入
     * @param id 公司ID，不为空
     * @param name 公司名称，不为空
     */
    @Transactional
    public void plusOne(String id, String name) {
        if (id == null) {
            throw new HotCorpException(EMPTY_ID_MESSAGE);
        }
        if (name == null) {
            throw new HotCorpException(EMPTY_NAME_MESSAEG);
        }

        HotCorp hotCorp =  getHotCoprById(id);
        if (hotCorp == null) {
            hotCorp = new HotCorp();
            hotCorp.setId(id);  hotCorp.setName(name);
            insertHotCorp(hotCorp);
        }
        else {
            hotCorp.setCount(hotCorp.getCount() + 1);
            updateHotCorp(hotCorp);
        }
    }

    /**
     * 插入企业至热搜榜，自动设置搜索次数为1，重复插入会抛出异常
     * @param hotCorp id不能为空, name不能为空
     */
    @Deprecated
    @Transactional
    public void insertHotCorp(HotCorp hotCorp) {
        if (hotCorp == null) {
            throw new HotCorpException(EMPTY_MESSAGE);
        }

        try {
            hotCorp.setCount(1L);
            hotCorpMapper.insert(hotCorp);
        }
        catch (DataIntegrityViolationException e) {
            if (hotCorp.getId() == null) {
                throw new HotCorpException(EMPTY_ID_MESSAGE);
            }
            else if (hotCorp.getName() == null) {
                throw new HotCorpException(EMPTY_NAME_MESSAEG);
            }
            else {
                throw new HotCorpException(e.getMessage());
            }
        }
    }

    /**
     * 更新企业热搜榜
     * @param hotCorp id不能为空
     */
    @Deprecated
    @Transactional
    public void updateHotCorp(HotCorp hotCorp) {
        if (hotCorp == null) {
            throw new HotCorpException(EMPTY_MESSAGE);
        }

        try {
            hotCorpMapper.updateByPrimaryKeySelective(hotCorp);
        }
        catch (DataIntegrityViolationException e) {
            if (hotCorp.getId() == null) {
                throw new HotCorpException(EMPTY_ID_MESSAGE);
            }
            else {
                throw new HotCorpException(e.getMessage());
            }
        }
    }

    /**
     * 根据ID删除热搜榜上的企业
     * @param id
     */
    @Deprecated
    @Transactional
    public void deleteHotCorp(String id) {
        if (id == null) {
            throw new HotCorpException(EMPTY_ID_MESSAGE);
        }

        try {
            hotCorpMapper.deleteByPrimaryKey(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new HotCorpException(e.getMessage());
        }
    }
}
