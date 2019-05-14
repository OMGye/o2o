	function addPriceReduce() {
			var fd = new FormData();
			var priceDeductInfo= new Array("priceDeductRank","priceDeductPercentage","priceDeductDec");

			for(let index in priceDeductInfo) {  
       			// console.log(index,engineerrankinfo[index]);
       			if(priceDeductInfo[index]=="priceDeductPercentage"){
	       			var val=$("#"+priceDeductInfo[index]+"").val();
	       			val=val/100;
	       			fd.append(priceDeductInfo[index],val);
       			}else{
       				var val=$("#"+priceDeductInfo[index]+"").val();
	       			fd.append(priceDeductInfo[index],val);
       			}
    		}

			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/priceDeductInfo/add.do',

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
					alert('新增成功');
					window.location.href="chargebacksconfig.html"
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

	function changePriceReduce() {
			var fd = new FormData();
			var priceDeductInfo= new Array("priceDeductId","priceDeductRank","priceDeductPercentage","priceDeductDec");

			for(let index in priceDeductInfo) {  
       			// console.log(index,engineerrankinfo[index]);
       			if(priceDeductInfo[index]=="priceDeductPercentage"){
	       			var val=$("#"+priceDeductInfo[index]+"").val();
	       			val=val/100;
	       			fd.append(priceDeductInfo[index],val);
       			}else{
       				var val=$("#"+priceDeductInfo[index]+"").val();
	       			fd.append(priceDeductInfo[index],val);
       			}
    		}

			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/priceDeductInfo/update.do',

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
					alert('修改成功');
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

	function getBasePriceList(pn) {
			// body...
			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/priceDeductInfo/list.do?pageNum='+pn+'&pageSize='+100,
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
						$("#chargeBackList").append(
							'<tr><td>'+val.priceDeductId+'</td><td>'+val.priceDeductRank+'级</td><td>'+val.priceDeductPercentage+'</td><td>'+val.priceDeductDec+'</td><td><a href="chargebacksconfigchange.html?Id='+val.priceDeductId+'" class="btn btn-default">修改</a><a class="btn btn-warning" onclick="del('+val.priceDeductId+')">删除</a></td></tr>'
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

	function ifCanGET(a){
		if(a==1){
			return "可接";
		}else{
			return "不可接";
		}
	}

function getById(Id) {
		// body...
			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/priceDeductInfo/getbyid.do?priceDeductId='+Id,
			// especifica si será una petición POST o GET
			type : 'GET',
			// el tipo de información que se espera de respuesta
			dataType : 'json',

			processData: false,  // 告诉jQuery不要去处理发送的数据
            
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头

			// código a ejecutar si la petición es satisfactoria;
			// la respuesta es pasada como argumento a la función
			success : function(v) {
				if(v.status==0){
					$("#priceDeductId").val(v.data.priceDeductId);
					$("#priceDeductPercentage").val(v.data.priceDeductPercentage*100);
					$("#priceDeductRank").val(v.data.priceDeductRank);
					$("#priceDeductDec").val(v.data.priceDeductDec);
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

	function del(id) {
		var fd = new FormData();

		fd.append("priceDeductId",id);  
		$.ajax({
			// la URL para la petición
			url : '/o2o/admin/priceDeductInfo/delete.do',
			// especifica si será una petición POST o GET
			type : 'POST',
			// el tipo de información que se espera de respuesta
			dataType : 'json',

			data:fd,

			processData: false,  // 告诉jQuery不要去处理发送的数据
            
            contentType: false,   // 告诉jQuery不要去设置Content-Type请求头

			// código a ejecutar si la petición es satisfactoria;
			// la respuesta es pasada como argumento a la función
			success : function(v) {
					if(v.status==0){
							alert("删除成功");
							window.location.reload();
					}else{
							alert(v.msg);
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
