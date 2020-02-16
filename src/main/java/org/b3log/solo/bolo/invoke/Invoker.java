/*
 * Solo - A small and beautiful blogging system written in Java.
 * Copyright (c) 2010-present, b3log.org
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package org.b3log.solo.bolo.invoke;

import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;
import org.b3log.latke.servlet.HttpMethod;
import org.b3log.latke.servlet.RequestContext;
import org.b3log.latke.servlet.annotation.RequestProcessing;
import org.b3log.latke.servlet.annotation.RequestProcessor;
import org.b3log.solo.util.Solos;

import javax.servlet.http.HttpServletResponse;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <h3>bolo-solo</h3>
 * <p>以字符串运行代码类（实用工具）</p>
 *
 * @author : https://github.com/AdlerED
 * @date : 2020-01-01 21:47
 **/
@RequestProcessor
public class Invoker {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(Invoker.class);

    /**
     * 执行 Java 代码
     */
    @RequestProcessing(value = "/invoke", method = {HttpMethod.POST})
    public void invoke(final RequestContext context) {
        if (!Solos.isAdminLoggedIn(context)) {
            context.sendError(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        String code = context.getRequest().getParameter("code");
        String head = context.getRequest().getParameter("head");
        StringBuilder codeBuilt = new StringBuilder();
        codeBuilt.append(head);
        codeBuilt.append("public class Invoke00 { ");
        codeBuilt.append(code);
        codeBuilt.append(" }");
        LOGGER.log(Level.INFO, "Running code: " + codeBuilt.toString());

        File file = new File("Invoke00.java");
        file.delete();
        try {
            file.createNewFile();
        } catch (IOException IOE) {
            IOE.printStackTrace();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(codeBuilt.toString().getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int compileResult = compiler.run(null, System.out, null, file.getAbsolutePath());

        List<String> strList = new ArrayList<String>();
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"java", "Invoke00"},null,null);
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            process.waitFor();
            while ((line = input.readLine()) != null){
                strList.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 删除文件
        file.delete();
        new File("Invoke00.class").delete();

        StringBuilder sb = new StringBuilder();
        for (String i : strList) {
            sb.append(i + "\n");
        }

        context.renderJSON().renderData(sb.toString());
    }
}
