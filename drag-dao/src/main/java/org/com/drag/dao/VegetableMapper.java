package org.com.drag.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.com.drag.model.Vegetable;

public interface VegetableMapper {

	List<Vegetable> selectGUIYANG(@Param("name")String name);
	
	List<String> selectName();
}
