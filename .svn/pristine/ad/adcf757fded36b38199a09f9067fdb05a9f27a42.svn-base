package org.com.drag.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cdyoue on 2016/12/3.
 */
public class Connect  implements Serializable{
    /**
	
	 * @fieldName: serialVersionUID
	
	 * @fieldType: long
	
	 * @Description: TODO
	
	 */
	private static final long serialVersionUID = -9186539434400332460L;
	private String sourceId;
    private String targetId;
    private String sourceModel;
    private String targetModel;
    private String connectType;
    private String connectSourcePosition;
    private String connectTargetPosition;
    private List<String> SourceAnchor;
    private List<String> TargetAnchor;

    public List<String> getTargetAnchor() {
        return TargetAnchor;
    }

    public void setTargetAnchor(List<String> targetAnchor) {
        TargetAnchor = targetAnchor;
    }

    public List<String> getSourceAnchor() {
        return SourceAnchor;
    }

    public void setSourceAnchor(List<String> sourceAnchor) {
        SourceAnchor = sourceAnchor;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getSourceModel() {
        return sourceModel;
    }

    public void setSourceModel(String sourceModel) {
        this.sourceModel = sourceModel;
    }

    public String getTargetModel() {
        return targetModel;
    }

    public void setTargetModel(String targetModel) {
        this.targetModel = targetModel;
    }



    //页面接受参数处理


    public void setPageSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public void setPageTargetId(String targetId) {
        this.targetId = targetId;
    }

    public void setSourceText(String sourceModel) {
        this.sourceModel = sourceModel;
    }
    public void setTargetText(String targetModel) {
        this.targetModel = targetModel;
    }

    public String getConnectType() {
        return connectType;
    }

    public void setConnectType(String connectType) {
        this.connectType = connectType;
    }

    public String getConnectSourcePosition() {
        return connectSourcePosition;
    }

    public void setConnectSourcePosition(String connectSourcePosition) {
        this.connectSourcePosition = connectSourcePosition;
    }

    public String getConnectTargetPosition() {
        return connectTargetPosition;
    }

    public void setConnectTargetPosition(String connectTargetPosition) {
        this.connectTargetPosition = connectTargetPosition;
    }

    @Override
    public String toString() {
        return "Connect{" +
                "sourceId='" + sourceId + '\'' +
                ", targetId='" + targetId + '\'' +
                ", sourceModel='" + sourceModel + '\'' +
                ", targetModel='" + targetModel + '\'' +
                ", connectType='" + connectType + '\'' +
                ", connectSourcePosition='" + connectSourcePosition + '\'' +
                ", connectTargetPosition='" + connectTargetPosition + '\'' +
                ", SourceAnchor=" + SourceAnchor +
                ", TargetAnchor=" + TargetAnchor +
                '}';
    }
}
