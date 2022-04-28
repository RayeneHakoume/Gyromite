package VueControleur;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

public class JarUtil {

    public static void readJARList(String jarFilePath) throws IOException {

        JarFile jarFile = new JarFile(jarFilePath);

        Enumeration en = jarFile.entries();
        while (en.hasMoreElements()) {
            process(en.nextElement());
        }
    }

    private static void process(Object obj) {
        JarEntry entry = (JarEntry) obj;
        String name = entry.getName();
        long size = entry.getSize();
        long compressedSize = entry.getCompressedSize();
        System.out.println(name + "\t" + size + "\t" + compressedSize);
    }

    public static void readJarFile(String jarFilePath, String fileName) throws IOException {
        JarFile jarFile = new JarFile(jarFilePath);
        JarEntry entry = jarFile.getJarEntry(fileName);
        InputStream input = jarFile.getInputStream(entry);
        readFile(input);
        jarFile.close();
    }

    public static void readFile(InputStream input) throws IOException {
        InputStreamReader in = new InputStreamReader(input);
        BufferedReader reader = new BufferedReader(in);
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }

    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }

    public static void writeJarFile(String jarFilePath, String entryName, byte[] data) throws Exception {

        JarFile jarFile = new JarFile(jarFilePath);
        TreeMap tm = new TreeMap();
        Enumeration es = jarFile.entries();
        while (es.hasMoreElements()) {
            JarEntry je = (JarEntry) es.nextElement();
            byte[] b = readStream(jarFile.getInputStream(je));
            tm.put(je.getName(), b);
        }

        JarOutputStream jos = new JarOutputStream(new FileOutputStream(jarFilePath));
        Iterator it = tm.entrySet().iterator();
        boolean has = false;

        while (it.hasNext()) {
            Map.Entry item = (Map.Entry) it.next();
            String name = (String) item.getKey();
            JarEntry entry = new JarEntry(name);
            jos.putNextEntry(entry);
            byte[] temp;
            if (name.equals(entryName)) {
                temp = data;
                has = true;
            } else {
                temp = (byte[]) item.getValue();
            }
            jos.write(temp, 0, temp.length);
        }

        if (!has) {
            JarEntry newEntry = new JarEntry(entryName);
            jos.putNextEntry(newEntry);
            jos.write(data, 0, data.length);
        }
        jos.finish();
        jos.close();

    }

}
