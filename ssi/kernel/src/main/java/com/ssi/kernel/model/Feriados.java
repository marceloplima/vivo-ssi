package com.ssi.kernel.model;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name="Feriados")
public class Feriados implements Serializable{

	private static final long serialVersionUID = 1554426181810003801L;
	
	public static final long ID_CIDADE_FERIADO_LOCAL_PADRAO = 26L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idferiado")
	private Long idferiado;
	
	@Column(name="nome")
	private String nome;
				
	@OneToOne(mappedBy="feriadonacional", cascade=CascadeType.ALL)
	private FeriadosNacionais feriadosnacionais;

	@OneToOne(mappedBy="feriadolocal", cascade=CascadeType.ALL)
	private FeriadosLocais feriadosLocais;
	
	public Feriados() {}
						
	public Feriados(Long idferiado) {
		this.idferiado = idferiado;
	}

	public FeriadosNacionais getFeriadosnacionais() {
		return feriadosnacionais;
	}

	public void setFeriadosnacionais(FeriadosNacionais feriadosnacionais) {
		this.feriadosnacionais = feriadosnacionais;
	}

	public FeriadosLocais getFeriadosLocais() {
		return feriadosLocais;
	}

	public void setFeriadosLocais(FeriadosLocais feriadosLocais) {
		this.feriadosLocais = feriadosLocais;
	}

	public Long getIdferiado() {
		return idferiado;
	}

	public void setIdferiado(Long idferiado) {
		this.idferiado = idferiado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		return this.getIdferiado() != null ? 
		this.getClass().hashCode() + this.getIdferiado().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		Feriados objint = (Feriados)obj;
		
		if(this.getIdferiado() != null && objint.getIdferiado() != null){
			if(this.getIdferiado().equals(objint.getIdferiado())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

}
