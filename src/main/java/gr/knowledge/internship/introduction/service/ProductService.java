package gr.knowledge.internship.introduction.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gr.knowledge.internship.introduction.dto.ProductDTO;
import gr.knowledge.internship.introduction.entity.Product;
import gr.knowledge.internship.introduction.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional
public class ProductService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ProductRepository productRepository;

	/**
	 * Deletes a product.
	 *
	 * @param productDTO the product to be deleted
	 * @return true
	 */
	public void deleteProduct(ProductDTO productDTO) {
		log.debug("Tried deleting product with id: " + productDTO.getId());
		productRepository.delete(modelMapper.map(productDTO, Product.class));
	}

	/**
	 * Retrieves all products.
	 *
	 * @return a list of all products
	 */
	@Transactional(readOnly = true)
	public List<ProductDTO> getAllProducts() {
		List<Product> products = productRepository.findAll();
		log.debug("Requested all products. Returned" + products.size() + " items.");
		return modelMapper.map(products, new TypeToken<List<ProductDTO>>() {
		}.getType());
	}

	/**
	 * Retrieves a product by its ID.
	 *
	 * @param productId the ID of the product to retrieve
	 * @return the product with the specified ID
	 */
	@Transactional(readOnly = true)
	public ProductDTO getProductById(Long productId) {
		Product product = productRepository.getReferenceById(productId);
		log.debug("Requested to get product with id: " + productId);
		return modelMapper.map(product, ProductDTO.class);
	}

	/**
	 * Saves a product.
	 *
	 * @param productDTO the product to be saved
	 * @return the saved product
	 */
	public ProductDTO saveProduct(ProductDTO productDTO) {
		log.debug("Saved product: " + productDTO.toString());
		productRepository.save(modelMapper.map(productDTO, Product.class));
		return productDTO;
	}

	/**
	 * Updates a product.
	 *
	 * @param productDTO the product to be updated
	 * @return the updated product
	 */
	public ProductDTO updateProduct(ProductDTO productDTO) {
		log.debug("Updated product: " + productDTO.toString());
		productRepository.save(modelMapper.map(productDTO, Product.class));
		return productDTO;
	}
}
