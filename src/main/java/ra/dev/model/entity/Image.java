package ra.dev.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Image")
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ImageID")
    private int imageID;
    @Column(name = "ImageLink")
    private String imageLink;
    @ManyToOne
    @JoinColumn(name = "ProductID")
    @JsonIgnore
    private Product product;

    @Override
    public String toString() {
        return this.imageLink;
    }
}
