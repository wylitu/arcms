package com.suniusoft.common.biz.dao;

import com.suniusoft.common.biz.domain.IdGen;
import org.springframework.stereotype.Repository;


@Repository
public interface IdGenMapper {


    int insert(IdGen record);

}
