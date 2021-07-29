package cn.xiaocai.json;

import cn.xiaocai.json.model.Token;
import cn.xiaocai.json.model.TokenType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonParser {
    private JsonTokenizer jsonTokenizer;

    public JsonParser(JsonTokenizer jsonTokenizer) {
        this.jsonTokenizer = jsonTokenizer;
    }

    public Object parseValue() {
        Token token = jsonTokenizer.nextToken();
        TokenType tt = token.getType();
        if (tt == TokenType.BEGIN_OBJECT) {
            Map map = parseObj();
            return map;
        }
        if (tt == TokenType.BEGIN_ARRAY) {
            List list = parseArray();
            return list;
        }
        if (tt == TokenType.STRING) {
            String str = token.getVal();
            return str;
        }
        if (tt == TokenType.NUMBER) {
            return new BigDecimal(token.getVal());
        }
        if (tt == TokenType.BOOLEN) {
            String s = token.getVal();
            if ("true".equals(s)) {
                return true;
            }
            if ("false".equals(s)) {
                return false;
            }
        }
        if (tt == TokenType.NULL) {
            return null;
        }
        throw new RuntimeException("parseValue error." + prettyToken(token));
    }

    private Map parseObj() {
        Token lookAhead = jsonTokenizer.lookAhead();
        if (lookAhead.getType() == TokenType.END_OBJECT) {
            jsonTokenizer.nextToken();
            return new LinkedHashMap();
        } else if (lookAhead.getType() == TokenType.STRING) {
            Map map = parseMembers();
            return map;
        } else {
            throw new RuntimeException("parseObj error." + prettyToken(lookAhead));
        }
    }

    private Map parseMembers() {
        Map map = new LinkedHashMap();
        Object[] objects = parseMember();
        map.put(objects[0], objects[1]);
        Token token = jsonTokenizer.nextToken();
        TokenType tt = token.getType();
        if (tt == TokenType.SEP_COMMA) {
            Map map1 = parseMembers();
            map.putAll(map1);
        } else if (tt == TokenType.END_OBJECT) {
        } else {
            throw new RuntimeException("parseMembers error. " + prettyToken(token));
        }
        return map;
    }

    private Object[] parseMember() {
        Token key = jsonTokenizer.nextToken();
        if (key.getType() != TokenType.STRING) {
            throw new RuntimeException("key的类型不为string:" + prettyToken(key));
        }
        String s = key.getVal();
        Token colon = jsonTokenizer.nextToken();
        if (colon.getType() != TokenType.SEP_COLON) {
            throw new RuntimeException("key后缺少冒号:" + prettyToken(key));
        }
        Object o = parseValue();
        Object[] objects = {s, o};
        return objects;
    }

    private List parseArray() {
        Token lookAhead = jsonTokenizer.lookAhead();
        if (lookAhead.getType() == TokenType.END_ARRAY) {
            jsonTokenizer.nextToken();
            return new ArrayList();
        } else {
            List list = parseValues();
            return list;
        }
    }

    private List parseValues() {
        List list = new ArrayList();
        Object o = parseValue();
        list.add(o);
        Token token = jsonTokenizer.nextToken();
        TokenType tt = token.getType();
        if (tt == TokenType.SEP_COMMA) {
            List list1 = parseValues();
            list.addAll(list1);
        } else if (tt == TokenType.END_ARRAY) {
        } else {
            throw new RuntimeException("parseValues error." + prettyToken(token));
        }
        return list;
    }

    private String prettyToken(Token token) {
        String s = " type:" + token.getType() + ",value:" + token.getVal();
        return s;
    }

}
