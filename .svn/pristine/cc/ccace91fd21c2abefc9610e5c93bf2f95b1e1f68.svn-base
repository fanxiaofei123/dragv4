package org.com.drag.service;

import org.com.drag.enums.MenuType;
import org.com.drag.model.FacadeModel;
import org.com.drag.model.FacadeTree;
import org.com.drag.service.BaseService;

import java.util.*;

/**
 * Created by sky on 2017/4/10.
 */
public interface TreeService   extends BaseService<FacadeTree> {

    List<FacadeTree> selectAll();

    List<FacadeTree> selectPNode(Integer pid);

    void deleteById(List<Long> fids,Long id);

    int addNode(FacadeTree node);

    FacadeTree selectById(Long id);

    FacadeTree selectByName(String pName);

    int updateNode(String cName, Long id, Long pid);
    
    List<FacadeTree> getByPid(Long pid);
    
    EnumMap<MenuType, LinkedList<FacadeTree>> getTree();
    
    List<FacadeTree> getLeaves(Long pid);

    FacadeTree findByCTime(Date date);

    void deleteByPId(Long id);

    void updateType0(Long pid);
    void updateType1(Long pid);

    void moveNodeById(Map<String, Object> idMap);

     List<Long> getLeafId(List<FacadeTree> origin, long pid);
     List<FacadeTree> getByOptions(String type, String frontusername);


    void updateNodeType(Long id);

    List<Long> selectReleaseId();
}
