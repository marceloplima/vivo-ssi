package com.ssi.redes.web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import com.ssi.kernel.controller.interfaces.AtividadesInt;
import com.ssi.kernel.controller.interfaces.CentraisInt;
import com.ssi.kernel.model.Atividades;
import com.ssi.kernel.model.Centrais;
import com.ssi.kernel.model.Ufs;
import com.ssi.kernel.redes.domain.Rotas;
import com.ssi.kernel.redes.interfaces.RotasInt;

@ManagedBean
@ViewScoped
public class RotasMB  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7235309383738462467L;
	
	@EJB
	private AtividadesInt interfaceint;
	
	@EJB
	private RotasInt rotasint;
	
	@EJB
	private CentraisInt centraisint;
	
	// Controlador que a op��o de inclus�o de rotas, de acordo com a atividade selecionada
	private boolean exiberota;
	private boolean exibeconfirmacaoremover;
	private boolean exibepopuprotas;
	private boolean exibepopuprotaslupa;
	
	private DemandasMB demandasmb;
	
	private Atividades atividade;
	private List<Rotas> rotas;
	private Rotas rota;
	private Ufs uforigem;
	private Centrais centralselecionadaorigem;
	private boolean exibedetalhesrotaorigem;
	
	private Ufs ufdestino;
	private Centrais centralselecionadadestino;
	private boolean exibedetalhesrotadestino;
	
	private boolean aproveitarrotaanterior; // Se true, o sistema ir� manter a rota rec�m-adicionada para facilitar cadastro futuro.
	
	@PostConstruct
	public void init(){
		
		exibedetalhesrotaorigem = false;
		exibedetalhesrotadestino = false;
		
		if(rota == null){
			rota = new Rotas();
		}
		
		demandasmb = RecuperadorInstanciasBean.recuperarInstanciaDemandasBean();
		
		if(demandasmb.getDemandas()!=null){
			setAtividade(demandasmb.getDemandas().getAtividade());
			
			controlarRota();
		}
	}
	
	public void setarCentralorigem(ValueChangeEvent event){
		Centrais c = (Centrais) event.getNewValue();
		if(c!=null){
			setCentralselecionadaorigem(centraisint.recuperaUnico(c));
			exibedetalhesrotaorigem = true;
		}
	}
	
	public void setarUforigem(ValueChangeEvent event){
		exibedetalhesrotaorigem = false;
		Ufs uf = (Ufs) event.getNewValue();
		setUforigem(uf);
	}
	
	public List<Centrais> recuperarCentraisOrigemPorUf(){
		if(uforigem!=null){
			return centraisint.recuperarPorUf(getUforigem());
		}
		else{
			return new ArrayList<Centrais>();
		}
	}
	
	public void setarCentraldestino(ValueChangeEvent event){
		Centrais c = (Centrais) event.getNewValue();
		if(c!=null){
			setCentralselecionadadestino(centraisint.recuperaUnico(c));
			exibedetalhesrotadestino = true;
		}
	}
	
	public void setarUfdestino(ValueChangeEvent event){
		exibedetalhesrotadestino = false;
		Ufs uf = (Ufs) event.getNewValue();
		setUfdestino(uf);
	}
	
	public List<Centrais> recuperarCentraisDestinoPorUf(){
		if(ufdestino!=null){
			return centraisint.recuperarPorUf(getUfdestino());
		}
		else{
			return new ArrayList<Centrais>();
		}
	}
	
	public void setarAtividade(Atividades a){
		setAtividade(a);
		controlarRota();
	}
	
	public void controlarRota(){
		exiberota = false;
		if(atividade != null && atividade.isFlagtemrota()){
			exiberota = true;
		}
	}
	
	public void habilitarPopupLupa(Rotas rota){
		this.rota = rota;
		this.uforigem = rota.getUforigem();
		this.ufdestino = rota.getUfdestino();
		exibepopuprotaslupa = true;
	}
	
	public void desabilitarPopupLupa(){
		exibepopuprotaslupa = false;
	}
	
	public void habilitarEditar(Rotas rota){
		this.rota = rota;
		this.uforigem = rota.getUforigem();
		this.ufdestino = rota.getUfdestino();
		recuperarCentraisOrigemPorUf();
		recuperarCentraisDestinoPorUf();
		setCentralselecionadaorigem(centraisint.recuperaUnico(this.rota.getCentralorigem()));
		setCentralselecionadadestino(centraisint.recuperaUnico(this.rota.getCentraldestino()));
		exibepopuprotas = true;
		exibedetalhesrotaorigem = true;
		exibedetalhesrotadestino = true;
	}
	
	public void habilitarPopup(){
		exibepopuprotas = true;
	}
	
	public void desabilitarPopup(){
		exibepopuprotas = false;
	}
	
	public void salvar(){
		if(rota.getIdrota()!=null){
			rotasint.persistir(rota);
		}
		List<Rotas> rotas = rotasint.recuperarRotasDemanda(demandasmb.getDemandas());
		rotas.addAll(demandasmb.getDemandas().getRotas());
		rota.setAutor(demandasmb.recuperarPessoaLogada());
		rota.setDemanda(demandasmb.getDemandas());
		rotas.add(rota);
		
		// Remove duplicados (se houver)
		HashSet<Rotas> rotassemduplicados = new LinkedHashSet<Rotas>();
		rotassemduplicados.addAll(rotas);
		
		demandasmb.getDemandas().setRotas(rotassemduplicados);
		desabilitarPopup();
		
		if(aproveitarrotaanterior){
			try {
				Rotas rotaaproveitar = (Rotas) rota.clone();
				rota = (Rotas)rotaaproveitar.clone();
				
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		else{
			rota = new Rotas();
		}
		
		exibedetalhesrotaorigem  = false;
		exibedetalhesrotadestino = false;
//		uf = new Ufs();
//		centralselecionada = new Centrais();
	}
	
	public void fecharPopupConfirmacaoRemover(){
		exibeconfirmacaoremover = false;
	}
	
	public void preRemover(Rotas rota){
		this.rota = rota;
		exibeconfirmacaoremover = true;
	}
	
	public void remover(){
		List<Rotas> rotasremanescentes = new LinkedList<Rotas>();
		if(rota.getIdrota()!=null){
			rotasint.remover(rota);
			rotasremanescentes = rotasint.recuperarRotasDemanda(demandasmb.getDemandas());
		}
		List<Rotas> listamenosum = new LinkedList<Rotas>();
		List<Rotas> rotasdemandas = demandasmb.getDemandas().getRotas();
		Iterator<Rotas> it = rotasdemandas.iterator();
		while(it.hasNext()){
			Rotas r = it.next();
				
			if(!r.getCnmrota().equals(rota.getCnmrota())){
				
				listamenosum.add(r);
			}
		}
		rotasremanescentes.addAll(listamenosum);
		demandasmb.getDemandas().setRotas(new HashSet<Rotas>(rotasremanescentes));
	
		fecharPopupConfirmacaoRemover();
	}
	
	public List<Rotas> recuperar(){
		
		rotas = new LinkedList<Rotas>();
		
		if(demandasmb.getDemandas().getIddemanda()!=null)
			rotas = rotasint.recuperarRotasDemanda(demandasmb.getDemandas());
		
		return rotas;
	}

	public Atividades getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividades atividade) {
		this.atividade = atividade;
	}

	public boolean isExiberota() {
		return exiberota;
	}

	public void setExiberota(boolean exiberota) {
		this.exiberota = exiberota;
	}

	public List<Rotas> getRotas() {
		return rotas;
	}

	public void setRotas(List<Rotas> rotas) {
		this.rotas = rotas;
	}

	public DemandasMB getDemandasmb() {
		return demandasmb;
	}

	public void setDemandasmb(DemandasMB demandasmb) {
		this.demandasmb = demandasmb;
	}

	public boolean isExibeconfirmacaoremover() {
		return exibeconfirmacaoremover;
	}

	public void setExibeconfirmacaoremover(boolean exibeconfirmacaoremover) {
		this.exibeconfirmacaoremover = exibeconfirmacaoremover;
	}

	public Rotas getRota() {
		return rota;
	}

	public void setRota(Rotas rota) {
		this.rota = rota;
	}

	public boolean isExibepopuprotas() {
		return exibepopuprotas;
	}

	public void setExibepopuprotas(boolean exibepopuprotas) {
		this.exibepopuprotas = exibepopuprotas;
	}

	public Ufs getUforigem() {
		return uforigem;
	}

	public void setUforigem(Ufs uforigem) {
		this.uforigem = uforigem;
	}

	public Centrais getCentralselecionadaorigem() {
		return centralselecionadaorigem;
	}

	public void setCentralselecionadaorigem(Centrais centralselecionadaorigem) {
		this.centralselecionadaorigem = centralselecionadaorigem;
	}

	public boolean isExibedetalhesrotaorigem() {
		return exibedetalhesrotaorigem;
	}

	public void setExibedetalhesrotaorigem(boolean exibedetalhesrotaorigem) {
		this.exibedetalhesrotaorigem = exibedetalhesrotaorigem;
	}

	public boolean isExibepopuprotaslupa() {
		return exibepopuprotaslupa;
	}

	public void setExibepopuprotaslupa(boolean exibepopuprotaslupa) {
		this.exibepopuprotaslupa = exibepopuprotaslupa;
	}

	public Ufs getUfdestino() {
		return ufdestino;
	}

	public void setUfdestino(Ufs ufdestino) {
		this.ufdestino = ufdestino;
	}

	public Centrais getCentralselecionadadestino() {
		return centralselecionadadestino;
	}

	public void setCentralselecionadadestino(Centrais centralselecionadadestino) {
		this.centralselecionadadestino = centralselecionadadestino;
	}

	public boolean isExibedetalhesrotadestino() {
		return exibedetalhesrotadestino;
	}

	public void setExibedetalhesrotadestino(boolean exibedetalhesrotadestino) {
		this.exibedetalhesrotadestino = exibedetalhesrotadestino;
	}

	public boolean isAproveitarrotaanterior() {
		return aproveitarrotaanterior;
	}

	public void setAproveitarrotaanterior(boolean aproveitarrotaanterior) {
		this.aproveitarrotaanterior = aproveitarrotaanterior;
	}

}
