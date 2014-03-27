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
@Table(name="AnalistasAquisicoes", schema="compras")
public class AnalistasAquisicoes implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1370435310699473143L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idanalistaaquisicao")
	private Long idanalistaaquisicao;	
	
	@OneToOne
	@JoinColumn(name="idgrupomodulo")
	private GruposModulos grupoModulo;
	
	@OneToOne
	@JoinColumn(name="idparticipante")
	private Participantes participanteanalaquisicao;	
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="UF_AnalistasAquisicoes", schema="compras",
			   joinColumns={@JoinColumn(name="idanalistaaquisicao", referencedColumnName="idanalistaaquisicao")},
			   inverseJoinColumns={@JoinColumn(name="iduf", referencedColumnName="iduf")})
	private Set<Ufs> ufs;
	
	public AnalistasAquisicoes() {
	}
	
	public Long getIdanalistaaquisicao() {
		return idanalistaaquisicao;
	}

	public void setIdanalistaaquisicao(Long idanalistaaquisicao) {
		this.idanalistaaquisicao = idanalistaaquisicao;
	}
	
	public Participantes getParticipanteanalaquisicao() {
		return participanteanalaquisicao;
	}

	public void setParticipanteanalaquisicao(Participantes participanteanalaquisicao) {
		this.participanteanalaquisicao = participanteanalaquisicao;
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
		return this.getIdanalistaaquisicao() != null ? 
		this.getClass().hashCode() + this.getIdanalistaaquisicao().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		AnalistasAquisicoes objint = (AnalistasAquisicoes)obj;
		
		if(this.getIdanalistaaquisicao() != null && objint.getIdanalistaaquisicao() != null){
			if(this.getIdanalistaaquisicao().equals(objint.getIdanalistaaquisicao())){
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
