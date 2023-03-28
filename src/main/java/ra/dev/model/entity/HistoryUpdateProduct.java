package ra.dev.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "History")
@Data
public class HistoryUpdateProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HistoryID")
    private int historyID;
    @Column(name = "DayUpdate")
    private LocalDate dayUpdate;
    @Column(name = "Quantity")
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "ProductDetailID")
    @JsonIgnore
    private ProductDetail productDetail;
    @Column(name = "Action")
    private String action;
}
