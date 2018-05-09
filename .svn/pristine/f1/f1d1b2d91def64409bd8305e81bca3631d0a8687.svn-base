package org.com.drag.service.impl;

import org.com.drag.dao.BackmsgMapper;
import org.com.drag.model.Backmsg;
import org.com.drag.service.BackmsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("backmsgService")
public class BackmsgServiceImpl extends BaseServiceImpl<Backmsg> implements BackmsgService {
    private static final Logger logger = LoggerFactory.getLogger(BackmsgServiceImpl.class);

    @Autowired
    private BackmsgMapper backmsgMapper;


    @Override
    public List<Backmsg> selectBySelectiveTime(Backmsg cal) {
        return backmsgMapper.selectBySelectiveTime(cal);
    }
}