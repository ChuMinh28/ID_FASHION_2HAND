package ra.dev.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Sizes")
@Data
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SizesID")
    private int sizeID;
    @Column(name = "sizesName")
    private String sizeName;
    @Column(name = "SizesStatus")
    private boolean sizesStatus;
    @OneToMany(mappedBy = "size")
    @JsonIgnore
    private List<ProductDetail> productDetails = new ArrayList<>();
    @Override
    public String toString() {
        return this.sizeName;
    }
}
