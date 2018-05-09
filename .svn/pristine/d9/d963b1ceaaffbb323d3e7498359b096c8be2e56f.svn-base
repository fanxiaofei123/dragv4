package org.com.drag.common.result;

import java.io.Serializable;

/**
 * Created by cdyoue on 2016/11/11.
 */
public class DatasResult implements Serializable {
    /**
	
	 * @fieldName: serialVersionUID
	
	 * @fieldType: long
	
	 * @Description: TODO
	
	 */
	private static final long serialVersionUID = -7256392628069613867L;
	private String size;
    private String fileName;
    private Boolean isdir;
    private String modificationTime;
    private String hdfsUrl;



    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Boolean getIsdir() {
        return isdir;
    }

    public void setIsdir(Boolean isdir) {
        this.isdir = isdir;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(String modificationTime) {
        this.modificationTime = modificationTime;
    }

    public DatasResult(String fileName, Boolean isdir, String modificationTime,String hdfsUrl) {
        this.fileName = fileName;
        this.isdir = isdir;
        this.modificationTime = modificationTime;
        this.hdfsUrl = hdfsUrl;
    }

    public String getHdfsUrl() {
		return hdfsUrl;
	}

	public void setHdfsUrl(String hdfsUrl) {
		this.hdfsUrl = hdfsUrl;
	}

	public DatasResult() {

    }
}
