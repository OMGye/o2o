	function addQuantityInfo() {
			var fd = new FormData();
			var quantityInfo= new Array("quantiyRank","quantityDraw","quantityPercentage","quantityDec");

			for(let index in quantityInfo) {  
       			// console.log(index,engineerrankinfo[index]);
       			if(quantityInfo[index]=="quantityDraw"||quantityInfo[index]=="quantityPercentage"){
	       			var val=$("#"+quantityInfo[index]+"").val();
	       			val=val/100;
	       			fd.append(quantityInfo[index],val);
       			}else{
       				var val=$("#"+quantityInfo[index]+"").val();
	       			fd.append(quantityInfo[index],val);
       			}
    		}

			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/quantityInfo/add.do',

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
					window.location.href="quantityInfo.html";
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

	function changeQuantityInfo() {
			var fd = new FormData();
			var quantityInfo= new Array("quantityId","quantiyRank","quantityDraw","quantityPercentage","quantityDec");

			for(let index in quantityInfo) {  
       			// console.log(index,engineerrankinfo[index]);
       			if(quantityInfo[index]=="quantityDraw"||quantityInfo[index]=="quantityPercentage"){
	       			var val=$("#"+quantityInfo[index]+"").val();
	       			val=val/100;
	       			fd.append(quantityInfo[index],val);
       			}else{
       				var val=$("#"+quantityInfo[index]+"").val();
	       			fd.append(quantityInfo[index],val);
       			}
    		}

			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/quantityInfo/update.do',

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

	function getById(Id) {
		// body...
			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/quantityInfo/getbyid.do?quantityId='+Id,
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
					$("#quantityId").val(v.data.quantityId);
					$("#quantiyRank").val(v.data.quantiyRank);
					$("#quantityDec").val(v.data.quantityDec);
					$("#quantityDraw").val(v.data.quantityDraw*100);
					$("#quantityPercentage").val(v.data.quantityPercentage*100);
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
			url : '/o2o/admin/quantityInfo/list.do?pageNum='+pn+'&pageSize='+100,
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
						$("#quantitlyList").append(
							'<tr><td>'+val.quantityId+'</td><td>'+val.quantiyRank+'级</td><td>'+val.quantityDraw+'</td><td>'+val.quantityPercentage+'</td><td>'+val.quantityDec+'</td><td><a href="quantityInfochange.html?Id='+val.quantityId+'" class="btn btn-default">修改</a><a onclick="del('+val.quantityId+')" class="btn btn-warning">删除</a></td></tr>'
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

		fd.append("quantityId",id);  
		$.ajax({
			// la URL para la petición
			url : '/o2o/admin/quantityInfo/delete.do',
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
