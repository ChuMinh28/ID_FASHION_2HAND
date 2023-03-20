package ra.dev.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
//    @Override
//    public String toString(){
//        return this.getImageLink();
//    }

}
