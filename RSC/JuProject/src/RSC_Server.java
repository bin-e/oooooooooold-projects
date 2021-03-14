
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RSC_Server extends javax.swing.JFrame {

    public RSC_Server() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField1.setFont(new java.awt.Font("굴림", 0, 24)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setEnabled(false);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("IP확인");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("서버 종료");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            getLocal();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
        jTextField1.setText(localadd);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed
    String localadd;

    public void getLocal() throws UnknownHostException {
        InetAddress local = InetAddress.getLocalHost();
        localadd = local.getHostAddress();
    }

    public static void main(String args[]) throws Exception {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RSC_Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RSC_Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RSC_Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RSC_Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RSC_Server().setVisible(true);
            }
        });
        ServerSocket serverSocket = null;
        DataInputStream dInput = null;
        DataOutputStream dOutput = null;

        try {
            System.out.println(getTime() + "Server : Connecting...");
            serverSocket = new ServerSocket(7777);

            while (true) {
                Socket Ssocket = serverSocket.accept();
                System.out.println(getTime() + Ssocket.getInetAddress() + "Server : Receiving...");

                try {

//                System.out.println("getPort() : " + Ssocket.getPort());
//                System.out.println("getLocalPort() : " + Ssocket.getLocalPort());
//                System.out.println(Ssocket.getLocalAddress().toString());

                    //                  dInput = new DataInputStream(Ssocket.getInputStream());
                    //                dOutput = new DataOutputStream(Ssocket.getOutputStream());

                    BufferedReader S_in = new BufferedReader(new InputStreamReader(Ssocket.getInputStream()));
                    PrintWriter S_out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(Ssocket.getOutputStream())), true);


                    switch (S_in.readLine()) {
                        case "Melon_ON":
                            Melon_ON();
                            break;
                        case "Eclipse_ON":
                            Eclipse_ON();
                            break;
                        case "LOL_ON":
                            LOL_ON();
                            break;
                        case "NateOn_ON":
                            NateOn_ON();
                            break;
                        case "GOM_ON":
                            GOM_ON();
                            break;

                        case "Melon_OFF":
                            Melon_OFF();
                            break;
                        case "Eclipse_OFF":
                            Eclipse_OFF();
                            break;
                        case "LOL_OFF":
                            LOL_OFF();
                        case "NateOn_OFF":
                            NateOn_OFF();
                            break;
                        case "GOM_OFF":
                            GOM_OFF();
                            break;
                    }
                    
                    
                    /*   if (dInput.readUTF().equals("Melon")) {
                     MelonRun();
                     }*/


                } catch (Exception e) {
                    System.out.println("Server : Error");
                    e.printStackTrace();
                } finally {
                    Ssocket.close();
                    System.out.println("Server : Done.");
                }

            }
        } catch (Exception e) {
            System.out.println("Server : Error");
            e.printStackTrace();
        }
    }

    static String getTime() {
        SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
        return f.format(new Date());
    }
    static Process pMelon;

    static void Melon_ON() {
        try {
            pMelon = Runtime.getRuntime().exec("C:\\Program Files (x86)\\MelOn Player4\\Melon.exe");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        /*
         // 정확한 경로 지정을 위해 "ProcessBuilder" 를 사용한다.

         ProcessBuilder process = new ProcessBuilder();
         Map<String, String> environment = process.environment();
         process.redirectErrorStream(true);

         // 지정 경로를 설정한다.

         // process.directory(new
         // File("C:\\Program Files (x86)\\MelOn Player4\\"));
         // environment.put("name", "var");

         // 지정경로 부분에서 실행할 파일을 설정한다.

         process.command("C:\\Program Files (x86)\\MelOn Player4\\Melon.exe");

         // process.

         try {
         // 설정한 객체를 실행(start()) 시켜주면 된다.

         Process p = process.start();
         BufferedReader output = new BufferedReader(new InputStreamReader(
         p.getInputStream()));
         String line;
         while ((line = output.readLine()) != null) {
         System.out.println(line);
         }

         // The process should be done now, but wait to be sure.
         try {
         p.waitFor();
         } catch (InterruptedException e) {
         e.printStackTrace();
         }

         } catch (IOException e1) {
         e1.printStackTrace();
         }
         */
    }
    static Process pEclipse;

    static void Eclipse_ON() {
        try {
            pEclipse = Runtime.getRuntime().exec("C:\\eclipse-jee-juno-win32-x86_64\\eclipse\\eclipse.exe");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    static Process pLOL;

    static void LOL_ON() {
        try {
            pLOL = Runtime.getRuntime().exec("C:\\Riot Games\\League of Legends KR\\lol.launcher.admin.exe");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    static Process pNateOn;

    static void NateOn_ON() {
        try {
            pNateOn = Runtime.getRuntime().exec("C:\\Program Files (x86)\\NATEON\\BIN\\NateOnMain.exe");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    static Process pGOM;

    static void GOM_ON() {
        try {
            pGOM = Runtime.getRuntime().exec("C:\\Program Files (x86)\\GRETECH\\GomPlayer\\GOM.exe");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static void Melon_OFF() {
        pMelon.destroy();
    }

    static void Eclipse_OFF() {
        pEclipse.destroy();
    }

    static void LOL_OFF() {
        pLOL.destroy();
    }

    static void NateOn_OFF() {
        pNateOn.destroy();
    }

    static void GOM_OFF() {
        pGOM.destroy();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
