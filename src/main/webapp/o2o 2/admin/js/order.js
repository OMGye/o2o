var pageNum;
var firstCategory=false;
var orderQae=false;
var state=false;

var OrderId;
var OrderFile;
var OrderQae;
var OrderState;
var EngineerName;
var EngineerCheckName;
var EngineerId;
var EngineerCheckId;
function withcheckorder() {
	// body...

		$.ajax({
			url: '/o2o/admin/orderInfo/adminchecklist.do',
			method: 'get',
			success: function(v) {
				if(v.status==1){
					alert(v.msg);  
				}else{
					if(!v.data.hasNextPage){
						$(".next").addClass("disabled");
					}else{
						console.log("xx");
						$(".next").removeClass("disabled");
						$(".next a").attr("href","order.html?pageNum="+(pageNum+1));
					}
					if(!v.data.hasPreviousPage){
						$(".prev").addClass("disabled");
					}else{
						$(".prev").removeClass("disabled");
						$(".prev a").attr("href","order.html?pageNum="+(pageNum-1));
					}
					$("#OrderList").html("");

					for (var i =  0; i < v.data.list.length; i++) {
						var val = v.data.list[i];
						if(!val.engineerCheckId){
							val.engineerCheckId="无审核人";
						}
						if(!val.engineerId){
							val.engineerId="无制作人";
						}
						if(val.orderQae=="1"){
							val.orderQae="是";
						}else{
							val.orderQae="否";
						}
						val.orderState=JudgeState(val.orderState);
						$("#OrderList").append(
							'<tr><td>'+val.orderId+'</td><td>'+val.orderFirstCategory+'</td><td>'+val.customerId+'</td><td>'+val.engineerId+'</td><td>'+val.orderQae+'</td><td>'+val.orderState+'</td><td>'+val.engineerCheckId+'</td><td>'+changeTime(val.createTime)+'</td><td><div class="btn btn-group"><a href="orderInfo.html?Id='+val.orderId+'" class="btn btn-default">详情</a><a onclick="checkit('+val.orderId+',0)" class="btn btn-danger">过审</a><a onclick="back5('+val.orderId+')" class="btn btn-warning">驳回</a><a href="'+val.orderFile+'" class="btn btn-success">附件</a></td></tr>'
						);
					}
					
				}
			}
		});
	
		
}

