package ra.dev.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID")
    private int orderID;
    @Column(name = "Email")
    private String email;
    @Column(name = "OrderDate")
    private LocalDate orderDate;
    @Column(name = "FullName")
    private String fullName;
    @Column(name = "Address")
    private String address;
    @Column(name = "TotalAmount")
    private int totalAmount;
    @Column(name = "OrderStatus")
    private boolean orderStatus;
    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;
}
