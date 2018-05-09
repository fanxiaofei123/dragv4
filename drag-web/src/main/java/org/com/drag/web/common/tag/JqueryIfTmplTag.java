package org.com.drag.web.common.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by honshe on 2016/9/22.
 */
public class JqueryIfTmplTag extends SimpleTagSupport {

    private String name;
    @Override
    public void doTag() throws JspException, IOException {
        super.doTag();
        JspWriter out = this.getJspContext().getOut();
        out.write("{"+name+"}");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
