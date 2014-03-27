package com.ssi.compras.web.controller;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ssi.kernel.controller.interfaces.AutenticadorInt;
import com.ssi.kernel.controller.interfaces.LogAcessoInt;
import com.ssi.kernel.controller.interfaces.PessoasInt;
import com.ssi.kernel.model.Dominios;
import com.ssi.kernel.model.LogAcesso;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.model.Usuarios;
import com.ssi.kernel.utils.MD5Hashing;



@ManagedBean
@RequestScoped
public class LoginMB{
	private Dominios dominio;
	private Usuarios usuario = new Usuarios();
	private Boolean flagerroautenticacao;
	
	@EJB
	private AutenticadorInt autenticadorproxy;
	
	@EJB
	private PessoasInt pessoasint;
	
	@EJB
	private LogAcessoInt logacessoint;
	
	@PostConstruct
	public void init(){
		// In�cio da constru��o do objeto por um par�metro passado para deslogar
		String deslogar = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("deslogar");
				
		if(deslogar !=null && deslogar.equals("true")){
			System.out.println(">> M�DULO COMPRAS : vai destruir sess�o..");
			destroisessao();
		}
	}
	
	public void destroisessao(){
		usuario = null;
		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		sessao.invalidate();
		sessao = null;
		System.out.println(">> MODULO COMPRAS : SESS�O DE USU�RIO ELIMINADA <<");
	}
	
	public Dominios getDominio() {
		return dominio;
	}
	public void setDominio(Dominios dominio) {
		this.dominio = dominio;
	}
	public Usuarios getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}
	
	public Boolean getFlagerroautenticacao() {
		return flagerroautenticacao;
	}
	public void setFlagerroautenticacao(Boolean flagerroautenticacao) {
		this.flagerroautenticacao = flagerroautenticacao;
	}
	
	public Pessoas recuperarPessoaLogado(){
		Map<String,Object>sessaoAtiva = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Pessoas pessoa = (Pessoas)sessaoAtiva.get("sessao");
		return pessoa;
	}
	
	public String recuperarUsuarioLogado(){
		Map<String,Object>sessaoAtiva = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Pessoas pessoa = (Pessoas)sessaoAtiva.get("sessao");
		if(pessoa!=null)
			return pessoa.getUsuario().getCnmlogin();
		else
			return "";
	}
	
	public String recuperarDataAcesso(){
		Map<String,Object>sessaoAtiva = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		String dataacesso = (String)sessaoAtiva.get("dataacesso");
		return dataacesso;
	}
	
	public String logar(){
		
		autenticadorproxy.setDominio(getDominio());
		
		if(getDominio().getIddominio() == 2){ // Se a autentica��o for local, criptografa a senha
			getUsuario().setCnmsenha(MD5Hashing.criptografar(getUsuario().getCnmsenha())); // Criptografar a senha
		}
		
		autenticadorproxy.setUsuario(getUsuario());
		
		if(autenticadorproxy.autenticar()){
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
			Date date = new Date();
			System.out.println();
			
			Map<String,Object> sessao = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
			sessao.put("sessao", pessoasint.recuperarPorUsuario(getUsuario()));
			sessao.put("dataacesso", dateFormat.format(date));
			setFlagerroautenticacao(false);
			
			// Loga o acesso
			LogAcesso logacesso = new LogAcesso();
			logacesso.setCnmlogin(getUsuario().getCnmlogin());
			logacesso.setCnmmodulo("COMPRAS");
			logacessoint.incluir(logacesso);
			
			return "index_sistema?faces-redirect=true";
		}
		else{
			flagerroautenticacao = true;
		}
		
		return "index";
	}
	
	public boolean deslogar(){
//		HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
//		sessao.invalidate();
//		sessao = null;
//		System.out.println(">> COMPRAS SESS�O DE USU�RIO ELIMINADA <<");
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		try {
			
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			//System.out.println(request.getContextPath()+">>>"+request.getLocalAddr()+">>>"+request.getLocalPort());
			
			response.sendRedirect("http://"+request.getLocalAddr()+":"+request.getLocalPort()+request.getContextPath()+"/index.xhtml?deslogar=true");
		} catch (IOException e) {
			e.printStackTrace();
		}
		response = null;
		System.gc();
		return true;
	}
	
}
