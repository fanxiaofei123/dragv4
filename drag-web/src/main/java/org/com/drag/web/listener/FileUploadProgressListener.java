package org.com.drag.web.listener;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;
import org.com.drag.model.UploadStatus;

/**
 * Created by cdyoue on 2016/11/14.
 */
public class FileUploadProgressListener implements ProgressListener {
    private HttpSession session;

   /* public void setSession(HttpSession session){
        this.session=session;
        UploadStatus status = new UploadStatus();
        session.setAttribute("status", status);
    }*/


    public FileUploadProgressListener(HttpSession session) {
        this.session=session;
        UploadStatus status = new UploadStatus();
        session.setAttribute("status", status);
    }

    /*
         * pBytesRead 到目前为止读取文件的比特数 pContentLength 文件总大小 pItems 目前正在读取第几个文件
         */
    public void update(long pBytesRead, long pContentLength, int pItems) {
        UploadStatus status = (UploadStatus) session.getAttribute("status");
        status.setBytesRead(pBytesRead);
        status.setContentLength(pContentLength);
        status.setItems(pItems);
        session.setAttribute("status",status);
    }
}
