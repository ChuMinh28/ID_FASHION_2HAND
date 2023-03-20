package ra.dev.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.dev.dto.request.CreateProductDetail;
import ra.dev.model.entity.Color;
import ra.dev.model.entity.Product;
import ra.dev.model.entity.ProductDetail;
import ra.dev.model.entity.Size;
import ra.dev.model.repository.ColorRepository;
import ra.dev.model.repository.ProductDetailRepository;
import ra.dev.model.repository.ProductRepository;
import ra.dev.model.repository.SizeRepository;
import ra.dev.model.service.ProductDetailService;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductDetailServiceImp implements ProductDetailService {
    @Autowired
    ProductDetailRepository productDetailRepository;
    @Autowired
    ColorRepository colorRepository;
    @Autowired
    SizeRepository sizeRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Size> getListSize(int colorID, int productID) {
        List<ProductDetail> productDetailList = productDetailRepository.findProductDetailByColorColorIDAndProductProductID(colorID,productID);
       List<Size> sizeList = new ArrayList<>();
        for (ProductDetail productDetail:productDetailList) {
            sizeList.add(productDetail.getSize());
        }
        return sizeList;
    }

    @Override
    public List<Color> getListColor(int sizeID, int productID) {
        List<ProductDetail> productDetailList = productDetailRepository.findProductDetailBySizeSizeIDAndProductProductID(sizeID,productID);
        List<Color> colorList = new ArrayList<>();
        for (ProductDetail productDetail:productDetailList) {
            colorList.add(productDetail.getColor());
        }
        return colorList;
    }
    @Override
    public ProductDetail getDetail(int sizeID, int ColorID,int productID) {
        return productDetailRepository.findProductDetailBySizeSizeIDAndColorColorIDAndProductProductID(sizeID,ColorID,productID);
    }
    @Override
    public ProductDetail createProductDetail(CreateProductDetail createProductDetail) {
        Product product = productRepository.findById(createProductDetail.getProductID()).get();
        Color color = colorRepository.findById(createProductDetail.getColorID()).get();
        Size size = sizeRepository.findById(createProductDetail.getSizeID()).get();
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProduct(product);
        productDetail.setSize(size);
        productDetail.setColor(color);
        productDetail.setQuantity(createProductDetail.getQuantity());
        productDetail.setDiscount(createProductDetail.getDiscount());
        return productDetailRepository.save(productDetail);
    }

    @Override
    public ProductDetail updateProductDetail(int productDetailID, CreateProductDetail createProductDetail) {
        Product product = productRepository.findById(createProductDetail.getProductID()).get();
        Color color = colorRepository.findById(createProductDetail.getColorID()).get();
        Size size = sizeRepository.findById(createProductDetail.getSizeID()).get();
        ProductDetail productDetail = productDetailRepository.findById(productDetailID).get();
        productDetail.setProduct(product);
        productDetail.setSize(size);
        productDetail.setColor(color);
        productDetail.setQuantity(createProductDetail.getQuantity());
        productDetail.setDiscount(createProductDetail.getDiscount());
        return productDetailRepository.save(productDetail);
    }
}
