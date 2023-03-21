package ra.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.dev.model.entity.Size;
import ra.dev.model.service.SizeService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/size")
public class SizeController {
    @Autowired
    private SizeService sizeService;

    @PostMapping("createSize")
    public ResponseEntity<?> createSize(@RequestBody Size size) {
        try {
            return ResponseEntity.ok(sizeService.saveOrUpdate(size));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Create failed!");
        }
    }

    @GetMapping("getAllSize")
    public ResponseEntity<?> getALlSize() {
        try {
            return ResponseEntity.ok(sizeService.getAllSize());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error!!!");
        }
    }

    @PatchMapping("updateSize/{sizeID}")
    public ResponseEntity<?> updateColor(@PathVariable("sizeID") int sizeID, @RequestBody Size size) {
        try {
            boolean check = sizeService.updateSize(sizeID, size);
            if (check) {
                return ResponseEntity.ok("Update size successfully!");
            } else {
                return ResponseEntity.badRequest().body("Error!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error!!!");
        }
    }

    @PatchMapping("changeSizeStatus/{sizeID}")
    public ResponseEntity<?> changeColorStatus(@PathVariable("sizeID") int sizeID, @RequestParam("action") String action) {
        try {
            boolean check = sizeService.changeSizeStatus(sizeID, action);
            if (check) {
                return ResponseEntity.ok("Unlock size successfully!");
            } else {
                return ResponseEntity.ok("Lock size successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error!!!");
        }
    }
}
