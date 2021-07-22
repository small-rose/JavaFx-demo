package cai.small.box.common.core;

import javafx.stage.Stage;

import java.util.HashMap;

/**
 * @Project : javaFx-demo
 * @Author : zhangzongyuan
 * @Description : [ AbstractStage ] 说明：无
 * @Function :  功能说明：无
 * @Date ：2021/7/19 15:16
 * @Version ： 1.0
 **/
public abstract class AbstractStage {

    protected static HashMap hashMapStage = new HashMap();

    protected void addStage(String fxmlName, Stage stage){
        hashMapStage.put(fxmlName, stage);
    }

    public Stage getStage(String fxmlName) {
       return (Stage) hashMapStage.get(fxmlName);
    }
}
