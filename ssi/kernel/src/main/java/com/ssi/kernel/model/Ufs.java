package com.ssi.kernel.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ssi.kernel.compras.domain.Orcamentos;

@Entity
@Cacheable
@Table(name="UF")
public class Ufs implements Serializable {

	private static final long serialVersionUID = 9162906109081234537L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="iduf")	
	private Long iduf;
	
	@Column(name="cnmuf")
	private String cnmuf;
	
	@OneToMany(mappedBy="ufs", cascade = CascadeType.ALL)
	private Set<Orcamentos> orcamentos;
	
	@OneToMany(mappedBy="ufferiado", cascade = CascadeType.ALL)
	private Set<FeriadosLocais> feriadoslocais;
	
//	@ManyToMany(mappedBy="ufsregionais", cascade=CascadeType.ALL)
//	private Set<Regionais>regionaisufs;
	
	@Transient
	private boolean checked;
	
	public Ufs() {}
		
	public Ufs(Long iduf) {
		this.iduf = iduf;
	}



	public Long getIduf() {
		return iduf;
	}

	public void setIduf(Long iduf) {
		this.iduf = iduf;
	}
		
	public Set<FeriadosLocais> getFeriadoslocais() {
		return feriadoslocais;
	}

	public void setFeriadoslocais(Set<FeriadosLocais> feriadoslocais) {
		this.feriadoslocais = feriadoslocais;
	}

	public String getCnmuf() {
		return cnmuf;
	}

	public void setCnmuf(String cnmuf) {
		this.cnmuf = cnmuf;
	}
			
	public Set<Orcamentos> getOrcamentos() {
		return orcamentos;
	}

	public void setOrcamentos(Set<Orcamentos> orcamentos) {
		this.orcamentos = orcamentos;
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
//	public List<Regionais> getRegionaisufs() {
//		if(regionaisufs != null && regionaisufs.size() >= 1)
//			return new ArrayList<Regionais>(regionaisufs);
//		else
//			return new ArrayList<Regionais>();
//	}
//
//	public void setRegionaisufs(Set<Regionais> regionaisufs) {
//		this.regionaisufs = regionaisufs;
//	}

	@Override
	public int hashCode() {
		return this.getIduf() != null ? 
		this.getClass().hashCode() + this.getIduf().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		Ufs objint = (Ufs)obj;
		
		if(this.getIduf() != null && objint.getIduf() != null){
			if(this.getIduf().equals(objint.getIduf())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
