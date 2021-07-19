package sampile;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import sampile.common.AbstractStage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController extends AbstractStage implements Initializable {


    @FXML
    private ProgressBar loadProgressBar ;
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

        //loadLabel.setPrefSize(600,20);
        loadProgressBar.setBorder(Border.EMPTY);
        loadLabel.setText("Loading ...");
        //loadProgressBar.set
        // 50%进度
        //loadProgressBar.setProgress(0.5);
        // 未知的进度
        //loadProgressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);

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
