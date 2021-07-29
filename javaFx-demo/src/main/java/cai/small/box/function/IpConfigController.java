package cai.small.box.function;

import cai.small.box.common.core.StageManager;
import cai.small.box.data.model.IPing;
import cai.small.box.data.service.DataService;
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
import cai.small.box.common.constants.GlobalConstants;
import cai.small.box.common.utils.AlertUtil;

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

    private StageManager stageManager = StageManager.getInstance();

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
    @FXML
    private ListView ipListHistory;


    private boolean lock = false ;

    private List<String> showPingList  = new ArrayList<>();

    private List<IPing> dataList = new ArrayList<>(); ;


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


    public void ipConfigFlushOnAction(ActionEvent actionEvent){
        String ipConfigStr = RuntimeUtil.execForStr("ipconfig /flushdns");
        ipConfigTextArea.setText(ipConfigStr);
        ipConfigTextArea.setEditable(false);
    }

    public void pingButtonOnAction(ActionEvent actionEvent){

        Stage stage = stageManager.getStage(GlobalConstants.WINDOW.IP_CONFIG);
        if (lock){
            AlertUtil.alertInfoDialog(stage, "提示","已经在执行了，你不要这么赶时间呀~");
            return;
        }
        pingResultTextArea.setText("");
        String pingIp = ipValueText.getText().trim();
        if ("".equals(pingIp)){
            AlertUtil.alertErrorDialog(stage, "提示","你忘记输入IP或域名了 \uD83D\uDE02");
            return;
        }
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

        Platform.runLater(()->saveIpList(pingIp));
    }

    private void saveIpList(String newIp){
        dataService.setIPingFilePath(new File(dataService.getIpFilePath()));
        dataService.loadIPingFromFile(dataService.getIPingFilePath());

        if (!"".equals(newIp)){
            Collections.reverse(showPingList);
            showPingList.add(newIp);

            dataList.clear();
            showPingList.stream().distinct().forEach((s) -> dataList.add(new IPing(s)));

            dataService.setIpList(dataList);
            dataService.savePingIpDataToFile(dataService.getIPingFilePath());
        }
        updateShowList();
    }


    private void updateShowList(){

        showPingList.clear();
        dataService.getIpList().stream().forEach((d)->showPingList.add(d.getIP()));
        Collections.reverse(showPingList);
        // 把清单对象转换为JavaFX控件能够识别的数据对象
        ObservableList<String> obList = FXCollections.observableArrayList(showPingList);
        // 依据指定数据更新列表视图
        ipListHistory.setItems(obList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataService = new DataService();

        dataService.setIPingFilePath(new File(dataService.getIpFilePath()));
        dataService.loadIPingFromFile(dataService.getIPingFilePath());

        showPingList.clear();
        dataService.getIpList().stream().forEach((d)->showPingList.add(d.getIP()));
        Collections.reverse(showPingList);
        // 把清单对象转换为JavaFX控件能够识别的数据对象
        ObservableList<String> obList = FXCollections.observableArrayList(showPingList);
        // 依据指定数据更新列表视图
        ipListHistory.setItems(obList);

        ipListHistory.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String old_str, String new_str) {
                // getSelectedIndex方法可获得选中项的序号，getSelectedItem方法可获得选中项的对象
                //String desc = String.format("您点了第%d项，IP是%s",
                //        ipListHistory.getSelectionModel().getSelectedIndex(),
                //       ipListHistory.getSelectionModel().getSelectedItem().toString());
                //System.out.println(" desc : " +desc);
                ipValueText.setText(ipListHistory.getSelectionModel().getSelectedItem().toString()); // 在标签上显示当前选中的文本项
            }
        });
    }



}
