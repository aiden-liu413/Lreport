package com.kxingyi.common.validator;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

/**
 * @author: wu_chao
 * @date: 2020/9/8
 * @time: 13:33
 */
public class ValidationGroup {
    public interface create extends Default {
    }

    public interface update extends Default {
    }

    public interface other extends Default {

    }



    @GroupSequence({Default.class, update.class})
    public interface CheckSequence {

    }
}
