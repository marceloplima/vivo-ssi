package com.ssi.admin.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.controller.interfaces.ConfigSMTPInt;
import com.ssi.kernel.controller.interfaces.ModulosInt;
import com.ssi.kernel.model.ConfigSMTP;
import com.ssi.kernel.model.Modulos;

@ManagedBean
@ViewScoped
public class CadModulosMB implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5637833019934725523L;

	@EJB 
	private ModulosInt modulosint;
	
	@EJB 
	private ConfigSMTPInt configsmtpint;
	
	private List<Modulos> modulos;
	private Modulos modulo = new Modulos();
	private Modulos moduloupdate = new Modulos(); // Usado na edi��o (update)
	private boolean edit;
	private List<ConfigSMTP> smtps;
	
	private String msgvalidacao;
	private boolean exibevalidacao;
	
	@PostConstruct
    public void init() {
		modulos = new ArrayList<Modulos>();
    }
	
    public void add() {
    	Iterator<Modulos> itm = modulos.iterator();
    	
    	setExibevalidacao(false);
		setMsgvalidacao("");
    	
    	boolean existe = false;
    	while(itm.hasNext()){
    		Modulos obj = (Modulos)itm.next();
    		if(obj.getCnmmodulo().equals(modulo.getCnmmodulo())){
    			existe = true;
    			setExibevalidacao(true);
    			setMsgvalidacao("Voce j� inseriu um m�dulo com esse nome");
    		}
    	}
    	
    	// Se n�o existe na listagem preliminar, procede para as verifica��es no banco
    	if(!existe){
    		
    		// Verifica se j� n�o existe um m�dulo com esse nome no banco
    		if(modulosint.verificaExistente(modulo)){
    			setExibevalidacao(true);
    			setMsgvalidacao("Esse M�dulo j� existe no sistema");
    		}else{
	    		modulo.setFlagativo(1);
	    		Modulos modulorefresh = modulosint.incluir(modulo);
	    		
	    		modulos.add(modulorefresh);
	    		
	    		modulorefresh = null;
    		}
    		
    		modulo = new Modulos();
    	}
    	
    }
    
    public void edit(Modulos modulo) {
    	setEdit(true);
    	setExibevalidacao(false);
		setMsgvalidacao("");
		this.moduloupdate.setCnmmodulo(modulo.getCnmmodulo());
		this.moduloupdate.setConfigsmtp(modulo.getConfigsmtp());
		this.moduloupdate.setFuncionalidadesmodulo(modulo.getFuncionalidadesmodulo());
		this.moduloupdate.setDatacadastro(modulo.getDatacadastro());
		this.moduloupdate.setFlagativo(modulo.getFlagativo());
		this.moduloupdate.setIdmodulo(modulo.getIdmodulo());
		
		this.modulo = modulo;
    }
    
    public void save() {
    	
	    if(!modulosint.verificaExistente(moduloupdate)){
			
    		modulosint.alterar(moduloupdate);
    		
    		moduloupdate.setConfigsmtp(configsmtpint.recuperarUnico(moduloupdate.getConfigsmtp().getIdsmtp()));
	    	
    		modulos.remove(modulo);
    		modulos.add(moduloupdate);
    		
	    }
	    else{
	    	setExibevalidacao(true);
			setMsgvalidacao("Voce j� inseriu um m�dulo com esse nome");
	    }
    	
	    moduloupdate = new Modulos();
        modulo = new Modulos();
        edit = false;
    }
    
    public void delete(Modulos modulo) {
    	modulos.remove(modulo);
    	modulosint.remover(modulo);
    }
	
	public List<Modulos> getModulos() {
		return modulos;
	}
	public void setModulos(List<Modulos> modulos) {
		this.modulos = modulos;
	}

	public Modulos getModulo() {
		return modulo;
	}

	public void setModulo(Modulos modulo) {
		this.modulo = modulo;
	}
	
	public boolean isEdit() {
        return edit;
    }
	
	public void setEdit(Boolean edit){
		this.edit = edit;
	}

	public List<ConfigSMTP> getSmtps() {
		return smtps;
	}

	public void setSmtps(List<ConfigSMTP> smtps) {
		this.smtps = smtps;
	}
	
	public List<ConfigSMTP> recuperar(){
		smtps = configsmtpint.recuperar();
		return smtps;
		
	}

	public String getMsgvalidacao() {
		return msgvalidacao;
	}

	public void setMsgvalidacao(String msgvalidacao) {
		this.msgvalidacao = msgvalidacao;
	}

	public boolean isExibevalidacao() {
		return exibevalidacao;
	}

	public void setExibevalidacao(Boolean exibevalidacao) {
		this.exibevalidacao = exibevalidacao;
	}


	public Modulos getModuloupdate() {
		return moduloupdate;
	}

	public void setModuloupdate(Modulos moduloupdate) {
		this.moduloupdate = moduloupdate;
	}

}
