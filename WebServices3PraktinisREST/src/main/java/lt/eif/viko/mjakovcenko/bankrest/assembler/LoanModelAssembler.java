package lt.eif.viko.mjakovcenko.bankrest.assembler;

import lt.eif.viko.mjakovcenko.bankrest.controller.BankAccountController;
import lt.eif.viko.mjakovcenko.bankrest.controller.LoanController;
import lt.eif.viko.mjakovcenko.bankrest.model.BankAccount;
import lt.eif.viko.mjakovcenko.bankrest.model.Client;
import lt.eif.viko.mjakovcenko.bankrest.model.Loan;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
@Component
public class LoanModelAssembler implements RepresentationModelAssembler<Loan, EntityModel<Loan>> {
    @Override
    public EntityModel<Loan> toModel(Loan loan) {

        return EntityModel.of(loan, //
                linkTo(methodOn(LoanController.class).one(loan.getId())).withSelfRel(),
                linkTo(methodOn(LoanController.class).all()).withRel("loans"));
    }
}
