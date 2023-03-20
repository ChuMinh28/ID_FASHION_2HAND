package ra.dev.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import ra.dev.dto.respone.GetCatalog;
import ra.dev.model.entity.Catalog;
import ra.dev.model.repository.CatalogRepository;
import ra.dev.model.service.CatalogService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogServiceImp implements CatalogService {
    @Autowired
    CatalogRepository catalogRepository;

    @Override
    public List<GetCatalog> getAll() {
        List<Catalog> catalogList = catalogRepository.findAll();
        List<GetCatalog> getCatalogsList = new ArrayList<>();
        for (Catalog catalog : catalogList) {
            GetCatalog getCatalog = new GetCatalog(catalog.getCatalogName());
            getCatalogsList.add(getCatalog);
        }
        return getCatalogsList;
    }


}
