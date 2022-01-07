package todo1.com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("todo1.com.controller")).paths(PathSelectors.any()).build();
	}

	@SuppressWarnings("unused")
	private ApiInfo getApiInfo() {
		Contact contact = new Contact("DSII", "http://stalin_ramirez.com", "none");
		return new ApiInfoBuilder().title("EXAMPLES SERVICES").description("Example Api Definition").version("1.0.0")
				.license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0").contact(contact)
				.build();
	}
}