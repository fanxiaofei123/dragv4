package org.com.drag.web.controller.interfaceController;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.com.drag.enums.MenuType;
import org.com.drag.model.FacadeTree;
import org.com.drag.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 菜单操作controller
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
	
	@Autowired
	private TreeService treeService;
	
	/**
	 * 获取菜单
	 * */
	@RequestMapping(value = "/menu")
	@ResponseBody
	public List<FacadeTree> getMenuz(HttpServletRequest request, HttpServletResponse response){
		List<FacadeTree> tree = treeService.selectAll();
		return tree;
	}
	
	/**
	 * 获取菜单
	 * */
	@RequestMapping(value = "/menus")
	@ResponseBody
	public EnumMap<MenuType, LinkedList<FacadeTree>> getMenus(HttpServletRequest request, HttpServletResponse response){
		EnumMap<MenuType,LinkedList<FacadeTree>> tree = treeService.getTree();
		return tree;
	}
	
	/**
	 * 获取菜单
	 * */
	@RequestMapping(value = "/options")
	@ResponseBody
	public List<FacadeTree> getMenusByOptions(HttpServletRequest request, HttpServletResponse response){
		String type = request.getParameter("type");
		String frontusername = request.getParameter("frontusername");
		List<FacadeTree> tree = treeService.getByOptions(type, frontusername);
		return tree;
	}
}
