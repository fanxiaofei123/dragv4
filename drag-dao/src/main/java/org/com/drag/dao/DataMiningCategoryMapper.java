package org.com.drag.dao;

import org.apache.ibatis.annotations.Param;
import org.com.drag.model.DataMiningCategory;

import java.util.List;
import java.util.Map;

public interface DataMiningCategoryMapper extends BaseDao<DataMiningCategory> {

    int updateCategoryById(DataMiningCategory dataMiningCategory);

    int deleteCategoryById(@Param("ids") List<Integer> ids);

    int insertMiningCategory(DataMiningCategory dataMiningCategory);

    int insertCategory(DataMiningCategory dataMiningCategory);

    int insertMining(DataMiningCategory dataMiningCategory);

    List<DataMiningCategory> selectZtree(@Param("userid") Integer userid, @Param("pId") Integer pId, @Param("isParent") Integer isParent);

    List<DataMiningCategory> selectDataMiningCategory(DataMiningCategory dataMiningCategory);

    List<Map<String, Object>> getDataMiningCategoryBall();

    DataMiningCategory selectOneDataMiningCategory(Integer id);

    int selectSum(@Param("dataMiningCategoryId") Integer dataMiningCategoryId,@Param("dataMiningCategoryName") String dataMiningCategoryName,@Param("dataMiningCategoryPid")Integer dataMiningCategoryPid,@Param("userid")Integer userid);
}
