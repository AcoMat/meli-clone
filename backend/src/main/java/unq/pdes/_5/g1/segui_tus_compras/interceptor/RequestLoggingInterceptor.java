package unq.pdes._5.g1.segui_tus_compras.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingInterceptor.class);
    private static final ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        startTimeThreadLocal.set(System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        try {
            Long startTime = startTimeThreadLocal.get();
            long duration = startTime != null ? System.currentTimeMillis() - startTime : 0;

            String queryString = request.getQueryString();
            String uri = request.getRequestURI() + (queryString == null ? "" : "?" + queryString);

            logger.info("Request completed - {} {} - from {} - status {} - duration {}ms",
                       request.getMethod(), uri, request.getRemoteAddr(), response.getStatus(), duration);

            if (ex != null) {
                logger.error("Request ended with exception", ex);
            }
        } finally {
            startTimeThreadLocal.remove();
        }
    }
}