import static javax.swing.JOptionPane.showMessageDialog;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Graphics screen for the register menu
 * @author Osama Yousef
 * @author So Yoon Kim
 */
public class Register extends javax.swing.JFrame {

	/**
	 * Creates new form Register
	 */
	public Register() {
		Main.setScreen(2);
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {

		cancelButton = new javax.swing.JButton();
		confirmButton = new javax.swing.JButton();
		rePasswordLabel = new javax.swing.JLabel();
		usernameLabel = new javax.swing.JLabel();
		passwordLabel = new javax.swing.JLabel();
		userNameField = new javax.swing.JTextField();
		clearFormButton = new javax.swing.JButton();
		passwordField = new javax.swing.JPasswordField();
		rePasswordField = new javax.swing.JPasswordField();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setMaximumSize(new java.awt.Dimension(1200, 1200));
		setMinimumSize(new java.awt.Dimension(740, 400));
		setResizable(false);

		cancelButton.setText("Cancel");
		cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				cancelButtonMouseClicked(evt);
			}
		});

		confirmButton.setText("Confirm");
		confirmButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					confirmButtonActionPerformed(evt);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		rePasswordLabel.setText("Reenter Password");

		usernameLabel.setLabelFor(usernameLabel);
		usernameLabel.setText("User Name");

		passwordLabel.setText("Password");

		clearFormButton.setText("Clear");
		clearFormButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				clearFormButtonMouseClicked(evt);
			}
		});
		clearFormButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				clearFormButtonActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(183, 183, 183)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
						.addComponent(passwordLabel, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(rePasswordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 117,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(usernameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
				.addGap(18, 18, 18)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
						.addComponent(confirmButton, javax.swing.GroupLayout.Alignment.TRAILING,
								javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
						.addComponent(cancelButton, javax.swing.GroupLayout.Alignment.TRAILING,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addComponent(clearFormButton, javax.swing.GroupLayout.Alignment.TRAILING,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addComponent(passwordField).addComponent(rePasswordField)
						.addComponent(userNameField, javax.swing.GroupLayout.Alignment.TRAILING))
				.addContainerGap(317, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(129, 129, 129)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(usernameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(userNameField, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(passwordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(
										layout.createSequentialGroup().addGap(12, 12, 12).addComponent(rePasswordLabel))
								.addGroup(layout.createSequentialGroup().addGap(18, 18, 18).addComponent(
										rePasswordField, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 55,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(clearFormButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(cancelButton,
								javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(75, Short.MAX_VALUE)));

		pack();
		setLocationRelativeTo(null);
	}

	/**
	 * Functions performed when the confirmation button is pressed
	 * Checks if username and password are valid
	 * @param evt Mouse event when button is pressed
	 * @throws IOException if input cannot be read
	 */
	private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
		try {
			String username = userNameField.getText();
			String password = passwordField.getText();
			String rePassword = rePasswordField.getText();

			// Check if username field is empty
			if (username.isEmpty()) {
				showMessageDialog(null, "username field cannot be empty");
				return;
			}
			// Check if password field is empty
			else if (password.isEmpty()) {
				showMessageDialog(null, "password field cannot be empty");
				return;
			}
			// Check if repassword field is empty
			else if (rePassword.isEmpty()) {
				showMessageDialog(null, "second password field cannot be empty");
				return;
			} else {
				// check if username is previously registered.
				for (user user : Main.users) {
					if (user.getUserName().equals(username)) {
						showMessageDialog(null,
								"Current username is already registered. Please type different username");
						return;
					}
				}
				// Check if password matches 2nd password
				if (!password.equals(rePassword)) {
					showMessageDialog(null, "Passwords do not match. Please check your passwords");
					return;
				}
				if (password.length() < 7) {
					showMessageDialog(null, "Password length must be greater than 6");
					return;
				}
			}

			// Add user to Main.users
			user newUser = new user(username, password, false, new int[0], new ArrayList<>());
			Main.users.add(newUser);

			// Save it to user Json.
			Main.saveJSON(); // will be added soon.
			showMessageDialog(null, "Registered Successfully. Please login");

		} catch (IOException iex) {
			System.out.print(iex.toString());
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

		// TODO add your handling code here:
	}

	/**
	 * Functions performed when clear form button is pressed
	 * Clears the username, password, and re-enter password text fields
	 * @param evt Mouse event when button is pressed
	 */
	private void clearFormButtonActionPerformed(java.awt.event.ActionEvent evt) {
		userNameField.setText("");
		passwordField.setText("");
		rePasswordField.setText("");
	}

	/**
	 * Functions performed when cancel button is pressed
	 * Closes this window and returns to log in menu
	 * @param evt Mouse event when button is pressed
	 */
	private void cancelButtonMouseClicked(java.awt.event.MouseEvent evt) {
		userNameField.setText("");
		passwordField.setText("");
		rePasswordField.setText("");
		Signin signinFrame = new Signin();
		signinFrame.setVisible(true);
		dispose();
	}

	/**
	 * Functions performed when clear form button is pressed
	 * Clears the username, password, and re-enter password text fields
	 * @param evt Mouse event when button is pressed
	 */
	private void clearFormButtonMouseClicked(java.awt.event.MouseEvent evt) {
		userNameField.setText("");
		passwordField.setText("");
		rePasswordField.setText("");
	}

	/**
	 * Method to run this class individually
	 * Only would run for quick testing, no exterior functions available
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Register().setVisible(true);

			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton cancelButton;
	private javax.swing.JButton clearFormButton;
	private javax.swing.JButton confirmButton;
	private javax.swing.JPasswordField passwordField;
	private javax.swing.JLabel passwordLabel;
	private javax.swing.JPasswordField rePasswordField;
	private javax.swing.JLabel rePasswordLabel;
	private javax.swing.JTextField userNameField;
	private javax.swing.JLabel usernameLabel;
	// End of variables declaration//GEN-END:variables
}