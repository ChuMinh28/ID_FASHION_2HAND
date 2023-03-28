package ra.dev.model.serviceImp;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.dev.dto.request.ProductByCat;
import ra.dev.dto.respone.*;
import ra.dev.model.entity.*;
import ra.dev.model.repository.*;
import ra.dev.model.service.ProductService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    @Autowired
    ImageRepository imageRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    EmployeeDAO employeeDAO;
    @Autowired
    OrderRepository orderRepository;

    @Override
    public List<GetProduct> getAll() {
        List<Product> productList = productRepository.findAll();
        List<GetProduct> getProductsList = new ArrayList<>();
        for (Product product : productList) {
            GetProduct getProduct = new GetProduct(
                    product.getProductID(),
                    product.getProductName(),
                    product.getImage(),
                    product.getTitle(),
                    product.getPrice(),
                    product.getDiscount());
            getProductsList.add(getProduct);
        }
        return getProductsList;
    }

    @Override
    public List<GetProduct> sortAndFilter(String direction, String color, String size) {
        try {
            List<ProductDetail> productDetailList = new ArrayList<>();
            if (color.equals("0")) {
                Size sizeFind = sizeRepository.findSizeBySizeName(size);
                productDetailList = productDetailRepository.findProductDetailBySizeSizeID(sizeFind.getSizeID());
            } else {
                Color colorFind = colorRepository.findColorByColorName(color);
                productDetailList = productDetailRepository.findProductDetailByColorColorID(colorFind.getColorID());
            }
            List<GetProduct> productList = new ArrayList<>();
            for (ProductDetail productDetail : productDetailList) {
                Product product = productRepository.findProductByListProductDetailContaining(productDetail);
                GetProduct getProduct = new GetProduct(
                        product.getProductID(),
                        product.getProductName(),
                        product.getImage(),
                        product.getTitle(),
                        product.getPrice(),
                        product.getDiscount());

                if (productList.contains(getProduct)) {
                    continue;
                } else {
                    productList.add(getProduct);
                }
            }
            Collections.sort(productList, new Comparator<GetProduct>() {
                @Override
                public int compare(GetProduct u1, GetProduct u2) {
                    if (direction.equals("asc")) {
                        return u1.getPrice() - u2.getPrice();
                    } else {
                        return u2.getPrice() - u1.getPrice();
                    }
                }
            });
            return productList;
        } catch (Exception e) {
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
                return u2.getProduct().getDiscount() - u1.getProduct().getDiscount();
            }
        });
        List<ProductSale> productSales = new ArrayList<>();
        List<ProductDetail> productDetailsListGet10Item = new ArrayList<>(productDetailList.subList(0, 5));
        for (ProductDetail productDetail : productDetailsListGet10Item) {
            Product product = productRepository.findProductByListProductDetailContaining(productDetail);
            ProductSale productSale = new ProductSale(
                    product.getProductID(),
                    product.getProductName(),
                    product.getImage(),
                    product.getPrice(),
                    productDetail.getColor().getColorName(),
                    productDetail.getSize().getSizeName(),
                    productDetail.getProduct().getDiscount());
            if (productSales.contains(productSale)) {
                continue;
            } else {
                productSales.add(productSale);
            }
        }
        return productSales;
    }

    @Override
    public List<GetProduct> getByGender(String direction, String color, String size, String sex) {
        try {
            Boolean getSex;
            if (sex.equals("men")) {
                getSex = true;
            } else {
                getSex = false;
            }
            List<ProductDetail> productDetailList = new ArrayList<>();
            if (color.equals("0") && size.equals("0")) {
                productDetailList = productDetailRepository.findProductDetailByProductGender(getSex);
            } else if (size.equals("0")) {
                Color colorFind = colorRepository.findColorByColorName(color);
                productDetailList = productDetailRepository.findProductDetailByColorColorIDAndProductGender(colorFind.getColorID(), getSex);
            } else {
                Size sizeFind = sizeRepository.findSizeBySizeName(size);
                productDetailList = productDetailRepository.findProductDetailBySizeSizeIDAndProductGender(sizeFind.getSizeID(), getSex);
            }
            List<GetProduct> productList = new ArrayList<>();
            for (ProductDetail productDetail : productDetailList) {
                Product product = productRepository.findProductByListProductDetailContaining(productDetail);
                GetProduct getProduct = new GetProduct(
                        product.getProductID(),
                        product.getProductName(),
                        product.getImage(),
                        product.getTitle(),
                        product.getPrice(),
                        product.getDiscount());
                if (productList.contains(getProduct)) {
                    continue;
                } else {
                    productList.add(getProduct);
                }
            }
            Collections.sort(productList, new Comparator<GetProduct>() {
                @Override
                public int compare(GetProduct u1, GetProduct u2) {
                    if (direction.equals("asc")) {
                        return u1.getPrice() - u2.getPrice();
                    } else {
                        return u2.getPrice() - u1.getPrice();
                    }
                }
            });
            return productList;
        } catch (Exception e) {
            e.printStackTrace();
            List<GetProduct> list = new ArrayList<>();
            return list;
        }
    }

    @Override
    public List<GetProduct> getByFilter(String color, String size) {
        try {
            List<ProductDetail> productDetailList = new ArrayList<>();
            if (color.equals("0")) {
                Size sizeFind = sizeRepository.findSizeBySizeName(size);
                productDetailList = productDetailRepository.findProductDetailBySizeSizeID(sizeFind.getSizeID());
            } else {
                Color colorFind = colorRepository.findColorByColorName(color);
                productDetailList = productDetailRepository.findProductDetailByColorColorID(colorFind.getColorID());
            }
            List<GetProduct> productList = new ArrayList<>();
            for (ProductDetail productDetail : productDetailList) {
                Product product = productRepository.findProductByListProductDetailContaining(productDetail);
                GetProduct getProduct = new GetProduct(
                        product.getProductID(),
                        product.getProductName(),
                        product.getImage(),
                        product.getTitle(),
                        product.getPrice(),
                        product.getDiscount());

                if (productList.contains(getProduct)) {
                    continue;
                } else {
                    productList.add(getProduct);
                }
            }

            return productList;
        } catch (Exception e) {
            e.printStackTrace();
            List<GetProduct> list = new ArrayList<>();
            return list;
        }
    }

    @Override
    public List<GetProduct> getProductLimited() {
        List<ProductDetail> productDetailList = productDetailRepository.findProductDetailByProductLimited(true);
        List<GetProduct> productList = new ArrayList<>();
        for (ProductDetail productDetail : productDetailList) {
            Product product = productRepository.findProductByListProductDetailContaining(productDetail);
            GetProduct getProduct = new GetProduct(
                    product.getProductID(),
                    product.getProductName(),
                    product.getImage(),
                    product.getTitle(),
                    product.getPrice(),
                    product.getDiscount());
            if (productList.contains(getProduct)) {
                continue;
            } else {
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
        for (ProductDetail productDetail : productDetailList) {
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
    public Product createProduct(Product createProduct) {
        List<Catalog> catalogList = new ArrayList<>();
        for (int i = 0; i < createProduct.getListCatalog().size(); i++) {
            if (catalogRepository.findCatalogByCatalogName(String.valueOf(createProduct.getListCatalog().get(i))) != null) {
                Catalog catalgoFind = catalogRepository.findCatalogByCatalogName(String.valueOf(createProduct.getListCatalog().get(i)));
                catalogList.add(catalgoFind);
            } else {
                Catalog catalog = new Catalog();
                catalog.setCatalogName(String.valueOf(createProduct.getListCatalog().get(i).getCatalogName()));
                catalog.setCatalogStatus(true);
                catalogList.add(catalogRepository.save(catalog));
            }
        }

        Product product = new Product();
        product.setProductName(createProduct.getProductName());
        product.setPrice(createProduct.getPrice());
        product.setDiscount(createProduct.getDiscount());
        product.setProductStatus(true);
        product.setTitle(createProduct.getTitle());
        product.setDescription(createProduct.getDescription());
        product.setGender(createProduct.isGender());
        product.setLimited(createProduct.isLimited());
        product.setImage(createProduct.getImage());
        product.setShipping(createProduct.isShipping());
        product.setListCatalog(catalogList);
        productRepository.save(product);
        for (int i = 0; i < createProduct.getListImage().size(); i++) {
            Image subImage = new Image();
            subImage.setImageLink(String.valueOf(createProduct.getListImage().get(i).getImageLink()));
            subImage.setProduct(product);
            imageRepository.save(subImage);
        }
        return product;
    }

    @Override
    public Product updateProduct(int productID, Product updateProduct) {
        List<Image> imageList = imageRepository.findByProductProductID(productID);
        for (Image image : imageList) {
            imageRepository.deleteById(image.getImageID());
        }
        List<Catalog> catalogListUpdate = new ArrayList<>();
        for (int i = 0; i < updateProduct.getListCatalog().size(); i++) {
            if (catalogRepository.findCatalogByCatalogName(String.valueOf(updateProduct.getListCatalog().get(i))) != null) {
                Catalog catalgoFind = catalogRepository.findCatalogByCatalogName(String.valueOf(updateProduct.getListCatalog().get(i)));
                catalogListUpdate.add(catalgoFind);
            } else {
                Catalog catalog = new Catalog();
                catalog.setCatalogName(String.valueOf(updateProduct.getListCatalog().get(i)));
                catalog.setCatalogStatus(true);
                catalogListUpdate.add(catalogRepository.save(catalog));
            }
        }
        Product productUpdate = productRepository.findById(productID).get();
        productUpdate.setProductName(updateProduct.getProductName());
        productUpdate.setPrice(updateProduct.getPrice());
        productUpdate.setDiscount(updateProduct.getDiscount());
        productUpdate.setProductStatus(true);
        productUpdate.setTitle(updateProduct.getTitle());
        productUpdate.setDescription(updateProduct.getDescription());
        productUpdate.setGender(updateProduct.isGender());
        productUpdate.setLimited(updateProduct.isLimited());
        productUpdate.setImage(updateProduct.getImage());
        productUpdate.setShipping(updateProduct.isShipping());

        productUpdate.setListCatalog(catalogListUpdate);
        productRepository.save(productUpdate);
        for (int i = 0; i < updateProduct.getListImage().size(); i++) {
            Image subImage = new Image();
            subImage.setImageLink(String.valueOf(updateProduct.getListImage().get(i).getImageLink()));
            subImage.setProduct(productUpdate);
            imageRepository.save(subImage);
        }
        return productUpdate;

    }

    public Map<String, Object> getPagination(Page<Product> productPage) {
        Map<String, Object> data = new HashMap<>();
        data.put("Product in page", productPage.getContent());
        data.put("TotalElement", productPage.getTotalElements());
        data.put("Size", productPage.getSize());
        data.put("TotalPage", productPage.getTotalPages());
        return data;
    }

    @Override
    public Map<String, Object> getPagging(int id, int number, String searchBy, String sortBy, String name, String direction, int page, int size) {

        Catalog catalog = catalogRepository.findById(id).get();
        if (searchBy.equals("0") && sortBy.equals("0")) {
            Pageable pageable = PageRequest.of(page, size);
            Page<Product> productPage = productRepository.findAll(pageable);
            return getPagination(productPage);
        } else if (searchBy != "0") {
            Pageable pageable = PageRequest.of(page, size);
            Page<Product> productPage;
            if (searchBy.equals("name")) {
                productPage = productRepository.findByProductNameContaining(name, pageable);
            } else if (searchBy.equals("discount")) {
                productPage = productRepository.findByDiscount(number, pageable);
            } else if (searchBy.equals("catalog")) {
                productPage = productRepository.findProductByListCatalogContaining(catalog, pageable);
            } else {
                productPage = productRepository.findByPrice(number, pageable);
            }
            return getPagination(productPage);
        } else if (sortBy != "0") {
            Pageable pageable;
            if (sortBy.equalsIgnoreCase("name")) {
                if (direction.equalsIgnoreCase("asc")) {
                    pageable = PageRequest.of(page, size, Sort.by("ProductName").ascending());
                } else {
                    pageable = PageRequest.of(page, size, Sort.by("ProductName").descending());
                }
            } else if (sortBy.equalsIgnoreCase("discount")) {
                if (direction.equalsIgnoreCase("asc")) {
                    pageable = PageRequest.of(page, size, Sort.by("Discount").ascending());
                } else {
                    pageable = PageRequest.of(page, size, Sort.by("Discount").descending());
                }
            } else {
                if (direction.equalsIgnoreCase("asc")) {
                    pageable = PageRequest.of(page, size, Sort.by("Price").ascending());
                } else {
                    pageable = PageRequest.of(page, size, Sort.by("Price").descending());
                }
            }
            Page<Product> productPage = productRepository.findAll(pageable);

            return getPagination(productPage);
        } else {
            Pageable pageable;
            if (sortBy.equalsIgnoreCase("name")) {
                if (direction.equalsIgnoreCase("asc")) {
                    pageable = PageRequest.of(page, size, Sort.by("ProductName").ascending());
                } else {
                    pageable = PageRequest.of(page, size, Sort.by("ProductName").descending());
                }
            } else if (sortBy.equalsIgnoreCase("discount")) {
                if (direction.equalsIgnoreCase("asc")) {
                    pageable = PageRequest.of(page, size, Sort.by("Discount").ascending());
                } else {
                    pageable = PageRequest.of(page, size, Sort.by("Discount").descending());
                }
            } else {
                if (direction.equalsIgnoreCase("asc")) {
                    pageable = PageRequest.of(page, size, Sort.by("Price").ascending());
                } else {
                    pageable = PageRequest.of(page, size, Sort.by("Price").descending());
                }
            }
            Page<Product> productPage;
            if (searchBy.equals("name")) {
                productPage = productRepository.findByProductNameContaining(name, pageable);
            } else if (searchBy.equals("discount")) {
                productPage = productRepository.findByDiscount(number, pageable);
            } else if (searchBy.equals("catalog")) {
                productPage = productRepository.findProductByListCatalogContaining(catalog, pageable);
            } else {
                productPage = productRepository.findByPrice(number, pageable);
            }
            return getPagination(productPage);
        }
    }

    @Override
    public Set<GetProductByCat> findProductByCatalog(ProductByCat productByCat) {
        Set<GetProductByCat> productList = new HashSet<>();
        for (Integer id : productByCat.getProductByCatalog()) {
            Catalog catalog = catalogRepository.findById(id).get();
            if (catalog != null) {
                List<Product> productListCat = productRepository.findProductByListCatalogContaining(catalog);
                if (productListCat != null) {
                    for (Product p : productListCat) {
                        GetProductByCat productGetAll = new GetProductByCat(
                                p.getProductID(),
                                p.getProductName(),
                                p.getImage(),
                                p.getTitle(),
                                p.getPrice()
                        );
                        productList.add(productGetAll);
                    }
                }
            }
        }
        return productList;
    }


    @Override
    public List<GetProduct> listSale() {
        List<Product> productList = employeeDAO.findAllEmployees();

        List<GetProduct> listShow = new ArrayList<>();
        for (Product product : productList) {
            GetProduct getProduct = new GetProduct(
                    product.getProductID(),
                    product.getProductName(),
                    product.getImage(),
                    product.getTitle(),
                    product.getPrice(),
                    product.getDiscount());
            listShow.add(getProduct);
        }
        return listShow;
    }

    @Override
    public List<RevenueLisst> getListRevenue(LocalDate start, LocalDate end) {
        List<Order> orderList = orderRepository.findOrderByOrderDateBetween(start, end);
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderIn(orderList);
        List<ProductRevenue> listRevenues = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailList) {
            Order order = orderRepository.findOrderByListOrderDetailContaining(orderDetail);
            if (listRevenues.size() == 0) {
                ProductRevenue revenue = new ProductRevenue();
                revenue.setDate(order.getOrderDate());
                revenue.setProductID(orderDetail.getProduct().getProductID());
                revenue.setProductName(productRepository.findById(orderDetail.getProduct().getProductID()).get().getProductName());
                revenue.setTotalAmout(orderDetail.getTotalAmount());
                listRevenues.add(revenue);
            } else {
                ProductRevenue revenueReserve = new ProductRevenue();
                boolean check = false;
                for (ProductRevenue revenueList : listRevenues) {
                    int findID = orderDetail.getProduct().getProductID();
                    if (revenueList.getProductID() == findID && revenueList.getDate().equals(order.getOrderDate())) {
                        check = true;
                        revenueReserve = revenueList;
                        break;
                    } else {
                        check = false;
                        revenueReserve.setDate(order.getOrderDate());
                        revenueReserve.setProductID(orderDetail.getProduct().getProductID());
                        revenueReserve.setProductName(productRepository.findById(orderDetail.getProduct().getProductID()).get().getProductName());
                        revenueReserve.setTotalAmout(orderDetail.getTotalAmount());
                    }
                }
                if (check) {
                    revenueReserve.setTotalAmout(revenueReserve.getTotalAmout() + orderDetail.getTotalAmount());
                } else {
                    listRevenues.add(revenueReserve);
                }
            }
        }
        List<RevenueLisst> revenueLissts = new ArrayList<>();
        long daysBetween = ChronoUnit.DAYS.between(start, end);
        for (int i = 0; i < daysBetween; i++) {
            RevenueLisst revenueLisst = new RevenueLisst();
            revenueLisst.setDate(start.plusDays(i));
            List<ProductRevenue> revenueList = new ArrayList<>();
            for (ProductRevenue productRevenue : listRevenues) {
                if (productRevenue.getDate().equals(revenueLisst.getDate())){
                    revenueList.add(productRevenue);
                }
            }
            Collections.sort(revenueList, new Comparator<ProductRevenue>() {
                @Override
                public int compare(ProductRevenue b1, ProductRevenue b2) {
                    return b2.getTotalAmout() - b1.getTotalAmout();
                }
            });
            revenueLisst.setRevenueList(revenueList);
            revenueLissts.add(revenueLisst);
        }
        return revenueLissts;
    }

    @Override
    public void exportFile(HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Danh sach san pham");
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Ma san pham");
        headerRow.createCell(1).setCellValue("Ten san pham");
        headerRow.createCell(2).setCellValue("Gia san pham");
        headerRow.createCell(3).setCellValue("So tien giam gia");
        List<Product> productList = productRepository.findAll();
        int rowNum = 1;
        for (Product product : productList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(product.getProductID());
            row.createCell(1).setCellValue(product.getProductName());
            row.createCell(2).setCellValue(product.getPrice());
            row.createCell(3).setCellValue(product.getDiscount());
        }
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=\"ProdutcList.xlsx\"");
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();
    }



}
