package cn.xiaocai.json;

import cn.xiaocai.json.exception.JsonException;

public class JSON {
    public static final Object parse(String s) {
        JsonTokenizer tokenizer = new JsonTokenizer(s);
        JsonParser parser = new JsonParser(tokenizer);
        Object o = null;
        try {
            o = parser.parseValue();
        } catch (Exception e) {
            int p = tokenizer.getPoint();
            int endIndex = Math.min(p + 20, s.length());
            String errMsg = "格式不正确位置:" + s.substring(p, endIndex) + ";" + e.getMessage();
            throw new JsonException(errMsg);
        }
        return o;
    }
}
