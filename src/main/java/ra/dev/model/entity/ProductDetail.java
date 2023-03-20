package ra.dev.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ProductDetail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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


}
