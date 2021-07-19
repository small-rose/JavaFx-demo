package sampile.function;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RuntimeUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sampile.StageManager;
import sampile.common.constants.GlobalConstants;
import sampile.common.utils.AlertUtil;

import java.io.*;


/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2021/7/18 22:53
 * @version: v1.0
 */
public class IpConfigController {

    private  StageManager stageManager = StageManager.getInstance();
    @FXML
    private Button ipConfigButton;
    @FXML
    private TextArea ipConfigTextArea;

    @FXML
    private TextField ipValueText;
    @FXML
    private Button pingButton;
    @FXML
    private TextArea pingResultTextArea;

    private boolean lock = false ;

    public void ipConfigOnAction(ActionEvent actionEvent){

        String ipConfigStr = RuntimeUtil.execForStr("ipconfig");
        ipConfigTextArea.setText(ipConfigStr);

    }


    public void pingButtonOnAction(ActionEvent actionEvent){

        if (lock){
            Stage stage = stageManager.getStage(GlobalConstants.WINDOW.IP_CONFIG);
            AlertUtil.alertInfoDialog(stage, "提示","已经在执行了，你不要这么赶时间呀~");
            return;
        }
        pingResultTextArea.setText("");
        String pingIp = ipValueText.getText().trim();
        Process process = RuntimeUtil.exec("ping " + pingIp);
        InputStream inputStream = process.getInputStream();

        pingResultTextArea.setWrapText(true);

        ThreadUtil.execute(() -> {
            lock = true;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;
            try {
                inputStreamReader = new InputStreamReader(inputStream, CharsetUtil.GBK);
                bufferedReader = new BufferedReader(inputStreamReader);
                String line = null;
                while (true) {
                    try {
                        if ((line = bufferedReader.readLine()) == null) {
                            break;
                        }
                    } catch (IOException ex) {
                       ex.printStackTrace();
                    }
                    pingResultTextArea.appendText(line +"\n");
                }
            } catch (UnsupportedEncodingException ex) {
               ex.printStackTrace();
            } finally {
                process.destroy();
                lock = false ;
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

        });
    }

}
