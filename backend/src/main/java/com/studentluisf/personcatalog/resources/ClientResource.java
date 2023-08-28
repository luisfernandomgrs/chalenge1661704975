package com.studentluisf.personcatalog.resources;

import com.studentluisf.personcatalog.dto.ClientDTO;
import com.studentluisf.personcatalog.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {
    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<Page<ClientDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                   @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
                                                   @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                                                   @RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return ResponseEntity.ok(this.clientService.findAllPaged(pageRequest));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ClientDTO> insert(@RequestBody ClientDTO requestInClientDTO) {
        ClientDTO clientDTO = this.clientService.insert(requestInClientDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clientDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(clientDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> update(@PathVariable Long id, @RequestBody ClientDTO requestInClientDTO) {
        return ResponseEntity.ok(this.clientService.update(id, requestInClientDTO));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
