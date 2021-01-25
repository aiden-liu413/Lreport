package com.kxingyi.common.util;


import java.util.UUID;


/**
 * @author hxy
 * 用于UUID的一系列公共操作
 */
public class UUIDTool {
    /**
     * 获取定长32位的UUID作为主键使用,采用UUID4的版本进行计算
     * @return
     */
    public static String getPrimarykeyId(){
        return UUID.randomUUID().toString().replace("-","");
    }
    /**
     * 通过UUID计算出传入name的UUID值，采用UUID3的版本进行计算
     */
    public static String getUuidByName(byte[] name){
        return UUID.nameUUIDFromBytes(name).toString().replace("-","");
    }
    
    public static void main(String[] args) {
		System.out.println(getPrimarykeyId());
	}
}
