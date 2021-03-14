import java.awt.*;
import javax.swing.*;

class Myform extends JFrame {

	// swing_Image
	ImageIcon Icn_BackGround = null;
	ImageIcon Icn_BGHealth = null;
	ImageIcon Icn_Health = null;
	ImageIcon Icn_Top = null;

	// swing_main
	JPanel jPanel1 = null;
	JLabel Lbl_MainCenter = null;
	JLabel Lbl_MainBottom = null;

	// swing_game
	JPanel Pnl_Text = null;
	JPanel Pnl_Top = null;
	JPanel Pnl_Health = null;
	JPanel Pnl_Health1 = null;
	JPanel Pnl_Health2 = null;
	JLabel Lbl_Health = null;
	JLabel Lbl_Text = null;

	Myform(String icon, int frame) {
		Icn_BackGround = new ImageIcon(icon);
		Icn_BGHealth = new ImageIcon("C:\\Users\\jangbi1\\Desktop\\WBServer_byOneDevice(20131122)\\WB_Image\\Health-.png");
		Icn_Health = new ImageIcon("C:\\Users\\jangbi1\\Desktop\\WBServer_byOneDevice(20131122)\\WB_Image\\Health.png");
		Icn_Top = new ImageIcon("C:\\Users\\jangbi1\\Desktop\\WBServer_byOneDevice(20131122)\\WB_Image\\BG_Top.png");
		initComponents(frame);
	}

