package cc.zruibin.modules.util;

import android.text.TextUtils;
import android.text.format.DateFormat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by ruibin.chow on 21/01/2018.
 */

public class StringUtil {

    private static String patternCoder = "(?<!\\d)\\d{6}(?!\\d)";
    private static String phonePatter = "^1\\d{10}$";

    /**32小写md5加密*/
    public static String md5(String plain) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plain.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            re_md5 = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }

    /**日期转换，几秒前，几分钟前，今天HH:mm，昨天HH:mm, MM月dd日 HH:mm, yyyy年MM月dd日 HH:mm*/
    public static String formatDate(String date){
        //把时区转换为东8区
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
        DateTimeZone.setDefault(DateTimeZone.forTimeZone(timeZone));

        DateTime nowDateTime = DateTime.now();
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        DateTime dateTime = DateTime.parse(date, format);

        int seconds = Seconds.secondsBetween(dateTime,nowDateTime).getSeconds();
        if (seconds < 60) {
            return "刚刚";
        }

        int minutes = Minutes.minutesBetween(dateTime,nowDateTime).getMinutes();
        if (minutes < 60) {
            return minutes + "分钟前";
        }

        int day = nowDateTime.getDayOfYear() - dateTime.getDayOfYear();
        int year = nowDateTime.getYear() - dateTime.getYear();
        if (year < 1 && day < 1) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("今天 HH:mm");
            return simpleDateFormat.format(dateTime.toDate());
        }

        if (year < 1 && day < 2) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("昨天 HH:mm");
            return simpleDateFormat.format(dateTime.toDate());
        }
        if (year < 1) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");
            return simpleDateFormat.format(dateTime.toDate());
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return simpleDateFormat.format(dateTime.toDate());
    }

    private static boolean regexMatcher(String string, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(string);
        return m.matches();
    }

    /**是否为手机号*/
    public static boolean isMobileNumber(String mobiles) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        return regexMatcher(mobiles, regex);
    }

    /**手机号隐藏*/
    public static String replacePhoneNumberWithAsterisk(String string) {
        String replaceStr = string.replaceAll("(\\d{3})\\d{6}(\\d{2})","$1******$2");;
        return replaceStr;
    }

    /**是否为email*/
    public static boolean isEmail(String email) {
        String regex = "^[A-Za-z]{1,40}@[A-Za-z0-9]{1,40}\\.[A-Za-z]{2,3}$";
        return regexMatcher(email, regex);
    }

    /**是不是网址url*/
    public static boolean isValidUrl(String url) {
        String regex = "^((http)|(https))+:[^\\s]+\\.[^\\s]*$";
        return regexMatcher(url, regex);
    }

    /**是否是中文*/
    public static boolean isValidChinese(String string) {
        String regex = "^[\\u4e00-\\u9fa5]+$";
        return regexMatcher(string, regex);
    }

    /**提取内容中的网址*/
//    public static String pickupURLString(String string) {
//        String regex = "\\bhttps?://[a-zA-Z0-9\\-.]+(?::(\\d+))?(?:(?:/[a-zA-Z0-9\\-._?,'+\\&%$=~*!():@\\\\]*)+)?";
//        Matcher m = Pattern.compile(regex).matcher(string);
//        String url = null;
//        while(m.find()){
//            url = m.group();
//        }
//        return url;
//    }
    public static String pickupURLString(String content) {
        if (StringUtil.isValidUrl(content)) {
            return content;
        }
        String regex = "((http[s]{0,1}|ftp)://[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)" +
                "|(www.[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})(:\\d+)?(/[a-zA-Z0-9\\.\\-~!@#$%^&*+?:_/=<>]*)?)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        String foundUrl = null;
        while (matcher.find()) {
            foundUrl = matcher.group(0);

        }
        return foundUrl;
    }

    /**数字转换*/
    public static String countTransition(long count) {
        if (count < 0) {
            return "0";
        }
        if (count < 10000) {
            return String.valueOf(count);
        } else {
            return String.valueOf(count/10000) + "." + String.valueOf(count%10000/10000) + "万";
        }
    }

    /** 获得当前时间 */
    public static CharSequence currentTime(CharSequence inFormat) {
        return DateFormat.format(inFormat, System.currentTimeMillis());
    }

    /** 时间戳转 yyyy年MM月dd日 HH:mm*/
    public static String getDateTime(String longTime){
        long time = Long.valueOf(longTime)*1000;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return  sdf.format(new Date(time));
    }

    /** 时间戳转 yyy年MM月dd日 HH:mm:ss*/
    public static String getDateSec(String longTime){
        long time = Long.valueOf(longTime)*1000;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return  sdf.format(new Date(time));
    }

    /** 时间戳转 MM月dd日 HH:mm*/
    public static String getDateMinite(String longTime){
        long time = Long.valueOf(longTime)*1000;
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
        return  sdf.format(new Date(time));
    }

    /** 时间戳转 yyyy-MM-dd HH:mm*/
    public static String getTime(String longTime){
        long time = Long.valueOf(longTime)*1000;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return  sdf.format(new Date(time));
    }

    /** 时间戳转 yyyy-MM-dd*/
    public static String getdate(String longTime){
        long time = Long.valueOf(longTime)*1000;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return  sdf.format(new Date(time));
    }

    /** 日期转时间戳*/
    public static String getTimeStamp(String dateTime, String format){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return String.valueOf(simpleDateFormat.parse(dateTime).getTime()/1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /** 从短信中截取验证码*/
    public static String patternCode(String patternContent) {
        if (TextUtils.isEmpty(patternContent)) {
            return null;
        }
        Pattern p = Pattern.compile(patternCoder);
        Matcher matcher = p.matcher(patternContent);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /** 检测手机号码*/
    public static boolean checkPhone(String patternContent){
        Pattern pattern = Pattern.compile(phonePatter);
        Matcher matcher =  pattern.matcher(patternContent);
        return matcher.matches();
    }

    /** 保留指定小数点位数,format传 "0.0" "0.00"形式分别保存一位，两位小数*/
    public static String doubleRound(double num, String format){
        DecimalFormat df = new DecimalFormat(format);
        return df.format(num);
    }

    /*** 判断单个字符串是否为空*/
    public static boolean isStr(String str){
        if(null != str && str.length() != 0) return true;
        return false;
    }

    /*** 判断多个字符串是否为空*/
    public static boolean isArrStr(String... str){
        if(null == str) return false;
        for(String s : str){
            if(!isStr(s)) return false;
        }
        return true;
    }

    /**是否含有表情*/
    public static boolean containsEmoji(String source) {
        int len = source.length();
        boolean isEmoji = false;
        for (int i = 0; i < len; i++) {
            char hs = source.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (source.length() > 1) {
                    char ls = source.charAt(i+1);
                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        return true;
                    }
                }
            } else {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c || hs == 0x2b1b || hs == 0x2b50|| hs == 0x231a ) {
                    return true;
                }
                if (!isEmoji && source.length() > 1 && i < source.length() -1) {
                    char ls = source.charAt(i+1);
                    if (ls == 0x20e3) {
                        return true;
                    }
                }
            }
        }
        return  isEmoji;
    }

    /**字节数转化为KB、MB、GB*/
    public static String getFileSizeString(long size) {
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + "B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + "KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + "GB";
        }
    }

}
