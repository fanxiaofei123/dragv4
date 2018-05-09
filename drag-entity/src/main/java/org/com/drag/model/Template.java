package org.com.drag.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Guzhendong on 2017/10/19 0019.
 */
public class Template  implements Serializable {
    private static final long serialVersionUID = 318782459021309923L;

    public Integer getVuserid() {
        return Vuserid;
    }

    public void setVuserid(Integer vuserid) {
        Vuserid = vuserid;
    }

    /**
     * 用于判断前台传入的是私人模板(null：不是;-1：是)
     */
    private Integer Vuserid;

    private String userName;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    /**
     * 文件
     */
    private MultipartFile file;

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    /**
     * 用于判断前台传入的是系统模板(null：不是;-1：是)
     */
    private Integer adminId;
    /**
     * 工作流名
     */
    private String workFlowName;
    /**
     * 模板分类名
     */
    private String templateCategoryName;
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private Integer templateId;

    /**
     * 模板案例名
     */
    private String templateName;

    /**
     * 模板分类id
     */
    private Integer tcid;

    /**
     * 用户登陆id
     */
    private Integer userid;

    /**
     * 相工作流id
     */
    private Integer flowId;

    /**
     * 模板说明
     */
    private String templateDescription;

    /**
     * 关键字
     */
    private String templateKeyword;

    /**
     * 图片路径
     */
    private String templatePictureDirectory;

    /**
     * 图片名
     */
    private String templatePictureName;

    public String getTemplateUpdateTimes() {
        return templateUpdateTimes;
    }

    public void setTemplateUpdateTimes(String templateUpdateTimes) {
        this.templateUpdateTimes = templateUpdateTimes;
    }

    /**
     * 修改时间
     */
    private String templateUpdateTimes;
    /**
     * 修改时间
     */
    private Date templateUpdateTime;

    /**
     * 1.已提交 0.未提交
     */
    private Byte templateDraft;

    /**
     * 模板说明编辑
     */
    private String templateInstruction;

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    /**
     * @return 模板案例名
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * @param templateName
     *            模板案例名
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * @return 模板分类id
     */
    public Integer getTcid() {
        return tcid;
    }

    /**
     * @param tcid
     *            模板分类id
     */
    public void setTcid(Integer tcid) {
        this.tcid = tcid;
    }

    /**
     * @return 用户登陆id
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * @param userid
     *            用户登陆id
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * @return 相工作流id
     */
    public Integer getFlowId() {
        return flowId;
    }

    /**
     * @param flowId
     *            相工作流id
     */
    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    /**
     * @return 模板说明
     */
    public String getTemplateDescription() {
        return templateDescription;
    }

    /**
     * @param templateDescription
     *            模板说明
     */
    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }

    /**
     * @return 关键字
     */
    public String getTemplateKeyword() {
        return templateKeyword;
    }

    /**
     * @param templateKeyword
     *            关键字
     */
    public void setTemplateKeyword(String templateKeyword) {
        this.templateKeyword = templateKeyword;
    }

    /**
     * @return 图片路径
     */
    public String getTemplatePictureDirectory() {
        return templatePictureDirectory;
    }

    public String getWorkFlowName() {
        return workFlowName;
    }

    public void setWorkFlowName(String workFlowName) {
        this.workFlowName = workFlowName;
    }

    public String getTemplateCategoryName() {
        return templateCategoryName;
    }

    public void setTemplateCategoryName(String templateCategoryName) {
        this.templateCategoryName = templateCategoryName;
    }

    /**
     * @param templatePictureDirectory
     *            图片路径
     */
    public void setTemplatePictureDirectory(String templatePictureDirectory) {
        this.templatePictureDirectory = templatePictureDirectory;
    }

    /**
     * @return 图片名
     */
    public String getTemplatePictureName() {
        return templatePictureName;
    }

    /**
     * @param templatePictureName
     *            图片名
     */
    public void setTemplatePictureName(String templatePictureName) {
        this.templatePictureName = templatePictureName;
    }

    /**
     * @return 修改时间
     */
    public Date getTemplateUpdateTime() {
        return templateUpdateTime;
    }

    /**
     * @param templateUpdateTime
     *            修改时间
     */
    public void setTemplateUpdateTime(Date templateUpdateTime) {
        this.templateUpdateTime = templateUpdateTime;
    }

    /**
     * @return 1.已提交 0.未提交
     */
    public Byte getTemplateDraft() {
        return templateDraft;
    }

    /**
     * @param templateDraft
     *            1.已提交 0.未提交
     */
    public void setTemplateDraft(Byte templateDraft) {
        this.templateDraft = templateDraft;
    }

    /**
     * @return 模板说明编辑
     */
    public String getTemplateInstruction() {
        return templateInstruction;
    }

    /**
     * @param templateInstruction
     *            模板说明编辑
     */
    public void setTemplateInstruction(String templateInstruction) {
        this.templateInstruction = templateInstruction;
    }
}