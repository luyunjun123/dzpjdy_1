/**
 * Created by 邢直 on 2019/1/12.
 */
var socket;
var socket2;
//load方法
$(function(){
    var timer=setInterval (showTime, 1000);
    var vdate= new Date();
    var vmonth =  ("0" + (vdate.getMonth() + 1)).slice(-2);
    var weekday=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
    var myddy=("0" + vdate.getDate()).slice(-2);
    var day = vdate.getDay();
    var week=weekday[day];

    $("#t_showdate").html(vdate.getFullYear() + '年' + vmonth + '月' + vdate.getDate() +'日');
    $("#t_timeandweek").html( fillZero(vdate.getHours(),2) + ':' + fillZero(vdate.getMinutes(),2) + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+week);

    Connect();
    Connect2();


    function showTime()
    {
        vdate= new Date();
        $("#t_timeandweek").html( fillZero(vdate.getHours(),2) + ':' + fillZero(vdate.getMinutes(),2) + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+week);

    }
    /*读取余票*/  //todo
  pageInit();
})

//界面初始化
function pageInit(){
    $.ajax({ url: "./threeHospitals/etk_getValidBillNo",
        async: true,
        type:"GET",
        context: document.body,
        success: function(responseTxt,statusTxt,xhr){
            if(responseTxt.status=="S_OK") {
                $("#sn").html(responseTxt.sn);
                $("#curbillno").html(responseTxt.curbillno);
                $("#surplus").html(responseTxt.surplus);
                var ptitle = responseTxt.pagetile
                $(document).attr("title",ptitle);
            }else
            {
                alert("Error: "+xhr.status+": "+xhr.statusText+"---------");
            }
        }
    });
}



// 读卡器控制方法
function Connect(){
    try{
        socket=new WebSocket('ws://127.0.0.1:18899');
    }catch(e){
        alert('读卡器加载失败');
        return;
    }
    socket.onopen = sOpen;
    socket.onerror = sError;
    socket.onmessage= sMessage;
    socket.onclose= sClose;
}
//二维码控制方法
function Connect2(){
    try{
        socket2=new WebSocket('ws://localhost:8095/websocket');
    }catch(e){
        alert('二维码加载失败');
        return;
    }
    socket2.onopen = sOpen2;
    socket2.onerror = sError2;
    socket2.onmessage= sMessage2;
    socket2.onclose= sClose2;
}
function reConnect(){
    console.log("socket 连接断开，正在尝试重新建立连接");
  // Connect2();
}
function sOpen2(){
    // alert('connect success!');像客户端发送消息
   socket2.send("Ewmopen");
    //heartCheck.reset().start();
}
function sOpen(){
    //alert('connect success!');
    /*读卡*/
    socket.send("READCARD");
}
function sError(e){

    alert("error " + e+"读卡器，请联系工作人员");
}
function sError2(e){

    alert("error " + e+"二维码开启异常，请联系工作人员");
}
function sClose(e){
    //alert("connect closed:" + e.code);
 //   reConnect();
}
function sClose2(e){
    //alert("connect closed:" + e.code);
    console.debug("二维码关闭");
  //  reConnect();
}
function Close(){
    socket.close();
}
/*
* 二维码
* */
function sMessage2(msg){
   // heartCheck.reset().start();
    var result = msg.data.split("#");
    //数据  CODE_READCARD_SUCCESS,0005939871
    if (result[0]=="CODE_READCARD_SUCCESS"){
        var decode = result[1];
        // 0005939871
        if ( result[1].length<=10){
               var EwmDecode = window.localStorage;
            //二维码存入本地浏览器中
            EwmDecode.setItem("decode",decode);
            getEwmPatientInfo(decode);
            //刷完二维码以后关闭二维码界面
            ewmlogintipclose();
        }else {
            ewmlogintipclose();
            Ewin.alert({ message: "二维码有误"}).on(function (e) {});
            window.localStorage.clear();
        }
    }else if(result[0]=="CODE_POPCARD_SUCCESS"){
        document.getElementById("ddsound1").play();
        loading();
        setTimeout(function(){removeloading();socket.send("READCARD");},6000);
    }
}
/*
* 读卡
* */
function sMessage(msg){
    var result = msg.data.split("#");
    if (result[0]=="CODE_READCARD_SUCCESS"){
        var cardno = result[1];
        if (result[1].indexOf("=")>-1){
            //cardno= result[1].substring(result[1].indexOf("0")+1,result[1].indexOf("="));
            cardno= result[1].substring(0,result[1].indexOf("="));
        }
       getPatientInfo(cardno);
    }else if(result[0]=="CODE_POPCARD_SUCCESS"){
        document.getElementById("ddsound1").play();
        loading();
        //READCARD#
        setTimeout(function(){removeloading();socket.send("READCARD");},6000);
    }
}
//微信二维码得到数据后执行的方法
function getEwmPatientInfo(decode) {
    console.log("二维码进入方法")
    loading();
    $.ajax({
        //请求地址(博思发出请求查询,获取打印列表)
        url: "./dzpjinterf/getEwmBillList",
        //默认设置为true，所有请求均为异步请求
        async: true,
        //   datatype:"text",
        //发送到服务器的数据，要求为Object或String类型的参数
        data:{decode:decode,Token:Token},
        type:"GET",
        //context参数作用 将回调里的this替换为context里对应的值
        //    context: document.body,
        success: function(responseTxt,statusTxt,xhr){

            // responseTxt,statusTxt,xhr
            // statusTxt=="success"
            if(statusTxt=="success") {
                try {
                    console.log("二维码信息",responseTxt)
                    var st1 = window.localStorage;
                    //查询出来发票列表存到本地服务器中
                    st1.setItem("responseTxt",JSON.stringify(responseTxt));
                    console.debug(responseTxt);
                    //周期的
                    // setInterval(function() {
                    //     console.log("hello")
                    // }, 1000);
                    //三秒后跳转
                    // var timer = setTimeout(function() {
                    //     //跳转
                    //     window.location.replace("pjdy.html");
                    // }, 3000);
                    window.location.replace("pjdy.html");
                } catch (e) {
                    console.debug("错误");
                    Ewin.alert({ message: e.message}).on(function (e) {});
                  //  socket.send("POPCARD");
                    removeloading();
                    window.localStorage.removeItem("decode");
                    window.localStorage.removeItem("responseTxt");
                    window.localStorage.clear();
                    returnmain();
                }

                removeloading();
            }else
            {
               // socket.send("POPCARD");
                alert("Error: "+xhr.status+": "+xhr.statusText);
                window.localStorage.removeItem("decode");
                window.localStorage.removeItem("responseTxt");
                window.localStorage.clear();
                removeloading();
            }
        }
    });
}
//获取病人信息
function getPatientInfo(cardno) {
    // //2019-04-28
    // var st = window.localStorage;
    // st.setItem("cardno",cardno);
    // window.location.replace("pjdy.html");
    // return;
    loading();
    $.ajax({
        //请求地址
        url: "./dzpjinterf/getpainfobycard",
        //默认设置为true，所有请求均为异步请求
        async: true,
        //发送到服务器的数据，要求为Object或String类型的参数
        data:{cardno:cardno},
        type:"GET",
        //context参数作用 将回调里的this替换为context里对应的值
        context: document.body,
        success: function(responseTxt,statusTxt,xhr){
            if(statusTxt=="success") {
                try {
                    console.log("asd",responseTxt);
                    showpatientinfo(responseTxt);
                } catch (e) {
                    Ewin.alert({ message: e.message}).on(function (e) {});
                    //退卡
                    socket.send("POPCARD");
                    removeloading();
                }

                removeloading();
            }else
            {
                socket.send("POPCARD");
                alert("Error: "+xhr.status+": "+xhr.statusText+"000000");
            }
        }
    });
}

