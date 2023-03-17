package ra.dev.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Color")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ColorID")
    private int colorID;
    @Column(name = "ColorName")
    private String colorName;
    @Column(name = "ColorStatus")
    private boolean colorStatus;
    @OneToMany(mappedBy = "color")
    @JsonIgnore
    private List<ProductDetail> productDetails = new ArrayList<>();


}
