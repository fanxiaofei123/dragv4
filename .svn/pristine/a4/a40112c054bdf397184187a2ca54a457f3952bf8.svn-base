package org.com.drag.service.impl;

import org.com.drag.dao.DatabaseTypeMapper;
import org.com.drag.model.DatabaseType;
import org.com.drag.service.DatabaseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseTypeServiceImpl extends BaseServiceImpl<DatabaseType> implements DatabaseTypeService{

    @Autowired
    private DatabaseTypeMapper databaseTypeMapper;

    @Override
    public int deleteByPrimaryKey(Short db_type_id) {
        return databaseTypeMapper.deleteByPrimaryKey(db_type_id);
    }

    @Override
    public int insert(DatabaseType record) {
        return databaseTypeMapper.insert(record);
    }

    @Override
    public DatabaseType selectByPrimaryKey(Short db_type_id) {
        return databaseTypeMapper.selectByPrimaryKey(db_type_id);
    }

    @Override
    public int updateByPrimaryKey(DatabaseType record) {
        return databaseTypeMapper.updateByPrimaryKey(record);
    }
}
