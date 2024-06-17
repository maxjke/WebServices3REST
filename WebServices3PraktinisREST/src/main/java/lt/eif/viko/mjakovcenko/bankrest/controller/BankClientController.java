package lt.eif.viko.mjakovcenko.bankrest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lt.eif.viko.mjakovcenko.bankrest.assembler.ClientModelAssembler;
import lt.eif.viko.mjakovcenko.bankrest.exception.ClientNotFoundException;
import lt.eif.viko.mjakovcenko.bankrest.model.Client;
import lt.eif.viko.mjakovcenko.bankrest.repository.ClientRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "clients")
@Tag(name="Bank client controller class")
public class BankClientController {

    private final ClientRepository repository;
    private final ClientModelAssembler assembler;

    BankClientController(ClientRepository repository, ClientModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Operation(
            summary = "Get all clients",
            description = "Returns all clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all clients",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public CollectionModel<EntityModel<Client>> all() {

        List<EntityModel<Client>> clients = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(clients, linkTo(methodOn(BankClientController.class).all()).withSelfRel());
    }

    @Operation(
            summary = "Create client",
            description = "Creates new client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    ResponseEntity<?> newClient(@RequestBody Client newClient) {

        EntityModel<Client> entityModel = assembler.toModel(repository.save(newClient));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Operation(
            summary = "Get client by ID",
            description = "Returns one client by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found one client",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))}),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("{id}")
    public EntityModel<Client> one(@PathVariable Long id) {

        Client client = repository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        return assembler.toModel(client);
    }

    @Operation(
            summary = "Update client by ID",
            description = "Returns updated client by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated one client",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Client.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("{id}")
    ResponseEntity<?> replaceClient(@RequestBody Client newClient, @PathVariable Long id) {

        Client updatedClient = repository.findById(id)
                .map(client -> {
                    client.setFirstName(newClient.getFirstName());
                    client.setLastName(newClient.getLastName());
                    return repository.save(client);
                })
                .orElseGet(() -> {
                    newClient.setId(id);
                    return repository.save(newClient);
                });

        EntityModel<Client> entityModel = assembler.toModel(updatedClient);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Operation(
            summary = "Delete client by ID",
            description = "Deletes one client by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted one client"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("{id}")
    ResponseEntity<?> deleteClient(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
