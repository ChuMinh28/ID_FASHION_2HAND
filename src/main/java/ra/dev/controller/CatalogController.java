package ra.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.dev.dto.respone.GetCatalog;
import ra.dev.model.service.CatalogService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {
    @Autowired
    CatalogService catalogService;

    @GetMapping()
    public List<GetCatalog> getAll(){
        return catalogService.getAll();
    }
}
