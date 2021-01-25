package com.kxingyi.common.util.strategy;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author byliu
 **/
public class SceneNumberBo {
    @NotNull(message = "场景列表不能为空")
    private List sceneTypes;

    public List getSceneTypes() {
        return sceneTypes;
    }

    public void setSceneTypes(List sceneTypes) {
        this.sceneTypes = sceneTypes;
    }

    public SceneNumberBo(@NotNull(message = "场景列表不能为空") List sceneTypes) {
        this.sceneTypes = sceneTypes;
    }

    public SceneNumberBo() {
    }

    @Override
    public String toString() {
        return "SceneNumberParams{" +
                "sceneTypes='" + sceneTypes + '\'' +
                '}';
    }
}
