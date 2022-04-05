package org.b3log.solo.util;

import org.b3log.latke.logging.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;

public final class FileUtil {

  private static final Logger LOGGER = Logger.getLogger(FileUtil.class);
	
	public static void saveDataToFile(String filePath, String context) {
		File file = new File(filePath);
		if (!file.getParentFile().exists() && file.getParentFile().mkdir()) {
			LOGGER.error("create " + file.getParentFile() + "failed");
		}
		BufferedWriter writer = null;
		try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false), StandardCharsets.UTF_8));
            writer.write(context);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
	
	public static String readFile(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
      LOGGER.error("file not exit " + filePath);
			return null;
		}
		BufferedReader reader = null;
		StringBuilder res = new StringBuilder();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null){
                res.append(tempString).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res.toString();
	}

}
