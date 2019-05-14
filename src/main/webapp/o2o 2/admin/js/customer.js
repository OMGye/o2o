var s;
function getCustomerBystate(pn,s) {
	// body...
	s=s;
	$.ajax({
			// la URL para la petición
			url : '/o2o/admin/customerInfo/list.do?pageNum='+pn+'&pageSize='+20+'&state='+s,
			// especifica si será una petición POST o GET
			type : 'GET',
			// el tipo de información que se espera de respuesta
			dataType : 'json',

			processData: false,  // 告诉jQuery不要去处理发送的数据
            
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头

			// código a ejecutar si la petición es satisfactoria;
			// la respuesta es pasada como argumento a la función
			success : function(v) {
				if(v.status==1){
					alert(v.msg);  
				}else{
					//分页处理
					if(!v.data.hasNextPage){
						$(".next").addClass("disabled");
					}else{
						$(".next").removeClass("disabled");
						$(".nexthref").attr("href","customer.html?pageNum="+(parseInt(pn)+parseInt(1)));
					}
					if(!v.data.hasPreviousPage){
						$(".prev").addClass("disabled");
					}else{
						$(".prev").removeClass("disabled");
						$(".fronthref").attr("href","customer.html?pageNum="+(parseInt(pn)-parseInt(1)));
					}

					for (var i =  0; i < v.data.list.length; i++) {
						var val = v.data.list[i];
						console.log(val);
						if(val.adminCheck==2){
							if(s==10){
							$("#UserList").append(
								'<tr><td>'+val.customerId+'</td><td>'+val.customerName+'</td><td>'+val.email+'</td><td>'+val.phone+'</td><td>'+val.customerBalance+'</td><td>'+changeTime(val.createTime)+'</td><td><a href="Customerchange.html?Id='+val.customerId+'" class="btn btn-default">修改</a><a href="userBill.html?Id='+val.customerId+'&userType=0" class="btn btn-danger">账单</a><a href="userPay.html?Id='+val.customerId+'&userType=0" class="btn btn-success">支付记录</a><a onclick="ban('+val.customerId+',1)" class="btn btn-success">解禁</a></td></tr>'
							);
						}else{
							$("#UserList").append(
								'<tr><td>'+val.customerId+'</td><td>'+val.customerName+'</td><td>'+val.email+'</td><td>'+val.phone+'</td><td>'+val.customerBalance+'</td><td>'+changeTime(val.createTime)+'</td><td><a href="Customerchange.html?Id='+val.customerId+'" class="btn btn-default">修改</a><a href="userBill.html?Id='+val.customerId+'&userType=0" class="btn btn-danger">账单</a><a href="userPay.html?Id='+val.customerId+'&userType=0" class="btn btn-success">支付记录</a><a onclick="adminCheck('+val.customerId+',0)" class="btn btn-success">需要检查</a><a onclick="ban('+val.customerId+',10)" class="btn btn-warning">封禁</a></td></tr>'
							);
						}
						}else{
							if(s==10){
							$("#UserList").append(
								'<tr><td>'+val.customerId+'</td><td>'+val.customerName+'</td><td>'+val.email+'</td><td>'+val.phone+'</td><td>'+val.customerBalance+'</td><td>'+changeTime(val.createTime)+'</td><td><a href="Customerchange.html?Id='+val.customerId+'" class="btn btn-default">修改</a><a href="userBill.html?Id='+val.customerId+'&userType=0" class="btn btn-danger">账单</a><a href="userPay.html?Id='+val.customerId+'&userType=0" class="btn btn-success">支付记录</a><a onclick="ban('+val.customerId+',1)" class="btn btn-success">解禁</a></td></tr>'
							);
						}else{
							$("#UserList").append(
								'<tr><td>'+val.customerId+'</td><td>'+val.customerName+'</td><td>'+val.email+'</td><td>'+val.phone+'</td><td>'+val.customerBalance+'</td><td>'+changeTime(val.createTime)+'</td><td><a href="Customerchange.html?Id='+val.customerId+'" class="btn btn-default">修改</a><a href="userBill.html?Id='+val.customerId+'&userType=0" class="btn btn-danger">账单</a><a href="userPay.html?Id='+val.customerId+'&userType=0" class="btn btn-success">支付记录</a><a onclick="adminCheck('+val.customerId+',2)" class="btn btn-success">不需检查</a><a onclick="ban('+val.customerId+',10)" class="btn btn-warning">封禁</a></td></tr>'
							);
						}	
						}
						
					}
					
				}
			},	

			// código a ejecutar si la petición falla;
			// son pasados como argumentos a la función
			// el objeto jqXHR (extensión de XMLHttpRequest), un texto con el estatus
			// de la petición y un texto con la descripción del error que haya dado el servidor
			error : function(jqXHR, status, error) {
				alert('Disculpe, existió un problema: '+xhr.status+" "+error+". No se podrá continuar");
			},

			// código a ejecutar sin importar si la petición falló o no
			complete : function(jqXHR, status) {
				// alert('Petición realizada');
			}
		});
}


