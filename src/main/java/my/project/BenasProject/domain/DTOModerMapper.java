package my.project.BenasProject.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.persistence.EntityManager;
import java.util.Collections;

public class DTOModerMapper extends RequestResponseBodyMethodProcessor {

    private static final ModelMapper modelMapper = new ModelMapper();

    private EntityManager entityManager;

    public DTOModerMapper(ObjectMapper objectMapper, EntityManager entityManager){
        super(Collections.singletonList(new MappingJackson2HttpMessageConverter(objectMapper)));
        this.entityManager = entityManager;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
//        return parameter.hasParameterAnnotation(DTO.class);
        return true;
    }
}
