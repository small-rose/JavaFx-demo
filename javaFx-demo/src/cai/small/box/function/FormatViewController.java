package cai.small.box.function;

import cai.small.box.common.core.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.dom4j.DocumentException;
import cai.small.box.common.constants.GlobalConstants;
import cai.small.box.common.tools.XmlFormatTool;
import cai.small.box.common.utils.AlertUtil;

import java.io.IOException;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2021/7/20 23:45
 * @version: v1.0
 */
public class FormatViewController {

    private static StageManager stageManager = StageManager.getInstance();

    @FXML
    private TextArea xmlSourceText;
    @FXML
    private TextArea  xmlTargetText;

    private static Stage stage ;

    static {
        stage = stageManager.getStage(GlobalConstants.WINDOW.FORMAT_TOOL);
    }

    /**
     * xml 格式化
     * @param actionEvent
     */
    public void xmlFormatOnAction(ActionEvent actionEvent){

        String sourceText = xmlSourceText.getText();
        if ("".equals(sourceText.trim())){
            AlertUtil.alertInfoDialog(stage, "提示", "XML是空的，请帖入XML");
            return;
        }
        String targetText = null;
        try {
            targetText = XmlFormatTool.prettyFormat(sourceText);
        } catch (DocumentException e) {
            e.printStackTrace();
            AlertUtil.alertErrorDialog(stage, "错误", "XML文档格式错误,检查后再格式化");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.alertErrorDialog(stage, "错误", e.getMessage());
        }

        xmlTargetText.setText("");
        xmlTargetText.setText(targetText);

    }

    /**
     * xml 压缩
     * @param actionEvent
     */
    public void xmlCompactOnAction(ActionEvent actionEvent){

        String sourceText = xmlSourceText.getText();
        if ("".equals(sourceText.trim())){
            AlertUtil.alertInfoDialog(stage, "提示", "XML是空的，请帖入XML");
            return;
        }
        String targetText = null;
        try {
            targetText = XmlFormatTool.createCompactFormat(sourceText);
        } catch (DocumentException e) {
            e.printStackTrace();
            AlertUtil.alertErrorDialog(stage, "错误", "XML文档格式错误,检查后再格式化");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.alertErrorDialog(stage, "错误", e.getMessage());
        }

        xmlTargetText.setText("");
        xmlTargetText.setText(targetText);
    }


    /**
     * json 格式化
     * @param actionEvent
     */
    public void jsonFormatOnAction(ActionEvent actionEvent){

        String sourceText = xmlSourceText.getText();
        if ("".equals(sourceText.trim())){
            AlertUtil.alertInfoDialog(stage, "提示", "XML是空的，请帖入XML");
            return;
        }
        String targetText = null;
        try {
            targetText = XmlFormatTool.prettyFormat(sourceText);
        } catch (DocumentException e) {
            e.printStackTrace();
            AlertUtil.alertErrorDialog(stage, "错误", "XML文档格式错误,检查后再格式化");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.alertErrorDialog(stage, "错误", e.getMessage());
        }

        xmlTargetText.setText("");
        xmlTargetText.setText(targetText);

    }

}
