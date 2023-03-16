package ra.dev.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Catalog")
@Data
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CatalogID")
    private int catalogID;
    @Column(name = "CatalogName")
    private String catalogName;
    @Column(name = "catalogStatus")
    private boolean catalogStatus;
}
