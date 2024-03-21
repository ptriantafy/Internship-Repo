package gr.knowledge.internship.introduction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr.knowledge.internship.introduction.dto.ProductDTO;
import gr.knowledge.internship.introduction.service.ProductService;
@RestController
@RequestMapping(value = "/products")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public List<ProductDTO> getProduct(){
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public ProductDTO getProductById(@PathVariable int productId){
        return productService.getProductById(productId);
    }

    @PostMapping("/save-product")
    public ProductDTO saveProduct(@RequestBody ProductDTO productDTO){
        return productService.saveProduct(productDTO);
    }

    @PutMapping("/update-product")
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO){
        return productService.updateProduct(productDTO);
    }

    @DeleteMapping("/delete-product")
    public boolean deleteProduct(@RequestBody ProductDTO productDTO){
        return productService.deleteProduct(productDTO);
    }
}
