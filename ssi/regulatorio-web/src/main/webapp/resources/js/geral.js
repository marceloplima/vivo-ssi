/**
 * 
 * Marca checkboxes numa tree recursiva
 * 
 * @param checks
 * @param ids
 *            (pais e filhos)
 */

function checaRecursivo(checks, ids) {

	var idarray = ids.split("|");
	var idpai = idarray[0];
	var id = idarray[1];
	var i;
	var arrChks = document.getElementsByTagName('input');
	for (i = 0; i < arrChks.length; i++) {
		if (arrChks[i].type == 'checkbox') {
			if (arrChks[i].name.indexOf(idpai + ".") >= 1) {
				arrChks[i].checked = checks;
			}
		}
	}

	// Faz um novo loop para saber se todos os filhos estão checados. Se
	// positivo, checa o pai
	var tg = 0;
	var tc = 0;
	for (i = 0; i < arrChks.length; i++) {
		if (arrChks[i].type == 'checkbox') {
			if (arrChks[i].name.indexOf(":" + id + ".") >= 1
					|| arrChks[i].name.indexOf(":" + id + ":") >= 1
					|| arrChks[i].name.indexOf("." + id + ".") >= 1
					|| arrChks[i].name.indexOf("." + id + ":") >= 1) {
				tg++;

				if (arrChks[i].checked == true) {
					tc++;
				}
			}
		}
	}

	// alert("tg: "+tg+"|tc: "+tc);

	if (tc == (tg - 1)) {
		for (i = 0; i < arrChks.length; i++) {
			if (arrChks[i].type == 'checkbox') {
				// alert(id);
				if (arrChks[i].name.indexOf("." + id + ".") >= 1
						|| arrChks[i].name.indexOf(":" + id + ":") >= 1
						|| arrChks[i].name.indexOf("." + id + ":") >= 1) {
					arrChks[i].checked = checks;
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
function mascara(o, f) {
	v_obj = o;
	v_fun = f;
	setTimeout("execmascara()", 1);
}

function execmascara() {
	v_obj.value = v_fun(v_obj.value);
}

// Mascara o CPF
function cpf_mask(v) {
	v = v.replace(/\D/g, ""); // Remove tudo o que não é dígito
	v = v.replace(/(\d{3})(\d)/, "$1.$2"); // Coloca ponto entre o terceiro e o
											// quarto dígitos
	v = v.replace(/(\d{3})(\d)/, "$1.$2"); // Coloca ponto entre o setimo e o
											// oitava dígitos
	v = v.replace(/(\d{3})(\d)/, "$1-$2"); // Coloca ponto entre o
											// decimoprimeiro e o decimosegundo
											// dígitos
	return v;
}

// Mascara o CNPJ
function cnpj_mask(v) {
	v = v.replace(/\D/g, ""); // Remove tudo o que não é dígito
	v = v.replace(/^(\d{2})(\d)/, "$1.$2"); // Coloca ponto entre o segundo e o
											// terceiro dígitos
	v = v.replace(/^(\d{2})\.(\d{3})(\d)/, "$1.$2.$3"); // Coloca ponto entre o
														// quinto e o sexto
														// dígitos
	v = v.replace(/\.(\d{3})(\d)/, ".$1/$2"); // Coloca barra entre o oitavo e
												// o nono dígitos
	v = v.replace(/(\d{4})(\d)/, "$1-$2"); // Coloca ponto entre o decimo
											// segundo e o decimo terceiro
											// dígitos
	return v;
}

// Mascara o telefone
function tel_mask(v) {
	v = v.replace(/\D/g, ""); // Remove tudo o que não é dígito
	v = v.replace(/(\d{2})(\d)/, "($1)$2"); // Coloca parênteses em volta dos
											// dois primeiros dígitos
	 //v=v.replace(/(\d{4})(\d)/,"$1-$2"); //Coloca hífen entre o quarto e o quinto dígitos
	
	var ini = v.substr(0,(v.length-4));
	var fim = v.substr((v.length-4),v.length);
	
	if(v.length>=12){
		v = ini+"-"+fim;
	}
	
	return v;
}

// Permite que sejam digitados apenas números no campo
function num_mask(v) {
	v = v.replace(/\D/g, ""); // Remove tudo o que não é dígito
	return v;
}

function moeda_mask(v){
    v=v.replace(/\D/g,"");//Remove tudo o que não é dígito
    
    if(v.length <= 2){
		var valor = "0,";
		v = valor+v;
	}
    
    if(v.length >= 4){
	    if(v.substring(0,1) == "0"){
	    	v = v.substring(1);
	    }
	}
    
    if(v=="0,0" || v=="0,"){
		v = "";
	}
    
    v=v.replace(/(\d)(\d{11})$/,"$1.$2");//coloca o ponto dos bilhões
    v=v.replace(/(\d)(\d{8})$/,"$1.$2");//coloca o ponto dos milhões  
    v=v.replace(/(\d)(\d{5})$/,"$1.$2");//coloca o ponto dos milhares
    v=v.replace(/(\d)(\d{2})$/,"$1,$2");//coloca a virgula antes dos 2 últimos dígitos  
    return v;  
}

//function MascaraMoeda(objTextBox, SeparadorMilesimo, SeparadorDecimal, e){
//    //var sep = 0;
//    var key = '';
//    var i = j = 0;
//    var len = len2 = 0;
//    var strCheck = '0123456789';
//    var aux = aux2 = '';
//    var whichCode = (window.Event) ? e.which : e.keyCode;
//    if (whichCode == 13) return true;
//    key = String.fromCharCode(whichCode); // Valor para o código da Chave
//    if (strCheck.indexOf(key) == -1) return false; // Chave inválida
//    len = objTextBox.value.length;
//    for(i = 0; i < len; i++)
//        if ((objTextBox.value.charAt(i) != '0') && (objTextBox.value.charAt(i) != SeparadorDecimal)) break;
//    aux = '';
//    for(; i < len; i++)
//        if (strCheck.indexOf(objTextBox.value.charAt(i))!=-1) aux += objTextBox.value.charAt(i);
//    aux += key;
//    len = aux.length;
//    if (len == 0) objTextBox.value = '';
//    if (len == 1) objTextBox.value = '0'+ SeparadorDecimal + '0' + aux;
//    if (len == 2) objTextBox.value = '0'+ SeparadorDecimal + aux;
//    if (len > 2) {
//        aux2 = '';
//        for (j = 0, i = len - 3; i >= 0; i--) {
//            if (j == 3) {
//                aux2 += SeparadorMilesimo;
//                j = 0;
//            }
//            aux2 += aux.charAt(i);
//            j++;
//        }
//        objTextBox.value = '';
//        len2 = aux2.length;
//        for (i = len2 - 1; i >= 0; i--)
//        objTextBox.value += aux2.charAt(i);
//        objTextBox.value += SeparadorDecimal + aux.substr(len - 2, len);
//    }
//    return false;
//}
//
//function formatCurrency(num) {
//	num = num.toString().replace(/\$|\,/g,'');
//	if(isNaN(num))
//		num = "0";
//	sign = (num == (num = Math.abs(num)));
//	num = Math.floor(num*100+0.50000000001);
//	cents = num%100;
//	num = Math.floor(num/100).toString();
//	if(cents<10)
//		cents = "0" + cents;
//	for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
//		num = num.substring(0,num.length-(4*i+3))+','+
//	num.substring(num.length-(4*i+3));
//	return (((sign)?'':'-') + num + ',' + cents);
//}
//function isNumber(event){
//	  var charCode = (event.which) ? event.which : event.keyCode;
//	  if (charCode > 31 && (charCode < 48 || charCode > 57))
//	   return false;
//	 return true;
//}