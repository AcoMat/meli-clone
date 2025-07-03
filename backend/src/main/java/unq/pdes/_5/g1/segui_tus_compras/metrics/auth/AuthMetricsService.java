package unq.pdes._5.g1.segui_tus_compras.metrics.auth;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class AuthMetricsService {

    private final Counter userRegistrationCounter;
    private final Counter userLoginFailedCounter;

    public AuthMetricsService(MeterRegistry meterRegistry) {
        this.userRegistrationCounter = Counter.builder("user_registration_total")
                .description("Total de usuarios registrados exitosamente")
                .tag("status", "success")
                .register(meterRegistry);
        this.userLoginFailedCounter = Counter.builder("user_login_failed_total")
                .description("Total de intentos de login fallidos")
                .tag("status", "error")
                .register(meterRegistry);
    }

    public void incrementUserRegistration() {
        userRegistrationCounter.increment();
    }

    public void incrementFailedLogin() {
        userLoginFailedCounter.increment();
    }
}
