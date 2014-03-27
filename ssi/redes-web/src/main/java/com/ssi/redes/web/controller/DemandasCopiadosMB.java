package com.ssi.redes.web.controller;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.controller.interfaces.AreasInt;
import com.ssi.kernel.controller.interfaces.AtividadesInt;
import com.ssi.kernel.model.Areas;
import com.ssi.kernel.model.Atividades;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.redes.domain.DemandasRedes;


@ManagedBean
@ViewScoped
public class DemandasCopiadosMB implements java.io.Serializable{

	private static final long serialVersionUID = 7861451599886282473L;
		
	@EJB
	private AreasInt areasController;
	
	@EJB
	private AtividadesInt atividadesController;
	
	private boolean mostrarTelaCopiados = false;
	private List<Pessoas> pessoasGruposCopiados = new ArrayList<Pessoas>();
	
	private Atividades atividadeselecionada;
	
	private DemandasRedes demanda;
	
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
		

	@PostConstruct
    public void init() {
		
		if(demanda ==null){
			demanda = demandasmb.getDemandas();
		}
		
		setarPessoasGrupoCopiados(demanda.getAreasolicitante());
		
		recuperarCopiados();
		
	}
	
	public void setarPessoasGrupoCopiados(Areas area) {
		//System.out.println(">>>>atividade selecionada: "+atividadeselecionada.getIdatividade());
		pessoasGruposCopiados = areasController.recuperarPessoasArea(area);		
	}
	
	public void setarAtividade(Atividades a){
		fecharTelaCopiados();
		atividadeselecionada = a;
	}

	private void recuperarCopiados(){
		
		for(Pessoas pessoa:demandasmb.getPessoasJaCopiadas()){
		
			for(Pessoas pessoagrupo:pessoasGruposCopiados){
				
				if(pessoagrupo.equals(pessoa)){
					pessoagrupo.setChecked(true);
				}
				
			}
			
		}
		
	}
	
	private void setarCopiados() {
		
		List<Pessoas> copiados = new ArrayList<Pessoas>();
		
		for(Pessoas pessoa:pessoasGruposCopiados){
			
			if(pessoa.isChecked()){
				copiados.add(pessoa);
			}
						
		}
				
		demandasmb.setPessoasJaCopiadas(copiados);
		
	}

	public void gravarCopiados(){
				
		setarCopiados();
		demanda.setCopiados(new HashSet<Pessoas>(demandasmb.getPessoasJaCopiadas()));
		fecharTelaCopiados();
		
	}
	
	
	
	public void mostrarTelaCopiados(){
		mostrarTelaCopiados = true;
	}
	
	public void fecharTelaCopiados(){
		mostrarTelaCopiados = false;
	}
	
	/*Getter e Setter*/
	
	
	
	public boolean isMostrarTelaCopiados() {
		return mostrarTelaCopiados;
	}

	public List<Pessoas> getPessoasGruposCopiados() {
		
		setarPessoasGrupoCopiados(demanda.getAreasolicitante());
		
		// Traz selecionados, as pessoas que fazem parte do grupo padr�o do T�tulo (atividade) selecionada
		if(atividadeselecionada!=null){
			List<Pessoas> pessoascopiadosgravados = atividadesController.retornarCopiadosAtividade(atividadeselecionada);
			for(Pessoas p:pessoasGruposCopiados){
				if(pessoascopiadosgravados!=null && pessoascopiadosgravados.contains(p)){
					p.setChecked(true);
				}
				
			}
		}
		return pessoasGruposCopiados;
	}

	public void setPessoasGruposCopiados(List<Pessoas> pessoasGruposCopiados) {
		this.pessoasGruposCopiados = pessoasGruposCopiados;
	}

	public void setMostrarTelaCopiados(boolean mostrarTelaCopiados) {
		this.mostrarTelaCopiados = mostrarTelaCopiados;
	}

	public Atividades getAtividadeselecionada() {
		return atividadeselecionada;
	}

	public void setAtividadeselecionada(Atividades atividadeselecionada) {
		this.atividadeselecionada = atividadeselecionada;
	}
	
	

}
