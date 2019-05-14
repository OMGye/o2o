console.log(getCookie("userType"));

if(getCookie("userType")!=""){
	$(".logined").show();
	if(getCookie("userType")==0){
		$(".Customerlogined").show();
		$(".user-level").html(getCookie("CustomerName"));
	}else{
		$(".Engineerlogined").show();
		$(".user-level").html(getCookie("engineerName"));
	}
}else{
	$(".unlogin").show();
}

getBaseConfig();

function getBaseConfig() {
	// body...
	$.ajax({
			// la URL para la petición
			url : '/o2o/admin/o2oInfo/list.do?pageNum='+1,
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
						console.log(val);
						if(val.o2oId=="1005"){
							$(".copyright").html(val.o2oParamValue);
						}
						if(val.o2oId=="1003"){
							$("[name='keywords']").attr("content",val.o2oParamValue);
						}
						if(val.o2oId=="1002"){
							$("[name='descriptions']").attr("content",val.o2oParamValue);
						}
						if(val.o2oId=="1007"){
							document.title = val.o2oParamValue;
							$(".SiteName").html(val.o2oParamValue);
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