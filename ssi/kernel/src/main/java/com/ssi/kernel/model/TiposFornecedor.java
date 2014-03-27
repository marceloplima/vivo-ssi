package com.ssi.kernel.model;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/** 
 * Representa os tipos de fornecedores existentes que participam do sistema SSI
 * 
 * @see Fornecedores fornecedores
 */


@Entity
@Cacheable
@Table(name="TiposFornecedor")
public class TiposFornecedor implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5262403131685107130L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idtipofornecedor")
	private Long idtipofornecedor;
	
	@Column(name="cnmtipofornecedor")
	private String cnmtipofornecedor;
	
	@ManyToMany(mappedBy="tiposfornecedor", cascade=CascadeType.ALL)
	private List<Fornecedores>fornecedores;
	
	@Column(name="flagativo")
	private boolean flagativo;
	
	@Transient
	private boolean checked;
	
	public Long getIdtipofornecedor() {
		return idtipofornecedor;
	}
	public void setIdtipofornecedor(Long idtipofornecedor) {
		this.idtipofornecedor = idtipofornecedor;
	}
	public String getCnmtipofornecedor() {
		return cnmtipofornecedor;
	}
	public void setCnmtipofornecedor(String cnmtipofornecedor) {
		this.cnmtipofornecedor = cnmtipofornecedor;
	}
	
	public List<Fornecedores> getFornecedores() {
		return fornecedores;
	}
	public void setFornecedores(List<Fornecedores> fornecedores) {
		this.fornecedores = fornecedores;
	}
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public boolean isFlagativo() {
		return flagativo;
	}
	public void setFlagativo(boolean flagativo) {
		this.flagativo = flagativo;
	}
	
	public TiposFornecedor getByid(Long id){
		TiposFornecedor obj = new TiposFornecedor();
		obj.setIdtipofornecedor(id);
		return obj;
	}
	
	@Override
	public int hashCode() {
		return this.idtipofornecedor != null ? 
		this.getClass().hashCode() + this.idtipofornecedor.hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		TiposFornecedor objint = (TiposFornecedor)obj;
		
		if(this.getIdtipofornecedor() != null && objint.getIdtipofornecedor() != null){
			if(this.getIdtipofornecedor().equals(objint.getIdtipofornecedor())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}
}
