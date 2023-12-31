package app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Categorias;
import model.Productos;
import model.Proveedor;
import model.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

public class FrmManteProd extends JFrame {

	private JPanel contentPane;

	private JTextArea txtSalida;
	private JTextField txtCodigo;
	private JComboBox<String> cboCategorias;
	private JComboBox<String> cboProveedores;
	private JTextField txtDescripcion;
	private JTextField txtStock;
	private JTextField txtPrecio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmManteProd frame = new FrmManteProd();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmManteProd() {
		setTitle("Mantenimiento de Productos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registrar();
			}
		});
		btnRegistrar.setBounds(324, 29, 89, 23);
		contentPane.add(btnRegistrar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 171, 414, 143);
		contentPane.add(scrollPane);

		txtSalida = new JTextArea();
		scrollPane.setViewportView(txtSalida);

		JButton btnListado = new JButton("Listado");
		btnListado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listado();
			}
		});
		btnListado.setBounds(177, 322, 89, 23);
		contentPane.add(btnListado);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(122, 11, 86, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);

		JLabel lblCodigo = new JLabel("Id. Producto :");
		lblCodigo.setBounds(10, 14, 102, 14);
		contentPane.add(lblCodigo);

		cboCategorias = new JComboBox();
		cboCategorias.setBounds(122, 70, 144, 22);
		contentPane.add(cboCategorias);

		JLabel lblCategora = new JLabel("Categor\u00EDa");
		lblCategora.setBounds(10, 74, 102, 14);
		contentPane.add(lblCategora);

		JLabel lblNomProducto = new JLabel("Nom. Producto :");
		lblNomProducto.setBounds(10, 45, 102, 14);
		contentPane.add(lblNomProducto);

		txtDescripcion = new JTextField();
		txtDescripcion.setColumns(10);
		txtDescripcion.setBounds(122, 42, 144, 20);
		contentPane.add(txtDescripcion);

		JLabel lblStock = new JLabel("Stock:");
		lblStock.setBounds(10, 106, 102, 14);
		contentPane.add(lblStock);

		txtStock = new JTextField();
		txtStock.setColumns(10);
		txtStock.setBounds(122, 103, 77, 20);
		contentPane.add(txtStock);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(10, 134, 102, 14);
		contentPane.add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setColumns(10);
		txtPrecio.setBounds(122, 131, 77, 20);
		contentPane.add(txtPrecio);

		JLabel lblProveedores = new JLabel("Proveedor:");
		lblProveedores.setBounds(230, 106, 102, 14);
		contentPane.add(lblProveedores);

		cboProveedores = new JComboBox();
		cboProveedores.setBounds(300, 104, 130, 22);
		contentPane.add(cboProveedores);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnBuscar.setBounds(324, 63, 89, 23);
		contentPane.add(btnBuscar);

		llenaCombo();
	}

	void llenaCombo() {
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("jpa_sesion01");
		EntityManager manager = fabrica.createEntityManager();
		
		// select * from tb_xxxx
		String sql = "select c from Categorias c"; // jpa
		List<Categorias> lstCategorias = manager.createQuery(sql, Categorias.class).getResultList();
		
		cboCategorias.addItem("Elija una categoria");
		for (Categorias c : lstCategorias) {
			cboCategorias.addItem(c.getDescripcion());
		}
		
		sql = "select a from Proveedor a"; // jpa
		List<Proveedor> lstProveedor = manager.createQuery(sql, Proveedor.class).getResultList();
		
		cboProveedores.addItem("Elija un proveedor");
		for (Proveedor a : lstProveedor) {
			cboProveedores.addItem(a.getNombre_rs());
		}
		
		manager.close();
	}

	void registrar() {
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("jpa_sesion01");
		EntityManager manager = fabrica.createEntityManager();
		
		String id_prod = txtCodigo.getText();
		String des_prod = txtDescripcion.getText(); 
		int stk_prod = Integer.parseInt(txtStock.getText());
	    double pre_prod = Double.parseDouble(txtPrecio.getText());
		int idcategoria = cboCategorias.getSelectedIndex();
		int est_prod = 1; // 1 -> true 0 -> false;
		int idproveedor = cboProveedores.getSelectedIndex();
		
		Productos p = new Productos();
		p.setId_prod(id_prod);
		p.setDes_prod(des_prod);
		p.setStk_prod(stk_prod);
		p.setPre_prod(pre_prod);
		p.setIdcategoria(idcategoria);
		p.setEst_prod(est_prod);
		p.setIdproveedor(idproveedor);
		
		

		try {
			manager.getTransaction().begin();
			manager.persist(p);
			manager.getTransaction().commit();
			aviso("Registro ok");
		} catch (Exception e) {
			aviso("Error: Al grabar, verificar datos");
		}
		
		manager.close();
	}

	void listado() {
		EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("jpa_sesion01");
		EntityManager manager = fabrica.createEntityManager();
		
		// select * from tb_xxxx
		String sql = "select u from Productos u"; // jpa
		List<Productos> lstProductos = manager.createQuery(sql, Productos.class).getResultList();
		
		// recorre el listado y lo muestra
		for (Productos u : lstProductos) {
			imprimir("Código...........: " + u.getId_prod());
			imprimir("descripcion......: " + u.getDes_prod());
			imprimir("Stock............: " + u.getStk_prod() +"\t\t"+ "       Categoria: " + u.getObjCategoria().getDescripcion());
			imprimir("precio...........: " + u.getPre_prod());
			imprimir("estado producto..: " + u.getEst_prod() +"\t"+ "        Proveedor: " + u.getObjProveedor().getNombre_rs());
			imprimir("....................."+"\n");
		}
		
		manager.close();
	}
	
	void aviso(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, "Aviso del sistema", JOptionPane.INFORMATION_MESSAGE);
	}
	
	void imprimir(String algo) {
		txtSalida.append(algo + "\n");
	}
	
	void buscar() {
		
	}
}
