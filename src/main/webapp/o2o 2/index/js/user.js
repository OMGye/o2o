var info;
function getuserinfo() {
	// body...
	if(getCookie("userType")==0){
		var url="/o2o/customer/customerInfo/getcustomerbyid.do";
		info="customer/customerInfo";
	}else{
		var url="/o2o/engineer/engineerInfo/getengineerbyid.do";
		info="engineer/engineerInfo";
	}
	$.ajax({
		url: url,
		type: "GET",
		dataType : "json",
		async:false,
		success: function(response,status,xhr){
			if(response.status==0){
				var data = response.data;
				setCookie("quantity",data.engineerQuantity);
				if(getCookie("userType")==1){
					$("#customerId").val(data.engineerId);
					$("#myBalance").html(data.engineerBalance);
					$("#personCard").val(data.personCode);
					$("#customerName").val(data.engineerName);
					$("#customerPayCount").val(data.engineerPayCount);
					$("#customerQq").val(data.engineerQq);
					$("#email").val(data.email);
					$("#phone").val(data.phone);
					$("#customerCity").val(data.engineerCity);
					$("#customerProv").val(data.engineerProv);
					$("#engineerClassfy").val(data.engineerClassfy);
					$("#engineerRank").val(data.engineerRank+"级工程师");
				}else{
					$("#customerId").val(data.customerId);
					$("#myBalance").html(data.customerBalance);
					$("#personCard").val(data.personCode);
					$("#customerName").val(data.customerName);
					$("#customerPayCount").val(data.customerPayCount);
					$("#customerQq").val(data.customerQq);
					$("#email").val(data.email);
					$("#phone").val(data.phone);
					$("#customerCity").val(data.customerCity);
					$("#customerProv").val(data.customerProv);
					$("#customerAttention").val(data.customerAttention);
				}
			}else{
				alert(response.msg);
				window.location.href="login.html";
			}
		} 
	});
}

$("#changeBtn").click(function(){
			var engineerrankinfo= new Array("customerId","customerName","customerPayCount","customerQq","email","phone","customerCity","customerProv","customerAttention");
			var fd = new FormData();

			for(let index in engineerrankinfo) {  
	       		var val=$("#"+engineerrankinfo[index]).val();  
	       		if(getCookie('userType')==1){
	       			if(engineerrankinfo[index]!="customerAttention"){
	       				var v = engineerrankinfo[index].replace(/customer/g, "engineer");
			       		fd.append(v,val);
	       			}
	       		}else{
		       		fd.append(engineerrankinfo[index],val);
	       		}
    		}
    		$.ajax({
				url: "/o2o/"+info+"/update.do",
				type: "post",
				dataType : "json",
				data:fd,
				async:false,
				processData: false,  // 告诉jQuery不要去处理发送的数据
            
	            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头

				success: function(response,status,xhr){
					if(response.status==0){
						var data = response.data;
						alert(data);
					}else{
						alert(response.msg);
						window.location.href="login.html";
					}
				} 
			});
});

$("#backpassword").click(function(){
			var engineerrankinfo= new Array("userName","email");
			var fd = new FormData();
			if($("#userType").val()==1){
				info="engineer/engineerInfo";
			}else{
				info="customer/customerInfo";
			}

			for(let index in engineerrankinfo) {  
	       		var val=$("#"+engineerrankinfo[index]).val();  
	       		if(engineerrankinfo[index]=="userName"){
		       		if(getCookie("userType")==1){
			       		fd.append("engineerName",val);
		       		}else{
			       		fd.append("customerName",val);
		       		}
	       		}else{
			       	fd.append(engineerrankinfo[index],val);
	       		}
    		}
    		$.ajax({
				url: "/o2o/"+info+"/findpassword.do",
				type: "post",
				dataType : "json",
				data:fd,
				async:false,
				processData: false,  // 告诉jQuery不要去处理发送的数据
            
	            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头

				success: function(response,status,xhr){
					if(response.status==0){
						var data = response.data;
						alert(data);
					}else{
						alert(response.msg);
						window.location.href="login.html";
					}
				} 
			});
});