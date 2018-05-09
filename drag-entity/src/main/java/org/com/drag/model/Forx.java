package org.com.drag.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cdyoue on 2016/12/13.
 */
public class Forx implements Serializable{
    /**
	
	 * @fieldName: serialVersionUID
	
	 * @fieldType: long
	
	 * @Description: TODO
	
	 */
	private static final long serialVersionUID = -3741930326895852664L;
	private String blockId;
    private List<String> targetId = new ArrayList<>();

    public Forx() {
        this.blockId = "forx-start";

    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public List<String> getTargetId() {
        return targetId;
    }

    public void setTargetId(List<String> targetId) {
        this.targetId = targetId;
    }
}