	private void initComponents(int select) {

		if (select == 1) {
			jPanel1 = new JPanel() {
				public void paintComponent(Graphics g) {
					g.drawImage(Icn_BackGround.getImage(), 0, 0, null);
					setOpaque(false);
					super.paintComponent(g);
				}
			};
			Lbl_MainCenter = new JLabel();

			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			setPreferredSize(new Dimension(1130, 780));
			setAlwaysOnTop(true);
			setTitle("Blue & White Flag Game !						 ver 1.0");
			setResizable(false);

			GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
			jPanel1.setLayout(jPanel1Layout);
			jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(
					GroupLayout.Alignment.LEADING)
					.addGap(0, 0, Short.MAX_VALUE));
			jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
					GroupLayout.Alignment.LEADING).addGap(0, 709,
					Short.MAX_VALUE));

			Lbl_MainCenter.setText("Main");
			Lbl_MainCenter.setFont(new Font("Rix∂±∫∫¿Ã M", 0, 20));

			GroupLayout layout = new GroupLayout(getContentPane());
			getContentPane().setLayout(layout);
			layout.setHorizontalGroup(layout
					.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(jPanel1, GroupLayout.DEFAULT_SIZE,
							GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(
							GroupLayout.Alignment.TRAILING,
							layout.createSequentialGroup()
									.addGap(0, 1048, Short.MAX_VALUE)
									.addComponent(Lbl_MainCenter,
											GroupLayout.PREFERRED_SIZE, 72,
											GroupLayout.PREFERRED_SIZE)));
			layout.setVerticalGroup(layout.createParallelGroup(
					GroupLayout.Alignment.LEADING).addGroup(
					layout.createSequentialGroup()
							.addComponent(jPanel1, GroupLayout.DEFAULT_SIZE,
									GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addPreferredGap(
									LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(Lbl_MainCenter,
									GroupLayout.PREFERRED_SIZE, 15,
									GroupLayout.PREFERRED_SIZE)));

			pack();
		} else if (select == 2) {

			Pnl_Text = new JPanel() {
				public void paintComponent(Graphics g) {
					g.drawImage(Icn_BackGround.getImage(), 0, 0, null);
					setOpaque(false);
					super.paintComponent(g);
				}
			};
			Pnl_Top = new JPanel() {
				public void paintComponent(Graphics g) {
					g.drawImage(Icn_Health.getImage(), 0, 0, null);
					setOpaque(false);
					super.paintComponent(g);
				}
			};
			Pnl_Health = new JPanel() {
				public void paintComponent(Graphics g) {
					g.drawImage(Icn_BGHealth.getImage(), 0, 0, null);
					setOpaque(false);
					super.paintComponent(g);
				}
			};
			Pnl_Health1 = new JPanel() {
				public void paintComponent(Graphics g) {
					g.drawImage(Icn_Health.getImage(), 0, 0, null);
					setOpaque(false);
					super.paintComponent(g);
				}
			};
			Pnl_Health2 = new JPanel() {
				public void paintComponent(Graphics g) {
					g.drawImage(Icn_Health.getImage(), 0, 0, null);
					setOpaque(false);
					super.paintComponent(g);
				}
			};
			Lbl_Text = new JLabel();
			Lbl_Health = new JLabel();

			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			setAlwaysOnTop(true);
			setPreferredSize(new Dimension(1130, 780));
			setResizable(false);

			Pnl_Top.setEnabled(false);

			Lbl_Health.setFont(new Font("RixWonderLand M", 0, 50)); // NOI18N
			Lbl_Health.setForeground(new Color(255, 0, 0));
			Lbl_Health.setText("               ");//"#Health"

			GroupLayout Pnl_HealthLayout = new GroupLayout(Pnl_Health);
			Pnl_Health.setLayout(Pnl_HealthLayout);
			Pnl_HealthLayout.setHorizontalGroup(Pnl_HealthLayout
					.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addComponent(Lbl_Health, GroupLayout.DEFAULT_SIZE,
							GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
			Pnl_HealthLayout
					.setVerticalGroup(Pnl_HealthLayout.createParallelGroup(
							GroupLayout.Alignment.LEADING).addGroup(
							Pnl_HealthLayout
									.createSequentialGroup()
									.addComponent(Lbl_Health,
											GroupLayout.DEFAULT_SIZE, 90,
											Short.MAX_VALUE).addContainerGap()));

			GroupLayout Pnl_Health1Layout = new GroupLayout(Pnl_Health1);
			Pnl_Health1.setLayout(Pnl_Health1Layout);
			Pnl_Health1Layout.setHorizontalGroup(Pnl_Health1Layout
					.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(
							0, 100, Short.MAX_VALUE));
			Pnl_Health1Layout.setVerticalGroup(Pnl_Health1Layout
					.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(
							0, 100, Short.MAX_VALUE));

			GroupLayout Pnl_Health2Layout = new GroupLayout(Pnl_Health2);
			Pnl_Health2.setLayout(Pnl_Health2Layout);
			Pnl_Health2Layout.setHorizontalGroup(Pnl_Health2Layout
					.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(
							0, 100, Short.MAX_VALUE));
			Pnl_Health2Layout.setVerticalGroup(Pnl_Health2Layout
					.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(
							0, 100, Short.MAX_VALUE));

			GroupLayout Pnl_TopLayout = new GroupLayout(Pnl_Top);
			Pnl_Top.setLayout(Pnl_TopLayout);
			Pnl_TopLayout.setHorizontalGroup(Pnl_TopLayout.createParallelGroup(
					GroupLayout.Alignment.LEADING).addGroup(
					Pnl_TopLayout
							.createSequentialGroup()
							.addComponent(Pnl_Health,
									GroupLayout.PREFERRED_SIZE,
									GroupLayout.DEFAULT_SIZE,
									GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(
									LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(Pnl_Health1,
									GroupLayout.PREFERRED_SIZE,
									GroupLayout.DEFAULT_SIZE,
									GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(
									LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(Pnl_Health2,
									GroupLayout.PREFERRED_SIZE,
									GroupLayout.DEFAULT_SIZE,
									GroupLayout.PREFERRED_SIZE)
							.addGap(0, 0, Short.MAX_VALUE)));
			Pnl_TopLayout
					.setVerticalGroup(Pnl_TopLayout
							.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addGroup(
									Pnl_TopLayout
											.createSequentialGroup()
											.addGap(0, 0, Short.MAX_VALUE)
											.addGroup(
													Pnl_TopLayout
															.createParallelGroup(
																	GroupLayout.Alignment.LEADING)
															.addComponent(
																	Pnl_Health,
																	GroupLayout.Alignment.TRAILING,
																	GroupLayout.PREFERRED_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.PREFERRED_SIZE)
															.addComponent(
																	Pnl_Health1,
																	GroupLayout.Alignment.TRAILING,
																	GroupLayout.PREFERRED_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.PREFERRED_SIZE)
															.addComponent(
																	Pnl_Health2,
																	GroupLayout.Alignment.TRAILING,
																	GroupLayout.PREFERRED_SIZE,
																	GroupLayout.DEFAULT_SIZE,
																	GroupLayout.PREFERRED_SIZE))));

			Lbl_Text.setFont(new Font("Rix∂±∫∫¿Ã M", 0, 100)); // NOI18N
			Lbl_Text.setForeground(new Color(0, 0, 255));
			Lbl_Text.setHorizontalAlignment(SwingConstants.CENTER);
			Lbl_Text.setText("πÆ ¡¶");

			GroupLayout Pnl_TextLayout = new GroupLayout(Pnl_Text);
			Pnl_Text.setLayout(Pnl_TextLayout);
			Pnl_TextLayout.setHorizontalGroup(Pnl_TextLayout
					.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(
							Pnl_TextLayout
									.createSequentialGroup()
									.addComponent(Pnl_Top,
											GroupLayout.DEFAULT_SIZE,
											GroupLayout.DEFAULT_SIZE,
											Short.MAX_VALUE).addContainerGap())
					.addComponent(Lbl_Text, GroupLayout.DEFAULT_SIZE, 1120,
							Short.MAX_VALUE));
			Pnl_TextLayout.setVerticalGroup(Pnl_TextLayout.createParallelGroup(
					GroupLayout.Alignment.LEADING).addGroup(
					Pnl_TextLayout
							.createSequentialGroup()
							.addComponent(Pnl_Top, GroupLayout.PREFERRED_SIZE,
									GroupLayout.DEFAULT_SIZE,
									GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(
									LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(Lbl_Text, GroupLayout.PREFERRED_SIZE,
									353, GroupLayout.PREFERRED_SIZE)
							.addContainerGap()));

			GroupLayout layout = new GroupLayout(getContentPane());
			getContentPane().setLayout(layout);
			layout.setHorizontalGroup(layout.createParallelGroup(
					GroupLayout.Alignment.LEADING).addComponent(Pnl_Text,
					GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
					Short.MAX_VALUE));
			layout.setVerticalGroup(layout.createParallelGroup(
					GroupLayout.Alignment.LEADING).addGroup(
					layout.createSequentialGroup()
							.addComponent(Pnl_Text, GroupLayout.DEFAULT_SIZE,
									GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addContainerGap()));

			pack();
		}
	}

	public void setV(boolean b) {
		setVisible(b);
	}

	public void setLabel() {

	}
}