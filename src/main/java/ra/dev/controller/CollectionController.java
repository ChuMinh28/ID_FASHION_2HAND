package ra.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.dev.dto.respone.GetCollection;
import ra.dev.model.service.CollectionService;

import java.util.List;

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
}
