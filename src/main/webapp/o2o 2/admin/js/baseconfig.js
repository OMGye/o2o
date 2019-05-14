	function addBaseConfig() {
			var fd = new FormData();
			if($("#infotitle").val()!=""){
				fd.append("o2oParamName",$("#infotitle").val());
			}else{
				alert("请输入标题");
				return;
			}
			if($("#infoinfo").val()!=""){
				fd.append("o2oParamValue",$("#infoinfo").val());
			}else{
				alert("请输入内容");
				return;
			}
			// body...
			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/o2oInfo/add.do',

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
					window.location.href="baseconfig.html";
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

function changeBaseConfig() {
			var fd = new FormData();
			fd.append("o2oId",$("#o2oId").val());

			if($("#infotitle").val()!=""){
				fd.append("o2oParamName",$("#infotitle").val());
			}else{
				alert("请输入标题");
				return;
			}
			if($("#infoinfo").val()!=""){
				fd.append("o2oParamValue",$("#infoinfo").val());
			}else{
				alert("请输入内容");
				return;
			}
			// body...
			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/o2oInfo/update.do',

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

		function getBaseConfig(pn) {
			// body...
			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/o2oInfo/list.do?pageNum='+pn,
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
						$("#baseInfoList").append(
							'<tr><td>'+val.o2oId+'</td><td>'+val.o2oParamName+'</td><td>'+val.o2oParamValue+'</td><td><a href="baseconfigchange.html?o2oId='+val.o2oId+'" class="btn btn-default">修改</a></td></tr>'
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
			url : '/o2o/admin/o2oInfo/getbyid.do?o2oId='+Id,
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
					$("#o2oId").val(v.data.o2oId);
					$("#infotitle").val(v.data.o2oParamName);
					$("#infoinfo").val(v.data.o2oParamValue);
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

	