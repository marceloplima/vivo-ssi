package com.ssi.redes.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.redes.domain.MotivacoesRedes;
import com.ssi.kernel.redes.interfaces.MotivacoesRedesInt;

@ManagedBean
@ViewScoped
public class MotivacoesMB implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 454673994683362414L;
	@EJB
	private MotivacoesRedesInt motivacoesint;

	
	public List<MotivacoesRedes> recuperarMotivacoesRedes(){
		return motivacoesint.recuperar();
	}
	
	
}
