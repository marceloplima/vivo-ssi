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
public class ResponsaveisTecnicosMB implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5516274010913814320L;

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
		
		
		participante = participanteint.recuperarPorPapel(PapeisRedes.ID_RESPONSAVELTECNICO);
		
		if(participante == null){
			participante = new ParticipantesRedes(PapeisRedes.ID_RESPONSAVELTECNICO);
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
		
		//participante.setResponsaveltecnico(getRt());
		
		PapeisRedes papel = new PapeisRedes();
		papel.setIdpapel(new Long(PapeisRedes.ID_RESPONSAVELTECNICO));
		
		Map<String,Object>sessaoAtiva = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Pessoas pessoa = (Pessoas)sessaoAtiva.get("sessao");
		
		participante.setAutor(pessoa);
		participante.setPapel(papel);
		participante.setFlagativo(true);
		
		participante.getResponsaveltecnico().setParticipante(participante);
		
		participanteint.definir(participante);
		
		participante = new ParticipantesRedes(PapeisRedes.ID_RESPONSAVELTECNICO);
		
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

}
