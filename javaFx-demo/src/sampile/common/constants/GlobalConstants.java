package sampile.common.constants;

import javafx.scene.image.Image;
import sampile.MainApp;



/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2021/7/19 0:17
 * @version: v1.0
 */
public class GlobalConstants {

    public static final String LOGO_NAME = "张小菜";

    public static final String LOGO_URL = "../images/icon/logo-3.png";

    public static final Image LOGO_IMAGE = new Image(MainApp .class.getResourceAsStream(LOGO_URL));




    public static class WINDOW{
        public static final String LOADING = "loading.fxml";
        public static final String MAIN  = "main/mainView.fxml";
        public static final String IP_CONFIG = "function/IpConfigView.fxml";
    }
}
