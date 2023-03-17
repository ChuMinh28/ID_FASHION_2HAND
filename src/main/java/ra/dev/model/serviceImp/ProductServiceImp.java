package ra.dev.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.dev.dto.respone.GetProduct;
import ra.dev.dto.respone.ProductSale;
import ra.dev.model.entity.Color;
import ra.dev.model.entity.Product;
import ra.dev.model.entity.ProductDetail;
import ra.dev.model.entity.Size;
import ra.dev.model.repository.ColorRepository;
import ra.dev.model.repository.ProductDetailRepository;
import ra.dev.model.repository.ProductRepository;
import ra.dev.model.repository.SizeRepository;
import ra.dev.model.service.ProductService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductDetailRepository productDetailRepository;
    @Autowired
    ColorRepository colorRepository;
    @Autowired
    SizeRepository sizeRepository;
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
    @Override
    public List<GetProduct> sortAndFilter(String direction, String color, String size) {
        try {
            List<ProductDetail> productDetailList = new ArrayList<>();
            if(color.equals("0")){
                Size sizeFind = sizeRepository.findSizeBySizeName(size);
                productDetailList = productDetailRepository.findProductDetailBySizeSizeID(sizeFind.getSizeID());
            }else {
                Color colorFind = colorRepository.findColorByColorName(color);
                productDetailList = productDetailRepository.findProductDetailByColorColorID(colorFind.getColorID());
            }
            List<GetProduct> productList = new ArrayList<>();
            for (ProductDetail productDetail: productDetailList) {
                Product product = productRepository.findProductByListProductDetailContaining(productDetail);
                GetProduct getProduct = new GetProduct(
                        product.getProductID(),
                        product.getProductName(),
                        product.getImage(),
                        product.getTitle(),
                        product.getPrice());
                if(productList.contains(getProduct)){
                    continue;
                }else {
                    productList.add(getProduct);
                }
            }
            Collections.sort(productList, new Comparator<GetProduct>() {
                @Override
                public int compare(GetProduct u1, GetProduct u2) {
                    if(direction.equals("asc")){
                        return u1.getPrice() - u2.getPrice();
                    }else {
                        return u2.getPrice() - u1.getPrice();
                    }
                }
            });
            return productList;
        }catch (Exception e){
            e.printStackTrace();
            List<GetProduct> list = new ArrayList<>();
            return list;
        }
    }

    @Override
    public List<ProductSale> getBestSale() {
        List<ProductDetail> productDetailList = productDetailRepository.findAll();
        Collections.sort(productDetailList, new Comparator<ProductDetail>() {
            @Override
            public int compare(ProductDetail u1, ProductDetail u2) {
                    return u2.getDiscount() - u1.getDiscount();
            }
        });
        List<ProductSale> productSales = new ArrayList<>();
        List<ProductDetail> productDetailsListGet10Item = new ArrayList<>(productDetailList.subList(0,5));
        for (ProductDetail productDetail: productDetailsListGet10Item) {
            Product product = productRepository.findProductByListProductDetailContaining(productDetail);
            ProductSale productSale = new ProductSale(
                    product.getProductID(),
                    product.getProductName(),
                    product.getImage(),
                    product.getPrice(),
                    productDetail.getColor().getColorName(),
                    productDetail.getSize().getSizeName(),
                    productDetail.getDiscount());
            if(productSales.contains(productSale)){
                continue;
            }else {
                productSales.add(productSale);
            }
        }
        return productSales;
    }

    @Override
    public List<GetProduct> getByGender(String direction, String color, String size, String sex) {
        try {
            Boolean getSex ;
            if(sex.equals("men")){
                getSex = true;
            }else {
                getSex = false;
            }
            List<ProductDetail> productDetailList = new ArrayList<>();
            if(color.equals("0")&&size.equals("0")){
                productDetailList = productDetailRepository.findProductDetailByProductGender(getSex);
            }else if(size.equals("0")) {
                Color colorFind = colorRepository.findColorByColorName(color);
                productDetailList = productDetailRepository.findProductDetailByColorColorIDAndProductGender(colorFind.getColorID(),getSex);
            }else {
                Size sizeFind = sizeRepository.findSizeBySizeName(size);
                productDetailList = productDetailRepository.findProductDetailBySizeSizeIDAndProductGender(sizeFind.getSizeID(),getSex);            }
            List<GetProduct> productList = new ArrayList<>();
            for (ProductDetail productDetail: productDetailList) {
                Product product = productRepository.findProductByListProductDetailContaining(productDetail);
                GetProduct getProduct = new GetProduct(
                        product.getProductID(),
                        product.getProductName(),
                        product.getImage(),
                        product.getTitle(),
                        product.getPrice());
                if(productList.contains(getProduct)){
                    continue;
                }else {
                    productList.add(getProduct);
                }
            }
            Collections.sort(productList, new Comparator<GetProduct>() {
                @Override
                public int compare(GetProduct u1, GetProduct u2) {
                    if(direction.equals("asc")){
                        return u1.getPrice() - u2.getPrice();
                    }else {
                        return u2.getPrice() - u1.getPrice();
                    }
                }
            });
            return productList;
        }catch (Exception e){
            e.printStackTrace();
            List<GetProduct> list = new ArrayList<>();
            return list;
        }
    }

    @Override
    public List<GetProduct> getByFilter(String color, String size) {
        try {
            List<ProductDetail> productDetailList = new ArrayList<>();
            if(color.equals("0")){
                Size sizeFind = sizeRepository.findSizeBySizeName(size);
                productDetailList = productDetailRepository.findProductDetailBySizeSizeID(sizeFind.getSizeID());
            }else {
                Color colorFind = colorRepository.findColorByColorName(color);
                productDetailList = productDetailRepository.findProductDetailByColorColorID(colorFind.getColorID());
            }
            List<GetProduct> productList = new ArrayList<>();
            for (ProductDetail productDetail: productDetailList) {
                Product product = productRepository.findProductByListProductDetailContaining(productDetail);
                GetProduct getProduct = new GetProduct(
                        product.getProductID(),
                        product.getProductName(),
                        product.getImage(),
                        product.getTitle(),
                        product.getPrice());
                if(productList.contains(getProduct)){
                    continue;
                }else {
                    productList.add(getProduct);
                }
            }

            return productList;
        }catch (Exception e){
            e.printStackTrace();
            List<GetProduct> list = new ArrayList<>();
            return list;
        }
    }
}
