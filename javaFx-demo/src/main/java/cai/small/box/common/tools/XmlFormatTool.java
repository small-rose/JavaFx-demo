package cai.small.box.common.tools;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2021/7/20 23:27
 * @version: v1.0
 */
public class XmlFormatTool {

    /**
     * 指定空格
     * @param xml
     * @param indent
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public static String prettyFormat(String xml, String indent) throws DocumentException, IOException {
        Document doc = getSaxDoc(xml);
        OutputFormat formater = getOutputFormat(indent);
        // 注释：创建输出(目标)
        StringWriter out = new StringWriter();
        // 注释：创建输出流
        XMLWriter writer = new XMLWriter(out, formater);
        // 注释：输出格式化的串到目标中，执行后。格式化后的串保存在out中。
        writer.write(doc);
        writer.close();
        //System.out.println(out.toString());
        // 注释：返回我们格式化后的结果
        return out.toString();
    }

    public static String prettyFormat(String xml) throws DocumentException, IOException {
        Document doc = getSaxDoc(xml);
        OutputFormat formater = getOutputFormat(null);
        // 注释：创建输出(目标)
        StringWriter out = new StringWriter();
        // 注释：创建输出流
        XMLWriter writer = new XMLWriter(out, formater);
        // 注释：输出格式化的串到目标中，执行后。格式化后的串保存在out中。
        writer.write(doc);

        writer.close();
        System.out.println(out.toString());
        // 注释：返回我们格式化后的结果
        return out.toString();
    }

    /**
     *  压缩
     * @param str
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public static String createCompactFormat(String str) throws DocumentException, IOException {
        Document doc = getSaxDoc(str);
        // 注释：创建输出格式
        OutputFormat formater = OutputFormat.createCompactFormat();
        // 注释：设置xml的输出编码
        formater.setEncoding("utf-8");
        // 注释：创建输出(目标)
        StringWriter out = new StringWriter();
        // 注释：创建输出流
        XMLWriter writer = new XMLWriter(out, formater);
        // 注释：输出格式化的串到目标中，执行后。格式化后的串保存在out中。
        writer.write(doc);

        writer.close();
        System.out.println(out.toString());
        // 注释：返回我们格式化后的结果
        return out.toString();
    }


    /**
     * 获取 输出格式化
     * @param indent
     * @return
     */
    private static OutputFormat getOutputFormat(String indent){
        // 注释：创建输出格式
        OutputFormat formater = OutputFormat.createPrettyPrint();
        //formater=OutputFormat.createCompactFormat();
        //OutputFormat formater = new OutputFormat();
        if (indent == null || indent.equals("Tab")) {
            formater.setIndent("\t"); // 使用TAB缩进
        }else{
            formater.setIndentSize(Integer.parseInt(indent));
        }
        //formater.setIndentSize(4);
        formater.setNewlines(true);
        formater.setTrimText(true);
        formater.setPadText(true);
        // 注释：设置xml的输出编码
        formater.setEncoding("utf-8");
        return formater;
    }

    /**
     * 获取XML文档对象
     * @param xml
     * @return
     * @throws DocumentException
     */
    private static Document getSaxDoc(String xml) throws DocumentException {
        SAXReader reader = new SAXReader();
        // System.out.println(reader);
        // 注释：创建一个串的字符输入流
        StringReader in = new StringReader(xml);
        Document doc = reader.read(in);
        // System.out.println(doc.getRootElement());
        return doc ;
    }
}
