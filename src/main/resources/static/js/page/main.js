/**
 * Created by 邢直 on 2019/1/12.
 */
var socket;

//load方法
$(function(){
    var timer=setInterval (showTime, 1000);
    var vdate= new Date();
    var vmonth = vdate.getMonth() +1;
    var weekday=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
    var myddy=vdate.getDay();
    var week=weekday[myddy];

    $("#t_showdate").html(vdate.getFullYear() + '年' + vmonth + '月' + vdate.getDate() +'日');
    $("#t_timeandweek").html( fillZero(vdate.getHours(),2) + ':' + fillZero(vdate.getMinutes(),2) + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+week);

    Connect();

    function showTime()
    {
        vdate= new Date();
        $("#t_timeandweek").html( fillZero(vdate.getHours(),2) + ':' + fillZero(vdate.getMinutes(),2) + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+week);

    }
})

// 读卡器控制方法
function Connect(){
    try{
        socket=new WebSocket('ws://127.0.0.1:18899');
    }catch(e){
        alert('error');
        return;
    }
    socket.onopen = sOpen;
    socket.onerror = sError;
    socket.onmessage= sMessage;
    socket.onclose= sClose;
}

function sOpen(){
    //alert('connect success!');
    socket.send("READCARD");
}
function sError(e){
    alert("error " + e);
}
function sClose(e){
    //alert("connect closed:" + e.code);
}
function Close(){
    socket.close();
}

function sMessage(msg){
    //alert(msg.data);
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
        setTimeout(function(){removeloading();socket.send("READCARD");},6000);
    }
}

//接口-获取病人信息
function getPatientInfo(cardno) {
    // //2019-04-28
    // var st = window.localStorage;
    // st.setItem("cardno",cardno);
    // window.location.replace("pjdy.html");
    // return;


    loading();
    $.ajax({ url: "./hisinterf/getpainfobycard",
        async: true,
        data:{cardno:cardno},
        type:"GET",
        context: document.body,
        success: function(responseTxt,statusTxt,xhr){
            if(statusTxt=="success") {
                showpatientinfo(responseTxt);
                removeloading();
            }else
            {
                socket.send("POPCARD");
                alert("Error: "+xhr.status+": "+xhr.statusText);
            }
        }
    });
}

//页面控制方法
function f_nav(menuno){
    var st = window.localStorage;

    if (st.getItem("cardno")==null||st.getItem("cardno")==""||st.getItem("cardno")=="undefined")
    {
        showlogintip();
    }else if(menuno==1){
        window.location.replace("pjdy.html");
    }

}

function showpatientinfo(json){
    if (json.status !="S_OK"){
        Ewin.alert({ message: json.message}).on(function (e) {});
        socket.send("POPCARD");
        return;
    }
    
    var pinfo = json.data;

    $("#m_name").html(pinfo.name);
    $("#m_cardno").html(pinfo.cardno);
    $("#m_sex").html(pinfo.sex);
    $("#m_age").html(pinfo.age);
    $("#m_socialno").html(pinfo.socialno);
    $("#m_patientid").html(pinfo.patientid);

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

function logintipclose(){
    $("#showReadcardImg").modal('hide');
}

function confirmpi(){
    var st = window.localStorage;
    st.setItem("cardno",$("#m_cardno").text());
    st.setItem("patientid",$("#m_patientid").text());
    st.setItem("socialno",$("#m_socialno").text());
    st.setItem("patientname",$("#m_name").text());
    window.location.replace("pjdy.html");
}

