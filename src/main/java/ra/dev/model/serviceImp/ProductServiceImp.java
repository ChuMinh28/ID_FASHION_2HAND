package ra.dev.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.dev.dto.respone.GetProduct;
import ra.dev.dto.respone.ProductDetailGet;
import ra.dev.dto.respone.ProductSale;
import ra.dev.model.entity.*;
import ra.dev.model.repository.*;
import ra.dev.model.service.ProductService;

import java.util.*;
import java.util.Collections;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductDetailRepository productDetailRepository;

    @Autowired
    CatalogRepository catalogRepository;
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

    @Override
    public List<GetProduct> getProductLimited() {
        List<ProductDetail> productDetailList = productDetailRepository.findProductDetailByProductLimited(true);
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
    }

    @Override
    public ProductDetailGet getDetail(int productID) {
        ProductDetailGet productDetailGet = new ProductDetailGet();
        Product product = productRepository.findById(productID).get();
        List<ProductDetail> productDetailList = productDetailRepository.findProductDetailByProductProductID(productID);
        List<Color> colorList = new ArrayList<>();
        List<Size> sizeList = new ArrayList<>();
        for (ProductDetail productDetail :productDetailList ) {
            colorList.add(productDetail.getColor());
            sizeList.add(productDetail.getSize());
        }
        productDetailGet.setProductID(productID);
        productDetailGet.setProductName(product.getProductName());
        productDetailGet.setImage(product.getImage());
        productDetailGet.setPrice(product.getPrice());
        productDetailGet.setTitle(product.getTitle());
        productDetailGet.setColorList(colorList);
        productDetailGet.setSizeList(sizeList);
        return productDetailGet;
    }

    @Override
    public Map<String, Object> findProductByListCatalogContaining(int id, int page, int size, String direction, String sortBy) {
        Catalog catalog=catalogRepository.findById(id).get();
        Pageable pageable;
        if (sortBy.equalsIgnoreCase("name")){
            if (direction.equalsIgnoreCase("asc")) {
                pageable = PageRequest.of(page, size, Sort.by("ProductName").ascending());
            } else {
                pageable = PageRequest.of(page, size, Sort.by("ProductName").descending());
            }
        }else {
            if (direction.equalsIgnoreCase("asc")) {
                pageable = PageRequest.of(page, size, Sort.by("Price").ascending());
            } else {
                pageable = PageRequest.of(page, size, Sort.by("Price").descending());
            }
        }
        Page<Product> productPage=productRepository.findProductByListCatalogContaining(catalog,pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("Product", productPage.getContent());
        data.put("Size", productPage.getSize());
        data.put("TotalElement", productPage.getTotalElements());
        data.put("TotalPage", productPage.getTotalPages());
        return data;
    }



}
