package sampile.main;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


import sampile.MainApp;
import sampile.StageManager;
import sampile.common.AbstractStage;
import sampile.common.constants.GlobalConstants;
import sun.security.krb5.internal.PAData;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

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
    private MenuBar myMenuBar ;
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

        ListIterator<Menu> menuListIterator = myMenuBar.getMenus().listIterator();
        while (menuListIterator.hasNext()){
            Menu next = menuListIterator.next();
            ListIterator<MenuItem> menuItemListIterator = next.getItems().listIterator();
            while (menuItemListIterator.hasNext()){
                MenuItem next1 = menuItemListIterator.next();
                next1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println(event.getEventType().getName());
                        MenuItem  menuItem = (MenuItem) event.getSource();
                        if ("ipConfigMenuItem".equals(menuItem.getId())){
                            ipConfigOnAction(event);
                        }
                        if ("xmlFormatMenuItem".equals(menuItem.getId())){
                            xmlFormatOnAction(event);
                        }
                        if ("aboutMenuItem".equals(menuItem.getId())){
                            aboutOnAction(event);
                        }
                    }
                });
            }
        }
    }


    public void ipConfigOnAction(ActionEvent actionEvent) {

        if (funcStage == null ) {
            System.out.println(" funcStage is null ");
            funcStage = stageManager.getStage(GlobalConstants.WINDOW.IP_CONFIG);
            //funcStage = new Stage();
            funcStage.setTitle("IpConfig");
            funcStage.getIcons().addAll(GlobalConstants.LOGO_IMAGE);
            funcStage.initStyle(StageStyle.DECORATED);
            funcStage.setFullScreen(false);
            funcStage.setMinWidth(200);
            funcStage.setMinHeight(100);
            funcStage.setMaxHeight(722);
            funcStage.setMaxWidth(837);
            funcStage.setMaximized(false);
            funcStage.setResizable(false);
            funcStage.getScene().getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        }
        funcStage.show();

        funcStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                funcStage.hide();

            }
        });
    }

    public void xmlFormatOnAction(ActionEvent actionEvent) {

    }

    public void aboutOnAction(ActionEvent actionEvent) {
        Stage stage = stageManager.getStage(GlobalConstants.WINDOW.MAIN);

    }

}
