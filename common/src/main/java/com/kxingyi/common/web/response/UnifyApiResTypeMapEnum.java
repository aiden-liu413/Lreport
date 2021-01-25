package com.kxingyi.common.web.response;


/**
 * 统一接口中的资产类型与DSMP资产类型的映射
 * @Author: chengpan
 * @Date: 2020/9/1
 */
public enum UnifyApiResTypeMapEnum {

    REDHAT("redhat", "102"),
    UBUNTU("ubuntu", "103"),
    SUSE("suse", "104"),

    AIX("aix", "201"),
    HP_UNIX("hp_unix", "202"),
    SOLARIS("solaris", "203"),

    WINDOWS7("windows 7", "301"),
    WINDOWS8("windows 8", "302"),
    WINDOWS10("windows 10", "303"),
    WINDOWSXP("windows xp", "304"),
    WINDOWSVISTA("windows vista", "305"),
    WINDOWSSERVER2000("windows server 2000", "306"),
    WINDOWSSERVER2003("windows server 2003", "307"),
    WINDOWSSERVER2008("windows server 2008", "308"),
    WINDOWSSERVER2011("windows server 2011", "309"),
    WINDOWSSERVER2012("windows server 2012", "310"),

    ORACLE("oracle", "401"),
    MYSQL("mysql", "402"),
    DB2("db2", "403"),
    POSTGRESQL("postgresql", "404"),
    SQLSERVER("sql server", "406"),
    SYBASE("sybase", "407"),

    HIVE("hive", "501"),
    HBASE("hbase", "502"),
    HDFS("hdfs", "503");


    private String dsmpCode;
    private String unifyApiCode;

    UnifyApiResTypeMapEnum(String dsmpCode, String unifyApiCode) {
        this.dsmpCode = dsmpCode;
        this.unifyApiCode = unifyApiCode;
    }

    public static String getUnifyApiCodeByDsmpCode(String dsmpCode) {
        for (UnifyApiResTypeMapEnum value : values()) {
            if (value.getDsmpCode().equalsIgnoreCase(dsmpCode)) {
                return value.getUnifyApiCode();
            }
        }
        return dsmpCode;
    }

    public static String getDsmpCodeByUnifyApiCode(String unifyApiCode) {
        for (UnifyApiResTypeMapEnum value : values()) {
            if (value.getUnifyApiCode().equalsIgnoreCase(unifyApiCode)) {
                return value.getDsmpCode();
            }
        }
        return unifyApiCode;
    }

    public String getDsmpCode() {
        return dsmpCode;
    }

    public String getUnifyApiCode() {
        return unifyApiCode;
    }
}
