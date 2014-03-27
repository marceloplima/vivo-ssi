package com.ssi.compras.web.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.ssi.compras.web.controller.RecuperadorInstanciasBean;
import com.ssi.kernel.compras.domain.Papeis;
import com.ssi.kernel.compras.domain.Participantes;
import com.ssi.compras.common.interfaces.ParticipanteInt;
import com.ssi.kernel.controller.interfaces.GruposModulosInt;
import com.ssi.kernel.controller.interfaces.UfsInt;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.model.Ufs;

@ManagedBean
@ViewScoped
public class GerentesAquisicoesMB implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -701311100217209699L;

	private final Integer idpapel = 8;
	
	@EJB
	private GruposModulosInt gruposmodulosint;
	
	@EJB
	private ParticipanteInt participanteint;
	private Participantes participante;
	
	@EJB
	private UfsInt ufint;
	private Ufs uf;
	
	private List<Ufs> ufs;
	private List<Ufs> ufschecked;
	
	private List<String>msgserro;
	private IndexMB indexmb;
	private String msgpanel;
	
	@PostConstruct
    public void init() {
		
		participante = participanteint.recuperarPorPapel(idpapel);
		
		if(participante == null){
			participante = new Participantes(idpapel);
		}
	}
	
	public void inicializarValidadores(){
		indexmb = RecuperadorInstanciasBean.recuperarInstanciaIndexBean();
	}
	
	public List<GruposModulos> recuperarGruposModulos(){
		return gruposmodulosint.recuperar();
	}
	
	public List<Ufs> recuperarUfs(){
		return ufint.recuperar();
	}
	
	public void transferirUfs(){
		ufschecked = new ArrayList<Ufs>();
		Iterator<Ufs> ituf = ufs.iterator();
		while(ituf.hasNext()){
			Ufs uf = ituf.next();
			if(uf.isChecked()){
				ufschecked.add(uf);
			}
		}
	}
	
	public void definir(){
		setMsgpanel("");
		inicializarValidadores();
		
		transferirUfs();
		
		Papeis papel = new Papeis();
		papel.setIdpapel(new Long(idpapel));
		
		Map<String,Object>sessaoAtiva = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Pessoas pessoa = (Pessoas)sessaoAtiva.get("sessao");
		
		participante.setAutor(pessoa);
		participante.setPapel(papel);
		participante.setFlagativo(true);
		
		getParticipante().getGerenteaquisicao().setParticipantegeraquisicoes(participante);
		if(ufschecked == null || ufschecked.size()<=0){
			getParticipante().getGerenteaquisicao().setUfs(new HashSet<Ufs>());
		}
		else{
			getParticipante().getGerenteaquisicao().setUfs(new HashSet<Ufs>(ufschecked));
		}
		
		participanteint.definir(participante);
		
		participante = participanteint.recuperarPorPapel(idpapel);
		
		indexmb.setPanelexibesucesso(true);
    	indexmb.setMsgpanel("Defini��o efetuada com sucesso!");
	}

	public List<String> getMsgserro() {
		return msgserro;
	}

	public void setMsgserro(List<String> msgserro) {
		this.msgserro = msgserro;
	}

	public String getMsgpanel() {
		return msgpanel;
	}

	public void setMsgpanel(String msgpanel) {
		this.msgpanel = msgpanel;
	}


	public Participantes getParticipante() {
		return participante;
	}

	public void setParticipante(Participantes participante) {
		this.participante = participante;
	}

	public Ufs getUf() {
		return uf;
	}

	public void setUf(Ufs uf) {
		this.uf = uf;
	}

	public List<Ufs> getUfs() {
		if(ufs == null){
			ufs = recuperarUfs();
			
			if(participante.getGerenteaquisicao().getUfs()!= null && participante.getGerenteaquisicao().getUfs().size()>=1){
				Iterator<Ufs> it = participante.getGerenteaquisicao().getUfs().iterator();
				while(it.hasNext()){
					Ufs uf = it.next();
					
					Iterator<Ufs> it2 = ufs.iterator();
					
					while(it2.hasNext()){
						Ufs uf2 = it2.next();
						if(uf2.getCnmuf().equals(uf.getCnmuf())){
							uf2.setChecked(true);
						}
					}
				}
			}
		}
		return ufs;
	}

	public void setUfs(List<Ufs> ufs) {
		this.ufs = ufs;
	}

	public List<Ufs> getUfschecked() {
		return ufschecked;
	}

	public void setUfschecked(List<Ufs> ufschecked) {
		this.ufschecked = ufschecked;
	}

	public Integer getIdpapel() {
		return idpapel;
	}

}
