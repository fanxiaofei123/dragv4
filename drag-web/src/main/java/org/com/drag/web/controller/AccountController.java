package org.com.drag.web.controller;

import org.apache.commons.lang.StringUtils;
import org.com.drag.model.User;
import org.com.drag.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by cdyoue on 2016/11/7.
 */
@Controller
@RequestMapping("/drag/acount")
public class AccountController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "active",method = RequestMethod.GET)
    public String active(User user,Model model){
        User users =  userService.selectByUser(user);
        if(users != null && StringUtils.isNotBlank(user.getemail()) && StringUtils.isNotBlank(user.getToken())){
            users.setStatus(Byte.parseByte("1"));
            users.setToken("");
            userService.updateByPrimaryKeySelective(users);
            model.addAttribute("messageTips","activited");
            return "login/login";
        }

        return "common/activationFailed.jsp";
    }
    @RequestMapping(value = "toModify",method = RequestMethod.GET)
    public String toModify(User user, Model model){

        if(StringUtils.isBlank(user.getemail() )|| StringUtils.isBlank(user.getToken())) {
            return "common/activation_failed";
        }
        User users =  userService.selectByUser(user);

        if(users == null){
            return "common/activation_failed";
        }
        model.addAttribute("id", users.getId());
        return "login/retrieve";
    }

}
