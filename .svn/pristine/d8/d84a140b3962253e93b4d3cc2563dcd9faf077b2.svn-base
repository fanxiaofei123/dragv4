package org.com.drag.model;

import org.apache.commons.collections.map.HashedMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cdyoue on 2016/12/13.
 */
public class ConnectsInfo {
    private List<String> joinPoint = new ArrayList<>();
    @SuppressWarnings("unchecked")
	private Map<String, List<Connect>> ruleChain = new HashedMap();

    public List<String> getJoinPoint() {
        return joinPoint;
    }

    public void setJoinPoint(List<String> joinPoint) {
        this.joinPoint = joinPoint;
    }

    public Map<String, List<Connect>> getRuleChain() {
        return ruleChain;
    }

    public void setRuleChain(Map<String, List<Connect>> ruleChain) {
        this.ruleChain = ruleChain;
    }

    public ConnectsInfo(List<String> joinPoint, Map<String, List<Connect>> ruleChain) {
        this.joinPoint = joinPoint;
        this.ruleChain = ruleChain;
    }

    public ConnectsInfo() {
    }
}
