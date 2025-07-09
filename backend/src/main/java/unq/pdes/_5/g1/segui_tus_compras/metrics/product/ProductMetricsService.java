package unq.pdes._5.g1.segui_tus_compras.metrics.product;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProductMetricsService {

    private final MeterRegistry meterRegistry;
    private final ConcurrentHashMap<String, Counter> productViewCounters;
    private final ConcurrentHashMap<String, Counter> productCommentCounters;
    private final ConcurrentHashMap<String, Counter> productReviewCounters;
    private final ConcurrentHashMap<String, Counter> productPurchaseCounters;
    private final ConcurrentHashMap<String, Counter> productFavoriteCounters;

    public ProductMetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.productViewCounters = new ConcurrentHashMap<>();
        this.productCommentCounters = new ConcurrentHashMap<>();
        this.productReviewCounters = new ConcurrentHashMap<>();
        this.productPurchaseCounters = new ConcurrentHashMap<>();
        this.productFavoriteCounters = new ConcurrentHashMap<>();
    }

    public void incrementProductView(String productId) {
        Counter counter = productViewCounters.computeIfAbsent(productId, id ->
            Counter.builder("product_views_total")
                    .description("Total de vistas por producto")
                    .tag("product_id", id)
                    .register(meterRegistry)
        );
        counter.increment();
    }

    public void incrementCommentByProduct(String productId) {
        Counter counter = productCommentCounters.computeIfAbsent(productId, id ->
            Counter.builder("product_comments_total")
                    .description("Total de comentarios por producto")
                    .tag("product_id", id)
                    .register(meterRegistry)
        );
        counter.increment();
    }

    public void incrementReviewByProduct(String productId) {
        Counter counter = productReviewCounters.computeIfAbsent(productId, id ->
            Counter.builder("product_reviews_total")
                    .description("Total de reviews por producto")
                    .tag("product_id", id)
                    .register(meterRegistry)
        );
        counter.increment();
    }


    public void incrementProductPurchase(String productId) {
        Counter counter = productPurchaseCounters.computeIfAbsent(productId, id ->
                Counter.builder("product_purchases_total")
                        .description("Total de compras por producto")
                        .tag("product_id", id)
                        .register(meterRegistry)
        );
        counter.increment();
    }

    public void incrementProductFavorite(String productId) {
        Counter counter = productFavoriteCounters.computeIfAbsent(productId, id ->
                Counter.builder("product_favorites_total")
                        .description("Total de favoritos por producto")
                        .tag("product_id", id)
                        .register(meterRegistry)
        );
        counter.increment();
    }

    public void decrementProductFavorite(String productId) {
        // Similar al anterior, mantenemos un contador de favoritos removidos
        Counter counter = productFavoriteCounters.computeIfAbsent(productId + "_removed", id ->
                Counter.builder("product_favorites_removed_total")
                        .description("Total de favoritos removidos por producto")
                        .tag("product_id", productId)
                        .register(meterRegistry)
        );
        counter.increment();
    }

}
