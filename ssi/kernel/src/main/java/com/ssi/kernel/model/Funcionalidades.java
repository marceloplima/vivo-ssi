package com.ssi.kernel.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/** 
 * Classe de controle das funcionalidades
 * 
 * @see Funcionalidades funcionalidadepai
 * @see Funcionalidades funcionalidadefilhas
 * @see Modulos modulofuncionalidade
 * @see TiposFuncionalidade tipofuncionalidade
 * @see Componentes componentes
 */

@Entity
@Cacheable
@Table(name="Funcionalidades")
public class Funcionalidades implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4183029602257138226L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idfuncionalidade")
	private Long idfuncionalidade;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="idfuncionalidadepai", referencedColumnName="idfuncionalidade")
	private Funcionalidades funcionalidadepai;
	
	@OneToMany(mappedBy="funcionalidadepai", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Funcionalidades> funcionalidadesfilhas;
	
	@ManyToMany(mappedBy="gruposfuncionalidades", cascade=CascadeType.ALL)
	private Set<Grupos> grupos;
	
	@ManyToMany(mappedBy="pessoasfuncionalidades", cascade=CascadeType.ALL)
	private Set<Pessoas> pessoas;
	
	@ManyToOne
	@JoinColumn(name="idmodulo", referencedColumnName="idmodulo")
	private Modulos modulofuncionalidade;
	
	@ManyToOne
	@JoinColumn(name="idtipofuncionalidade", referencedColumnName="idtipofuncionalidade")
	private TiposFuncionalidade tipofuncionalidade;
	
	@Column(name="cnmfuncionalidade")
	private String cnmfuncionalidade;
	
	@Column(name="flagativo")
	private Integer flagativo;
	
	@Column(name="cnmaction")
	private String cnmaction;
	
	@Transient
	private boolean checked;
	
	public Long getIdfuncionalidade() {
		return idfuncionalidade;
	}
	public void setIdfuncionalidade(Long idfuncionalidade) {
		this.idfuncionalidade = idfuncionalidade;
	}
	public Funcionalidades getFuncionalidadepai() {
		return funcionalidadepai;
	}
	public void setFuncionalidadepai(Funcionalidades funcionalidadepai) {
		this.funcionalidadepai = funcionalidadepai;
	}
	
	public List<Funcionalidades> getFuncionalidadesfilhas() {
		return funcionalidadesfilhas;
	}
	public void setFuncionalidadesfilhas(List<Funcionalidades> funcionalidadesfilhas) {
		this.funcionalidadesfilhas = funcionalidadesfilhas;
	}
	
	public Modulos getModulofuncionalidade() {
		return modulofuncionalidade;
	}
	public void setModulofuncionalidade(Modulos modulofuncionalidade) {
		this.modulofuncionalidade = modulofuncionalidade;
	}
	public TiposFuncionalidade getTipofuncionalidade() {
		return tipofuncionalidade;
	}
	public void setTipofuncionalidade(TiposFuncionalidade tipofuncionalidade) {
		this.tipofuncionalidade = tipofuncionalidade;
	}
	
	public String getCnmfuncionalidade() {
		return cnmfuncionalidade;
	}
	public void setCnmfuncionalidade(String cnmfuncionalidade) {
		this.cnmfuncionalidade = cnmfuncionalidade;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getFlagativo() {
		return flagativo;
	}
	public void setFlagativo(Integer flagativo) {
		this.flagativo = flagativo;
	}
	
	public String getCnmaction() {
		return cnmaction;
	}
	public void setCnmaction(String cnmaction) {
		this.cnmaction = cnmaction;
	}
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List<Grupos> getGrupos() {
		if(grupos!=null)
			return new ArrayList<Grupos>(grupos);
		else
			return new ArrayList<Grupos>();
	}
	public void setGrupos(Set<Grupos> grupos) {
		this.grupos = grupos;
	}
	
	public Funcionalidades getByid(Long id){
		Funcionalidades obj = new Funcionalidades();
		obj.setIdfuncionalidade(id);
		return obj;
	}
	
	@Override
	public int hashCode() {
		return this.idfuncionalidade != null ? 
		this.getClass().hashCode() + this.idfuncionalidade.hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		Funcionalidades objint = (Funcionalidades)obj;
		
		if(this.getIdfuncionalidade() != null && objint.getIdfuncionalidade() != null){
			if(this.getIdfuncionalidade().equals(objint.getIdfuncionalidade())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}
	public List<Pessoas> getPessoas() {
		if(pessoas!=null)
			return new ArrayList<Pessoas>(pessoas);
		else
			return new ArrayList<Pessoas>();
	}
	public void setPessoas(Set<Pessoas> pessoas) {
		this.pessoas = pessoas;
	}

}
