package com.ssi.redes.web.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.ssi.kernel.controller.interfaces.DominiosInt;
import com.ssi.kernel.model.Dominios;


@ManagedBean
@RequestScoped
public class DominiosMB{

	@EJB
	private DominiosInt dominiosproxy;
	
	private Dominios dominio = new Dominios();
	private List<Dominios> dominios;
	
	public Dominios getDominio() {
		return dominio;
	}
	public void setDominio(Dominios dominio) {
		this.dominio = dominio;
	}
	public List<Dominios> getDominios() {
		return dominios;
	}
	public void setDominios(List<Dominios> dominios) {
		this.dominios = dominios;
	}
	
	public List<Dominios> recuperar(){
		//if(dominios!=null && dominios.size()>=1){
			dominios = dominiosproxy.recuperar();
			return dominios;
		//}
		//else
			//return null;
	}

}
