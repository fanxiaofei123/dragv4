<%--
  Created by IntelliJ IDEA.
  User: cdyoue
  Date: 2016/11/4
  Time: 16:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="page-sidebar-wrapper">
    <!-- END SIDEBAR -->
    <div class="page-sidebar navbar-collapse collapse">
        <!-- BEGIN SIDEBAR MENU -->
        <ul class="page-sidebar-menu  page-header-fixed page-sidebar-menu-hover-submenu " id="pageSidebarMenu" data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200">
            <li id="dragIndex" class="nav-item start " data-li="1">
                <a href="${basePath}/drag/dispatcher/drag.do" class="nav-link nav-toggle">
                    <i class="icon-home"></i>
                    <span class="title">首页</span>
                    <span class="arrow"></span>
                    <span class="selected"></span>
                </a>
            </li>
            <li id="workSpace" class="nav-item" data-li="2">
                <a href="${basePath}/drag/dispatcher/workSpaceList.do" class="nav-link nav-toggle">
                    <i class="icon-briefcase"></i>
                    <span class="title">工作空间</span>
                    <span class="arrow"></span>
                    <span class="selected"></span>
                </a>
            </li>
            
            
            <li id="modelUpload" class="nav-item" data-li="8">
                <a href="javascript:;" class="nav-link nav-toggle">
                    <i class="icon-layers"></i>
                    <span class="title">模型共享</span>
                    <span class="arrow"></span>
                    <span class="selected"></span>
                </a>
                <ul class="sub-menu">
                <shiro:hasPermission name="/drag/dispatcher/modelUpdateList">
                    <li class="nav-item ">
                        <a href="${basePath}/drag/dispatcher/modelUpdateList.do" class="nav-link ">
                            <i class="icon-bar-chart"></i>
                            <span class="title">模型上传</span>
                        </a>
                    </li>
                 </shiro:hasPermission>
                 
                 <shiro:hasPermission name="/drag/dispatcher/modelAuditList">
                    <li class="nav-item ">
                        <a href="${basePath}/drag/dispatcher/modelAuditList.do" class="nav-link ">
                            <i class="icon-bulb"></i>
                            <span class="title">模型审核</span>
                        </a>
                    </li>
                 </shiro:hasPermission>   
                 <shiro:hasPermission name="/drag/dispatcher/modelSelectType">
                     <li class="nav-item ">
                        <a href="${basePath}/drag/dispatcher/modelSelectType.do" class="nav-link ">
                            <i class="icon-bulb"></i>
                            <span class="title">模型状态查看</span>
                        </a>
                    </li>
                  </shiro:hasPermission>
                 <shiro:hasPermission name="/drag/dispatcher/modelRelease">   
                     <li class="nav-item ">
                        <a href="${basePath}/drag/dispatcher/modelRelease.do" class="nav-link ">
                            <i class="icon-bulb"></i>
                            <span class="title">模型发布</span>
                        </a>
                    </li>
                 </shiro:hasPermission>
                 
                  <shiro:hasPermission name="/drag/dispatcher/modelRelease">   
                     <li class="nav-item ">
                        <a href="${basePath}/drag/dispatcher/modelSubscribe.do" class="nav-link ">
                            <i class="icon-bulb"></i>
                            <span class="title">模型订阅审核</span>
                        </a>
                    </li>
                 </shiro:hasPermission> 
                 
                <%--  <shiro:hasPermission name="/drag/dispatcher/modelOffline">
                     <li class="nav-item ">
                        <a href="${basePath}/drag/dispatcher/modelOffline.do" class="nav-link ">
                            <i class="icon-bulb"></i>
                            <span class="title">模型下线</span>
                        </a>
                    </li>
                  </shiro:hasPermission> --%>
                </ul>
            </li>
            
            
            
            <li id="uploadData" class="nav-item" data-li="3">
                <a href="javascript:;" class="nav-link nav-toggle">
                    <i class="icon-layers"></i>
                    <span class="title">数据</span>
                    <span class="arrow"></span>
                    <span class="selected"></span>
                </a>
                <ul class="sub-menu">
                    <li class="nav-item ">
                        <a href="${basePath}/drag/dispatcher/uploadData.do" class="nav-link ">
                            <i class="icon-bar-chart"></i>
                            <span class="title">私有数据</span>
                        </a>
                    </li>
                    <li class="nav-item ">
                        <a href="${basePath}/drag/dispatcher/share.do" class="nav-link ">
                            <i class="icon-bulb"></i>
                            <span class="title">共享数据</span>
                        </a>
                    </li>
                    <li class="nav-item ">
                        <a href="${basePath}/drag/dispatcher/resultData.do" class="nav-link ">
                            <i class="icon-graph"></i>
                            <span class="title">数据结果</span>
                        </a>
                    </li>
                </ul>
            </li>
            
            
            
            <li id="loglist" class="nav-item" data-li="7">
               <a href="javascript:;" class="nav-link nav-toggle">
                    <i class="icon-layers"></i>
                    <span class="title">日志</span>
                    <span class="arrow"></span>
                    <span class="selected"></span>
                </a>
                  <ul class="sub-menu">
                   <shiro:hasPermission name="/drag/dispatcher/loglist">
                    <li class="nav-item ">
                        <a href="${basePath}/drag/dispatcher/loglist.do" class="nav-link nav-toggle">
                          <i class="icon-docs"></i>
                          <span class="title">后台日志</span>
                        </a>
                    </li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/drag/dispatcher/logFlist">
                    <li class="nav-item ">
                        <a href="${basePath}/drag/dispatcher/logFlist.do" class="nav-link ">
                            <i class="icon-bulb"></i>
                            <span class="title">前台日志</span>
                        </a>
                    </li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="/drag/dispatcher/logHlist">
                    <li class="nav-item ">
                        <a href="${basePath}/drag/dispatcher/logHlist.do" class="nav-link ">
                            <i class="icon-bulb"></i>
                            <span class="title">用户日志</span>
                        </a>
                    </li>
                    </shiro:hasPermission>
                </ul>
                
            </li>
            <li id="users" class="nav-item" data-li="4">
               <%--  <a href="${basePath}/drag/users/select.do?page=1&rows=10" class="nav-link nav-toggle"> --%>
               <a href="${basePath}/drag/dispatcher/userList.do" class="nav-link nav-toggle"> 
                    <i class="icon-user"></i>
                    <span class="title">用户</span>
                    <span class="arrow"></span>
                    <span class="selected"></span>
                </a>
            </li>
            <%--<li class="nav-item  ">--%>
                <%--<a href="javascript:;" class="nav-link nav-toggle">--%>
                    <%--<i class="icon-tag"></i>--%>
                    <%--<span class="title">标签</span>--%>
                <%--</a>--%>
            <%--</li>--%>
            <li id="modelIndex" class="nav-item" data-li="5">
                <a href="${basePath}/drag/dispatcher/modelIndex.do" class="nav-link nav-toggle">
                    <i class="icon-grid"></i>
                    <span class="title">模型示例</span>
                    <span class="arrow"></span>
                    <span class="selected"></span>
                </a>
            </li>
            <li id="workSpace" class="nav-item" data-li="12">
                <a href="${basePath}/drag/dispatcher/backMsg.do" class="nav-link nav-toggle">
                    <i class="icon-briefcase"></i>
                    <span class="title">问题反馈</span>
                    <span class="arrow"></span>
                    <span class="selected"></span>
                </a>
            </li>
            <li id="navgation" class="nav-item" data-li="6">
                <a href="${basePath}/drag/dispatcher/navigation.do" class="nav-link nav-toggle">
                    <i class="icon-question"></i>
                    <span class="title">使用帮助</span>
                    <span class="arrow"></span>
                    <span class="selected"></span>
                </a>
            </li>
            <shiro:hasPermission name="/drag/dispatcher/modeltree">
            <li id="treenode" class="nav-item" data-li="10">
                <a href="${basePath}/drag/dispatcher/modeltree.do" class="nav-link nav-toggle">
                    <i class="icon-question"></i>
                    <span class="title">模型结构</span>
                    <span class="arrow"></span>
                    <span class="selected"></span>
                </a>
            </li>
            </shiro:hasPermission>
           <%-- <li class="nav-item  ">
                <a href="javascript:;" class="nav-link nav-toggle">
                    <i class="icon-question"></i>
                    <span class="title">帮助</span>
                </a>
            </li>--%>
            <li id="uploadData" class="nav-item" data-li="11">
                <a href="javascript:;" class="nav-link nav-toggle">
                    <i class="icon-grid"></i>
                    <span class="title">应用</span>
                    <span class="arrow"></span>
                    <span class="selected"></span>
                </a>
                <ul class="sub-menu">
                    <li class="nav-item ">
                        <a href="${basePath}/vegetable/vegetable.do" class="nav-link ">
                            <i class="glyphicon glyphicon-grain"></i>
                            <span class="title">农产品价格预测</span>
                        </a>
                    </li>
                    <li class="nav-item ">
                        <a href="${basePath}/vegetable/scp.do" class="nav-link ">
                            <i class="" style="display: inline-block;width: 16px;height: 16px;background: url('${basePath}/content/images/icon/patent.png') no-repeat 0 4px;"></i>
                            <span class="title">专利价值评分</span>
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
        <!-- END SIDEBAR MENU -->
    </div>
    <!-- END SIDEBAR -->
</div>
