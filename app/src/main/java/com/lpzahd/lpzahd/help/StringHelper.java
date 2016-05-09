package com.lpzahd.lpzahd.help;

/**
 * Created by Administrator on 2016/5/9.
 */
public class StringHelper {

    /**
     * 拼接字符串 aa_bb_cc_
     * @param strs
     * @return
     */
    public static String stitchStr(String... strs) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : strs) {
            stringBuffer.append(s + "_");
        }

        return stringBuffer.toString();
//        return stringBuffer.substring(0, stringBuffer.length() - 1);
    }
}
