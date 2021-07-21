package sampile;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Border;
import sampile.common.AbstractStage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class LoadingController extends AbstractStage implements Initializable {


    @FXML
    private ProgressBar loadProgressBar ;
    @FXML
    private Label loadLabel ;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("LoadingController ... initialize ");

        //loadLabel.setPrefSize(600,20);
        loadProgressBar.setBorder(Border.EMPTY);
        loadLabel.setText("Loading ...");




    }



    private void initProgressBar(int  progress) {

        System.out.println(" --- (initProgressBar) Thread Name = " + Thread.currentThread().getName());

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
            Platform.runLater(() -> loadLabel.textProperty().set(infoShow));
        }).start();
    }

}