//页面控制方法
function f_nav(menuno){
    var st = window.localStorage;
    //todo
    cardno="1801089820";
    getPatientInfo(cardno)

    if (st.getItem("cardno")==null||st.getItem("cardno")==""||st.getItem("cardno")=="undefined")
    {
        showlogintip();
    }else if(menuno==1){
        window.location.replace("pjdy.html");
    }

}

function f_navewm(menuno){
    var st = window.localStorage;
    //点击二维码 没有从localStorage查到号码就弹出提示框并打开提示音乐
    if (st.getItem("decode")==null||st.getItem("decode")==""||st.getItem("decode")=="undefined")
    {
        ewmshowlogintip();
        //打开二维码命令
        socket2.send("openEwm");
        setTimeout(function() {
            //20秒后关闭
            ewmlogintipclose()
        }, 20000);
    }else if(menuno==1){
        window.location.replace("pjdy.html");
    }

}
function ewmlogintipclose(){

    $("#showEwmImg").modal('hide');
    socket2.send("closeEwm");
}
//json
function showpatientinfo(json){
    if (json.status !="S_OK"){
        console.debug(44444)
        Ewin.alert({ message: json.message}).on(function (e) {});
        socket.send("POPCARD");//弹卡
        return;
    }
     var pinfo = json.data;
     console.log("pinfio",pinfo.patName)
    /*设置模态框*/
    $("#m_name").html(pinfo.patName);
    $("#m_cardno").html(pinfo.caseNo);
    var sex=pinfo.patSex;
    if(sex=="1"){
        sex="男";
    }else if(sex=="2"){
        sex="女";
    }else {
        sex="未填写";
    }
    console.log("sex",sex);
    $("#m_sex").html(sex);
    $("#m_age").html(pinfo.patAge);
    $("#m_socialno").html(pinfo.certId);
    $("#m_patientid").html(pinfo.patId);
    console.log("数据",json);
    logintipclose();

    $('#patientInfo').modal({
        backdrop: 'static',//点击空白处不关闭对话框
        keyboard: false,//键盘关闭对话框
        show:true//弹出对话框
    });
}

function showlogintip() {
    $('#showReadcardImg').modal({
        backdrop: 'static',//点击空白处不关闭对话框
        keyboard: false,//键盘关闭对话框
        show:true//弹出对话框
    });
    document.getElementById("ddsound").play();
}

function ewmshowlogintip() {
    $('#showEwmImg').modal({
        backdrop: 'static',//点击空白处不关闭对话框
        keyboard: false,//键盘关闭对话框
        show:true//弹出对话框
    });
    // document.getElementById("ddsoundewm").play();
    document.getElementById("EWMddsound").play();
}

function logintipclose(){
   //open
    $("#showReadcardImg").modal('hide');
}
function ewmlogintipclose(){

    $("#showEwmImg").modal('hide');
    socket2.send("closeEwm");
}


function confirmpi(){
    var st = window.localStorage;
    /*设置信息值*/
    st.setItem("cardno",$("#m_socialno").text());
    st.setItem("patientid",$("#m_patientid").text());
    st.setItem("patientname",$("#m_name").text());
    window.location.replace("pjdy.html");
}
function popcard() {
    try{
        socket.send("POPCARD");
    }catch (e){}

}
