package com.kxingyi.common.validator;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * 控制校验的顺序，让自定义租借最后执行
 * @author: wu_chao
 * @date: 2020/10/16
 * @time: 15:46
 */
public class ValidationSequenceGroup {

    public interface lastSequence {

    }
    @GroupSequence({Default.class, lastSequence.class})
    public interface afterDefaultSequence {

    }

}
