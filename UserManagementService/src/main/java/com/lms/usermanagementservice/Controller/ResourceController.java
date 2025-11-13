package com.lms.usermanagementservice.Controller;


import com.LMS.DTOs.DeleteResourceDTO;
import com.LMS.DTOs.ResourceDTO.ResourceRequestDTO;
import com.LMS.DTOs.ResourceDTO.ResourceResponseDTO;
import com.lms.usermanagementservice.Service.ResourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    // Create Resource
    @PostMapping
    public ResponseEntity<ResourceResponseDTO> addResource(@Valid @RequestBody ResourceRequestDTO requestDTO) {
        ResourceResponseDTO created = resourceService.addResource(requestDTO);
        return ResponseEntity.ok(created);
    }

    // Get All Resources (paginated)
    @GetMapping
    public ResponseEntity<Page<ResourceResponseDTO>> getAllResources(Pageable pageable) {
        Page<ResourceResponseDTO> resources = resourceService.findAllResources(pageable);
        return ResponseEntity.ok(resources);
    }

    // Get Resource by ID
    @GetMapping("/{id}")
    public ResponseEntity<ResourceResponseDTO> getResourceById(@PathVariable Long id) {
        ResourceResponseDTO resource = resourceService.findResourceById(id);
        return ResponseEntity.ok(resource);
    }

    // Delete Resource by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResourceDTO> deleteResource(@PathVariable Long id) {
        DeleteResourceDTO deleted = resourceService.deleteResourceById(id);
        return ResponseEntity.ok(deleted);
    }
}
