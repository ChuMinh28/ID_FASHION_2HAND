package ra.dev.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ProductDetail")
@Data
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductDetailID")
    private int productDetailID;
    @Column(name = "Discount")
    private int discount;
    @Column(name = "Quantity")
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "ProductID")
    @JsonIgnore
    private Product product;

    @ManyToOne
    @JoinColumn(name = "SizeID")
    private Size size;
    @ManyToOne
    @JoinColumn(name = "ColorID")
    private Color color;




//    @ManyToMany
//    @JoinTable(name = "Product_Sizes", joinColumns = @JoinColumn(name = "productDetailID"), inverseJoinColumns = @JoinColumn(name = "sizesID"))
//    private List<Size> listSizes;
//    @ManyToMany
//    @JoinTable(name = "Product_Color", joinColumns = @JoinColumn(name = "productDetailID"), inverseJoinColumns = @JoinColumn(name = "colorID"))
//    private List<Color> listColor;


}
