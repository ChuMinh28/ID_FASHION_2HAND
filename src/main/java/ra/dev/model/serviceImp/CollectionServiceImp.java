package ra.dev.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.dev.dto.respone.CollectionGet;
import ra.dev.dto.respone.GetCollection;
import ra.dev.model.entity.Collections;
import ra.dev.model.entity.Product;
import ra.dev.model.repository.CollectionRepository;
import ra.dev.model.repository.ProductRepository;
import ra.dev.model.service.CollectionService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        for (Collections collections: collectionList) {
            GetCollection collectionGet = new GetCollection(collections.getCollectionName());
            getCollectionList.add(collectionGet);
        }
        return getCollectionList;
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