$("#changep").click(function(argument) {
	// body...
	changep();
})


function adminCheck(id,status) {
	// body...
	var fd= new FormData();
			fd.append("customerId",id);
			fd.append("adminCheck",status);
			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/customerInfo/admincheck.do',
			// especifica si será una petición POST o GET
			type : 'POST',
			// el tipo de información que se espera de respuesta 
			dataType : 'json',
			data:fd,

			processData: false,  // 告诉jQuery不要去处理发送的数据
            
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头

			success: function(data) {
				if(data.status==0){
					alert(data.data);
					window.location.reload();
				}else{
					alert(data.msg)
				}
			}
		});
}

function changep() {
	// body...

			var price = $("#price").val();
			var type = $("#type").val();
			var id = $("#customerId").val();
			var fd= new FormData();
			fd.append("price",price);
			fd.append("type",type);
			fd.append("customerId",id);
			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/customerInfo/deductoraddprice.do',
			// especifica si será una petición POST o GET
			type : 'POST',
			// el tipo de información que se espera de respuesta 
			dataType : 'json',
			data:fd,

			processData: false,  // 告诉jQuery不要去处理发送的数据
            
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头

			success: function(data) {
				if(data.status==0){
					alert(data.data)
				}else{
					alert(data.msg)
				}
			}
		});
	
		
}

function getById(Id) {
	// body...
	$.ajax({
			// la URL para la petición
			url : '/o2o/admin/customerInfo/getcustomerbyid.do?customerId='+Id,
			// especifica si será una petición POST o GET
			type : 'GET',
			// el tipo de información que se espera de respuesta
			dataType : 'json',

			processData: false,  // 告诉jQuery不要去处理发送的数据
            
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头

			// código a ejecutar si la petición es satisfactoria;
			// la respuesta es pasada como argumento a la función
			success : function(v) {
				if(v.status==1){
					alert(v.msg);  
				}else{
					var data = v.data;
					$("#customerId").val(data.customerId);
					$("#customerName").val(data.customerName);
					$("#customerPayCount").val(data.customerPayCount);
					$("#customerQq").val(data.customerQq);
					$("#email").val(data.email);
					$("#personCode").val(data.personCode);
					$("#phone").val(data.phone);
					$("#customerCity").val(data.customerCity);
					$("#customerProv").val(data.customerProv);
				}
			},	

			// código a ejecutar si la petición falla;
			// son pasados como argumentos a la función
			// el objeto jqXHR (extensión de XMLHttpRequest), un texto con el estatus
			// de la petición y un texto con la descripción del error que haya dado el servidor
			error : function(jqXHR, status, error) {
				alert('Disculpe, existió un problema: '+xhr.status+" "+error+". No se podrá continuar");
			},

			// código a ejecutar sin importar si la petición falló o no
			complete : function(jqXHR, status) {
				// alert('Petición realizada');
			}
		});		
}

function changeCustomer(argument) {
	// body...
			var fd = new FormData();
			var priceTogetherInfo= new Array("customerId","password","customerName","phone","email","customerQq","personCode","customerProv","customerCity","customerAttention","customerPayCount");

			for(let index in priceTogetherInfo) {  
       			// console.log(index,engineerrankinfo[index]);
       			var val=$("#"+priceTogetherInfo[index]+"").val();
	       		fd.append(priceTogetherInfo[index],val);
    		}

			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/customerInfo/check.do',

			// la información a enviar
			// (también es posible utilizar una cadena de datos)
			data : fd ,

			// especifica si será una petición POST o GET
			type : 'POST',

			// el tipo de información que se espera de respuesta
			dataType : 'json',

			processData: false,  // 告诉jQuery不要去处理发送的数据
            
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头

			// código a ejecutar si la petición es satisfactoria;
			// la respuesta es pasada como argumento a la función
			success : function(v) {
				if(v.status==1){
					alert(v.msg);
				}else{
					alert('操作成功');
				}
			},	

			// código a ejecutar si la petición falla;
			// son pasados como argumentos a la función
			// el objeto jqXHR (extensión de XMLHttpRequest), un texto con el estatus
			// de la petición y un texto con la descripción del error que haya dado el servidor
			error : function(jqXHR, status, error) {
				alert('Disculpe, existió un problema: '+xhr.status+" "+error+". No se podrá continuar");
			},

			// código a ejecutar sin importar si la petición falló o no
			complete : function(jqXHR, status) {
				// alert('Petición realizada');
			}
		});	
}

function changemoney() {
	// body...
	
}

