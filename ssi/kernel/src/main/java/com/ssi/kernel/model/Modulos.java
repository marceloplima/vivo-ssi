package com.ssi.kernel.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/** 
 * Classe que representa os m�dulos (aplica��es SSI) que se conectam com o kernel central
 * 
 * @see Funcionalidades funcionalidades
 */

@Entity
@Cacheable
@Table(name="Modulos")
public class Modulos implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5834913225029361929L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idmodulo")
	private Long idmodulo;
	
	@Column(name="cnmmodulo")
	private String cnmmodulo;
	
	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;
	
	@OneToMany(mappedBy="modulofuncionalidade", cascade=CascadeType.REMOVE)
	private List<Funcionalidades> funcionalidadesmodulo;
	
	@OneToOne
	@JoinColumn(name="idsmtp")
	private ConfigSMTP configsmtp;

	@OneToMany(mappedBy="modulo", cascade=CascadeType.ALL)
	private List<GruposModulos> gruposmodulos;
	
	@ManyToMany(mappedBy = "noticiasmodulos", cascade=CascadeType.ALL)
	private List<Noticias> modulosnoticias;
	
	@Column(name="flagativo")
	private Integer flagativo;
	
	@Transient
	private boolean checked;
	
	public Long getIdmodulo() {
		return idmodulo;
	}
	public void setIdmodulo(Long idmodulo) {
		this.idmodulo = idmodulo;
	}

	public String getCnmmodulo() {
		return cnmmodulo;
	}
	public void setCnmmodulo(String cnmmodulo) {
		this.cnmmodulo = cnmmodulo;
	}
	public Calendar getDatacadastro() {
		return datacadastro;
	}
	public void setDatacadastro(Calendar datacadastro) {
		this.datacadastro = datacadastro;
	}
	public List<Funcionalidades> getFuncionalidadesmodulo() {
		return funcionalidadesmodulo;
	}
	public void setFuncionalidadesmodulo(List<Funcionalidades> funcionalidadesmodulo) {
		this.funcionalidadesmodulo = funcionalidadesmodulo;
	}
	public Integer getFlagativo() {
		return flagativo;
	}
	public void setFlagativo(Integer flagativo) {
		this.flagativo = flagativo;
	}
	public ConfigSMTP getConfigsmtp() {
		return configsmtp;
	}
	public void setConfigsmtp(ConfigSMTP configsmtp) {
		this.configsmtp = configsmtp;
	}
	
	public List<GruposModulos> getGruposmodulos() {
		return gruposmodulos;
	}
	public void setGruposmodulos(List<GruposModulos> gruposmodulos) {
		this.gruposmodulos = gruposmodulos;
	}
	

	public List<Noticias> getModulosnoticias() {
		return modulosnoticias;
	}
	public void setModulosnoticias(List<Noticias> modulosnoticias) {
		this.modulosnoticias = modulosnoticias;
	}
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	
	public Modulos getByid(Long id){
		Modulos obj = new Modulos();
		obj.setIdmodulo(id);
		return obj;
	}
	
	@Override
	public int hashCode() {
		return this.idmodulo != null ? 
		this.getClass().hashCode() + this.idmodulo.hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		Modulos objint = (Modulos)obj;
		
		if(this.getIdmodulo() != null && objint.getIdmodulo() != null){
			if(this.getIdmodulo().equals(objint.getIdmodulo())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}
	
}
