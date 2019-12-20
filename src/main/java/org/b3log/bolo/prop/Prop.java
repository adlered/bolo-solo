package org.b3log.bolo.prop;

import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;

import java.io.*;
import java.util.Properties;
import java.util.Set;

/**
 * <h3>bolo-solo</h3>
 * <p>控制 bolo 属性设置</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2019-12-20 18:50
 **/
public class Prop {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(Prop.class);

    private static Properties properties = new Properties();

    private String propPath = "bolo_config/config.prop";

    public Prop() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(propPath));
            properties.load(bufferedReader);
            LOGGER.log(Level.INFO, "Profile \"" + new File(propPath).getAbsolutePath() + "\" loaded successfully.");
        } catch (FileNotFoundException FNFE) {
            LOGGER.log(Level.WARN, "Cannot found properties file \"" + propPath + "\" at the root path, re-generating default...");
            try {
                File file = new File(propPath);
                File upper = new File(file.getParent());
                upper.mkdirs();
                file.createNewFile();
                // Set default props
                addProperty("hello", "world");
                BufferedReader bufferedReader = new BufferedReader(new FileReader(propPath));
                properties.load(bufferedReader);
            } catch (IOException IOE) {
                IOE.printStackTrace();
            }
        } catch (IOException IOE) {
            IOE.printStackTrace();
        }
    }

    public Prop addAnnotation(String annotation) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(propPath), true);
            fileOutputStream.write(("# " + annotation + "\n").getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException FNFE) {
            FNFE.printStackTrace();
        } catch (IOException IOE) {
            IOE.printStackTrace();
        }
        return this;
    }

    public Prop addProperty(String key, String value) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(propPath), true);
            fileOutputStream.write((key + "=" + value + "\n").getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException FNFE) {
            FNFE.printStackTrace();
        } catch (IOException IOE) {
            IOE.printStackTrace();
        }
        return this;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        try {
            properties.setProperty(key, value);
            PrintWriter printWriter = new PrintWriter(new FileWriter(propPath), true);
            Set set = properties.keySet();
            for (Object object : set) {
                String k = (String) object;
                String v = properties.getProperty(k);
                printWriter.println(k + "=" + v);
            }
            printWriter.flush();
            printWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
