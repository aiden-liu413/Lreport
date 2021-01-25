package com.dsmp.report.provider;

import com.bstek.ureport.provider.report.ReportProvider;

/**
 * @author byliu
 **/
public abstract class VenusProvider implements ReportProvider {

    public String prefix;
    public String suffix;
    private static final String ureport_suffix = "ureport.xml";
    /**
     * 获取没有前缀的文件名
     *
     * @param name 报表名称
     */
    public String getCorrectName(String name){
        name = name.replace(ureport_suffix, suffix);
        if(name.startsWith(prefix)){
            name = name.substring(prefix.length());
        }
        return name;
    }

    public VenusProvider(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }
}
