$("#pass").click(function(){
	dopass($("#engineerId").val(),$("#firstGategory").val(),$("#engineerRank").val());
});

var s;

function dopass(a,b,c) {
	// body...
			var fd = new FormData();
	       	fd.append("engineerId",a);
	       	fd.append("engineerState",1);
	       	fd.append("engineerClassfy",b);
	       	fd.append("engineerRank",c);
			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/engineerInfo/check.do',

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

function getEngineerBystate(pn,s) {
	// body...
	s=s;
	console.log(s);
	$.ajax({
			// la URL para la petición
			url : '/o2o/admin/engineerInfo/list.do?pageNum='+pn+'&pageSize='+20+'&state='+s,
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
					for (var i = 0; i <v.data.list.length ; i++) {
						var val = v.data.list[i];
						console.log(val);
						if(val.adminCheck==2){
							if(s==10){
							$("#UserList").append(
								'<tr><td>'+val.engineerId+'</td><td>'+val.engineerName+'</td><td>'+val.email+'</td><td>'+val.engineerQuantity*100+'%</td><td>'+val.phone+'</td><td>'+val.engineerBalance+'</td><td>'+changeTime(val.createTime)+'</td><td><a href="Engineerchange.html?Id='+val.engineerId+'" class="btn btn-default">修改</a><a href="userBill.html?Id='+val.engineerId+'&userType=1" class="btn btn-danger">账单</a><a onclick="ban('+val.engineerId+',1)" class="btn btn-success">解禁</a></td></tr>'
							);
							}else if(s==1){
								$("#UserList").append(
									'<tr><td>'+val.engineerId+'</td><td>'+val.engineerName+'</td><td>'+val.email+'</td><td>'+val.engineerQuantity*100+'%</td><td>'+val.phone+'</td><td>'+val.engineerBalance+'</td><td>'+changeTime(val.createTime)+'</td><td><a href="Engineerchange.html?Id='+val.engineerId+'" class="btn btn-default">修改</a><a onclick="adminCheck('+val.engineerId+',0)" class="btn btn-success">需要检查</a><a href="userBill.html?Id='+val.engineerId+'&userType=1" class="btn btn-danger">账单</a><a onclick="ban('+val.engineerId+',10)" class="btn btn-warning">封禁</a></td></tr>'
								);
							}else{
								$("#UserList").append(
									'<tr><td>'+val.engineerId+'</td><td>'+val.engineerName+'</td><td>'+val.email+'</td><td>'+val.engineerQuantity*100+'%</td><td>'+val.phone+'</td><td>'+val.engineerBalance+'</td><td>'+changeTime(val.createTime)+'</td><td><a href="'+val.engineerFile+'" class="btn btn-default">下载简历</a><a onclick="pass('+val.engineerId+')" class="btn btn-warning">过审</a></td></tr>'
								);
							}
						}else{
							if(s==10){
							$("#UserList").append(
								'<tr><td>'+val.engineerId+'</td><td>'+val.engineerName+'</td><td>'+val.email+'</td><td>'+val.engineerQuantity*100+'%</td><td>'+val.phone+'</td><td>'+val.engineerBalance+'</td><td>'+changeTime(val.createTime)+'</td><td><a href="Engineerchange.html?Id='+val.engineerId+'" class="btn btn-default">修改</a><a href="userBill.html?Id='+val.engineerId+'&userType=1" class="btn btn-danger">账单</a><a onclick="ban('+val.engineerId+',1)" class="btn btn-success">解禁</a></td></tr>'
							);
							}else if(s==1){
								$("#UserList").append(
									'<tr><td>'+val.engineerId+'</td><td>'+val.engineerName+'</td><td>'+val.email+'</td><td>'+val.engineerQuantity*100+'%</td><td>'+val.phone+'</td><td>'+val.engineerBalance+'</td><td>'+changeTime(val.createTime)+'</td><td><a href="Engineerchange.html?Id='+val.engineerId+'" class="btn btn-default">修改</a><a onclick="adminCheck('+val.engineerId+',2)" class="btn btn-success">不需检查</a><a href="userBill.html?Id='+val.engineerId+'&userType=1" class="btn btn-danger">账单</a><a onclick="ban('+val.engineerId+',10)" class="btn btn-warning">封禁</a></td></tr>'
								);
							}else{
								$("#UserList").append(
									'<tr><td>'+val.engineerId+'</td><td>'+val.engineerName+'</td><td>'+val.email+'</td><td>'+val.engineerQuantity*100+'%</td><td>'+val.phone+'</td><td>'+val.engineerBalance+'</td><td>'+changeTime(val.createTime)+'</td><td><a href="'+val.engineerFile+'" class="btn btn-default">下载简历</a><a onclick="pass('+val.engineerId+')" class="btn btn-warning">过审</a></td></tr>'
								);
							}
						}
						
					}

					//分页处理
					if(!v.data.hasNextPage){
						$(".next").addClass("disabled");
					}else{
						$(".next").removeClass("disabled");
						$(".nexthref").attr("href","engineer.html?pageNum="+(parseInt(pn)+parseInt(1)));
					}
					if(!v.data.hasPreviousPage){
						$(".prev").addClass("disabled");
					}else{
						$(".prev").removeClass("disabled");
						$(".fronthref").attr("href","engineer.html?pageNum="+(parseInt(pn)-parseInt(1)));
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
			fd.append("engineerId",id);
			fd.append("adminCheck",status);
			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/engineerInfo/admincheck.do',
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

function changep() {
	// body...

			var price = $("#price").val();
			var type = $("#type").val();
			var id = $("#engineerId").val();
			var fd= new FormData();
			fd.append("price",price);
			fd.append("type",type);
			fd.append("engineerId",id);
			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/engineerInfo/deductoraddprice.do',
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

function pass(id) {
	// body...
	$('#engineerId').val(id);
	$('#selectRank').modal('show');
}

function getEngineerRank() {
	// body...
	$.ajax({
			// la URL para la petición
			url : '/o2o/admin/engineerrankinfo/list.do?pageNum='+1+'&pageSize='+100,
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
					getById(getPar("Id"))
					for (var i = v.data.list.length - 1; i >= 0; i--) {
						var val = v.data.list[i];
						$("#engineerRank").append(
							'<option value="'+val.engineerRank+'">'+val.engineerRank+'级工程师</option>'
						);
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

function getById(Id) {
	// body...
	$.ajax({
			// la URL para la petición
			url : '/o2o/admin/engineerInfo/getengineerbyid.do?engineerId='+Id,
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
					$("#engineerId").val(data.engineerId);
					$("#engineerName").val(data.engineerName);
					$("#personCode").val(data.personCode);
					$("#engineerPayCount").val(data.engineerPayCount);
					$("#engineerQq").val(data.engineerQq);
					$("#email").val(data.email);
					$("#phone").val(data.phone);
					$("#engineerCity").val(data.engineerCity);
					$("#engineerProv").val(data.engineersProv);
					$("#engineerClassfy").val(data.engineerClassfy);
					$("#engineerRank").val(data.engineerRank);
					$("#files").attr("href",data.engineerFile);
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

function changeengineer(argument) {
	// body...
			var fd = new FormData();
			var priceTogetherInfo= new Array("engineerId","password","engineerName","phone","email","engineerQq","personCode","engineerProv","engineerCity","engineerAttention","engineerPayCount","engineerClassfy","engineerRank");

			for(let index in priceTogetherInfo) {  
       			// console.log(index,engineerrankinfo[index]);
       			var val=$("#"+priceTogetherInfo[index]+"").val();
       			if(val!=""){
		       		fd.append(priceTogetherInfo[index],val);
       			}
    		}

			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/engineerInfo/check.do',

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

function ban(Id,s) {
	// body...
			var fd = new FormData();
	       	fd.append("EngineerId",Id);
	       	fd.append("EngineerState",s);

			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/engineerInfo/check.do',

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
	s=$(this).attr('name');
	if(v!=""){
		$.ajax({
			url: '/o2o/admin/engineerInfo/getByEngineerIdLike.do?engineerId='+v+"&pageNum=1&pageSize=20",
			method: 'get',
			success: function(data) {
				$("#UserList").html("");
				if(data.status==0){
					for (var i = data.data.list.length - 1; i >= 0; i--) {
						var val = data.data.list[i];
						console.log(s);
						if(val.adminCheck==2){
							if(s==10){
							$("#UserList").append(
								'<tr><td>'+val.engineerId+'</td><td>'+val.engineerName+'</td><td>'+val.email+'</td><td>'+val.engineerQuantity*100+'%</td><td>'+val.phone+'</td><td>'+changeTime(val.createTime)+'</td><td><a href="Engineerchange.html?Id='+val.engineerId+'" class="btn btn-default">修改</a><a href="userBill.html?Id='+val.engineerId+'&userType=1" class="btn btn-danger">账单</a><a onclick="ban('+val.engineerId+',1)" class="btn btn-success">解禁</a></td></tr>'
							);
						}else if(s==1){
							$("#UserList").append(
								'<tr><td>'+val.engineerId+'</td><td>'+val.engineerName+'</td><td>'+val.email+'</td><td>'+val.engineerQuantity*100+'%</td><td>'+val.phone+'</td><td>'+changeTime(val.createTime)+'</td><td><a href="Engineerchange.html?Id='+val.engineerId+'" class="btn btn-default">修改</a><a onclick="adminCheck('+val.engineerId+',0)" class="btn btn-success">需要检查</a><a href="userBill.html?Id='+val.engineerId+'&userType=1" class="btn btn-danger">账单</a><a onclick="ban('+val.engineerId+',10)" class="btn btn-warning">封禁</a></td></tr>'
							);
						}else{
							$("#UserList").append(
								'<tr><td>'+val.engineerId+'</td><td>'+val.engineerName+'</td><td>'+val.email+'</td><td>'+val.engineerQuantity*100+'%</td><td>'+val.phone+'</td><td>'+changeTime(val.createTime)+'</td><td><a href="'+val.engineerFile+'" class="btn btn-default">下载简历</a><a onclick="pass('+val.engineerId+')" class="btn btn-warning">过审</a></td></tr>'
							);
						}
						}else{
						if(s==10){
							$("#UserList").append(
								'<tr><td>'+val.engineerId+'</td><td>'+val.engineerName+'</td><td>'+val.email+'</td><td>'+val.engineerQuantity*100+'%</td><td>'+val.phone+'</td><td>'+changeTime(val.createTime)+'</td><td><a href="Engineerchange.html?Id='+val.engineerId+'" class="btn btn-default">修改</a><a href="userBill.html?Id='+val.engineerId+'&userType=1" class="btn btn-danger">账单</a><a onclick="ban('+val.engineerId+',1)" class="btn btn-success">解禁</a></td></tr>'
							);
						}else if(s==1){
							$("#UserList").append(
								'<tr><td>'+val.engineerId+'</td><td>'+val.engineerName+'</td><td>'+val.email+'</td><td>'+val.engineerQuantity*100+'%</td><td>'+val.phone+'</td><td>'+changeTime(val.createTime)+'</td><td><a href="Engineerchange.html?Id='+val.engineerId+'" class="btn btn-default">修改</a><a onclick="adminCheck('+val.engineerId+',0)" class="btn btn-success">不需检查</a><a href="userBill.html?Id='+val.engineerId+'&userType=1" class="btn btn-danger">账单</a><a onclick="ban('+val.engineerId+',10)" class="btn btn-warning">封禁</a></td></tr>'
							);
						}else{
							$("#UserList").append(
								'<tr><td>'+val.engineerId+'</td><td>'+val.engineerName+'</td><td>'+val.email+'</td><td>'+val.engineerQuantity*100+'%</td><td>'+val.phone+'</td><td>'+changeTime(val.createTime)+'</td><td><a href="'+val.engineerFile+'" class="btn btn-default">下载简历</a><a onclick="pass('+val.engineerId+')" class="btn btn-warning">过审</a></td></tr>'
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
                getEngineerBystate(pageNum,1);
	}
});