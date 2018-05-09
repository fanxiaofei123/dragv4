package org.com.drag.model;

import java.io.Serializable;

/**
 * Created by cdyoue on 2016/12/19.
 */
public class Block implements Serializable{

    /**
	
	 * @fieldName: serialVersionUID
	
	 * @fieldType: long
	
	 * @Description: TODO
	
	 */
	private static final long serialVersionUID = -2809295989762951865L;
	private String BlockId;
    private String BlockContent;
    private String BlockX;
    private String BlockY;
    private String BlockBorClass;
    private String BlockBgClass;
    private String BlockFontClass;
    private String BlockFontIconClass;
    private String Name;

    public String getBlockId() {
        return BlockId;
    }

    public void setBlockId(String blockId) {
        BlockId = blockId;
    }

    public String getBlockContent() {
        return BlockContent;
    }

    public void setBlockContent(String blockContent) {
        BlockContent = blockContent;
    }

    public String getBlockX() {
        return BlockX;
    }

    public void setBlockX(String blockX) {
        BlockX = blockX;
    }

    public String getBlockY() {
        return BlockY;
    }

    public void setBlockY(String blockY) {
        BlockY = blockY;
    }

    public String getBlockBorClass() {
        return BlockBorClass;
    }

    public void setBlockBorClass(String blockBorClass) {
        BlockBorClass = blockBorClass;
    }

    public String getBlockBgClass() {
        return BlockBgClass;
    }

    public void setBlockBgClass(String blockBgClass) {
        BlockBgClass = blockBgClass;
    }

    public String getBlockFontClass() {
        return BlockFontClass;
    }

    public void setBlockFontClass(String blockFontClass) {
        BlockFontClass = blockFontClass;
    }

    public String getBlockFontIconClass() {
        return BlockFontIconClass;
    }

    public void setBlockFontIconClass(String blockFontIconClass) {
        BlockFontIconClass = blockFontIconClass;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
