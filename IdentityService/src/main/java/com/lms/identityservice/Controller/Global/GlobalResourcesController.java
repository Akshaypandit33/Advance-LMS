package com.lms.identityservice.Controller.Global;

import com.LMS.DTOs.ResourceDTO.ResourceRequestDTO;
import com.LMS.DTOs.ResourceDTO.ResourceResponseDTO;
import com.lms.identityservice.Mapper.ResourceMapper;
import com.lms.identityservice.Model.Global.GlobalResources;
import com.lms.identityservice.Service.Global.GlobalResourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/global-resources")
@RequiredArgsConstructor
public class GlobalResourcesController {

    private final GlobalResourceService globalResourcesService;

    @PostMapping
    public ResponseEntity<ResourceResponseDTO> createResource(
            @Valid @RequestBody ResourceRequestDTO request) {
        var resource = ResourceMapper.toGlobalResource(request);

        var createdResource = globalResourcesService.createResource(resource);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResourceMapper.toeResourceResponseDTO(createdResource));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResourceResponseDTO> getResourceById(@PathVariable UUID id) {
        var resource = globalResourcesService.getResourceById(id);
        return ResponseEntity.ok((ResourceMapper.toeResourceResponseDTO(resource)));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ResourceResponseDTO> getResourceByName(@PathVariable String name) {
        var resource = globalResourcesService.getResourceByName(name);
        return ResponseEntity.ok(ResourceMapper.toeResourceResponseDTO(resource));
    }

    @GetMapping
    public ResponseEntity<List<ResourceResponseDTO>> getAllResources() {
        List<ResourceResponseDTO> resources = globalResourcesService.getAllResources()
                .stream()
                .map(ResourceMapper::toeResourceResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/type/{resourceType}")
    public ResponseEntity<List<ResourceResponseDTO>> getResourcesByType(
            @PathVariable String resourceType) {
        List<ResourceResponseDTO> resources = globalResourcesService.getResourcesByType(resourceType)
                .stream()
                .map(ResourceMapper::toeResourceResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResourceResponseDTO> updateResource(
            @PathVariable UUID id,
            @Valid @RequestBody ResourceRequestDTO request) {
       var resourceDetails = ResourceMapper.toGlobalResource(request);

        var updatedResource = globalResourcesService.updateResource(id, resourceDetails);
        return ResponseEntity.ok(ResourceMapper.toeResourceResponseDTO(updatedResource));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable UUID id) {
        globalResourcesService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }
}
