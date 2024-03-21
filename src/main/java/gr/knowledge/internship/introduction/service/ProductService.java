package gr.knowledge.internship.introduction.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.knowledge.internship.introduction.dto.ProductDTO;
import gr.knowledge.internship.introduction.entity.Product;
import gr.knowledge.internship.introduction.repository.ProductRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ModelMapper modelMapper;

	public ProductDTO saveProduct(ProductDTO productDTO) {
		productRepository.save(modelMapper.map(productDTO, Product.class));
		return productDTO;
	}

	public List<ProductDTO> getAllProducts() {
		List<Product> products = productRepository.findAll();
		return modelMapper.map(products, new TypeToken<List<ProductDTO>>() {
		}.getType());
	}

	public ProductDTO getProductById(int productId) {
		Product product = productRepository.getReferenceById(productId);
		return modelMapper.map(product, ProductDTO.class);
	}

	public ProductDTO updateProduct(ProductDTO productDTO) {
		productRepository.save(modelMapper.map(productDTO, Product.class));
		return productDTO;
	}

	public boolean deleteProduct(ProductDTO productDTO) {
		productRepository.delete(modelMapper.map(productDTO, Product.class));
		return true;
	}
}
