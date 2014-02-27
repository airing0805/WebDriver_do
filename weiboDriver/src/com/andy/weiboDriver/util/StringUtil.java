package com.andy.weiboDriver.util;

public class StringUtil {  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
        String string = "It's so hard to tell someone how you really feel about them when you're too afraid of losing what you already have. ~~~有时候，要坦白对某个人的感觉真的太难了，因为太害怕连现在这种关系都失去。";  
        System.out.println(subBeginFirstChina(string));  
    }  
    /** 
     *  
     * @param content 输入的内容 
     * @param maxSize 最大长度 
     * @return 
     */  
    public static String judgeChina(String content, Integer maxSize) {  
        Integer index = 0;  
  
        StringBuffer sBuffer = new StringBuffer();  
        for (int i = 0; i < content.length(); i++) {  
            String retContent = content.substring(i, i + 1);  
            // 生成一个Pattern,同时编译一个正则表达式  
            boolean isChina = retContent.matches("[\u4E00-\u9FA5]");  
            boolean isCapital = retContent.matches("[A-Z][a-z]");  
              
           if(isChina){
        	  System.out.println(content.substring(i));
        	  break;
           }
        }  
        return sBuffer.toString();  
    }  
    
    public static int  chinaIndex(String content) {  
  
        for (int i = 0; i < content.length(); i++) {  
            String retContent = content.substring(i, i + 1);  
            boolean isChina = retContent.matches("[\u4E00-\u9FA5]");  
              
           if(isChina){
        	  return i;
           }
        }  
        return -1;  
    }
    
    public static String subBeginFirstChina(String content) {  
        for (int i = 0; i < content.length(); i++) {  
            String retContent = content.substring(i, i + 1);  
            boolean isChina = retContent.matches("[\u4E00-\u9FA5]");  
           if(isChina){
        	  return content.substring(i);
           }
        }  
        return "";  
    }
    
    
}  
