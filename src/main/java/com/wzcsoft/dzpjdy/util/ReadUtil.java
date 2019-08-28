package com.wzcsoft.dzpjdy.util;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lyj
 * @date 2019/7/18 1:02
 */
public class ReadUtil {

    public static void WriteStringToFile(String invoiceSeq,String patientid,String responseXml) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String format = df.format(new Date());
        // 第1步、使用File类找到一个文件
        File f= new File("C:\\myfile") ;	// 声明File对象
        // 第2步、通过子类实例化父类对象
        Writer out = null	;// 准备好一个输出的对象
        try {
            // 通过对象多态性，进行实例化
            out = new FileWriter(f,true) ;
            // 第3步、进行写操作
            // 准备要写入的字符串
            out.write("返回状态："+responseXml+"，时间:"+format+"，返回号码：("+invoiceSeq+")，病人id："+patientid+"\r\n") ;// 将内容输出，保存文件
            // 第4步、关闭输出流
            out.close() ;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("写入异常");
        }
    }
}
