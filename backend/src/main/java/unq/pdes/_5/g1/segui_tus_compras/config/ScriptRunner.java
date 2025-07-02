package unq.pdes._5.g1.segui_tus_compras.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Profile("!test")
public class ScriptRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ScriptRunner.class);

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public ScriptRunner(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) {
        try {
            Long count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM app_users", Long.class);
            if (count != null && count == 0) {
                logger.info("Ejecutando script SQL porque la tabla 'app_users' está vacía...");
                ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                        false, false, "UTF-8", new ClassPathResource("data_init.sql"));
                populator.execute(dataSource);
                logger.info("Script ejecutado con éxito.");
            } else {
                logger.info("Datos ya presentes. No se ejecuta script.");
            }
        } catch (Exception e) {
            logger.error("No se pudo verificar la base o tabla 'users': {}", e.getMessage(), e);
        }
    }
}