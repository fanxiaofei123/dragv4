package org.com.drag.service;

import java.util.List;

import org.com.drag.model.FacadeModel;
import org.com.drag.model.ModelDetail;
import org.com.drag.model.ModelFrontUser;
import org.com.drag.service.BaseService;

/**
 * Created by sky on 2017/4/10.
 */
public interface ModelService   extends BaseService<FacadeModel> {

	public List<? extends FacadeModel> getByTreeId(Long treeId, int page, String frontName, String type);
	public ModelDetail getModel(int id);
	public List<ModelFrontUser> getMyModels(Long treeId, int page, String frontId, String type);
	public List<ModelFrontUser> getMyApply(Long treeId, int page, String frontName,String type);
}
