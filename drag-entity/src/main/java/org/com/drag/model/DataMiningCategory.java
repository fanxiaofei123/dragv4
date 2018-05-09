package org.com.drag.model;

import java.io.Serializable;

/**
 *
 * @author Guzhendong
 *
 */
public class DataMiningCategory  implements Serializable {
    private static final long serialVersionUID = 482503208042514932L;

    private Integer dataMiningCategoryId;

    private String dataMiningCategoryDescription;

    public String getDataMiningCategoryDescription() {
        return dataMiningCategoryDescription;
    }

    public void setDataMiningCategoryDescription(String dataMiningCategoryDescription) {
        this.dataMiningCategoryDescription = dataMiningCategoryDescription;
    }

    /**
     * 目录分类名称
     */
    private String dataMiningCategoryName;

    /**
     * 父节点id
     */
    private Integer dataMiningCategoryPid;

    /**
     * 是否是叶子节点，是：1，否：0
     */
    private Byte dataMiningCategoryIsParent;

    private Integer userid;

    private String dataMiningName;
    private String dataMiningDescription;

    public String getDataMiningName() {
        return dataMiningName;
    }

    public void setDataMiningName(String dataMiningName) {
        this.dataMiningName = dataMiningName;
    }

    public String getDataMiningDescription() {
        return dataMiningDescription;
    }

    public void setDataMiningDescription(String dataMiningDescription) {
        this.dataMiningDescription = dataMiningDescription;
    }

    public Integer getDataMiningCategoryId() {
        return dataMiningCategoryId;
    }

    public void setDataMiningCategoryId(Integer dataMiningCategoryId) {
        this.dataMiningCategoryId = dataMiningCategoryId;
    }

    /**
     * @return 目录分类名称
     */
    public String getDataMiningCategoryName() {
        return dataMiningCategoryName;
    }

    /**
     * @param dataMiningCategoryName
     *            目录分类名称
     */
    public void setDataMiningCategoryName(String dataMiningCategoryName) {
        this.dataMiningCategoryName = dataMiningCategoryName;
    }

    /**
     * @return 父节点id
     */
    public Integer getDataMiningCategoryPid() {
        return dataMiningCategoryPid;
    }

    /**
     * @param dataMiningCategoryPid
     *            父节点id
     */
    public void setDataMiningCategoryPid(Integer dataMiningCategoryPid) {
        this.dataMiningCategoryPid = dataMiningCategoryPid;
    }

    /**
     * @return 是否是叶子节点，是：1，否：0
     */
    public Byte getDataMiningCategoryIsParent() {
        return dataMiningCategoryIsParent;
    }

    /**
     * @param dataMiningCategoryIsParent
     *            是否是叶子节点，是：1，否：0
     */
    public void setDataMiningCategoryIsParent(Byte dataMiningCategoryIsParent) {
        this.dataMiningCategoryIsParent = dataMiningCategoryIsParent;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}