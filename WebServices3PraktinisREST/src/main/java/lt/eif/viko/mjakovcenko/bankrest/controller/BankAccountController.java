package lt.eif.viko.mjakovcenko.bankrest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lt.eif.viko.mjakovcenko.bankrest.assembler.AccountModelAssembler;
import lt.eif.viko.mjakovcenko.bankrest.assembler.BankAccountModelAssembler;
import lt.eif.viko.mjakovcenko.bankrest.exception.AccountNotFoundException;
import lt.eif.viko.mjakovcenko.bankrest.exception.BankAccountNotFoundException;
import lt.eif.viko.mjakovcenko.bankrest.model.Account;
import lt.eif.viko.mjakovcenko.bankrest.model.BankAccount;
import lt.eif.viko.mjakovcenko.bankrest.repository.AccountRepository;
import lt.eif.viko.mjakovcenko.bankrest.repository.BankAccountRepository;
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
@RequestMapping(path = "bankAccounts")
@Tag(name="BankAccount controller class")
public class BankAccountController {

    private final BankAccountRepository repository;

    private final BankAccountModelAssembler assembler;

    BankAccountController(BankAccountRepository repository, BankAccountModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Operation(
            summary = "Get all bank accounts",
            description = "Returns all bank accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all bank accounts",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BankAccount.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public CollectionModel<EntityModel<BankAccount>> all() {

        List<EntityModel<BankAccount>> bankAccounts = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(bankAccounts, linkTo(methodOn(BankAccountController.class).all()).withSelfRel());
    }

    @Operation(
            summary = "Create bank account",
            description = "Creates new bank account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bank account created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BankAccount.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    ResponseEntity<?> newBankAccount(@RequestBody BankAccount newBankAccount) {

        EntityModel<BankAccount> entityModel = assembler.toModel(repository.save(newBankAccount));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Operation(
            summary = "Get bank account by ID",
            description = "Returns one bank account by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found one bank account",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BankAccount.class))}),
            @ApiResponse(responseCode = "404", description = "Bank account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("{id}")
    public EntityModel<BankAccount> one(@PathVariable Long id) {

        BankAccount bankAccount = repository.findById(id)
                .orElseThrow(() -> new BankAccountNotFoundException(id));

        return assembler.toModel(bankAccount);
    }

    @Operation(
            summary = "Update bank account by ID",
            description = "Returns updated bank account by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated one bank account",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BankAccount.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Bank account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("{id}")
    ResponseEntity<?> replaceBankAccount(@RequestBody BankAccount newBankAccount, @PathVariable Long id) {

        BankAccount updatedBankAccount = repository.findById(id)
                .map(bankAccount -> {
                    bankAccount.setBalance(newBankAccount.getBalance());
                    bankAccount.setCurrency(newBankAccount.getCurrency());
                    return repository.save(bankAccount);
                })
                .orElseGet(() -> {
                    newBankAccount.setId(id);
                    return repository.save(newBankAccount);
                });

        EntityModel<BankAccount> entityModel = assembler.toModel(updatedBankAccount);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Operation(
            summary = "Delete bank account by ID",
            description = "Deletes one bank account by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted one bank account"),
            @ApiResponse(responseCode = "404", description = "Bank account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("{id}")
    ResponseEntity<?> deleteBankAccount(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}

