package com.ssi.compras.common.interfaces;


import java.util.List;

import javax.ejb.Local;
import com.ssi.kernel.compras.domain.ResponsaveisTecnicos;



@Local
public interface ResponsaveisTecnicosInt {
	
	public List<ResponsaveisTecnicos> recuperar();
	public ResponsaveisTecnicos recuperarUnico();

}
