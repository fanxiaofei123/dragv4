package org.com.drag.service.impl;

import org.com.drag.dao.TemplateDao;
import org.com.drag.model.Template;
import org.com.drag.service.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("templateService")
public class TemplateServiceImpl extends BaseServiceImpl<Template> implements TemplateService {
    private static final Logger logger = LoggerFactory.getLogger(TemplateServiceImpl.class);

    @Autowired
    private TemplateDao templateDao;

    @Override
    public List<Template> selectAllByUserId(Template template) {
        return templateDao.selectAllByUserId(template);
    }

    @Override
    public Integer deleteById(String[] templateId) {
        return templateDao.deleteById(templateId);
    }

    @Override
    public Integer insertTemplate(Template template) {
        Integer i = templateDao.insertTemplate(template);
        return template.getTemplateId();
    }

    @Override
    public List<Template> selectAllByUserIdVim(Template template) {
        return templateDao.selectAllByUserIdVim(template);
    }
}