package org.com.drag.service.oozie.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;

/**
 * Created by zhh on 2016/10/21.
 */
public class FileInfo implements Serializable{
    /**
	
	 * @fieldName: serialVersionUID
	
	 * @fieldType: long
	
	 * @Description: TODO
	
	 */
	private static final long serialVersionUID = -1779269373658622940L;
	private Path path;
    private long length;
    private boolean isDir;
    private long modificationTime;

    private String modifiedTime;

    private long accessTime;
    private FsPermission permission;
    private String owner;
    private String group;
    private String curDir;

    public String getCurDir() {
        return curDir;
    }

    public void setCurDir(String curDir) {
        this.curDir = curDir;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void setLength(long length) {
        this.length = length;
    }



    public long getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(long modificationTime) {
        this.modificationTime = modificationTime;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public long getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(long accessTime) {
        this.accessTime = accessTime;
    }

    public void setPermission(FsPermission permission) {
        this.permission = permission;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Path getPath() {
        return path;
    }

    public long getLength() {
        return length;
    }

//    public boolean getIsdir() {
//        return isdir;
//    }


    public boolean isDir() {
        return isDir;
    }

    public void setIsDir(boolean dir) {
        isDir = dir;
    }

    public FsPermission getPermission() {
        return permission;
    }

    public String getOwner() {
        return owner;
    }

    public String getGroup() {
        return group;
    }

	@Override
	public String toString() {
		return "FileInfo [path=" + path + ", length=" + length + ", isdir=" + isDir + ", modification_time="
				+ modificationTime + ", access_time=" + accessTime + ", permission=" + permission + ", owner=" + owner
				+ ", group=" + group + "]";
	}

}
