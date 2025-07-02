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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        String queryString = request.getQueryString();
        String uri = request.getRequestURI() + (queryString == null ? "" : "?" + queryString);

        logger.info("Incoming request - {} {} - from {}", request.getMethod(), uri, request.getRemoteAddr());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        long startTime = (Long) request.getAttribute("startTime");
        long duration = System.currentTimeMillis() - startTime;

        String queryString = request.getQueryString();
        String uri = request.getRequestURI() + (queryString == null ? "" : "?" + queryString);

        logger.info("Outgoing response - {} {} - status {} - duration {}ms", request.getMethod(), uri, response.getStatus(), duration);
        if (ex != null) {
            logger.error("Request ended with exception", ex);
        }
    }
}