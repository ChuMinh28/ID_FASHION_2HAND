package ra.dev.model.entity;

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
    @ManyToOne
    @JoinColumn(name = "productID")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "orderID")
    private Order order;


}
