package com.arcms.biz.service.system.impl;

import com.arcms.biz.dao.generation.system.LstOfValMapper;
import com.arcms.biz.domain.generation.system.LstOfVal;
import com.arcms.biz.domain.generation.system.LstOfValExample;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**ls
 *   
 *  @ProjectName: arcms  
 *  @Description: 
 *  @author zoujian  zoujian@suniusoft.com
 *  @date 15/11/23 下午11:32  
 */
@Service
@Log4j
public class LstOfValServiceImpl implements com.arcms.biz.service.system.LstOfValService {


    @Autowired
    private LstOfValMapper lstOfValMapper;

    /**
     * 根据type和name查找值
     *
     * @param type
     * @param name
     * @return
     */
    @Override
    public LstOfVal getLstOfValsByTypeAndName(String type, String name) {

        LstOfValExample lstOfValExample = new LstOfValExample();
        lstOfValExample.createCriteria().andTypeEqualTo(type).andNameEqualTo(name);

        List<LstOfVal> lstOfValList = null;

        try {

            lstOfValList = lstOfValMapper.selectByExample(lstOfValExample);

        } catch (Exception e) {

            log.error("LstOfValService.getLstOfValsByTypeAndName() is failure.", e);
        }


        if (CollectionUtils.isEmpty(lstOfValList)) {

            return null;
        }
        return lstOfValList.get(0);
    }

    /**
     * 根据type和name查找大值
     *
     * @param type
     * @param name
     * @return
     */
    @Override
    public LstOfVal getLstOfBigValsByTypeAndName(String type, String name) {

        LstOfValExample lstOfValExample = new LstOfValExample();
        lstOfValExample.createCriteria().andTypeEqualTo(type).andNameEqualTo(name);

        List<LstOfVal> lstOfBigValList = null;

        try {

            lstOfBigValList = lstOfValMapper.selectByExampleWithBLOBs(lstOfValExample);

        } catch (Exception e) {

            log.error("LstOfValService.getLstOfBigValsByTypeAndName() is failure.", e);
        }


        if (CollectionUtils.isEmpty(lstOfBigValList)) {

            return null;
        }
        return lstOfBigValList.get(0);

    }

    @Override
    public List<LstOfVal> getLstOfValsByType(String type) {

        LstOfValExample lstOfValExample = new LstOfValExample();
        lstOfValExample.createCriteria().andTypeEqualTo(type);

        List<LstOfVal> lstOfValList = null;

        try {

            lstOfValList = lstOfValMapper.selectByExample(lstOfValExample);

        } catch (Exception e) {

            log.error("getLstOfValsByType is failure.", e);
        }

        return lstOfValList;

    }


    /**
     * 保存并更新lstOfVal
     *
     * @param lstOfVal
     */
    @Override
    public void saveAndUpdateLstOfVals(LstOfVal lstOfVal) {

        LstOfValExample lstOfValExample = new LstOfValExample();
        LstOfValExample.Criteria criteria = lstOfValExample.createCriteria();
        criteria.andTypeEqualTo(lstOfVal.getType());
        criteria.andNameEqualTo(lstOfVal.getName());

        try {

            int code = lstOfValMapper.updateByExampleSelective(lstOfVal, lstOfValExample);

            if (code == 0) {

                lstOfValMapper.insertSelective(lstOfVal);
            }
        } catch (Exception e) {

            log.error("LstOfValService.saveAndUpdateLstOfVals() is failure.");
        }


    }


}
