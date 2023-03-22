package ra.dev.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.dev.dto.respone.CollectionGet;
import ra.dev.dto.respone.GetCollection;
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
    public Map<String,Object> pagination(Page<Collections> collectionsPage){
        Map<String,Object> data=new HashMap<>();
        data.put("Collection in page",collectionsPage.getContent());
        data.put("Total elements in Collection", collectionsPage.getTotalElements());
        data.put("Total page in Collection", collectionsPage.getTotalPages());
        data.put("Size", collectionsPage.getSize());
        return data;
    }

    @Override
    public Map<String, Object> getPagging(String search, String sort,String name, String direction, int page, int size) {
        if (search.equals("0")&&sort.equals("0")){
            Pageable pageable=PageRequest.of(page,size);
            Page<Collections> collectionsPage=collectionRepository.findAll(pageable);
            return pagination(collectionsPage);
        } else if (search.equals("0")) {
            Pageable pageable;
            if (direction.equalsIgnoreCase("asc")){
                pageable=PageRequest.of(page,size,Sort.by("CollectionName").ascending());
            }else {
                pageable=PageRequest.of(page,size,Sort.by("CollectionName").ascending());
            }
            Page<Collections> collectionsPage=collectionRepository.findAll(pageable);
            return pagination(collectionsPage);
        } else if (sort.equals("0")) {
            Pageable pageable=PageRequest.of(page,size);
            Page<Collections> collectionsPage=collectionRepository.findByCollectionNameContaining(name,pageable);
            return pagination(collectionsPage);
        }else {
            Pageable pageable;
            if (direction.equalsIgnoreCase("asc")){
                pageable=PageRequest.of(page,size,Sort.by("CollectionName").ascending());
            }else {
                pageable=PageRequest.of(page,size,Sort.by("CollectionName").ascending());
            }
            Page<Collections> collectionsPage=collectionRepository.findByCollectionNameContaining(name,pageable);
            return pagination(collectionsPage);
        }
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
