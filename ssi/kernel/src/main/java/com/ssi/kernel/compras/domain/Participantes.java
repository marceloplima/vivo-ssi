package com.ssi.kernel.compras.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.ssi.kernel.model.Pessoas;

@Entity
@Cacheable
@Table(name="Participantes", schema="compras")
public class Participantes implements Serializable {

	private static final long serialVersionUID = -6186172476713745416L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idparticipante")
	private Long idparticipante;
	
	@ManyToOne
	@JoinColumn(name="idpessoaautor", referencedColumnName="idpessoa")
	private Pessoas autor;
	
	@ManyToOne
	@JoinColumn(name="idpapel", referencedColumnName="idpapel")
	private Papeis papel;
	
	@OneToOne(mappedBy="participantediretor", cascade=CascadeType.ALL)
	private Diretores diretor;
	
	@OneToOne(mappedBy="participanteemissor", cascade=CascadeType.ALL)
	private Emissores emissor;
	
	@OneToOne(mappedBy="participantegerorca", cascade=CascadeType.ALL)
	private GerentesOrcamentos gerenteorcamento;
	
	@OneToOne(mappedBy="participante", cascade=CascadeType.ALL)
	private ResponsaveisTecnicos responsaveltecnico;
	
	@OneToOne(mappedBy="participanteanalorca", cascade=CascadeType.ALL)
	private AnalistasOrcamentos analistaorcamento;
	
	@OneToOne(mappedBy="participantegercomp", cascade=CascadeType.ALL)
	private GerentesCompras gerentecompra;
	
	@OneToOne(mappedBy="participantecomprador", cascade=CascadeType.ALL)
	private Compradores comprador;
	
	@OneToOne(mappedBy="participantegeraquisicoes", cascade=CascadeType.ALL)
	private GerentesAquisicoes gerenteaquisicao;
	
	@OneToOne(mappedBy="participanteanalaquisicao", cascade=CascadeType.ALL)
	private AnalistasAquisicoes analistaaquisicao;
	
	@OneToOne(mappedBy="participantegerentecontrato", cascade=CascadeType.ALL)
	private GerentesContratos gerentecontrato;
	
	@OneToOne(mappedBy="participanteanalistacontrato", cascade=CascadeType.ALL)
	private AnalistasContratos analistacontrato;
	
	@OneToOne(mappedBy="participantecompradorlp2s", cascade=CascadeType.ALL)
	private CompradoresLP2s compradorlp2s;
	
	@OneToOne(mappedBy="participantecompradorlp2ro", cascade=CascadeType.ALL)
	private CompradoresLP2ro compradorlp2ro;
	
	@OneToOne(mappedBy="participantecompradorlp3", cascade=CascadeType.ALL)
	private CompradoresLP3 compradorlp3;
	
	@OneToOne(mappedBy="participantecompradorlp4", cascade=CascadeType.ALL)
	private CompradoresLP4 compradorlp4;
	
	@OneToOne(mappedBy="participantegercomplp2s", cascade=CascadeType.ALL)
	private GerentesComprasLP2s gerentecompralp2s;
	
	@OneToOne(mappedBy="participantegercomplp2ro", cascade=CascadeType.ALL)
	private GerentesComprasLP2ro gerentecompralp2ro;
	
	@OneToOne(mappedBy="participantegercomplp3", cascade=CascadeType.ALL)
	private GerentesComprasLP3 gerentecompralp3;
	
	@OneToOne(mappedBy="participantegercomplp4", cascade=CascadeType.ALL)
	private GerentesComprasLP4 gerentecompralp4;
	
	@Column(name="flagativo")
	private boolean flagativo;

	@Transient
	private boolean checked;
	
