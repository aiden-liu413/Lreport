package com.kxingyi.common.service;

public abstract class AbstractService<Vo, Bo, Do> extends BaseService {
    public abstract Vo toVo(Do doo);

    public abstract Do toDo(Bo bo);


}
