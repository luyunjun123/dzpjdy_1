package com.wzcsoft.dzpjdy.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

import static com.wzcsoft.dzpjdy.websocket.MyWebSocket.webSocketSet;

@Controller
public class InitController {

    @RequestMapping("/websocket")
    public String init() {
        System.out.println("--------------------------------------------------");
        System.out.println("websocket init");
        System.out.println("--------------------------------------------------");
        return "websocket.html";
    }

    @RequestMapping("/ss")
    public String ss(){
        for (MyWebSocket item : webSocketSet) {
            try {
                item.sendMessage("11111111111111");
            } catch (IOException e) {
                continue;
            }
        }
        return "main.html";
    }
}