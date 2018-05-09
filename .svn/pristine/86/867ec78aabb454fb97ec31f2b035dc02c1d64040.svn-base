package org.com.drag.service.oozie.scheduler.hdfs;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

/**
 * Created by Administrator on 2016/11/10.
 */
public class PathFilterImpl implements PathFilter {

    private String filter;

    public PathFilterImpl(String filter) {
        this.filter = filter;
    }

    @Override
    public boolean accept(Path path) {
        if(path.getName().contains(filter)){
            return true;
        }
        return false;
    }
}
