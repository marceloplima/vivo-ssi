package com.ssi.kernel.compras.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name="Pareceres", schema="compras")
public class Pareceres implements Serializable {

	private static final long serialVersionUID = 1727187362494033478L;
	
	public static final long ID_PROCEDE = 1L;
	public static final long ID_PROCEDE_COM_RESSALVA = 2L;
	public static final long ID_IMPROCEDENTE = 3L;
	public static final long ID_REVISAO_PELO_EMISSOR = 4L;
	public static final long ID_DEVOLVER = 5L;
	public static final long ID_PARECER_REVISAO_LP = 6L;
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idparecer")
	private Long idparecer;
	
	@Column(name="cnmparecer")
	private String cnmparecer;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="Pareceres_Status", schema="compras",
			   joinColumns={@JoinColumn(name="idparecer", referencedColumnName="idparecer")},
			   inverseJoinColumns={@JoinColumn(name="idstatus", referencedColumnName="idstatus")})
	private Set<Status> status;
	
	public Pareceres(){}
	
	public Long getIdparecer() {
		return idparecer;
	}
	public void setIdparecer(Long idparecer) {
		this.idparecer = idparecer;
	}
	
	public String getCnmparecer() {
		return cnmparecer;
	}
	public void setCnmparecer(String cnmparecer) {
		this.cnmparecer = cnmparecer;
	}
	
	public Pareceres getByid(Long id){
		Pareceres obj = new Pareceres();
		obj.setIdparecer(id);
		return obj;
	}
	
	public List<Status> getStatus() {
		if(status!=null){
			return new ArrayList<Status>(status);
		}
		else{
			return new ArrayList<Status>();
		}
	}

	public void setStatus(Set<Status> status) {
		this.status = status;
	}
	
	@Override
	public int hashCode() {
		return this.getIdparecer() != null ? 
		this.getClass().hashCode() + this.getIdparecer().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Pareceres objint = (Pareceres)obj;
		
		if(this.getIdparecer() != null && objint.getIdparecer() != null){
			if(this.getIdparecer().equals(objint.getIdparecer())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
