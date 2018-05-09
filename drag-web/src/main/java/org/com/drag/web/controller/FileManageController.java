package org.com.drag.web.controller;

import org.apache.hadoop.fs.*;
import org.com.drag.common.page.PageBean;
import org.com.drag.common.result.ResponseResult;
import org.com.drag.common.util.Constants;
import org.com.drag.model.Node;
import org.com.drag.model.User;
import org.com.drag.service.oozie.api.IHdfsApi;
import org.com.drag.service.oozie.bean.FileInfo;
import org.com.drag.web.util.ReadFileUtil;
import org.datanucleus.store.types.backed.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.com.drag.common.util.MailAuthorSetting.HDFS_PATH_FEIX;
import static org.com.drag.web.common.ListSort.fileInfoListSort;

/**
 * Created by huangyu on 2017/7/25.
 */
@Controller
@RequestMapping("drag/filemanage")
public class FileManageController {

    public static final int DEFAULT_ROWCOUNT=5;
    public static final int DEFAULT_PAGENUM=1;

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    private IHdfsApi iHdfsApi;

    /**
     * 异步加载所有子节点
     * @param id 节点id
     * @param name
     * @param curDir  节点hdfs路径
     * @param session
     * @return
     */
    @RequestMapping("shownode")
    @ResponseBody
    public ResponseResult showNode(String id, String name, String curDir, String pId, HttpSession session){
        User user = (User) session.getAttribute(Constants.USER_KEY);
        List<FileInfo> fileStatusList =  iHdfsApi.list(curDir,user.getLoginname());
        List<Node> nodes=new ArrayList<Node>();
        long idCount= Long.valueOf(String.valueOf(session.getAttribute("idCount")));
        for (FileInfo fileInfo : fileStatusList) {
            if(fileInfo.getCurDir().indexOf("_SUCCESS")==-1){
            Node node = new Node();
            node.setId(idCount);
            node.setpId(Long.valueOf(id));
            node.setName(fileInfo.getPath().getName());
            node.setCurDir(String.valueOf(fileInfo.getPath()));
            if (fileInfo.isDir()){
                node.setIsParent(true);
            }else{
               node.setIsParent(false);
            }
            idCount++;
            nodes.add(node);
            }
        }
      /*  List<FileInfo> fileInfoList=new ArrayList<>();
        List<FileInfo> csvList=getAllCsv(curDir,user.getLoginname(),fileInfoList,session);
        session.setAttribute("csvList",csvList);*/
        session.setAttribute("idCount",idCount);
        for (Node node : nodes) {
            System.out.println(node);
        }
            return new ResponseResult(HttpStatus.OK,"加载成功",nodes);
    }

/*    *//**
     * 异步加载所有子节点
     * @param session
     * @return
     *//*
    @RequestMapping("showboxnode")
    public @ResponseBody List<Node> showboxNode(HttpSession session){
        String currentDir="datas";
        User user = (User) session.getAttribute(Constants.USER_KEY);
        List<String> defaultList= new ArrayList<>();
        List<FileInfo> fileStatusList =  iHdfsApi.list(currentDir,user.getLoginname());
        List<Node> nodes=new ArrayList<Node>();
        Node nodeone= new Node();
        int id=2;
        nodeone.setId(Long.valueOf(id));
        nodeone.setName("我的文件");
        nodeone.setIsParent(true);
        nodeone.setpId(Long.valueOf(0));
        nodeone.setCurDir("datas/");
        nodes.add(nodeone);
        long idcount= Long.valueOf(String.valueOf(session.getAttribute("idcount")));
        for (FileInfo fileInfo : fileStatusList) {
            Node node = new Node();
            node.setId(idcount);
            node.setpId(Long.valueOf(2));
            node.setName(fileInfo.getPath().getName());
            node.setCurDir(String.valueOf(fileInfo.getPath()));
            node.setIsParent(true);
//            if (fileInfo.isdir()){
//                node.setIsParent(true);
//            }else{
//                node.setIsParent(false);
//            }
            idcount++;
            nodes.add(node);

        }
      *//*  List<FileInfo> fileInfoList=new ArrayList<>();
        List<FileInfo> csvList=getAllCsv(curDir,user.getLoginname(),fileInfoList,session);
        session.setAttribute("csvList",csvList);*//*
        session.setAttribute("idcount",idcount);
        for (Node node : nodes) {
            System.out.println(node);
        }
        return nodes;
    }*/


