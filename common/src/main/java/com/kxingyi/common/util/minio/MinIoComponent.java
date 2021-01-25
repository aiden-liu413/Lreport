package com.kxingyi.common.util.minio;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 **/
@Component
public class MinIoComponent {
    /**
     * 排序
     */
    public final static boolean SORT = true;
    /**
     * 不排序
     */
    public final static boolean NOT_SORT = false;
    /**
     * 斜线
     */
    public static final String slash = "/";
    /**
     * 默认过期时间(分钟)
     */
    private final static Integer DEFAULT_EXPIRY = 60;
    /**
     * 时间格式化对象
     */
    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final Logger logger = LoggerFactory.getLogger(MinIoComponent.class);
    /**
     * 默认的存储桶
     */
    private static String chunkBucKet;
    /**
     * minioclient对象
     */
    private static MinioClient minioClient;

    private static List suffixWhiteList;

    public static void init(List suffixWhiteList, MinioClient minioClient, String chunkBucKet) {
        MinIoComponent.suffixWhiteList = suffixWhiteList;
        MinIoComponent.minioClient = minioClient;
        MinIoComponent.chunkBucKet = chunkBucKet;
    }

    /**
     * 存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return true/false
     */

    public static boolean isBucketExist(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            throw new RuntimeException("the method : [isBucketExist] invoke failed");
        }
    }

    /**
     * 创建存储桶
     *
     * @param bucketName 存储桶名称
     * @return true/false
     */

    public static boolean createBucket(String bucketName) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            throw new RuntimeException("the method : [createBucket] invoke failed");
        }
        return true;
    }

    /**
     * 获取访问对象的外链地址
     *
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param expiry     过期时间(分钟) 最大为7天 超过7天则默认最大值
     * @return viewUrl
     */

    public static String getObjectUrl(String bucketName, String objectName, Integer expiry) {
        expiry = expiryHandle(expiry);
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(expiry)
                            .build()
            );
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            throw new RuntimeException("the method : [getObjectUrl] invoke failed");
        }
    }

    /**
     * 创建上传文件对象的外链
     *
     * @param bucketName 存储桶名称
     * @param objectName 欲上传文件对象的名称
     * @param expiry     过期时间(分钟) 最大为7天 超过7天则默认最大值
     * @return uploadUrl
     */

    public static String createUploadUrl(String bucketName, String objectName, Integer expiry) {
        expiry = expiryHandle(expiry);
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(expiry)
                            .build()
            );
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            throw new RuntimeException("the method : [createUploadUrl] invoke failed");
        }
    }

    /**
     * 创建上传文件对象的外链
     *
     * @param bucketName 存储桶名称
     * @param objectName 欲上传文件对象的名称
     * @return uploadUrl
     */
    public static String createUploadUrl(String bucketName, String objectName) {
        return createUploadUrl(bucketName, objectName, DEFAULT_EXPIRY);
    }

    /**
     * 批量创建分片上传外链
     *
     * @param bucketName 存储桶名称
     * @param objectMD5  欲上传分片文件主文件的MD5
     * @param chunkCount 分片数量
     * @return uploadChunkUrls
     */
    public static List<String> createUploadChunkUrlList(String bucketName, String objectMD5, Integer chunkCount) {
        if (null == bucketName) {
            bucketName = chunkBucKet;
        }
        if (null == objectMD5) {
            return null;
        }
        objectMD5 += "/";
        if (null == chunkCount || 0 == chunkCount) {
            return null;
        }
        List<String> urlList = new ArrayList<>(chunkCount);
        for (int i = 1; i <= chunkCount; i++) {
            String objectName = objectMD5 + i + ".chunk";
            urlList.add(createUploadUrl(bucketName, objectName, DEFAULT_EXPIRY));
        }
        return urlList;
    }

    /**
     * 创建指定序号的分片文件上传外链
     *
     * @param bucketName 存储桶名称
     * @param objectMD5  欲上传分片文件主文件的MD5
     * @param partNumber 分片序号
     * @return uploadChunkUrl
     */
    public static String createUploadChunkUrl(String bucketName, String objectMD5, Integer partNumber) {
        if (null == bucketName) {
            bucketName = chunkBucKet;
        }
        if (null == objectMD5) {
            return null;
        }
        objectMD5 += "/" + partNumber + ".chunk";
        return createUploadUrl(bucketName, objectMD5, DEFAULT_EXPIRY);
    }

    /**
     * 获取对象文件名称列表
     *
     * @param bucketName 存储桶名称
     * @param prefix     对象名称前缀
     * @param sort       是否排序(升序)
     * @return objectNames
     */

    public static List<String> listObjectNames(String bucketName, String prefix, Boolean sort) {
        ListObjectsArgs listObjectsArgs;
        if (null == prefix) {
            listObjectsArgs = ListObjectsArgs.builder()
                    .bucket(bucketName)
                    .recursive(true)
                    .build();
        } else {
            listObjectsArgs = ListObjectsArgs.builder()
                    .bucket(bucketName)
                    .prefix(prefix)
                    .recursive(true)
                    .build();
        }
        Iterable<Result<Item>> chunks = minioClient.listObjects(listObjectsArgs);
        List<String> chunkPaths = new ArrayList<>();
        for (Result<Item> item : chunks) {
            try {
                chunkPaths.add(item.get().objectName());
            } catch (Exception e) {
                logger.info(e.getMessage(), e);
                throw new RuntimeException("the method : [listObjectNames] invoke failed");
            }
        }
        if (sort) {
            return chunkPaths.stream().distinct().collect(Collectors.toList());
        }
        return chunkPaths;
    }

    /**
     * 获取对象文件名称列表
     *
     * @param bucketName 存储桶名称
     * @param prefix     对象名称前缀
     * @return objectNames
     */
    public static List<String> listObjectNames(String bucketName, String prefix) {
        return listObjectNames(bucketName, prefix, NOT_SORT);
    }

    /**
     * 获取分片文件名称列表
     *
     * @param bucketName 存储桶名称
     * @param ObjectMd5  对象Md5
     * @return objectChunkNames
     */
    public static List<String> listChunkObjectNames(String bucketName, String ObjectMd5) {
        if (null == bucketName) {
            bucketName = chunkBucKet;
        }
        if (null == ObjectMd5) {
            return null;
        }
        return listObjectNames(bucketName, ObjectMd5, SORT);
    }

    /**
     * 获取分片名称地址HashMap key=分片序号 value=分片文件地址
     *
     * @param bucketName 存储桶名称
     * @param ObjectMd5  对象Md5
     * @return objectChunkNameMap
     */
    public static Map<Integer, String> mapChunkObjectNames(String bucketName, String ObjectMd5) {
        if (null == bucketName) {
            bucketName = chunkBucKet;
        }
        if (null == ObjectMd5) {
            return null;
        }
        List<String> chunkPaths = listObjectNames(bucketName, ObjectMd5);
        if (null == chunkPaths || chunkPaths.size() == 0) {
            return null;
        }
        Map<Integer, String> chunkMap = new HashMap<>(chunkPaths.size());
        for (String chunkName : chunkPaths) {
            Integer partNumber = Integer.parseInt(chunkName.substring(chunkName.indexOf("/") + 1, chunkName.lastIndexOf(".")));
            chunkMap.put(partNumber, chunkName);
        }
        return chunkMap;
    }

    /**
     * 合并分片文件成对象文件
     *
     * @param chunkBucKetName   分片文件所在存储桶名称
     * @param composeBucketName 合并后的对象文件存储的存储桶名称
     * @param chunkNames        分片文件名称集合
     * @param objectName        合并后的对象文件名称
     * @return true/false
     */

    public static boolean composeObject(String chunkBucKetName, String composeBucketName, List<String> chunkNames, String objectName) {
        if (null == chunkBucKetName) {
            chunkBucKetName = chunkBucKet;
        }
        List<ComposeSource> sourceObjectList = new ArrayList<>(chunkNames.size());
        for (String chunk : chunkNames) {
            sourceObjectList.add(
                    ComposeSource.builder()
                            .bucket(chunkBucKetName)
                            .object(chunk)
                            .build()
            );
        }
        try {
            minioClient.composeObject(
                    ComposeObjectArgs.builder()
                            .bucket(composeBucketName)
                            .object(objectName)
                            .sources(sourceObjectList)
                            .build()
            );
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            throw new RuntimeException("the method : [composeObject] invoke failed");
        }
        return true;
    }

    /**
     * 合并分片文件成对象文件
     *
     * @param bucketName 存储桶名称
     * @param chunkNames 分片文件名称集合
     * @param objectName 合并后的对象文件名称
     * @return true/false
     */
    public static boolean composeObject(String bucketName, List<String> chunkNames, String objectName) {
        return composeObject(chunkBucKet, bucketName, chunkNames, objectName);
    }

    /**
     * @param bucketName: 存储桶名称
     * @param folderPath: 文件夹路径 注意：这里的路径不包含存储桶
     **/
    public static void createEmptyFolder(String bucketName, String folderPath) {
        try {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(folderPath + slash).stream(
                    new ByteArrayInputStream(new byte[]{}), 0, -1)
                    .build());
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            throw new RuntimeException("the method : [createEmptyFolder] invoke failed");
        }

    }

    /**
     * 将分钟数转换为秒数
     *
     * @param expiry 过期时间(分钟数)
     * @return expiry
     */
    private static int expiryHandle(Integer expiry) {
        expiry = expiry * 60;
        if (expiry > 604800) {
            return 604800;
        }
        return expiry;
    }

    public static void createDefaultBucket() {
        try {
            if (!isBucketExist(chunkBucKet)) {
                createBucket(chunkBucKet);
            }
        } catch (Exception e) {
            logger.info("create default bucket failed" + ": " + e.getMessage());
        }
    }

    public static InputStream getObject(String objectName) {
        return getObject(chunkBucKet, objectName);
    }

    /**
     * @param date:   时间
     * @param uuid:   文件名（uuid）
     * @param suffix: 文件后缀
     **/
    public static InputStream getObject(Date date, String uuid, String suffix) {
        return getObject(chunkBucKet, assembleObjectName(date, uuid, suffix));
    }

    /**
     * @param objectName: 要下载的对象的名称
     * @param bucketName: 存储桶名称
     **/
    public static InputStream getObject(String bucketName, String objectName) {
        InputStream object = null;
        try {
            object = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            throw new RuntimeException("get object failed");
        }
        return object;
    }

    /**
     * @param in:输入流
     * @param objectName:要存储的对象名称
     * @param bucketName:存储桶名称
     **/
    public static void putObject(InputStream in, String objectName, String bucketName) {
        try {
            minioClient.putObject(PutObjectArgs.builder().stream(in, in.available(), -1L).bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            throw new RuntimeException("put object failed");
        }
    }

    /**
     * @param in:文件输入流
     * @param objectName: 要存储的对象名称
     **/
    public static void putObject(InputStream in, String objectName) {
        putObject(in, objectName, chunkBucKet);
    }

    /**
     * @param in:     输入流
     * @param date:   时间
     * @param uuid:   生产的文件名（uuid）
     * @param suffix: 文件后缀名
     **/
    public static void putObject(InputStream in, Date date, String uuid, String suffix) {
        putObject(in, assembleObjectName(date, uuid, suffix));
    }

    /**
     * @param bucketName: 存储库名
     * @param objectName: 对象名
     **/
    public static void removeObject(String bucketName, String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            throw new RuntimeException("put object failed");
        }
    }

    /**
     * @param objectName: 对象名
     **/
    public static void removeObject(String objectName) {
        removeObject(chunkBucKet, objectName);
    }

    /**
     * @param date:时间
     * @param uuid:文件名
     * @param suffix:文件后缀
     **/
    public static void removeObject(Date date, String uuid, String suffix) {
        removeObject(assembleObjectName(date, uuid, suffix));
    }

    /**
     * @param date:
     * @param uuid:
     * @param suffix:
     **/
    public static String assembleObjectName(Date date, String uuid, String suffix) {
        return sdf.format(date) + "/" + uuid + "." + suffix;
    }

    /**
     * 把从minio得到的in写入到ou
     *
     * @param in:       输入流
     * @param ou:       输出流
     * @param byteSize: 字节数组大小
     **/
    public static void transIo(InputStream in, OutputStream ou, int byteSize) {
        try {
            byte[] b = new byte[byteSize];
            int len;
            while ((len = in.read(b)) != -1) {
                ou.write(b, 0, len);
            }
        } catch (Exception e) {
            logger.info("trans IO failed" + ": " + e.getMessage());
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.info("close inputStream failed" + ": " + e.getMessage());
                }
            }
            if (null != ou) {
                try {
                    ou.close();
                } catch (IOException e) {
                    logger.info("close outputStream failed" + ": " + e.getMessage());
                }
            }
        }
    }

    /**
     * 把从minio得到的in写入到ou
     *
     * @param in: 输入流
     * @param ou: 输出流
     **/
    public static void transIo(InputStream in, OutputStream ou) {
        transIo(in, ou, 2048);
    }

    public static boolean validSuffix(String suffix) {
        if (StringUtils.isBlank(suffix)) {
            return false;
        }
        if (null == suffixWhiteList || suffixWhiteList.isEmpty()) {
            return true;
        } else {
            return suffixWhiteList.stream().anyMatch(x -> x.equals(suffix.toLowerCase()));
        }
    }
}