function back5(id) {
	// body...
	var fd = new FormData();
		fd.append("orderId",id);
		$.ajax({
			url: '/o2o/admin/orderInfo/admincheckrefuse.do',
			method: 'post',
			data: fd,
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
            processData: false,  // 告诉jQuery不要去处理发送的数据
			success: function(data) {
				if(data.status==0){
					alert("操作成功");
					window.location.reload();
				}else{
					alert(data.msg);
				}
			}
		});	
}

	function checkit(id,statu) {
		// body...
			var fd = new FormData();
			fd.append("orderId",id);
			$.ajax({
				url: '/o2o/admin/orderInfo/admincheck.do',
				method: 'post',
				data: fd,
	            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
	            processData: false,  // 告诉jQuery不要去处理发送的数据
				success: function(data) {
					if(data.status==0){
						alert("操作成功");
						window.location.reload();
					}else{
						alert(data.msg);
					}
				}
			});
	}

	$("#helpcheckerback").click(function() {
		// body...
		var id = getPar("Id");
		var fd = new FormData();
		fd.append("orderId",id);
		$.ajax({
			url: '/o2o/admin/orderInfo/adminhelpreturntoenginner.do',
			method: 'post',
			data: fd,
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
            processData: false,  // 告诉jQuery不要去处理发送的数据
			success: function(data) {
				if(data.status==0){
					alert("操作成功");
					window.location.reload();
				}else{
					alert(data.msg);
				}
			}
		});	
	})

	$("#helpfinfishorder").click(function() {
		// body...
		var id = getPar("Id");
		var fd = new FormData();
		fd.append("orderId",id);
		$.ajax({
			url: '/o2o/admin/orderInfo/adminhelpfinishorder.do',
			method: 'post',
			data: fd,
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
            processData: false,  // 告诉jQuery不要去处理发送的数据
			success: function(data) {
				if(data.status==0){
					alert("操作成功");
					window.location.reload();
				}else{
					alert(data.msg);
				}
			}
		});	
	})

	$("#helpcancelorder").click(function() {
		// body...
		var id = getPar("Id");
		var fd = new FormData();
		fd.append("orderId",id);
		$.ajax({
			url: '/o2o/admin/orderInfo/adminHelpCancleOrder.do',
			method: 'post',
			data: fd,
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
            processData: false,  // 告诉jQuery不要去处理发送的数据
			success: function(data) {
				if(data.status==0){
					alert("操作成功");
					window.location.reload();
				}else{
					alert(data.msg);
				}
			}
		});	
	})

	$("#helpcustomerbackcheck").click(function() {
		// body...
		var id = getPar("Id");
		var fd = new FormData();
		fd.append("orderId",id);
		$.ajax({
			url: '/o2o/admin/orderInfo/adminhelpreturntoqaeenginner.do',
			method: 'post',
			data: fd,
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
            processData: false,  // 告诉jQuery不要去处理发送的数据
			success: function(data) {
				if(data.status==0){
					alert("操作成功");
					window.location.reload();
				}else{
					alert(data.msg);
				}
			}
		});	
	})

	$("#helpcustomerback").click(function() {
		// body...
		var id = getPar("Id");
		var fd = new FormData();
		fd.append("orderId",id);
		$.ajax({
			url: '/o2o/admin/orderInfo/adminhelpcustomerreturntoenginner.do',
			method: 'post',
			data: fd,
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
            processData: false,  // 告诉jQuery不要去处理发送的数据
			success: function(data) {
				if(data.status==0){
					alert("操作成功");
					window.location.reload();
				}else{
					alert(data.msg);
				}
			}
		});	
	})

	$("#helpcheck").click(function() {
		// body...
		var id = getPar("Id");
		var fd = new FormData();
		fd.append("orderId",id);
		$.ajax({
			url: '/o2o/admin/orderInfo/adminhelpcheck.do',
			method: 'post',
			data: fd,
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
            processData: false,  // 告诉jQuery不要去处理发送的数据
			success: function(data) {
				if(data.status==0){
					alert("操作成功");
					window.location.reload();
				}else{
					alert(data.msg);
				}
			}
		});	
	})

	$("#backorder").click(function() {
		// body...
		var id = getPar("Id");
		var fd = new FormData();
		fd.append("orderId",id);
		$.ajax({
			url: '/o2o/admin/orderInfo/adminhelpreturnorder.do',
			method: 'post',
			data: fd,
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
            processData: false,  // 告诉jQuery不要去处理发送的数据
			success: function(data) {
				if(data.status==0){
					alert("操作成功");
					window.location.reload();
				}else{
					alert(data.msg);
				}
			}
		});	
	})

function getChatInfo(OrderId) {
	// body...
	$.ajax({
		url: '/o2o/admin/orderInfo/listansorque.do?orderId='+OrderId+"&pageNum=1&pageSize=1000",
		method: 'get',
		success: function(data) {
			if(data.status==0){
				for (var i = 0; i <= data.data.list.length - 1; i++) {
					var v = data.data.list[i];
					var regex = /^http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- ./?%&=]*)?$/;
					if(regex.test(v.orderAnsqueContent)){
								if(v.userType==0){
									$("#chat").append(
										'<div class="wrap card" onclick="javascript:window.open('+v.orderAnsqueContent+')"><div class="card-header"><span class="badge badge-info">客:</span></div><div class="card-body wrap">'+v.orderAnsqueContent+'<a class="change btn btn-warning" href="'+v.orderAnsqueContent+'">点我下载</a></div></div>'
										)
								}else{
									$("#chat").append(
										'<div class="wrap card" onclick="javascript:window.open('+v.orderAnsqueContent+')"><div class="card-header"><span class="badge badge-warning">工:</span></div><div class="card-body wrap">'+v.orderAnsqueContent+'<a class="change btn btn-warning" href="'+v.orderAnsqueContent+'">点我下载</a></div></div>'
										)
								}
					}else{
							if(v.userType==0){
								$("#chat").append(
										'<div class="wrap card" onclick="javascript:window.open('+v.orderAnsqueContent+')"><div class="card-header"><span class="badge badge-info">客:</span></div><div class="card-body wrap">'+v.orderAnsqueContent+'</div></div>'
									)
							}else{
								$("#chat").append(
										'<div class="wrap card" onclick="javascript:window.open('+v.orderAnsqueContent+')"><div class="card-header"><span class="badge badge-warning">工:</span></div><div class="card-body wrap">'+v.orderAnsqueContent+'</div></div>'
									)
							}
					}
				}
				var div = document.getElementById('chat');
    			div.scrollTop = div.scrollHeight;
			}else{
			}
		}
	});
}

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

