package org.com.drag.dao;

import org.apache.ibatis.annotations.Param;
import org.com.drag.model.DatabaseConnect;

import java.util.List;

public interface DatabaseConnectMapper {
    int deleteByPrimaryKey(Integer db_con_id);

    int insert(DatabaseConnect record);

    int insertSelective(DatabaseConnect record);

    DatabaseConnect selectByPrimaryKey(Integer dbConId);

    int updateByPrimaryKeySelective(DatabaseConnect record);

    int updateByPrimaryKey(DatabaseConnect record);

    List<DatabaseConnect> selectByUserID(int userid);

    //判断用户新建的连接名是否已经存在。
    int selectByUserIdAndConnName(@Param("d") DatabaseConnect record);

    //判断用户的连接地址是否存在
    int selectByUserIdAndUrl(@Param("d") DatabaseConnect record);

    DatabaseConnect findByUserIdAndConnName(@Param("d") DatabaseConnect record);

    int updateConnEnadbleByUserIdAndConnName(DatabaseConnect record);

    //int deleteByUserIdAndConnName(DatabaseConnect databaseConnect);
}