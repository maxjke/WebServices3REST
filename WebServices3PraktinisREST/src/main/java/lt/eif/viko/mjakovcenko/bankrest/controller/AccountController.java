package lt.eif.viko.mjakovcenko.bankrest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lt.eif.viko.mjakovcenko.bankrest.assembler.AccountModelAssembler;
import lt.eif.viko.mjakovcenko.bankrest.exception.AccountNotFoundException;
import lt.eif.viko.mjakovcenko.bankrest.model.Account;
import lt.eif.viko.mjakovcenko.bankrest.repository.AccountRepository;
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
@RequestMapping(path = "accounts")
@Tag(name="Account controller class")
public class AccountController {

    private final AccountRepository repository;
    private final AccountModelAssembler assembler;

    AccountController(AccountRepository repository, AccountModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Operation(
            summary = "Get all accounts",
            description = "Returns all accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all accounts",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Account.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public CollectionModel<EntityModel<Account>> all() {

        List<EntityModel<Account>> accounts = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(accounts, linkTo(methodOn(AccountController.class).all()).withSelfRel());
    }

    @Operation(
            summary = "Create account",
            description = "Creates new account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Account.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    ResponseEntity<?> newAccount(@RequestBody Account newAccount) {

        EntityModel<Account> entityModel = assembler.toModel(repository.save(newAccount));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Operation(
            summary = "Get account by ID",
            description = "Returns one account by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found one account",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Account.class))}),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("{id}")
    public EntityModel<Account> one(@PathVariable Long id) {

        Account account = repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        return assembler.toModel(account);
    }

    @Operation(
            summary = "Update account by ID",
            description = "Returns updated account by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated one account",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Account.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("{id}")
    ResponseEntity<?> replaceAccount(@RequestBody Account newAccount, @PathVariable Long id) {

        Account updatedAccount = repository.findById(id)
                .map(account -> {
                    account.setUsername(newAccount.getUsername());
                    account.setPassword(newAccount.getPassword());
                    return repository.save(account);
                })
                .orElseGet(() -> {
                    newAccount.setId(id);
                    return repository.save(newAccount);
                });

        EntityModel<Account> entityModel = assembler.toModel(updatedAccount);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Operation(
            summary = "Delete account by ID",
            description = "Deletes one account by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted one account"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("{id}")
    ResponseEntity<?> deleteAccount(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
