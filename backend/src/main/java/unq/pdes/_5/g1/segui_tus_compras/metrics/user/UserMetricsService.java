package unq.pdes._5.g1.segui_tus_compras.metrics.user;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserMetricsService {

    private final MeterRegistry meterRegistry;
    private final ConcurrentHashMap<String, Counter> userPurchaseCounters;
    private final ConcurrentHashMap<String, Counter> userFavoriteCounters;

    public UserMetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.userPurchaseCounters = new ConcurrentHashMap<>();
        this.userFavoriteCounters = new ConcurrentHashMap<>();
    }

    public void incrementUserPurchase(Long userId) {
        Counter counter = userPurchaseCounters.computeIfAbsent(String.valueOf(userId), id ->
            Counter.builder("user_purchases_total")
                    .description("Total de compras por usuario")
                    .tag("user_id", id)
                    .register(meterRegistry)
        );
        counter.increment();
    }

    public void incrementUserFavorite(Long userId) {
        Counter counter = userFavoriteCounters.computeIfAbsent(String.valueOf(userId), id ->
            Counter.builder("user_favorites_total")
                    .description("Total de favoritos por usuario")
                    .tag("user_id", id)
                    .register(meterRegistry)
        );
        counter.increment();
    }

    public void decrementUserFavorite(Long userId) {
        // Para decrementar, podemos usar un counter negativo o manejar la lógica diferente
        // En este caso, vamos a mantener un contador de favoritos removidos
        Counter counter = userFavoriteCounters.computeIfAbsent(String.valueOf(userId) + "_removed", id ->
            Counter.builder("user_favorites_removed_total")
                    .description("Total de favoritos removidos por usuario")
                    .tag("user_id", String.valueOf(userId))
                    .register(meterRegistry)
        );
        counter.increment();
    }

}
