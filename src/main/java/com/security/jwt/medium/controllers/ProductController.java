package com.security.jwt.medium.controllers;

import java.util.Collection;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.jwt.medium.services.ProductService;
import com.security.jwt.model.Product;

@RestController
@RequestMapping("products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/getProducts")
	@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
	public Collection<Product> getProducts() {
		return productService.getAllProducts();
	}

	@PostMapping
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public void addProduct(@RequestBody Product product) {
		productService.addProduct(product);
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	public void removeProduct(@PathVariable long id) {
		productService.deleteProductById(id);
	}

}