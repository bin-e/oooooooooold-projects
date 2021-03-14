import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class TcpIpServer {
	public static void main(String args[]) throws Exception {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(7777);
			System.out.println(getTime() + "서버가 준비되었습니다.");
		} catch (IOException ie) {
			ie.printStackTrace();
		}

		while (true) {
			try {
				System.out.println(getTime() + "연결 요청을 기다립니다.");
				Socket socket = serverSocket.accept();
				System.out.println(getTime() + socket.getInetAddress()
						+ " <로 부터 연결요청이 들어왔습니다.");

				System.out.println("getPort() : " + socket.getPort());
				System.out.println("getLocalPort() : " + socket.getLocalPort());
				System.out.println(socket.getLocalAddress().toString());

				DataInputStream dInput = new DataInputStream(
						socket.getInputStream());
				DataOutputStream dOutput = new DataOutputStream(
						socket.getOutputStream());

				dOutput.writeUTF("보내는 말");
				System.out.println(getTime() + "'보내는 말' 를 전송했습니다.");

				dInput.close();
				dOutput.close();
				socket.close();

			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}
	}

	private static void MelonRun() {

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
			while ((line = output.readLine()) != null)
				System.out.println(line);

			// The process should be done now, but wait to be sure.
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	static String getTime() {
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date());
	}
}
