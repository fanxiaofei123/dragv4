package org.com.drag.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.com.drag.model.FacadeTree;
import org.com.drag.model.Node;
import org.com.drag.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by sky on 2017/4/17.
 */
@Controller
@RequestMapping("/drag/treemode")
public class TestTreeController {
    @Autowired
    private TreeService treeService;


    /**
     * 添加目录
     * @param pid 父目录的id
     * @return
     */
    @RequestMapping("/addcommonnode")
    public @ResponseBody Long addCommonNode(Long pid){
        FacadeTree node=new FacadeTree();
        Date date=new Date();
        node.setCtime(date);
        node.setNodeindex(1);
        node.setName("new node");
        node.setPid(pid);
        node.setType("COMMON");
        treeService.addNode(node);
        treeService.updateType0(pid);//修改父节点的类型为不是叶子节点
        FacadeTree cNode=treeService.findByCTime(date);
        return cNode.getId();

    }

    @RequestMapping("/addtopicnode")
    public @ResponseBody Long addTopicNode(Long pid){
        FacadeTree node=new FacadeTree();
        Date date=new Date();
        node.setCtime(date);
        node.setNodeindex(1);
        node.setName("new node");
        node.setPid(pid);
        treeService.addNode(node);
        treeService.updateType0(pid);//修改父节点的类型为不是叶子节点
        FacadeTree cNode=treeService.findByCTime(date);
        Long id=cNode.getId();
        treeService.updateNodeType(id);
        return id;

    }

    /**
     *根据id修改此节点的name
     * @param id
     * @param newName
     */
    @RequestMapping("/updateName")
    public void updateName(Long id,String newName){
        FacadeTree node=treeService.selectById(id);
        treeService.updateNode(newName,id,node.getPid());
    }

    /**
     * 通过id删除目录及子目录
     * @param id
     * @return
     */
    @RequestMapping("/deleteNode")
    public void deleteById(Long id){
        List<FacadeTree> origin = treeService.selectAll();
        List<Long> fids=treeService.getLeafId(origin,id);
        treeService.deleteById(fids,id);
    }


    /**
     * 移动目录
     * @param moveId
     * @param targetId
     */
    @RequestMapping("/moveNode")
    public void updateNode(Long moveId,Long targetId){
        Map<String,Object> idMap= new HashMap<String,Object>();
        idMap.put("moveId",moveId);
        idMap.put("targetId",targetId);
        treeService.moveNodeById(idMap);

    }

//    /**
//     * 获取要修改的目录和父目录的信息；
//     * @param id
//     * @return
//     */
//    @RequestMapping("/getNodeInfo")
//    public @ResponseBody String [] getNodeInfo(Long id){
//        FacadeTree cNode=treeService.selectById(id);
//        String[] names=new String[2];
//        if (cNode.getPid()==0){
//            names[0]="";
//        }else {
//            FacadeTree fNode=treeService.selectById(cNode.getPid());
//            names[0]=fNode.getName();
//        }
//
//        names[1]=cNode.getName();
//        return names;
//    }
//
//    /**
//     * 修改目录的所属或者修改目录名
//     * @param pName 父目录的名称
//     * @param cName 当前目录的名称
//     * @param id    当前目录的id
//     * @return
//     */
//    @RequestMapping("/updateNode")
//    public @ResponseBody int updateNode(String pName,String cName,Long id){
//        int x=0;
//        Long pid;
//        if (pName==""||pName==null){
//            pid= Long.valueOf(0);
//            x=treeService.updateNode(cName,id,pid);
//        }else {
//            FacadeTree fNode=treeService.selectByName(pName);
//            if(fNode!=null){
//                pid=fNode.getId();
//                x=treeService.updateNode(cName,id,pid);
//            }else {
//                return x;
//            }
//        }
//         return x;
//    }

    /**
     *  查询所有的的节点
     * @param modelMap
     * @return
     */
    @RequestMapping("/selectAll")
    public @ResponseBody List<Node> selectAll(ModelMap modelMap){
        List<FacadeTree> treeList= treeService.selectAll();
        List<Node> nodes=new ArrayList<Node>();
        for (int i=0;i<treeList.size();i++){
            long id=treeList.get(i).getId();
            long pid=treeList.get(i).getPid();
            String name=treeList.get(i).getName();
            Node node= new Node();
            node.setId(id);
            node.setpId(pid);
            node.setName(name);
            nodes.add(node);
        }
        return nodes;


    }

    @RequestMapping("/checkRelease")
    public @ResponseBody String checkRelease(Long id){
        String type="allow";
        List<FacadeTree> origin = treeService.selectAll();
        List<Long> fids=treeService.getLeafId(origin,id);
        List<Long> releasedId=treeService.selectReleaseId();
        if (releasedId.size()!=0&&releasedId!=null){
            for (int i=0;i<fids.size();i++){
                for (int j=0;j<releasedId.size();j++){
                    if (fids.get(i).equals(releasedId.get(j))){
                        type= "prohibit";
                        break;
                    }
                }
            }
        }
        return type;
    }

}
