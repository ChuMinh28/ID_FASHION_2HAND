package ra.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ra.dev.dto.request.ProductByCat;
import ra.dev.dto.respone.GetProduct;

import ra.dev.dto.respone.GetProductByCat;
import ra.dev.dto.respone.ProductDetailGet;
import ra.dev.dto.respone.ProductSale;
import ra.dev.model.entity.Product;

import ra.dev.model.service.ProductService;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public List<ProductSale> getProductBestSale() {
        return productService.getBestSale();
    }

    @GetMapping("getProductByGender")
    public List<GetProduct> getByGender(
            @RequestParam String direction,
            @RequestParam String colorName,
            @RequestParam String sizeName,
            @RequestParam String sex
    ) {
        return productService.getByGender(direction, colorName, sizeName, sex);
    }

    @GetMapping("getByFilter")
    public List<GetProduct> getByFilter(
            @RequestParam String colorName,
            @RequestParam String sizeName
    ) {
        return productService.getByFilter(colorName, sizeName);
    }

    @GetMapping("getListLimited")
    public List<GetProduct> getProductLimited() {
        return productService.getProductLimited();
    }

    @GetMapping("getProductDetail/{productID}")
    public ProductDetailGet getProduct(@PathVariable("productID") int productID) {
        return productService.getDetail(productID);
    }

    @PostMapping("create")
    public Product createProduct(@RequestBody Product createProduct) {
        return productService.createProduct(createProduct);
    }

    @PutMapping("updateProduct/{productID}")
    public Product updateProduct(@PathVariable("productID") int productID, @RequestBody Product productUpdate) {
        return productService.updateProduct(productID, productUpdate);
    }


    @GetMapping("/action")
    public Map<String, Object> paginationProduct(@RequestParam("catalogID") int catalogID,
                                                 @RequestParam(defaultValue = "10") int number,
                                                 @RequestParam(defaultValue = "0") String searchBy,
                                                 @RequestParam(defaultValue = "0") String sortBy,
                                                 @RequestParam(defaultValue = "c") String name,
                                                 @RequestParam(defaultValue = "desc") String direction,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "3") int size) {
        return productService.getPagging(catalogID, number, searchBy, sortBy, name, direction, page, size);
    }

    @GetMapping("getBestSale2")
    public List<Product> listBestSale() {
        return productService.listSale();
    }

    @GetMapping("/findProductByCatalog")
    public Set<GetProductByCat> findProductByCatalog(@RequestBody ProductByCat listCat) {
        return productService.findProductByCatalog(listCat);
    }
}
