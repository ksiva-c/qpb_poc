package com.demo.limits.rest.services.core;

import com.demo.limits.core.api.ClientOperations;
import com.demo.limits.model.core.Client;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RestController("ClientResourceV1")
@RequestMapping(
        value = "v1/Client"
)
@Api(value="Client", description="Manage Clients")
public class ClientResource {

    @Named("ovClientTemplate")
    @Inject
    ClientOperations ClientOperations;

    private static final Logger logger = LoggerFactory.getLogger(ClientResource.class.getName());

    @ApiOperation(
            value = "Create Client",
            notes = "360 Clients",
            response = Client.class,
            consumes = "application/json",
            produces = "application/json"
    )
    @RequestMapping(value = "", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Client create(@RequestBody Client client){
        return ClientOperations.create(client);
    }

    @Transactional
    @ApiOperation(
            value = "Delete Client",
            notes = "360 Clients",
            response = Client.class,
            consumes = "application/json",
            produces = "application/json"
    )
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable ("id") Long clientId){
        ClientOperations.delete(clientId);
    }

    @ApiOperation(
            value = "Update Client",
            notes = "360 Clients",
            response = Client.class,
            consumes = "application/json",
            produces = "application/json"
    )
    @RequestMapping(value = "", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Client update(@RequestBody Client client){
        return ClientOperations.update(client);
    }

    @ApiOperation(
            value = "Get All Clients",
            notes = "360 Clients",
            response = Client.class,
            consumes = "application/json",
            produces = "application/json",
            responseContainer = "list"
    )
    @RequestMapping(
            value = "", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Client> list() {
           return ClientOperations.Clients();
    }





}

