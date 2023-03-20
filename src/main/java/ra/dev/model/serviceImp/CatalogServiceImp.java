package ra.dev.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ra.dev.dto.respone.GetCatalog;
import ra.dev.model.entity.Catalog;
import ra.dev.model.repository.CatalogRepository;
import ra.dev.model.service.CatalogService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Catalog save(Catalog catalog) {
        catalog.setCatalogStatus(true);
        return catalogRepository.save(catalog);
    }

    @Override
    public Catalog update(int id, Catalog catalog) {
        Catalog catalogold=catalogRepository.findById(id).get();
        catalogold.setCatalogName(catalog.getCatalogName());
        catalogold.setCatalogStatus(catalog.isCatalogStatus());
        return catalogRepository.save(catalogold);
    }

    @Override
    public void delete(int id) {
      Catalog catalog=catalogRepository.findById(id).get();
      catalog.setCatalogStatus(false);
      catalogRepository.save(catalog);
    }

    @Override
    public Catalog getById(int id) {
        return catalogRepository.findById(id).get();
    }

    @Override
    public List<Catalog> searchCatalogByCatalogName(String name) {
        return catalogRepository.findByCatalogNameContaining(name);
    }

    @Override
    public List<Catalog> sortCatalogByCatalogName(String direction) {
        if (direction.equalsIgnoreCase("asc")){
            return catalogRepository.findAll(Sort.by("CatalogName").ascending());
        }else {
            return catalogRepository.findAll(Sort.by("CatalogName").descending());
        }
    }

    @Override
    public Map<String,Object> getPagging( int page, int size) {
       Pageable  pageable= PageRequest.of(page,size);
        Page<Catalog> catalogPage=catalogRepository.findAll(pageable);
        Map<String,Object> data=new HashMap<>();
        data.put("Catagory in page",catalogPage.getContent());
        data.put("Total elements in Catalog",catalogPage.getTotalElements());
        data.put("Total page in Catalog",catalogPage.getTotalPages());
        data.put("Size",catalogPage.getSize());
        return data;
    }


}
