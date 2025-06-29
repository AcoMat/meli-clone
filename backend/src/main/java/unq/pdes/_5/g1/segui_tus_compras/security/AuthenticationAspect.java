package unq.pdes._5.g1.segui_tus_compras.security;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import unq.pdes._5.g1.segui_tus_compras.exception.auth.InvalidTokenException;
import unq.pdes._5.g1.segui_tus_compras.exception.auth.MissingAuthorizationHeaderException;
import unq.pdes._5.g1.segui_tus_compras.exception.auth.UnauthorizedAccessException;

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

        Long userId = jwtTokenProvider.validateTokenAndGetUserId(token);
        if (userId == null) {
            throw new InvalidTokenException();
        }
        request.setAttribute("userId", userId);

        return joinPoint.proceed();
    }

    @Around("@within(unq.pdes._5.g1.segui_tus_compras.security.annotation.AdminOnly) || " +
            "@annotation(unq.pdes._5.g1.segui_tus_compras.security.annotation.AdminOnly)")
    public Object authenticateAdmin(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader(AUTHORIZATION_HEADER);

        if (token == null || token.isEmpty()) {
            throw new MissingAuthorizationHeaderException();
        }

        Long userId = jwtTokenProvider.validateAdminTokenAndGetUserId(token);
        if (userId == null) {
            throw new UnauthorizedAccessException();
        }
        request.setAttribute("userId", userId);

        return joinPoint.proceed();
    }

}
