package ra.dev.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import ra.dev.dto.respone.GetCatalog;
import ra.dev.model.entity.Catalog;

import java.util.List;
import java.util.Map;

public interface CatalogService {
    List<GetCatalog> getAll();

    Catalog save(Catalog catalog);

    Catalog update(int id,Catalog catalog);

    void delete(int id);
    Catalog getById(int id);
    List<Catalog> searchCatalogByCatalogName(String name);
    List<Catalog> sortCatalogByCatalogName(String direction);
    Map<String,Object> getPagging(int page, int size);
}
