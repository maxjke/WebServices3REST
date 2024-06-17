package lt.eif.viko.mjakovcenko.bankrest.assembler;

import lt.eif.viko.mjakovcenko.bankrest.controller.CreditCardController;
import lt.eif.viko.mjakovcenko.bankrest.controller.LoanController;
import lt.eif.viko.mjakovcenko.bankrest.model.Client;
import lt.eif.viko.mjakovcenko.bankrest.model.CreditCard;
import lt.eif.viko.mjakovcenko.bankrest.model.Loan;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class CreditCardModelAssembler  implements RepresentationModelAssembler<CreditCard, EntityModel<CreditCard>> {
    @Override
    public EntityModel<CreditCard> toModel(CreditCard creditCard) {

        return EntityModel.of(creditCard, //
                linkTo(methodOn(CreditCardController.class).one(creditCard.getId())).withSelfRel(),
                linkTo(methodOn(CreditCardController.class).all()).withRel("creditCards"));
    }
}
