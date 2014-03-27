package com.ssi.kernel.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.ssi.kernel.compras.domain.FornecedoresAgendamentos;
import com.ssi.kernel.compras.domain.Propostas;

/** Classe da Estrutura organizacional da empresa
 * @see TiposFornecedor
 */
@Entity
@Cacheable
@Table(name="Fornecedores")
public class Fornecedores implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8572002303930767031L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idfornecedor")
	private Long idfornecedor;
	
	@Column(name="cnmfornecedor")
	private String cnmfornecedor;
	
	@Column(name="flagativo")
	private boolean flagativo;
	
	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="Fornecedores_TiposFornecedor",
			   joinColumns={@JoinColumn(name="idfornecedor", referencedColumnName="idfornecedor")},
			   inverseJoinColumns={@JoinColumn(name="idtipofornecedor", referencedColumnName="idtipofornecedor")})
	private Set<TiposFornecedor>tiposfornecedor; 
	
	@OneToMany(mappedBy="fornecedorprop", cascade=CascadeType.ALL)
	private List<Propostas> propostas;
	
	@OneToMany(mappedBy="fornecedores", cascade = CascadeType.ALL)
	private Set<FornecedoresAgendamentos> fornecedoresagendamentos;	
	
	@Transient
	private boolean checked;
	
	public void remapearItens(Fornecedores fornecedor){
		if(!this.getTiposfornecedor().isEmpty()){
			Iterator<TiposFornecedor>it = this.getTiposfornecedor().iterator();
			while(it.hasNext()){
				TiposFornecedor obj = it.next();
				List<Fornecedores>lista = new ArrayList<Fornecedores>();
				lista.add(fornecedor);
				obj.setFornecedores(lista);
			}
		}
	}
			
	public Long getIdfornecedor() {
		return idfornecedor;
	}
	public void setIdfornecedor(Long idfornecedor) {
		this.idfornecedor = idfornecedor;
	}
	public String getCnmfornecedor() {
		return cnmfornecedor;
	}
	public void setCnmfornecedor(String cnmfornecedor) {
		this.cnmfornecedor = cnmfornecedor;
	}
	public List<TiposFornecedor> getTiposfornecedor() {
		if(tiposfornecedor!=null)
			return new ArrayList<TiposFornecedor>(tiposfornecedor);
		else
			return new ArrayList<TiposFornecedor>();
	}
	public void setTiposfornecedor(Set<TiposFornecedor> tiposfornecedor) {
		this.tiposfornecedor = tiposfornecedor;
	}
	
	public boolean isFlagativo() {
		return flagativo;
	}
	public void setFlagativo(boolean flagativo) {
		this.flagativo = flagativo;
	}
	public Calendar getDatacadastro() {
		return datacadastro;
	}
	public void setDatacadastro(Calendar datacadastro) {
		this.datacadastro = datacadastro;
	}

	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public List<Propostas> getPropostas() {
		return propostas;
	}

	public void setPropostas(List<Propostas> propostas) {
		this.propostas = propostas;
	}
					
	public Set<FornecedoresAgendamentos> getFornecedoresagendamentos() {
		return fornecedoresagendamentos;
	}

	public void setFornecedoresagendamentos(
			Set<FornecedoresAgendamentos> fornecedoresagendamentos) {
		this.fornecedoresagendamentos = fornecedoresagendamentos;
	}

	@Override
	public int hashCode() {
		return this.idfornecedor != null ? 
		this.getClass().hashCode() + this.idfornecedor.hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		Fornecedores objint = (Fornecedores)obj;
		
		if(this.getIdfornecedor() != null && objint.getIdfornecedor() != null){
			if(this.getIdfornecedor().equals(objint.getIdfornecedor())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
