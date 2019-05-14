// 如果要设置过期时间以秒为单位
function setCookie(c_name,value,expireseconds){
    var exdate=new Date();
    exdate.setTime(exdate.getTime()+expireseconds * 1000);
    document.cookie=c_name+ "=" +escape(value)+
    ((expireseconds==null) ? "" : ";expires="+exdate.toGMTString())
}

function getCookie(c_name){
    if (document.cookie.length>0){
        c_start=document.cookie.indexOf(c_name + "=");
        if (c_start!=-1){
            c_start=c_start + c_name.length+1;
            c_end=document.cookie.indexOf(";",c_start);
            if (c_end==-1){ 
                c_end=document.cookie.length;
            }

            return unescape(document.cookie.substring(c_start,c_end));
        }
     }

    return "";
}

function checkCookie(c_name){
    username=getCookie(c_name);
    if (username!=null && username!=""){
        // 如果cookie值存在，执行下面的操作。
        alert('Welcome again '+username+'!');
    }else{
        username=prompt('Please enter your name:',"");
        if (username!=null && username!=""){
            //如果cookie不存在，执行下面的操作。
            setCookie('username',username,365)
        }   
    }
}

function delCookie(name)
{
var exp = new Date();
exp.setTime(exp.getTime() - 1);
var cval=getCookie(name);
if(cval!=null)
document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}

function logout() {
    // body...
    if(getCookie("userType")==0){
        var info = "customer/customerInfo";
    }else{
        var info = "engineer/engineerInfo";
    }
            $.ajax({
            url: '/o2o/'+info+'/logout.do',
            method: 'post',
            success: function(data) {
                alert("注销成功");
                window.location.href="login.html";
                delCookie("customerName");
                delCookie("customerId");
                delCookie("engineerName");
                delCookie("engineerId");
                delCookie("userType");
            }
        });
    
        
}