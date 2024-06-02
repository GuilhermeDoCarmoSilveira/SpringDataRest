package silveira.carmo.guilherme.SpringDataJasper.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import silveira.carmo.guilherme.SpringDataJasper.model.dto.ProdutoDTO;
import silveira.carmo.guilherme.SpringDataJasper.model.entity.Produto;
import silveira.carmo.guilherme.SpringDataJasper.repository.IProdutoRepository;

@RestController
@RequestMapping("/api")
public class ProdutoController implements ICRUDController<ProdutoDTO> {

	@Autowired
	private IProdutoRepository pRep;

	@Override
	@GetMapping("/produto")
	public List<ProdutoDTO> lista() {
		List<Produto> produtos = pRep.findAll();
		List<ProdutoDTO> produtosDTO = convListProdProdDTO(produtos);
		return produtosDTO;
	}
	
	@Override
	@GetMapping("/produto/{codigoProd}")
    public ResponseEntity<ProdutoDTO> busca(@PathVariable(value = "codigoProd") int cod) {
        Produto p = pRep.findById(cod).orElseThrow(() -> new ResourceNotFoundException("Codigo Invalido"));
        ProdutoDTO pDTO = convProdProdDTO(p);
        return ResponseEntity.ok().body(pDTO);
    }

	@Override
	@PostMapping("/produto")
	public ResponseEntity<String> adiciona(@Valid @RequestBody ProdutoDTO pDTO) {
		Produto p = convProdDTOProd(pDTO);
		pRep.save(p);
		String saida = "Produto adicionado com sucesso";
		return ResponseEntity.ok().body(saida);
	}

	@Override
	@PutMapping("/produto")
	public ResponseEntity<String> atualiza(@Valid @RequestBody ProdutoDTO pDTO) {
		Produto p = convProdDTOProd(pDTO);
		pRep.save(p);
		String saida = "Produto Atualizado com sucesso";
		return ResponseEntity.ok().body(saida);
	}

	@Override
	@DeleteMapping("/produto")
	public ResponseEntity<String> exclui(ProdutoDTO pDTO) {
		Produto p = convProdDTOProd(pDTO);
		pRep.delete(p);
		String saida = "Produto Excluido com sucesso";
		return ResponseEntity.ok().body(saida);
	}
	
	@GetMapping("/produto/fn/{qtd}")
	public int qtdAbaixoEstoque(@PathVariable(value = "qtd") int qtd){
		qtd = pRep.fn_QtdProdutosAbaixoDoParam(qtd);
		return qtd;
	}
	
	private List<ProdutoDTO> convListProdProdDTO(List<Produto> produtos) {
		
		List<ProdutoDTO> produtosDTO = new ArrayList<ProdutoDTO>();
		for(Produto p : produtos) {
			ProdutoDTO pDTO = new ProdutoDTO();
			pDTO.setCodigo(p.getCodigo());
			pDTO.setNome(p.getNome());
			pDTO.setQtdEstoque(p.getQtdEstoque());
			pDTO.setValorUnitario(p.getValorUnitario());
		}
		return produtosDTO;
	}
	
	private ProdutoDTO convProdProdDTO(Produto p) {
		ProdutoDTO pDTO = new ProdutoDTO();
		pDTO.setCodigo(p.getCodigo());
		pDTO.setNome(p.getNome());
		pDTO.setQtdEstoque(p.getQtdEstoque());
		pDTO.setValorUnitario(p.getValorUnitario());
		return pDTO;
	}
	
	private Produto convProdDTOProd(ProdutoDTO pDTO) {
		Produto p = new Produto();
		p.setCodigo(pDTO.getCodigo());
		p.setNome(pDTO.getNome());
		p.setQtdEstoque(pDTO.getQtdEstoque());
		p.setValorUnitario(pDTO.getValorUnitario());
		return p;
	}


}
