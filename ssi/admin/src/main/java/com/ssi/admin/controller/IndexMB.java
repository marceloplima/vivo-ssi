package com.ssi.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.ssi.kernel.model.Usuarios;


@ManagedBean
@RequestScoped
public class IndexMB{

	//private List<TreeNode> rootNodes = new ArrayList<TreeNode>();
	
	private Usuarios usuario = new Usuarios();
	private boolean panelexibesucesso;
	private boolean panelexibealerta;
	private boolean panelexibeerro;
	private String msgpanel;
	private List<String> msgspanel = new ArrayList<String>();
	private boolean renderizalistadeerros;
	
	// Retorna o menu selecionado (arquivo.xhtml) ou destroi a sess�o do usu�rio, caso saia do sistema.
	public String processarmenu(String acao){
		if(!acao.equals("sair")){
			return acao;
		}else{
			return "index.xhtml?deslogar=true";
		}
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
	
	public String exibeMenuLogin(){
		return "paginalogin";
	}
	
	public String exibeMenuTeste(){
		return "teste";
	}
	
	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	public Usuarios getUsuario() {
		return usuario;
	}
	
//	public void deslogar(){
//		usuario = null;
//		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
//		sessao.invalidate();
//		sessao = null;
//		System.out.println(">> MODULO ADM : SESS�O DE USU�RIO ELIMINADA <<");
//	}

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

}
