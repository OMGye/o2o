var p = new Array();
var Info;

$("#Usertype").on("input propertychange",function(){
	// console.log($(this).attr('id'));
	if($("#Usertype").val()==0){
		$("#engineerFile").hide();
		$("#tishis").hide();
	}else{
		$("#engineerFile").show();
		$("#tishis").show();
	}
});

$("#register").click(function(){
	p[0] = new Array();
	
	//判断是工程师还是用户
	if($("#Usertype").val()==0){
		p[0]['param']="customerName";
		info="customer/customerInfo"
	}else{
		p[0]['param']="engineerName";
		info="engineer/engineerInfo"
	}

	//获得所有参数
	p[0]['name']="用户名";
	p[0]['value']=$("#username").val();

	console.log(p[0]['value']);

	p[1] = new Array();
	p[1]['name']="邮箱";
	p[1]['param']="email";
	p[1]['value']=$("#email").val();

	p[2] = new Array();
	p[2]['name']="密码";
	p[2]['param']="password";
	p[2]['value']=$("#pass").val();

	p[3] = new Array();
	p[3]['name']="手机号码";
	p[3]['param']="phone";
	p[3]['value']=$("#phone").val();

	p[4] = new Array();
	p[4]['name']="确认密码";
	p[4]['param']="repass";
	p[4]['value']=$("#repass").val();

	p[5] = new Array();
	p[5]['name']="身份证号码";
	p[5]['param']="personCode";
	p[5]['value']=$("#personCode").val();
	//确认是否有空
	for (var i = 0; i <= 4; i++) {
		if(p[i]['value']==""){
			alert(p[i]['name']+"不能为空");
			return;
		}
	}	
	//确认密码是否一致
	if(p[4]['value']!=p[2]['value']){
		alert("两次输入密码不一致");
		return;
	}
	//确认手机是否是数字
	if(isNaN(p[3]['value'])){
  		alert("手机号格式错误");
		return;
	}
	//验证身份证号码
	var regIdNo = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
	console.log(p[5]['value']);
	console.log(regIdNo.test(p[5]['value']));
	if(!regIdNo.test(p[5]['value'])){  
	    alert('身份证号填写有误');  
	    return false;  
	}  
	//验证手机号码

	// if(checkMobile(p[3]['value'])){
	// 	alert("手机号格式错误");
	// 	return;
	// }
	//确认手机和邮箱单一
	for (var i = 0; i <= 1; i++) {
		console.log(checkParam(p[i]['param'],p[i]['value']));
		if(!checkParam(p[i]['param'],p[i]['value'])){
			alert(p[i]["name"]+"已经被使用");
			return;
		}
	}
	//身份证，手机
	if(!checkParam(p[5]['param'],p[5]['value'])){
			alert(p[5]["name"]+"已经被使用");
			return;
	}
	if(!checkParam(p[3]['param'],p[3]['value'])){
			alert(p[3]["name"]+"已经被使用");
			return;
	}
	doregister();
});

function checkMobile(str) {
	var re = /^1\d{10}$/
	if (re.test(str)) {		
		return false;
	} else {
		return true;
	}
}

function doregister(argument) {
	// body...
	var formData = new FormData(); 
	formData.append(p[0]['param'],p[0]['value']);
	formData.append("email",p[1]['value']);
	formData.append("password",p[2]['value']);
	formData.append("phone",p[3]['value']);
	formData.append("personCode",p[5]['value']);
	if(p[0]['param']=="engineerName"){
    	formData.append("upload_file",$('#engineerFile')[0].files[0]);
	}
	$.ajax({
		url: "/o2o/"+info+"/register.do",
		type: "POST",
		data:formData,
		dataType : "json",
		processData: false,  // 告诉jQuery不要去处理发送的数据
		contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
		success: function(response,status,xhr){
			if(response.status==0){
				if(response.msg){
					alert(response.msg);
				}else{
					alert(response.data);
				}
				window.location.href="login.html";
			}else{
				alert(response.msg);
			}
		} 
	});
}

function checkParam(t,v) {
	// body...
	var value;
	var formData = new FormData(); 
	console.log(t);
	if(t=="customerName"){
		formData.append("customerName",v);
	}

	if(t=="engineerName"){
		formData.append("engineerName",v);
	}

	if(t=="email"){
		formData.append("email",v);
	}
	if(t=="phone"){
		formData.append("phone",v);
	}
	if(t=="personCode"){
		formData.append("personCode",v);
	}
	$.ajax({
		url: "/o2o/"+info+"/comfirm.do",
		type: "POST",
		data:formData,
		dataType : "json",
		async:false,
		processData: false,  // 告诉jQuery不要去处理发送的数据
		contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
		success: function(response,status,xhr){
			value=response;
		} 
	});
	if(value.status==0){
		return true;
	}else{
		return false;
	}
}