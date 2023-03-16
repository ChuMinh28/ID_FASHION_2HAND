package ra.dev.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.dev.dto.respone.GetCollection;
import ra.dev.model.entity.Collections;
import ra.dev.model.repository.CollectionRepository;
import ra.dev.model.service.CollectionService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CollectionServiceImp implements CollectionService {
    @Autowired
    CollectionRepository collectionRepository;
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
}
