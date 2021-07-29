package cai.small.box.common.core;

import javafx.stage.Stage;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2021/7/19 0:15
 * @version: v1.0
 */
public class StageManager extends AbstractStage {

    private final static StageManager INSTANCE = new StageManager();

    public static StageManager getInstance(){
        return INSTANCE;
    }

    @Override
    public void addStage(String fxmlName, Stage stage) {
        super.addStage(fxmlName, stage);
    }

    @Override
    public Stage getStage(String fxmlName) {
       return super.getStage(fxmlName);
    }



/*
    public void init(String fxmlName){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/

    /*

    private static HashMap hashMapStage = new HashMap();


    public void addStage(String fxmlName){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlName));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.getIcons().addAll(new Image(MainApp.class.getResourceAsStream("../images/icon/logo-3.png")));
            hashMapStage.put(fxmlName, stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addStage(String fxmlName, int sceneWidth, int sceneHeight){
        try {
            if (hashMapStage.keySet().contains(fxmlName)){
                System.out.println("fxmlName : "+fxmlName +" is already load !");
                return;
            }
            System.out.println("fxmlName : "+fxmlName);
            Parent root = FXMLLoader.load(getClass().getResource(fxmlName));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, sceneWidth, sceneHeight));
            stage.getIcons().addAll(new Image(MainApp.class.getResourceAsStream("../images/icon/logo-3.png")));
            hashMapStage.put(fxmlName, stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getStage(String name){
        return (Stage) hashMapStage.get(name);
    }


    public boolean showWindow(String resource) throws IOException {
        try {
            getStage(resource).show();
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean closeWindow(String resource){
        getStage(resource).close();
        return true;

    }
*/

}
