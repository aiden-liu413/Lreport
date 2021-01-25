package com.kxingyi.common.util;

import java.io.File;
import java.util.Arrays;

/**
 * @Author: chengpan
 * @Date: 2020/2/8
 */
public class FileUtil {
    public static File getNewestFile(String dir) {
        File dirFile = new File(dir);
        return Arrays.stream(dirFile.listFiles())
                .filter(f -> f.isFile())
                .sorted((f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()))
                .findFirst()
                .get();
    }
}
