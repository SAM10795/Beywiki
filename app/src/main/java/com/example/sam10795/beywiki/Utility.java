package com.example.sam10795.beywiki;

/**
 * Created by SAM10795 on 03-04-2015.
 */
public class Utility
{
    public static String getURL(String text)
    {
        int c = text.indexOf(",");
        int c2 = text.indexOf(",",c+1);
        if(c2!=-1) {
            return text.substring(c+1,c2);
        }
        else
        {
            return text.substring(c+1);
        }
    }
    public static String getTitle(String text)
    {
        int c = text.indexOf(",");
        return text.substring(0,c);
    }
    public static String getNum(String text)
    {
        int c = text.indexOf("Number:");
        int d = text.indexOf(",",c);
        return text.substring(c+7,d);
    }
    public static String getSys(String text)
    {
        int c = text.indexOf("System:");
        int d = text.indexOf(",",c);
        return text.substring(c+7,d);
    }
    public static String getType(String text)
    {
        int c = text.indexOf("Type:");
        int d = text.indexOf(",",c);
        if(text.substring(c+5,d).length()<30) {
            return text.substring(c + 5, d);
        }
        else
        {
            return " - ";
        }
    }
    public static String getmainID(String text)
    {
        int i;
        if((i=text.indexOf("-"))!=-1)
        {
            return text.substring(0,i);
        }
        else
        {
            return "";
        }
    }
    public static int getnumID(String text)
    {
        int i=0;
         int num = i;
            int end = text.length();
            for(int j = i;j<text.length();j++)
            {
                if(Character.isDigit(text.charAt(j)))
                {
                    num = j;
                    break;
                }
            }
            for(int k = num;k<text.length();k++)
            {
                if(!Character.isDigit(text.charAt(k)))
                {
                    end = k;
                    break;
                }
            }
        if(num!=end)
        {
            return Integer.parseInt(text.substring(num, end));
        }
        else
        {
            return 0;
        }
    }
}
