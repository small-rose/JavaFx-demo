package sampile;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import sampile.common.AbstractStage;
import sampile.common.constants.GlobalConstants;
import sampile.common.utils.AlertUtil;

import java.io.IOException;

public class MainApp extends Application {

    private  final StageManager stageManager = StageManager.getInstance();

    private static Label infoLb ;

    @FXML
    private LoadingController controller;

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage ;

        System.out.println("开始初始化 load fxml");
        initLoading();

       /*
        System.out.println("add loading ");
        stageManager.addStage(GlobalConstants.WINDOW.LOADING, 600, 375);
        System.out.println("add main ");

        stageManager.addStage(GlobalConstants.WINDOW.MAIN, 1200, 600);
        System.out.println("add ipconfig ");

        stageManager.addStage(GlobalConstants.WINDOW.IP_CONFIG, 820, 300);
*/

        primaryStage.show();

        System.out.println("开始初始化 mainView fxml");
        initMainWin(GlobalConstants.WINDOW.MAIN);
        initIpConfig(GlobalConstants.WINDOW.IP_CONFIG);
        // 核心代码
        new Thread(() -> {

            initSystem();// 1

            Platform.runLater(() -> {// 2
                try {
                    final Stage mainStage =  stageManager.getStage(GlobalConstants.WINDOW.MAIN);

                    mainStage.addEventHandler(WindowEvent.WINDOW_SHOWN, event -> {
                        primaryStage.hide();
                    });
                    mainStage.show();
                } catch (Throwable e) {
                    e.printStackTrace();// 3
                }
            });
        }).start();
    }

    // 初始化系统
    private void initSystem() {
        try {
            showInfo("初始化目录...");
            Thread.sleep(1000);
            showInfo("初始化系统配置...");
            Thread.sleep(1000);
            showInfo("版本检测...");
            Thread.sleep(1000);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    // 显示信息
    public static void showInfo(String info) {

        //Platform.runLater(() -> infoLb.setText(info));
    }



    private void initLoading() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("loading.fxml"));
        primaryStage.setScene(new Scene(root, 600, 375));
        primaryStage.initStyle(StageStyle.UNDECORATED);//设定窗口无边框
    }


    private void initMainWin(String fxmlName) {
        Parent main = null;
        try {
            main = FXMLLoader.load(getClass().getResource(fxmlName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage mainStage = new Stage();

        mainStage.setTitle("张小菜");
        mainStage.setScene(new Scene(main, 1200, 660));
        mainStage.getIcons().addAll(new Image(MainApp.class.getResourceAsStream("../images/icon/logo-3.png")));
        //设定默认窗口边框
        mainStage.initStyle(StageStyle.DECORATED);
        mainStage.initModality(Modality.APPLICATION_MODAL);
        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {

                boolean bool = AlertUtil.AlertConfirmDialog(mainStage, "退出软件提示", "确认关闭软件吗？");
                if (bool) {
                    Platform.exit();
                } else {
                    System.out.println("---- show");
                    mainStage.show();
                }
            }
        });

        // 添加到缓存
        stageManager.addStage(GlobalConstants.WINDOW.MAIN, mainStage);

    }

    private void initIpConfig(String fxmlName) {
        Stage funcStage = new Stage();
        funcStage.setTitle("IpConfig");
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("function/IpConfigView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        funcStage.setScene(new Scene(anchorPane, 550, 320));
        funcStage.initStyle(StageStyle.DECORATED);
        funcStage.setFullScreen(false);
        funcStage.setMinWidth(200);
        funcStage.setMinHeight(100);

        funcStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                funcStage.hide();

            }
        });
        stageManager.addStage(fxmlName, funcStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
