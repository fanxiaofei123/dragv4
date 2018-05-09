
package org.com.drag.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class FacadeTree implements Serializable, Comparable<FacadeTree>{

	private static final long serialVersionUID = -2662826829072025129L;

	private Long id;

    private Date ctime;

    private String name;

    private Long pid;
    
    private Integer nodeindex;
    
    /**
     * 为方便向前端传递数据，这里添加子节点引用
     * @deprecated 现在组装树形结构放在前端
     * */
    private List<FacadeTree> children;
    
    /**
     * 为方便构建树形结构，这里添加是否叶子节点属性
     * */
    private Integer isleaf;
    
    /**
     * 菜单分类，例如通用模型、主题模型等
     * */
    private String type;
    
    public Integer getNodeindex() {
        return nodeindex;
    }

    public void setNodeindex(Integer nodeindex) {
        this.nodeindex = nodeindex;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * @deprecated
     * */
	public List<FacadeTree> getChildren() {
		return children;
	}

	/**
     * @deprecated
     * */
	public void setChildren(List<FacadeTree> children) {
		this.children = children;
	}

	public Integer getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(Integer isleaf) {
		this.isleaf = isleaf;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int compareTo(FacadeTree tree) {
		if(tree.getId().equals(this.getId())){
			return 0;
		}else{
			if(tree.getType().equals(this.getType())){
				if(tree.getPid().equals(this.getPid())){
					return this.getNodeindex()-tree.getNodeindex();
				}else{
					return 0;
				}
			}else{
				return 0;
			}
		}
	}
}