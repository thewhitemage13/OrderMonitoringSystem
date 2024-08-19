package org.thewhitemage13.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thewhitemage13.ProductCreateEvent;
import org.thewhitemage13.entity.Product;
import org.thewhitemage13.exception.ProductNotFoundException;
import org.thewhitemage13.interfaces.ProductServiceInterface;
import org.thewhitemage13.processor.ProductProcessor;
import org.thewhitemage13.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

@Transactional
@Service
public class ProductService implements ProductServiceInterface {
    private final ProductRepository productRepository;
    private final KafkaTemplate<Long, ProductCreateEvent> kafkaTemplate;
    private final ProductProcessor productProcessor;

    public ProductService(ProductRepository productRepository, KafkaTemplate<Long, ProductCreateEvent> kafkaTemplate, ProductProcessor productProcessor) {
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.productProcessor = productProcessor;
    }

    @Override
    public String getProductNameById(Long productId) throws ProductNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product with id = %s not found".formatted(productId)));
        return product.getName();
    }

    @Override
    public BigDecimal getPriceByProductId(Long productId) throws ProductNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product with id = %s not found".formatted(productId)));
        return product.getPrice();
    }

    @Override
    public boolean checkProductInventory(Long productId, Long countOfItems) throws ProductNotFoundException {
        boolean check = productRepository.existsById(productId);
        if(check) {
            Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product with id = %s not found".formatted(productId)));
            return product.getQuantity() > countOfItems;
        }else
            return false;
    }

    @Override
    public void addProduct(Product product) {

        productRepository.save(product);

        System.out.println(product.getId());

        ProductCreateEvent productCreateEvent = productProcessor.getProductCreateEvent(product);

        kafkaTemplate.send("add.product", product.getId(), productCreateEvent);

    }

    @Override
    public void deleteProduct(Long productId) throws ProductNotFoundException {
        Product deleteProduct = getProduct(productId);
        productRepository.delete(deleteProduct);
    }

    @Override
    public Product getProduct(Long productId) throws ProductNotFoundException {
        return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product with id = %s not found".formatted(productId)));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void updateQuantity(Long productId, Long quantity) throws ProductNotFoundException {
        Product product = getProduct(productId);
        product.setQuantity(quantity);

        productRepository.save(product);

        ProductCreateEvent productCreateEvent = productProcessor.getProductCreateEvent(product);


        kafkaTemplate.send("update.stock", product.getId(), productCreateEvent);
    }

    @Override
    public void updateProduct(Long productId, Product product) throws ProductNotFoundException {
        Product updateProduct = getProduct(productId);

        updateProduct.setName(product.getName());
        updateProduct.setQuantity(product.getQuantity());
        updateProduct.setPrice(product.getPrice());

        productRepository.save(updateProduct);
    }

    @Override
    public Product getProductByName(String productName) {
        return productRepository.findByName(productName);
    }
}
