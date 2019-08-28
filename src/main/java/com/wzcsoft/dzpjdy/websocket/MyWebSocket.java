package com.wzcsoft.dzpjdy.websocket;

import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.channels.ClosedChannelException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ServerEndpoint(value = "/websocket")
@Component
public class MyWebSocket {

    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    // concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    public static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;


    private static boolean  decodestate = false;
    private static boolean stateflag = true;
    static int s = 0;
    private static String sCode = "";
    private Vapi V = new Vapi();

    private static ScheduledExecutorService executors = Executors.
            newSingleThreadScheduledExecutor(new CustomizableThreadFactory("scan-code-pool-"));


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        try {
            this.session = session;
            webSocketSet.add(this); // 加入set中
            if(V.vbarOpen()) {
                V.vbarAddSymbolType(1, true);
            }

            executors.scheduleWithFixedDelay(() -> {
                System.out.println("Thread:" + Thread.currentThread().getName());
//                System.out.println("decodestate:" + decodestate);

                if (decodestate) {
                    if(V.vbarIsConnect()) {

                        String decode = null;
                        try {
                            decode = V.vbarScan();
                            System.out.println("decode: " + decode);
                            if (decode!=null){
                                V.vbarBeep();
                                //往前端websocket抛数据
                                for (MyWebSocket item : webSocketSet) {
                                    try {
                                        item.sendMessage("CODE_READCARD_SUCCESS#"+decode);
                                    } catch (IOException e) {
                                        //
                                        e.printStackTrace();
                                    }
                                }

                                decodestate =false;//不在进入这个循环
//                            System.out.println(5);
                                V.vbarBacklight(false);//关灯
                                stateflag = false;
                            }
                        } catch (UnsupportedEncodingException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }else{

//                        System.out.println("===============================再次打开二维码设备===================================");
                        if(V.vbarOpen()) {
//                            System.out.println("===============================再次打开二维码设备成功===================================");
                            V.vbarAddSymbolType(1, true);
                        }

                    }
                }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            }, 1000, 1000,TimeUnit.MILLISECONDS);
        }catch (Exception e){
            System.out.println("错误");
        }



    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this); // 从set中删除
        subOnlineCount(); // 在线数减1
        System.out.println("有一连接关闭！当前在线人数为 : " + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     *            客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        if ("openEwm".equals(message)){
            decodestate = true;
            V.vbarBacklight(true);
        }
        if ("closeEwm".equals(message)){
            V.vbarBacklight(false);//关灯
            //decodestate = false;
        }
//         群发消息
        for (MyWebSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error)  {
            try {

            }catch (Exception e){
                System.out.println("发生错误");
                error.printStackTrace();
            }
    }

    public void sendMessage(String message) throws IOException {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("sendMessage:"+message);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
        this.session.getBasicRemote().sendText(message);
        // this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {
        System.out.println("=================================");
        System.out.println("sendInfo:"+message);
        System.out.println("=================================");
        for (MyWebSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                //
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }

   /* public void  run(){
        int i=0;
//        if(V.vbarOpen())
//        {
//            V.vbarAddSymbolType(1, true);
//        }
        while(true) {
            System.out.println("Thread:" + Thread.currentThread().getName());
            //System.out.println("decodestate:" + decodestate);
            if (decodestate) {
                if(V.vbarIsConnect()) {
                    //System.out.println("vbarIsConnect");
//                    boolean b = V.vbarBacklight(true);
//                    if(!stateflag)//开灯
//                    {
////                        System.out.println(2);
//                        boolean b = V.vbarBacklight(true);
//                        System.out.println(b+"开灯状态");
//                        stateflag = true;
//                    }
                    String decode = null;
                    try {
//                        System.out.println(3);
                        decode = V.vbarScan();
                        System.out.println("decode: " + decode);
                        if (decode!=null){
                            V.vbarBeep();
//                            System.out.println(4);
//                            System.out.println(decode);
//                            sCode = decode;
                            //往前端websocket抛数据
                            for (MyWebSocket item : webSocketSet) {
                                try {
                                    item.sendMessage("CODE_READCARD_SUCCESS#"+decode);
                                } catch (IOException e) {
                                    //
                                    e.printStackTrace();
                                }
                            }

                            decodestate =false;//不在进入这个循环
//                            System.out.println(5);
                            V.vbarBacklight(false);//关灯
                            stateflag = false;
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }else{

//                        System.out.println("===============================再次打开二维码设备===================================");
                    if(V.vbarOpen()) {
//                            System.out.println("===============================再次打开二维码设备成功===================================");
                        V.vbarAddSymbolType(1, true);
                    }

                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/
}