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

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Deletes a product.
     * @param productDTO the product to be deleted
     * @return true
     */
    public boolean deleteProduct(ProductDTO productDTO) {
        productRepository.delete(modelMapper.map(productDTO, Product.class));
        return true;
    }

    /**
     * Retrieves all products.
     * @return a list of all products
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return modelMapper.map(products, new TypeToken<List<ProductDTO>>() {}.getType());
    }

    /**
     * Retrieves a product by its ID.
     * @param productId the ID of the product to retrieve
     * @return the product with the specified ID
     */
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.getReferenceById(productId);
        return modelMapper.map(product, ProductDTO.class);
    }

    /**
     * Saves a product.
     * @param productDTO the product to be saved
     * @return the saved product
     */
    public ProductDTO saveProduct(ProductDTO productDTO) {
        productRepository.save(modelMapper.map(productDTO, Product.class));
        return productDTO;
    }

    /**
     * Updates a product.
     * @param productDTO the product to be updated
     * @return the updated product
     */
    public ProductDTO updateProduct(ProductDTO productDTO) {
        productRepository.save(modelMapper.map(productDTO, Product.class));
        return productDTO;
    }
}
