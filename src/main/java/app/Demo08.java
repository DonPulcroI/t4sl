package app;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

import model.Usuario;

public class Demo08 {
	// Listado de usuarios según un tipo
	public static void main(String[] args) {
		String usuario = JOptionPane.showInputDialog("Ingrese su usuario:");
		String clave = JOptionPane.showInputDialog("Ingrese su clave:");
		
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("jpa_sesion01");
		EntityManager manager = fabrica.createEntityManager();
		
		// Select * from tb_xx where usr_usua = ? and cla_usua = ?
		String sql = "select u from Usuario u where"
				+ " u.usr_usua = :xusr and u.cla_usua = :xcla"; // jpa
		try {
			Usuario u = manager.createQuery(sql, Usuario.class).
			setParameter("xusr", usuario).
			setParameter("xcla", clave).
			getSingleResult();
			
			System.out.println("Código....: " + u.getCod_usua());
			System.out.println("Nombre....: " + u.getNom_usua() + " " + u.getApe_usua());
			System.out.println("Tipo......: " + u.getIdtipo() + " " + u.getObjTipo().getDescripcion());
			System.out.println("............................");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Usuario o clave incorrecta");
		}
		
		
		manager.close();
	}
}
