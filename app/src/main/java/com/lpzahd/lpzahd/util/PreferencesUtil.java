package com.lpzahd.lpzahd.util;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.lpzahd.lpzahd.app.App;
import com.lpzahd.lpzahd.constance.Constances;
import org.xutils.x;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * SharePreference 工具类
 * 主要储存 账号信息
 *
 * Created by mac-lpzahd on 16/4/13.
 */
public class PreferencesUtil {

    private static Application app = App.app;

    /**
     * 保存账号昵称数组
     * @param nicks
     */
    public static void saveUserNicks(String... nicks) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(app);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constances.User.NICKS, handleNicks(nicks));
        editor.commit();
    }

    /**
     * 增加账号昵称信息
     * @param nicks
     */
    public static void appendUserNicks(String... nicks) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(app);
        String oriNicks = sharedPreferences.getString(Constances.User.NICKS, "");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constances.User.NICKS, appendNicks(oriNicks, nicks));
        editor.commit();
    }

    /**
     * 获取所有账号昵称信息
     * @return
     */
    public static List<String> getUserNicks() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(app);
        String nicks = sharedPreferences.getString(Constances.User.NICKS, "");
        if(TextUtils.isEmpty(nicks)) {
            return null;
        } else {
            List<String> nickList = new ArrayList<String>();
            if(nicks.contains(",")) {
                for(String nick : nicks.split(",")) {
                    nickList.add(nick);
                }
            } else {
                nickList.add(nicks);
            }
            return nickList;
        }
    }

    /**
     * 保存对应账号的密码
     * @param nick
     * @param password
     */
    public static void saveUserPassword(String nick, String password) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(app);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(nick, PreferencesHelper.encryptPassword(password));
        editor.commit();
    }

    /**
     * 获取密码信息
     * @param nick
     * @return
     */
    public static String getUserPassword(String nick) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(app);
        String password = sharedPreferences.getString(nick, "");
        return PreferencesHelper.decryptPassword(password);
    }

    /**
     * 清理部分账号信息
     * @param nicks
     */
    public static void clearUserNickInfo(String... nicks) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(app);
        String oriNicks = sharedPreferences.getString(Constances.User.NICKS, "");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for(String nick : nicks) {
            editor.putString(nick, "");
        }
        editor.putString(Constances.User.NICKS, removeNicks(oriNicks, nicks));
        editor.commit();
    }

    /**
     * 清理所有账号信息
     */
    public static void clearUserInfo() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(app);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for(String nick : getUserNicks()) {
            editor.putString(nick, "");
        }
        editor.putString(Constances.User.NICKS, "");
        editor.commit();
    }

    /**
     * 整合成  小明，小芳，小红  样式
     * @param nicks
     * @return
     */
    private static String handleNicks(String... nicks) {
        StringBuffer sb = new StringBuffer();
        for (String nick : nicks) {
            sb.append(nick);
            sb.append(",");
        }
        sb.deleteCharAt(sb.toString().length());
        return sb.toString();
    }

    /**
     * 解析nicks 成为set集合
     * @param nicks
     * @return
     */
    private static Set<String> parseNicks(String nicks) {
        Set<String> nickSet = new HashSet<String>();

        if(TextUtils.isEmpty(nicks)) {
            return nickSet;
        }

        if(nicks.contains(",")) {
            for(String nick : nicks.split(",")) {
                nickSet.add(nick);
            }
        } else {
            nickSet.add(nicks);
        }
        return nickSet;
    }

    /**
     * 往oriNicks 中添加元素
     * @param oriNicks
     * @param appendNicks
     * @return
     */
    private static String appendNicks(String oriNicks, String... appendNicks) {
        Set<String> nickSet = parseNicks(oriNicks);
        for(String appendNick : appendNicks) {
            nickSet.add(appendNick);
        }
        return handleNicks((String[]) nickSet.toArray());
    }

    /**
     * 从oriNicks 中移除 包含 removeNicks 元素
     * @param oriNicks
     * @param removeNicks
     * @return
     */
    private static String removeNicks(String oriNicks, String... removeNicks) {
        Set<String> nickSet = parseNicks(oriNicks);
        for(String removeNick : removeNicks) {
            nickSet.remove(removeNick);
        }
        return handleNicks((String[]) nickSet.toArray());
    }

    public static class PreferencesHelper {

        /**
         * 密码加密
         * @param password
         * @return
         */
        public static String encryptPassword(String password) {
            return EncodeUtil.encryptBASE64(password);
        }

        /**
         * 密码解密
         * @param password
         * @return
         */
        public static String decryptPassword(String password) {
            return EncodeUtil.decryptBASE64(password);
        }
    }

}
