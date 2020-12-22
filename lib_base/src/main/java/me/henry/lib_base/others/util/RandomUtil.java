package me.henry.lib_base.others.util;

import java.util.UUID;

/**
 * 随机数生成工具类
 * Created by ChiKong on 17/9/19.
 */

public class RandomUtil {

    /**
     * 获取UUID
     * @return UUID
     */
    public static String getUUID(){
        UUID id = UUID.randomUUID();
        return id.toString();
    }
    /**
     * 获取UUID
     * @return UUID
     */
    public static String getUUID(int length){
        String id = UUID.randomUUID().toString();
        if (length < 1 || length > id.length()){
            throw new IndexOutOfBoundsException("获取UUID，非法长度");
        }
        return id.substring(0,length);
    }

}
