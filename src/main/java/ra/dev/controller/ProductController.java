package ra.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ra.dev.dto.respone.GetProduct;
import ra.dev.dto.respone.ProductSale;
import ra.dev.model.entity.Color;
import ra.dev.model.entity.Product;
import ra.dev.model.entity.ProductDetail;
import ra.dev.model.service.ProductService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping()
    public List<GetProduct> productList() {
        return productService.getAll();
    }

    @GetMapping("/sortAndFilter")
    public List<GetProduct> sortAndFilter(@RequestParam String direcion,
                                          @RequestParam String colorName,
                                          @RequestParam String sizeName) {
        return productService.sortAndFilter(direcion, colorName, sizeName);
    }
    @GetMapping("getBestSale")
    public List<ProductSale> getProductBestSale(){
        return productService.getBestSale();
    }

    @GetMapping("getProductByGender")
    public List<GetProduct> getByGender(
            @RequestParam String direction,
            @RequestParam String colorName,
            @RequestParam String sizeName,
            @RequestParam String sex
    ){
        return productService.getByGender(direction,colorName,sizeName,sex);
    }

    @GetMapping("getByFilter")
    public List<GetProduct> getByFilter(
            @RequestParam String colorName,
            @RequestParam String sizeName
    ){
        return productService.getByFilter(colorName,sizeName);
    }
    @GetMapping("getListLimited")
    public List<GetProduct> getProductLimited(){
        return productService.getProductLimited();
    }






}
