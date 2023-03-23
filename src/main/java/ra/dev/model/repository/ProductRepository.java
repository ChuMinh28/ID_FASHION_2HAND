package ra.dev.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.dev.model.entity.Catalog;
import ra.dev.model.entity.Collections;
import ra.dev.model.entity.Product;
import ra.dev.model.entity.ProductDetail;
import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    Product findProductByListProductDetailContaining(ProductDetail productDetail);
    Product findProductByListProductDetailContainingAndGender(ProductDetail productDetail, Boolean status);
    List<Product> findProductByListCollectionContaining(Collections collections);
    Page<Product> findProductByListCatalogContaining(Catalog catalog, Pageable pageable);
    List<Product> findProductByListCatalogContaining(Catalog catalog);
    Page<Product> findByProductNameContaining(String name,Pageable pageable);
    Page<Product> findByDiscount(int number,Pageable pageable);
    Page<Product> findByPrice(int number,Pageable pageable);

}
