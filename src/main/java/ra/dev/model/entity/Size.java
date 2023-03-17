package ra.dev.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Sizes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
