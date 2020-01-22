package lt.bit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Files {

	private String sb = "";
	private String root = "";
	boolean hasPlacedBtn;

	public Files(File f) {
		this.root = f.getName();
		this.hasPlacedBtn = false;
		readStuff(f);
	}

	public Files(File f, boolean placeholder) {
		this.hasPlacedBtn = false;
		System.out.println(f.getName());
		HTMLviewer(f);
	}

	private void readStuff(File f) {
		if (f == null) {
			return;
		}
		if (!f.exists()) {
			return;
		}
		if (f.getName() == this.root && !hasPlacedBtn) {
			sb += "<a style=\"font-size:24px;color:red\" href=\"/exit\">Exit</a><br><br>";
			hasPlacedBtn = true;
		} else if (f.getName() != this.root && !hasPlacedBtn) {
			sb += "<a style=\"font-size:24px;color:red\" href=\"../\">Back</a><br><br>";
			hasPlacedBtn = true;
		}
		if (f.isDirectory()) {
			File[] ff = f.listFiles();
			for (int i = 0; i < ff.length; i++) {
				StringBuilder(ff[i]);
				readStuff(ff[i]);
			}
		} else {

		}

	}

//	private String parentDir(File f) {
//		String s = f.getName() + "/";
//		try {
//			while (!f.getName().equals("web") || !f.getParentFile().getName().equals("web")) {
//				f = f.getParentFile();
//				s += f.getName();
//			}
//			if(s.contains("web")) {
//				s = s.replace("web/", "");
//			}
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//		return s;
//	}
	
	private String workWithPath(File f) {
		String s = f.getAbsolutePath();
		s = s.replaceAll("^(.*?)\\web\\\\", "/");
		return s;
	}
	
	private String whiteSpace(File f) {
		String s = workWithPath(f);
		char c = '\\';
		int count = 1;
		for (int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == c) {
				count++;
			}
		}
		count = count * 20;
		
		return Integer.toString(count);
	}

	private void StringBuilder(File f) {
		sb += "<li style = \"margin-left:"+whiteSpace(f)+"\">";
//		sb += "<a href=\"/" + (f.isDirectory() ? "" : "/" ) + f.getName() + "\">";
		sb += "<a href=\"" + workWithPath(f) + "\">";
		sb += (f.isDirectory() ? "/" : "") + f.getName();
		sb += "</a>";
		sb += "</li>";
	}

	private void HTMLviewer(File f) {
		
		try (InputStream is = new FileInputStream(f);
				Reader r = new InputStreamReader(is, "UTF-8");
				BufferedReader br = new BufferedReader(r);) {
			String s;
			sb = "<a style=\"font-size:24px;color:red\" href=\"../\">Back</a><br><br>";
			while ((s = br.readLine()) != null) {
				sb += s;
			}

		} catch (Exception e) {
			sb = "<a style=\"font-size:24px;color:red\" href=\"../\">Back</a><br><br>";
			sb += "<h1>404 Not found :)</h1>";
			System.out.println(f.getName());
			System.out.println(e);
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "<ul>" + sb + "</ul>";
	}

}
