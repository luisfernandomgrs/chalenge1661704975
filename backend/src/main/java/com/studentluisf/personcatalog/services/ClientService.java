package com.studentluisf.personcatalog.services;

import com.studentluisf.personcatalog.dto.ClientDTO;
import com.studentluisf.personcatalog.entities.Client;
import com.studentluisf.personcatalog.repositories.ClientRepository;
import com.studentluisf.personcatalog.services.exceptions.DatabaseException;
import com.studentluisf.personcatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
        return repository.findAll(pageRequest).map(client -> new ClientDTO(client));
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        Optional<Client> optional = repository.findById(id);
        return new ClientDTO(optional.orElseThrow(() -> new ResourceNotFoundException("Entity not found")));
    }

    @Transactional
    public ClientDTO insert(ClientDTO clientDTO) {
        Client client = new Client();
        this.copyDtoToEntity(client, clientDTO);
        repository.save(client);

        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO clientDTO) {
        try {
            Client client = repository.getReferenceById(id);
            this.copyDtoToEntity(client, clientDTO);
            repository.save(client);

            return  new ClientDTO(client);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            Optional<Client> optional = repository.findById(id);
            optional.orElseThrow(() -> new ResourceNotFoundException("Id not found"));

            repository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    private void copyDtoToEntity(Client entity, ClientDTO clientDTO) {
        entity.setName(clientDTO.getName());
        entity.setCpf(clientDTO.getCpf());
        entity.setIncome(clientDTO.getIncome());
        entity.setBirthDate(clientDTO.getBirthDate());
        entity.setChildren(clientDTO.getChildren());
    }
}
