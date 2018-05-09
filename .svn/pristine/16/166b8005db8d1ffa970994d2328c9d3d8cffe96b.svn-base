package org.com.drag.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * Created by cdyoue on 2016/12/3.
 */
public class Model implements Serializable {
    /**
	
	 * @fieldName: serialVersionUID
	
	 * @fieldType: long
	
	 */
	private static final long serialVersionUID = -1281111232628910074L;
	private Integer id;
    private String BlockId;
    private String targetBlockId;
    public String getBlockId() {
        return BlockId;
    }
    public Integer trainedModelId;

    public Integer getTrainedModelId() {
        return trainedModelId;
    }

    public void setTrainedModelId(Integer trainedModelId) {
        this.trainedModelId = trainedModelId;
    }

    public void setBlockId(String blockId) {
        BlockId = blockId;
    }
    List<ModelAttribute> data = new ArrayList<>();

    public List<ModelAttribute> getData() {
        return data;
    }

    public void setData(String dataStr) {
        List<ModelAttribute> modelAttributes = JSON.parseArray(dataStr, ModelAttribute.class);
        this.data = modelAttributes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = Model.class.getResourceAsStream("/functionConfig.properties");
            prop.load(input);
            Map<String, String> proMap = new HashMap<String, String>((Map) prop);
            String functionName = proMap.get(id+"");
            if(StringUtils.isNotBlank(functionName)){
                initData(functionName,6);
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        this.id = id;
    }



    public void initData(String name,Integer type){
        ModelAttribute modelAttribute = new ModelAttribute();
        modelAttribute.setMvalue(name);
        modelAttribute.setMattribute("function");
        modelAttribute.setType(type);
        List<ModelAttribute> modelAttributes = new ArrayList<>();
        modelAttributes.add(modelAttribute);

        modelAttributes.addAll(data);

        this.data = modelAttributes;
    }

    public String getTargetBlockId() {
        return targetBlockId;
    }

    public void setTargetBlockId(String targetBlockId) {
        this.targetBlockId = targetBlockId;
    }

	@Override
	public String toString() {
		return "Model [id=" + id + ", BlockId=" + BlockId + ", targetBlockId=" + targetBlockId + ", data=" + data + "]";
	}
    
    
}
