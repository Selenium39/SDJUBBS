package com.selenium.sdjubbs.common.util;

import java.io.File;

public class FileUtil {
    public static void deleteFilesWithPrefix(String path, String prefix) {
        if (path.isEmpty() || prefix.isEmpty()) {
            return;
        }
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File fileTemp : files) {
                if (fileTemp.getName().contains(prefix)) {
                    fileTemp.delete();
                }
            }
        }

    }
}
