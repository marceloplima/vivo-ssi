package com.ssi.kernel.redes.domain;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name="transfssi", schema="redes")
public class Transferencia implements Serializable {

	private static final long serialVersionUID = -9199730664882510465L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idtransfssi")
	private Long idtrasferencia;
	
	@Column(name="idaplicacaotransf")
	private Long idaplicacaotransf;
	
	@Column(name="secaorequisitante")
	private String secaorequisitante;
	
	@Column(name="idinstrucao")
	private Long idinstrucao;
	
	@Column(name="secaoprincipal")
	private String secaoprincipal;
	
	@Column(name="arearesponsavel")
	private String arearesponsavel;
	
	@Column(name="numerossi")
	private String numerossi;
	
	@Column(name="metadata")
	private String metadata;
	
	@Column(name="iddocumento")
	private String iddocumento;
	
	public Transferencia(){}

	public Long getIdtrasferencia() {
		return idtrasferencia;
	}

	public void setIdtrasferencia(Long idtrasferencia) {
		this.idtrasferencia = idtrasferencia;
	}

	public Long getIdaplicacaotransf() {
		return idaplicacaotransf;
	}

	public void setIdaplicacaotransf(Long idaplicacaotransf) {
		this.idaplicacaotransf = idaplicacaotransf;
	}

	public String getSecaorequisitante() {
		return secaorequisitante;
	}

	public void setSecaorequisitante(String secaorequisitante) {
		this.secaorequisitante = secaorequisitante;
	}

	public Long getIdinstrucao() {
		return idinstrucao;
	}

	public void setIdinstrucao(Long idinstrucao) {
		this.idinstrucao = idinstrucao;
	}

	public String getSecaoprincipal() {
		return secaoprincipal;
	}

	public void setSecaoprincipal(String secaoprincipal) {
		this.secaoprincipal = secaoprincipal;
	}

	public String getArearesponsavel() {
		return arearesponsavel;
	}

	public void setArearesponsavel(String arearesponsavel) {
		this.arearesponsavel = arearesponsavel;
	}

	public String getNumerossi() {
		return numerossi;
	}

	public void setNumerossi(String numerossi) {
		this.numerossi = numerossi;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public String getIddocumento() {
		return iddocumento;
	}

	public void setIddocumento(String iddocumento) {
		this.iddocumento = iddocumento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idaplicacaotransf == null) ? 0 : idaplicacaotransf
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transferencia other = (Transferencia) obj;
		if (idaplicacaotransf == null) {
			if (other.idaplicacaotransf != null)
				return false;
		} else if (!idaplicacaotransf.equals(other.idaplicacaotransf))
			return false;
		return true;
	}
	
	

}
