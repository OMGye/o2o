var PriceToger = new Array();;
var otherparam = new Array();;
var Basicprice = new Array();;
var Thirdarr = new Array();
Thirdarr.push("CAM");
getOtherParam();
getPriceToger();
var price=[];
var ifprice=true;
var sum=0;
var togerprice=0;
Caculation();
$("#doorder").click(function(){
		if($("#basicLayer").val()==""){
			alert("请输入层数");
		}else{
			if($("#priceToger").val()==""){
				alert("请输入款数")
			}else{
				doorder();
			}
		}
});

$("#firstCategory").on("input propertychange",function(){
	// console.log($(this).attr('id'));
		if($("#basicLayer").val()!=""){
			Caculation($(this).attr('id'));
		}
});

$("#secondRank").on("input propertychange",function(){
	// console.log($(this).attr('id'));
	$("#C"+$(this).val()).show();
	if($(this).val()=="A"){
		$("#CB").hide();
		$("#CC").hide();
		$("#CD").hide();
	}
	if($(this).val()=="C"){
		$("#CB").hide();
		$("#CA").hide();
		$("#CD").hide();
	}
	if($(this).val()=="B"){
		$("#CA").hide();
		$("#CC").hide();
		$("#CD").hide();
	}
	if($(this).val()=="D"){
		$("#CA").hide();
		$("#CC").hide();
		$("#CB").hide();
	}

		if($("#basicLayer").val()!=""){
			Caculation($(this).attr('id'));
		}
});

function changeType(val) {
	// body...
	if(is_inarray(val)){
		Thirdarr.remove(val);
		$("#"+val).removeClass("btn-success");
		$("#"+val).addClass("btn-default");
	}else{
		$("#"+val).removeClass("btn-default");
		$("#"+val).addClass("btn-success");
		Thirdarr.push(val);
	}
	console.log(Thirdarr);
	if($("#basicLayer").val()!=""){
		Caculation();
	}	
}

Array.prototype.remove = function (val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};

function is_inarray(val) {
	// body...
	var s=false;
	for (var i = Thirdarr.length - 1; i >= 0; i--) {
		if(Thirdarr[i]==val){
			s = true;
		}
	}
	return s;
}

$("#thirdCategory").on("input propertychange",function(){
	// console.log($(this).attr('id'));
		if($("#basicLayer").val()!=""){
			Caculation($(this).attr('id'));
		}	
});

$("#basicLayer").on("input propertychange",function(){
		if($(this).val()!=""){
			Caculation($(this).attr('id'));
		}
});

$("#priceToger").on("input propertychange",function(){
		if($("#basicLayer").val()!=""){
			Caculation($(this).attr('id'));
		}
});

$("#waitFile").click(function() {
	// body...
	$("#waitfile").click();
})

$('#waitfile').on('change', function( e ){
    //e.currentTarget.files 是一个数组，如果支持多个文件，则需要遍历
    var name = e.currentTarget.files[0].name;
    $("#waitfiletext").val(name);
});

