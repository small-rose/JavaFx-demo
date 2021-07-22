package cai.small.box.data.model;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2021/7/19 22:42
 * @version: v1.0
 */
public class IPing {

    public IPing() {
    }
    public IPing(String IP) {
        this.IP = IP;
    }

    private String IP ;

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }
}
