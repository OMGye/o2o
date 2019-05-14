    function getPar(par){
                //获取当前URL
                var local_url = document.location.href; 
                //获取要取得的get参数位置
                var get = local_url.indexOf(par +"=");
                if(get == -1){
                    return false;   
                }   
                //截取字符串
                var get_par = local_url.slice(par.length + get + 1);    
                //判断截取后的字符串是否还有其他get参数
                var nextPar = get_par.indexOf("&");
                if(nextPar != -1){
                    get_par = get_par.slice(0, nextPar);
                }
                return get_par;
    }

    function changeTime(sj)
   {
       var now = new Date(sj);
       var   year=now.getFullYear();    
         var   month=now.getMonth()+1;    
         var   date=now.getDate();    
         var   hour=now.getHours();    
         var   minute=now.getMinutes();    
         var   second=now.getSeconds();    
         return   year+"-"+month+"-"+date+"   "+hour+":"+minute+":"+second;    
        
   }

   function logout() {
       // body...
       $.ajax({
            url: '/o2o/admin/logout.do',
            method: 'post',
            success: function(data) {
                alert("注销成功");
                window.location.href="login.html";
            }
        });
   }