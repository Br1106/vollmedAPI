package med.voll.api.infra.springdoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocConfiguration {

	
	//Método de configuração que habilita o a pagina de documentação do Spring Doc 
	//Enviar no cabeçalho um token JWT
	// @SecurityRequirement(name = "bearer-key")-> anotação necessária para bloquear requisições nos controllers (métodos específicos ou na própria classe)
	@Bean
	public OpenAPI customOpenAPI() {
	    return new OpenAPI()
	            .components(new Components()
	                    .addSecuritySchemes("bearer-key",
	                            new SecurityScheme()
	                                    .type(SecurityScheme.Type.HTTP)
	                                    .scheme("bearer")
	                                    .bearerFormat("JWT")))
	                    .info(new Info()
	                            .title("Voll.med API")
	                            .description("API Rest da aplicação Voll.med, contendo as funcionalidades de CRUD de médicos e de pacientes, além de agendamento e cancelamento de consultas. Desenvolvido como proposta da formação Spring Boot da Alura")
	                            .contact(new Contact()
	                                    .name("Bruno Domingos")
	                                    .email("bruninhodt2467@gmail.com")));
	}
}
