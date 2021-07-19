package sampile.main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import sampile.MainApp;
import sampile.StageManager;
import sampile.common.AbstractStage;
import sampile.common.constants.GlobalConstants;
import sampile.common.utils.AlertUtil;
import sampile.function.IpConfigController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2021/7/18 22:16
 * @version: v1.0
 */
public class MainController extends AbstractStage implements Initializable {

    private StageManager stageManager = StageManager.getInstance();
    @FXML
    private MenuItem ipConfigMenuItem;
    @FXML
    private MenuItem xmlFormatMenuItem;
    @FXML
    private MenuItem jsonFormatMenuItem;

    @FXML
    private MenuItem mysqlMenuItem;

    @FXML
    private MenuItem aboutMenuItem;

    private Stage loadState ;

    private Stage mainStage ;

    // 功能窗口
    private Stage funcStage ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("MainController ... initialize ");

    }

    private void initSystem() {
        try {
            TimeUnit.SECONDS.sleep(1);

            TimeUnit.SECONDS.sleep(1);

            TimeUnit.SECONDS.sleep(1);

            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void ipConfigOnAction(ActionEvent actionEvent) throws IOException {
        //((Node) e.getSource()).getScene().getWindow();
        //Parent function = FXMLLoader.load(getClass().getResource("../function/IpConfigView.fxml"));
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("../function/IpConfigView.fxml"));

        //mainStage = stageManager.getStage(GlobalConstants.WINDOW.MAIN);
        //mainStage.hide();
        //mainStage.setScene(new Scene(anchorPane, mainStage.getMaxWidth(), mainStage.getMaxHeight()));
        //mainStage.show();
        /*
        funcStage = new Stage();
        funcStage.setTitle("IpConfig");
        funcStage.setScene(new Scene(function, 500, 300));
        funcStage.initStyle(StageStyle.DECORATED);
        funcStage.setFullScreen(false);
        funcStage.setMinWidth(200);
        funcStage.setMinHeight(100);

        funcStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                funcStage.close();
            }
        });
        funcStage.show();
        */

        if (funcStage == null ) {
            funcStage = stageManager.getStage(GlobalConstants.WINDOW.IP_CONFIG);
            //funcStage = new Stage();
            funcStage.setTitle("IpConfig");
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

            funcStage.show();
        }else{
            funcStage.show();
        }

    }



}
