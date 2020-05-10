package org.b3log.solo.bolo.tool;

/**
 * <h3>bolo-solo</h3>
 * <p>XSS 过滤</p>
 *
 * @author : https://github.com/adlered
 * @date : 2020-05-10
 **/
public class AntiXSS {
    public static String getSafeStringXSS(String s) {
        if (s == null || "".equals(s)) {
            return s;
        }
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '\'':
                    sb.append("&prime;");// &acute;");
                    break;
                case '′':
                    sb.append("&prime;");// &acute;");
                    break;
                case '\"':
                    sb.append("&quot;");
                    break;
                case '＂':
                    sb.append("&quot;");
                    break;
                case '&':
                    sb.append("＆");
                    break;
                case '#':
                    sb.append("＃");
                    break;
                case '\\':
                    sb.append('￥');
                    break;
                case '=':
                    sb.append("&#61;");
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString();
    }
}
