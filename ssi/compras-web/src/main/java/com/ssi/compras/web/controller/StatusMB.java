package com.ssi.compras.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.compras.domain.Status;
import com.ssi.kernel.compras.domain.TiposDocumentos;
import com.ssi.compras.common.interfaces.StatusInt;

@ManagedBean
@ViewScoped
public class StatusMB implements Serializable {

	private static final long serialVersionUID = 6452739520451698144L;
	
	private static final long idStatusEmRascunhoDemanda = 1L;
	
	@EJB
	private StatusInt statusInt;

	public static long getIdstatusemrascunhodemanda() {
		return idStatusEmRascunhoDemanda;
	}
	
	public List<Status> recuperarStatusBusca(Long idtipodocumento){
		TiposDocumentos td = new TiposDocumentos();
		td.setIdtipodocumento(idtipodocumento);
		return statusInt.recuperar(td);
	}
	
	
}
