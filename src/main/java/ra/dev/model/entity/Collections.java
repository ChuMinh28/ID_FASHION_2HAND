package ra.dev.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Collection")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Collections {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CollectionID")
    private int collectionID;
    @Column(name = "CollectionName")
    private String collectionName;
    @Column(name = "CollectionStatus")
    private boolean collectionStatus;

//    @Override
//    public String toString() {
//        return this.collectionName;
//    }
}
