package cai.small.box.data.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2021/7/19 23:33
 * @version: v1.0
 */
@XmlRootElement(name = "IPList")
public class IPListWrapper {

    private List<IPing> ipList;

    @XmlElement(name = "IPing")
    public List<IPing> getIpList() {
        return ipList;
    }

    public void setIpList(List<IPing> ipList) {
        this.ipList = ipList;
    }

    @Override
    public String toString() {
        return "IPListWrapper{" +
                "ipList=" + ipList +
                '}';
    }
}
