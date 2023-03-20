package ra.dev.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Catalog")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CatalogID")
    private int catalogID;
    @Column(name = "CatalogName")
    private String catalogName;
    @Column(name = "CatalogStatus")
    private boolean catalogStatus;
    @Override
    public String toString(){
        return this.getCatalogName();
    }
}
