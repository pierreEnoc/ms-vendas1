package com.pierredev.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pierredev.data.vo.ProdutoVO;
import com.pierredev.entities.Produto;
import com.pierredev.exception.ResourceNotfoundException;
import com.pierredev.repositories.ProdutoRepository;

@Service
public class ProdutoService {
	
	//private final ProdutoRepository produtoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
		
	public ProdutoVO create(ProdutoVO produtoVO) {
		ProdutoVO proVoRetorno = ProdutoVO.create(produtoRepository.save(Produto.create(produtoVO)));
		return  proVoRetorno;
	}
	
	public Page<ProdutoVO> findAll(Pageable pageable) {
		
		var page = produtoRepository.findAll(pageable);
			return page.map(this::convertToProdutoVO);
	}
	
	private ProdutoVO convertToProdutoVO(Produto produto) {
		return ProdutoVO.create(produto);
	}
	
	public ProdutoVO findById(Long id) {
		var entity = produtoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotfoundException("No records found for this id"));
		return ProdutoVO.create(entity);
	}
	
	public ProdutoVO update(ProdutoVO produtoVO) {
		final Optional<Produto> optionalProduto = produtoRepository.findById(produtoVO.getId());
		if(!optionalProduto.isPresent()) {
			new ResourceNotfoundException("No records found for this id");
		}
		return ProdutoVO.create(produtoRepository.save(Produto.create(produtoVO)));
	}
	
	public void delete(Long id) {
		var entity = produtoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotfoundException("No records found for this id"));
		produtoRepository.delete(entity);
	}
	
	
}
