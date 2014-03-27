package com.ssi.compras.web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.ssi.compras.common.interfaces.EventosInt;
import com.ssi.compras.common.interfaces.ParticipanteInt;
import com.ssi.kernel.compras.domain.Papeis;
import com.ssi.kernel.compras.domain.Participantes;
import com.ssi.kernel.controller.interfaces.GruposModulosInt;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Pessoas;

@ManagedBean
@ViewScoped
public class AnalistasContratosMB implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6386552361641831317L;
	
	private final Integer idpapel = 11;
	
	@EJB
	private EventosInt eventosint;
	
	@EJB
	private GruposModulosInt gruposmodulosint;
	
	@EJB
	private ParticipanteInt participanteint;
	private Participantes participante;
	
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
	
	public void definir(){
		setMsgpanel("");
		inicializarValidadores();
		
		Papeis papel = new Papeis();
		papel.setIdpapel(new Long(idpapel));
		
		Map<String,Object>sessaoAtiva = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Pessoas pessoa = (Pessoas)sessaoAtiva.get("sessao");
		
		participante.setAutor(pessoa);
		participante.setPapel(papel);
		participante.setFlagativo(true);
		
		getParticipante().getAnalistacontrato().setParticipanteanalistacontrato(participante);
		
		participanteint.definir(participante);
		
		participante = participanteint.recuperarPorPapel(idpapel);
		
		indexmb.setPanelexibesucesso(true);
    	indexmb.setMsgpanel("Defini��o efetuada com sucesso!");
	}
	

	public Integer getIdpapel() {
		return idpapel;
	}

	public Participantes getParticipante() {
		return participante;
	}

	public void setParticipante(Participantes participante) {
		this.participante = participante;
	}

	public List<String> getMsgserro() {
		return msgserro;
	}

	public void setMsgserro(List<String> msgserro) {
		this.msgserro = msgserro;
	}

	public IndexMB getIndexmb() {
		return indexmb;
	}

	public void setIndexmb(IndexMB indexmb) {
		this.indexmb = indexmb;
	}

	public String getMsgpanel() {
		return msgpanel;
	}

	public void setMsgpanel(String msgpanel) {
		this.msgpanel = msgpanel;
	}

}
