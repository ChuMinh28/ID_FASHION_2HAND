package ra.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.dev.dto.respone.CollectionGet;
import ra.dev.dto.respone.GetCollection;
import ra.dev.model.entity.Catalog;
import ra.dev.model.entity.Collections;
import ra.dev.model.service.CollectionService;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/collection")
public class CollectionController {
    @Autowired
    CollectionService collectionService;
    @GetMapping()
    public List<GetCollection> getAll(){
        return collectionService.getAll();
    }
    @GetMapping("/{collectionID}")
    public CollectionGet getCollection(@PathVariable("collectionID") int collectionID){
        return collectionService.getByID(collectionID);

    }
    @PostMapping
    public Collections createCollections(@RequestBody Collections collections) {
        return collectionService.save(collections);
    }

    @PutMapping("/{collectionID}")
    public Collections updateCatalog(@PathVariable("collectionID") int collectionID,@RequestBody Collections collections) {
        return collectionService.update(collectionID,collections);
    }

    @GetMapping("/lockCollection/{collectionID}")
    public ResponseEntity<?> deleteCollections(@PathVariable("collectionID") int collectionID) {
        collectionService.delete(collectionID);
        return ResponseEntity.ok("Lock Collections successfully!");
    }

    @GetMapping("/searchCollection")
    public List<Collections> searchCatalogByName(@RequestParam("name") String name ,@RequestParam("searchBy") String searchBy) {
        return collectionService.searchCollectionsBy(searchBy,name);
    }

    @GetMapping("/sortCollectionsByName")
    public List<Collections> sortCollectionsByName(@RequestParam("direction") String direction) {
        return collectionService.sortCollectionsByCollectionsName(direction);
    }

    @GetMapping("/paginationCollections")
    public Map<String, Object> paginationCollections(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "3") int size,@RequestParam("direction") String direction) {
        return collectionService.getPagging(page,size,direction);
    }
}
