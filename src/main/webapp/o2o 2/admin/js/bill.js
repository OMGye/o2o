var pageNum;
var firstCategory=false;
var orderQae=false;
var state=false;

function getPageNum() {
	// body...
	pageNum=getPar("pageNum");
	if(!pageNum){
		pageNum=1;
	}
}

function changeState(state,info) {
	// body...
	getPageNum();
	this.state=state;
	$("#OrderList").html("");
	$(".stateshow").html("-"+info+"-");
	getAllorder(pageNum,firstCategory,orderQae,state);
}

function changFirst(firstCategory,info) {
	// body...
	getPageNum();
	this.firstCategory=firstCategory;
	$("#OrderList").html("");
	$(".firstCategoryshow").html("-"+info+"-");
	getAllorder(pageNum,firstCategory,orderQae,state);
}

function changSecond(orderQae,info) {
	// body...
	getPageNum();
	this.orderQae=orderQae;
	$("#OrderList").html("");
	$(".orderQae").html("-"+info+"-");
	getAllorder(pageNum,firstCategory,orderQae,state);
}

function getAllbill(pageNum) {
	// body...
	var url='/o2o/admin/billInfo/list.do?pageNum='+pageNum+'&pageSize='+20;
	$.ajax({
			// la URL para la petición
			url : url,
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
						$(".next").attr("href","customer.html?pageNum="+(pageNum+1));
					}
					if(!v.data.hasPreviousPage){
						$(".prev").addClass("disabled");
					}else{
						$(".prev").removeClass("disabled");
						$(".prev").attr("href","customer.html?pageNum="+(pageNum-1));
					}
					for (var i =  0; i < v.data.list.length; i++) {
						var val = v.data.list[i];
						console.log(val);
						if(!val.engineerCheckName){
							val.engineerCheckName="无审核人";
						}
						if(!val.engineerName){
							val.engineerName="无制作人";
						}
						if(val.orderQae=="1"){
							val.orderQae="是";
						}else{
							val.orderQae="否";
						}
						if (val.userType==1) {
							val.userType="工程师";
						}else{
							val.userType="客户";
						}
						$("#OrderList").append(
							'<tr><td>'+val.billId+'</td><td>'+val.userId+'</td><td>'+val.userName+'</td><td>'+val.userType+'</td><td>'+val.billMoney+'</td><td>'+val.billDec+'</td><td>'+changeTime(val.createTime)+'</td></tr>'
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

function getOnebill(pageNum,userId,userType) {
	// body...
	var url='/o2o/admin/billInfo/list.do?pageNum='+pageNum+'&pageSize='+20+"&userId="+userId+"&userType="+userType;
	$.ajax({
			// la URL para la petición
			url : url,
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
						$(".next").attr("href","customer.html?pageNum="+(pageNum+1));
					}
					if(!v.data.hasPreviousPage){
						$(".prev").addClass("disabled");
					}else{
						$(".prev").removeClass("disabled");
						$(".prev").attr("href","customer.html?pageNum="+(pageNum-1));
					}
					for (var i =  0; i < v.data.list.length; i++) {
						var val = v.data.list[i];
						console.log(val);
						if(!val.engineerCheckName){
							val.engineerCheckName="无审核人";
						}
						if(!val.engineerName){
							val.engineerName="无制作人";
						}
						if(val.orderQae=="1"){
							val.orderQae="是";
						}else{
							val.orderQae="否";
						}
						if (val.userType==1) {
							val.userType="工程师";
						}else{
							val.userType="客户";
						}
						$("#OrderList").append(
							'<tr><td>'+val.billId+'</td><td>'+val.userId+'</td><td>'+val.userName+'</td><td>'+val.userType+'</td><td>'+val.billMoney+'</td><td>'+val.billDec+'</td><td>'+changeTime(val.createTime)+'</td></tr>'
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
					$("#engineerPayCount").val(data.engineerPayCount);
					$("#engineerQq").val(data.engineerQq);
					$("#email").val(data.email);
					$("#phone").val(data.phone);
					$("#engineerCity").val(data.engineerCity);
					$("#engineerProv").val(data.engineersProv);
					$("#engineerClassfy").val(data.engineerClassfy);
					$("#engineerRank").val(data.engineerRank+"级工程师");
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
			var priceTogetherInfo= new Array("engineerId","password");

			for(let index in priceTogetherInfo) {  
       			// console.log(index,engineerrankinfo[index]);
       			var val=$("#"+priceTogetherInfo[index]+"").val();
	       		fd.append(priceTogetherInfo[index],val);
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
	if(v!=""){
		$.ajax({
			url: '/o2o/admin/billInfo/getByBillIdLike.do?billId='+v+"&pageNum=1&pageSize=20",
			method: 'get',
			success: function(data) {
				$("#OrderList").html("");
				if(data.status==0){
					for (var i = data.data.list.length - 1; i >= 0; i--) {
						var val = data.data.list[i];
						if(!val.engineerCheckName){
							val.engineerCheckName="无审核人";
						}
						if(!val.engineerName){
							val.engineerName="无制作人";
						}
						if(val.orderQae=="1"){
							val.orderQae="是";
						}else{
							val.orderQae="否";
						}
						if (val.userType==1) {
							val.userType="客户";
						}else{
							val.userType="工程师";
						}
						$("#OrderList").append(
							'<tr><td>'+val.billId+'</td><td>'+val.userId+'</td><td>'+val.userName+'</td><td>'+val.userType+'</td><td>'+val.billMoney+'</td><td>'+val.billDec+'</td><td>'+changeTime(val.createTime)+'</td><td><a href="orderInfo.html?Id='+val.orderId+'" class="btn btn-default">详情</a><a onclick="del('+val.orderId+',1)" class="btn btn-warning">删除</a></td></tr>'
						);
					}
				}
			}
		});
	}else{
		var pageNum=getPar("pageNum");
                if(!pageNum){
                    pageNum=1;
                }
                var firstCategory=false;
                var orderQae=false;
                var state=false;
                getAllbill(pageNum,firstCategory,orderQae,state);
	}
});