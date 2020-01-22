package lt.bit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Core {

	public static void main(String[] args) throws IOException {
		boolean working = true;
		while (working) {
			ServerSocket ss = new ServerSocket(8003);

			Socket s = ss.accept();

			Reader r = new InputStreamReader(s.getInputStream(), "UTF-8");

			BufferedReader br = new BufferedReader(r);
			boolean isHtml = false;
			String st;
			String path = "";
			st = br.readLine();
			try {
				path = st;
				if (st.contains(".html")) {
					isHtml = true;
				}
				Pattern p = Pattern.compile("\\s\\/\\w+.*\\s");
				Matcher m = p.matcher(path);
				m.find();
				path = m.group();
				path = path.replaceAll("\\s", "");
				System.out.println("Going: " + path);
				if (path.equals("/exit")) {
					working = false;
				}
			} catch (IllegalStateException e) {
				path = "";
				System.out.println("Going: root");
			} catch (Exception e) {
				// air
			}

			Files files = null;
			if (isHtml) {
				File startingPoint = new File("web" + path);
				files = new Files(startingPoint, true);
			} else {
				File startingPoint = new File("web" + path);
				files = new Files(startingPoint);
			}

			Writer w = new OutputStreamWriter(s.getOutputStream(), "UTF-8");
			BufferedWriter bw = new BufferedWriter(w);
			bw.write("HTTP/1.1 200 Ok");
			bw.newLine();
			bw.newLine();
			bw.write("<html><style>body{background-color:#e9f5f8;}a{color:blue}</style><body>" + files + "</body></html>");
			if (!working) {
				bw.write("<a href=\"../\">Back (after restart)</a><br>");
			}
			bw.flush();

			s.close();
			ss.close();
		}

	}

}
