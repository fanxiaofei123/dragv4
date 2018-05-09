package org.com.drag.service;

import org.com.drag.model.DataMining;

import java.util.List;

public interface DataMiningService extends BaseService<DataMining> {

    List<DataMining> selectAll(DataMining dataMining);

    List<DataMining> getManyOrOneDataMining(String categoryName, Integer id);

    int updateOneDataMining(DataMining dataMining);

    DataMining selectOneDataMiningById(Integer dataMiningId);

    int deleteOneDataMining(Integer dataMiningId);

    int deleteManyDataMining(String[] dataMiningIds);

    List<DataMining> selectDataMining(DataMining dataMining);

    List<DataMining> getManyDataMining(Integer id);

    int upateDataMiningName(Integer id, String name);

    DataMining selectDataMiningReName(Integer id);

    int selectSum(Integer cid,String name);
}
