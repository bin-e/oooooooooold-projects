import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.Delayed;

import javax.bluetooth.RemoteDevice;
import javax.microedition.io.StreamConnection;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

public class ThreadedEchoHandler extends Thread {

	// Flag Attribute
	String[] flagEx = { "청기올려", "백기 내리지 말고 청기올려", "백기 올리지 말고 청기올려", "청기내려",
			"백기 올리지 말고 청기내려", "백기 내리지 말고 청기내려", "백기올려", "청기 올리지 말고 백기올려",
			"청기 내리지 말고 백기올려", "백기내려", "청기 올리지 말고 백기내려", "청기 내리지 말고 백기내려" };

	// 청기 올려 = 1
	// 청기 내려 = 2
	// 백기 올려 = 3
	// 백기 내려 = 4
	// String[] flagState = {"1", "2", "3", "4"};
	static int HealthCount = 2;

	static int count = 0;

	// swing
	String strLable;
	Myform mf_conneced = new Myform("C:\\Users\\jangbi1\\Desktop\\WBServer_byOneDevice(20131122)\\WB_Image\\BG_base.png", 2);

	WB_server wbServer = null;
	private StreamConnection conn; // client connection
	private InputStream in;
	private OutputStream out = null;

	private volatile boolean isRunning = false;
	private String clientName;

	static int DeviceCount = 0;

	public ThreadedEchoHandler(StreamConnection conn) {
		DeviceCount += 1;
		this.conn = conn;

		clientName = reportDeviceName(conn);
		System.out.println("  Handler spawned for client: " + clientName);

	} // end of ThreadedEchoHandler()

	private String reportDeviceName(StreamConnection conn) {
		String devName;
		try {
			RemoteDevice rd = RemoteDevice.getRemoteDevice(conn);
			devName = rd.getFriendlyName(false); // to reduce connections
		} catch (IOException e) {
			devName = "device ??";
		}
		return devName;
	} // end of reportDeviceName()

