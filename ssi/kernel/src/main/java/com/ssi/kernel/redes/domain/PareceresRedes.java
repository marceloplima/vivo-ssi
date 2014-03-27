package com.ssi.kernel.redes.domain;

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
@Table(name="PareceresRedes", schema="redes")
public class PareceresRedes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 768755601055541485L;
	public static final long ID_PROCEDE = 1L;
	public static final long ID_PROCEDE_COM_RESSALVA = 2L;
	public static final long ID_NECESSIDADE_DADOS = 3L;
	public static final long ID_IMPROCEDENTE = 4L;
	public static final long ID_EXECUCAO_TOTAL = 5L;
	public static final long ID_EXECUCAO_PARCIAL = 6L;
	public static final long ID_CANCELAR = 7L;
	public static final long ID_REENCAMINHAR = 8L;
	public static final long ID_SEM_SUCESSO = 9L;
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idparecer")
	private Long idparecer;
	
	@Column(name="cnmparecer")
	private String cnmparecer;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="PareceresRedes_StatusRedes", schema="redes",
			   joinColumns={@JoinColumn(name="idparecer", referencedColumnName="idparecer")},
			   inverseJoinColumns={@JoinColumn(name="idstatus", referencedColumnName="idstatus")})
	private Set<StatusRedes> status;
	
	public PareceresRedes(){}
	
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
	
	public PareceresRedes getByid(Long id){
		PareceresRedes obj = new PareceresRedes();
		obj.setIdparecer(id);
		return obj;
	}
	
	public List<StatusRedes> getStatus() {
		if(status!=null){
			return new ArrayList<StatusRedes>(status);
		}
		else{
			return new ArrayList<StatusRedes>();
		}
	}

	public void setStatus(Set<StatusRedes> status) {
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
		PareceresRedes objint = (PareceresRedes)obj;
		
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
