	function addEngineerRank() {
			var fd = new FormData();
			var engineerrankinfo= new Array("engineerRank","engineerRankA","engineerRankB","engineerRankC","engineerRankD","engineerRankMi","engineerRankQae");

			for(let index in engineerrankinfo) {  
       			// console.log(index,engineerrankinfo[index]);
       			if(engineerrankinfo[index]=="engineerRank"){
	       			// console.log($("#"+engineerrankinfo[index]+"").val());  
	       			var val=$("#"+engineerrankinfo[index]+"").val();
	       			if(val==""){
	       				alert("请输入工程师等级");
	       				return;
	       			}else{
	       				fd.append(engineerrankinfo[index],val);
	       			}
       			}else{
	       			// console.log($("input[name='"+engineerrankinfo[index]+"']:checked").val());
	       			var val=$("input[name='"+engineerrankinfo[index]+"']:checked").val();  
	       			fd.append(engineerrankinfo[index],val);
       			}  
    		}

    		// for(let index in engineerrankinfo){
	    	// 	console.log(fd.get(engineerrankinfo[index])); 
    		// }

			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/engineerrankinfo/add.do',

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
					window.location.href="engineerrank.html";
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

	function changeEngineerRank() {
			var fd = new FormData();
			var engineerrankinfo= new Array("engineerRankId","engineerRank","engineerRankA","engineerRankB","engineerRankC","engineerRankD","engineerRankMi","engineerRankQae");

			for(let index in engineerrankinfo) {  
       			// console.log(index,engineerrankinfo[index]);
       			if(engineerrankinfo[index]=="engineerRank"||engineerrankinfo[index]=="engineerRankId"){
	       			// console.log($("#"+engineerrankinfo[index]+"").val());  
	       			var val=$("#"+engineerrankinfo[index]+"").val();
	       			if(val==""){
	       				alert("请输入工程师等级");
	       				return;
	       			}else{
	       				fd.append(engineerrankinfo[index],val);
	       			}
       			}else{
	       			// console.log($("input[name='"+engineerrankinfo[index]+"']:checked").val());
	       			var val=$("input[name='"+engineerrankinfo[index]+"']:checked").val();  
	       			fd.append(engineerrankinfo[index],val);
       			}  
    		}

    		// for(let index in engineerrankinfo){
	    	// 	console.log(fd.get(engineerrankinfo[index])); 
    		// }

			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/engineerrankinfo/update.do',

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

	function getEngineerRank(pn) {
			// body...
			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/engineerrankinfo/list.do?pageNum='+pn+'&pageSize='+100,
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
						val.engineerRankA=ifCanGET(val.engineerRankA);
						val.engineerRankB=ifCanGET(val.engineerRankB);
						val.engineerRankC=ifCanGET(val.engineerRankC);
						val.engineerRankD=ifCanGET(val.engineerRankD);
						val.engineerRankQae=ifCanGET(val.engineerRankQae);
						val.engineerRankMi=ifCanGET(val.engineerRankMi);
						$("#engineerRankList").append(
							'<tr><td>'+val.engineerRankId+'</td><td>'+val.engineerRank+'级</td><td>'+val.engineerRankA+'</td><td>'+val.engineerRankB+'</td><td>'+val.engineerRankC+'</td><td>'+val.engineerRankD+'</td><td>可接</td><td>'+val.engineerRankMi+'</td><td>'+val.engineerRankQae+'</td><td><a href="engineerrankchange.html?Id='+val.engineerRankId+'" class="btn btn-default">修改</a><a onclick="del('+val.engineerRankId+')" class="btn btn-warning">删除</a></td></tr>'
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
		console.log(id);
		var fd = new FormData();

		fd.append("engineerRankId",id);  
		$.ajax({
			// la URL para la petición
			url : '/o2o/admin/engineerrankinfo/delete.do',
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

	function getById(Id) {
		// body...
			$.ajax({
			// la URL para la petición
			url : '/o2o/admin/engineerrankinfo/getbyid.do?engineerRankId='+Id,
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
					$("#engineerRankId").val(v.data.engineerRankId);
					$("#engineerRank").val(v.data.engineerRank);
					if(v.data.engineerRankMi==0){
							$("input[name=engineerRankMi]:eq(1)").attr("checked",'checked');
					}else{
							$("input[name=engineerRankMi]:eq(0)").attr("checked",'checked');
					}
					if(v.data.engineerRankQae==0){
							$("input[name=engineerRankQae]:eq(1)").attr("checked",'checked');
					}else{
							$("input[name=engineerRankQae]:eq(0)").attr("checked",'checked');
					}
					if(v.data.engineerRankA==0){
							$("input[name=engineerRankA]:eq(1)").attr("checked",'checked');
					}else{
							$("input[name=engineerRankA]:eq(0)").attr("checked",'checked');
					}
					if(v.data.engineerRankB==0){
							$("input[name=engineerRankB]:eq(1)").attr("checked",'checked');
					}else{
							$("input[name=engineerRankB]:eq(0)").attr("checked",'checked');
					}
					if(v.data.engineerRankC==0){
							$("input[name=engineerRankC]:eq(1)").attr("checked",'checked');
					}else{
							$("input[name=engineerRankC]:eq(0)").attr("checked",'checked');
					}
					if(v.data.engineerRankD==0){
							$("input[name=engineerRankD]:eq(1)").attr("checked",'checked');
					}else{
							$("input[name=engineerRankD]:eq(0)").attr("checked",'checked');
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
	