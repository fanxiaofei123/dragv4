package org.com.drag.service;

import org.com.drag.model.DatabaseConnect;

import java.util.List;

public interface DatabaseConnectService extends BaseService<DatabaseConnect>{

    //TODO 根据主键删除数据。
    int deleteByPrimaryKey(Integer id);

    //插入数据
    int insert(DatabaseConnect record);

    //TODO 根据uiserId和ConnNmae查询数据。
    int selectByUserIdAndConnName(DatabaseConnect record);

    //TODO 根据userId和Url查询数据。
    int selectByUserIdAndUrl(DatabaseConnect record);

    //TODO 根据userId和ConnName查询主键。
    DatabaseConnect findByUserIdAndConnName(DatabaseConnect record) ;

    //TODO 根据userId查询数据集合。
    List<DatabaseConnect> selectByUserID(int userid);

    //TODO 更新数据。
    int updateConnEnadbleByUserIdAndConnName(DatabaseConnect record);

    //TODO 根据主键查询。
    DatabaseConnect selectByPrimaryKey(Integer id);

    //TODO 根据主键更新数据。
    int updateByPrimaryKey(DatabaseConnect record);

    @Override
    int updateByPrimaryKeySelective(DatabaseConnect record);
}