    /**
     * 异步加载所有弹框的树的目录
     * @param id 节点id
     * @param name
     * @param curDir  节点hdfs路径
     * @param session
     * @return
     */
    @RequestMapping("boxNode")
    @ResponseBody
    public ResponseResult boxNode(String id, String name, String curDir, HttpSession session){
        User user = (User) session.getAttribute(Constants.USER_KEY);
        List<FileInfo> fileStatusList =  iHdfsApi.list(curDir,user.getLoginname());
        List<Node> nodes=new ArrayList<Node>();
        long idCount= Long.valueOf(String.valueOf(session.getAttribute("idCount")));
        for (FileInfo fileInfo : fileStatusList) {
            Node node = new Node();
            node.setId(idCount);
            node.setpId(Long.valueOf(id));
            node.setName(fileInfo.getPath(
            ).getName());
            node.setCurDir(String.valueOf(fileInfo.getPath()));
            if (fileInfo.isDir()){    //只要文件夹D
                node.setIsParent(true);
                nodes.add(node);
                idCount++;
            }



        }
        session.setAttribute("idCount",idCount);
        return new ResponseResult(HttpStatus.OK,"加载成功",nodes);
    }

    /**
     *
     *添加文件夹
     * @param id
     * @param pId
     * @param name
     * @param curDir 当前节点的路径
     * @param session
     * @return
     */
    @RequestMapping("addDir")
    @ResponseBody
    public ResponseResult addNode(Integer id, Integer pId, String name, String curDir, HttpSession session) throws IOException {
        User user = (User) session.getAttribute(Constants.USER_KEY);
        ResponseResult result = new ResponseResult(HttpStatus.EXPECTATION_FAILED,"文件夹已存在");
        StringBuffer path = new StringBuffer(curDir);

        FileSystem fileSystem = iHdfsApi.getFileSystem(user.getLoginname());
        Path curpath = new Path(curDir);
        boolean mkdir=fileSystem.exists(curpath);
        if (!mkdir) {
            Boolean flag = iHdfsApi.createUserWorkspace(path.toString(), user.getLoginname(), false);
            if (flag) {
                result = new ResponseResult(HttpStatus.OK,"新建文件夹成功");
            }else {
                result = new ResponseResult(HttpStatus.EXPECTATION_FAILED,"新建文件夹失败");
            }
        }
        return result;
    }


    /**
     * 级联删除文件夹及其子文件夹或文件
     * @param curDir
     * @param session
     * @return
     */
    @RequestMapping("delDir")
    @ResponseBody
    public ResponseResult delDir(String curDir, HttpSession session){
        User user = (User) session.getAttribute(Constants.USER_KEY);
        List<FileInfo> fileStatusList =  iHdfsApi.list(curDir,user.getLoginname());
        Boolean result=iHdfsApi.deleteFileOrDir(curDir,user.getLoginname(),true);
        session.removeAttribute("curDir");//删除操作之后把curDir路径清空，执行fileInfoList方法的时候重新去查相应路径下所有的文件
        return result ? new ResponseResult(HttpStatus.OK,"删除成功"):new ResponseResult(HttpStatus.EXPECTATION_FAILED,"删除失败");

    }

    /**
     * 重命名文件夹或文件
     * @param id
     * @param curDir
     * @param newName
     * @param session
     * @return
     */
    @RequestMapping("rename")
    @ResponseBody
    public ResponseResult reName(String id, String curDir, String newName, HttpSession session) throws IOException {
        User user = (User) session.getAttribute(Constants.USER_KEY);
        ResponseResult result = new ResponseResult(HttpStatus.EXPECTATION_FAILED, "文件已存在");

        String[] strs=curDir.split("/");
        String path="";
        for (int i=0;i<strs.length-1;i++){
            path=path+strs[i]+"/";
        }
        path=path+newName;
        FileSystem fileSystem = iHdfsApi.getFileSystem(user.getLoginname());
        Path curpath = new Path(path);
        boolean mkdir = fileSystem.exists(curpath);
        if (!mkdir) {
            Boolean flag = iHdfsApi.renameDirectoryOrFile(curDir, newName, user.getLoginname());
            if (flag) {
                result = new ResponseResult(HttpStatus.OK, "重命名成功");
            } else {
                result = new ResponseResult(HttpStatus.EXPECTATION_FAILED, "重命名失败");
            }
        }
        return result;
    }

