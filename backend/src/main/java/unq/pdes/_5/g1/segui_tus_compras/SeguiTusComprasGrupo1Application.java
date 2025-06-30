package unq.pdes._5.g1.segui_tus_compras;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("unq.pdes._5.g1.segui_tus_compras.model")
public class SeguiTusComprasGrupo1Application {

	public static void main(String[] args) {
		SpringApplication.run(SeguiTusComprasGrupo1Application.class, args);
	}
}
