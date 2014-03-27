package com.ssi.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.controller.interfaces.MaquinaEmailsInt;
import com.ssi.kernel.model.MaquinaEmails;
import com.ssi.kernel.utils.Mensageria;

@ManagedBean
@ViewScoped
public class BuscarMailPendMB implements java.io.Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9149771641635733657L;

	@EJB
	private MaquinaEmailsInt interfaceejb;
	
	private MaquinaEmails obj = new MaquinaEmails();
	
	private Map<String,Object>filtros = new LinkedHashMap<String,Object>();
	
	private List<MaquinaEmails> objs;
	private List<MaquinaEmails> objschecked = new ArrayList<MaquinaEmails>();
	
	// Filtros
	private String cnmmodulo;
	
	private boolean exibelistagem = false;
	private boolean modalconfirmaacao = false;
	private boolean exibepopupeditar = false;
	
	//Controlador de exibi��o do select de a��es (disabled/enabled)
	private boolean ocultaacoes;
	
	private String acao; // a��o selecionada no combo de A��es
	
	@PostConstruct
    public void init() {
		
		obj = new MaquinaEmails();
		
		// A��es vem inicialmente, ocultas
		ocultaacoes = true;
		
		setObjs(new ArrayList<MaquinaEmails>());
		objschecked = new ArrayList<MaquinaEmails>();
    }
	
	public void buscar(){
		// Cria um objeto com o filtro informado e passa para o ejb
		MaquinaEmails obj = new MaquinaEmails();
		obj.setCnmmodulo(cnmmodulo);
		filtros.put("obj",obj);
		setObjs(interfaceejb.recuperarFiltrado(filtros));
		exibelistagem = true;
	}
	
	// Tenta reenviar os emails aos selecionados
	public void reenviarSelecionados(){
		Iterator<MaquinaEmails> it = objschecked.iterator();
		while(it.hasNext()){
			MaquinaEmails obj = it.next();
			
			// Reenvia os emails selecionados
			Map<String,String>listaemails = new HashMap<String,String>();
			listaemails.put(obj.getCnmdestinatario(),obj.getCnmemaildestinatario());
			
			Mensageria mensageria = new Mensageria();
			String assunto = obj.getCnmassunto();
			String strmsg = obj.getCnmmensagem();
			mensageria.enviarMensagem(strmsg, assunto, listaemails,obj.getCnmipsmtp(),obj.getCnmmodulo(),obj.getCnmssi());
			
			mensageria = null;
			listaemails = null;
			assunto = null;
			strmsg = null;
			
			obj.setChecked(false);
		}
		objschecked = new ArrayList<MaquinaEmails>();
		modalconfirmaacao = false;
	}
		
	public void selecionarCheckbox(MaquinaEmails obj){
		if(objschecked.contains(obj)){
			obj.setChecked(false);
			objschecked.remove(obj);
		}else{
			obj.setChecked(true);
			objschecked.add(obj);
		}
			
		// Verifica se apenas 1 checkbox est� checado. Caso positivo, habilita A��es
		setOcultaacoes(true);
		if(objschecked.size() == 1){
			setOcultaacoes(false);
		}
	}
	
	public void fecharPopups(){
		setExibepopupeditar(false);
		modalconfirmaacao = false;
	}
	
	// Quando o usu�rio seleciona uma a��o no select da view, esse m�todo � acionado
	public void efetuaracao(){
			
		// Mantem todos os popups com rendered=false antes do processameto das a��es para mante-los fechados
		fecharPopups();
			
		// S� permite usar esse recurso caso tenha sido selecionado apenas um registro
		if(objschecked.size() == 1){
				
			// Seta o objeto referenciando GruposModulos com o da List vinda do checkbox da busca
			obj = objschecked.get(0);
				
			if(acao.equals("1")){ // A��o: Editar
				exibepopupeditar = true;
			}
		}
			
		acao = "0"; // reseta o combobox de a��es
	}

	public List<MaquinaEmails> getObjs() {
		return objs;
	}

	public void setObjs(List<MaquinaEmails> objs) {
		this.objs = objs;
	}

	public String getCnmmodulo() {
		return cnmmodulo;
	}

	public void setCnmmodulo(String cnmmodulo) {
		this.cnmmodulo = cnmmodulo;
	}

	public boolean isExibelistagem() {
		return exibelistagem;
	}

	public void setExibelistagem(boolean exibelistagem) {
		this.exibelistagem = exibelistagem;
	}

	public List<MaquinaEmails> getObjschecked() {
		return objschecked;
	}

	public void setObjschecked(List<MaquinaEmails> objschecked) {
		this.objschecked = objschecked;
	}

	public boolean isModalconfirmaacao() {
		return modalconfirmaacao;
	}

	public void setModalconfirmaacao(boolean modalconfirmaacao) {
		this.modalconfirmaacao = modalconfirmaacao;
	}

	public boolean isOcultaacoes() {
		return ocultaacoes;
	}

	public void setOcultaacoes(boolean ocultaacoes) {
		this.ocultaacoes = ocultaacoes;
	}

	public boolean isExibepopupeditar() {
		return exibepopupeditar;
	}

	public void setExibepopupeditar(boolean exibepopupeditar) {
		this.exibepopupeditar = exibepopupeditar;
	}

	public MaquinaEmails getObj() {
		return obj;
	}

	public void setObj(MaquinaEmails obj) {
		this.obj = obj;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

}
