package diskbiggest;

import java.io.File;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * User: Oleksiy Pylypenko
 * Date: 10/10/12
 * Time: 11:49 AM
 */
public class DiskBiggest {

    static String sizeShortcut(long size) {
        if (size < 1024) {
            return String.format("%dB", size);
        } else if (size < 1024L * 1024) {
            return String.format("%.2fKiB", size / 1024.0);
        } else if (size < 1024L * 1024 * 1024) {
            return String.format("%.2fMiB", size / (1024.0 * 1024.0));
        } else if (size < 1024L * 1024 * 1024 * 1024) {
            return String.format("%.2fGiB", size / (1024.0 * 1024.0 * 1024.0));
        } else {
            return String.format("%.2fTiB", size / (1024.0 * 1024.0 * 1024.0 * 1024.0));
        }
    }

    static class FilePath implements Comparable<FilePath> {
        private long size;
        private File filename;

        public FilePath(File filename, long size) {
            this.filename = filename;
            this.size = size;
        }

        public int compareTo(FilePath o) {
            return -Long.valueOf(size).compareTo(o.size);
        }

        @Override
        public String toString() {
            return DiskBiggest.sizeShortcut(size) + " " + filename;
        }
    }

    private NavigableSet<FilePath> set = new TreeSet<FilePath>();
    private final int limit;

    public DiskBiggest(int limit) {
        this.limit = limit;
    }

    public long iterate(File filename) {
        long size = 0;
        File[] files = filename.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    size += f.length();
                } else if (f.isDirectory()) {
                    size += iterate(f);
                }
            }
        }
        enqueue(new FilePath(filename, size));
        return size;
    }

    private void enqueue(FilePath filePath) {
        if (set.size() >= limit) {
            set.pollLast();
        }
        set.add(filePath);
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("java -jar diskbiggest.jar PATH [N=30]");
            System.exit(1);
        }

        int n = 30;
        if (args.length >= 2) {
            n = Integer.parseInt(args[1]);
        }
        System.out.println("Top " + n + " file and directories");
        DiskBiggest diskBiggest = new DiskBiggest(n);
        diskBiggest.iterate(new File(args[0]));
        diskBiggest.output();
    }

    public void output() {
        for (FilePath path : set) {
            System.out.println(path);
        }
    }
}
