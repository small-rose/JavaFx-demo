package cai.small.box.common.tools;

import cn.xiaocai.json.*;

import java.util.*;

/**
 * @Project : javaFx-demo
 * @Author : zhangzongyuan
 * @Description : [ Hpack ] 说明：无
 * @Function :  功能说明：无
 * @Date ：2021/7/29 9:49
 * @Version ： 1.0
 **/
public class Hpack {




    public static void main(String[] args) {
        String data = "{\"a\":1,\"b\":2,\"c\":1,\"d\":2,\"e\":1,\"f\":2}";
        System.out.println(JSON.parse(data));

        String data2 = "[{\"a\":1,\"b\":[{\"b1\":1,\"b2\":2}]},{\"c\":1,\"d\":2}]";
        System.out.println(JSON.parse(data2));

        String data3 = "[{\"a\":1,\"b\":{\"b1\":1,\"b2\":2}},{\"c\":1,\"d\":2}]";
        System.out.println(JSON.parse(data3));

        String data4 = "{\"a\":1,\"b\":{\"b1\":1,\"b2\":2},\"c\":1,\"d\":2}";
        System.out.println(JSON.parse(data4));

        String data5 = "{\"a\":1,\"b\":[{\"b1\":1,\"b2\":2},{\"b3\":1,\"b4\":2}],\"c\":1,\"d\":2}";
        System.out.println(JSON.parse(data5));
    }
}
