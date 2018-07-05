package resources;

import java.net.*;

public class RLoader {

public static void loadResourceLibrary(String libName){
	
	String name = new String(libName);
	String osname = System.getProperty("os.name");

	if (System.getProperty("os.arch").contains("64")){
		name = name + "64";
	}
	

	boolean ok = false;
	
	if (osname.startsWith("Windows")){
		name = name + ".dll";
		ok = true;
	} else {
		if (osname.startsWith("Linux")){
			name = name + ".so";
			ok = true;
		}
		if (osname.startsWith("Mac")){
			name = name + "mac.so";
			ok = true;
		}
	}
	
	if (ok == true){
		String path = getResourcePath(name);
		System.load(path);
	} else {
		System.err.println("System not supported or Native library not installed");
	}
}


public static String getResourcePath(String resource){
	URL url = RLoader.class.getResource(resource);
	if (url == null){
		System.err.println("no resource found (" + resource + ") ... Fatal Error");
	}
	String path = url.getPath();
	path = path.replace("%20", " ");
	return path;
}

}
