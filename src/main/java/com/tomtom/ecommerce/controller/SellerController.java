package com.tomtom.ecommerce.controller;

import com.tomtom.ecommerce.bean.Product;
import com.tomtom.ecommerce.bean.person.Seller;
import com.tomtom.ecommerce.service.ProductService;
import com.tomtom.ecommerce.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/sellers")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private ProductService productService;


    @GetMapping
    public ResponseEntity<List<Seller>> getAllSeller() {
        try {
            List<Seller> response = sellerService.getAllSeller();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Exception in processing the request to get all seller info" + e);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{sellerId}")
    public ResponseEntity<Seller> getSeller(@PathVariable Long sellerId) {
        try {
            Seller seller = sellerService.getSeller(sellerId);
            if (seller != null) {
                return new ResponseEntity<>(seller, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Exception in getting the seller info for id : " + sellerId + " IS :: " + e);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping
    public ResponseEntity<String> addNewSeller(@RequestBody @Valid Seller seller) {
        try {
            sellerService.saveOrUpdateSeller(seller);
            return new ResponseEntity<>("Success ..!!", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Exception in saving the seller ::" + seller + " is : " + e);
        }
        return new ResponseEntity<>("Unable to store seller", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{sellerId}/products")
    public ResponseEntity<List<Product>> getAllProducts(@PathVariable Long sellerId) {
        try {
            List<Product> listOfProduct = productService.getAllProductForSeller(sellerId);
            return new ResponseEntity<>(listOfProduct, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Exception in getting all the products from sellerID : " + sellerId + " IS : " + e);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/{sellerId}/products/")
    public ResponseEntity<String> addNewProduct(@PathVariable Long sellerId, @RequestBody @Valid Product product) {
        try {
            product.setSellerId(sellerId);
            productService.addNewProduct(product);
            return new ResponseEntity<>("Success..!!!", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Exception in adding a new   product from sellerID : " + sellerId + " IS : " + e);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{sellerId}/products/{productId}")
    public ResponseEntity<String> removeProduct(@PathVariable Long sellerId, @PathVariable  Long productId) {
        try {
            productService.removeProduct(sellerId,productId);
            return new ResponseEntity<>("Success..!!!", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Exception in adding a new   product from sellerID : " + sellerId + " IS : " + e);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
