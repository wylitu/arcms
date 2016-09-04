package com.suniusoft.common.utils;

import com.suniusoft.common.biz.dao.IdGenMapper;
import com.suniusoft.common.biz.domain.IdGen;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *   
 *  @ProjectName: sf-crm  
 *  @Description: 全局id生成工具
 *  @author litu  litu@shufensoft.com
 *  @date 15/12/27  
 */
public class IdGenUtils {

    private static IdGenMapper idGenMapper = (IdGenMapper)SpringContextUtil.getBean("idGenMapper");

    public static  Long orderIdGen(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        String date = format.format(new Date());

        return Long.parseLong(date + idGen());

    }


    public static  Long idGen(){

        IdGen idGen = new IdGen();

        idGenMapper.insert(idGen);

        return idGen.getId();

    }

}
