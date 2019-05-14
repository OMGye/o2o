
var OrderId;
var OrderFile;
var OrderQae;
var OrderState;
var EngineerName;
var EngineerCheckName;
var EngineerId;
var EngineerCheckId;
var int=self.setInterval("getChatInfo()",60000);
$("#chooseFile").click(function() {
	// body...
	$("#choosefile").click();
})

function getChatInfo() {
	// body...
	if(getCookie("userType")==0){
		var info="customer";
	}else{
		var info="engineer";
	}
	var fd = new FormData();
	fd.append("orderAnsqueContent",$("#chatInfo").val());
	fd.append("orderId",OrderId);
	$.ajax({
		url: '/o2o/'+info+'/orderInfo/listansorque.do?orderId='+OrderId+"&pageNum=1&pageSize=1000",
		method: 'get',
		success: function(data) {
			if(data.status==0){
				$("#chat").html("")
				for (var i = 0; i <= data.data.list.length - 1; i++) {
					var v = data.data.list[i];
					var regex = /^http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- ./?%&=]*)?$/;
					if(regex.test(v.orderAnsqueContent)){
							if(getCookie("userType")==0){
								if(v.userType==0){
									$("#chat").append(
										'<div class="wrap card" onclick="javascript:window.open('+v.orderAnsqueContent+')"><div class="card-header"><span class="badge badge-info">客:</span></div><div class="card-body">'+v.orderAnsqueContent+'<a class="change btn btn-warning" href="'+v.orderAnsqueContent+'">点我下载</a></div></div>'
										)
									// $("#chat").append(
										// '<div style="width: 100px;display:block;word-break: break-all;word-wrap: break-word;text-align: right;"><a style="margin-top:5px" class="change btn btn-warning" href="'+v.orderAnsqueContent+'">'+v.orderAnsqueContent+'</a>:客</div>'
										// )
								}else{
									$("#chat").append(
										'<div class="wrap card" onclick="javascript:window.open('+v.orderAnsqueContent+')"><div class="card-header"><span class="badge badge-warning">工:</span></div><div class="card-body">'+v.orderAnsqueContent+'<a class="change btn btn-warning" href="'+v.orderAnsqueContent+'">点我下载</a></div></div>'
										)
								}
							}else{
								if(v.userType==0){
									$("#chat").append(
										'<div class="wrap card" onclick="javascript:window.open('+v.orderAnsqueContent+')"><div class="card-header"><span class="badge badge-info">客:</span></div><div class="card-body">'+v.orderAnsqueContent+'<a class="change btn btn-warning" href="'+v.orderAnsqueContent+'">点我下载</a></div></div>'
										)
								}else{
									$("#chat").append(
										'<div class="wrap card" onclick="javascript:window.open('+v.orderAnsqueContent+')"><div class="card-header"><span class="badge badge-warning">工:</span></div><div class="card-body">'+v.orderAnsqueContent+'<a class="change btn btn-warning" href="'+v.orderAnsqueContent+'">点我下载</a></div></div>'
										)
								}
							}	
					}else{
						if(getCookie("userType")==0){
							if(v.userType==0){
								$("#chat").append(
										'<div class="wrap card" onclick="javascript:window.open('+v.orderAnsqueContent+')"><div class="card-header"><span class="badge badge-info">客:</span></div><div class="card-body">'+v.orderAnsqueContent+'</div></div>'
										)
							}else{
								$("#chat").append(
										'<div class="wrap card" onclick="javascript:window.open('+v.orderAnsqueContent+')"><div class="card-header"><span class="badge badge-warning">工:</span></div><div class="card-body">'+v.orderAnsqueContent+'</div></div>'
										)
							}
						}else{
							if(v.userType==0){
								$("#chat").append(
										'<div class="wrap card" onclick="javascript:window.open('+v.orderAnsqueContent+')"><div class="card-header"><span class="badge badge-info">客:</span></div><div class="card-body">'+v.orderAnsqueContent+'</div></div>'
										)
							}else{
								$("#chat").append(
										'<div class="wrap card" onclick="javascript:window.open('+v.orderAnsqueContent+')"><div class="card-header"><span class="badge badge-warning">工:</span></div><div class="card-body">'+v.orderAnsqueContent+'</div></div>'
										)
							}
						}
					}
				}
				var div = document.getElementById('chat');
    			div.scrollTop = div.scrollHeight;
			}else{
				// alert("系统错误");
				// window.location.href="login.html";
			}
		}
	});
}

