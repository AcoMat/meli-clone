package unq.pdes._5.g1.segui_tus_compras.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import unq.pdes._5.g1.segui_tus_compras.interceptor.RequestLoggingInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RequestLoggingInterceptor interceptor;

    public WebConfig(RequestLoggingInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
    }
}
