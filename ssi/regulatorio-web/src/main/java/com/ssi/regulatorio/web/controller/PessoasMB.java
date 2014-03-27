package com.ssi.regulatorio.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ssi.kernel.controller.interfaces.PessoasInt;
import com.ssi.kernel.model.Pessoas;
import com.ssi.kernel.redes.domain.DemandasRedes;
import com.ssi.kernel.redes.interfaces.DemandasRedesInt;

@ManagedBean
@ViewScoped
public class PessoasMB implements Serializable {

	private static final long serialVersionUID = -4973839265386772445L;
	
	@EJB
	private PessoasInt pessoaint;
	
	@EJB
	private DemandasRedesInt demandaInt;
	
	private List<DemandasRedes> demandasMinhas = new ArrayList<DemandasRedes>();
	private List<DemandasRedes> demandasComigo = new ArrayList<DemandasRedes>();
	
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
		demandasMinhas = new ArrayList<DemandasRedes>();
		demandasMinhas.addAll(demandaInt.recuperarDemandasCriadasPeloAutor(pessoaLogada));
	}
	
	public void atualizarDemandasComigo(Pessoas pessoaLogada){
		System.out.println("ATUALIZA DEMANDAS COMIGO");
		demandasComigo = new ArrayList<DemandasRedes>();
		demandasComigo.addAll(demandaInt.recuperarDemandasEstaoComPessoaLogada(pessoaLogada));
	}
		
	//Setters e Getters
	public List<DemandasRedes> getDemandasMinhas() {
		return demandasMinhas;
	}

	public void setDemandasMinhas(List<DemandasRedes> demandasMinhas) {
		this.demandasMinhas = demandasMinhas;
	}

	public List<DemandasRedes> getDemandasComigo() {
		return demandasComigo;
	}

	public void setDemandasComigo(List<DemandasRedes> demandasComigo) {
		this.demandasComigo = demandasComigo;
	}
	
	
	
}
