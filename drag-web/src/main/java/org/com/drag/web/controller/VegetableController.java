package org.com.drag.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.com.drag.dao.VegetableMapper;
import org.com.drag.model.Vegetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by cdyoue on 2016/11/7.
 */
@Controller
@RequestMapping("/vegetable")
public class VegetableController {
    @Autowired
    private VegetableMapper vegetableMapper;

    @RequestMapping(value = "/simulated")
    @ResponseBody
    public List<Vegetable> active(HttpServletRequest request,HttpServletResponse response){
        String name = request.getParameter("name");
        if(StringUtils.isBlank(name)){
        	name = null;
        }else{
        	name = name.trim();
        }
        return vegetableMapper.selectGUIYANG(name);
    }
    
    @RequestMapping(value = "/names")
    @ResponseBody
    public List<String> getNames(HttpServletRequest request,HttpServletResponse response){
    	return vegetableMapper.selectName();
    }
    
    @RequestMapping(value = "/vegetable")
    public String vegetable(HttpServletRequest request,HttpServletResponse response){
    	return "/score/vegetable";
    }
    
    @RequestMapping(value = "/scp")
    public String scp(HttpServletRequest request,HttpServletResponse response){
    	return "/score/score";
    }
}
