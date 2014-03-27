/**
 * 
 * Marca checkboxes numa tree recursiva
 * 
 * @param checks
 * @param ids (pais e filhos)
 */

function checaRecursivo(checks,ids) {
	
	var idarray = ids.split("|");
	var idpai = idarray[0];
	var id	  = idarray[1];
	var i;
	var arrChks = document.getElementsByTagName('input');
    for(i=0;i<arrChks.length;i++){
        if(arrChks[i].type=='checkbox'){
        	if(arrChks[i].name.indexOf(idpai+".") >= 1){
        		arrChks[i].checked=checks;
	        }
        }
    }

    // Faz um novo loop para saber se algum dos filhos estão checados. Se positivo, checa o pai
    var tg=0;
    var tc=0;
	for(i=0;i<arrChks.length;i++){
		if(arrChks[i].type=='checkbox'){
			if(arrChks[i].name.indexOf(":"+id+".") >= 1 || arrChks[i].name.indexOf(":"+id+":")>=1 || arrChks[i].name.indexOf("."+id+".") >= 1 || arrChks[i].name.indexOf("."+id+":") >= 1){
				tg++;						
				
				if(arrChks[i].checked == true){
					tc++;
				}
			}					
		}
	}

	//alert("tg: "+tg+"|tc: "+tc);			
	
	if(tc>1){
		for(i=0;i<arrChks.length;i++){
			if(arrChks[i].type=='checkbox'){
				if(arrChks[i].name.indexOf(":"+(id-1)+":")>=1 && arrChks[i].checked==false){
					alert(arrChks[i].name);
					arrChks[i].checked=checks;
				}
			}
		}
	}
	
	
	if(tc == (tg-1)){
	  //if(tc>=1){
		for(i=0;i<arrChks.length;i++){
	        if(arrChks[i].type=='checkbox'){
	        	
	        	if(arrChks[i].checked==false && (
	        	   arrChks[i].name.indexOf("."+id+".") >= 1 || 
	        	   arrChks[i].name.indexOf(":"+id+":")>=1 || 
	        	   arrChks[i].name.indexOf("."+id+":")>=1 ||
	        	   arrChks[i].name.indexOf(":"+(id-1)+":")>=1)){
	        		
	        		arrChks[i].checked=checks;
		        }
	        }
	    }
	}
}

//function tracinhotel(o){
//	var str = o.value;
//	var strret = str.replace("-","");
//	var ini = str.substr(0,(str.length-4));
//	var fim = str.substr((str.length-4),str.length);
//	alert(str.length);
//	if(str.length>=12){
//		strret = ini+"-"+fim;
//	}
//	return strret;
//}

/**
 * MASCARA ( mascara(o,f) e execmascara() )
 */
function mascara(o,f){
	v_obj=o;
	v_fun=f;
	setTimeout("execmascara()",1);
}

function execmascara(){
	v_obj.value=v_fun(v_obj.value);
}

// Mascara o CPF
function cpf_mask(v){
	v=v.replace(/\D/g,"");                 //Remove tudo o que não é dígito
	v=v.replace(/(\d{3})(\d)/,"$1.$2");    //Coloca ponto entre o terceiro e o quarto dígitos
	v=v.replace(/(\d{3})(\d)/,"$1.$2");    //Coloca ponto entre o setimo e o oitava dígitos
	v=v.replace(/(\d{3})(\d)/,"$1-$2");   //Coloca ponto entre o decimoprimeiro e o decimosegundo dígitos
	return v;
}

// Mascara o telefone
function tel_mask(v){
	v=v.replace(/\D/g,"");                 //Remove tudo o que não é dígito
	v=v.replace(/(\d{2})(\d)/,"($1)$2"); //Coloca parênteses em volta dos dois primeiros dígitos
	//v=v.replace(/(\d{4})(\d)/,"$1-$2"); //Coloca hífen entre o quarto e o quinto dígitos
	
	var ini = v.substr(0,(v.length-4));
	var fim = v.substr((v.length-4),v.length);
	
	if(v.length>=12){
		v = ini+"-"+fim;
	}
	
	return v;
}

// Permite que sejam digitados apenas números no campo
function num_mask(v){
	v=v.replace(/\D/g,"");                 //Remove tudo o que não é dígito
	return v;
}
