package sampile.function;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RuntimeUtil;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sampile.StageManager;
import sampile.common.constants.GlobalConstants;
import sampile.common.utils.AlertUtil;
import sampile.data.model.IPing;
import sampile.data.service.DataService;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;


/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2021/7/18 22:53
 * @version: v1.0
 */
public class IpConfigController implements Initializable {

    private  StageManager stageManager = StageManager.getInstance();

    private DataService dataService ;
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

    private List<String> showPingList  = new ArrayList<>();

    private List<IPing> dataList = new ArrayList<>(); ;
    @FXML
    private ListView ipListHistory;

    public void ipConfigOnAction(ActionEvent actionEvent){

        String ipConfigStr = RuntimeUtil.execForStr("ipconfig");
        ipConfigTextArea.setText(ipConfigStr);
        ipConfigTextArea.setEditable(false);
    }

    public void ipConfigAllOnAction(ActionEvent actionEvent){
        String ipConfigStr = RuntimeUtil.execForStr("ipconfig /all");
        ipConfigTextArea.setText(ipConfigStr);
        ipConfigTextArea.setEditable(false);
    }

    private void addConfigAreaListener(){

        ipConfigTextArea.selectedTextProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                System.out.println("oldVal : " + oldValue);
                System.out.println("newValue : " + newValue);
                String val = ipConfigTextArea.getSelectedText();
                System.out.println("val : " + val);
            }
        });
    }
    public void ipConfigFlushOnAction(ActionEvent actionEvent){
        String ipConfigStr = RuntimeUtil.execForStr("ipconfig /flushdns");
        ipConfigTextArea.setText(ipConfigStr);
        ipConfigTextArea.setEditable(false);
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

                Platform.runLater(()->saveIpList(pingIp));
                Platform.runLater(()->updateShowList());

            }

        });
    }

    private void saveIpList(String newIp){
        dataService.setIPingFilePath(dataService.getIPingFilePath());
        dataService.loadIPingFromFile(new File(GlobalConstants.FILEPATH.IP_PING_FILE));
        Collections.reverse(showPingList);
        showPingList.add(newIp);


        dataList.clear();
        showPingList.stream().distinct().forEach((s) -> dataList.add(new IPing(s)));

        dataService.setIpList(dataList);
        dataService.savePingIpDataToFile(dataService.getIPingFilePath());
    }


    private void updateShowList(){
        dataService.setIPingFilePath(dataService.getIPingFilePath());
        dataService.loadIPingFromFile(new File(GlobalConstants.FILEPATH.IP_PING_FILE));

        showPingList.clear();
        dataService.getIpList().stream().forEach((d)->showPingList.add(d.getIP()));
        Collections.reverse(showPingList);
        // 把清单对象转换为JavaFX控件能够识别的数据对象
        ObservableList<String> obList = FXCollections.observableArrayList(showPingList);
        ipListHistory.setItems(obList); // 依据指定数据创建列表视图
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataService = new DataService();

        updateShowList();

        ipListHistory.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String old_str, String new_str) {
                // getSelectedIndex方法可获得选中项的序号，getSelectedItem方法可获得选中项的对象
                String desc = String.format("您点了第%d项，快餐名称是%s",
                        ipListHistory.getSelectionModel().getSelectedIndex(),
                        ipListHistory.getSelectionModel().getSelectedItem().toString());
                System.out.println(" desc : " +desc);
                ipValueText.setText(ipListHistory.getSelectionModel().getSelectedItem().toString()); // 在标签上显示当前选中的文本项
            }
        });
    }



}
