package org.com.drag.service.oozie.scheduler.oozie;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * Created by Administrator on 2016/9/5.
 */
public class OozieHdfs {

    public static void main(String[] args) throws Exception{

        Configuration conf = new Configuration();
        FileSystem fileSystem = FileSystem.get(conf);
        fileSystem.deleteOnExit(new Path("/user/root/out1"));

    }

}
