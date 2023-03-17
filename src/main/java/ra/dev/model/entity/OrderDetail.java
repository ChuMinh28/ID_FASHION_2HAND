package ra.dev.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "OrderDetail")
@Data
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderDetailID")
    private int orderDetailID;
    @Column(name = "Quantity")
    private int quantity;
    @Column(name = "Price")
    private int price;
    @Column(name = "TotalAmount")
    private int totalAmount;
    @Column(name = "Color")
    private String color;
    @Column(name = "Size")
    private String size;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "productID")
    private Product product;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "orderID")
    private Order order;
}
