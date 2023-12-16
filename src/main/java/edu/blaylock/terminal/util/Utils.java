package edu.blaylock.terminal.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {
    public static void loadDll(String path) throws IOException {
        InputStream in = Utils.class.getResourceAsStream(path);
        assert in != null;

        File temp = File.createTempFile("terminal", ".dll");
        temp.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(temp);

        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            fos.write(buffer, 0, read);
        }
        fos.close();
        in.close();
        System.load(temp.getAbsolutePath());

    }
}
