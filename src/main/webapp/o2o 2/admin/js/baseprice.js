	function addBasePrice() {
			var fd = new FormData();
			var basepriceinfo= new Array("firstCategory","secondRank","thirdCategory","basicLayer","price","basicDec");

			for(let index in basepriceinfo) {  
       			// console.log(index,engineerrankinfo[index]);
	       		var val=$("#"+basepriceinfo[index]+"").val();
	       		fd.append(basepriceinfo[index],val);
    		}

			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/basicPriceInfo/add.do',

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
					window.location.href="baseprice.html";
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

	function changeBasePrice() {
			var fd = new FormData();
			var basepriceinfo= new Array("basicPriceId","firstCategory","secondRank","thirdCategory","basicLayer","price","basicDec");

			for(let index in basepriceinfo) {  
       			// console.log(index,engineerrankinfo[index]);
	       		var val=$("#"+basepriceinfo[index]+"").val();
	       		fd.append(basepriceinfo[index],val);
    		}

			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/basicPriceInfo/update.do',

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
			url : '/o2o/admin/basicPriceInfo/getbyid.do?basicPriceId='+Id,
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
					$("#basicPriceId").val(v.data.basicPriceId);
					$("#basicDec").val(v.data.basicDec);
					$("#price").val(v.data.price);
					$("#basicLayer").val(v.data.basicLayer);
					$("#firstCategory").val(v.data.firstCategory);
					$("#secondRank").val(v.data.secondRank);
					$("#thirdCategory").val(v.data.thirdCategory);
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
			url : '/o2o/admin/basicPriceInfo/list.do?pageNum='+pn+'&pageSize='+1000,
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
					console.log(v.data.list);
					for (var i = v.data.list.length - 1; i >= 0; i--) {
						var val = v.data.list[i];
						$("#basePriceList").append(
							'<tr><td>'+val.basicPriceId+'</td><td>'+val.firstCategory+'</td><td>'+val.secondRank+'</td><td>'+val.thirdCategory+'</td><td>'+val.basicLayer+'</td><td>'+val.price+'</td><td>'+val.basicDec+'</td><td><a href="basepricechange.html?Id='+val.basicPriceId+'" class="btn btn-default">修改</a><a class="btn btn-warning" onclick="del('+val.basicPriceId+')">删除</a></td></tr>'
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

	function del(id) {
		var fd = new FormData();

		fd.append("basicPriceId",id);  
		$.ajax({
			// la URL para la petición
			url : '/o2o/admin/basicPriceInfo/delete.do',
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

	function ifCanGET(a){
		if(a==1){
			return "可接";
		}else{
			return "不可接";
		}
	}
	