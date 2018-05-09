package org.com.drag.service;

import org.com.drag.model.Backmsg;

import java.util.List;

public interface BackmsgService extends BaseService<Backmsg> {

    /**
     * 通过传入值模糊查询
     * @param cal
     * @return
     */
    List<Backmsg> selectBySelectiveTime(Backmsg cal);

}