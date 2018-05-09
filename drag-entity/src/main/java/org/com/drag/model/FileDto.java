package org.com.drag.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cdyoue on 2016/12/5.
 */
public class FileDto implements Serializable{
    /**
	
	 * @fieldName: serialVersionUID
	
	 * @fieldType: long
	
	 * @Description: TODO
	
	 */
	private static final long serialVersionUID = -8422399116946053911L;
	private FilefloderInfo filefloderInfo;
    private Integer level;
    private Integer Plevel;


    private List<FileDto> childFiles = new ArrayList<>();

    public FilefloderInfo getFilefloderInfo() {
        return filefloderInfo;
    }

    public void setFilefloderInfo(FilefloderInfo filefloderInfo) {
        this.filefloderInfo = filefloderInfo;
    }

    public List<FileDto> getChildFiles() {
        return childFiles;
    }

    public void setChildFiles(List<FileDto> childFiles) {
        this.childFiles = childFiles;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getPlevel() {
        return Plevel;
    }

    public void setPlevel(Integer plevel) {
        Plevel = plevel;
    }

	@Override
	public String toString() {
		return "FileDto [filefloderInfo=" + filefloderInfo + ", level=" + level + ", Plevel=" + Plevel + ", childFiles="
				+ childFiles + "]";
	}
    
    
}
