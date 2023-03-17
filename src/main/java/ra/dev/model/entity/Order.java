package ra.dev.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private int orderStatus;
    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;
    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private List<OrderDetail> listOrderDetail = new ArrayList<>();

}
