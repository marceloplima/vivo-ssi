package com.ssi.regulatorio.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.model.Modulos;
import com.ssi.kernel.model.Usuarios;


@ManagedBean
@RequestScoped
public class IndexMB{

	@EJB
	private ModulosInt modulosint;
	
	private Usuarios usuario = new Usuarios();
	private boolean panelexibesucesso;
	private boolean panelexibealerta;
	private boolean panelexibeerro;
	private String msgpanel;
	private List<String> msgspanel = new ArrayList<String>();
	private boolean renderizalistadeerros;
	private List<Modulos> modulos = new ArrayList<Modulos>();

	private boolean modaldashboard = false;
		
	// Retorna o menu selecionado (arquivo.xhtml) ou destroi a sess�o do usu�rio, caso saia do sistema.
	public String processarmenu(String acao){
		if(!acao.equals("sair")){
			return acao+"?faces-redirect=true";
		}else{
			return "index.xhtml?deslogar=true&faces-redirect=true";
		}
	}
	
	public void exibeDashBoard(){
		modaldashboard = true;
	}
	
	public String exibeMenuLogin(){
		return "paginalogin";
	}
	
	public String exibeMenuTeste(){
		return "teste";
	}
	
	public void desabilitaSucesso(){
		this.panelexibesucesso = false;
	}
	
	public void desabilitaAlerta(){
		this.panelexibealerta = false;
	}
	
	public void desabilitaErro(){
		this.panelexibeerro = false;
	}
	
	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	public Usuarios getUsuario() {
		return usuario;
	}

	public boolean isPanelexibesucesso() {
		return panelexibesucesso;
	}

	public void setPanelexibesucesso(boolean panelexibesucesso) {
		this.panelexibesucesso = panelexibesucesso;
	}

	public boolean isPanelexibealerta() {
		return panelexibealerta;
	}

	public void setPanelexibealerta(boolean panelexibealerta) {
		this.panelexibealerta = panelexibealerta;
	}

	public boolean isPanelexibeerro() {
		return panelexibeerro;
	}

	public void setPanelexibeerro(boolean panelexibeerro) {
		this.panelexibeerro = panelexibeerro;
	}

	public String getMsgpanel() {
		return msgpanel;
	}

	public void setMsgpanel(String msgpanel) {
		this.msgpanel = msgpanel;
	}

	public List<String> getMsgspanel() {
		return msgspanel;
	}

	public void setMsgspanel(List<String> msgspanel) {
		this.msgspanel = msgspanel;
	}

	public boolean isRenderizalistadeerros() {
		return renderizalistadeerros;
	}

	public void setRenderizalistadeerros(boolean renderizalistadeerros) {
		this.renderizalistadeerros = renderizalistadeerros;
	}

	public List<Modulos> getModulos() {
		modulos = modulosint.recuperar();
		return modulos;
	}

	public void setModulos(List<Modulos> modulos) {
		this.modulos = modulos;
	}

	public boolean isModaldashboard() {
		return modaldashboard;
	}

	public void setModaldashboard(boolean modaldashboard) {
		this.modaldashboard = modaldashboard;
	}

}
