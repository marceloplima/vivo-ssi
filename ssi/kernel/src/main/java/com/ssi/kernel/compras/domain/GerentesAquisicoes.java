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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ssi.kernel.model.GruposModulos;
import com.ssi.kernel.model.Ufs;

@Entity
@Cacheable
@Table(name="GerentesAquisicoes", schema="compras")
public class GerentesAquisicoes implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8754282946791898104L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idgerenteaquisicao")
	private Long idgerenteaquisicao;	
	
	@OneToOne
	@JoinColumn(name="idgrupomodulo")
	private GruposModulos grupoModulo;
	
	@OneToOne
	@JoinColumn(name="idparticipante")
	private Participantes participantegeraquisicoes;	
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="UF_GerentesAquisicoes", schema="compras",
			   joinColumns={@JoinColumn(name="idgerenteaquisicao", referencedColumnName="idgerenteaquisicao")},
			   inverseJoinColumns={@JoinColumn(name="iduf", referencedColumnName="iduf")})
	private Set<Ufs> ufs;
	
	public GerentesAquisicoes() {
	}
	
	public Long getIdgerenteaquisicao() {
		return idgerenteaquisicao;
	}

	public void setIdgerenteaquisicao(Long idgerenteaquisicao) {
		this.idgerenteaquisicao = idgerenteaquisicao;
	}
	
	public Participantes getParticipantegeraquisicoes() {
		return participantegeraquisicoes;
	}

	public void setParticipantegeraquisicoes(Participantes participantegeraquisicoes) {
		this.participantegeraquisicoes = participantegeraquisicoes;
	}
	
	public List<Ufs> getUfs() {
		if(ufs!=null){
			return new ArrayList<Ufs>(ufs);
		}
		else{
			return new ArrayList<Ufs>();
		}
	}

	public void setUfs(Set<Ufs> ufs) {
		this.ufs = ufs;
	}
	
	@Override
	public int hashCode() {
		return this.getIdgerenteaquisicao() != null ? 
		this.getClass().hashCode() + this.getIdgerenteaquisicao().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		GerentesAquisicoes objint = (GerentesAquisicoes)obj;
		
		if(this.getIdgerenteaquisicao() != null && objint.getIdgerenteaquisicao() != null){
			if(this.getIdgerenteaquisicao().equals(objint.getIdgerenteaquisicao())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

	public GruposModulos getGrupoModulo() {
		return grupoModulo;
	}

	public void setGrupoModulo(GruposModulos grupoModulo) {
		this.grupoModulo = grupoModulo;
	}
	
}
