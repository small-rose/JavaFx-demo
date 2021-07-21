package sampile;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
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
        this.primaryStage = primaryStage;

        System.out.println("开始初始化 load fxml");
        initLoading();

        primaryStage.show();

        System.out.println("开始初始化 mainView fxml");
        initMainWin(GlobalConstants.WINDOW.MAIN);

        //给进度条设置初始值
        //loadProgressBar.setProgress(0.0);
        initLabel.setText("TEST");
        //获取Task任务(也就是主逻辑方法)
        Task<Integer> myTask  = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                System.out.println(" thread : " + Thread.currentThread().getName());
                for (int initVal = 9; initVal < 101; initVal = initVal + 1) {
                    updateProgress(initVal, 100);
                    switch (initVal) {
                        case 20:
                            updateMessage("正在启动中...");
                            break;
                        case 40:
                            updateMessage("正在初始化目录...");
                            break;
                        case 60:
                            updateMessage("正在准备工作空间...");
                            break;
                        case 100:
                            updateMessage("加载完成");
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                return 100;
            }
        };

        //绑定进度条的值属性
        loadProgressBar.progressProperty().unbind();
        loadProgressBar.progressProperty().bind(myTask.progressProperty());

        //绑定TextField的值属性
        //initLabel.textProperty().unbind();
        //initLabel.textProperty().bind(myTask.messageProperty());

        //添加Task的监听方法:如果Task的Message变化,则会经过该方法
        myTask.messageProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue);
                initLabel.setText(newValue);
                Platform.runLater(() -> initLabel.setText(newValue));
            }
        });


        //使用新线程启动 -- 这是重中之重
        new Thread(myTask).start();

        final Stage mainStage =  stageManager.getStage(GlobalConstants.WINDOW.MAIN);

        loadProgressBar.progressProperty().addListener((observable, oldValue, newValue) -> {
            //System.out.println(" new val : "+newValue);
            if (newValue.doubleValue() == 1.00){
                mainStage.show();
            }
        });

        Platform.runLater(()->{

            mainStage.addEventHandler(WindowEvent.WINDOW_SHOWN, event -> {
                primaryStage.hide();
            });
            //mainStage.show();
        });

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
        this.initLabel = loadLabel ;
        ProgressBar progressBar = (ProgressBar) root.lookup("#loadProgressBar");
        this.loadProgressBar = progressBar ;
        this.primaryStage.setScene(new Scene(root, 600, 375));
        this.primaryStage.getIcons().addAll(GlobalConstants.LOGO_IMAGE);
        this.primaryStage.initStyle(StageStyle.UNDECORATED);//设定窗口无边框

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


