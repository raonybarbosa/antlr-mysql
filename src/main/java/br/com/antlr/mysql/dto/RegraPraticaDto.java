package br.com.antlr.mysql.dto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegraPraticaDto {

	private String tipoScript;
	private List<RegrasDto> listaOrdenadaDeRegras;

	public RegraPraticaDto() {
		listaOrdenadaDeRegras = new ArrayList();
		this.setTipoScript("create_table_stmt");
		RegrasDto regras2 = new RegrasDto();
		regras2.setNomeRepresentativoRegra("external");
		regras2.setNivelVerticalOrdem(1);
		RegrasDto regras3 = new RegrasDto();
		regras3.setNomeRepresentativoRegra("partitioned");
		regras3.setNivelVerticalOrdem(2);
		RegrasDto regras4 = new RegrasDto();
		regras4.setNomeRepresentativoRegra("location");
		regras4.setNivelVerticalOrdem(3);

		listaOrdenadaDeRegras.add(regras2);
		listaOrdenadaDeRegras.add(regras3);
		listaOrdenadaDeRegras.add(regras4);
		listaOrdenadaDeRegras = listaOrdenadaDeRegras.stream()
				.sorted(Comparator.comparing(RegrasDto::getNivelVerticalOrdem)).collect(Collectors.toList());
	}
}
