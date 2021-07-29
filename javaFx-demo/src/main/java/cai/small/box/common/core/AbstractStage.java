package cai.small.box.common.core;

import javafx.scene.layout.Pane;
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
public abstract class AbstractStage  extends AbstractLog{

    protected static HashMap<String, Stage> hashMapStage = new HashMap<String, Stage>();

    protected static HashMap<String, Pane> hashMapPanel = new HashMap<String,Pane>();

    protected void addStage(String fxmlName, Stage stage){
        hashMapStage.put(fxmlName, stage);
    }

    public Stage getStage(String fxmlName) {
       return (Stage) hashMapStage.get(fxmlName);
    }

    protected void addPanel(String fxmlName, Pane panel){
        hashMapPanel.put(fxmlName, panel);
    }

    public Pane getPanel(String panel) {
        return  hashMapPanel.get(panel);
    }
}
