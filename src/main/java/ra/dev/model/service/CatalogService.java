package ra.dev.model.service;


import ra.dev.dto.respone.GetCatalog;
import ra.dev.model.entity.Catalog;
import java.util.List;

public interface CatalogService {
    List<GetCatalog> getAll();


}
