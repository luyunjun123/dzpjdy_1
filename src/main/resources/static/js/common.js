function getContextPath() {
    var contextPath = document.location.pathname;
    var index =contextPath.substr(1).indexOf("/");
    contextPath = contextPath.substr(0,index+1);
    delete index;
    return contextPath;
}
var baseurl = getContextPath();

function fillZero(number, digits){
	number = String(number);
	var length = number.length;
	if(number.length<digits){
		for(var i=0;i<digits-length;i++){
			number = "0"+number;
		}
	}
	return number;
}

function writeLog(opername,activeid){
    var keycode = window.localStorage.getItem("keycode");
    var username = window.localStorage.getItem("username");
    var idcardno = window.localStorage.getItem("idcardno");

    $.ajax({
        type: "post",
        url: baseurl+"/sysoperlog/writelog",
        contentType:"application/json",
        data:"{\"keycode\":\""+keycode+"\",\"activeid\":\"\",\"opername\":\""+opername+"\",\"operator\":\""+username+"\",\"opidcardno\":\""+idcardno+"\",\"remark\":\"\"}",
        dataType:"json",
        success: function(data){

        }
    });

}

function loading() {
    $('body').loading({
        loadingWidth: 120,
        title: '',
        name: 'loadmask',
        discription: '',
        direction: 'column',
        type: 'origin',
        originDivWidth: 40,
        originDivHeight: 40,
        originWidth: 6,
        originHeight: 6,
        smallLoading: false,
        loadingMaskBg: 'rgba(0,0,0,0.2)'
    });
}
function removeloading() {
    removeLoading('loadmask');
}

function printloading() {
    $('body').loading({
        loadingWidth:300,
        title:'正在打印，请稍等!',
        name:'ploading',
        discription:'',
        direction:'row',
        type:'origin',
        originBg:'#71EA71',
        originDivWidth:30,
        originDivHeight:30,
        originWidth:4,
        originHeight:4,
        smallLoading:false,
        titleColor:'#388E7A',
        loadingBg:'#312923',
        loadingMaskBg:'rgba(22,22,22,0.2)'
    });
}
function printremoveloading() {
    removeLoading('ploading');
}


