package org.com.drag.service.impl;

import org.com.drag.dao.TemplateCategoryDao;
import org.com.drag.model.TemplateCategory;
import org.com.drag.service.TemplateCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("templateCategoryService")
public class TemplateCategoryServiceImpl extends BaseServiceImpl<TemplateCategory> implements TemplateCategoryService {
    private static final Logger logger = LoggerFactory.getLogger(TemplateCategoryServiceImpl.class);

    @Autowired
    private TemplateCategoryDao templateCategoryDao;

    @Override
    public List<TemplateCategory> selectZtree(Integer userid, Integer pid, Integer isParent) {
        return templateCategoryDao.selectZtree(userid,pid,isParent);
    }

    @Override
    public List<TemplateCategory> selectList(Integer userid, String pid, Integer isParent) {
        return templateCategoryDao.selectList(userid,pid,isParent);
    }

    @Override
    public Integer deleteById(String[] templateCategoryId) {

        return templateCategoryDao.deleteById(templateCategoryId);
    }
}