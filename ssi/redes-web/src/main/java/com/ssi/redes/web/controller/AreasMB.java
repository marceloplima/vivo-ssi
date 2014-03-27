package com.ssi.redes.web.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.redes.web.controller.RecuperadorInstanciasBean;
import com.ssi.kernel.controller.interfaces.AreasInt;
import com.ssi.kernel.controller.interfaces.PessoasInt;
import com.ssi.kernel.model.Areas;

@ManagedBean
@ViewScoped
public class AreasMB implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6614336031679195796L;

	@EJB
	private AreasInt interfaceint;
	
	@EJB
	private PessoasInt pessoaint;
	
	private Areas obj = new Areas();
	
	private List<Areas> areas;
	private List<Areas> areaspessoas; // Somente as �reas da pessoa logada
	private List<Areas> areasoperacao; // Somente as �reas da Opera��o
	
	private LoginMB loginmb = RecuperadorInstanciasBean.recuperarInstanciaLoginBean();
	
	@PostConstruct
    public void init() {
	}
	
	public Areas getObj() {
		return obj;
	}

	public void setObj(Areas obj) {
		this.obj = obj;
	}

	public List<Areas> getAreas() {
		if(areas == null){
			areas = interfaceint.recuperar();
		}
		return areas;
	}

	public void setAreas(List<Areas> areas) {
		this.areas = areas;
	}

	public List<Areas> getAreaspessoas() {
		if(areaspessoas == null){
			areaspessoas = pessoaint.retornarAreasPessoa(loginmb.recuperarPessoaLogado());
		}
		return areaspessoas;
	}
	
	public List<Areas> recuperarAreasOperacao(){
		if(areasoperacao == null){
			areasoperacao = interfaceint.retornarAreasOperacao();
		}
		return areasoperacao;
	}

	public void setAreaspessoas(List<Areas> areaspessoas) {
		this.areaspessoas = areaspessoas;
	}

	public LoginMB getLoginmb() {
		return loginmb;
	}

	public void setLoginmb(LoginMB loginmb) {
		this.loginmb = loginmb;
	}

	public List<Areas> getAreasoperacao() {
		return areasoperacao;
	}

	public void setAreasoperacao(List<Areas> areasoperacao) {
		this.areasoperacao = areasoperacao;
	}

}
