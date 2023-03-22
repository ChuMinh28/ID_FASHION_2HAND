package ra.dev.dto.request;



import java.util.List;

public class ProductByCat {
    private List<Integer> productByCatalog;

    public ProductByCat() {
    }

    public ProductByCat(List<Integer> productByCatalog) {
        this.productByCatalog = productByCatalog;
    }

    public List<Integer> getProductByCatalog() {
        return productByCatalog;
    }

    public void setProductByCatalog(List<Integer> productByCatalog) {
        this.productByCatalog = productByCatalog;
    }
}
