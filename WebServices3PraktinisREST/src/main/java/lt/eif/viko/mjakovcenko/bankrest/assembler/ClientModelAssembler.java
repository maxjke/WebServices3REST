package lt.eif.viko.mjakovcenko.bankrest.assembler;

import lt.eif.viko.mjakovcenko.bankrest.controller.BankClientController;
import lt.eif.viko.mjakovcenko.bankrest.model.Client;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ClientModelAssembler implements RepresentationModelAssembler<Client, EntityModel<Client>> {
    @Override
    public EntityModel<Client> toModel(Client client) {

        return EntityModel.of(client, //
                linkTo(methodOn(BankClientController.class).one(client.getId())).withSelfRel(),
                linkTo(methodOn(BankClientController.class).all()).withRel("clients"));
    }
}