    /**
     * 文件列表的展示和分页
     * @param page 页码
     * @param curDir  路径
     * @param rowCount   每页展示的条数
     * @param session
     * @return
     */
    @RequestMapping("showfilelist")
    @ResponseBody
    public PageBean fileInfoList(Integer page,String curDir,Integer rowCount,HttpSession session){
        if (page==null|| page==0){
            page=DEFAULT_PAGENUM;
        }
        if (rowCount==null){
            rowCount=DEFAULT_ROWCOUNT;
        }
        if (curDir==null||curDir==""){
            curDir=Constants.DATAS_SUFFIX;
        }
        User user = (User) session.getAttribute(Constants.USER_KEY);
        List<FileInfo> fileInfoList=new ArrayList<>();
        List<FileInfo> csvList = new ArrayList<>();
        String currentDir= (String) session.getAttribute("curDir");
        if (currentDir==null||!currentDir.equals(curDir)){
           csvList=getAllCsv(curDir,user.getLoginname(),fileInfoList,session);
                fileInfoListSort(csvList);
            session.setAttribute("csvList",csvList);
            session.setAttribute("curDir",curDir);
        }else {
            csvList= (List<FileInfo>) session.getAttribute("csvList");
        }
        List<FileInfo> filelist=new ArrayList<>();
        int startItem=(page-1)*rowCount;
        int endItem=page*rowCount-1;
        int totalPage=0;
        if (rowCount>csvList.size()){
            totalPage=1;
        }else{
            totalPage=csvList.size()%rowCount==0?csvList.size()/rowCount:csvList.size()/rowCount+1;
        }

        if (endItem>csvList.size()-1){
            endItem=csvList.size()-1;
        }
        PageBean pageBean = new PageBean();

        pageBean.setTotal(csvList.size());
        pageBean.setTotalPage(totalPage);
        pageBean.setCurPage(page);

        for (int i=startItem;i<=endItem;i++){
            filelist.add(csvList.get(i));
        }
        pageBean.setRows(filelist);



        return pageBean;
    }


    /**
     * 文件搜索
     * @param page
     * @param rowCount
     * @param inputName 输入的内容
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping("inputfilelist")
    @ResponseBody
    public PageBean inputFileList(Integer page,Integer rowCount,String inputName,HttpSession session) throws IOException {
        if (page==null|| page==0){
            page=DEFAULT_PAGENUM;
        }
        if (rowCount==null){
            rowCount=DEFAULT_ROWCOUNT;
        }
        String curDir=Constants.DATAS_SUFFIX;
        User user = (User) session.getAttribute(Constants.USER_KEY);
        List<FileInfo> pageList=new ArrayList<>();
        List<FileInfo> inputList = new ArrayList<>();
        String inputName2= (String) session.getAttribute("inputName");
        if (inputName2==null||!inputName2.equals(inputName)){
            FileSystem fs = iHdfsApi.getFileSystem(user.getLoginname());
            RemoteIterator<LocatedFileStatus> files = fs.listFiles(new Path(HDFS_PATH_FEIX+user.getLoginname()+"/"+Constants.DATAS_SUFFIX+"/"), true);
            Pattern pattern = Pattern.compile(".*"+inputName+".*");
            while (files.hasNext()) {
                Path path = files.next().getPath();
                if(String.valueOf(path).indexOf("_SUCCESS")==-1){
                if (pattern.matcher(path.toString()).matches())
                {
                    System.out.println(path);
                    FileStatus fileStatus=fs.getFileStatus(path);
                    FileInfo fileInfo = new FileInfo();
                    fileInfo.setCurDir(String.valueOf(fileStatus.getPath()));
                    fileInfo.setModificationTime(fileStatus.getModificationTime());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String modifiTime=simpleDateFormat.format(new Date(fileStatus.getModificationTime()));
                    fileInfo.setModifiedTime(modifiTime);
                    fileInfo.setPath(fileStatus.getPath());
                    inputList.add(fileInfo);
                }
                }
            }
                 fileInfoListSort(inputList);
            session.setAttribute("inputList",inputList);
            session.setAttribute("inputName",inputName);
        }else {
            inputList= (List<FileInfo>) session.getAttribute("inputList");
        }

        int startItem=(page-1)*rowCount;
        int endItem=page*rowCount-1;
        int totalPage=0;
        if (rowCount>inputList.size()){
            totalPage=1;
        }else{
            totalPage=inputList.size()%rowCount==0?inputList.size()/rowCount:inputList.size()/rowCount+1;
        }

        if (endItem>inputList.size()-1){
            endItem=inputList.size()-1;
        }
        PageBean pageBean = new PageBean();

        pageBean.setTotal(inputList.size());
        pageBean.setTotalPage(totalPage);
        pageBean.setCurPage(page);

        for (int i=startItem;i<=endItem;i++){
            pageList.add(inputList.get(i));
        }
        pageBean.setRows(pageList);

        return pageBean;
    }

    /**
     * 查出所有的csv文件
     * @param currentDir
     * @param userName
     * @param fileInfoList
     * @param session
     * @return
     */
    private List<FileInfo> getAllCsv(String currentDir, String userName ,List<FileInfo> fileInfoList , HttpSession session){
        List<FileInfo> fileStatusList =  iHdfsApi.list(currentDir,userName);
        for (FileInfo fileInfo : fileStatusList) {
            if (fileInfo.getCurDir().indexOf("_SUCCESS")==-1){
                String curDir= String.valueOf(fileInfo.getPath());
                if (fileInfo.isDir()){
                    getAllCsv(curDir,userName,fileInfoList,session);
                }else {
                    fileInfoList.add(fileInfo);
                }
            }
        }
        return fileInfoList;
    }

