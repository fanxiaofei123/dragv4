package org.com.drag.service.impl;

import java.util.List;

import org.com.drag.dao.DatabaseConnectMapper;
import org.com.drag.model.DatabaseConnect;
import org.com.drag.service.DatabaseConnectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseConnectServiceImpl extends BaseServiceImpl<DatabaseConnect> implements DatabaseConnectService {

    @Autowired
    private DatabaseConnectMapper databaseConnectMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return databaseConnectMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(DatabaseConnect record) {
        return databaseConnectMapper.insert(record);
    }

    @Override
    public int selectByUserIdAndConnName(DatabaseConnect record) {
        return databaseConnectMapper.selectByUserIdAndConnName(record);
    }

    @Override
    public int selectByUserIdAndUrl(DatabaseConnect record) {
        return databaseConnectMapper.selectByUserIdAndUrl(record);
    }

    @Override
    public DatabaseConnect findByUserIdAndConnName(DatabaseConnect record) {
        return databaseConnectMapper.findByUserIdAndConnName(record);
    }

    @Override
    public List<DatabaseConnect> selectByUserID(int userid) {
        return databaseConnectMapper.selectByUserID(userid);
    }

    @Override
    public int updateConnEnadbleByUserIdAndConnName(DatabaseConnect record) {
        return databaseConnectMapper.updateConnEnadbleByUserIdAndConnName(record);
    }

//    @Override
//    public int deleteByUserIdAndConnName(DatabaseConnect record) {
//       return databaseConnectMapper.deleteByUserIdAndConnName(record);
//        return 0;
//    }

    @Override
    public DatabaseConnect selectByPrimaryKey(Integer id) {
        return databaseConnectMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(DatabaseConnect record) {
        return databaseConnectMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(DatabaseConnect record) {
        return databaseConnectMapper.updateByPrimaryKeySelective(record);
    }
}
