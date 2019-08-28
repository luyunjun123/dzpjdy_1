/**
 * Created by 邢直 on 2019/1/12.
 */
var socket;
var pageSize = 8;
var pageNo = 0;
var pageCount=0;
var chargetime;
var second = 120;//倒计时
var decode =window.localStorage.getItem("decode");
var EwmResponseTxt =window.localStorage.getItem("responseTxt");
//读卡器his获取患者唯一id
var patientid =window.localStorage.getItem("patientid");
var cardno_3 =window.localStorage.getItem("cardno");
var patientname =window.localStorage.getItem("patientname");
/*
* pBillBatchCode  :纸质票据代码
*   pbillNo   新纸质票据号
* */
var pbillName,pbillBatchCode,pbillNo,prandom,pivcDateTime,pbusDate,ptotalAmt,pBusFlag;
var paperBillno;
var Token;

$(function(){
    $("#chargetime").flatpickr({fwidth:"500"});
    Connect();
    console.debug(chargetime+"时间");
    //周期性调用setInterval
    var timer=setInterval (showTime, 1000);
    var vdate= new Date();
    var vmonth =  ("0" + (vdate.getMonth() + 1)).slice(-2);
    var weekday=["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];
    var myddy=("0" + vdate.getDate()).slice(-2);
    var day = vdate.getDay();
    var week=weekday[day];
    var today = vdate.getFullYear() + "-" + (vmonth) + "-" + (myddy);
    //为时间设值
    $("#chargetime").val(today);
    search();
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
            returnmain();
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
    console.debug("读卡器异常")
}
function sClose(e){
    //alert("connect closed:" + e.code);
}
function Close(){
    socket.close();
    window.localStorage.clear();
    window.location.replace("main.html");
}
function returnmain(){
    window.localStorage.clear();
    window.location.replace("main.html");
}

function sMessage(msg){
    console.log("msg",msg);
    //alert(msg.data);
    var result = msg.data.split("#");
    console.log("信息result:",result[0]);
    /*没有信息   CODE_READCARD_SUCCESS 退卡*/
    if (result[0]=="CODE_POPCARD_SUCCESS"){
        document.getElementById("ddsound").play();
        loading();
        setTimeout(function(){removeloading();Close();},6000);
        console.log("退卡吗")
    }else if(result[0]=="CODE_POPCARD_FAILED"){
        Ewin.alert({ message: "退卡操作出错，错误信息：" + result[1]}).on(function (e) {});
        console.log("失败进入")
    }
    else if(result[0]=="CODE_PRINTBILL_SUCCESS"){
        second = 120;
        setPrintStatus();
        console.log("打印机成功");
    }else if(result[0]=="CODE_PRINTBILL_FAILED"){
        Ewin.alert({ message: "打印出错，错误信息：" + result[1]}).on(function (e) {});
        printremoveloading();
        console.log("错误")
    }

}

//页面流程控制
function popcard() {
    socket.send("POPCARD");
    returnmain();
}


/*查询框查询*/
function search(){
    second = 120;
    chargetime = $("#chargetime").val();
    console.log("chargetime",chargetime);
    if (chargetime==null||chargetime==""||chargetime=="undefined")
    {
        Ewin.alert({ message: "请输入业务日期"}).on(function (e) {});
        return;
    }
    console.log("插卡查询patientid号：",patientid)
    console.log("插卡查询cardno:",cardno_3)
    //获取票据打印列表
        loading();
        $.ajax({
            url: "./threeHospitals/etk_getEBillUnPrintList",
            async: true,
            // chargetime:chargetime,patientid:patientid,cardno:cardno,pageno:pageNo,pagesize:pageSize
            data: {chargetime: chargetime, patientid: patientid, cardno: cardno_3, pageno: 1, pagesize: pageSize},
            type: "GET",
            context: document.body,
            success: function (responseTxt, statusTxt, xhr) {
                console.log("插卡查询方法",patientid);

                if (statusTxt == "success") {
                    showbilllist(responseTxt);
                    removeloading();
                } else {
                    console.debug("未能查到数据")
                    alert("Error: " + xhr.status + ": " + xhr.statusText);
                    removeloading();
                }
            }

        });
}
function search3(){
    second = 120;
    chargetime = $("#chargetime").val();
    console.log("二维码chargetime:",chargetime);
    if (chargetime==null||chargetime==""||chargetime=="undefined")
    {
        Ewin.alert({ message: "请输入业务日期"}).on(function (e) {});
        return;
    }
            $.ajax({
                //请求地址(博思发出请求查询,获取打印列表)
                url: "./dzpjinterf/getEwmTimeQuery",
                //默认设置为true，所有请求均为异步请求
                async: true,
                //发送到服务器的数据，要求为Object或String类型的参数
                //  本地存储二维码格式问题
                data:{decode:decode,chargetime: chargetime,pageno:pageNo,pagesize:pageSize,Token:Token},
                type:"GET",
                //context参数作用 将回调里的this替换为context里对应的值
                //    context: document.body,
                success: function(responseTxt,statusTxt,xhr){
                    console.log("二维码pjdy动态查询",responseTxt)
                    if(statusTxt=="success") {
                        try {
                            showbilllist(responseTxt)
                        } catch (e) {
                            console.log("二维码错误");
                            Ewin.alert({ message: e.message}).on(function (e) {});
                            // socket.send("POPCARD");
                            removeloading();
                            window.localStorage.removeItem("decode");
                            window.localStorage.removeItem("responseTxt");
                            window.localStorage.clear();
                            returnmain();
                        }
                    }else
                    {
                        console.log("异常")
                        // socket.send("POPCARD");
                        alert("Error: "+xhr.status+": "+xhr.statusText+"1231231");
                        window.localStorage.removeItem("decode");
                        window.localStorage.removeItem("responseTxt");
                        window.localStorage.clear();
                        returnmain();
                    }
                }
            });
}
//二维码
function search2(){

    second = 120;
    chargetime = $("#chargetime").val();
    console.log("二维码chargetime:",chargetime);
    if (chargetime==null||chargetime==""||chargetime=="undefined")
    {
        Ewin.alert({ message: "请输入业务日期"}).on(function (e) {});
        return;
    }
    /*patientid his查出来的唯一id*/
    var patientid =window.localStorage.getItem("patientid");
    console.log("二维码查询",patientid)
  //  卡号
    var cardno =window.localStorage.getItem("cardno");
    console.log("二维码查询",cardno)
    // 获取当前日期  用于二维码判断是否是查询框查询
    var currentTime= new Date();
    var currenVmonth =  ("0" + (currentTime.getMonth() + 1)).slice(-2);
    var currenMyddy=("0" + currentTime.getDate()).slice(-2);
    var currenToday = currentTime.getFullYear() + "-" + (currenVmonth) + "-" + (currenMyddy);
    console.log("二维码时间",currenToday);
    // 判断是否是二维码查询
    if(patientid==null&&cardno==null){
        //判断是否是查询框调整时间查询
        if(chargetime!=currenToday){
            $.ajax({
                //请求地址(博思发出请求查询,获取打印列表)
                url: "./dzpjinterf/getEwmTimeQuery",
                //默认设置为true，所有请求均为异步请求
                async: true,
                //发送到服务器的数据，要求为Object或String类型的参数
                //  本地存储二维码格式问题
                data:{decode:decode,chargetime: chargetime,pageno:pageNo,pagesize:pageSize},
                type:"GET",
                //context参数作用 将回调里的this替换为context里对应的值
                //    context: document.body,
                success: function(responseTxt,statusTxt,xhr){
                    console.log("进入二维码查询方法",responseTxt)
                    if(statusTxt=="success") {
                        try {
                            showbilllist(responseTxt)
                        } catch (e) {
                            console.log("二维码错误");
                            Ewin.alert({ message: e.message}).on(function (e) {});
                            // socket.send("POPCARD");
                            removeloading();
                            window.localStorage.removeItem("decode");
                            window.localStorage.removeItem("responseTxt");
                            window.localStorage.clear();
                            returnmain();
                        }
                    }else
                    {
                        console.log("异常")
                       // socket.send("POPCARD");
                        alert("Error: "+xhr.status+": "+xhr.statusText+"1231231");
                        window.localStorage.removeItem("decode");
                        window.localStorage.removeItem("responseTxt");
                        window.localStorage.clear();
                        returnmain();
                    }
                }
            });
        }else {
            //   console.debug(EwmResponseTxt.toString()+"二维码数据")
            console.debug("二维码扫描未变动查询")
            showbilllist(eval("(" + EwmResponseTxt + ")"));
        }
    }
}
//lyj
function showbilllist(json) {
    // console.log("json",json);
    var status = json.status;
    // var status = "S_OK";
    if (status!="S_OK"){
        console.debug(6666666)
        Ewin.alert({ message: json.message}).on(function (e) {});
        window.localStorage.removeItem("decode");
        window.localStorage.removeItem("responseTxt");
        window.localStorage.clear();
        return;
    }
    //从被选元素移除所有内容，包括所有文本和子节点
    // console.debug(json.data)
    $("#t_tbody").empty();
    var data = json.data;
    console.log("data",data)
    var total = data.total;
    pageCount = Math.ceil(parseInt(total)/pageSize);
    pageNo = parseInt(data.pageNo);

    $("#pagetitle").html("第" + pageNo + "页/共" + pageCount + "页");
    var billlist = data.billList;
    for (var i=0;i<billlist.length;i++){
        // var ivcDateTime = getFormattedDate(billlist[i].ivcDateTime);
        // var totalAmt = getFormattedAmt(billlist[i].totalAmt);
        var ivcDateTime = billlist[i].ivcDateTime;
        var totalAmt = billlist[i].totalAmt;
        var listHtml =  '<tr class="tbody_font">' +
            '<td>' + billlist[i].busNo + '</td>' +
            '<td>' + billlist[i].billName + '</td>' +
            '<td>' + ivcDateTime + '</td>' +
            '<td>' + totalAmt + '</td>' +
            '<td>' + billlist[i].remark + '</td>' +
            '<td>' +
            '<button class="btn btn-block btn-lg" type="button" id="prnBtn-' +
            billlist[i].random + '' +
            '" onclick=Getpaperbillno("'+ billlist[i].billName +'",' +
            '"'+ billlist[i].billBatchCode +'",' +
            '"'+ billlist[i].billNo +'",' +
            '"'+ billlist[i].random +'",' +
            '"'+ billlist[i].ivcDateTime.substring(0,8) +'",' +
            '"'+ billlist[i].busDate.substring(0,8) +'",' +
            '"'+ totalAmt +'")>' +
            '<span class="glyphicon glyphicon-print"></span> 打印票据' +
            '</button>' +
            '</td>' +
            '</tr>';

        $("#t_tbody").append(listHtml);
    }

}
/*打印票据*/
// "billName": "电子票据种类名称",
//     "billBatchCode": "电子票据代码",
//     "billNo": "电子票据号码",
//     "random": "电子票校验码",
//     "ivcDateTime": "开票时间",
//     "state": "状态",
//     "isPrtPaper": "是否打印纸质票据",
//     "pBillBatchCode": "纸质票据代码",
//     "pBillNo": "纸质票据号码",
//     "isScarlet": "是否已开红票",
//     "scarletBillBatchCode": "红字电子票据代码",
//     "scarletBillNo": "红字电子票据号码",
//     "scarletRandom": "红字电子票据随机码",
//     "scarletBillQRCode": "红字电子票据二维码图片数据"
function Getpaperbillno(billName,billBatchCode,billNo,random,ivcDateTime,busDate,totalAmt){
    //保存参数
    pbillName = billName;
    pbillBatchCode = billBatchCode;
    pbillNo = billNo;
    prandom = random;
    pivcDateTime = ivcDateTime;
    pbusDate = busDate;
    ptotalAmt = totalAmt;
    //等待信息
    second = 120;
     printloading();
    //获取当前纸质票据可用号码
    $.ajax({ url: "./threeHospitals/etk_getPaperBillNo",
        async: true,
        data:{pBillBatchCode:"001"},
        type:"GET",
        context: document.body,
        success: function(responseTxt,statusTxt,xhr){
            // 返回参数
            //     成功
            //     {
            //         "result": "S0000",
            //         "message":{
            //         "pBillBatchCode": "纸质票据代码",
            //             "pBillNo": "纸质票据号码"
            //     }
            if(statusTxt=="success") {
                getCenPaperBillNo(responseTxt);
            }else
            {
                alert("Error: "+xhr.status+": "+xhr.statusText);
                printremoveloading();
                window.localStorage.removeItem("decode");
                window.localStorage.removeItem("responseTxt");
                window.localStorage.clear();
            }
        }
    });
}

function getCenPaperBillNo(json){
    if (json.status == "S_OK"){
        // pBillNo	获得 纸质票据号
        paperBillno = json.data.pBillNo;
        //socket.send("SCAN");
       getbillinfo();
    }else{
        Ewin.alert({ message: json.message}).on(function (e) {});
        printremoveloading();
        window.localStorage.removeItem("decode");
        window.localStorage.removeItem("responseTxt");
        window.localStorage.clear();
        return;
    }
}


function getbillinfo() {
    // 4.1.15获取电子票据明细接口  （三级菜单）paperBillno  "./dzpjinterf/getbillinfo",
    $.ajax({ url: "./threeHospitals/etk_getBillDetail",
        async: true,
         data:{billname:pbillName,billbatchcode:pbillBatchCode,billno:pbillNo,payer:"",random:prandom,ivcdatetime:pivcDateTime},
        type:"GET",
        context: document.body,
        // 01	住院
        // 02	门诊
        // 03	急诊
        // 04	体检中心
        // 05	门特
        // 06	挂号
        success: function(responseTxt,statusTxt,xhr){
        //console.log("responseTxt",responseTxt);
            //
            if( statusTxt=="success") {
                // var json = responseTxt;
                // if ("01"==json.data.busType){
                //     pBusFlag = 3;
                // }else if("02"==json.data.busType){
                //     pBusFlag = 1;
                // }else if("03"==json.data.busType){
                //     pBusFlag = 1;
                // }else if("04"==json.data.busType){
                //     pBusFlag = 1;
                // }else if("05"==json.data.busType){
                //     pBusFlag = 1;
                // }else if("06"==json.data.busType){
                //     pBusFlag = 2
                // }else{
                //     pBusFlag = 1;
                // }
                // printBill(responseTxt,pbillName,pbillBatchCode,pbillNo,prandom,pivcDateTime,pbusDate,ptotalAmt);
                printBillPdf(responseTxt);
            }else
            {
                alert("Error: "+xhr.status+": "+xhr.statusText);
                //TODO  关闭
                printremoveloading();
                window.localStorage.removeItem("decode");
                window.localStorage.removeItem("responseTxt");
                window.localStorage.clear();
            }
        }
    });
}
// 调用打印机
function printBillPdf(json){
    setPrintStatus();
    //测试数据
    // pdffile="E:\\lyj\\pdf\\fp_template.pdf";
    // paperBillno="000323398";
    // console.debug("asdasda"+pdffile)
    // console.debug("11111111111"+paperBillno)
   // console.log("json",json);
   //  var base64 = new Base64();
   //  var pdffile = json.pdffile;
   //  console.log(paperBillno+"paperBillno");
   //  var base64 = new Base64();
   //  // 路劲加编码 PRINTBILL打印机命令
   //  var sendStr = "PRINTBILL#" +
   //      base64.encode(paperBillno) + "#" +
   //      base64.encode(pdffile);
   //  //调用打印机
   //  socket.send(sendStr);
    //todo 测试回传

    //document.getElementById("dyzqsh").play();
}
/*肿瘤医院*/
function printBill(json,billName,billBatchCode,billNo,random,ivcDateTime,busDate,totalAmt) {
    //var json = JSON.parse(jsonStr);
    var pattern = /(\d{4})(\d{2})(\d{2})/;
    var payCompany = "四川省肿瘤医院";
    var payer = json.data.payer;
    var busNo=json.data.busNo;
    var busType;
    //todo  pBusFlag?????????
    if ("01"==json.data.busType){
        busType = "住院";
        pBusFlag = 3;
    }else if("02"==json.data.busType){
        busType = "门诊";
        pBusFlag = 1;
    }else if("03"==json.data.busType){
        busType = "急诊";
        pBusFlag = 1;
    }else if("04"==json.data.busType){
        busType = "体检中心";
    }else if("05"==json.data.busType){
        busType = "门特";
        pBusFlag = 1;
    }else if("06"==json.data.busType){
        busType = "挂号";
        pBusFlag = 2
    }else{
        busType = json.data.busType;
        pBusFlag = 1;
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
        var unitStr = (chargeList[i].unit==null||chargeList[i].unit=="null"||chargeList[i].unit=="")?"元":chargeList[i].unit;
        var itemStr = prpad(chargeList[i].chargeName,10) + "　　" +
            prpad(unitStr,4)+ "　　" +
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
    // 业务系统根据电子票据信息，
    // 向医疗电子票据管理平台发起电子票据换开纸质票据请求，生成纸质票据
    //打印成功给博士返回数据//
    //纸质票据号
    $.ajax({ url: "./threeHospitals/etk_turnPaper",
        async: true,
        data:{billbatchcode:pbillBatchCode,billno:pbillNo,pbillbatchcode:"4001",pbillno:paperBillno},
        type:"GET",
        context: document.body,
        success: function(responseTxt,statusTxt,xhr){
            if( statusTxt=="success") {
                console.log("博思数据回传")
                //打印成功禁止按钮
                $("#prnBtn-" + prandom).attr('disabled',true);
               //省医院his返回数据 pbillBatchCode pbillNo,
               //  setTicketInfo_1();
                //写入日志
              // writePrintLog();
              //   关闭操作
                printremoveloading();
            }else
            {
                alert("Error: "+xhr.status+": "+xhr.statusText);
                printremoveloading();
            }
        }
    });

}
// 省医院数据返回  pbillNopbillNo,,chargetime
function setTicketInfo_1(){
    console.log("省医院his数据回传")
    if(patientid==null){
        patientid=decode;
    }

    // alert("ebillno=" + ebillno + ";pbillno=" + pbillno + ";pbillbatchcode=" + pbillbatchcode + ";pflag=" + pBusFlag);
    //打印成功告诉his   setpainfobycard
    $.ajax({ url: "./hisinterf/setpainfobycard",
        async: true,
        // data:{billno:pbillNo,pbillno:paperBillno}  ,,pbillNo:pbillNo
        data:{patientid:patientid,billno:pbillNo},
        type:"GET",
        context: document.body,
        success: function(responseTxt,statusTxt,xhr){
            if(responseTxt.status=="S_FALSE") {
                console.log("his数据返回异常")
                // alert(responseTxt.message);
            }else if(responseTxt.status=="S_OK"){
                console.log("his数据返回成功")
            }
        }
    });
}
function setTicketInfo(ebillno,pbillno,pbillbatchcode){
    // alert("ebillno=" + ebillno + ";pbillno=" + pbillno + ";pbillbatchcode=" + pbillbatchcode + ";pflag=" + pBusFlag);
    //打印成功告诉his
    $.ajax({ url: "./hisinterf/setticketinfo",
        async: true,
        //todo  参数
        data:{ebillno:ebillno,pbillno:pbillno,pbillbatchcode:pbillbatchcode,pflag:pBusFlag},
        type:"GET",
        context: document.body,
        success: function(responseTxt,statusTxt,xhr){
            if(responseTxt.status=="S_FALSE") {
                //TODO 成功后未做处理
                // alert(responseTxt.message);
            }
        }
    });
}

function writePrintLog(){
    //本地存储器取值
    var cardno = window.localStorage.getItem("cardno");
    var patientname = window.localStorage.getItem("patientname");
    //二维码的话身份证号码没有
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
    // 写入日志
    $.ajax({ url: "./printlog/writeprintlog",
        async: true,
        data:{cardno:cardno,patientname:patientname,socialno:socialno,billno:pbillNo,billamount:ptotalAmt,remark:""},
        type:"GET",
        context: document.body,
        success: function(responseTxt,statusTxt,xhr){
            if(statusTxt=="success") {
                //var json = JSON.parse(responseTxt);
                //alert(responseTxt.message);
            }else
            {
                alert("Error: "+xhr.status+": "+xhr.statusText);
            }
        }
    });
}

/*上一页*/
function pretpage(){
    if(pageCount==0||pageNo==1){return;}
    if(pageNo>1){
        pageNo--;
        var patientid =window.localStorage.getItem("patientid");
        var cardno =window.localStorage.getItem("cardno");
        console.log("patientid:插卡方法拿到的卡号上一页",patientid)
        console.log("patientid:插卡方法拿到的卡号上一页",cardno)
        //二维码上下页
        if(patientid==null&&cardno==null){
            $.ajax({
                //请求地址(博思发出请求查询,获取打印列表)
                url: "./dzpjinterf/getEwmTimeQuery",
                //默认设置为true，所有请求均为异步请求
                async: true,
                //发送到服务器的数据，要求为Object或String类型的参数
                data:{decode:decode,chargetime: chargetime,pageno:pageNo,pagesize:pageSize},
                type:"GET",
                //context参数作用 将回调里的this替换为context里对应的值
                //    context: document.body,
                success: function(responseTxt,statusTxt,xhr){

                    // responseTxt,statusTxt,xhr
                    // statusTxt=="success"
                    //  测试状态
                    if(statusTxt=="success") {
                        try {
                            console.debug("二维码上一页")
                            showbilllist(responseTxt);
                            removeloading();
                        } catch (e) {
                            console.debug("错误");
                            Ewin.alert({ message: e.message}).on(function (e) {});
                            socket.send("POPCARD");
                            removeloading();
                            window.location.replace("main.html");
                        }
                    }else
                    {
                        socket.send("POPCARD");
                        alert("Error: "+xhr.status+": "+xhr.statusText+"1231231");
                    }
                }
            });
        }else {

            loading();
            //todo
            $.ajax({ url: "./dzpjinterf/getbilllist",
                async: true,
                //chargetime  业务时间  patientid :病人id  cardno：卡号 pageNo:当前页码 pageSize:每页条数
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
}
/*下一页*/
function nextpage(){
    if(pageCount==0||pageNo==pageCount){return;}
    if(pageNo<pageCount){
        pageNo++;
        var patientid =window.localStorage.getItem("patientid");
        var cardno =window.localStorage.getItem("cardno");
        //二维码上下页
        if(patientid==null&&cardno==null){
            loading();
            $.ajax({
                //请求地址(博思发出请求查询,获取打印列表)
                url: "./dzpjinterf/getEwmTimeQuery",
                //默认设置为true，所有请求均为异步请求
                async: true,
                //发送到服务器的数据，要求为Object或String类型的参数
                data:{decode:decode,chargetime: chargetime,pageno:pageNo,pagesize:pageSize},
                type:"GET",
                //context参数作用 将回调里的this替换为context里对应的值
                //    context: document.body,
                success: function(responseTxt,statusTxt,xhr){

                    // responseTxt,statusTxt,xhr
                    // statusTxt=="success"
                    // 测试状态
                    if(statusTxt=="success") {
                        try {
                            console.debug("二维码下一页")
                            showbilllist(responseTxt);
                            removeloading();
                        } catch (e) {
                            console.debug("错误");
                            Ewin.alert({ message: e.message}).on(function (e) {});
                            socket.send("POPCARD");
                            removeloading();
                        }

                        removeloading();
                    }else
                    {
                        socket.send("POPCARD");
                        alert("Error: "+xhr.status+": "+xhr.statusText+"1231231");
                    }
                }
            });
        }else {
            loading();
            $.ajax({ url: "./dzpjinterf/getbilllist",
                async: true,
                //chargetime:chargetime,patientid:patientid,cardno:cardno,pageno:pageNo,pagesize:pageSize
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
    if (str == null) {return prpad("  ",len)}
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