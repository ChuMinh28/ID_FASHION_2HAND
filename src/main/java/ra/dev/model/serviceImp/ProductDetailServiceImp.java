package ra.dev.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.dev.model.entity.Color;
import ra.dev.model.entity.ProductDetail;
import ra.dev.model.entity.Size;
import ra.dev.model.repository.ProductDetailRepository;
import ra.dev.model.service.ProductDetailService;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductDetailServiceImp implements ProductDetailService {
    @Autowired
    ProductDetailRepository productDetailRepository;
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
}
