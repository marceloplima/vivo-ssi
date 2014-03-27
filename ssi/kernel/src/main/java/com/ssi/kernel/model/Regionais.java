package com.ssi.kernel.model;

import java.io.Serializable;
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

import com.ssi.kernel.redes.domain.DemandasRedes;

@Entity
@Cacheable
@Table(name="Regionais")
public class Regionais implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -772310265412779062L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idregional")
	private Long idregional;
	
	@Column(name="cnmregional")
	private String cnmregional;
	
	@Transient
	private boolean checked;
	
//	@ManyToMany(cascade=CascadeType.ALL)
//	@JoinTable(name="Regionais_UF",
//			   joinColumns={@JoinColumn(name="idregional", referencedColumnName="idregional")},
//			   inverseJoinColumns={@JoinColumn(name="iduf", referencedColumnName="iduf")})
//	private Set<Ufs> ufsregionais;

	@ManyToMany(mappedBy="regionaisdemandas", cascade = CascadeType.REFRESH)
	private List<DemandasRedes> demandasregionais;
	
	public Long getIdregional() {
		return idregional;
	}

	public void setIdregional(Long idregional) {
		this.idregional = idregional;
	}

	public String getCnmregional() {
		return cnmregional;
	}

	public void setCnmregional(String cnmregional) {
		this.cnmregional = cnmregional;
	}

//	public List<Ufs> getUfsregionais() {
//		if(ufsregionais != null && ufsregionais.size() >= 1)
//			return new ArrayList<Ufs>(ufsregionais);
//		else
//			return new ArrayList<Ufs>();
//	}
//
//	public void setUfsregionais(Set<Ufs> ufsregionais) {
//		this.ufsregionais = ufsregionais;
//	}
	
	@Override
	public int hashCode() {
		return this.getIdregional() != null ? 
		this.getClass().hashCode() + this.getIdregional().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Regionais objint = (Regionais)obj;
		
		if(this.getIdregional() != null && objint.getIdregional() != null){
			if(this.getIdregional().equals(objint.getIdregional())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<DemandasRedes> getDemandasregionais() {
		return demandasregionais;
	}

	public void setDemandasregionais(List<DemandasRedes> demandasregionais) {
		this.demandasregionais = demandasregionais;
	}

}
