package org.com.drag.common.util;

import java.io.*;
import java.util.List;

/**
 * Created by cdyoue on 2016/12/16.
 */
public class DeepCopyUtils {



    /**
     * 深层拷贝
     * 通过序列反序列化深层复制的效率很低，有可能的话优化一下
     * @param list1
     * @return
     * @throws Exception
     */
    public static <T> List<T> deepClone(List<T> list1) throws Exception{
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(buf);
        out.writeObject(list1);
        out.close();
        byte[] bytes = buf.toByteArray();
        ByteArrayInputStream byIn = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(byIn);
        List<T> list2 = (List<T>) in.readObject();
        return list2;
    }

}
