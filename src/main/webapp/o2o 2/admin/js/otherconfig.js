	function changeOtherParam() {
			var fd = new FormData();
			var otherParamInfo= new Array("paramId","paramName","paramPercentage","paramDec");

			for(let index in otherParamInfo) {  
       			// console.log(index,engineerrankinfo[index]);
       			if(otherParamInfo[index]=="paramPercentage"){
	       			var val=$("#"+otherParamInfo[index]+"").val();
	       			val=val/100;
	       			fd.append(otherParamInfo[index],val);
       			}else{
       				var val=$("#"+otherParamInfo[index]+"").val();
	       			fd.append(otherParamInfo[index],val);
       			}
    		}

			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/otherParamInfo/update.do',

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

	function addOtherParam() {
			var fd = new FormData();
			var otherParamInfo= new Array("paramName","paramPercentage","paramDec");

			for(let index in otherParamInfo) {  
       			// console.log(index,engineerrankinfo[index]);
       			if(otherParamInfo[index]=="paramPercentage"){
	       			var val=$("#"+otherParamInfo[index]+"").val();
	       			val=val/100;
	       			fd.append(otherParamInfo[index],val);
       			}else{
       				var val=$("#"+otherParamInfo[index]+"").val();
	       			fd.append(otherParamInfo[index],val);
       			}
    		}

			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/otherParamInfo/add.do',

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
					window.location.href-"otherconfig.html"
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

	function getOtherParam(pn) {
			// body...
			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/otherParamInfo/list.do?pageNum='+pn+'&pageSize='+100,
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
						if(val.paramId==1002){
							$("#paramList").append(
								'<tr><td>'+val.paramId+'</td><td>'+val.paramName+'件</td><td>'+val.paramPercentage+'</td><td>'+val.paramDec+'</td><td><a href="otherconfigchange.html?Id='+val.paramId+'" class="btn btn-default">修改</a></td></tr>'
							);
						}else{							
							$("#paramList").append(
								'<tr><td>'+val.paramId+'</td><td>'+val.paramName+'件</td><td>'+val.paramPercentage+'</td><td>'+val.paramDec+'</td><td><a href="otherconfigchange.html?Id='+val.paramId+'" class="btn btn-default">修改</a><a class="btn btn-warning" onclick="del('+val.paramId+')">删除</a></td></tr>'
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
			url : '/o2o/admin/otherParamInfo/getbyid.do?paramId='+Id,
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
					if(v.data.paramId==1002){
						$("#paramName").attr("disabled","disabled");
						// $("#paramDec").attr("disabled","disabled");
					}
					$("#paramId").val(v.data.paramId);
					$("#paramPercentage").val(v.data.paramPercentage*100);
					$("#paramName").val(v.data.paramName);
					$("#paramDec").val(v.data.paramDec);
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

		fd.append("paramId",id);  
		$.ajax({
			// la URL para la petición
			url : '/o2o/admin/otherParamInfo/delete.do',
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

