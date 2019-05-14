function getFinishOrder(pn) {
	// body...
			var info;
			if(getCookie("userType")==0){
				info="customer";
			}else{
				info="engineer";
			}
			$.ajax({
			// la URL para la petición
			url : '/o2o/'+info+'/orderInfo/ownerlist.do?pageSize=10&pageNum='+pn,
	
			// la información a enviar
			// (también es posible utilizar una cadena de datos)
			type : 'get',
	
			// el tipo de información que se espera de respuesta
			dataType : 'json',
	
			// código a ejecutar si la petición es satisfactoria;
			// la respuesta es pasada como argumento a la función
			success : function(json) {
				if(json.data.hasPreviousPage){
					$("#pre").show();
					$("#pre").click(function(argument) {
						// body...
						window.location.href="Myorder.html?pageNum="+(parseInt(pn)-1);
					})	
				}
				if(json.data.hasNextPage){
					$("#lp").show();
					$("#lp").click(function(argument) {
						// body...
						window.location.href="Myorder.html?pageNum="+(parseInt(pn)-1);
					})	
				}
				for (var i = 0; i <= json.data.list.length; i++) {
					var v= json.data.list[i];
					var second="CAM";
					if(v.orderDec==""){
						v.orderDec="无订单描述";
					}
					if(v.orderMi==1){
						second=second+",MI";
					}
					if(v.orderQae==1){
						second=second+",QAE";
					}
					if(getCookie("userType")==0){
						info="customer";
							
					}else{
						info="engineer";
					}
					v.orderState=JudgeState(v.orderState,v.orderQae,v.adminCheck);
					if(v.unReadMessage==0){
						$("#OrderList").append(
							'<tr><td>'+v.orderId+'</td><td>'+v.customerFileRealName+'</td><td>'+v.orderFirstCategory+'</td><td>'+second+'</td><td>'+v.orderState+'</td><td>'+changeTime(v.createTime)+'</td><td></td><td><a href="orderDetail.html?orderId='+v.orderId+'" class="btn btn-default">查看</a></td></tr>'
						);
					}else{
						$("#OrderList").append(
							'<tr><td>'+v.orderId+'</td><td>'+v.customerFileRealName+'</td><td>'+v.orderFirstCategory+'</td><td>'+second+'</td><td>'+v.orderState+'</td><td>'+changeTime(v.createTime)+'</td><td><span class="badge badge-warning">'+v.unReadMessage+'</span></td><td><a href="orderDetail.html?orderId='+v.orderId+'" class="btn btn-default">查看</a></td></tr>'
						);
					}
				}
			},
	
			// código a ejecutar si la petición falla;
			// son pasados como argumentos a la función
			// el objeto jqXHR (extensión de XMLHttpRequest), un texto con el estatus
			// de la petición y un texto con la descripción del error que haya dado el servidor
			error : function(jqXHR, status, error) {
			},
	
			// código a ejecutar sin importar si la petición falló o no
			complete : function(jqXHR, status) {
			}
		});
}

