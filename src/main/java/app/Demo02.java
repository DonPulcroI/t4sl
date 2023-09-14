package app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Usuario;

public class Demo02 {
	// actualizar los datos de un Usuario existente en la tabla
	public static void main(String[] args) {
		// llamar a la conexiom
		EntityManagerFactory fabrica = 
				Persistence.createEntityManagerFactory("jpa_sesion01");
	    // crear un manejador de las entidades
		EntityManager manager = fabrica.createEntityManager();
		
		Usuario u = new Usuario();
		u.setCod_usua(3);
		u.setNom_usua("Zoila");
		u.setApe_usua("Toro");
		u.setUsr_usua("U003@gmail.com");
		u.setCla_usua("212121");
		u.setFna_usua("2008/03/07");
		u.setIdtipo(1);
		u.setEst_usua(1);

		//si queremos registrar, actualizar o eliminar -> transa..
		try {
			manager.getTransaction().begin();
			//update
			manager.merge(u);
			manager.getTransaction().commit();
			System.out.println("Actualización ok");
		} catch (Exception e) {
			System.out.println("Error: " + e.getCause().getMessage());
		}
		manager.close();
	}
}
