package sampile.function;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.dom4j.DocumentException;
import sampile.StageManager;
import sampile.common.constants.GlobalConstants;
import sampile.common.tools.XmlFormatTool;
import sampile.common.utils.AlertUtil;

import java.io.IOException;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2021/7/20 23:45
 * @version: v1.0
 */
public class FormatViewController {

    private StageManager stageManager = StageManager.getInstance();

    @FXML
    private TextArea xmlSourceText;
    @FXML
    private TextArea  xmlTargetText;


    public void xmlFormatOnAction(ActionEvent actionEvent){

        Stage stage = stageManager.getStage(GlobalConstants.WINDOW.FORMAT_TOOL);

        String sourceText = xmlSourceText.getText();

        String targetText = null;
        try {
            targetText = XmlFormatTool.prettyFormat(sourceText);
        } catch (DocumentException e) {
            e.printStackTrace();

            AlertUtil.alertErrorDialog(stage, "错误", "XML文档格式错误,检查后再格式化");

        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.alertErrorDialog(stage, "错误", e.getMessage());
        }

        xmlTargetText.setText("");
        xmlTargetText.setText(targetText);

    }
}