function ban(Id,s) {
	// body...
			var fd = new FormData();
	       	fd.append("customerId",Id);
	       	fd.append("customerState",s);

			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/customerInfo/check.do',

			// la información a enviar
			// (también es posible utilizar una cadena de datos)
			data : fd ,

			// especifica si será una petición POST o GET
			type : 'POST',

			// el tipo de información que se espera de respuesta
			dataType : 'json',

			processData: false,  // 告诉jQuery不要去处理发送的数据
            
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头

			// código a ejecutar si la petición es satisfactoria;
			// la respuesta es pasada como argumento a la función
			success : function(v) {
				if(v.status==1){
					alert(v.msg);
				}else{
					alert('操作成功');
					window.location.reload();
				}
			},	

			// código a ejecutar si la petición falla;
			// son pasados como argumentos a la función
			// el objeto jqXHR (extensión de XMLHttpRequest), un texto con el estatus
			// de la petición y un texto con la descripción del error que haya dado el servidor
			error : function(jqXHR, status, error) {
				alert('Disculpe, existió un problema: '+xhr.status+" "+error+". No se podrá continuar");
			},

			// código a ejecutar sin importar si la petición falló o no
			complete : function(jqXHR, status) {
				// alert('Petición realizada');
			}
		});	
}


$("#search").on("input propertychange",function(){
	// console.log($(this).attr('id'));
	var v = $(this).val();
	s= $(this).attr("name");
	if(v!=""){
		$.ajax({
			url: '/o2o/admin/customerInfo/getByCustomerIdLike.do?customerId='+v+"&pageNum=1&pageSize=20",
			method: 'get',
			success: function(data) {
				$("#UserList").html("");
				if(data.status==0){
					for (var i = data.data.list.length - 1; i >= 0; i--) {
						var val = data.data.list[i];
						if(val.adminCheck==2){
							if(s==10){
							$("#UserList").append(
								'<tr><td>'+val.customerId+'</td><td>'+val.customerName+'</td><td>'+val.email+'</td><td>'+val.phone+'</td><td>'+val.customerBalance+'</td><td>'+changeTime(val.createTime)+'</td><td><a href="Customerchange.html?Id='+val.customerId+'" class="btn btn-default">修改</a><a href="userBill.html?Id='+val.customerId+'&userType=0" class="btn btn-danger">账单</a><a href="userPay.html?Id='+val.customerId+'&userType=0" class="btn btn-success">支付记录</a><a onclick="ban('+val.customerId+',1)" class="btn btn-success">解禁</a></td></tr>'
							);
						}else{
							$("#UserList").append(
								'<tr><td>'+val.customerId+'</td><td>'+val.customerName+'</td><td>'+val.email+'</td><td>'+val.phone+'</td><td>'+val.customerBalance+'</td><td>'+changeTime(val.createTime)+'</td><td><a href="Customerchange.html?Id='+val.customerId+'" class="btn btn-default">修改</a><a href="userBill.html?Id='+val.customerId+'&userType=0" class="btn btn-danger">账单</a><a href="userPay.html?Id='+val.customerId+'&userType=0" class="btn btn-success">支付记录</a><a onclick="adminCheck('+val.customerId+',0)" class="btn btn-success">需要检查</a><a onclick="ban('+val.customerId+',10)" class="btn btn-warning">封禁</a></td></tr>'
							);
						}
						}else{
							if(s==10){
							$("#UserList").append(
								'<tr><td>'+val.customerId+'</td><td>'+val.customerName+'</td><td>'+val.email+'</td><td>'+val.phone+'</td><td>'+val.customerBalance+'</td><td>'+changeTime(val.createTime)+'</td><td><a href="Customerchange.html?Id='+val.customerId+'" class="btn btn-default">修改</a><a href="userBill.html?Id='+val.customerId+'&userType=0" class="btn btn-danger">账单</a><a href="userPay.html?Id='+val.customerId+'&userType=0" class="btn btn-success">支付记录</a><a onclick="ban('+val.customerId+',1)" class="btn btn-success">解禁</a></td></tr>'
							);
						}else{
							$("#UserList").append(
								'<tr><td>'+val.customerId+'</td><td>'+val.customerName+'</td><td>'+val.email+'</td><td>'+val.phone+'</td><td>'+val.customerBalance+'</td><td>'+changeTime(val.createTime)+'</td><td><a href="Customerchange.html?Id='+val.customerId+'" class="btn btn-default">修改</a><a href="userBill.html?Id='+val.customerId+'&userType=0" class="btn btn-danger">账单</a><a href="userPay.html?Id='+val.customerId+'&userType=0" class="btn btn-success">支付记录</a><a onclick="adminCheck('+val.customerId+',2)" class="btn btn-success">不需检查</a><a onclick="ban('+val.customerId+',10)" class="btn btn-warning">封禁</a></td></tr>'
							);
						}	
						}
					}
				}
			}
		});
	}else{
		var pageNum=getPar("pageNum");
                if(!pageNum){
                    pageNum=1;
                }
				$("#UserList").html("");
                
                // Easy pie charts
                getCustomerBystate(pageNum,1);
	}
});