	public void run() {
		String ms = null;
		try {
			in = conn.openInputStream();
			out = conn.openOutputStream();

			if (DeviceCount == 1) {
				sendMessage("OK");
				mf_conneced.setV(true);
				wbServer.mf_main.setV(false);
			}

			System.out.println("\t>Server Connecting Device Count = " + ms);

			processMsgs();
			close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (mf_conneced.isShowing()) {
			mf_conneced.setV(false);
		}
		try {
			DeviceCount -= 1;

			System.out.println("  Closing " + clientName + " connection");
			if (conn != null) {
				in.close();
				out.close();
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	Thread tr;
	private void processMsgs() throws AWTException {
		isRunning = true;
		String line = null;

		while (isRunning) {
			if ((line = readData()) == null) {
				System.out.println("readData == null");
				isRunning = false;
			} else {
				if (HealthCount <= 0){
					tr.stop();
					mf_conneced.Lbl_Text.setText("GAME OVER");
				}
				System.out.println(line);
				switch (line) {
				case "1":
					if (strLable == flagEx[0] || strLable == flagEx[1] || strLable == flagEx[2]) {
						mf_conneced.Lbl_Health.setText("정답 !");
						if (tr != null && tr.isAlive()) {
							tr.stop();
						}

						tr = new Thread(new Runnable() {
							public void run() {
								if (HealthCount <= 0){
									tr.stop();
									mf_conneced.Lbl_Text.setText("GAME OVER");
								}
								count++;
								while (true) {
									try {
										int l = (int) (Math.random()*12);
										strLable = flagEx[l];
										mf_conneced.Lbl_Text.setText(strLable);
										Thread.sleep(2000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						});
						tr.start();
					} else {
						mf_conneced.Lbl_Health.setText("오답 !");
						HealthCount--;
						if (HealthCount == 2)
							mf_conneced.Pnl_Health2.setVisible(false);
						else if (HealthCount == 1) {
							mf_conneced.Pnl_Health1.setVisible(false);
						}
						if (tr != null && tr.isAlive()) {
							tr.stop();
						}

						tr = new Thread(new Runnable() {
							public void run() {
								if (HealthCount <= 0){
									tr.stop();
									mf_conneced.Lbl_Text.setText("GAME OVER");
								}
								count++;
								while (true) {
									try {
										int l = (int) (Math.random()*12);
										strLable = flagEx[l];
										mf_conneced.Lbl_Text.setText(strLable);
										Thread.sleep(2000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						});
						tr.start();
					}
					break;

				case "2":
					if (strLable == flagEx[3] || strLable == flagEx[4] || strLable == flagEx[5]) {
						mf_conneced.Lbl_Health.setText("정답 !");
						if (tr != null && tr.isAlive()) {
							tr.stop();
						}
						tr = new Thread(new Runnable() {
							public void run() {
								if (HealthCount <= 0){
									tr.stop();
									mf_conneced.Lbl_Text.setText("GAME OVER");
								}
								count++;
								while (true) {
									try {
										int l = (int) (Math.random()*12);
										strLable = flagEx[l];
										mf_conneced.Lbl_Text.setText(strLable);
										Thread.sleep(2000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						});
						tr.start();
					} else {
						mf_conneced.Lbl_Health.setText("오답 !");
						HealthCount--;
						if (HealthCount == 2)
							mf_conneced.Pnl_Health2.setVisible(false);
						else if (HealthCount == 1) {
							mf_conneced.Pnl_Health1.setVisible(false);
						}
						if (tr != null && tr.isAlive()) {
							tr.stop();
						}
						tr = new Thread(new Runnable() {
							public void run() {
								if (HealthCount <= 0){
									tr.stop();
									mf_conneced.Lbl_Text.setText("GAME OVER");
								}
								count++;
								while (true) {
									try {
										int l = (int) (Math.random()*12);
										strLable = flagEx[l];
										mf_conneced.Lbl_Text.setText(strLable);
										Thread.sleep(2000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						});
						tr.start();
					}
					break;

				case "3":
					if (strLable == flagEx[6] || strLable == flagEx[7] || strLable == flagEx[8]) {
						mf_conneced.Lbl_Health.setText("정답 !");
						if (tr != null && tr.isAlive()) {
							tr.stop();
						}
						tr = new Thread(new Runnable() {
							public void run() {
								if (HealthCount <= 0){
									tr.stop();
									mf_conneced.Lbl_Text.setText("GAME OVER");
								}
								count++;
								while (true) {
									try {
										int l = (int) (Math.random()*12);
										strLable = flagEx[l];
										mf_conneced.Lbl_Text.setText(strLable);
										Thread.sleep(2000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						});
						tr.start();
					} else {
						mf_conneced.Lbl_Health.setText("오답 !");
						HealthCount--;
						if (HealthCount == 2)
							mf_conneced.Pnl_Health2.setVisible(false);
						else if (HealthCount == 1) {
							mf_conneced.Pnl_Health1.setVisible(false);
							// mf_conneced.setV(false);
						}
						if (tr != null && tr.isAlive()) {
							tr.stop();
						}

						tr = new Thread(new Runnable() {
							public void run() {
								if (HealthCount <= 0){
									tr.stop();
									mf_conneced.Lbl_Text.setText("GAME OVER");
								}
								count++;
								while (true) {
									try {
										int l = (int) (Math.random()*12);
										strLable = flagEx[l];
										mf_conneced.Lbl_Text.setText(strLable);
										Thread.sleep(2000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						});
						tr.start();
					}
					break;

				case "4":
					if (strLable == flagEx[9] || strLable == flagEx[10]
							|| strLable == flagEx[11]) {
						mf_conneced.Lbl_Health.setText("정답 !");
						if (tr != null && tr.isAlive()) {
							tr.stop();
						}

						tr = new Thread(new Runnable() {
							public void run() {
								if (HealthCount <= 0){
									tr.stop();
									mf_conneced.Lbl_Text.setText("GAME OVER");
								}
								count++;
								while (true) {
									try {
										int l = (int) (Math.random()*12);
										strLable = flagEx[l];
										mf_conneced.Lbl_Text.setText(strLable);
										Thread.sleep(2000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						});
						tr.start();
					} else {
						mf_conneced.Lbl_Health.setText("오답 !");
						HealthCount--;
						if (HealthCount == 2)
							mf_conneced.Pnl_Health2.setVisible(false);
						else if (HealthCount == 1) {
							mf_conneced.Pnl_Health1.setVisible(false);
							// mf_conneced.setV(false);
						}
						if (tr != null && tr.isAlive()) {
							tr.stop();
						}

						tr = new Thread(new Runnable() {
							public void run() {
								if (HealthCount <= 0){
									tr.stop();
									mf_conneced.Lbl_Text.setText("GAME OVER");
								}
								count++;
								while (true) {
									try {
										int l = (int) (Math.random()*12);
										strLable = flagEx[l];
										mf_conneced.Lbl_Text.setText(strLable);
										Thread.sleep(2000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						});
						tr.start();
					}
					break;

				case "5":
					HealthCount = 2;
					count = 0;
					mf_conneced.Pnl_Health1.setVisible(true);
					mf_conneced.Pnl_Health2.setVisible(true);
					if (tr != null && tr.isAlive()) {
						tr.stop();
					}

					tr = new Thread(new Runnable() {
						public void run() {
							if (HealthCount <= 0){
								tr.stop();
								mf_conneced.Lbl_Text.setText("GAME OVER");
							}
							while (true) {
								try {
									int l = (int) (Math.random()*12);
									strLable = flagEx[l];
									mf_conneced.Lbl_Text.setText(strLable);
									Thread.sleep(2000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					});
					tr.start();
					break;
				default:
					System.out.println("default MSG = " + line);
				}
			}
		}
	} // end of processMsgs()

	public void closeDown() {
		isRunning = false;
	}

	private String readData() {
		String Str_read = null;
		byte[] data = new byte[1];
		try {
			int len = in.read(data); // get the message length

			if (len <= 0) {
				System.out.println(clientName + ": Message Length Error");
				return null;
			}

			/*
			 * 
			 * data = new byte[len]; len = 0;
			 */

			// read the message, perhaps requiring several read() calls
			/*
			 * while (len != data.length) { int ch = in.read(data, len,
			 * data.length - len); if (ch == -1) { System.out.println(clientName
			 * + ": Message Read Error"); return null; } len += ch; }
			 */
		} catch (IOException e) {
			System.out.println(clientName + " readData(): " + e);
			return null;
		}
		// System.out.println("readData: " + new String(data));
		return new String(data);// .trim(); // convert byte[] to trimmed String
	} // end of readData()

	private boolean sendMessage(String msg) {
		try {
			out.write(msg.length());
			out.write(msg.getBytes());
			out.flush();
			return true;
		} catch (Exception e) {
			System.out.println(clientName + " sendMessage(): " + e);
			return false;
		}
	} // end of sendMessage()

} // end of ThreadedEchoHandler class