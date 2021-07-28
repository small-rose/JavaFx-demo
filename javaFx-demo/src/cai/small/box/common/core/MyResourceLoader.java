package cai.small.box.common.core;

import cai.small.MainApp;

import java.net.URL;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2021/7/22 20:44
 * @version: v1.0
 */
public class MyResourceLoader {


    public static URL getFxmlResource(String fxmlName) {
        URL url = MainApp.class.getResource(fxmlName);
        return url;
    }
}
