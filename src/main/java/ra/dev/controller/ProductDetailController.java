package ra.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ra.dev.dto.request.CreateProductDetail;
import ra.dev.model.entity.Color;
import ra.dev.model.entity.ProductDetail;
import ra.dev.model.entity.Size;
import ra.dev.model.service.ProductDetailService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/productDetail")
public class ProductDetailController {
    @Autowired
    ProductDetailService productDetailService;
    @GetMapping("getSize")
    public List<Size> getListSize(@RequestParam int colorID,
                                  @RequestParam int productID){
       return productDetailService.getListSize(colorID, productID);
    }
    @GetMapping("getColor")
    public List<Color> getListColor(@RequestParam int sizeID,
                                    @RequestParam int productID){
        return productDetailService.getListColor(sizeID, productID);
    }
    @GetMapping("getDetail")
    public ProductDetail getDetail(@RequestParam int sizeID,
                                   @RequestParam int colorID,
                                   @RequestParam int productID){
        return productDetailService.getDetail(sizeID,colorID,productID);
    }

    @PostMapping("create")
    public ProductDetail createProductDetail(@RequestBody CreateProductDetail createProductDetail){
      return productDetailService.createProductDetail(createProductDetail);
    }
    @PostMapping("update/{productDetailID}")
    public ProductDetail updateProductDetail(@PathVariable("productDetailID") int productDetailID, @RequestBody CreateProductDetail createProductDetail){
        return productDetailService.updateProductDetail(productDetailID,createProductDetail);
    }


}