function getOwnerQae(argument) {
	// body...
			$.ajax({
			// la URL para la petición
			url : '/o2o/engineer/orderInfo/ownerqaelist.do?pageSize=10&pageNum='+pn,
	
			// la información a enviar
			// (también es posible utilizar una cadena de datos)
			type : 'get',
	
			// el tipo de información que se espera de respuesta
			dataType : 'json',
	
			// código a ejecutar si la petición es satisfactoria;
			// la respuesta es pasada como argumento a la función
			success : function(json) {
				if(json.data.hasPreviousPage){
					$("#pre").show();
					$("#pre").click(function(argument) {
						// body...
						window.location.href="Myorder.html?pageNum="+(parseInt(pn)-1);
					})	
				}
				if(json.data.hasNextPage){
					$("#lp").show();
					$("#lp").click(function(argument) {
						// body...
						window.location.href="Myorder.html?pageNum="+(parseInt(pn)-1);
					})	
				}
				for (var i = 0; i <= json.data.list.length; i++) {
					var v= json.data.list[i];
					var second="CAM";
					if(v.orderDec==""){
						v.orderDec="无订单描述";
					}
					if(v.orderMi==1){
						second=second+",MI";
					}
					if(v.orderQae==1){
						second=second+",QAE";
					}
					v.orderState=JudgeState(v.orderState,v.orderQae);
					if(v.unReadMessage==0){
						$("#OrderList").append(
							'<tr><td>'+v.orderId+'</td><td>'+v.customerFileRealName+'</td><td>'+v.orderFirstCategory+'</td><td>'+second+'</td><td>'+v.orderState+'</td><td>'+changeTime(v.createTime)+'</td><td></td><td><a href="orderDetail.html?orderId='+v.orderId+'" class="btn btn-default">查看</a></td></tr>'
						);
					}else{
						$("#OrderList").append(
							'<tr><td>'+v.orderId+'</td><td>'+v.customerFileRealName+'</td><td>'+v.orderFirstCategory+'</td><td>'+second+'</td><td>'+v.orderState+'</td><td>'+changeTime(v.createTime)+'</td><td><span class="badge badge-warning">'+v.unReadMessage+'</span></td><td><a href="orderDetail.html?orderId='+v.orderId+'" class="btn btn-default">查看</a></td></tr>'
						);
					}
				}
			},
	
			// código a ejecutar si la petición falla;
			// son pasados como argumentos a la función
			// el objeto jqXHR (extensión de XMLHttpRequest), un texto con el estatus
			// de la petición y un texto con la descripción del error que haya dado el servidor
			error : function(jqXHR, status, error) {
			},
	
			// código a ejecutar sin importar si la petición falló o no
			complete : function(jqXHR, status) {
			}
		});
}

function getWaitCheckList(pn) {
	// body...
			var info;
			if(getCookie("userType")==0){
				info="customer";
			}else{
				info="engineer";
			}
			$.ajax({
			// la URL para la petición
			url : '/o2o/'+info+'/orderInfo/canqaecaughtlist.do?pageSize=10&pageNum='+pn,
	
			// la información a enviar
			// (también es posible utilizar una cadena de datos)
			type : 'get',
	
			// el tipo de información que se espera de respuesta
			dataType : 'json',
	
			// código a ejecutar si la petición es satisfactoria;
			// la respuesta es pasada como argumento a la función
			success : function(json) {
				if(json.data.hasPreviousPage){
					$("#pre").show();
					$("#pre").click(function(argument) {
						// body...
						window.location.href="Myorder.html?pageNum="+(parseInt(pn)-1);
					})	
				}
				if(json.data.hasNextPage){
					$("#lp").show();
					$("#lp").click(function(argument) {
						// body...
						window.location.href="Myorder.html?pageNum="+(parseInt(pn)-1);
					})	
				}
				for (var i = 0; i <= json.data.list.length; i++) {
					var v= json.data.list[i];
					var second="CAM";
					if(v.orderDec==""){
						v.orderDec="无订单描述";
					}
					if(v.orderMi==1){
						second=second+",MI";
					}
					if(v.orderQae==1){
						second=second+",QAE";
					}
					v.orderState=JudgeState(v.orderState,v.orderQae);
						$("#OrderList").append(
							'<tr><td>'+v.orderId+'</td><td>'+v.customerFileRealName+'</td><td>'+v.orderFirstCategory+'</td><td>'+second+'</td><td>'+v.orderState+'</td><td>'+changeTime(v.createTime)+'</td><td></td><td><a href="orderDetail.html?orderId='+v.orderId+'&qae=1" class="btn btn-default">查看</a></td></tr>'
						);
				}
			},
	
			// código a ejecutar si la petición falla;
			// son pasados como argumentos a la función
			// el objeto jqXHR (extensión de XMLHttpRequest), un texto con el estatus
			// de la petición y un texto con la descripción del error que haya dado el servidor
			error : function(jqXHR, status, error) {
			},
	
			// código a ejecutar sin importar si la petición falló o no
			complete : function(jqXHR, status) {
			}
		});
}

