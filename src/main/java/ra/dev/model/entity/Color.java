package ra.dev.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Color")
@Data
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ColorID")
    private int colorID;
    @Column(name = "ColorName")
    private String colorName;
    @Column(name = "ColorStatus")
    private boolean colorStatus;

    @Override
    public String toString() {
        return this.colorName;
    }
}
