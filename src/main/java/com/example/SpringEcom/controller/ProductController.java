package com.example.SpringEcom.controller;

import com.example.SpringEcom.model.Product;
import com.example.SpringEcom.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> allProducts(){
        return new ResponseEntity<>( productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
        Product product=productService.getProductById(id);

        if(product!=null)
            return new ResponseEntity<>(product,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile image){
        Product savedProduct= null;
        try {
            savedProduct = productService.addOrUpdateProduct(product,image);
            return new ResponseEntity<>(savedProduct,HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
        Product product= productService.getProductById(productId);
        if(product!=null)
            return new ResponseEntity<>(product.getImageData(),HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> UpdateProduct(@PathVariable int id,@RequestPart Product product,@RequestPart MultipartFile image){
        Product updateProduct=productService.addOrupdateProduct(product,image);
        return new ResponseEntity<>("Updated",HttpStatus.OK);

    }

    @DeleteMapping("/product/id")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        Product product=productService.getProductById(id);
        if(product!=null){
            productService.deleteProduct(id);
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    @GetMapping("/product/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword){
        List<Product> products=productService.searchProducts(keyword);
        System.out.println("Searching with :"+keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);

    }

}
