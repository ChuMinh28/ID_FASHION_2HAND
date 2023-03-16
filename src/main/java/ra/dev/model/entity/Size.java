package ra.dev.model.entity;

import lombok.Data;

import javax.persistence.*;

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

    @Override
    public String toString() {
        return this.sizeName;
    }
}
