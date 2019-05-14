	function addCollageCongie() {
			var fd = new FormData();
			var priceTogetherInfo= new Array("priceTogetherNum","priceTogetherName","priceTogetherPercentage","priceTogetherDec");

			for(let index in priceTogetherInfo) {  
       			// console.log(index,engineerrankinfo[index]);
       			if(priceTogetherInfo[index]=="priceTogetherPercentage"){
	       			var val=$("#"+priceTogetherInfo[index]+"").val();
	       			val=val/100;
	       			fd.append(priceTogetherInfo[index],val);
       			}else{
       				var val=$("#"+priceTogetherInfo[index]+"").val();
	       			fd.append(priceTogetherInfo[index],val);
       			}
    		}

			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/priceTogetherInfo/add.do',

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
					window.locaion.href="collagecongie.html";
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

		function changeCollageCongie() {
			var fd = new FormData();
			var priceTogetherInfo= new Array("priceTogetherId","priceTogetherNum","priceTogetherName","priceTogetherPercentage","priceTogetherDec");

			for(let index in priceTogetherInfo) {  
       			// console.log(index,engineerrankinfo[index]);
       			if(priceTogetherInfo[index]=="priceTogetherPercentage"){
	       			var val=$("#"+priceTogetherInfo[index]+"").val();
	       			val=val/100;
	       			fd.append(priceTogetherInfo[index],val);
       			}else{
       				var val=$("#"+priceTogetherInfo[index]+"").val();
	       			fd.append(priceTogetherInfo[index],val);
       			}
    		}

			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/priceTogetherInfo/update.do',

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

	function getCollageCongie(pn) {
			// body...
			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/priceTogetherInfo/list.do?pageNum='+pn+'&pageSize='+1000,
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
						$("#collagecongieList").append(
							'<tr><td>'+val.priceTogetherId+'</td><td>'+val.priceTogetherNum+'件</td><td>'+val.priceTogetherName+'</td><td>'+val.priceTogetherPercentage+'</td><td>'+val.priceTogetherDec+'</td><td><a href="collagecongiechange.html?Id='+val.priceTogetherId+'" class="btn btn-default">修改</a><a class="btn btn-warning" coclick="del('+val.priceTogetherId+')">删除</a></td></tr>'
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
			url : '/o2o/admin/priceTogetherInfo/getbyid.do?priceTogetherId='+Id,
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
					$("#priceTogetherId").val(v.data.priceTogetherId);
					$("#priceTogetherNum").val(v.data.priceTogetherNum);
					$("#priceTogetherDec").val(v.data.priceTogetherDec);
					$("#priceTogetherName").val(v.data.priceTogetherName);
					$("#priceTogetherPercentage").val(v.data.priceTogetherPercentage*100);
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

		fd.append("priceTogetherId",id);  
		$.ajax({
			// la URL para la petición
			url : '/o2o/admin/priceTogetherInfo/delete.do',
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
