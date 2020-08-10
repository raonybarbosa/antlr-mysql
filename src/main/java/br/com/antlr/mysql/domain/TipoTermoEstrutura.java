package br.com.antlr.mysql.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tipo_termo_estrutura")
public class TipoTermoEstrutura {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "codigo_tipo_termo")
	private int codigoTipoTermo;
	@Column(name = "nome_tipo_termo")
	private String nomeTipoTermo;
}
