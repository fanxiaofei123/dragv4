package org.com.drag.model;

import java.util.*;

public class WorkflowNode {
    String id;
    List<String> sourceConnectTypes = new ArrayList<>();
    List<String> targetConnectTypes = new ArrayList<>();
    List<String> sourceConnectPostion = new ArrayList<>();
    List<String> targetConnectPosition = new ArrayList<>();
    List<WorkflowNode> preNodes = new ArrayList<WorkflowNode>();
    List<WorkflowNode> nextNodes = new ArrayList<WorkflowNode>();
    LinkedHashMap<String, String> attrs = new LinkedHashMap<String, String>();

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<WorkflowNode> getPreNodes() {
        return preNodes;
    }
    public void setPreNodes(List<WorkflowNode> preNodes) {
        this.preNodes = preNodes;
    }
    public List<WorkflowNode> getNextNodes() {
        return nextNodes;
    }
    public void setNextNodes(List<WorkflowNode> nextNodes) {
        this.nextNodes = nextNodes;
    }
    public Map<String, String> getAttrs() {
        return attrs;
    }
    public void setAttrs(LinkedHashMap<String, String> attrs) {
        this.attrs = attrs;
    }

    public List<String> getSourceConnectTypes() {
        return sourceConnectTypes;
    }

    public void setSourceConnectTypes(List<String> sourceConnectTypes) {
        this.sourceConnectTypes = sourceConnectTypes;
    }

    public List<String> getTargetConnectTypes() {
        return targetConnectTypes;
    }

    public void setTargetConnectTypes(List<String> targetConnectType) {
        this.targetConnectTypes = targetConnectType;
    }

    public List<String> getSourceConnectPostion() {
        return sourceConnectPostion;
    }

    public void setSourceConnectPostion(List<String> sourceConnectPostion) {
        this.sourceConnectPostion = sourceConnectPostion;
    }

    public List<String> getTargetConnectPosition() {
        return targetConnectPosition;
    }

    public void setTargetConnectPosition(List<String> targetConnectPosition) {
        this.targetConnectPosition = targetConnectPosition;
    }
}
