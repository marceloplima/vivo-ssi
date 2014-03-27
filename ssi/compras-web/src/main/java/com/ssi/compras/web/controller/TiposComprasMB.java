package com.ssi.compras.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.compras.domain.TiposCompras;
import com.ssi.compras.common.interfaces.TiposComprasInt;

@ManagedBean
@ViewScoped
public class TiposComprasMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3189819176647459690L;

	@EJB
	private TiposComprasInt TiposComprasInt;
	
	private List<TiposCompras> objs;
		
	public TiposComprasMB() {
	}
	
	public List<TiposCompras> getObjs() {
		
		if(objs==null){
			objs = TiposComprasInt.recuperar();
		}			
		
		return objs;
	}

	public void setObjs(List<TiposCompras> objs) {
		this.objs = objs;
	}


}
