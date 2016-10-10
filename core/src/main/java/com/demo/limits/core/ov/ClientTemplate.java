package com.demo.limits.core.ov;

import com.demo.limits.core.api.ClientOperations;

import com.demo.limits.core.exception.CollectionCompositeDeleteException;
import com.demo.limits.core.exception.CollectionNotFoundException;

import com.demo.limits.common.validation.Assertions;
import com.demo.limits.common.validation.ValidationGroup;
import com.demo.limits.domain.core.EClient;
import com.demo.limits.model.core.Client;
import com.demo.limits.model.common.validation.group.New;
import com.demo.limits.model.common.validation.group.Update;
import com.demo.limits.repository.core.ClientRepository;
import com.demo.limits.repository.exceptions.EntityDuplicateException;
import com.demo.limits.servicessupport.mappers.AuditMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;


@Named("ovClientTemplate")
@Transactional
public class ClientTemplate implements ClientOperations {

    @Inject
    ClientRepository ClientRepository;

    @Inject
    Assertions Assert;

    @Inject
    AuditMapper auditMapper;


    @Override
    public List<Client> Clients() {
        List<Client> Clients = new ArrayList<>();
        for(EClient eClient : ClientRepository.findAll()){
            Clients.add(toValue(eClient));
        }
        return Clients;
    }

    @Override
    public Client getClientById(Long id) {
        EClient eClient = ClientRepository.getOne(id);
        Assert.notNull(eClient,
                CollectionNotFoundException.class,
                new Object[]{"type", Client.class.getSimpleName(), "id", id});

        return toValue(eClient);
    }

    @Override
    @ValidationGroup({New.class})
    public @NotNull Client create(@Valid Client Client) {


       
        EClient eClient = new EClient(
                Client.getName().trim(),
                Client.getDescription(),
                0
                
        );
        eClient = ClientRepository.saveAndFlush(eClient);
        return toValue(eClient);
    }

    @Override
    @ValidationGroup({Default.class, Update.class})
    public Client update(@Valid Client Client) {

        Long id = Client.getId();
        EClient eClient = ClientRepository.getOne(id);

        Assert.notNull(eClient,
                CollectionNotFoundException.class,
                new Object[]{"type", Client.class.getSimpleName(), "id", id}
        );


        eClient.setName(Client.getName().trim());
        eClient.setDescription(Client.getDescription());
        eClient = ClientRepository.saveAndFlush(eClient);
        return toValue(eClient);
    }

    @Override
    public void delete(Long id) {

        EClient eClient = ClientRepository.getOne(id);

        Assert.notNull(eClient,
                CollectionNotFoundException.class,
                new Object[]{"type", Client.class.getSimpleName(), "id", id}
        );


            ClientRepository.delete(eClient.getId());
    }


    private Client toValue(EClient eClient){
        return new Client(
                eClient.getId(),
              
                eClient.getName(),
                eClient.getDescription(),
              
               
                auditMapper.map(eClient.getCreateInfo(), eClient.getUpdateInfo())
            );
    }

}
