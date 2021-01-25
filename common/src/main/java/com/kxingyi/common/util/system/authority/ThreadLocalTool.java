package com.kxingyi.common.util.system.authority;

/**
 * @author: wu_chao
 * @date: 2020/12/25
 * @time: 14:28
 */
public class ThreadLocalTool {

    private static ThreadLocal tl = new ThreadLocal();

    public static ThreadLocal getTreadLocal() {
        return tl;
    }


}
