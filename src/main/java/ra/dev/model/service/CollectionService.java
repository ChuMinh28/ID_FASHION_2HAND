package ra.dev.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.dev.dto.respone.CollectionGet;
import ra.dev.dto.respone.GetCollection;
import ra.dev.model.entity.Collections;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CollectionService {
    List<GetCollection> getAll();

    Collections save(Collections collections);
    Collections update(int id,Collections collections);

    void delete(int id);

    Map<String,Object> getPagging(String search,String sort,String pagination,String name,String direction,int page, int size);
    CollectionGet getByID(int collectionID);

}
