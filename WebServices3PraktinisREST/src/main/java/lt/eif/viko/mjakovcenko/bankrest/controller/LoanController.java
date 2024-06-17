package lt.eif.viko.mjakovcenko.bankrest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lt.eif.viko.mjakovcenko.bankrest.assembler.LoanModelAssembler;
import lt.eif.viko.mjakovcenko.bankrest.exception.LoanNotFoundException;
import lt.eif.viko.mjakovcenko.bankrest.model.Loan;
import lt.eif.viko.mjakovcenko.bankrest.repository.LoanRepository;
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
@RequestMapping(path = "loans")
@Tag(name="Loan controller class")
public class LoanController {

    private final LoanRepository repository;
    private final LoanModelAssembler assembler;

    LoanController(LoanRepository repository, LoanModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Operation(
            summary = "Get all loans",
            description = "Returns all loans")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all loans",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Loan.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public CollectionModel<EntityModel<Loan>> all() {

        List<EntityModel<Loan>> loans = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(loans, linkTo(methodOn(LoanController.class).all()).withSelfRel());
    }

    @Operation(
            summary = "Create loan",
            description = "Creates new loan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Loan created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Loan.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    ResponseEntity<?> newLoan(@RequestBody Loan newLoan) {

        EntityModel<Loan> entityModel = assembler.toModel(repository.save(newLoan));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Operation(
            summary = "Get loan by ID",
            description = "Returns one loan by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found one loan",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Loan.class))}),
            @ApiResponse(responseCode = "404", description = "Loan not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("{id}")
    public EntityModel<Loan> one(@PathVariable Long id) {

        Loan loan = repository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException(id));

        return assembler.toModel(loan);
    }

    @Operation(
            summary = "Update loan by ID",
            description = "Returns updated loan by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated one loan",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Loan.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Loan not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("{id}")
    ResponseEntity<?> replaceLoan(@RequestBody Loan newLoan, @PathVariable Long id) {

        Loan updatedLoan = repository.findById(id)
                .map(loan -> {
                    loan.setLoanPercent(newLoan.getLoanPercent());
                    loan.setLoanStartDate(newLoan.getLoanStartDate());
                    loan.setLoanEndDate(newLoan.getLoanEndDate());
                    loan.setLoanAmount(newLoan.getLoanAmount());
                    loan.setMonthlyPayment(newLoan.getMonthlyPayment());
                    return repository.save(loan);
                })
                .orElseGet(() -> {
                    newLoan.setId(id);
                    return repository.save(newLoan);
                });

        EntityModel<Loan> entityModel = assembler.toModel(updatedLoan);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @Operation(
            summary = "Delete loan by ID",
            description = "Deletes one loan by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted one loan"),
            @ApiResponse(responseCode = "404", description = "Loan not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("{id}")
    ResponseEntity<?> deleteLoan(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
