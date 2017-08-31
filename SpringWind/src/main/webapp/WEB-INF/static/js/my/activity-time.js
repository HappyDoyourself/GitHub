// ActionScript Document
$(function(){ 
  var countdown = setInterval(countTime, 1000);
  var endTime = $("#endTime").val();
    var dateTime = new Date();
   function countTime(){
       var dateTime = new Date();
       var nMS= endTime - dateTime.getTime();
        var myD=Math.floor(nMS/(1000*60*60*24)); //天
        var myH=Math.floor(nMS/(1000*60*60)) % 24; //小时
        var myM=Math.floor(nMS/(1000*60)) % 60; //分钟
        var myS=Math.floor(nMS/1000) % 60; //秒
	   if(myD>=0 && myH>=0 && myM>=0 && myS>=0){
           var str = "倒计时" +myD+"天" + myH + "小时" + myM  + "分" +myS  + "秒";

       }else{
           var str = "已结束";
           //$(".reserve").attr("disabled",true);
           $("#yuding").removeAttr("onclick");
           $(".reserve a").attr("style","background:#CCCCCC;");
           $(".reserve a").removeAttr("href");
           $(".reserve a").removeAttr("onclick");
           $("#yuding").attr("style","background:#CCCCCC;");
           clearInterval(countdown);
           // $(".num_right").attr("style","background:#CCCCCC;height:200px; width:400px;").attr("disabled",true);;
	   }
         $(".num_right").html(str);
   }

 })