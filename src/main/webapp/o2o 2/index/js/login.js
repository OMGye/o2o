var p = new Array();
var p2 = new Array();

$("#login").click(function(){
	//获得所有参数
	var userType=$("#userType").val();

	p[0] = new Array();
	p[0]['name']="用户名";
	p[0]['value']=$("#userName").val();
	
	p[1] = new Array();
	p[1]['name']="密码";
	p[1]['param']="password";
	p[1]['value']=$("#password").val();

	//确认是否有空
	for (var i = 0; i <= 1; i++) {
		if(p[i]['value']==""){
			alert(p[i]['name']+"不能为空");
			return;
		}
	}	

	if(userType==0){
		p[0]['param']="customerName";
		doCustomerlogin();
	}else{
		p[0]['param']="engineerName";
		doEngineerlogin();
	}
});

function doCustomerlogin() {
	// body...
	var formData = new FormData(); 
	formData.append("customerName",p[0]['value']);
	formData.append("password",p[1]['value']);
	$.ajax({
		url: "/o2o/customer/customerInfo/login.do",
		type: "POST",
		data:formData,
		dataType : "json",
		async:false,
		processData: false,  // 告诉jQuery不要去处理发送的数据
		contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
		success: function(response,status,xhr){
			if(response.status==0){
				setCookie('customerName',response.data.customerName,36000);
				setCookie('customerId',response.data.customerId,36000);
				setCookie('userType',0,36000);
				alert("登陆成功！");
				window.location.href="index.html";
			}else{
				alert(response.msg);
			}
		} 
	});
}

function doEngineerlogin() {
	// body...
	var formData = new FormData(); 
	formData.append("engineerName",p[0]['value']);
	formData.append("password",p[1]['value']);
	$.ajax({
		url: "/o2o/engineer/engineerInfo/login.do",
		type: "POST",
		data:formData,
		dataType : "json",
		async:false,
		processData: false,  // 告诉jQuery不要去处理发送的数据
		contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
		success: function(response,status,xhr){
			if(response.status==0){
				setCookie('engineerName',response.data.engineerName,36000);
				setCookie('engineerId',response.data.engineerId,36000);
				setCookie('userType',1,36000);
				alert("登陆成功！");
				window.location.href="index.html";
			}else{
				alert(response.msg);
			}
		} 
	});
}
