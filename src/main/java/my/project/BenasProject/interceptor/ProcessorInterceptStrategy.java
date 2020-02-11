package my.project.BenasProject.interceptor;

import java.sql.SQLException;
import my.project.BenasProject.domain.ContactsInfo;
import my.project.BenasProject.routes.processors.DbProcessor;
import org.apache.camel.CamelContext;
import org.apache.camel.Processor;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.processor.DelegateSyncProcessor;
import org.apache.camel.spi.InterceptStrategy;
import org.mockito.Mockito;
import org.springframework.stereotype.Component;

@Component
public class ProcessorInterceptStrategy implements InterceptStrategy {

    @Override
    public Processor wrapProcessorInInterceptors(CamelContext context,
        ProcessorDefinition<?> definition, Processor target, Processor nextTarget)
        throws SQLException {

        if(target instanceof DelegateSyncProcessor){
            if(((DelegateSyncProcessor) target).getProcessor() instanceof DbProcessor) {
                DbProcessor dbProcessor = (DbProcessor) Mockito.spy(((DelegateSyncProcessor) target).getProcessor());
                Mockito.doNothing().when(dbProcessor).persistData(Mockito.any(ContactsInfo.class));
                return dbProcessor;
            } else {
                return target;
            }
        }
        return target;
    }
}