    /**
     * 文件预览
     * @param curDir
     * @param session
     * @return
     */
    @RequestMapping(value = "readfile",method = RequestMethod.POST)
    @ResponseBody
    public List<List<String>> readCsv(String curDir, HttpSession session) {
        List<List<String>> table=ReadFileUtil.readCsvCount(curDir,iHdfsApi,session);
        return table;
    }


    /**
     * 文件下载
     * @param curDir
     * @param request
     * @param response
     * @param session
     * @throws IOException
     */
    @RequestMapping(value = "download")
    public void downLoadHdfsFile(String curDir, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        User user = (User) session.getAttribute(Constants.USER_KEY);
        FileSystem fileSystem = iHdfsApi.getFileSystem(user.getLoginname());
        FSDataInputStream is = null;
        try {
            Path path = new Path(curDir);
            String fileName= path.getName();
/*
            fileName=new String(fileName.getBytes("iso8859-1"), "UTF-8");
            fileName = URLEncoder.encode(fileName,"UTF-8");
            if (fileName!=null && !fileName.equals("")) {
                // 设置头部信息
                response.setContentType("multipart/form-data;charset=UTF-8");
                response.setHeader(
                        "Content-disposition",
                        "attachment;filename="
                                + URLEncoder.encode(fileName, "UTF-8"));
            }
*/
            if(fileName!=null && !fileName.trim().equals("")){
                response.setContentType("multipart/form-data");

                String userAgent = request.getHeader("User-Agent");

                // 针对IE或者以IE为内核的浏览器：
                if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                    fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
                } else {
                    // 非IE浏览器的处理：
                    fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
                }
                response.setHeader("Content-disposition",
                        String.format("attachment; filename=\"%s\"", fileName));
                response.setCharacterEncoding("UTF-8");
            }
            if(!fileSystem.exists(new Path(curDir))){
                throw new FileNotFoundException("File["+curDir+"] does not exists.");
            }
            is = fileSystem.open(path);
            // 输出流
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[1024*1024];
            int bytesRead;
            while (-1 != (bytesRead = is.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bos.flush();
            is.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            fileSystem.close();
        }

    }

    /**
     * 上传前判断上传文件是否存在
     * @param path  上传文件的路径
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping("checkupload")
    @ResponseBody
    public ResponseResult checkUpload(String path,HttpSession session) throws IOException {
        User user = (User) session.getAttribute(Constants.USER_KEY);
        FileSystem fileSystem = iHdfsApi.getFileSystem(user.getLoginname());
        Path curpath = new Path(path);
        boolean uploadfile = fileSystem.exists(curpath);
        return uploadfile ? new ResponseResult(HttpStatus.EXPECTATION_FAILED, "文件已存在") : new ResponseResult(HttpStatus.OK,"上传成功");
    }



}
