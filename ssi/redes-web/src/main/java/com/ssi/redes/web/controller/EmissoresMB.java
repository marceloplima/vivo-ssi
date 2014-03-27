package com.ssi.redes.web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.ssi.kernel.redes.domain.PapeisRedes;
import com.ssi.kernel.redes.domain.ParticipantesRedes;
import com.ssi.kernel.redes.interfaces.ParticipantesRedesInt;
import com.ssi.redes.web.controller.RecuperadorInstanciasBean;
import com.ssi.kernel.controller.interfaces.GruposModulosInt;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Pessoas;

@ManagedBean
@ViewScoped
public class EmissoresMB implements java.io.Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8091780412526843285L;
	
	private final Integer idpapel = 1;
	
	@EJB
	private GruposModulosInt gruposmodulosint;
	
	@EJB
	private ParticipantesRedesInt participanteint;
	private ParticipantesRedes participante;
	
	private List<String>msgserro;
	private IndexMB indexmb;
	private String msgpanel;
	
	@PostConstruct
    public void init() {
		
		participante = participanteint.recuperarPorPapel(idpapel);
		
		if(participante == null){
			participante = new ParticipantesRedes(idpapel);
		}
	}
	
	public void inicializarValidadores(){
		indexmb = RecuperadorInstanciasBean.recuperarInstanciaIndexBean();
	}
	
	public List<GruposModulos> recuperarGruposModulos(){
		return gruposmodulosint.recuperar();
	}
	
	public void definir(){
		setMsgpanel("");
		inicializarValidadores();
		
		PapeisRedes papel = new PapeisRedes();
		papel.setIdpapel(new Long(idpapel));
		
		Map<String,Object>sessaoAtiva = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Pessoas pessoa = (Pessoas)sessaoAtiva.get("sessao");
		
		participante.setAutor(pessoa);
		participante.setPapel(papel);
		participante.setFlagativo(true);
		
		getParticipante().getEmissor().setParticipanteemissor(participante);
		
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

	public ParticipantesRedes getParticipante() {
		return participante;
	}

	public void setParticipante(ParticipantesRedes participante) {
		this.participante = participante;
	}

	public Integer getIdpapel() {
		return idpapel;
	}

}
