package com.ssi.regulatorio.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.controller.interfaces.AreasInt;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.redes.domain.AnexosRedes;
import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.redes.domain.StatusRedes;
import com.ssi.kernel.redes.interfaces.PermissoesRedesInt;

@ManagedBean
@ViewScoped
public class PermissoesMB implements Serializable{	
	
	private static final long serialVersionUID = 8150454244775550747L;
	
	@EJB
	private PermissoesRedesInt permissoesInt;
	
	@EJB
	private AreasInt areasController;
	
	private DemandasRedes demanda;
	
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	@PostConstruct
	public void init() {
		if(demanda == null){
			demanda = demandasmb.getDemandasRedes();
			if(demanda == null){
				demanda = new DemandasRedes();
				demanda.setIddemanda(StatusRedes.ID_DEMANDA_INICIAL);
				demanda.setAutor(demandasmb.recuperarPessoaLogada());
			}
		}
	}
	
	public boolean verificarDemandaPersistida(){
		if(demandasmb.getDemandas() != null || demandasmb.getDemandas().getIddemanda()>=1){
			return true;
		}
		return false;
	}
	
	public boolean verificarPermissoesEmissor(){
		if(verificarDemandaPersistida()){
			boolean permitido = false;
			
			List<Long>statuspossiveis = new ArrayList<Long>();
			statuspossiveis.add(StatusRedes.ID_DEMANDA_INICIAL);
			statuspossiveis.add(StatusRedes.ID_DEMANDA_RASCUNHO);
			
			boolean papel = permissoesInt.verificarPermissaoEmissor(demandasmb.recuperarPessoaLogada());
			
			boolean statuspermite = (statuspossiveis.contains(demanda.getStatus().getIdstatus()));
				
			if(papel && statuspermite){
				permitido = true;
			}
				
			return permitido;
		}
		return false;
	}
	
	public boolean verificarCancelarSSI(){
		if(verificarDemandaPersistida()){
			List<Long>statusNaoPossiveis = new ArrayList<Long>();
			statusNaoPossiveis.add(StatusRedes.ID_DEMANDA_INICIAL);
			statusNaoPossiveis.add(StatusRedes.ID_DEMANDA_CANCELADA);
			
			return !statusNaoPossiveis.contains(demanda.getStatus().getIdstatus());
		}
		return false;
	}
	

	public boolean verificarCopiados(){
		return verificarPermissoesEmissor();
	}
	
	public boolean verificarEncaminharSSI(){
		return verificarPermissoesEmissor();
	}
	
	public boolean verificarAnaliseAreaRequisitada(){
		
		if(!verificarDemandaPersistida()){
			return false;
		}
		
		List<Long>statuspossiveis = new ArrayList<Long>();
		statuspossiveis.add(StatusRedes.ID_DEMANDA_ANALISE_PELA_EXECUTORA);
						
		return statuspossiveis.contains(demanda.getStatus().getIdstatus()) 
				&& isParticipanteDaAreaRequisitada();		
		
		
	}
	
	private boolean isParticipanteDaAreaRequisitada() {		
		return areasController.recuperarPessoasArea(demanda.getArearequisitada()).contains(demandasmb.recuperarPessoaLogada());
	}

	public boolean verificarConclusao(){
		if(!verificarDemandaPersistida()){
			return false;
		}
		
		List<Long>statuspossiveis = new ArrayList<Long>();
		statuspossiveis.add(StatusRedes.ID_DEMANDA_EM_EXECUCAO);
						
		return statuspossiveis.contains(demanda.getStatus().getIdstatus()) 
				&& isParticipanteDaAreaRequisitada();
	}
	
	public boolean verificarInformacoesComplementares(){
		
		List<Long>statuspossiveis = new ArrayList<Long>();
		statuspossiveis.add(StatusRedes.ID_DEMANDA_REVISAO_PELO_EMISSOR);

		return statuspossiveis.contains(demanda.getStatus().getIdstatus()) 
				&& isEmissor(demandasmb.recuperarPessoaLogada());
		
	}
	
	public boolean editaCamposDadosDeAberturaDemanda(){
		
		List<Long>statuspossiveis = new ArrayList<Long>();
		statuspossiveis.add(StatusRedes.ID_DEMANDA_REVISAO_PELO_EMISSOR);
		statuspossiveis.add(StatusRedes.ID_DEMANDA_INICIAL);
		statuspossiveis.add(StatusRedes.ID_DEMANDA_RASCUNHO);
						
		return statuspossiveis.contains(demanda.getStatus().getIdstatus()) 
				&& isEmissor(demandasmb.recuperarPessoaLogada());
		
	}
	
	private boolean isEmissor(Pessoas pessoaLogada) {						
		return demanda.getAutor().equals(pessoaLogada) 
				&& permissoesInt.verificarPermissaoEmissor(demandasmb.recuperarPessoaLogada());
	}
	
	public boolean verificarPodeRemoverAnexo(AnexosRedes anexo){
		if(anexo.getAutor().getIdpessoa() == demandasmb.recuperarPessoaLogada().getIdpessoa()){
			return true;
		}
		
		return false;
	}

}
