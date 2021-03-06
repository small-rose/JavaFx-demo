package cai.small.box.common.tools;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.*;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2021/7/20 23:30
 * @version: v1.0
 */
public class JsonFormatTool {

    public static String prettyFormat(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }




    /**
     * 单位缩进字符串。
     */
    private static String SPACE = "    ";


    /**
     * 返回格式化JSON字符串。
     *
     * @param json 未格式化的JSON字符串。
     * @return 格式化的JSON字符串。
     */
    public String formatJson(String json)
    {
        StringBuffer result = new StringBuffer();

        int length = json.length();
        int number = 0;
        char key = 0;

        //遍历输入字符串。
        for (int i = 0; i < length; i++)
        {
            //1、获取当前字符。
            key = json.charAt(i);

            //2、如果当前字符是前方括号、前花括号做如下处理：
            if((key == '[') || (key == '{') )
            {
                //（1）如果前面还有字符，并且字符为“：”，打印：换行和缩进字符字符串。
                if((i - 1 > 0) && (json.charAt(i - 1) == ':'))
                {
                    result.append('\n');
                    result.append(indent(number));
                }

                //（2）打印：当前字符。
                result.append(key);

                //（3）前方括号、前花括号，的后面必须换行。打印：换行。
                result.append('\n');

                //（4）每出现一次前方括号、前花括号；缩进次数增加一次。打印：新行缩进。
                number++;
                result.append(indent(number));

                //（5）进行下一次循环。
                continue;
            }

            //3、如果当前字符是后方括号、后花括号做如下处理：
            if((key == ']') || (key == '}') ){
                //（1）后方括号、后花括号，的前面必须换行。打印：换行。
                result.append('\n');

                //（2）每出现一次后方括号、后花括号；缩进次数减少一次。打印：缩进。
                number--;
                result.append(indent(number));

                //（3）打印：当前字符。
                result.append(key);

                //（4）如果当前字符后面还有字符，并且字符不为“，”，打印：换行。
                if(((i + 1) < length) && (json.charAt(i + 1) != ',')) {
                    result.append('\n');
                }

                //（5）继续下一次循环。
                continue;
            }

            //4、如果当前字符是逗号。逗号后面换行，并缩进，不改变缩进次数。
            if((key == ',')){
                result.append(key);
                result.append('\n');
                result.append(indent(number));
                continue;
            }

            //5、打印：当前字符。
            result.append(key);
        }

        return result.toString();
    }

    /**
     * 返回指定次数的缩进字符串。每一次缩进三个空格，即SPACE。
     *
     * @param number 缩进次数。
     * @return 指定缩进次数的字符串。
     */
    private String indent(int number){
        StringBuffer result = new StringBuffer();
        for(int i = 0; i < number; i++) {
            result.append(SPACE);
        }
        return result.toString();
    }




    public static String compactJson(String sourceText) {

        parseData(sourceText);

        return "";
    }

    private static Map<String, Object> parseData(String data){
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Map<String, Object> map = gson.fromJson(data, new TypeToken<Map<String, Object>>() {

        }.getType());
        return map;
    }



    public static void main(String[] args) {
        String data = "{\"a\":1,\"b\":2,\"c\":1,\"d\":2,\"e\":1,\"f\":2}";
        System.out.println(parseData(data));

        String data2 = "[{\"a\":1,\"b\":[{\"b1\":1,\"b2\":2}]},{\"c\":1,\"d\":2}]";
        System.out.println(parseData(data2));

        String data3 = "[{\"a\":1,\"b\":{\"b1\":1,\"b2\":2}},{\"c\":1,\"d\":2}]";
        System.out.println(parseData(data3));

        String data4 = "{\"a\":1,\"b\":{\"b1\":1,\"b2\":2},\"c\":1,\"d\":2}";
        System.out.println(parseData(data4));

        String data5 = "{\"a\":1,\"b\":[{\"b1\":1,\"b2\":2},{\"b3\":1,\"b4\":2}],\"c\":1,\"d\":2}";
        System.out.println(parseData(data5));
    }
}
