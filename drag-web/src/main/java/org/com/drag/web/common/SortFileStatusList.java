package org.com.drag.web.common;

import org.apache.hadoop.fs.FileStatus;
import org.com.drag.service.oozie.bean.FileInfo;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by huangyu on 2017/8/14.
 */
public class SortFileStatusList {
    public SortFileStatusList(List<FileStatus> inputList) {
        Collections.sort(inputList, new Comparator<FileStatus>() {
            @Override
            public int compare(FileStatus o1, FileStatus o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dt1 = format.parse(String.valueOf(o1.getModificationTime()));
                    Date dt2 = format.parse(String.valueOf(o2.getModificationTime()));
                    if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }


}
