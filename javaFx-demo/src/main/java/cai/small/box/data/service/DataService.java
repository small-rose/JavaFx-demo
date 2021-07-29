package cai.small.box.data.service;

import cai.small.MainApp;
import cai.small.box.data.model.IPListWrapper;
import cai.small.box.data.model.IPing;
import cai.small.box.data.config.Config;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * @description: TODO 功能角色说明：
 * TODO 描述：
 * @author: 张小菜
 * @date: 2021/7/19 23:12
 * @version: v1.0
 */
public class DataService {


    private  List<IPing> ipList = new ArrayList<>();

    private String ipFilePath ;

    public DataService(){
        URL xmlpath = Config.class.getClassLoader().getResource("cai/small/box/data/config/IPing.xml");
        ipFilePath = xmlpath.getPath();
        System.out.println("right path : " + xmlpath.getPath());
    }

    /**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     * @return
     */
    public File getIPingFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        System.out.println("filePath:" + filePath);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return new File(ipFilePath);
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     *
     * @param file the file or null to remove the path
     */
    public void setIPingFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());
        } else {
            prefs.remove("filePath");

        }
    }

    /**
     * Loads ip data from the specified file. The current ip data will
     * be replaced.
     *
     * @param file
     */
    public void loadIPingFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(IPListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            IPListWrapper wrapper = (IPListWrapper) um.unmarshal(file);
            System.out.println(wrapper);
            ipList.clear();
            ipList.addAll(wrapper.getIpList());

            // Save the file path to the registry.
            setIPingFilePath(file);

        } catch (Exception e) { // catches ANY exception
            //Dialogs.create().title("Error")
            //        .masthead("Could not load data from file:\n" + file.getPath())
            //        .showException(e);e
            e.printStackTrace();
        }
    }

    /**
     * Saves the current ip data to the specified file.
     *
     * @param file
     */
    public void savePingIpDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(IPListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            IPListWrapper wrapper = new IPListWrapper();

            wrapper.setIpList(ipList);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setIPingFilePath(file);
        } catch (Exception e) { // catches ANY exception
           /* Dialogs.create().title("Error")
                    .masthead("Could not save data to file:\n" + file.getPath())
                    .showException(e);*/
            e.printStackTrace();
        }
    }


    public String getIpFilePath() {
        return ipFilePath;
    }

    public void setIpFilePath(String ipFilePath) {
        this.ipFilePath = ipFilePath;
    }

    public List<IPing> getIpList() {
        return ipList;
    }

    public void setIpList(List<IPing> ipList) {
        this.ipList = ipList;
    }
}
