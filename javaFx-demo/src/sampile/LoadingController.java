package sampile;

import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import sampile.common.AbstractStage;
import sampile.common.constants.GlobalConstants;
import sampile.main.MainController;
import sampile.common.utils.AlertUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController extends AbstractStage implements Initializable {


    @FXML
    private ProgressBar progressBar ;
    @FXML
    private Label loadLabel ;
    @FXML
    private ImageView loadingImageView;
    @FXML
    private Button startButton;

    private Stage mainStage ;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("LoadingController ... initialize ");
        //File file = new File("images/icon/logo.jpg");
        //System.out.println(file.getPath());
        //Image logoImage = new Image(file.toURI().toString());
        //loadingImageView.setImage(logoImage);

        //stageManager.addStage(GlobalConstants.WINDOW.MAIN, 1200, 600);

        loadLabel.setPrefSize(600,20);

        // 50%进度
        progressBar.setProgress(0.5);
        // 未知的进度
        progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);

        /*
        mainStage = stageManager.getStage(GlobalConstants.WINDOW.MAIN);
        mainStage.setTitle("张小菜");
        //设定默认窗口边框
        mainStage.initStyle(StageStyle.DECORATED);

        mainStage.initModality(Modality.APPLICATION_MODAL);
        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            public void handle(WindowEvent event) {

                boolean bool = AlertUtil.AlertConfirmDialog(mainStage, "退出软件提示", "确认关闭软件吗？");
                if (bool){
                    Platform.exit();
                }else{
                    System.out.println("---- show");
                    mainStage.show();
                }
            }
        });
*/
    }

}
