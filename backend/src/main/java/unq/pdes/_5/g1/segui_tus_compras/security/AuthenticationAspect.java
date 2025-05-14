package unq.pdes._5.g1.segui_tus_compras.security;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import unq.pdes._5.g1.segui_tus_compras.exception.InvalidTokenException;
import unq.pdes._5.g1.segui_tus_compras.exception.MissingAuthorizationHeaderException;

@Aspect
@Component
public class AuthenticationAspect {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationAspect(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Around("@within(unq.pdes._5.g1.segui_tus_compras.security.annotation.NeedsAuth) || " +
            "@annotation(unq.pdes._5.g1.segui_tus_compras.security.annotation.NeedsAuth)")
    public Object authenticate(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader(AUTHORIZATION_HEADER);

        if (token == null || token.isEmpty()) {
            throw new MissingAuthorizationHeaderException();
        }

        try {
            Long userId = jwtTokenProvider.validateTokenAndGetUserId(token);
            request.setAttribute("userId", userId);
        } catch (Exception e) {
            throw new InvalidTokenException();
        }

        return joinPoint.proceed();
    }
}
