/**
 * 日期格式化
 * @param time
 * @returns {*}
 */
function formatDate(time) {
    if (time == '' || time == null || time == undefined) return '';
    var datetime = new Date();
    datetime.setTime(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    return year + "年" + month + "月" + date + "日 " + hour + ":" + minute + ":" + second;
}

function formatState(state) {
    if(state == 0){
        return "等待付款";
    }else if(state == 1){
        return "已支付";
    }else if(state == 2){
        return "支付失败";
    }else{
        return "取消";
    }
}

function formatAmount(amount){
    if (amount == '' || amount == null || amount == undefined)
        return   "￥" + 0.00 ;
    else
       return   "￥" + Math.round(amount/100) ;
}

function formatPay(pay){
    if(pay == 0)
        return "全额支付";
    else
        return "订金支付";
}

function formatPayType(pay){
    if(pay == 0)
        return "全额预定";
    else
        return "订金预定";
}

function formatActivityState(state){
    if(state == 1)
        return "进行中";
    else
        return "已结束";
}