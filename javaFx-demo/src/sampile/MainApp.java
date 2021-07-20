package sampile;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import sampile.common.constants.GlobalConstants;
import sampile.common.utils.AlertUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MainApp extends Application {

    private  final StageManager stageManager = StageManager.getInstance();

    private static ProgressBar loadProgressBar ;
    private static Label initLabel ;

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

            //initSystem();// 1
            initProgressBar(1.00);
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

    private void initProgressBar(Double  progress) {
        new Thread(()->{
            for (double initVal = 0 ; initVal < progress ; initVal = initVal + 0.01){
                if (initVal>=1){
                    loadProgressBar.setProgress(1.00);
                    break;
                }
                loadProgressBar.setProgress(initVal);
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    // 初始化系统
    private void initSystem() {
        try {

            showInfo("启动中...");
            initProgressBar((double) 0.10);
            Thread.sleep(1000);

            initProgressBar((double) 0.20);
            Thread.sleep(1000);
            showInfo("初始化目录...");
            initProgressBar((double) 0.30);
            Thread.sleep(1000);

            showInfo("初始化系统配置...");
            initProgressBar((double) 0.50);
            Thread.sleep(1000);

            showInfo("初始化工作空间...");
            initProgressBar((double) 0.70);
            Thread.sleep(1000);

            initProgressBar((double) 0.90);
            Thread.sleep(500);

            showInfo("版本检测...");
            initProgressBar((double) 1.00);
            Thread.sleep(500);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    // 显示信息
    public static void showInfo(String info) {
        final String infoShow = info ;
        new Thread(() -> {
                Platform.runLater(() -> initLabel.textProperty().set(infoShow));
        }).start();
    }



    private void initLoading() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("loading.fxml"));
        Label loadLabel = (Label) root.lookup("#loadLabel");
        initLabel = loadLabel ;
        ProgressBar progressBar = (ProgressBar) root.lookup("#loadProgressBar");
        loadProgressBar = progressBar ;
        primaryStage.setScene(new Scene(root, 600, 375));
        primaryStage.getIcons().addAll(GlobalConstants.LOGO_IMAGE);
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
        mainStage.setScene(new Scene(main, 1000, 600));
        mainStage.getIcons().addAll(GlobalConstants.LOGO_IMAGE);
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
        funcStage.setScene(new Scene(anchorPane, 850, 520));
        funcStage.initStyle(StageStyle.DECORATED);
        funcStage.getIcons().addAll(GlobalConstants.LOGO_IMAGE);
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
