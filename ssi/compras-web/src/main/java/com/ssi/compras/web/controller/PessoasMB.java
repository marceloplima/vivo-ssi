package com.ssi.compras.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.compras.web.controller.RecuperadorInstanciasBean;
import com.ssi.kernel.compras.domain.Demandas;
import com.ssi.compras.common.interfaces.DemandaInt;
import com.ssi.kernel.controller.interfaces.PessoasInt;
import com.ssi.kernel.model.Pessoas;

@ManagedBean
@ViewScoped
public class PessoasMB implements Serializable {

	private static final long serialVersionUID = -4973839265386772445L;
	
	@EJB
	private PessoasInt pessoaint;
	
	@EJB
	private DemandaInt demandaInt;
	
	private List<Demandas> demandasMinhas = new ArrayList<Demandas>();
	private List<Demandas> demandasComigo = new ArrayList<Demandas>();
	
	private DemandasMB demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
	
	public PessoasMB() {}
	
	@PostConstruct
	public void init() {
		atualizarDemandasMinhas(demandasmb.recuperarPessoaLogada());
		atualizarDemandasComigo(demandasmb.recuperarPessoaLogada());
	}
	
	public List<String> searchbyName(String autobusca){
		return pessoaint.recuperarAutocomplete(autobusca.trim());
	}
	
	public void atualizarDemandasMinhas(Pessoas pessoaLogada){
		System.out.println("ATUALIZA MINHAS DEMANDAS");
		demandasMinhas = new ArrayList<Demandas>();
		demandasMinhas.addAll(demandaInt.recuperarDemandasCriadasPeloAutor(pessoaLogada));
	}
	
	public void atualizarDemandasComigo(Pessoas pessoaLogada){
		System.out.println("ATUALIZA DEMANDAS COMIGO");
		demandasComigo = new ArrayList<Demandas>();
		demandasComigo.addAll(demandaInt.recuperarDemandasEstaoComPessoaLogada(pessoaLogada));
	}
		
	//Setters e Getters
	public List<Demandas> getDemandasMinhas() {
		return demandasMinhas;
	}

	public void setDemandasMinhas(List<Demandas> demandasMinhas) {
		this.demandasMinhas = demandasMinhas;
	}

	public List<Demandas> getDemandasComigo() {
		return demandasComigo;
	}

	public void setDemandasComigo(List<Demandas> demandasComigo) {
		this.demandasComigo = demandasComigo;
	}
	
	
	
}