function getCanCaughtList(pn) {
	// body...
			var info;
			if(getCookie("userType")==0){
				info="customer";
			}else{
				info="engineer";
			}
			$.ajax({
			// la URL para la petición
			url : '/o2o/'+info+'/orderInfo/cancaughtlist.do?pageSize=10&pageNum='+pn,
	
			// la información a enviar
			// (también es posible utilizar una cadena de datos)
			type : 'get',
	
			// el tipo de información que se espera de respuesta
			dataType : 'json',
	
			// código a ejecutar si la petición es satisfactoria;
			// la respuesta es pasada como argumento a la función
			success : function(json) {
				if(json.data.hasPreviousPage){
					$("#pre").show();
					$("#pre").click(function(argument) {
						// body...
						window.location.href="Myorder.html?pageNum="+(parseInt(pn)-1);
					})	
				}
				if(json.data.hasNextPage){
					$("#lp").show();
					$("#lp").click(function(argument) {
						// body...
						window.location.href="Myorder.html?pageNum="+(parseInt(pn)-1);
					})	
				}
				for (var i = 0; i <= json.data.list.length; i++) {
					var v= json.data.list[i];
					var second="CAM";
					if(v.orderDec==""){
						v.orderDec="无订单描述";
					}
					if(v.orderMi==1){
						second=second+",MI";
					}
					if(v.orderQae==1){
						second=second+",QAE";
					}
					v.orderState=JudgeState(v.orderState,v.orderQae);
						$("#OrderList").append(
							'<tr><td>'+v.orderId+'</td><td>'+v.customerFileRealName+'</td><td>'+v.orderFirstCategory+'</td><td>'+second+'</td><td>'+v.orderState+'</td><td>'+changeTime(v.createTime)+'</td><td></td><td><a href="orderDetail.html?orderId='+v.orderId+'" class="btn btn-default">查看</a></td></tr>'
						);
				}
			},
	
			// código a ejecutar si la petición falla;
			// son pasados como argumentos a la función
			// el objeto jqXHR (extensión de XMLHttpRequest), un texto con el estatus
			// de la petición y un texto con la descripción del error que haya dado el servidor
			error : function(jqXHR, status, error) {
			},
	
			// código a ejecutar sin importar si la petición falló o no
			complete : function(jqXHR, status) {
			}
		});
}

function getCustomerOrder(pn,sta) {
			var info;
			if(getCookie("userType")==0){
				info="customer";
			}else{
				info="engineer";
			}
			if(sta==13){
				var url= '/o2o/'+info+'/orderInfo/list.do?pageSize=10&pageNum='+pn;
			}else{
				var url= '/o2o/'+info+'/orderInfo/list.do?pageSize=10&pageNum='+pn+'&orderState='+sta;
			}
			$.ajax({
			// la URL para la petición
			url : url,
	
			// la información a enviar
			// (también es posible utilizar una cadena de datos)
			type : 'get',
	
			// el tipo de información que se espera de respuesta
			dataType : 'json',
	
			// código a ejecutar si la petición es satisfactoria;
			// la respuesta es pasada como argumento a la función
			success : function(json) {
				if(json.data.hasPreviousPage){
					console.log("hola datevid");
					$("#pre").show();
					if(sta!=13){
						$("#pre").click(function(argument) {
							// body...
							window.location.href="Myorder.html?sta="+sta+"&pageNum="+(parseInt(pn)-1);
						})	
					}else{
						$("#pre").click(function(argument) {
							// body...
							window.location.href="Myorder.html?pageNum="+(parseInt(pn)-1);
						})	
					}
				}
				if(json.data.hasNextPage){
					$("#lp").show();
					if(sta!=13){
						$("#lp").click(function(argument) {
							// body...
							window.location.href="Myorder.html?sta="+sta+"&pageNum="+(parseInt(pn)+1);
						})	
					}else{
						$("#lp").click(function(argument) {
							// body...
							window.location.href="Myorder.html?pageNum="+(parseInt(pn)+1);
						})	
					}
				}
				for (var i = 0; i <= json.data.list.length; i++) {
					var v= json.data.list[i];
					var second="CAM";
					if(v.orderMi==1){
						second=second+",MI";
					}
					if(v.orderQae==1){
						second=second+",QAE";
					}
					v.orderState=JudgeState(v.orderState,v.orderQae);
					// <span class="badge badge-warning">Warning</span>
					if(v.unReadMessage==0){
						if(v.orderState=="未付款"){
							$("#OrderList").append(
								'<tr><td>'+v.orderId+'</td><td>'+v.customerFileRealName+'</td><td>'+v.orderFirstCategory+'</td><td>'+second+'</td><td>'+v.orderState+'</td><td>'+changeTime(v.createTime)+'</td><td></td><td><a href="orderDetail.html?orderId='+v.orderId+'" class="btn btn-default">查看</a><a href="#" name="'+v.orderId+'" class="btn btn-success doPay">付款</a></td></tr>'
							);
							$(".doPay").click(function(argument) {
								// body...
								$("#AliPay").attr("name",$(this).attr("name"));
								$("#Choose").modal("show");
							})
						}else{
							$("#OrderList").append(
								'<tr><td>'+v.orderId+'</td><td>'+v.customerFileRealName+'</td><td>'+v.orderFirstCategory+'</td><td>'+second+'</td><td>'+v.orderState+'</td><td>'+changeTime(v.createTime)+'</td><td></td><td><a href="orderDetail.html?orderId='+v.orderId+'" class="btn btn-default">查看</a></td></tr>'
							);
						}
					}else{
						if(v.orderState=="未付款"){
							$("#OrderList").append(
								'<tr><td>'+v.orderId+'</td><td>'+v.customerFileRealName+'</td><td>'+v.orderFirstCategory+'</td><td>'+second+'</td><td>'+v.orderState+'</td><td>'+changeTime(v.createTime)+'</td><td><span class="badge badge-warning">'+v.unReadMessage+'</span></td><td><a href="orderDetail.html?orderId='+v.orderId+'" class="btn btn-default">查看</a><a href="#" name="'+v.orderId+'" class="btn btn-success doPay">付款</a></td></tr>'
							);
							$(".doPay").click(function(argument) {
								// body...
								$("#AliPay").attr("name",$(this).attr("name"));
								$("#Choose").modal("show");
							})
						}else{
							$("#OrderList").append(
								'<tr><td>'+v.orderId+'</td><td>'+v.customerFileRealName+'</td><td>'+v.orderFirstCategory+'</td><td>'+second+'</td><td>'+v.orderState+'</td><td>'+changeTime(v.createTime)+'</td><td><span class="badge badge-warning">'+v.unReadMessage+'</span></td><td><a href="orderDetail.html?orderId='+v.orderId+'" class="btn btn-default">查看</a></td></tr>'
							);
						}		
					}
						
				}
			},
	
			// código a ejecutar si la petición falla;
			// son pasados como argumentos a la función
			// el objeto jqXHR (extensión de XMLHttpRequest), un texto con el estatus
			// de la petición y un texto con la descripción del error que haya dado el servidor
			error : function(jqXHR, status, error) {
			},
	
			// código a ejecutar sin importar si la petición falló o no
			complete : function(jqXHR, status) {

			}
		});
}

