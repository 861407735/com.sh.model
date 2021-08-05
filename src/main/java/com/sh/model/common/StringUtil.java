package com.sh.model.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n"); //去除字符串中的空格、回车、换行符、制表符
            Matcher m = p.matcher(str);  //匹配
            dest = m.replaceAll("");
        }
        return dest;
    }
}
