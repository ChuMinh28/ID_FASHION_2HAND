package ra.dev.dto.respone;

import org.springframework.stereotype.Repository;
import ra.dev.model.entity.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
@Repository
public class EmployeeDAO {
    @PersistenceContext
    private EntityManager entityManager;
    public List<Product> findAllEmployees() {
        String sql = "select p.ProductID, p.Description, p.Gender, p.Image, p.Limited, p.Price,p.ProductName, p.ProductStatus, p.Shipping, p.Title\n" +
                "from orderdetail o inner join product p on o.productID = p.ProductID\n" +
                "where (select od.OrderStatus from orders od where od.OrderID = o.orderID) = 3\n" +
                "group by o.productID\n" +
                "order by sum(o.Quantity) DESC";
        Query query = entityManager.createNativeQuery(sql, Product.class);
        List<Product> employees = query.getResultList();
        return employees;
    }
}