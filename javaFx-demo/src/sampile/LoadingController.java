package sampile;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Border;
import sampile.common.AbstractStage;

import java.net.URL;
import java.util.ResourceBundle;

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
        //loadProgressBar.set
        // 50%进度
        //loadProgressBar.setProgress(0.5);
        // 未知的进度
        //loadProgressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);


    }

}
