package lt.eif.viko.mjakovcenko.bankrest.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Maksim J",
                        email = "m.jakovcenko@stud.viko.lt",
                        url = "eif.viko.lt"
                ),
                description = "Open API documentation for Bank Application",
                title = "Bank Application REST",
                version = "1.0",
                license = @License(
                        name = "Free to use",
                        url = "eif.viko.lt"
                )
        ), servers = {
                @Server(
                        description = "Local DEV",
                        url = "localhost:5252"
                ),
                @Server(
                description = "Test Server",
                url = "localhost:5252"
        )
}

)
public class OpenApiConfig {
}
