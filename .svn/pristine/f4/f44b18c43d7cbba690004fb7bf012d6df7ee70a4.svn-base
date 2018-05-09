package org.com.drag.service;

import org.apache.ibatis.annotations.Param;
import org.com.drag.model.TemplateCategory;

import java.util.List;

public interface TemplateCategoryService extends BaseService<TemplateCategory> {
    /**
     * 查询ztree
     * @return
     */
    List<TemplateCategory> selectZtree(@Param("userid")Integer userid, @Param("pid") Integer pid, @Param("isParent")Integer isParent);

    List<TemplateCategory> selectList(@Param("userid")Integer userid, @Param("pid") String pid, @Param("isParent")Integer isParent);

    Integer deleteById(String[] templateCategoryId);
}