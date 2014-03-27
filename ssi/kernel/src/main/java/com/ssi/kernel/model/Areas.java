package com.ssi.kernel.model;

import java.util.Calendar;
import java.util.List;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/** Classe da Estrutura organizacional da Empresa
 * 
 * @see Areas areapai
 * @see Areas areasfilho
 * @see Atividades areasatividades
 * @see Pessoas areaspessoas
 */

@Entity
@Cacheable
@Table(name="Areas")
public class Areas implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4469843121189223752L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idarea")
	private Long idarea;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="idareapai", referencedColumnName="idarea")
	private Areas areapai;
	
	@OneToMany(mappedBy="areapai", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Areas> areasfilho;
	
	@Column(length=20, name="cnmsiglaarea")
	private String cnmsiglaarea;
	
	@Column(name="cnmdescarea")
	private String cnmdescarea;
	
	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;
	
	@ManyToMany(mappedBy="atividadesareas", cascade=CascadeType.ALL)
	private List<Atividades> areasatividades;
	
	@ManyToMany(mappedBy = "pessoasareas", cascade=CascadeType.ALL)
	private List<Pessoas> areaspessoas;
	
	@Column(name="flagativo")
	private boolean flagativo;
	
	@Transient
	private boolean checked;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="idpapelarea", referencedColumnName="idpapelarea")
	private PapeisAreas papelarea;
	
	public Long getIdarea() {
		return idarea;
	}
	public void setIdarea(Long idarea) {
		this.idarea = idarea;
	}
	public Areas getAreapai() {
		return areapai;
	}
	public void setAreapai(Areas areapai) {
		this.areapai = areapai;
	}
	
	public List<Areas> getAreasfilho() {
		return areasfilho;
	}
	public void setAreasfilho(List<Areas> areasfilho) {
		this.areasfilho = areasfilho;
	}
	public String getCnmsiglaarea() {
		return cnmsiglaarea;
	}
	public void setCnmsiglaarea(String cnmsiglaarea) {
		this.cnmsiglaarea = cnmsiglaarea;
	}
	public String getCnmdescarea() {
		return cnmdescarea;
	}
	public void setCnmdescarea(String cnmdescarea) {
		this.cnmdescarea = cnmdescarea;
	}
	public Calendar getDatacadastro() {
		return datacadastro;
	}
	public void setDatacadastro(Calendar datacadastro) {
		this.datacadastro = datacadastro;
	}
	public List<Atividades> getAreasatividades() {
		return areasatividades;
	}
	public void setAreasatividades(List<Atividades> areasatividades) {
		this.areasatividades = areasatividades;
	}
	
	public List<Pessoas> getAreaspessoas() {
		return areaspessoas;
	}
	public void setAreaspessoas(List<Pessoas> areaspessoas) {
		this.areaspessoas = areaspessoas;
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
	
	public Areas getByid(Long id){
		Areas obj = new Areas();
		obj.setIdarea(id);
		return obj;
	}
	
	@Override
	public int hashCode() {
		return this.idarea != null ? 
		this.getClass().hashCode() + this.idarea.hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		Areas objint = (Areas)obj;
		
		if(this.getIdarea() != null && objint.getIdarea() != null){
			if(this.getIdarea().equals(objint.getIdarea())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}
	public PapeisAreas getPapelarea() {
		return papelarea;
	}
	public void setPapelarea(PapeisAreas papelarea) {
		this.papelarea = papelarea;
	}
	
}
