package ra.dev.model.service;

import ra.dev.controller.ProductController;
import ra.dev.dto.request.GetProduct;
import ra.dev.model.entity.Product;

import java.util.List;

public interface ProductService {
    List<GetProduct> getAll();
}
