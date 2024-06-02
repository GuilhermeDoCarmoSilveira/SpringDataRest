package silveira.carmo.guilherme.SpringDataJasper.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProdutoDTO {
	

	private int codigo;
	private String nome;
	private double valorUnitario ;
	private int qtdEstoque ;
	
}
