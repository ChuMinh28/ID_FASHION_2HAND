package ra.dev.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.dev.dto.request.CreateProductDetail;
import ra.dev.dto.respone.HistoryResponse;
import ra.dev.model.entity.*;
import ra.dev.model.repository.*;
import ra.dev.model.service.ProductDetailService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    @Autowired
    HistoryRepository historyRepository;

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
        HistoryUpdateProduct historyUpdateProduct = new HistoryUpdateProduct();
        historyUpdateProduct.setDayUpdate(LocalDate.now());
        historyUpdateProduct.setQuantity(createProductDetail.getQuantity());
        historyUpdateProduct.setProductDetail(productDetail);
        historyUpdateProduct.setAction("Order");
        historyRepository.save(historyUpdateProduct);
        return productDetailRepository.save(productDetail);
    }

    @Override
    public Map<String, Object> getPagging(int page, int size, String direction, String sortBy) {
        Pageable pageable;

        if (sortBy.equalsIgnoreCase("discount")){
            if (direction.equalsIgnoreCase("asc")) {
                pageable = PageRequest.of(page, size, Sort.by("Discount").ascending());
            } else {
                pageable = PageRequest.of(page, size, Sort.by("Discount").descending());
            }

        }else {
            if (direction.equalsIgnoreCase("asc")) {
                pageable = PageRequest.of(page, size, Sort.by("Quantity").ascending());
            } else {
                pageable = PageRequest.of(page, size, Sort.by("Quantity").descending());
            }

        }
        Page<ProductDetail> productDetails=productDetailRepository.findAll(pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("ProductDetails in page", productDetails.getContent());
        data.put("TotalElement ", productDetails.getTotalElements());
        data.put("Size", productDetails.getSize());
        data.put("Total page", productDetails.getTotalPages());
        return data;
    }

    @Override
    public boolean updateQuantity(int productDetailID, int quantity, String action) {
        try {
            ProductDetail productDetail = productDetailRepository.findById(productDetailID).get();
            if (action.equals("add")) {
                productDetail.setQuantity(productDetail.getQuantity()+quantity);
                productDetailRepository.save(productDetail);
                HistoryUpdateProduct historyUpdateProduct = new HistoryUpdateProduct();
                historyUpdateProduct.setDayUpdate(LocalDate.now());
                historyUpdateProduct.setQuantity(quantity);
                historyUpdateProduct.setProductDetail(productDetail);
                historyUpdateProduct.setAction("Add Quantity");
                historyRepository.save(historyUpdateProduct);
            } else {
                productDetail.setQuantity(quantity);
                productDetailRepository.save(productDetail);
                HistoryUpdateProduct historyUpdateProduct = new HistoryUpdateProduct();
                historyUpdateProduct.setDayUpdate(LocalDate.now());
                historyUpdateProduct.setQuantity(quantity);
                historyUpdateProduct.setProductDetail(productDetail);
                historyUpdateProduct.setAction("Order");
                historyRepository.save(historyUpdateProduct);
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<HistoryResponse> getHistory(int productID) {
        try {
            List<HistoryUpdateProduct> list = historyRepository.findAllByProductDetail_ProductDetailID(productID);
            List<HistoryResponse> listResponse = new ArrayList<>();
            for (HistoryUpdateProduct pro:list) {
                HistoryResponse historyResponse = new HistoryResponse();
                historyResponse.setProductName(pro.getProductDetail().getProduct().getProductName());
                historyResponse.setDayUpdate(pro.getDayUpdate());
                historyResponse.setAction(pro.getAction());
                historyResponse.setQuantity(pro.getQuantity());
                listResponse.add(historyResponse);
            }
            return listResponse;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
