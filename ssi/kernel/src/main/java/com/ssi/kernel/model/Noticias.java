package com.ssi.kernel.model;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/** 
 * Classe que � utilizada por todos os m�dulos (aplica��es SSI) e representa as not�cias exibidas ao usu�rio
 */

@Entity
@Cacheable
@Table(name="Noticias")
public class Noticias implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2519039303980966837L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idnoticia")
	private Long idnoticia;
	
	@Column(name="cnmtitulo")
	private String cnmtitulo;
	
	@Column(name="cnmnoticia")
	private String cnmnoticia;
	
	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;
	
	@Transient
	private boolean checked;
	
	@Column(name="flagativo", columnDefinition="default true")
	private boolean flagativo;
	
	@ManyToMany(cascade=CascadeType.PERSIST)
	@JoinTable(name="Noticias_Modulos",
			   joinColumns={@JoinColumn(name="idnoticia", referencedColumnName="idnoticia")},
			   inverseJoinColumns={@JoinColumn(name="idmodulo", referencedColumnName="idmodulo")})
	private Set<Modulos>noticiasmodulos;
	
	public String getCnmtitulo() {
		return cnmtitulo;
	}
	public void setCnmtitulo(String cnmtitulo) {
		this.cnmtitulo = cnmtitulo;
	}
	
	public Long getIdnoticia() {
		return idnoticia;
	}
	public void setIdnoticia(Long idnoticia) {
		this.idnoticia = idnoticia;
	}
	public String getCnmnoticia() {
		return cnmnoticia;
	}
	public void setCnmnoticia(String cnmnoticia) {
		this.cnmnoticia = cnmnoticia;
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
	public boolean isFlagativo() {
		return flagativo;
	}
	public void setFlagativo(boolean flagativo) {
		this.flagativo = flagativo;
	}

	public List<Modulos> getNoticiasmodulos() {
		if(noticiasmodulos!=null)
			return new ArrayList<Modulos>(noticiasmodulos);
		else
			return new ArrayList<Modulos>();
	}
	public void setNoticiasmodulos(Set<Modulos> noticiasmodulos) {
		this.noticiasmodulos = noticiasmodulos;
	}
	
	public void remapearItens(Noticias noticia){
		if(!this.getNoticiasmodulos().isEmpty()){
			Iterator<Modulos>it = this.getNoticiasmodulos().iterator();
			while(it.hasNext()){
				Modulos obj = it.next();
				List<Noticias>l = new ArrayList<Noticias>();
				l.add(noticia);
				obj.setModulosnoticias(l);
			}
		}
	}
	
	@Override
	public int hashCode() {
		return this.idnoticia != null ? 
		this.getClass().hashCode() + this.idnoticia.hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		Noticias objint = (Noticias)obj;
		
		if(this.getIdnoticia() != null && objint.getIdnoticia() != null){
			if(this.getIdnoticia().equals(objint.getIdnoticia())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