$("#sendChat").click(function(argument) {
	var file = $('#choosefile')[0].files[0];
	console.log(file);
	if(file){
		var fd = new FormData();
		fd.append("orderId",OrderId);
		fd.append("upload_file",file);

		if(getCookie("userType")==0){
			var info = "customer";
		}else{
			var info = "engineer";
		}
		$.ajax({
			url: '/o2o/'+info+'/orderInfo/uploadansorque.do',
			method: 'post',
			data: fd,
			processData:false,
			contentType:false,
			success: function(data) {
				if(data.status==0){
					alert(data.data);
					window.location.reload();
				}else{
					alert("系统错误");
					window.location.href="login.html";
				}
			},
		});
		return;
	}
	if(getCookie("userType")==0){
		var info="customer";
		var i ="客";
		var b ="info";
	}else{
		var info="engineer";
		var i ="工";
		var b ="warning";
	}
	var fd = new FormData();
	if($("#chatInfo").val()){
		fd.append("orderAnsqueContent",$("#chatInfo").val());
	}else{
		alert("请输入内容");
		return;
	}
	fd.append("orderId",OrderId);
	$.ajax({
		url: '/o2o/'+info+'/orderInfo/addansorque.do',
		method: 'post',
		data: fd,
		processData:false,
		contentType:false,
		success: function(data) {
			if(data.status==0){
				$("#chat").append(
					$("#chat").append(
										'<div class="wrap card" onclick="javascript:window.open('+$("#chatInfo").val()+')"><div class="card-header"><span class="badge badge-'+b+'">'+i+':</span></div><div class="card-body">'+$("#chatInfo").val()+'</div></div>'
										)
					// '<div style="display: block;text-align: right;"><button style="margin-top:5px" class="btn btn-warning">'+$("#chatInfo").val()+'</button>:工</div>'
				)
				$("#chatInfo").val("");
				var div = document.getElementById('chat');
    			div.scrollTop = div.scrollHeight;				
			}else{
				alert(data.msg);
				// window.location.href="login.html";
			}
		}
	});
});

