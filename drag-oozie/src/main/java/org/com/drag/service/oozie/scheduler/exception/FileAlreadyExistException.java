package org.com.drag.service.oozie.scheduler.exception;

/**
 * Created by zhh on 2016/12/8.
 */
public class FileAlreadyExistException extends RuntimeException {

    public FileAlreadyExistException() {
    }

    public FileAlreadyExistException(String message) {
        super(message);
    }

    public FileAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileAlreadyExistException(Throwable cause) {
        super(cause);
    }


}
