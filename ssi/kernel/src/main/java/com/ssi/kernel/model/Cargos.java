package com.ssi.kernel.model;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name="Cargos")
public class Cargos implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8405601422188680681L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idcargo")
	private Long idcargo;
	
	@Column(name="cnmcargo")
	private String cnmcargo;

	@OneToMany(mappedBy="cargo")
	private List<Pessoas> pessoas;
	
	public Cargos getByid(Long id) {
		Cargos c = new Cargos();
		c.setIdcargo(id);
		return c;
	}
	
	public Long getIdcargo() {
		return idcargo;
	}

	public void setIdcargo(Long idcargo) {
		this.idcargo = idcargo;
	}

	public String getCnmcargo() {
		return cnmcargo;
	}

	public void setCnmcargo(String cnmcargo) {
		this.cnmcargo = cnmcargo;
	}
	
	public List<Pessoas> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoas> pessoas) {
		this.pessoas = pessoas;
	}
	
	@Override
	public int hashCode() {
		return this.idcargo != null ? 
		this.getClass().hashCode() + this.idcargo.hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		Cargos objint = (Cargos)obj;
		
		if(this.getIdcargo() != null && objint.getIdcargo() != null){
			if(this.getIdcargo().equals(objint.getIdcargo())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}
}
