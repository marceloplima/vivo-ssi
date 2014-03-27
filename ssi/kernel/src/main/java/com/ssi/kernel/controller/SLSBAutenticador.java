package com.ssi.kernel.controller;

import java.util.Hashtable;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.ssi.kernel.controller.interfaces.AutenticadorInt;
import com.ssi.kernel.model.Dominios;
import com.ssi.kernel.model.Usuarios;

@Stateless(mappedName="SLSBAutenticador")
public class SLSBAutenticador implements AutenticadorInt{

	@PersistenceContext(unitName="ssifwpc")
	EntityManager em;
	
	Usuarios usuarioauth;
	Dominios dominioauth;

	@Override
	public void setUsuario(Usuarios usuario) {
		usuarioauth = usuario;
	}

	@Override
	public void setDominio(Dominios dominio) {
		dominioauth = dominio;
	}
	
	public void autenticarLocal() throws IndexOutOfBoundsException{
		// Tenta localizar o login do usu�rio na base do sistema. Se n�o for poss�vel, retorna false e para o processo
		final String jpaquery = "SELECT usuario FROM Usuarios usuario where usuario.cnmlogin = :login and usuario.cnmsenha = :senha";
		TypedQuery<Usuarios> q = em.createQuery(jpaquery, Usuarios.class);
		q.setParameter("login", usuarioauth.getCnmlogin());
		q.setParameter("senha", usuarioauth.getCnmsenha());
		q.getResultList().get(0);
	}
	
	public void preLocalizarUsuario() throws IndexOutOfBoundsException{
		// Tenta localizar o login do usu�rio na base do sistema. Se n�o for poss�vel, retorna false e para o processo
		final String jpaquery = "SELECT usuario FROM Usuarios usuario where usuario.cnmlogin = :login";
		TypedQuery<Usuarios> q = em.createQuery(jpaquery, Usuarios.class);
		q.setParameter("login", usuarioauth.getCnmlogin());
		q.getResultList().get(0);
	}
	
	public Dominios recuperarDominio(){
		return em.find(Dominios.class,new Integer(dominioauth.getIddominio()));
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public boolean autenticar() {
		
		try{
			this.preLocalizarUsuario();
		}
		catch(IndexOutOfBoundsException e){
			return false;
		}
		
		dominioauth = recuperarDominio();
		String login = dominioauth.getCnmdominio().trim()+"\\"+(usuarioauth.getCnmlogin().toLowerCase().trim());
		// Se dominio for 2, n�o autentica por dom�nio. A autentica��o ser� na base do sistema
		if(dominioauth.getIddominio()!=2){
			try {
				String ldap  = "LDAP://"+dominioauth.getCnmip();
				
				System.out.println("==========================================================================");
				System.out.println("SSI - TENTANDO AUTENTICAR || "+login+" : "+usuarioauth.getCnmlogin());
				
				Hashtable<String, String> env = new Hashtable<String, String>();
				env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
				env.put(Context.SECURITY_PRINCIPAL, login);
				env.put(Context.SECURITY_CREDENTIALS, usuarioauth.getCnmsenha());
				env.put(Context.PROVIDER_URL, ldap);
				LdapContext ctx = new InitialLdapContext(env,null);
				ctx.close();
				env.clear();
				env = null;
				ldap = null;
				login = null;
				
			} catch (NamingException e) {
				System.out.println("ERRO AO AUTENTICAR USU�RIO VIA LDAP EM: LDAP://"+dominioauth.getCnmip());
				System.out.println("==========================================================================");
				e.printStackTrace();
				return false;
			}
		}
		else{
			try{
				this.autenticarLocal();
			}
			catch(IndexOutOfBoundsException e){
				return false;
			}
		}
		
		return true;
	}
	
}