	@Column(name="datacadastro", insertable=false, updatable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar datacadastro;
	
	public Participantes(){}
	
	public Participantes(int idpapel) {
		//System.out.println(">>>>>>>>>> "+idpapel+" <<<<<<<<<<");
		switch(idpapel){
		
			case 1:
				emissor = new Emissores();
			break;
			
			case 2:
				responsaveltecnico = new ResponsaveisTecnicos();
			break;
			
			case 3:
				diretor = new Diretores();
			break;
			
			case 4:
				gerenteorcamento = new GerentesOrcamentos();
			break;
			
			case 5:
				analistaorcamento = new AnalistasOrcamentos();
			break;
			
			case 6:
				gerentecompra = new GerentesCompras();
			break;
			
			case 7:
				comprador = new Compradores();
			break;
			
			case 8:
				gerenteaquisicao = new GerentesAquisicoes();
			break;
			
			case 9:
				analistaaquisicao = new AnalistasAquisicoes();
			break;
			
			case 10:
				gerentecontrato = new GerentesContratos();
			break;
			
			case 11:
				analistacontrato = new AnalistasContratos();
			break;
			
			case 17:
				gerentecompralp2s = new GerentesComprasLP2s();
			break;
			
			case 16:
				gerentecompralp2ro = new GerentesComprasLP2ro();
			break;
			
			case 18:
				gerentecompralp3 = new GerentesComprasLP3();
			break;
			
			case 19:
				gerentecompralp4 = new GerentesComprasLP4();
			break;
			
			case 12:
				compradorlp2s = new CompradoresLP2s();
			break;
			
			case 13:
				compradorlp2ro = new CompradoresLP2ro();
			break;
			
			case 14:
				compradorlp3 = new CompradoresLP3();
			break;
			
			case 15:
				compradorlp4 = new CompradoresLP4();
			break;
			
		}
	}
	
	public Long getIdparticipante() {
		return idparticipante;
	}

	public void setIdparticipante(Long idparticipante) {
		this.idparticipante = idparticipante;
	}

	public Pessoas getAutor() {
		return autor;
	}

	public void setAutor(Pessoas autor) {
		this.autor = autor;
	}

	public Papeis getPapel() {
		return papel;
	}

	public void setPapel(Papeis papel) {
		this.papel = papel;
	}

	public Emissores getEmissor() {
		return emissor;
	}

	public void setEmissor(Emissores emissor) {
		this.emissor = emissor;
	}
		
	public ResponsaveisTecnicos getResponsaveltecnico() {
		return responsaveltecnico;
	}

	public void setResponsaveltecnico(ResponsaveisTecnicos responsaveltecnico) {
		this.responsaveltecnico = responsaveltecnico;
	}

	public boolean isFlagativo() {
		return flagativo;
	}

	public void setFlagativo(boolean flagativo) {
		this.flagativo = flagativo;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Calendar getDatacadastro() {
		return datacadastro;
	}

	public void setDatacadastro(Calendar datacadastro) {
		this.datacadastro = datacadastro;
	}

	public Diretores getDiretor() {
		return diretor;
	}

	public void setDiretor(Diretores diretor) {
		this.diretor = diretor;
	}

	public GerentesOrcamentos getGerenteorcamento() {
		return gerenteorcamento;
	}

	public void setGerenteorcamento(GerentesOrcamentos gerenteorcamento) {
		this.gerenteorcamento = gerenteorcamento;
	}
	
	public AnalistasOrcamentos getAnalistaorcamento() {
		return analistaorcamento;
	}

	public void setAnalistaorcamento(AnalistasOrcamentos analistaorcamento) {
		this.analistaorcamento = analistaorcamento;
	}
	
	public GerentesCompras getGerentecompra() {
		return gerentecompra;
	}

	public void setGerentecompra(GerentesCompras gerentecompra) {
		this.gerentecompra = gerentecompra;
	}
	
	public Compradores getComprador() {
		return comprador;
	}

	public void setComprador(Compradores comprador) {
		this.comprador = comprador;
	}
	
	public GerentesAquisicoes getGerenteaquisicao() {
		return gerenteaquisicao;
	}

	public void setGerenteaquisicao(GerentesAquisicoes gerenteaquisicao) {
		this.gerenteaquisicao = gerenteaquisicao;
	}
	
	public AnalistasAquisicoes getAnalistaaquisicao() {
		return analistaaquisicao;
	}

	public void setAnalistaaquisicao(AnalistasAquisicoes analistaaquisicao) {
		this.analistaaquisicao = analistaaquisicao;
	}
	
	public GerentesContratos getGerentecontrato() {
		return gerentecontrato;
	}

	public void setGerentecontrato(GerentesContratos gerentecontrato) {
		this.gerentecontrato = gerentecontrato;
	}

	public AnalistasContratos getAnalistacontrato() {
		return analistacontrato;
	}

	public void setAnalistacontrato(AnalistasContratos analistacontrato) {
		this.analistacontrato = analistacontrato;
	}
	
	@Override
	public int hashCode() {
		return this.getIdparticipante() != null ? 
		this.getClass().hashCode() + this.getIdparticipante().hashCode() : 
		super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		Participantes objint = (Participantes)obj;
		
		if(this.getIdparticipante() != null && objint.getIdparticipante() != null){
			if(this.getIdparticipante().equals(objint.getIdparticipante())){
				objint = null;
				return true;
			}
		}
		
		objint = null;
		
		return false;
	}

	public CompradoresLP2s getCompradorlp2s() {
		return compradorlp2s;
	}

	public void setCompradorlp2s(CompradoresLP2s compradorlp2s) {
		this.compradorlp2s = compradorlp2s;
	}

	public CompradoresLP2ro getCompradorlp2ro() {
		return compradorlp2ro;
	}

	public void setCompradorlp2ro(CompradoresLP2ro compradorlp2ro) {
		this.compradorlp2ro = compradorlp2ro;
	}

	public CompradoresLP3 getCompradorlp3() {
		return compradorlp3;
	}

	public void setCompradorlp3(CompradoresLP3 compradorlp3) {
		this.compradorlp3 = compradorlp3;
	}

	public CompradoresLP4 getCompradorlp4() {
		return compradorlp4;
	}

	public void setCompradorlp4(CompradoresLP4 compradorlp4) {
		this.compradorlp4 = compradorlp4;
	}

	public GerentesComprasLP2s getGerentecompralp2s() {
		return gerentecompralp2s;
	}

	public void setGerentecompralp2s(GerentesComprasLP2s gerentecompralp2s) {
		this.gerentecompralp2s = gerentecompralp2s;
	}

	public GerentesComprasLP2ro getGerentecompralp2ro() {
		return gerentecompralp2ro;
	}

	public void setGerentecompralp2ro(GerentesComprasLP2ro gerentecompralp2ro) {
		this.gerentecompralp2ro = gerentecompralp2ro;
	}

	public GerentesComprasLP3 getGerentecompralp3() {
		return gerentecompralp3;
	}

	public void setGerentecompralp3(GerentesComprasLP3 gerentecompralp3) {
		this.gerentecompralp3 = gerentecompralp3;
	}

	public GerentesComprasLP4 getGerentecompralp4() {
		return gerentecompralp4;
	}

	public void setGerentecompralp4(GerentesComprasLP4 gerentecompralp4) {
		this.gerentecompralp4 = gerentecompralp4;
	}
			
}
