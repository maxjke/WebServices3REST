package lt.eif.viko.mjakovcenko.bankrest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lt.eif.viko.mjakovcenko.bankrest.assembler.CreditCardModelAssembler;
import lt.eif.viko.mjakovcenko.bankrest.exception.CreditCardNotFoundException;
import lt.eif.viko.mjakovcenko.bankrest.model.CreditCard;
import lt.eif.viko.mjakovcenko.bankrest.repository.CreditCardRepository;
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
@RequestMapping(path = "creditCards")
@Tag(name="CreditCard controller class")
public class CreditCardController {

    private final CreditCardRepository repository;
    private final CreditCardModelAssembler assembler;

    CreditCardController(CreditCardRepository repository, CreditCardModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Operation(
            summary = "Get all credit cards",
            description = "Returns all credit cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all credit cards",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreditCard.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public CollectionModel<EntityModel<CreditCard>> all() {

        List<EntityModel<CreditCard>> creditCards = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(creditCards, linkTo(methodOn(CreditCardController.class).all()).withSelfRel());
    }

    @Operation(
            summary = "Create credit card",
            description = "Creates new credit card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Credit card created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreditCard.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    ResponseEntity<?> newCreditCard(@RequestBody CreditCard newCreditCard) {

        EntityModel<CreditCard> entityModel = assembler.toModel(repository.save(newCreditCard));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Operation(
            summary = "Get credit card by ID",
            description = "Returns one credit card by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found one credit card",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreditCard.class))}),
            @ApiResponse(responseCode = "404", description = "Credit card not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("{id}")
    public EntityModel<CreditCard> one(@PathVariable Long id) {

        CreditCard creditCard = repository.findById(id)
                .orElseThrow(() -> new CreditCardNotFoundException(id));

        return assembler.toModel(creditCard);
    }

    @Operation(
            summary = "Update credit card by ID",
            description = "Returns updated credit card by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated one credit card",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreditCard.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Credit card not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("{id}")
    ResponseEntity<?> replaceCreditCard(@RequestBody CreditCard newCreditCard, @PathVariable Long id) {

        CreditCard updatedCreditCard = repository.findById(id)
                .map(creditCard -> {
                    creditCard.setCardLimit(newCreditCard.getCardLimit());
                    creditCard.setCardNumber(newCreditCard.getCardNumber());
                    creditCard.setCVC(newCreditCard.getCVC());
                    creditCard.setExpireDate(newCreditCard.getExpireDate());
                    return repository.save(creditCard);
                })
                .orElseGet(() -> {
                    newCreditCard.setId(id);
                    return repository.save(newCreditCard);
                });

        EntityModel<CreditCard> entityModel = assembler.toModel(updatedCreditCard);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Operation(
            summary = "Delete credit card by ID",
            description = "Deletes one credit card by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted one credit card"),
            @ApiResponse(responseCode = "404", description = "Credit card not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("{id}")
    ResponseEntity<?> deleteCreditCard(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
