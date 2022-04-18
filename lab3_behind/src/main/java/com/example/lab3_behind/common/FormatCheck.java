package com.example.lab3_behind.common;

import com.example.lab3_behind.domain.dto.CourseApplyingData;
import com.example.lab3_behind.domain.dto.UserEnteringData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatCheck {

    public static void courseApplyingDataCheck(CourseApplyingData courseApplyingData) throws Exception {
        if(courseApplyingData.getCapacity() <= 0){
            throw new Exception("课程容量不合法");
        }
        if(courseApplyingData.getCredits() <= 0){
            throw new Exception("课程学分不合法");
        }
        if(courseApplyingData.getCreditHours() <=0){
            throw new Exception("课程学时不合法");
        }
    }

    public static void userEnteringDataCheck(UserEnteringData userEnteringData) throws Exception {
        if(!isIDNum(userEnteringData.getIdNum())){
            throw new Exception("身份证号格式有误");
        }
        if(userEnteringData.getEmail()!=null && (!isEmail(userEnteringData.getEmail()))){
            throw new Exception("邮箱格式有误");
        }
        if(!isPassword("fDu" + userEnteringData.getNumber())){
            throw new Exception("密码格式有误");
        }
    }

    public static boolean isIDNum(String IDNumber){
        if (IDNumber == null || "".equals(IDNumber)) {
            return false;
        }
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        //假设18位身份证号码:41000119910101123X  410001 19910101 123X
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //(18|19|20)                19（现阶段可能取值范围18xx-20xx年）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十七位奇数代表男，偶数代表女）
        //[0-9Xx] 0123456789Xx其中的一个 X（第十八位为校验值）
        //$结尾

        //假设15位身份证号码:410001910101123  410001 910101 123
        //^开头
        //[1-9] 第一位1-9中的一个      4
        //\\d{5} 五位数字           10001（前六位省市县地区）
        //\\d{2}                    91（年份）
        //((0[1-9])|(10|11|12))     01（月份）
        //(([0-2][1-9])|10|20|30|31)01（日期）
        //\\d{3} 三位数字            123（第十五位奇数代表男，偶数代表女），15位身份证不含X
        //$结尾
        boolean matches = IDNumber.matches(regularExpression);

        //判断第18位校验值
//        if (matches) {
//            if (IDNumber.length() == 18) {
//                try {
//                    char[] charArray = IDNumber.toCharArray();
//                    //前十七位加权因子
//                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
//                    //这是除以11后，可能产生的11位余数对应的验证码
//                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
//                    int sum = 0;
//                    for (int i = 0; i < idCardWi.length; i++) {
//                        int current = Integer.parseInt(String.valueOf(charArray[i]));
//                        int count = current * idCardWi[i];
//                        sum += count;
//                    }
//                    char idCardLast = charArray[17];
//                    int idCardMod = sum % 11;
//                    return idCardY[idCardMod].equalsIgnoreCase(String.valueOf(idCardLast));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return false;
//                }
//            }
//
//        }
        return matches;
    }

    public static boolean isPassword(String password){
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        boolean isSpecial = false;//定义一个boolean值，用来表示是否包含特殊字符

        for (int i = 0; i < password.length(); i++) {
            //判断是否含有特殊字符
            Pattern p = Pattern.compile("[~`#$%^&*!@.,()\\\\{}|:;?<>]");
            Matcher m = p.matcher(password);
            if (Character.isDigit(password.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            }
            if (Character.isLetter(password.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
            if (m.find()) {//判断是否包含特殊字符
                isSpecial = true;
            }
        }
        //密码的正则规则 6到32位  包含数字 字母 特殊字符 至少两种
        String regex = "^([a-zA-Z0-9~`#$%^&*!@.,()\\\\{}|:;?<>]){6,32}$";
        int digit = isDigit ? 1 : 0;
        int letter = isLetter ? 1 : 0;
        int special = isSpecial ? 1 : 0;
        boolean minTwo = ((digit + letter + special) >= 2) ;
        return  minTwo && password.matches(regex);
    }

    public static boolean isEmail(String email){
        //判断是否为空邮箱
        int k = 0;
        if(email == null) {
            return false;
        }
         /*
          * 单引号引的数据 是char类型的
                                    双引号引的数据 是String类型的
                                    单引号只能引一个字符
                                    而双引号可以引0个及其以上*
          */

        //判断是否有仅有一个@且不能在开头或结尾
        if(email.indexOf("@") > 0 && email.indexOf('@') == email.lastIndexOf('@') && email.indexOf('@') < email.length()-1) {
            k++;
        }

        //判断"@"之后必须有"."且不能紧跟
        if(email.indexOf('.',email.indexOf('@')) > email.indexOf('@')+1 ) {
            k++;
        }
        //判断"@"之前或之后不能紧跟"."
        if(email.indexOf('.') < email.indexOf('@')-1 || email.indexOf('.') > email.indexOf('@')+1 ) {
            k++;
        }
        //@之前要有6个字符
        if(email.indexOf('@') > 5 ) {
            k++;
        }

        if(email.endsWith("com") || email.endsWith("org") || email.endsWith("cn") ||email.endsWith("net")) {
            k++;
        }
        if(k == 5) {
            return true;
        }
        return false;
    }
}
