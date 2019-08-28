package com.wzcsoft.dzpjdy.websocket;

public class ss {
    @SuppressWarnings("static-access")
    public static void main(String[] args) {
        Thread t =Thread.currentThread();
        t.setName("单例线程");
 //有1000条数据需要更新，由一个人来完成
        for(int i=0;i<1000;i++){
            System.out.println(t.getName()+"正在执行"+i);
            //t.sleep(500);  //线程休眠中
        }

    }
}