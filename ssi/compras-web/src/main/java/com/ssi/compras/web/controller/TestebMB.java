package com.ssi.compras.web.controller;

import java.util.ArrayList;
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
import com.ssi.kernel.controller.interfaces.PessoasInt;
import com.ssi.kernel.model.Pessoas;

@ManagedBean
@ViewScoped
public class TestebMB implements java.io.Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8091780412526843285L;
	
	
	@EJB
	private PessoasInt pessoasint;
	
	@EJB
	private ParticipanteInt participanteint;
	private Participantes participante;
	
	// Controladores de exibi��o de pain�s modais
	private boolean exibepopupeditar = false;
	private boolean modalconfirmaacao = false;
	
	// A��o que o usu�rio selecionou na tela
	private String acao;
	
	// Controlador de exibi��o do select de a��es (disabled/enabled)
	private boolean ocultaacoes;
	
	private List<Participantes> objschecked = new ArrayList<Participantes>();
	
	private List<String>msgserro;
	private IndexMB indexmb;
	
	private String msgpanel;
	
	@PostConstruct
    public void init() {
		// A��es vem inicialmente, ocultas
		ocultaacoes = true;
		
		// Caso navegue para outra tela e retorne, a lista de checados � zerada
		objschecked = new ArrayList<Participantes>();
		
		participante = participanteint.recuperarPorPapel(3); // 3 = Diretor
		
		//participante = participanteint.recuperar();
		
		if(participante == null){
			participante = new Participantes(3);
		}
	}
	
	public void inicializarValidadores(){
		indexmb = RecuperadorInstanciasBean.recuperarInstanciaIndexBean();
	}
	
	public List<Pessoas> recuperarPessoas(){
		return pessoasint.recuperar();
	}
	
	// Ao clicar em "Ativar/Desativar Selecionados" na view, seta o atributo "flagativo" pra true ou false em todos os marcados
	public void ativarDesativarSelecionados(){
		Iterator<Participantes> it = objschecked.iterator();
		while(it.hasNext()){
			Participantes o = it.next();
			if(o.isFlagativo())
				o.setFlagativo(false);
			else
				o.setFlagativo(true);
			//participanteint.definir(o);
		}
		objschecked = new ArrayList<Participantes>();
		setModalconfirmaacao(false);
	}
	
	public void selecionarCheckbox(Participantes o){
		if(objschecked.contains(o)){
			o.setChecked(false);
			objschecked.remove(o);
		}else{
			o.setChecked(true);
			objschecked.add(o);
		}
		
		// Verifica se apenas 1 checkbox est� checado. Caso positivo, habilita A��es
		ocultaacoes = true;
		if(objschecked.size() == 1){
			ocultaacoes = false;
		}
	}
	
	// Quando o usu�rio seleciona uma a��o no select da view, esse m�todo � acionado
	public void efetuaracao(){
		// S� permite usar esse recurso caso tenha sido selecionado apenas um registro
		if(objschecked.size() == 1){
			if(acao.equals("1")){ // A��o: Editar
				exibepopupeditar = true;
				
				// Seta o objeto referenciando Telefones com o da List vinda do checkbox de telefones
				//setParticipante(objschecked.get(0));
			}
			
			acao = "0"; // reseta o combobox de a��es
		}
	}
	
	public void definir(){
		System.out.println(">> definir() <<");
		setMsgpanel("");
		inicializarValidadores();
		
		Papeis papel = new Papeis();
		papel.setIdpapel(3L); // ID de Diretor
		
		Map<String,Object>sessaoAtiva = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Pessoas pessoa = (Pessoas)sessaoAtiva.get("sessao");
		
//		participante.setCnmtestea("testea");
//		participante.getTesteb().setTestea(participante);
//		participanteint.inserir(participante);
//		participante = participanteint.recuperar();
		participante.setAutor(pessoa);
		participante.setPapel(papel);
		participante.setFlagativo(true);
		
//		participante.setResponsaveltecnico(null);
//		participante.setGerenteorcamento(null);
		
		participante.getDiretor().setParticipantediretor(participante);
		participanteint.definir(participante);
		
		participante = participanteint.recuperarPorPapel(3);
		
		exibepopupeditar = false;
		indexmb.setPanelexibesucesso(true);
    	indexmb.setMsgpanel("Defini��o efetuada com sucesso!");
	}
	
	
	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public boolean isOcultaacoes() {
		return ocultaacoes;
	}

	public void setOcultaacoes(boolean ocultaacoes) {
		this.ocultaacoes = ocultaacoes;
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

	public boolean isExibepopupeditar() {
		return exibepopupeditar;
	}

	public void setExibepopupeditar(boolean exibepopupeditar) {
		this.exibepopupeditar = exibepopupeditar;
	}

	public boolean isModalconfirmaacao() {
		return modalconfirmaacao;
	}

	public void setModalconfirmaacao(boolean modalconfirmaacao) {
		this.modalconfirmaacao = modalconfirmaacao;
	}

	public Participantes getParticipante() {
		return participante;
	}

	public void setParticipante(Participantes participante) {
		this.participante = participante;
	}

}
