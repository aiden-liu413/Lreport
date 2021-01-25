package com.kxingyi.common.util.lifecycle;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author byliu
 **/
public class SceneNumberBo {
    @NotNull(message = "场景列表不能为空")
    private String sceneType;

    public String getSceneType() {
        return sceneType;
    }

    public void setSceneType(String sceneType) {
        this.sceneType = sceneType;
    }

    public SceneNumberBo(@NotNull(message = "场景列表不能为空") String sceneType) {
        this.sceneType = sceneType;
    }

    public SceneNumberBo() {
    }

    @Override
    public String toString() {
        return "SceneNumberParams{" +
                "sceneType='" + sceneType + '\'' +
                '}';
    }
}