function getAllorder(pageNum,firstCategory,orderQae,state) {
	// body...
	console.log(pageNum);
	console.log(firstCategory);
	console.log(orderQae);
	console.log(state);
	var url='/o2o/admin/orderInfo/list.do?pageNum='+pageNum+'&pageSize='+20;
	if(state!=false){
		url = url+'&state='+state;
	}
	if(firstCategory!=false){
		url = url+'&firstCategory='+firstCategory; 
	}
	if(orderQae!=false){
		url = url+'&orderQae='+orderQae;
	}
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
					if(!v.data.hasNextPage){
						$(".next").addClass("disabled");
					}else{
						console.log("xx");
						$(".next").removeClass("disabled");
						$(".next a").attr("href","order.html?pageNum="+(pageNum+1));
					}
					if(!v.data.hasPreviousPage){
						$(".prev").addClass("disabled");
					}else{
						$(".prev").removeClass("disabled");
						$(".prev a").attr("href","order.html?pageNum="+(pageNum-1));
					}
					for (var i =  0; i < v.data.list.length; i++) {
						var val = v.data.list[i];
						if(!val.engineerCheckId){
							val.engineerCheckId="无审核人";
						}
						if(!val.engineerId){
							val.engineerId="无制作人";
						}
						if(val.orderQae=="1"){
							val.orderQae="是";
						}else{
							val.orderQae="否";
						}
						val.orderState=JudgeState(val.orderState);
						$("#OrderList").append(
							'<tr><td>'+val.orderId+'</td><td>'+val.orderFirstCategory+'</td><td>'+val.customerFileRealName+'</td><td>'+val.customerId+'</td><td>'+val.engineerId+'</td><td>'+val.orderQae+'</td><td>'+val.orderState+'</td><td>'+val.engineerCheckId+'</td><td>'+changeTime(val.createTime)+'</td><td><div class="btn btn-group"><a href="orderInfo.html?Id='+val.orderId+'" class="btn btn-default">详情</a></td></tr>'
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


function JudgeState(state) {
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
		return "制作中";
	}else if(state==3){
			return "二期准备";
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


function cs(id,type) {
	// body...
			var fd= new FormData();
			fd.append("orderId",id);
			fd.append("type",type);
			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/orderInfo/changestate.do',
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

function getOrderCreate(Id) {
	// body...
	$.ajax({
			// la URL para la petición
			url : '/o2o/admin/incomeInfo/list.do?orderId='+Id,
			// especifica si será una petición POST o GET
			type : 'GET',
			// el tipo de información que se espera de respuesta
			dataType : 'json',

			processData: false,  // 告诉jQuery不要去处理发送的数据
            
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头

			// código a ejecutar si la petición es satisfactoria;
			// la respuesta es pasada como argumento a la función
			success : function(v) {
				var v=v.data.list;
				if(v.length==0){
					$("#Pay").html("待完工");
				}else{
					$("#Pay").html(v[0].incomeMoney);
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

function getOrderById(Id) {
	// body...
	$.ajax({
			// la URL para la petición
			url : '/o2o/admin/orderInfo/getorderbyid.do?orderId='+Id,
			// especifica si será una petición POST o GET
			type : 'GET',
			// el tipo de información que se espera de respuesta
			dataType : 'json',

			processData: false,  // 告诉jQuery不要去处理发送的数据
            
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头

			// código a ejecutar si la petición es satisfactoria;
			// la respuesta es pasada como argumento a la función
			success : function(v) {
				var v=v.data;
				//赋值
				OrderId=v.orderId;	
				OrderFile=v.orderFile;
				OrderQae=v.orderQae;
				OrderState=v.orderState;
				EngineerName=v.engineerName;
				EngineerCheckName=v.engineerCheckName;
				EngineerCheckId=v.engineerCheckId;
				EngineerId=v.engineerId;

				$("#engineerName").html(v.engineerId);
				$("#engineerCheckName").html(v.engineerCheckId);
				//待接单阶段
				if(v.orderState==1){
					$("#OrderState").html("待接单");
				}else if(v.orderState==11){
					//超时取消阶段
					$("#OrderState").html("已取消");
				}else if(v.orderState==2){
					if(v.refuseDec!=null){
						$(".BackDec").show();
						$("#BackDec").html(v.refuseDec);
					}
					$("#OrderState").html("开发中");
					$(".FinishFile").show();
					$("#FinishFile").html("待上传完工附件");
				}else if(v.orderState==3){
					if(v.orderQae==1){
						$("#OrderState").html("待审核工程师接单");
						$(".FinishFile").show();
						$("#FinishFile").html("<a class='btn btn-success' style='color:white' href='"+OrderFile+"' target='_blank'>下载完工附件</a>");
					}else{
						$("#OrderState").html("待验收");
						$(".FinishFile").show();
						$("#FinishFile").html("<a style='color:white' class='btn btn-success' href='"+OrderFile+"' target='_blank'>下载完工附件</a>");
					}
				}else if(v.orderState==4){
					$("#OrderState").html("待审核工程师审核");
					$(".FinishFile").show();
					$("#FinishFile").html("<a style='color:white' class='btn btn-success' href='"+OrderFile+"' target='_blank'>下载完工附件</a>");
				}else if(v.orderState==5){
						$(".FinishFile").show();
						$("#FinishFile").html("<a style='color:white' class='btn btn-success' href='"+OrderFile+"' target='_blank'>下载完工附件</a>");
				}else if(v.orderState==6){
					$("#OrderState").html("待确认完工");
					$(".FinishFile").show();
					$("#FinishFile").html("<a style='color:white' class='btn btn-success' href='"+OrderFile+"' target='_blank'>下载完工附件</a>");
				}else if(v.orderState==7){
					$("#OrderState").html("已确认完工");
					$(".FinishFile").show();
					$("#FinishFile").html("<a class='btn btn-success' href='"+OrderFile+"' style='color:white' target='_blank'>下载完工附件</a>");
				}else if(v.orderState==8){
					$("#OrderState").html("发起扣款中，等待制作工程师确认");
					$(".FinishFile").show();
					$("#FinishFile").html("<a class='btn btn-success' href='"+OrderFile+"' style='color:white' target='_blank'>下载完工附件</a>");
					$("#ReductRank").html(v.orderDeductRank+"级扣款");
					$(".ReductRank").show();
					$("#ReductDec").html(v.orderDeductDec);
					$(".ReductDec").show();
				}else if(v.orderState==9){
					$("#OrderState").html("发起扣款中，制作工程师已确认，等待审核工程师确认");
					$(".FinishFile").show();
					$("#FinishFile").html("<a class='btn btn-success' href='"+OrderFile+"' style='color:white' target='_blank'>下载完工附件</a>");
					$("#ReductRank").html(v.orderDeductRank+"级扣款");
					$(".ReductRank").show();
					$("#ReductDec").html(v.orderDeductDec);
					$(".ReductDec").show();
				}else if(v.orderState==10){
					$("#OrderState").html("协商中");
					$(".FinishFile").show();
					$("#FinishFile").html("<a class='btn btn-success' href='"+OrderFile+"' style='color:white' target='_blank'>下载完工附件</a>");
					$("#ReductRank").html(v.orderDeductRank+"级扣款");
					$(".ReductRank").show();
					$("#ReductDec").html(v.orderDeductDec);
					$(".ReductDec").show();
				}
				$("#OrderDec").html(v.orderDec);
				$("#firstCategory").val(v.orderFirstCategory);
				$("#secondRank").val(v.orderSecondCategory);
				if(v.orderQae==1){
					 $("#thirdCategory option[value='QAE']").attr("selected","selected");
				}else{
					 $("#engineerQaePrice").hide();
				}
				if(v.orderMi==1){
					 $("#thirdCategory option[value='MI']").attr("selected","selected");
				}
				$("#priceToger").val(v.priceTogetherNum);
				$("#basicLayer").val(v.basicLayer);
				if(v.orderRush==1){
					v.otherParamInfo="加急,"+v.otherParamInfo;
				}
				$("#otherParam").html(v.otherParamInfo);
				$(".totalPrice").html(v.orderPrice);
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
			url: '/o2o/admin/orderInfo/getByOrderIdLike.do?orderId='+v+"&pageNum=1&pageSize=20",
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
						$("#OrderList").append(
							'<tr><td>'+val.orderId+'</td><td>'+val.orderFirstCategory+'</td><td>'+val.customerName+'</td><td>'+val.engineerName+'</td><td>'+val.orderQae+'</td><td>'+val.engineerCheckName+'</td><td>'+changeTime(val.createTime)+'</td><td><a href="orderInfo.html?Id='+val.orderId+'" class="btn btn-default">详情</a><a onclick="del('+val.orderId+',1)" class="btn btn-warning">删除</a></td></tr>'
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
		$("#OrderList").html("");
		var firstCategory=false;
		var orderQae=false;
		var state=false;
		getAllorder(pageNum,firstCategory,orderQae,state);
	}
});

function changeSearchType(t,w) {
	// body...
	$("#searchByUser").attr("name",t);
	$("#SearchType").html(w);
}

$("#searchByUser").on("input propertychange",function(){
	// console.log($(this).attr('id'));
	var v = $(this).val();
	var t = $(this).attr("name");
	if(!t){
		t=1;
	}
	if(v!=""){
		$.ajax({
			url: '/o2o/admin/orderInfo/getByOtherIdLike.do?id='+v+"&type="+t,
			method: 'get',
			success: function(data) {
				$("#OrderList").html("");
				if(data.status==0){
					for (var i = data.data.list.length - 1; i >= 0; i--) {
						var val = data.data.list[i];
						if(!val.engineerCheckId){
							val.engineerCheckId="无审核人";
						}
						if(!val.engineerId){
							val.engineerId="无制作人";
						}
						if(val.orderQae=="1"){
							val.orderQae="是";
						}else{
							val.orderQae="否";
						}
						val.orderState=JudgeState(val.orderState);
						$("#OrderList").append(
							'<tr><td>'+val.orderId+'</td><td>'+val.orderFirstCategory+'</td><td>'+val.customerId+'</td><td>'+val.engineerId+'</td><td>'+val.orderQae+'</td><td>'+val.orderState+'</td><td>'+val.engineerCheckId+'</td><td>'+changeTime(val.createTime)+'</td><td><div class="btn btn-group"><a href="orderInfo.html?Id='+val.orderId+'" class="btn btn-default">详情</a><a onclick="cs('+val.orderId+',0)" class="btn btn-danger">订单完成</a><a onclick="cs('+val.orderId+',1)" class="btn btn-success">订单取消</a></div></td></tr>'
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
		$("#OrderList").html("");
		var firstCategory=false;
		var orderQae=false;
		var state=false;
		getAllorder(pageNum,firstCategory,orderQae,state);
	}
});

$("#toushu").click(function(){
		var file = $('#choosefile')[0].files[0];
	console.log(file);
	if(file){
		var fd = new FormData();
		fd.append("orderId",getPar("Id"));
		fd.append("upload_file",file);
		$.ajax({
			url: '/o2o/admin/orderInfo/adminhelpuploadfile.do',
			method: 'post',
			data: fd,
			processData:false,
			contentType:false,
			success: function(data) {
				if(data.status==0){
					alert("操作成功");
					window.location.reload();
				}else{
					alert(data.msg);
				}
			},
		});
		return;
	}	
		});

$("#toushu2").click(function(){
	var fd = new FormData();
	       	fd.append("orderId",getPar("Id"));
	       	if($("#customerPrice").val()!="")
	       	{
	       		var p1=$("#customerPrice").val();
	       		fd.append("customerPrice",$("#customerPrice").val());
	       	}else{
	       		alert("请输入用户分配金额");
	       		return;
	       	}
	       	if($("#engineerPrice").val()!=""){
	       		var p2=$("#engineerPrice").val();
		       	fd.append("engineerPrice",$("#engineerPrice").val());
	       	}else{
	       		alert("请输入制作工程师分配金额");
	       		return;
	       	}
	       	if($("#engineerQaePrice").val()==""){
	       		var p3=0;
		       	fd.append("engineerQaePrice",0);
	       	}else{
	       		var p3=$("#engineerQaePrice").val();
		       	fd.append("engineerQaePrice",$("#engineerQaePrice").val());
	       	}
	       	var total = parseInt(p1)+ parseInt(p2)+ parseInt(p3);
	       	var t2=parseInt($(".totalPrice").html());
	       	if(total>t2){
				alert("请输入适当的数字 ");
	       		return;
	       	}
			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/orderInfo/dealorder.do',

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
});