package cn.xiaocai.json;

import cn.xiaocai.json.model.Token;
import cn.xiaocai.json.model.TokenType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonTokenizer {
    private String s;
    private int p;
    private Pattern numberPtn = Pattern.compile("([1-9]\\d+|\\d|\\-[1-9]\\d+|\\-\\d)(\\.\\d+)?([Ee][\\+\\-]?\\d+)?");

    public JsonTokenizer(String s) {
        this.s = s;
    }

    public final boolean hasNext() {
        return p < s.length();
    }

    public final Token nextToken() {
        Token token = new Token();
        char ch = s.charAt(p);
        switch (ch) {
            case '{':
                token.setType(TokenType.BEGIN_OBJECT);
                token.setVal("{");
                p++;
                break;
            case '}':
                token.setType(TokenType.END_OBJECT);
                token.setVal("}");
                p++;
                break;
            case '[':
                token.setType(TokenType.BEGIN_ARRAY);
                token.setVal("[");
                p++;
                break;
            case ']':
                token.setType(TokenType.END_ARRAY);
                token.setVal("]");
                p++;
                break;
            case ',':
                token.setType(TokenType.SEP_COMMA);
                token.setVal(",");
                p++;
                break;
            case ':':
                token.setType(TokenType.SEP_COLON);
                token.setVal(":");
                p++;
                break;
            case 't':
                if (s.charAt(++p) == 'r' && s.charAt(++p) == 'u' && s.charAt(++p) == 'e') {
                    token.setType(TokenType.BOOLEN);
                    token.setVal("true");
                } else {
                    throw new RuntimeException();
                }
                p++;
                break;
            case 'f':
                if (s.charAt(++p) == 'a' && s.charAt(++p) == 'l' && s.charAt(++p) == 's' && s.charAt(++p) == 'e') {
                    token.setType(TokenType.BOOLEN);
                    token.setVal("false");
                } else {
                    throw new RuntimeException();
                }
                p++;
                break;
            case 'n':
                if (s.charAt(++p) == 'u' && s.charAt(++p) == 'l' && s.charAt(++p) == 'l') {
                    token.setType(TokenType.NULL);
                    token.setVal("null");
                } else {
                    throw new RuntimeException();
                }
                p++;
                break;
            case '"':
                StringBuilder val = new StringBuilder();
                int i = 1;
                char c = s.charAt(p + i);
                while ('"' != c) {
                    if ('\\' == c) {
                        i++;
                        switch (s.charAt(p + i)) {
                            case 'b':
                                val.append('\b');
                                break;
                            case 'f':
                                val.append('\f');
                                break;
                            case 'n':
                                val.append('\n');
                                break;
                            case 'r':
                                val.append('\r');
                                break;
                            case 't':
                                val.append('\t');
                                break;
                            case '\\':
                                val.append('\\');
                                break;
                            case '/':
                                val.append('/');
                                break;
                            case '"':
                                val.append('"');
                                break;
                            case 'u':
                                i++;
                                char u1 = s.charAt(p + i);
                                i++;
                                char u2 = s.charAt(p + i);
                                i++;
                                char u3 = s.charAt(p + i);
                                i++;
                                char u4 = s.charAt(p + i);
                                if (!isDigit(u1) || !isDigit(u2) || !isDigit(u3) || !isDigit(u4)) {
                                    throw new RuntimeException();
                                }
                                char ucode = (char) Integer.parseInt(String.valueOf(new char[]{u1, u2, u3, u4}), 16);
                                val.append(ucode);
                                break;
                            default:
                                throw new RuntimeException();
                        }
                    } else {
                        val.append(c);
                    }
                    i++;
                    c = s.charAt(p + i);
                }
                token.setType(TokenType.STRING);
                token.setVal(val.toString());
                p = p + i + 1;
                break;
            case '-':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                Matcher numMt = numberPtn.matcher(s.substring(p));
                if (numMt.lookingAt()) {
                    int end = numMt.end();
                    String grp = numMt.group();
                    token.setType(TokenType.NUMBER);
                    token.setVal(grp);
                    p = p + end;
                    break;
                } else {
                    throw new RuntimeException();
                }
            case ' ':
            case '\n':
            case '\r':
            case '\t':
//                token.setType(TokenType.WS);
//                token.setVal(ch + "");
                p++;
                token = nextToken();
                break;
            default:
                throw new RuntimeException();
        }
        return token;
    }

    public Token lookAhead() {
        int p1 = p;
        Token token = nextToken();
        p = p1;
        return token;
    }

    private Boolean isDigit(char ch) {
        return ch >= 48 || ch <= 57 || ch >= 65 || ch <= 70 || ch >= 97 || ch <= 102;
    }

    public int getPoint() {
        return p;
    }
}
