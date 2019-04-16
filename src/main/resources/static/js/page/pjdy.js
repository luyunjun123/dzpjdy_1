/**
 * Created by 邢直 on 2019/1/12.
 */
var socket;
var pageSize = 8;
var pageNo = 0;
var pageCount=0;
var chargetime;
var second = 180;

var pbillName,pbillBatchCode,pbillNo,prandom,pivcDateTime,pbusDate,ptotalAmt,pvalidpbillno;
var paperBillno;

$(function(){
    $("#chargetime").flatpickr({fwidth:"500"});
    Connect();

    var timer=setInterval (showTime, 1000);
    var vdate= new Date();
    var vmonth = vdate.getMonth() +1;
    var weekday=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
    var myddy=vdate.getDay();
    var week=weekday[myddy];

    $("#t_showdate").html(vdate.getFullYear() + '年' + vmonth + '月' + vdate.getDate() +'日');
    $("#t_timeandweek").html( fillZero(vdate.getHours(),2) + ':' + fillZero(vdate.getMinutes(),2) + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+week);

    function showTime()
    {
        vdate= new Date();
        $("#t_timeandweek").html( fillZero(vdate.getHours(),2) + ':' + fillZero(vdate.getMinutes(),2) + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+week);

        if(second==0)
        {
            clearInterval(timer);
            //调用退卡
            popcard();
        }
        second--;
        $("#t_countdown").html('将在<font color="#4285FF">'+ second +'秒</font>后返回首页并退卡');

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
}
function sError(e){
    alert("error " + e);
}
function sClose(e){
    //alert("connect closed:" + e.code);
}
function Close(){
    socket.close();
    window.localStorage.clear();
    window.location.replace("main.html");
}


function sMessage(msg){
    //alert(msg.data);
    var result = msg.data.split("#");

    if (result[0]=="CODE_POPCARD_SUCCESS"){
        document.getElementById("ddsound").play();
        loading();
        setTimeout(function(){removeloading();Close();},6000);
    }else if(result[0]=="CODE_POPCARD_FAILED"){
        Ewin.alert({ message: "退卡操作出错，错误信息：" + result[1]}).on(function (e) {});
    }
    // else if(result[0]=="CODE_SCAN_SUCCESS"){
    //     var realPaperBillno = result[1];
    //     if (realPaperBillno==paperBillno){
    //         getbillinfo();
    //     }else{
    //         Ewin.alert({ message: "读取纸质票据号码和系统取出票据号码不一致，请联系系统维护人员"}).on(function (e) {});
    //         return;
    //     }
    // }else if(result[0]=="CODE_SCAN_FAILED"){
    //         Ewin.alert({ message: "扫描发票条码出错，错误信息：" + result[1]}).on(function (e) {});
    //         printremoveloading();
    // }
    else if(result[0]=="CODE_PRINTBILL_SUCCESS"){
        second = 180;
        setPrintStatus();
    }else if(result[0]=="CODE_PRINTBILL_FAILED"){
        Ewin.alert({ message: "打印出错，错误信息：" + result[1]}).on(function (e) {});
        printremoveloading();
    }
}

//页面流程控制
function popcard() {
    socket.send("POPCARD");
}

function search(){
    //
    // var base64 = new Base64();
    // var chargeListStr=base64.encode(prpad("收费项目名称",10) + "　　" )+
    //     base64.encode(prpad("计量单位",4)+ "　　")  +
    //     base64.encode(prpad("收费标准",10)+ "　　")  +
    //     base64.encode(prpad("数量",4) + "　　") +
    //     base64.encode(prpad("金额",5)) + "$";
    //
    // var clen = 2;
    //
    // for (var i = 0;i<2;i++){
    //     var itemStr = prpad("治疗费",10) + "　　" +
    //         prpad("元",4)+ "　　" +
    //         prpad(getFormattedAmt("100"),10)+ "　　" +
    //         prpad("1",4) + "　　" +
    //         prpad(getFormattedAmt("100"),5);
    //
    //     chargeListStr += base64.encode(itemStr) + "$";
    // }
    //
    // chargeListStr = (chargeListStr.substring(0,chargeListStr.length-1));
    //
    // var sendStr = "PRINTBILL#" +
    //     base64.encode("0000094015") + "#" +
    //     base64.encode("四川省医疗卫生单位门诊结算票据（电子）") + "#" +
    //     base64.encode("51060119") + "#" +
    //     base64.encode("0000012197") + "#" +
    //     base64.encode("王招财") + "#" +
    //     base64.encode("3c23a3") + "#" +
    //     base64.encode("2019年3月1日") + "#" +
    //     base64.encode("业务流水号：" + "20190117000002") + "#" +
    //     base64.encode("业务标识：" + "住院") + "#" +
    //     base64.encode("业务发生时间：" + " 2019年3月1日") + "#" +
    //     chargeListStr + "#" +
    //     base64.encode("总金额：" + "200.00") + "#" +
    //     base64.encode("四川省肿瘤医院") + "#" +
    //     base64.encode("收款人");
    //
    // alert(sendStr);

    second = 180;
    chargetime = $("#chargetime").val();
    if (chargetime==null||chargetime==""||chargetime=="undefined")
    {
        Ewin.alert({ message: "请输入业务日期"}).on(function (e) {});
        return;
    }
    var patientid =window.localStorage.getItem("patientid");
    var cardno =window.localStorage.getItem("cardno");
    // var patientid ="999999999";
    // var cardno="0123456789";
    loading();
    $.ajax({ url: "./dzpjinterf/getbilllist",
        async: true,
        data:{chargetime:chargetime,patientid:patientid,cardno:cardno,pageno:1,pagesize:pageSize},
        type:"GET",
        context: document.body,
        success: function(responseTxt,statusTxt,xhr){
            if(statusTxt=="success") {
                //showbilllist(JSON.parse(responseTxt));
                showbilllist(responseTxt);
                removeloading();
            }else
            {
                alert("Error: "+xhr.status+": "+xhr.statusText);
                removeloading();
            }
        }
    });
}

function showbilllist(json) {
    var status = json.status;
    if (status!="S_OK"){
        Ewin.alert({ message: json.message}).on(function (e) {});
        return;
    }
    $("#t_tbody").empty();
    var data = json.data;
    var total = data.total;
    pageCount = Math.ceil(parseInt(total)/pageSize);
    pageNo = parseInt(data.pageNo);

    $("#pagetitle").html("第" + pageNo + "页/共" + pageCount + "页");
    var billlist = data.billList;
    for (var i=0;i<billlist.length;i++){
        var ivcDateTime = getFormattedDate(billlist[i].ivcDateTime);
        var totalAmt = getFormattedAmt(billlist[i].totalAmt);
        var listHtml =  '<tr class="tbody_font">' +
                            '<td>' + billlist[i].busNo + '</td>' +
                            '<td>' + billlist[i].billName + '</td>' +
                            '<td>' + ivcDateTime + '</td>' +
                            '<td>' + totalAmt + '</td>' +
                            '<td>' + billlist[i].remark + '</td>' +
                            '<td>' +
                                '<button class="btn btn-block btn-lg" type="button" id="prnBtn-' + billlist[i].random + '" onclick=Getpaperbillno("'+ billlist[i].billName +'","'+ billlist[i].billBatchCode +'","'+ billlist[i].billNo +'","'+ billlist[i].random +'","'+ billlist[i].ivcDateTime.substring(0,8) +'","'+ billlist[i].busDate.substring(0,8) +'","'+ totalAmt +'")>' +
                                    '<span class="glyphicon glyphicon-print"></span> 打印票据' +
                                '</button>' +
                            '</td>' +
                        '</tr>';

        $("#t_tbody").append(listHtml);
    }

}

function Getpaperbillno(billName,billBatchCode,billNo,random,ivcDateTime,busDate,totalAmt){
    //保存参数
    pbillName = billName;
    pbillBatchCode = billBatchCode;
    pbillNo = billNo;
    prandom = random;
    pivcDateTime = ivcDateTime;
    pbusDate = busDate;
    ptotalAmt = totalAmt;
    printloading();
    $.ajax({ url: "./dzpjinterf/getpaperbillno",
        async: true,
        data:{pBillBatchCode:"4001"},
        type:"GET",
        context: document.body,
        success: function(responseTxt,statusTxt,xhr){
            if(statusTxt=="success") {
                getCenPaperBillNo(responseTxt);
            }else
            {
                alert("Error: "+xhr.status+": "+xhr.statusText);
                printremoveloading();
            }
        }
    });
}

function getCenPaperBillNo(json){
    //var json = JSON.parse(jsonStr);
    if (json.status == "S_OK"){
        paperBillno = json.data.pBillNo;
        //socket.send("SCAN");
        getbillinfo();
    }else{
        Ewin.alert({ message: json.message}).on(function (e) {});
        printremoveloading();
        return;
    }
}


function getbillinfo() {
    $.ajax({ url: "./dzpjinterf/getbillinfo",
        async: true,
        data:{billbatchcode:pbillBatchCode,billno:pbillNo,random:prandom},
        type:"GET",
        context: document.body,
        success: function(responseTxt,statusTxt,xhr){
            if(statusTxt=="success") {
                printBill(responseTxt,pbillName,pbillBatchCode,pbillNo,prandom,pivcDateTime,pbusDate,ptotalAmt);
            }else
            {
                alert("Error: "+xhr.status+": "+xhr.statusText);
                printremoveloading();
            }
        }
    });
}

function printBill(json,billName,billBatchCode,billNo,random,ivcDateTime,busDate,totalAmt) {
    //var json = JSON.parse(jsonStr);
    var pattern = /(\d{4})(\d{2})(\d{2})/;
    var payCompany = "四川省肿瘤医院";
    var payer = json.data.payer;
    var busNo=json.data.busNo;
    var busType;

    if ("01"==json.data.busType){
        busType = "住院";
    }else if("02"==json.data.busType){
        busType = "门诊";
    }else if("03"==json.data.busType){
        busType = "急诊";
    }else if("04"==json.data.busType){
        busType = "体检中心";
    }else if("05"==json.data.busType){
        busType = "门特";
    }else if("06"==json.data.busType){
        busType = "挂号";
    }else{
        busType = json.data.busType;
    }

    var payee = json.data.payee;
    var chargeList = json.data.chargeDetail;
    var ivcDatestr = ivcDateTime.replace(pattern, '$1年 $2月 $3日');
    var busDatestr = busDate.replace(pattern, '$1年 $2月 $3日');


    var base64 = new Base64();

    var titleStr = prpad("收费项目名称",10) + "　　" +
        prpad("计量单位",4)+ "　　" +
        prpad("收费标准",10)+ "　　" +
        prpad("数量",4) + "　　" +
        prpad("金额",10);

    var chargeListStr=base64.encode(titleStr) + "$";

    var clen = (chargeList.length > 10?10:chargeList.length);

    for (var i = 0;i<clen;i++){
        var itemStr = prpad(chargeList[i].chargeName,10) + "　　" +
            prpad(chargeList[i].unit,4)+ "　　" +
            prpad(getFormattedAmt(chargeList[i].std).toString(),10)+ "　　" +
            prpad(chargeList[i].number,4) + "　　" +
            prpad(getFormattedAmt(chargeList[i].amt).toString(),10);

        //alert("itemStr=" + itemStr);
        chargeListStr += base64.encode(itemStr) + "$";
    }

    chargeListStr = (chargeListStr.substring(0,chargeListStr.length-1));
    //alert("chargeListStr=" + chargeListStr);


    var sendStr = "PRINTBILL#" +
        base64.encode(paperBillno) + "#" +
        base64.encode(billName) + "#" +
        base64.encode(billBatchCode) + "#" +
        base64.encode(billNo) + "#" +
        base64.encode(payer) + "#" +
        base64.encode(random) + "#" +
        base64.encode(ivcDatestr) + "#" +
        base64.encode("业务流水号：" + busNo) + "#" +
        base64.encode("业务标识：" + busType) + "#" +
        base64.encode("业务发生时间：" + busDatestr) + "#" +
        chargeListStr + "#" +
        base64.encode("总金额：" + totalAmt) + "#" +
        base64.encode(payCompany) + "#" +
        base64.encode(payee);

    socket.send(sendStr);
}

function setPrintStatus() {
    $.ajax({ url: "./dzpjinterf/turnpaper",
        async: true,
        data:{billbatchcode:pbillBatchCode,billno:pbillNo,pbillbatchcode:"4001",pbillno:paperBillno},
        type:"GET",
        context: document.body,
        success: function(responseTxt,statusTxt,xhr){
            if(statusTxt=="success") {
                $("#prnBtn-" + prandom).attr('disabled',true);
                writePrintLog();
                printremoveloading();
                setTicketInfo(pbillNo,paperBillno,pbillBatchCode);
            }else
            {
                alert("Error: "+xhr.status+": "+xhr.statusText);
                printremoveloading();
            }
        }
    });

}

function setTicketInfo(ebillno,pbillno,pbillbatchcode){
    $.ajax({ url: "./hisinterf/setticketinfo",
        async: true,
        data:{ebillno:ebillno,pbillno:pbillno,pbillbatchcode:pbillbatchcode},
        type:"GET",
        context: document.body,
        success: function(responseTxt,statusTxt,xhr){

        }
    });
}

function writePrintLog(){
    var cardno = window.localStorage.getItem("cardno");
    var patientname = window.localStorage.getItem("patientname");
    var socialno = window.localStorage.getItem("socialno");
    // var cardno = "00121021212";
    // var patientname = "王招财";
    // var socialno = "622621197904110012";
    // pbillNo = "1234567890";
    // ptotalAmt = "200.00"

    // alert(cardno);
    // alert(patientname);
    // alert(socialno);
    // alert(pbillNo);
    // alert(ptotalAmt);

    $.ajax({ url: "./printlog/writeprintlog",
        async: true,
        data:{cardno:cardno,patientname:patientname,socialno:socialno,billno:pbillNo,billamount:ptotalAmt,remark:""},
        type:"GET",
        context: document.body,
        success: function(responseTxt,statusTxt,xhr){
            if(statusTxt=="success") {
                var json = JSON.parse(responseTxt);
                alert(json.message);
            }else
            {
                alert("Error: "+xhr.status+": "+xhr.statusText);
            }
        }
    });
}


function pretpage(){
    if(pageCount==0||pageNo==1){return;}
    if(pageNo>1){
        pageNo--;
        var patientid =window.localStorage.getItem("patientid");
        var cardno =window.localStorage.getItem("cardno");
        loading();
        $.ajax({ url: "./dzpjinterf/getbilllist",
            async: true,
            data:{chargetime:chargetime,patientid:patientid,cardno:cardno,pageno:pageNo,pagesize:pageSize},
            type:"GET",
            context: document.body,
            success: function(responseTxt,statusTxt,xhr){
                if(statusTxt=="success") {
                    //showbilllist(JSON.parse(responseTxt));
                    showbilllist(responseTxt);
                    removeloading();
                }else
                {
                    alert("Error: "+xhr.status+": "+xhr.statusText);
                }
            }
        });
    }

}

function nextpage(){
    if(pageCount==0||pageNo==pageCount){return;}
    if(pageNo<pageCount){
        pageNo++;
        var patientid =window.localStorage.getItem("patientid");
        var cardno =window.localStorage.getItem("cardno");
        loading();
        $.ajax({ url: "./dzpjinterf/getbilllist",
            async: true,
            data:{chargetime:chargetime,patientid:patientid,cardno:cardno,pageno:pageNo,pagesize:pageSize},
            type:"GET",
            context: document.body,
            success: function(responseTxt,statusTxt,xhr){
                if(statusTxt=="success") {
                    //showbilllist(JSON.parse(responseTxt));
                    showbilllist(responseTxt);
                    removeloading();
                }else
                {
                    alert("Error: "+xhr.status+": "+xhr.statusText);
                }
            }
        });
    }
}


function getFormattedDate(dateString) {
    var subDataString = dateString.substring(0,14);
    var pattern = /(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})/;
    var formatedDate = subDataString.replace(pattern, '$1-$2-$3 $4:$5:$6');
    return formatedDate;
}

function getFormattedAmt(value){
    var value=Math.round(parseFloat(value)*100)/100;
    var xsd=value.toString().split(".");
    if(xsd.length==1){
        value=value.toString()+".00";
        return value;
    }else if(xsd.length>1){
        if(xsd[1].length<2){
            value=value.toString()+"0";
        }
        return value;
    }
}

function prpad(str,len) {
    var retStr = "";
    var bytesCount = 0;
    for (var i = 0; i < str.length; i++)
    {
        var c = str.charAt(i);
        if (/^[\u0000-\u00ff]$/.test(c)) //匹配双字节
        {
            bytesCount += 1;
        }
        else
        {
            bytesCount += 2;
        }
        if(bytesCount<=len*2){
            retStr += c;
        }
    }

    if(bytesCount<len*2){
        for(var i=0;i<(len-bytesCount/2);i++){
            retStr += "　";
        }
    }
    return retStr;
}