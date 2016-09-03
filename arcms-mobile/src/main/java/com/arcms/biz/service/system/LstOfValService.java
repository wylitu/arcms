package com.arcms.biz.service.system;

import com.arcms.biz.domain.generation.system.LstOfVal;

import java.util.List;

/**
 *   
 *  @ProjectName: arcms  
 *  @Description: 
 *  @author zoujian  zoujian@suniusoft.com
 *  @date 15/11/23 下午11:30  
 */
public interface LstOfValService {


    public LstOfVal getLstOfValsByTypeAndName(String type, String name);

    public LstOfVal getLstOfBigValsByTypeAndName(String type, String name);

    public List<LstOfVal> getLstOfValsByType(String type);

    public void saveAndUpdateLstOfVals(LstOfVal lstOfVal);

}