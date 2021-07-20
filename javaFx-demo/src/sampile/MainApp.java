package sampile;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import sampile.common.constants.GlobalConstants;
import sampile.common.utils.AlertUtil;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MainApp extends Application {

    private  final StageManager stageManager = StageManager.getInstance();

    private static ProgressBar loadProgressBar ;
    private static Label initLabel ;

    @FXML
    private LoadingController controller;

    private Stage primaryStage;

    final CountDownLatch latch = new CountDownLatch(1);

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage ;

        System.out.println("开始初始化 load fxml");
        initLoading();

        primaryStage.show();

        System.out.println("开始初始化 mainView fxml");
        initMainWin(GlobalConstants.WINDOW.MAIN);

        initProgressBar(100);
        // 核心代码
        new Thread(() -> {


            Platform.runLater(() -> {// 2
                System.out.println(" --- Thread Name  runlater= " + Thread.currentThread().getName());
                //initProgressBar(100);
                try {
                    final Stage mainStage =  stageManager.getStage(GlobalConstants.WINDOW.MAIN);
                    mainStage.addEventHandler(WindowEvent.WINDOW_SHOWN, event -> {
                        //primaryStage.hide();
                    });

                    //mainStage.show();
                } catch (Throwable e) {
                    e.printStackTrace();// 3
                }
            });
        }).start();
    }

    private void initProgressBar(int  progress) {

        System.out.println(" --- (initProgressBar) Thread Name = " + Thread.currentThread().getName());
        new Thread(()-> {
            for (int initVal = 1; initVal < progress; initVal = initVal + 1) {
                if (initVal >= 100) {
                    showProgress(1.00);
                    break;
                }
                loadProgressBar.setProgress(initVal/100);
                System.out.println(" initVal = " + initVal);
                //showProgress(1/100);
                switch (initVal) {
                    case 20:
                        showInfo("正在启动中...");
                        break;
                    case 40:
                        showInfo("正在初始化目录...");
                        break;
                    case 60:
                        showInfo("正在准备工作空间...");
                        break;
                    case 100:
                        showInfo("加载完成");
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        //});
       }).start();

    }

    // 显示信息
    private  void showProgress(double initVal) {
        new Thread(()->{
            Platform.runLater(() -> loadProgressBar.progressProperty().setValue(initVal));
        });
    }

    // 显示信息
    private  void showInfo(String info) {
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
        mainStage.setOnCloseRequest(event -> {

            boolean bool = AlertUtil.AlertConfirmDialog(mainStage, "退出软件提示", "确认关闭软件吗？");
            if (bool) {
                Platform.exit();
            } else {
                System.out.println("---- show");
                mainStage.show();
            }
        });

        // 添加到缓存
        stageManager.addStage(GlobalConstants.WINDOW.MAIN, mainStage);

    }


    public static void main(String[] args) {
        launch(args);
    }
}


