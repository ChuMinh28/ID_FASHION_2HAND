package ra.dev.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.dev.dto.respone.CollectionGet;
import ra.dev.dto.respone.GetCollection;
import ra.dev.model.entity.Catalog;
import ra.dev.model.entity.Collections;
import ra.dev.model.entity.Product;
import ra.dev.model.repository.CollectionRepository;
import ra.dev.model.repository.ProductRepository;
import ra.dev.model.service.CollectionService;

import java.util.*;

@Service
public class CollectionServiceImp implements CollectionService {
    @Autowired
    CollectionRepository collectionRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<GetCollection> getAll() {
        List<Collections> collectionList = collectionRepository.findAll();
        List<GetCollection> getCollectionList = new ArrayList<>();
        for (Collections collections : collectionList) {
            GetCollection collectionGet = new GetCollection(collections.getCollectionName());
            getCollectionList.add(collectionGet);
        }
        return getCollectionList;
    }

    @Override
    public Collections save(Collections collections) {
        collections.setCollectionStatus(true);
        return collectionRepository.save(collections);
    }

    @Override
    public Collections update(int id, Collections collection) {
        Collections collectionOld = collectionRepository.findById(id).get();
        collectionOld.setCollectionName(collection.getCollectionName());
        collectionOld.setCollectionDescription(collection.getCollectionDescription());
        collectionOld.setCollectionStatus(collection.isCollectionStatus());
        return collectionRepository.save(collectionOld);
    }

    @Override
    public void delete(int id) {
        Collections collectionOld = collectionRepository.findById(id).get();
        collectionOld.setCollectionStatus(false);
         collectionRepository.save(collectionOld);
    }

    @Override
    public List<Collections> searchCollectionsBy(String searchBy, String name) {
        if (searchBy.equalsIgnoreCase("name")) {
            return collectionRepository.findByCollectionNameContaining(name);
        } else {
            return collectionRepository.findByCollectionDescriptionContaining(name);
        }
    }

    @Override
    public List<Collections> sortCollectionsByCollectionsName(String direction) {
        if (direction.equalsIgnoreCase("asc")) {
            return collectionRepository.findAll(Sort.by("collectionName").ascending());
        } else {
            return collectionRepository.findAll(Sort.by("collectionName").descending());
        }
    }

    @Override
    public Map<String, Object> getPagging(int page, int size,String direction) {
        Pageable  pageable;
        if (direction.equalsIgnoreCase("asc")){
            pageable = PageRequest.of(page, size, Sort.by("CollectionName").ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by("CollectionName").descending());
        }

        Page<Collections> catalogPage=collectionRepository.findAll(pageable);
        Map<String,Object> data=new HashMap<>();
        data.put("Collection in page",catalogPage.getContent());
        data.put("Total elements in Catalog",catalogPage.getTotalElements());
        data.put("Total page in Catalog",catalogPage.getTotalPages());
        data.put("Size",catalogPage.getSize());
        return data;
    }



    @Override
    public CollectionGet getByID(int collectionID) {
        Collections collections = collectionRepository.findById(collectionID).get();

        List<Product> productList = productRepository.findProductByListCollectionContaining(collections);

        CollectionGet collectionsNew = new CollectionGet();
        collectionsNew.setCollectionID(collections.getCollectionID());
        collectionsNew.setCollectionName(collections.getCollectionName());
        collectionsNew.setCollectionStatus(collections.isCollectionStatus());
        collectionsNew.setProductList(productList);
        return collectionsNew;
    }
}
