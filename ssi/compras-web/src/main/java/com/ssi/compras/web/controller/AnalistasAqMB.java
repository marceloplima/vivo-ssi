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

import com.ssi.compras.common.interfaces.DemandaInt;
import com.ssi.compras.common.interfaces.EventosInt;
import com.ssi.compras.common.interfaces.ParticipanteInt;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.kernel.compras.domain.Eventos;
import com.ssi.kernel.compras.domain.Logs;
import com.ssi.kernel.compras.domain.Papeis;
import com.ssi.kernel.compras.domain.Participantes;
import com.ssi.kernel.controller.interfaces.GruposModulosInt;
import com.ssi.kernel.controller.interfaces.UfsInt;
import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.model.Ufs;

@ManagedBean
@ViewScoped
public class AnalistasAqMB implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -120169484678100078L;

	private final Integer idpapel = 9;
	
	@EJB
	private EventosInt eventosint;
	
	@EJB
	private GruposModulosInt gruposmodulosint;
	
	@EJB
	private ParticipanteInt participanteint;
	private Participantes participante;
	
	@EJB
	private UfsInt ufint;
	private Ufs uf;
	
	@EJB
	private DemandaInt demandaint;
	
	private List<Ufs> ufs;
	private List<Ufs> ufschecked;
	
	private List<String>msgserro;
	private IndexMB indexmb;
	private String msgpanel;
	
	private Demandas demanda;
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	private Eventos evento;
	
	private boolean exibemodal; // Usado quando � acionado pelo form de Demandas
	
	@PostConstruct
    public void init() {
		
		participante = participanteint.recuperarPorPapel(idpapel);
		
		if(participante == null){
			participante = new Participantes(idpapel);
		}
		
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
		}
	}
	
	public void habilitaModal(){
		exibemodal = true;
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
		
		getParticipante().getAnalistaaquisicao().setParticipanteanalaquisicao(participante);
		if(ufschecked == null || ufschecked.size()<=0){
			getParticipante().getAnalistaaquisicao().setUfs(new HashSet<Ufs>());
		}
		else{
			getParticipante().getAnalistaaquisicao().setUfs(new HashSet<Ufs>(ufschecked));
		}
		
		participanteint.definir(participante);
		participante = participanteint.recuperarPorPapel(idpapel);
		
		indexmb.setPanelexibesucesso(true);
    	indexmb.setMsgpanel("Defini��o efetuada com sucesso!");
    	
    	if(demanda!=null && demanda.getIddemanda()!=null){
	    	// Cria o Log
	    	String descricaoLog;
	    	descricaoLog = "Indica Novo Analista de Aquisi��o";
	    	Logs log = demandasmb.criaLog(1L, demandasmb.recuperarPessoaLogada(), descricaoLog);
	    	log.setDemanda(demanda);
	    	demanda.setLogs(demandasmb.adcionaLogDemandas(demanda,log));
	    	demandaint.alterar(demanda);
	    	
	    	// Registra Evento
	    	// Armazena o status anterior
	    	evento = new Eventos();
			evento.setStatusanterior(demanda.getStatus());
	    	evento = new Eventos();
	    	evento.setAutor(demandasmb.recuperarPessoaLogada());
	    	evento.setDemanda(getDemanda());
	    	evento.setStatus(getDemanda().getStatus());
	    	evento.getEgenerico().setEventogenerico(evento); // Faz o relacionamento inverso pro cascade funcionar
	    	eventosint.registrar(getEvento());
	    	setExibemodal(false);
	    	demandasmb.setDemandas(demandaint.recuperarDemandaEspecifica(demanda.getIddemanda()));
    	}
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
			
			if(participante.getAnalistaaquisicao().getUfs()!= null && participante.getAnalistaaquisicao().getUfs().size()>=1){
				Iterator<Ufs> it = participante.getAnalistaaquisicao().getUfs().iterator();
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
	
	public Demandas getDemanda() {
		return demanda;
	}

	public void setDemanda(Demandas demanda) {
		this.demanda = demanda;
	}

	public DemandasMB getDemandasmb() {
		return demandasmb;
	}

	public void setDemandasmb(DemandasMB demandasmb) {
		this.demandasmb = demandasmb;
	}
	
	public Eventos getEvento() {
		return evento;
	}

	public void setEvento(Eventos evento) {
		this.evento = evento;
	}

	public boolean isExibemodal() {
		return exibemodal;
	}

	public void setExibemodal(boolean exibemodal) {
		this.exibemodal = exibemodal;
	}
}
