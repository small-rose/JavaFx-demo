package test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * @Project : javaFx-demo
 * @Author : zhangzongyuan
 * @Description : [ LoadingExample ] 说明：无
 * @Function :  功能说明：无
 * @Date ：2021/7/21 12:45
 * @Version ： 1.0
 **/
public class LoadingExample extends Application {

    private static Label infoLb;

    @Override
    public void start(Stage stage) throws Exception {
        Image image = new Image("https://img-blog.csdnimg.cn/20200927152800323.png");
        ImageView view = new ImageView(image);

        infoLb = new Label();
        infoLb.setTextFill(Color.WHITE);
        AnchorPane.setRightAnchor(infoLb, 10.0);
        AnchorPane.setBottomAnchor(infoLb, 10.0);

        AnchorPane page = new AnchorPane();
        page.getChildren().addAll(view, infoLb);

        stage.setTitle("eclipse");
        stage.setScene(new Scene(page));
        stage.setWidth(image.getWidth());
        stage.setHeight(image.getHeight());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("https://img-blog.csdnimg.cn/20200927163943686.png"));
        stage.show();

        // 核心代码
        new Thread(() -> {
            initSystem();// 1

            Platform.runLater(() -> {// 2
                try {
                    Home home = new Home();

                    home.addEventHandler(WindowEvent.WINDOW_SHOWN, event -> {
                        stage.hide();
                    });
                    home.show();
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
            Thread.sleep(1500);
            showInfo("初始化系统配置...");
            Thread.sleep(1500);
            showInfo("版本检测...");
            Thread.sleep(1500);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    // 显示信息
    public static void showInfo(String info) {
        Platform.runLater(() -> infoLb.setText(info));
    }

    /**
     * 主页
     *
     * @author Miaoqx
     */
    public class Home extends Stage {

        public Home() {
            Pane pane = new Pane();
            pane.getChildren().add(new Label("Hello, I'm 78KgMiao."));

            setScene(new Scene(pane));
            setTitle("Home");
            setMaximized(true);
            getIcons().add(new Image("https://img-blog.csdnimg.cn/20200927163943686.png"));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
