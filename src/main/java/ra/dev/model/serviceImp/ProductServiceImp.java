package ra.dev.model.serviceImp;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.dev.dto.request.GetProduct;
import ra.dev.model.entity.Product;
import ra.dev.model.repository.ProductRepository;
import ra.dev.model.service.ProductService;

import java.util.ArrayList;
import java.util.List;
@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Override
    public List<GetProduct> getAll() {
        List<Product> productList = productRepository.findAll();
        List<GetProduct> getProductsList = new ArrayList<>();
        for (Product product: productList) {
            GetProduct getProduct = new GetProduct(
                    product.getProductID(),
                    product.getProductName(),
                    product.getImage(),
                    product.getTitle(),
                    product.getPrice());
            getProductsList.add(getProduct);
        }
        return getProductsList;
    }
}
