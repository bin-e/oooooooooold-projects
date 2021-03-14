import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Rsa_server extends javax.swing.JFrame {

    public static final int delayTime = 200;
    String localadd;

    public Rsa_server() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        textField_server = new javax.swing.JTextField();
        button_ip = new javax.swing.JButton();
        button_exit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textField_server.setFont(new java.awt.Font("굴림", 0, 24)); // NOI18N
        textField_server.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textField_server.setEnabled(false);

        button_ip.setText("IP확인");
        button_ip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_ipActionPerformed(evt);
            }
        });

        button_exit.setText("서버 종료");
        button_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_exitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textField_server, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(button_ip, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_exit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(textField_server, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button_exit, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(button_ip, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
    }// </editor-fold>                        

    static String getTime() {
        SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
        return f.format(new Date());
    }

    private void button_ipActionPerformed(java.awt.event.ActionEvent evt) {                                          
        try {
            getLocal();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
        textField_server.setText(localadd);
    }                                         

    public void getLocal() throws UnknownHostException {
        InetAddress local = InetAddress.getLocalHost();
        localadd = local.getHostAddress();
    }

    private void button_exitActionPerformed(java.awt.event.ActionEvent evt) {                                            
        System.exit(0);
    }                                           

    public static void main(String args[]) throws Exception {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Rsa_server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Rsa_server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Rsa_server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Rsa_server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Rsa_server().setVisible(true);
            }
        });
        ServerSocket serverSocket = null;
        PrintWriter S_out;
        BufferedReader S_in;

        try {
            System.out.println(getTime() + "Server : Connecting...");
            serverSocket = new ServerSocket(7777);

            while (true) {
                Socket Ssocket = serverSocket.accept();
                System.out.println(getTime() + Ssocket.getInetAddress() + " Server : Receiving...");

                try {
                    S_in = new BufferedReader(new InputStreamReader(Ssocket.getInputStream()));
                    S_out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(Ssocket.getOutputStream())), true);

                    String input = S_in.readLine();
                    
                    for (int i = 0; i < input.length(); i++) {
                        if (input.charAt(i) == ':') {
                            String[] action = new String[2];
                            action = input.split(":");
                            MOUSE_MOVE(Integer.parseInt(action[0]), Integer.parseInt(action[1]));
                        }
                    }
                    switch (input) {

                        /* Connecting */
                        case "CONNECT":
                            System.out.println("\t\t -- Is Connect OK ! --");
                            S_out.println(1);
                            break;
                            
                        /* MOUSE */
                        case "MOSE_PRESS":
                            MOSE_PRESS();
                            break;

                        /* PPT */
                        case "PPT_ON":
                            PPT_ON();
                            break;
                        case "PPT_SHOW_START":
                            PPT_SHOW_START();
                            break;
                        case "PPT_SHOW_HOME":
                            PPT_SHOW_HOME();
                            break;
                        case "PPT_SHOW_BACK":
                            PPT_SHOW_BACK();
                            break;
                        case "PPT_SHOW_FRONT":
                            PPT_SHOW_FRONT();
                            break;
                        case "PPT_SHOW_END":
                            PPT_SHOW_END();
                            break;
                        case "PPT_FILE_OPEN":
                            PPT_FILE_OPEN();
                            break;
                        case "PPT_SHOW_EXIT":
                            PPT_SHOW_EXIT();
                            break;
                        case "PPT_OFF":
                            PPT_OFF();
                            break;

                        /*  Melon   */
                        case "MELON_ON":
                            MELON_ON();
                            break;
                        case "MELON_PREV":
                            MELON_PREV();
                            break;
                        case "MELON_BACK":
                            MELON_BACK();
                            break;
                        case "MELON_PLAY":
                            MELON_PLAY();
                            break;
                        case "MELON_FRONT":
                            MELON_FRONT();
                            break;
                        case "MELON_NEXT":
                            MELON_NEXT();
                            break;
                        case "MELON_MUTE":
                            MELON_MUTE();
                            break;
                        case "MELON_OFF":
                            MELON_OFF();
                            break;

                        case "MELON_VLUME_UP":
                            MELON_VLUME_UP();
                            break;
                        case "MELON_VLUME_DOWN":
                            MELON_VLUME_DOWN();
                            break;

                        /* Gom */
                        case "GOM_ON":
                            GOM_ON();
                            break;
                        case "GOM_PREV":
                            GOM_PREV();
                            break;
                        case "GOM_BBACK":
                            GOM_BBACK();
                            break;
                        case "GOM_BACK":
                            GOM_BACK();
                            break;
                        case "GOM_PLAY":
                            GOM_PLAY();
                            break;
                        case "GOM_FRONT":
                            GOM_FRONT();
                            break;
                        case "GOM_FFRONT":
                            GOM_FFRONT();
                            break;
                        case "GOM_NEXT":
                            GOM_NEXT();
                            break;
                        case "GOM_MUTE":
                            GOM_MUTE();
                            break;
                        case "GOM_VLUME_UP":
                            GOM_VLUME_UP();
                            break;
                        case "GOM_VLUME_DOWN":
                            GOM_VLUME_DOWN();
                            break;
                        case "GOM_PLAYLIST":
                            GOM_PLAYLIST();
                            break;
                        case "GOM_FILE_OPEN":
                            GOM_FILE_OPEN();
                            break;
                        case "GOM_OFF":
                            GOM_OFF();
                            break;
                        case "GOM_ESC":
                            GOM_ESC();
                            break;

                        /* Etc */
                        case "KAKAO_ON":
                            KAKAO_ON();
                            break;
                        case "KAKAO_OFF":
                            KAKAO_OFF();
                            break;
                        case "NATE_ON":
                            NATE_ON();
                            break;
                        case "NATE_OFF":
                            NATE_OFF();
                            break;
                        case "TEAMVIEW_ON":
                            TEAMVIEW_ON();
                            break;
                        case "TEAMVIEW_OFF":
                            TEAMVIEW_OFF();
                            break;
                    }
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

    /* MOUSE */
    static void MOUSE_MOVE(int mX, int mY) throws Exception {
        Robot rb = new Robot();
        Point p = MouseInfo.getPointerInfo().getLocation();
    
        System.out.println(p.x + mX + ", " + p.y + mY);
        rb.mouseMove((mX), (mY));

    }

    static void MOSE_PRESS() throws Exception {
        Robot rb = new Robot();
        rb.mousePress(InputEvent.BUTTON1_MASK);
        rb.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    /* Power Point */
    static Process pcs_Ppt;
    static final String PPT_Location = "C:\\Program Files (x86)\\Microsoft Office\\Office14\\POWERPNT.EXE";

    static void PPT_FILE_OPEN() throws Exception { // Ctrl + O
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_O);
        rb.keyRelease(KeyEvent.VK_CONTROL);
        rb.delay(delayTime);
    }

    static void PPT_ON() throws Exception {
        pcs_Ppt = Runtime.getRuntime().exec(PPT_Location);
    }

    static void PPT_OFF() throws Exception {
        pcs_Ppt.destroy();
    }

    static void PPT_SHOW_START() throws Exception { // F5
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_F5);
        rb.delay(delayTime);
    }

    static void PPT_SHOW_EXIT() throws Exception { // ESC
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_ESCAPE);
        rb.delay(delayTime);
    }

    static void PPT_SHOW_HOME() throws Exception { // Home
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_HOME);
        rb.delay(delayTime);
    }

    static void PPT_SHOW_BACK() throws Exception { // P, Page Up
        Robot rb = new Robot();
//        rb.keyPress(KeyEvent.VK_PAGE_UP);
        rb.keyPress(KeyEvent.VK_LEFT);
        rb.delay(delayTime);
    }

    static void PPT_SHOW_FRONT() throws Exception { // N, Enter, Page Down, Spacebar
        Robot rb = new Robot();
//        rb.keyPress(KeyEvent.VK_PAGE_DOWN);
        rb.keyPress(KeyEvent.VK_RIGHT);
        rb.delay(delayTime);
    }

    static void PPT_SHOW_END() throws Exception { // End
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_END);
        rb.delay(delayTime);
    }

    /* Melon */
    static Process psc_Melon;
    static final String MELON_Location = "C:\\Program Files (x86)\\MelOn Player4\\Melon.exe";

    static void MELON_ON() throws Exception {
        psc_Melon = Runtime.getRuntime().exec(MELON_Location);
    }

    static void MELON_OFF() throws Exception {
        psc_Melon.destroy();
    }

    static void MELON_PREV() throws Exception { // Ctrl + Left
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_LEFT);
        rb.keyRelease(KeyEvent.VK_CONTROL);
        rb.delay(delayTime);
    }

    static void MELON_BACK() throws Exception { // Alt + Left
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_ALT);
        rb.keyPress(KeyEvent.VK_LEFT);
        rb.keyRelease(KeyEvent.VK_ALT);
        rb.delay(delayTime);
    }

    static void MELON_PLAY() throws Exception { // Space
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_SPACE);
        rb.delay(delayTime);
    }

    static void MELON_FRONT() throws Exception { // Alt + Right
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_ALT);
        rb.keyPress(KeyEvent.VK_RIGHT);
        rb.keyRelease(KeyEvent.VK_ALT);
        rb.delay(delayTime);
    }

    static void MELON_NEXT() throws Exception { // Ctrl + Right
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_RIGHT);
        rb.keyRelease(KeyEvent.VK_CONTROL);
        rb.delay(delayTime);
    }

    static void MELON_MUTE() throws Exception { // F6
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_F6);
        rb.delay(delayTime);
    }

    static void MELON_VLUME_UP() throws Exception { // Ctrl + Up
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_UP);
        rb.keyRelease(KeyEvent.VK_CONTROL);
    }

    static void MELON_VLUME_DOWN() throws Exception { // Ctrl + Down
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_DOWN);
        rb.keyRelease(KeyEvent.VK_CONTROL);
    }

    /* Gom */
    static Process psc_Gom;
    static final String GOM_Location = "C:\\Program Files (x86)\\GRETECH\\GomPlayer\\GOM.exe";

    static void GOM_FILE_OPEN() throws Exception { // Alt + i
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_I);
        rb.keyRelease(KeyEvent.VK_CONTROL);
        rb.delay(delayTime);
    }

    static void GOM_ON() throws Exception {
        psc_Gom = Runtime.getRuntime().exec(GOM_Location);
    }

    static void GOM_OFF() throws Exception {
        psc_Gom.destroy();
    }

    static void GOM_BBACK() throws Exception { // Ctrl + Left
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_LEFT);
        rb.keyRelease(KeyEvent.VK_CONTROL);
        rb.delay(delayTime);
    }

    static void GOM_BACK() throws Exception { // Left
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_LEFT);
        rb.delay(delayTime);
    }

    static void GOM_PLAY() throws Exception { // Space		
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_SPACE);
        rb.delay(delayTime);
    }

    static void GOM_FRONT() throws Exception { // Right		
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_RIGHT);
        rb.delay(delayTime);
    }

    static void GOM_FFRONT() throws Exception { // Ctrl + Right
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_RIGHT);
        rb.keyRelease(KeyEvent.VK_CONTROL);
        rb.delay(delayTime);
    }

    static void GOM_PLAYLIST() throws Exception { // F8			
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_F8);
        rb.keyRelease(KeyEvent.VK_F8);
        rb.delay(delayTime);
    }

    static void GOM_PREV() throws Exception { // PageUp		
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_PAGE_UP);
        rb.delay(delayTime);
    }

    static void GOM_NEXT() throws Exception { // PageDown		
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_PAGE_DOWN);
        rb.delay(delayTime);
    }

    static void GOM_MUTE() throws Exception { // M
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_M);
        rb.delay(delayTime);
    }

    static void GOM_VLUME_UP() throws Exception { // Up
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_UP);
        rb.delay(delayTime);
    }

    static void GOM_VLUME_DOWN() throws Exception { // Down
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_DOWN);
        rb.delay(delayTime);
    }

    static void GOM_ESC() throws Exception { // 특수키
        Robot rb = new Robot();
        rb.keyPress(KeyEvent.VK_ESCAPE);
        rb.delay(delayTime);
    }

    /* ETC Programs */
    // Kakao
    static Process psc_Kakao;
    static final String KAKAO_Location = "C:\\Program Files (x86)\\Kakao\\KakaoTalk\\KakaoTalk.exe";

    static void KAKAO_ON() throws Exception {
        psc_Kakao = Runtime.getRuntime().exec(KAKAO_Location);
    }

    static void KAKAO_OFF() throws Exception {
        psc_Kakao.destroy();
    }
    //Nate
    static Process psc_Nate;
    static final String NATE_Location = "C:\\Program Files (x86)\\SK Communications\\NATEON\\BIN\\NateOn.exe";

    static void NATE_ON() throws Exception {
        psc_Nate = Runtime.getRuntime().exec(NATE_Location);
    }

    static void NATE_OFF() throws Exception {
        psc_Nate.destroy();
    }

    //TeamView
    static Process TeamView;
    static final String TEAMVIEW_Location = "C:\\Program Files (x86)\\TeamViewer\\Version8\\TeamViewer.exe";

    static void TEAMVIEW_ON() throws Exception {
        TeamView = Runtime.getRuntime().exec(TEAMVIEW_Location);
    }

    static void TEAMVIEW_OFF() throws Exception {
        TeamView.destroy();
    }
    // Variables declaration - do not modify                     
    private javax.swing.JButton button_exit;
    private javax.swing.JButton button_ip;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField textField_server;
    // End of variables declaration                   
}
