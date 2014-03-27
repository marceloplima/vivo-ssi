package com.ssi.regulatorio.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.redes.domain.StatusRedes;
import com.ssi.kernel.redes.domain.TiposDocumentosRedes;
import com.ssi.kernel.redes.interfaces.StatusRedesInt;

@ManagedBean
@ViewScoped
public class StatusMB implements Serializable {

	private static final long serialVersionUID = 6452739520451698144L;
	
	private static final long idStatusEmRascunhoDemanda = 1L;
	
	@EJB
	private StatusRedesInt statusInt;

	public static long getIdstatusemrascunhodemanda() {
		return idStatusEmRascunhoDemanda;
	}
	
	public List<StatusRedes> recuperarStatusBusca(Long idtipodocumento){
		TiposDocumentosRedes td = new TiposDocumentosRedes();
		td.setIdtipodocumento(idtipodocumento);
		return statusInt.recuperar(td);
	}
	
	
}
