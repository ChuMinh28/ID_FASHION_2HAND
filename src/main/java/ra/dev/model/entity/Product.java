package ra.dev.model.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private int productID;
    @Column(name = "ProductName")
    private String productName;
    @Column(name = "ProductStatus")
    private boolean productStatus;
    @Column(name = "Image")
    private String image;
    @Column(name = "Title")
    private String title;
    @Column(name = "Description")
    private String description;
    @Column(name = "Price")
    private int price;
    @Column(name = "Discount")
    private int discount;
    @Column(name = "Gender")
    private boolean gender;

    @Column(name = "Limited")
    private boolean limited;
    @Column(name = "Shipping")
    private boolean shipping;
    @OneToMany(mappedBy = "product")
    List<ProductDetail> listProductDetail = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "Product_Catalog", joinColumns = @JoinColumn(name = "productID"), inverseJoinColumns = @JoinColumn(name = "catalogID"))
    private List<Catalog> listCatalog;
    @ManyToMany
    @JoinTable(name = "Product_Collection", joinColumns = @JoinColumn(name = "productID"), inverseJoinColumns = @JoinColumn(name = "collectionID"))
    private List<Collections> listCollection;
    @OneToMany(mappedBy = "product")
    List<Image> listImage = new ArrayList<>();
    @OneToMany(mappedBy = "product")
    List<OrderDetail> orderDetails = new ArrayList<>();



}