$("#AliPay").click(function() {
	// body...
	$("#Choose").modal("hide");
	payMoney($(this).attr("name"));
})

$("#PayByBlance").click(function() {
	// body...
	var formData = new FormData(); 
	formData.append("orderId",$("#AliPay").attr("name"));
	// body...
	$.ajax({
		url: "/o2o/customer/orderInfo/payforbalance.do",
		type: "POST",
		data:formData,
		dataType : "json",
		async:false,
		processData: false,  // 告诉jQuery不要去处理发送的数据
		contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
		success: function(response,status,xhr){
			if(response.status==1){
				alert(response.msg);
			}else{
				alert("扣款成功");
			}
		} 
	});
})

function payMoney(Id) {
	var formData = new FormData(); 
	formData.append("orderId",Id);
	// body...
	$.ajax({
		url: "/o2o/customer/orderInfo/pay.do",
		type: "POST",
		data:formData,
		dataType : "html",
		async:false,
		processData: false,  // 告诉jQuery不要去处理发送的数据
		contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
		success: function(response,status,xhr){
			$('#payBody').html(response);
			$('#payModal').modal('show');
		} 
	});
}



function JudgeState(state,qae,c) {
	// body...
	if(state==1){
		return "待接单";
	}else if(state==0){
		return "未付款";
	}else if(state==11){
		return "已取消";
	}else if(state==10){
		return "协商中";
	}else if(state==2){
		if(c==1){
			return "待验收";
		}else{
			return "制作中";
		}
	}else if(state==3){
		if(qae==1){
			return "待审核接单";
		}else{
			return "待验收";
		}
	}else if(state==4){
		return "待审核";
	}else if(state==5){
		return "待验收";
	}else if(state==6){
		return "待确认完工";
	}else if(state==7){
		return "已确认完工";
	}else if(state==8){
		return "待制作工程师确认扣款";
	}else if(state==9){
		return "待审核工程师确认扣款";
	}
}
