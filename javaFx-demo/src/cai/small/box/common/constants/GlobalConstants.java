package cai.small.box.common.constants;

import javafx.scene.image.Image;


import java.io.InputStream;


/**
 * @description: TODO 功能角色说明：
 * @author: 张小菜
 * @date: 2021/7/19 0:17
 * @version: v1.0
 */
public class GlobalConstants {

    public static final String LOGO_NAME = "张小菜";

    //public static final String START_IMAGE = "/resources/images/start.jpg";

    public static final String LOGO_URL = "/resources/images/logo.png";

    public static Image LOGO_IMAGE = new Image(GlobalConstants.class.getResourceAsStream(LOGO_URL));

    static {
        LOGO_IMAGE = new Image(GlobalConstants.class.getResourceAsStream(LOGO_URL));
    }


    public static class WINDOW{
        public static final String LOADING = "loading.fxml";
        public static final String MAIN  = "function/MainView.fxml";
        public static final String IP_CONFIG = "box/function/IpConfigView.fxml";
        public static final String FORMAT_TOOL = "box/function/FormatView.fxml";
    }


    public static class FILEPATH {
        public static final String IP_PING_FILE = "D:\\IPing.xml";
    }


    public static class CSS{
        public static final String  BOOTSTRAP_FX = "org/kordamp/bootstrapfx/bootstrapfx.css";
    }
}
