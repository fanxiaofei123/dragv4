package org.com.drag.service.impl;

import java.util.List;

import org.com.drag.dao.FacadeUploadInfoMapper;
import org.com.drag.model.FacadeUploadInfo;
import org.com.drag.service.FacadeUploadInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by sky on 2017/4/19.
 */
@Service
public class FacadeUploadInfoServiceImpl extends BaseServiceImpl<FacadeUploadInfo> implements FacadeUploadInfoService {
	
    @Autowired
    private FacadeUploadInfoMapper facadeUploadInfoMapper;

    /**
     * 通过条件查询信息
     */
	@Override
	public List<FacadeUploadInfo> selectByFacadeUploadInfo(FacadeUploadInfo fi) {

		
		return facadeUploadInfoMapper.selectByFacadeUploadInfo(fi);
	}



}
