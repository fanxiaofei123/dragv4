package org.com.drag.service.oozie.scheduler.oozie;

/**
 *
 * Created by zhh on 2016/9/8.
 */
public enum HandleResultEnum {

    SUCCESS("success"),
    FAIL("fail"),
    CONF_INVALID("Your configretion is null."),
    JOB_ID_INVALID("You given a invalid job id, please check your job id.");
    private String message;

    private HandleResultEnum(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
