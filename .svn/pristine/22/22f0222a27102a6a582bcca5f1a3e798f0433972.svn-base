package org.com.drag.dao;

import org.apache.ibatis.annotations.Param;
import org.com.drag.model.DataMining;
import org.com.drag.model.DataMiningCategory;

import java.util.List;

public interface DataMiningMapper extends BaseDao<DataMining> {

    List<DataMining> getManyOrOneDataMining(@Param("dataMiningName") String dataMiningName,@Param("id") Integer id);

    int updateDataMining(DataMining dataMining);

    DataMining selectOneDataMiningById(Integer dataMiningId);

    int deleteOneDataMiningById(Integer dataMiningId);

    int deleteManyDataMinings(@Param("ids") String[] dataMiningIds);

    List<DataMining> selectDataMining(DataMining dataMining);

    List<DataMining> getManyDataMining(Integer id);

    int insertDataMining(DataMining dataMining);

    int updateDataMiningName(@Param("id") Integer id, @Param("name") String name);

    DataMining selectDataMiningReName(@Param("dataMiningId") Integer dataMiningId);

    int selectSum(@Param("cid") Integer cid,@Param("name") String name);

    int deleteManyOrOneDataMining(@Param("ids") List<Integer> ids);
}
