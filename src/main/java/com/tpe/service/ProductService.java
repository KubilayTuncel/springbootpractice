package com.tpe.service;

import com.tpe.domain.Customer;
import com.tpe.domain.Product;
import com.tpe.dto.ProductDTO;
import com.tpe.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerService customerService;

    public void saveProduct(ProductDTO productDTO) {
        Product product = new Product();
        Customer customer = customerService.getCustomerById(productDTO.getCustomerId());

        product.setProductName(productDTO.getProductName());
        product.setBrand(productDTO.getBrand());
        product.setPrice(productDTO.getPrice());
        product.setCustomer(customer);
        productRepository.save(product);
    }
}
