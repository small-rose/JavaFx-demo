package cai.small.box.function;

import cai.small.MainApp;
import cai.small.box.common.core.AbstractStage;
import cai.small.box.common.core.StageManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import cai.small.box.common.constants.GlobalConstants;
import cai.small.box.common.utils.AlertUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ListIterator;
import java.util.ResourceBundle;

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
    private MenuItem formatMenuItem;

    @FXML
    private MenuItem mysqlMenuItem;

    @FXML
    private MenuItem aboutMenuItem;


    private Stage mainStage ;

    // 功能窗口
    private Stage funcStage ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainStage = stageManager.getStage(GlobalConstants.WINDOW.MAIN);
        System.out.println("MainController ... initialize ");

        if (funcStage == null ) {
            funcStage = new Stage();
            funcStage.setTitle("IpConfig");
            AnchorPane anchorPane = null;
            try {
                anchorPane = FXMLLoader.load(MainApp.class.getResource(GlobalConstants.WINDOW.IP_CONFIG));
            } catch (IOException e) {
                e.printStackTrace();
            }
            funcStage.setScene(new Scene(anchorPane, 850, 520));
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
            funcStage.setOnCloseRequest(event -> funcStage.hide());
            stageManager.addStage(GlobalConstants.WINDOW.IP_CONFIG, funcStage);
        }

        initMenuItems();

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
                        if ("formatMenuItem".equals(menuItem.getId())){
                            formatMenuItemOnAction(event);
                        }
                        if ("aboutMenuItem".equals(menuItem.getId())){
                            aboutOnAction(event);
                        }
                    }
                });
            }
        }
    }

    private void initMenuItems() {
        //ImageView imageView = new ImageView("file:/images/icon/gur-project/gur-project-01.png");
        //System.out.println(imageView);
        //ipConfigMenuItem.setGraphic(imageView);
    }


    public void ipConfigOnAction(ActionEvent actionEvent) {

        funcStage.show();
        //funcStage.setOnCloseRequest(event -> funcStage.hide());
    }

    public void formatMenuItemOnAction(ActionEvent actionEvent) {
        funcStage = stageManager.getStage(GlobalConstants.WINDOW.FORMAT_TOOL);
        if (funcStage == null ) {
            funcStage = new Stage();
            funcStage.setTitle("格式化工具");
            AnchorPane anchorPane = null;
            try {
                anchorPane = FXMLLoader.load(MainApp.class.getResource(GlobalConstants.WINDOW.FORMAT_TOOL));
            } catch (IOException e) {
                e.printStackTrace();
            }
            funcStage.setScene(new Scene(anchorPane, 850, 520));
            funcStage.initStyle(StageStyle.DECORATED);
            funcStage.getIcons().addAll(GlobalConstants.LOGO_IMAGE);
            funcStage.setFullScreen(false);
            funcStage.setMinWidth(200);
            funcStage.setMinHeight(100);
            funcStage.setMaximized(false);
            funcStage.setResizable(false);
            funcStage.getScene().getStylesheets().add(GlobalConstants.CSS.BOOTSTRAP_FX);
            stageManager.addStage(GlobalConstants.WINDOW.FORMAT_TOOL, funcStage);
        }
        funcStage.show();

        funcStage.setOnCloseRequest(event -> funcStage.hide());
    }

    public void aboutOnAction(ActionEvent actionEvent) {

        AlertUtil.alertInfoDialog(mainStage, " 提示", "功能正在开发中");
    }

}