function doorder(id) {
	// body...
	var formData = new FormData(); 
	formData.append("orderFirstCategory",$("#firstCategory").val());
	formData.append("orderSecondCategory",$("#secondRank").val());
	formData.append("basicLayer",$("#basicLayer").val());
	formData.append("priceTogetherNum",$("#priceToger").val());
	formData.append("orderDec",$("#other").val());

	var thirdCategory=Thirdarr;
	var mi=0;
	var qae=0;
	for (var i = thirdCategory.length - 1; i >= 0; i--) {
		var v = thirdCategory[i];
		console.log(v);
		if(v=="MI"){
			mi=1;
		}
		if(v=="QAE"){
			qae=1;
		}
	}
	formData.append("orderMi",mi);
	formData.append("orderQae",qae);

	for (var i = otherparam.length - 1; i >= 0; i--) {
		var v = otherparam[i];
		if(v['paramName']=="加急"){
			if($("#"+v['paramName']+v['paramId']).val()=="true"){
				formData.append("rushId",v['paramId']);
			}
		}else{
			if($("#"+v['paramName']+v['paramId']).val()=="true"){
				formData.append("params",v['paramId']);
			}	
		}
	}
	if($("#waitfiletext").val()=="待选择文件"){
		alert("请上传附件")
		return;
	}else{
		// body...
		var file = $('#waitfile')[0].files[0];
		formData.append("upload_file",file);
	}
	$.ajax({
		url: "/o2o/customer/orderInfo/createorder.do",
		type: "POST",
		data:formData,
		dataType : "json",
		processData: false,  // 告诉jQuery不要去处理发送的数据
		contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
		xhr: function() {
            var xhr = new XMLHttpRequest();
            //使用XMLHttpRequest.upload监听上传过程，注册progress事件，打印回调函数中的event事件
            xhr.upload.addEventListener('progress', function (e) {
                console.log(e);
                //loaded代表上传了多少
                //total代表总数为多少
                var progressRate = (e.loaded / e.total) * 100*0.8 + '%';

                //通过设置进度条的宽度达到效果
                $('.progress > div').css('width', progressRate);
            })

            return xhr;
        },
		success: function(response,status,xhr){
			$('.progress > div').css('width', "100%");
			if(response.status==0){
				// price=response.data;
				$("#AliPay").attr("name",response.data);
				$("#Choose").modal("show");
			}else{
				alert(response.msg);
				window.location.href="login.html";
				// ifprice=false;
				// $("#basicprice").html(response.msg);	
				// console.log(response.msg);
			}
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
				window.location.href="Myorder.html";
			}else{
				alert("扣款成功");
				window.location.href="Myorder.html";
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

function Caculation(val) {
	// body...
	//计算基础价格
	price=[];
	var thirdCategory=Thirdarr;
	for (var i = thirdCategory.length - 1; i >= 0; i--) {
		price[i]=new Array();
		price[i]["price"]=getBasicPrice(thirdCategory[i]);
		price[i]["name"]=thirdCategory[i];
		price[i]["toger"]=false;
	}
	console.log(price);
	showprice();
	//计算款数价格
	if($("#priceToger").val()!=""){
		addpricetoger();
	}else{
		$("#numPrice").html("请输入款数")
	}
	//计算其他参数价格
	//sss
	var a=new Array();

	for (var i = otherparam.length - 1; i >= 0; i--) {
		var v = otherparam[i];
		a.push(v['paramName']+v['paramId']+"price");
		if($("#"+v['paramName']+v['paramId']).val()=="true"){
			if(v['paramId']==1002){
				$("#"+v['paramName']+v['paramId']+"price").html(0);
			}else{
				$("#"+v['paramName']+v['paramId']+"price").html((sum*v['paramPercentage']).toFixed(2));
			}
		}else{
			$("#"+v['paramName']+v['paramId']+"price").html(0);
		}
	}
	//计算总价
	a.push("basicprice");
	a.push("numPrice");
	var total=0;
	for (var i = a.length - 1; i >= 0; i--) {
		if(!isNaN($("#"+a[i]).html())){
			total=total+parseFloat($("#"+a[i]).html());
		}else{
			total=total+0;
		}
	}
	for (var i = otherparam.length - 1; i >= 0; i--) {
		var v = otherparam[i];
		if(v['paramId']==1002){
			if($("#"+v['paramName']+v['paramId']).val()=="true"){
				$("#"+v['paramName']+v['paramId']+"price").html((total*v['paramPercentage']).toFixed(2));
				total=total+total*v['paramPercentage'];
			}
		}
	}
	total1=total.toFixed(2);
	total2=total.toFixed(3);
	str=total2.toString();
	if(str.charAt(str.length-1)!=0){
		total1=parseFloat(total1)+parseFloat(0.01);
		console.log(total1);
	}
	console.log(total1);
	sum=total1
	$("#totalPrice").html(sum);
}

function getBasicPrice(thirdCategory) {
	// body...
	var price=0;
	var formData = new FormData(); 
	formData.append("basicLayer",$("#basicLayer").val());
	formData.append("thirdCategory",thirdCategory);
	formData.append("firstCategory",$("#firstCategory").val());
	formData.append("secondRank",$("#secondRank").val());
	$.ajax({
		url: "/o2o/customer/basicPriceInfo/getbasicprice.do",
		type: "POST",
		data:formData,
		dataType : "json",
		async:false,
		processData: false,  // 告诉jQuery不要去处理发送的数据
		contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
		success: function(response,status,xhr){
			if(response.status==0){
				price=response.data;
			}else{
				ifprice=false;
				$("#basicprice").html(response.msg);	
				// console.log(response.msg);
			}
		} 
	});
	// console.log(price);
	return price;
}

function showprice() {
	// body...
	// console.log(price);
	if(isInArray(price,0)){
		ifprice=false;
	}else{
		ifprice=true;
	}
	if(ifprice){
		sum=0;
		for (var i = price.length - 1; i >= 0; i--) {
			sum=sum+price[i]['price']
		}
		$("#basicprice").html(sum.toFixed(2));	
	}
}
//获得其他参数
function getOtherParam(argument) {
	// body...
	$.ajax({
		url: "/o2o/customer/otherParamInfo/list.do?pageSize=1000",
		type: "GET",
		dataType : "json",
		async:false,
		processData: false,  // 告诉jQuery不要去处理发送的数据
		contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
		success: function(response,status,xhr){
			// console.log(response);
			if(response.status==0){
				// console.log(response.data.list);
				for (var i = response.data.list.length - 1; i >= 0; i--) {
					var v = response.data.list[i];
					otherparam.push(v);
					CreateDivForParam(v.paramId,v.paramName,v.paramPercentage);
				}
			}else{
				console.log(response.msg);
			}
		} 
	});
	// console.log(otherparam);
}

function CreateDivForParam(id,name,percen) {
	// body...
	var appdiv='<div class="form-group"><label class="col-lg-4 control-label" for="focusedInput">'+name+'</label><div class="col-lg-12"><select class="form-control" name="'+percen+'" id="'+name+id+'" style="width: 100%;display: inline-block;" ><option value="false">否</option><option value="true">是</option></select></div></div>'
	$("#endParam").after(appdiv);

	$("#"+name+id).on("input propertychange",function(){
			if($("#basicLayer").val()!=""){
				Caculation($(this).attr('id'));
			}
	});

	appdiv='<tr><th>'+name+'</th><td><span id="'+name+id+'price">0</span></td></tr>'
	$("#endParamPrice").after(appdiv);
}
//获得拼款参数
function getPriceToger(argument) {
	// body...
	$.ajax({
		url: "/o2o/customer/priceTogetherInfo/list.do?pageSize=1000",
		type: "GET",
		dataType : "json",
		async:false,
		processData: false,  // 告诉jQuery不要去处理发送的数据
		contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
		success: function(response,status,xhr){
			// console.log(response);
			if(response.status==0){
				for (var i = response.data.list.length - 1; i >= 0; i--) {
					var v = response.data.list[i];
					PriceToger.push(v);
				}
			}else{
				console.log(response.msg);
			}
		} 
	});
	// console.log(PriceToger);
}

function addpricetoger() {
	// body...
	togerprice=0;
	var num=$("#priceToger").val();
	console.log(PriceToger);
	console.log(price);
	for (var i = PriceToger.length - 1; i >= 0; i--) {
		for (var j = price.length - 1; j >= 0; j--) {
			if(price[j]['name']==PriceToger[i]['priceTogetherName']){
				if(PriceToger[i]['priceTogetherNum']==num){
					price[j]['toger']=true;
					price[j]['togerprice']=price[j]['price']*PriceToger[i]['priceTogetherPercentage'];
					togerprice=togerprice+price[j]['price']*PriceToger[i]['priceTogetherPercentage'];
					console.log(PriceToger[i]);
					console.log(price[j]);
					$("#numPrice").html(togerprice.toFixed(2));
				}
			}
		}
	}
	for (var j = price.length - 1; j >= 0; j--) {
		console.log(price[j]);
		if(!price[j]['toger']){
			price[j]['togerprice']=0;
			$("#numPrice").html("不存在"+price[j]['name']+"的拼款量");
		}
	}
}