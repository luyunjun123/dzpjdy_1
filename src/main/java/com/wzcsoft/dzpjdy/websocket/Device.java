package com.wzcsoft.dzpjdy.websocket;

import java.io.UnsupportedEncodingException;

public class Device implements Runnable{

    public static void main(String[] args) {

        Device devices = new Device();
        Thread device = new Thread(devices);
        device.start();

    }

    boolean decodestate = true;
    boolean stateflag = true;
    Vapi V = new Vapi();
    public void run(){
        int i=0;
        if(V.vbarOpen())
        {
            V.vbarAddSymbolType(1, true);
        }
        while(true)
        {
//            System.out.println(i++);

            if (decodestate)
            if(V.vbarIsConnect())
            {
                if(!stateflag)
                {
                    V.vbarBacklight(true);
                    stateflag = true;
                }
                String decode = null;
                try {
                    decode = V.vbarScan();
                    if (decode!=null){
                    System.out.println(decode);
                        decodestate =false;
                    }
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if(decode != null)
                {
                    V.vbarBeep();
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }else{
                if(stateflag)
                {
                    System.out.println("设备未连接");
                    stateflag = false;
                }
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}

