package com.ssi.kernel.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name="PapeisAreas")
public class PapeisAreas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1735387108919459023L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idpapelarea")
	private Long idpapelarea;
	
	@Column(length=50, name="cnmpapelarea")
	private String cnmpapelarea;
	
	@OneToMany(mappedBy="papelarea", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Areas> areas;

	public PapeisAreas getByid(Long id){
		PapeisAreas obj = new PapeisAreas();
		obj.setIdpapelarea(id);
		return obj;
	}
	
	public Long getIdpapelarea() {
		return idpapelarea;
	}

	public void setIdpapelarea(Long idpapelarea) {
		this.idpapelarea = idpapelarea;
	}
	
	public String getCnmpapelarea() {
		return cnmpapelarea;
	}

	public void setCnmpapelarea(String cnmpapelarea) {
		this.cnmpapelarea = cnmpapelarea;
	}
	
	@Override
	public int hashCode() {
		return this.idpapelarea != null ? 
		this.getClass().hashCode() + this.idpapelarea.hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		PapeisAreas objint = (PapeisAreas)obj;
		
		if(this.getIdpapelarea() != null && objint.getIdpapelarea() != null){
			if(this.getIdpapelarea().equals(objint.getIdpapelarea())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

	public List<Areas> getAreas() {
		return areas;
	}

	public void setAreas(List<Areas> areas) {
		this.areas = areas;
	}	

}
