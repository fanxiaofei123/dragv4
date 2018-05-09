package org.com.drag.model;

public class CalculationFrontHistory extends CalculationHistory{
	private static final long serialVersionUID = -6008231498795281803L;

    private String backemail;//上传用户的email

    public String getBackemail() {
        return backemail;
    }

    public void setBackemail(String backemail) {
        this.backemail = backemail;
    }
}