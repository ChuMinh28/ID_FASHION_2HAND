package ra.dev.model.service;

import ra.dev.dto.respone.CollectionGet;
import ra.dev.dto.respone.GetCollection;
import ra.dev.model.entity.Collections;

import java.util.Collection;
import java.util.List;

public interface CollectionService {
    List<GetCollection> getAll();

    CollectionGet getByID(int collectionID);

}
