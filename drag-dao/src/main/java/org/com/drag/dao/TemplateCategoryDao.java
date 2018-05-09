package org.com.drag.dao;

import org.apache.ibatis.annotations.Param;
import org.com.drag.model.FacadeTree;
import org.com.drag.model.TemplateCategory;
import org.com.drag.model.WorkSpace;

import java.util.List;

public interface TemplateCategoryDao extends BaseDao<TemplateCategory> {
    /**
     * 查询ztree
     * @return
     */
    List<TemplateCategory> selectZtree(@Param("userid")Integer userid, @Param("pid") Integer pid, @Param("isParent")Integer isParent);

    List<TemplateCategory> selectList(@Param("userid") Integer userid, @Param("pid") String pid,  @Param("isParent")Integer isParent);

    Integer deleteById(String[] templateCategoryId);
}