function getorderDetail(orderId) {
	// body...
			if(getCookie("userType")==0){
				var info="customer";
			}else{
				var info="engineer";
			}
			$.ajax({
			// la URL para la petición
			url : '/o2o/'+info+'/orderInfo/getorderbyid.do?orderId='+orderId,
	
			// la información a enviar
			// (también es posible utilizar una cadena de datos)
			type : 'get',
	
			// el tipo de información que se espera de respuesta
			dataType : 'json',
	
			// código a ejecutar si la petición es satisfactoria;
			// la respuesta es pasada como argumento a la función
			success : function(json) {
				var v=json.data;
				//赋值
				OrderId=v.orderId;	
				OrderFile=v.orderFile;
				OrderQae=v.orderQae;
				OrderState=v.orderState;
				EngineerName=v.engineerName;
				EngineerCheckName=v.engineerCheckName;
				EngineerCheckId=v.engineerCheckId;
				EngineerId=v.engineerId;
				
				if(getCookie("userType")==1){
					if(getCookie("engineerName")==EngineerCheckName){
						//审核工程
						var bp=v.orderQaePrice;
						$("#totalPrice").html((bp-getCC(bp)).toFixed(2));
					}else{
						if(getPar("qae")==1){
							var bp=v.orderQaePrice;
							$("#totalPrice").html((bp-getCC(bp)).toFixed(2));
						}else{
							var bp=v.orderPrice-v.orderQaePrice;
							$("#totalPrice").html((bp-getCC(bp)).toFixed(2));
						}
					}
				}else{
					$("#totalPrice").html(v.orderPrice);
				}

				$("#itemFile").html(
					'<a href="'+v.orderCustomerFile+'" class="btn btn-warning">下载</a>'
					);

				if(v.engineerId){
					$("#engineerName").html("编号"+v.engineerId);
				}else{
					$("#engineerName").html("暂时空缺");
				}
				if(v.engineerCheckId){
					$("#engineerCheckName").html("编号"+v.engineerCheckId);
				}else{
					$("#engineerCheckName").html("暂时空缺");
				}
				if(v.customerFileRealName){
					$("#customerFileRealName").html("型号"+v.customerFileRealName);
				}else{
					$("#customerFileRealName").html("暂时空缺");
				}
				$("#oid").html("编号"+v.orderId);
				//待接单阶段
				if(v.orderState==1){
					$(".chatView").hide();
					$("#OrderState").html("待接单");
					if(getCookie("userType")==0){
						$("#backOrder").show();
						$("#backOrder").click(function(argument) {
							var fd = new FormData();
							fd.append("orderId",OrderId);
							$.ajax({
								url: '/o2o/customer/orderInfo/ordercancle.do',
								method: 'post',
								data:fd,
								async : false,
								processData:false,
								contentType:false,
								success: function(data) {
									if(data.status==0){
										alert("取消成功");
										window.location.go(-1);
									}else{
										alert(data.msg);
				// window.location.href="login.html";
									}
								},
							});
						});
					}else{
						$("#getOrder").show();
					}
				}else if(v.orderState==11){
					//超时取消阶段
					$(".chatView").hide();
					$("#OrderState").html("超时取消");
				}else if(v.orderState==2){
					getChatInfo();
					if(v.adminCheck==1){
						if(getCookie("userType")==0){
							$("#OrderState").html("制作中");
						}else{
							if(getCookie("engineerName")==EngineerName){
								$("#OrderState").html("待验收");
							}
						}
					}else{
						if(getCookie("userType")==0){
							// $("#backOrder").show();
						}else{
							if(getCookie("engineerName")==EngineerName){
							    $("#uploadprogress").show();
								$("#UploadFile").show();
								// $("#backOrder").show();
								$("#backOrder").click(function(argument) {
									var fd = new FormData();
									fd.append("orderId",OrderId);
									$.ajax({
										url: '/o2o/engineer/orderInfo/engineercancle.do',
										method: 'post',
										data:fd,
										processData:false,
										contentType:false,
										success: function(data) {
											if(data.status==0){
												window.location.reload();
											}else{
												alert(data.msg);
					// window.location.href="login.html";
											}
										},
									});
								});
							}
						}
						if(v.refuseDec!=null){
							$(".BackDec").show();
							$("#BackDec").html(v.refuseDec);
						}
						$("#OrderState").html("开发中");
						$(".FinishFile").show();
						$("#FinishFile").html("待上传完工附件");	
					}
					
				}else if(v.orderState==3){
					getChatInfo();

					if(v.orderQae==1){
						
						$("#OrderState").html("待审核工程师接单");
						$(".FinishFile").show();
						$("#FinishFile").html("<a class='btn btn-success' style='color:white' href='"+OrderFile+"' target='_blank'>下载完工附件</a>");
					}else{
						$("#OrderState").html("待验收");
						$(".FinishFile").show();
						$("#FinishFile").html("<button class='btn btn-success' id='DownLoadFile'>下载完工附件</button>");
					}
					
					if(getCookie("userType")==0){
						$("#DownLoadFile").click(function(argument) {
							console.log(OrderFile);
							$.ajax({
								url: '/o2o/customer/orderInfo/receiveorder.do?orderId='+OrderId,
								method: 'get',
								async : false,
								success: function(data) {
									if(data.status==0){
										window.open(OrderFile);
										window.location.reload();
									}else{
										alert(data.msg);
				// window.location.href="login.html";
									}
								},
							});
						});
					}else{
						if(getCookie("engineerName")!=EngineerName){
							$("#getOrder").show();
						}
						$(".FinishFile").show();
						$("#FinishFile").html("<a style='color:white' class='btn btn-success' href='"+OrderFile+"' target='_blank'>下载完工附件</a>");
					}
				}else if(v.orderState==4){
					getChatInfo();

					$("#OrderState").html("待审核工程师审核");
					$(".FinishFile").show();
					$("#FinishFile").html("<a style='color:white' class='btn btn-success' href='"+OrderFile+"' target='_blank'>下载完工附件</a>");
					if(getCookie("userType")==0){

					}else{
						if(getCookie("engineerName")==EngineerCheckName){
							// $("#backOrder").show();
							$("#backOrder").click(function(argument) {
								$.ajax({
									url: '/engineer/orderInfo/engineerqaecancle.do?orderId='+OrderId,
									method: 'get',
									async : false,
									success: function(data) {
										if(data.status==0){
											window.location.reload();
										}else{
											alert(data.msg);
				// window.location.href="login.html";
										}
									},
								});
							});
							$("#BackFile").show();
							$("#BackFile").click(function(argument) {
								info=prompt("请输入打回理由?");
								var fd= new FormData();
								fd.append("orderId",OrderId);
								fd.append("refuseDec",info);
								fd.append("state",0);
								$.ajax({
									url: '/o2o/engineer/orderInfo/check.do',
									method: 'post',
									data:fd,
									processData:false,
									contentType:false,
									success: function(data) {
										if(data.status==0){
											alert("驳回成功");
											window.location.reload();
										}else{
											alert(data.msg);
				// window.location.href="login.html";
										}
									},
								});
							});
							$("#PassOrder").show();
							$("#PassOrder").click(function(argument) {
								var fd= new FormData();
								fd.append("orderId",OrderId);
								fd.append("state",1);
								$.ajax({
									url: '/o2o/engineer/orderInfo/check.do',
									method: 'post',
									data:fd,
									processData:false,
									contentType:false,
									success: function(data) {
										if(data.status==0){
											alert("审核成功");
											window.location.reload();
										}else{
											alert(data.msg);
				// window.location.href="login.html";
										}
									},
								});
							});
						}
					}
				}else if(v.orderState==5){
					getChatInfo();

					$("#OrderState").html("待验收");
					if(getCookie("userType")==0){
						$(".FinishFile").show();
						$("#FinishFile").html("<button class='btn btn-success' id='DownLoadFile'>下载完工附件</button>");
						$("#DownLoadFile").click(function(argument) {
							console.log(OrderFile);
							$.ajax({
								url: '/o2o/customer/orderInfo/receiveorder.do?orderId='+OrderId,
								method: 'get',
								async : false,
								success: function(data) {
									if(data.status==0){
										window.open(OrderFile);
										window.location.reload();
									}else{
										alert(data.msg);
				// window.location.href="login.html";
									}
								},
							});
						});
					}else{
						$(".FinishFile").show();
						$("#FinishFile").html("<a style='color:white' class='btn btn-success' href='"+OrderFile+"' target='_blank'>下载完工附件</a>");
					}
				}else if(v.orderState==6){
					getChatInfo();

					getKoukuan();
					$("#OrderState").html("待确认完工");
					$(".FinishFile").show();
					if(getCookie("userType")==0){
						$("#FinishFile").html("<a class='btn btn-success' href='"+OrderFile+"' target='_blank' style='color:white'>下载完工附件</a><br><br><button class='btn btn-danger' id='backFile'>退回附件</button>");
						$("#FinishOrder").show();
						$("#orderDeduct").show();
						$("#FinishOrder").click(function(argument) {
							var fd= new FormData();
							fd.append("orderId",OrderId);
							$.ajax({
								url: '/o2o/customer/orderInfo/comfirmorder.do?orderId='+OrderId,
								method: 'post',
								data: fd,
								processData:false,
								contentType:false,
								success: function(data) {
									if(data.status==0){
										alert("确认成功");
										window.location.reload();
									}else{
										alert(data.msg);
				// window.location.href="login.html";
									}
								},
							});
						});
						$("#orderDeduct2").click(function(argument) {
							var fd= new FormData();
							fd.append("orderId",OrderId);
							fd.append("priceDeductId",$("#deductGrade").val());
							fd.append("orderDeductDec",$("#deductInfo").val());
							$.ajax({
								url: '/o2o/customer/orderInfo/orderdeduct.do',
								method: 'post',
								data:fd,
								processData:false,
								contentType:false,
								success: function(data) {
									if(data.status==0){
										alert("发起扣款成功，等待工程师确认");
										window.location.reload();
									}else{
										alert(data.msg);
				// window.location.href="login.html";
									}
								},
							});
						});
						$("#backFile").click(function(argument) {
							info=prompt("请输入打回理由?");
							var fd= new FormData();
							fd.append("orderId",OrderId);
							fd.append("refuseDec",info);
							$.ajax({
								url: '/o2o/customer/orderInfo/refusetoengineer.do',
								method: 'post',
								data:fd,
								processData:false,
								contentType:false,
								success: function(data) {
									if(data.status==0){
										alert("驳回成功");
										window.location.reload();
									}else{
										alert(data.msg);
				// window.location.href="login.html";
									}
								},
							});
						});
					}else{
						$("#FinishFile").html("<a style='color:white' class='btn btn-success' href='"+OrderFile+"' target='_blank'>下载完工附件</a>");
					}
				}else if(v.orderState==7){
					getChatInfo();

					$("#OrderState").html("已确认完工");
					$(".FinishFile").show();
					$("#FinishFile").html("<a class='btn btn-success' href='"+OrderFile+"' style='color:white' target='_blank'>下载完工附件</a>");
					if(getCookie("userType")==0){
						$("#engineerdefriendcam").show();
						$("#engineerdefriendqae").show();
						$("#engineerdefriendcam").click(function(argument) {
								var fd= new FormData();
								fd.append("engineerId",EngineerId);
								$.ajax({
									url: '/o2o/customer/orderInfo/engineerdefriend.do',
									method: 'post',
									data:fd,
									processData:false,
									contentType:false,
									success: function(data) {
										if(data.status==0){
											alert("拉黑成功");
											window.location.reload();
										}else{
											alert(data.msg);
				// window.location.href="login.html";
										}
									},
								});
							});
					}
				}else if(v.orderState==8){
					getChatInfo();

					$("#OrderState").html("发起扣款中，等待制作工程师确认");
					$(".FinishFile").show();
					$("#FinishFile").html("<a class='btn btn-success' href='"+OrderFile+"' style='color:white' target='_blank'>下载完工附件</a>");
					$("#ReductRank").html(v.orderDeductRank+"级扣款");
					$(".ReductRank").show();
					$("#ReductDec").html(v.orderDeductDec);
					$(".ReductDec").show();
					if(getCookie("userType")==0){

					}else{
						if(getCookie("engineerName")==EngineerName){
							//制作工程师确认扣款
							$("#toushu").show();
							$("#confirmKoukuan").show();
							$("#confirmKoukuan").click(function(argument) {
								var fd= new FormData();
								fd.append("orderId",OrderId);
								fd.append("complain",0);
								$.ajax({
									url: '/o2o/engineer/orderInfo/engineercomfirmorcomplain.do',
									method: 'post',
									data:fd,
									processData:false,
									contentType:false,
									success: function(data) {
										if(data.status==0){
											alert("确认成功");
											window.location.reload();
										}else{
											alert(data.msg);
				// window.location.href="login.html";
										}
									},
								});
							});
							$("#toushu2").click(function(argument) {
								var fd= new FormData();
								fd.append("orderId",OrderId);
								fd.append("complain",1);
								fd.append("dec",$("#toushuc").val());
								$.ajax({
									url: '/o2o/engineer/orderInfo/engineercomfirmorcomplain.do',
									method: 'post',
									data:fd,
									processData:false,
									contentType:false,
									success: function(data) {
										if(data.status==0){
											alert("投诉成功");
											window.location.reload();
										}else{
											alert(data.msg);
				// window.location.href="login.html";
										}
									},
								});
							});
						}
					}
				}else if(v.orderState==9){
					getChatInfo();

					$("#OrderState").html("发起扣款中，制作工程师已确认，等待审核工程师确认");
					$(".FinishFile").show();
					$("#FinishFile").html("<a class='btn btn-success' href='"+OrderFile+"' style='color:white' target='_blank'>下载完工附件</a>");
					$("#ReductRank").html(v.orderDeductRank+"级扣款");
					$(".ReductRank").show();
					$("#ReductDec").html(v.orderDeductDec);
					$(".ReductDec").show();
					if(getCookie("userType")==0){

					}else{
						if(getCookie("engineerName")==EngineerCheckName){
							//制作工程师确认扣款
							$("#toushu").show();
							$("#confirmKoukuan").show();
							$("#confirmKoukuan").click(function(argument) {
								var fd= new FormData();
								fd.append("orderId",OrderId);
								fd.append("complain",0);
								$.ajax({
									url: '/o2o/engineer/orderInfo/engineerqaecomfirmorcomplain.do',
									method: 'post',
									data:fd,
									processData:false,
									contentType:false,
									success: function(data) {
										if(data.status==0){
											alert("确认成功");
											window.location.reload();
										}else{
											alert(data.msg);
				// window.location.href="login.html";
										}
									},
								});
							});
							$("#toushu2").click(function(argument) {
								var fd= new FormData();
								fd.append("orderId",OrderId);
								fd.append("complain",1);
								fd.append("dec",$("#toushuc").val());
								$.ajax({
									url: '/o2o/engineer/orderInfo/engineerqaecomfirmorcomplain.do',
									method: 'post',
									data:fd,
									processData:false,
									contentType:false,
									success: function(data) {
										if(data.status==0){
											alert("投诉成功");
											window.location.reload();
										}else{
											alert(data.msg);
				// window.location.href="login.html";
										}
									},
								});
							});
						}
					}
				}else if(v.orderState==10){
					getChatInfo();

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
				
				$("#distributionBtn").click(function(argument) {
								var fd= new FormData();
								fd.append("orderId",orderId);
								fd.append("customerPrice",$("#customerPrice").val());
								fd.append("engineerPrice",$("#engineerPrice").val());
								if($("#engineerQaePrice").val()==""){
									fd.append("engineerQaePrice",0);
								}else{
									fd.append("engineerQaePrice",$("#engineerQaePrice").val());
								}
								$.ajax({
									url: '/o2o/customer/orderInfo/engineerdefriend.do',
									method: 'post',
									data:fd,
									processData:false,
									contentType:false,
									success: function(data) {
										if(data.status==0){
											alert("拉黑成功");
											window.location.reload();
										}else{
											alert(data.msg);
				// window.location.href="login.html";
										}
									},
								});
							});
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

function getCC(p) {
	console.log(p);
	var rt;
            // body...
            $.ajax({
            // la URL para la petición
            url : '/o2o/admin/quantityInfo/list.do?pageNum=1&pageSize='+1000,
            // especifica si será una petición POST o GET
            type : 'GET',
            // el tipo de información que se espera de respuesta
            dataType : 'json',
            async :false,
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
                        	console.log(val);
                        	console.log(getCookie("quantity"));
                        if(getCookie("quantity")>=val.quantityPercentage){
                        	console.log("111");
                        	rt=p*val.quantityDraw;
                        	break;
                        }else{
                        	continue;
                        }
                        // $("#quantitlyList").append(
                            // '<tr><td>'+val.quantityId+'</td><td>'+val.quantiyRank+'级</td><td>'+val.quantityDraw+'</td><td>'+val.quantityPercentage+'</td><td>'+val.quantityDec+'</td><td><a href="quantityInfochange.html?Id='+val.quantityId+'" class="btn btn-default">修改</a><a onclick="del('+val.quantityId+')" class="btn btn-warning">删除</a></td></tr>'
                        // );
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
            console.log(rt);
            return rt;
    }       


$("#getOrder").click(function() {
	// body...
	var fd= new FormData();
	fd.append("orderId",OrderId);
	console.log(OrderQae);
	console.log(OrderState);
	if(OrderQae==1){
		if(OrderState==3){
			var url="/o2o/engineer/orderInfo/qaecaughtcamorder.do";
		}else{
			var url='/o2o/engineer/orderInfo/caughtcamorder.do';
		}
	}else{
		var url='/o2o/engineer/orderInfo/caughtcamorder.do';
	}
	$.ajax({
			// la URL para la petición
			url : url,
	
			// la información a enviar
			// (también es posible utilizar una cadena de datos)
			type : 'post',

			data:fd,
	
			// el tipo de información que se espera de respuesta
			dataType : 'json',
			processData: false,  // 不处理数据
  			contentType: false,   // 不设置内容类型
			// código a ejecutar si la petición es satisfactoria;
			// la respuesta es pasada como argumento a la función
			success : function(json) {
				if(json.status==0){
					alert(json.data);
					window.location.reload();
				}else{
					alert(json.msg);
					// window.location.href="login.html";
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
});

$("#UploadFile").click(function(){
	$("#fileInput").click();
});

$("#delFile").click(function() {
	// body...
    $("#chatInfo").text("");
    $("#chatInfo").attr("disabled",false);
    $("#delFile").hide();
});

$('#choosefile').on('change', function( e ){
    //e.currentTarget.files 是一个数组，如果支持多个文件，则需要遍历
    var name = e.currentTarget.files[0].name;
    $("#delFile").show();
    $("#chatInfo").text( name );
    $("#chatInfo").attr("disabled","disabled");
});

$('#fileInput').on('change', function( e ){
    //e.currentTarget.files 是一个数组，如果支持多个文件，则需要遍历
    var name = e.currentTarget.files[0].name;
    $("#FinishFile").text( name );
    // $("#uploadprogress").show();
    $("#ConfirmUploadFile").show();
});

$("#ConfirmUploadFile").click(function() {
	// body...
	var file = $('#fileInput')[0].files[0];
	console.log(file);
	var fd = new FormData();
	fd.append("orderId",OrderId);
	fd.append("upload_file",file);

	$.ajax({
		url: '/o2o/engineer/orderInfo/orderuploadfile.do',
		method: 'post',
		data: fd,
		processData:false,
		contentType:false,
		// beforeSend:function(){
        	// $("#loadingGif").show();
        // },
        // complete:function(){
        	// $("#loadingGif").hide();
        // },
        xhr: function() {
            var xhr = new XMLHttpRequest();
            //使用XMLHttpRequest.upload监听上传过程，注册progress事件，打印回调函数中的event事件
            xhr.upload.addEventListener('progress', function (e) {
                console.log(e);
                //loaded代表上传了多少
                //total代表总数为多少
                var progressRate = (e.loaded / e.total) * 100 + '%';

                //通过设置进度条的宽度达到效果
                $('.progress > div').css('width', progressRate);
            })

            return xhr;
        },
		success: function(data) {
			if(data.status==0){
				alert(data.data);
				window.location.reload();
			}else{
				alert("系统错误");
				window.location.href="login.html";
			}
		},
	});
});

function getKoukuan(argument) {
	// body...
		$.ajax({
			url: '/o2o/admin/priceDeductInfo/list.do?pageSize=100&pageNum=1',
			method: 'get',
			success: function(data) {
				for (var i = data.data.list.length - 1; i >= 0; i--) {
					var v=data.data.list[i];
					$("#deductGrade").append("<option value='"+v.priceDeductId+"'>"+v.priceDeductRank+"级扣款/扣款率："+v.priceDeductPercentage+"</option>");
				}
			}
		});
	
		
}