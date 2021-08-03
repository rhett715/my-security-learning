package org.rhett.mysecurity.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Author Rhett
 * @Date 2021/6/3
 * @Description
 * swagger2文档配置
 */
@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public Docket apiConfig() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("webApi")
                .apiInfo(apiInfo())
                .select()
                //扫描所有有注解的api
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //扫描所有api
                //.apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Security学习项目API文档")
                .description("项目各个模块接口调用方式")
                .version("1.0.0")
                .contact(new Contact("rhett", "https://github.com/rhett715", "zrui312715@gmail.com"))
                .build();
    }
}
