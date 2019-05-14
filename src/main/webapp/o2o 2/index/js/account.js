function getMyaccount(pn) {
	if(getCookie("userType")==0){
		var info="customer";
	}else{
		var info="engineer";
	}
	$.ajax({
		url: '/o2o/'+info+'/billInfo/list.do?pageNum='+pn+'&pageSize=20',
		method: 'get',
		success: function(data) {
			if(!data.data.isFirstPage){
					$("#pre").show();
					$("#pre").click(function(argument) {
						// body...
						window.location.href="Myorder.html?pageNum="+(parseInt(pn)-1);
					})	
				}
				if(!data.data.isLastPage){
					$("#lp").show();
					$("#lp").click(function(argument) {
						// body...
						window.location.href="Myorder.html?pageNum="+(parseInt(pn)-1);
					})	
				}
			if(data.status==0){
				for (var i = 0; i <= data.data.list.length; i++) {
					var v = data.data.list[i];
					$("#AccountList").append(
						'<tr><td>'+v.billId+'</td><td>'+v.billDec+'</td><td>'+v.billMoney+'</td><td>'+changeTime(v.createTime)+'</td></tr>'
						);
				}
			}else{
				alert("系统错误");
				window.location.href="login.html";
			}
		}
	});
}

function getMyApply(pn) {
	if(getCookie("userType")==0){
		var info="customer";
	}else{
		var info="engineer";
	}
	$.ajax({
		url: '/o2o/'+info+'/drawCashInfo/list.do?pageNum='+pn+'&pageSize=20',
		method: 'get',
		success: function(data) {
			if(data.data.hasPreviousPage){
					$("#pre").show();
					$("#pre").click(function(argument) {
						// body...
						window.location.href="Myorder.html?pageNum="+(parseInt(pn)-1);
					})	
				}
				if(data.data.hasNextPage){
					$("#lp").show();
					$("#lp").click(function(argument) {
						// body...
						window.location.href="Myorder.html?pageNum="+(parseInt(pn)-1);
					})	
				}
			if(data.status==0){
				for (var i = data.data.list.length - 1; i >= 0; i--) {
					var v = data.data.list[i];
					if(v.state==0){
						v.state="待审核";
					}else{
						v.state="提现成功";
					}
					$("#AccountList").append(
						'<tr><td>'+v.drawCashId+'</td><td>'+v.drawCashMoney+'</td><td>'+v.state+'</td><td>'+changeTime(v.createTime)+'</td></tr>'
						);
				}
			}else{
				alert("系统错误");
				window.location.href="login.html";
			}
		}
	});
}

$("#ApplyTX").click(function(){
	if(getCookie("userType")==0){
		var info="customer";
	}else{
		var info="engineer";
	}
	var fd= new FormData();
	fd.append("drawAccount",$("#drawAccount").val());
	fd.append("password",$("#password").val());
	fd.append("drawDec",$("#drawDec").val());
	$.ajax({
		url: '/o2o/'+info+'/drawCashInfo/add.do',
		method: 'post',
		data: fd,
		processData:false,
		contentType:false,
		success: function(data) {
			if(data.status==0){
				alert("申请提交成功");
				window.location.href="tixian.html"
			}else{
				alert(data.msg);
			}
		}
	});	
});

$("#Chongzhi").click(function(){
	if(getCookie("userType")==0){
		var info="customer";
	}else{
		var info="engineer";
	}
	var fd= new FormData();
	fd.append("price",$("#price").val());
	$.ajax({
		url: '/o2o/'+info+'/orderInfo/paybalance.do',
		method: 'post',
		data: fd,
		dataType:"html",
		async:false,
		processData:false,
		contentType:false,
		success: function(data) {
			$('#payBody').html(data);
			$('#cz').modal('hide');
			$('#payModal').modal('show');
		}
	});	
});

$("#propost").click(function(){
	if(getCookie("userType")==0){
		var info="customer";
	}else{
		var info="engineer";
	}
	var fd= new FormData();
	fd.append("proposeContent",$("#proposeContent").val());
	$.ajax({
		url: '/o2o/'+info+'/proposeInfo/add.do',
		method: 'post',
		data: fd,
		processData:false,
		contentType:false,
		success: function(data) {
			if(data.status==0){
				alert("操作成功");
			}else{
				alert("系统错误");
			}
		}
	});	
});