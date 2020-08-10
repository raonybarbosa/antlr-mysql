package br.com.antlr.mysql.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "comando_estrutura")
public class ComandoEstrutura {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "codigo_comando")
	private int codigoComando;
	@Column(name = "codigo_tipo_termo")
	private int codigoTipoTermo;
	@Column(name = "nome_comando")
	private String nomeComando;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ManyToOne
	private TipoTermoEstrutura tipoTermoEstrutura;
}
