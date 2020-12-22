package me.henry.lib_base.others.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;



//import net.sf.jmimemagic.Magic;
//import net.sf.jmimemagic.MagicException;
//import net.sf.jmimemagic.MagicMatchNotFoundException;
//import net.sf.jmimemagic.MagicParseException;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 文件工具类
 * <p>
 * 提供诸如文件格式转换、获取文件类型等通用的方法
 * @author chenf
 * @since 1.0
 */
public class FileUtil {

    private static final String TAG = "FileUtil";

    /**
     * 文件转字节码
     * @param filePath 文件路径
     * @return
     */
    public static byte[] file2byte(String filePath) {
        if (TextUtils.isEmpty(filePath) || !new File(filePath).exists())
            return null;
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[8 * 1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 字节码转文件
     * @param buf  字节码
     * @param file 文件
     */
    public static void byte2File(byte[] buf, File file) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 复制文件
     * @param source 输入文件
     * @param target 输出文件
     */
    public static void copy(File source, File target) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            if (!target.exists())
                target.createNewFile();
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(target);
            byte[] buffer = new byte[8 * 1024];
            int byteRead;
            while (-1 != (byteRead = fileInputStream.read(buffer))) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 复制文件
     * @param filename 文件名
     * @param bytes    数据
     */
    public static void copy(String filename, byte[] bytes) {
        try {
            //如果手机已插入sd卡,且app具有读写sd卡的权限
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                FileOutputStream output = null;
                output = new FileOutputStream(filename);
                output.write(bytes);
                output.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件大小转换
     * @param bytes     转换的字节
     * @param needUnits 是否需要单位
     * @return
     */
    public static String byteFormat(long bytes, boolean needUnits) {
        String[] units = new String[]{" B", " KB", " MB", " GB", " TB", " PB", " EB", " ZB", " YB"};
        int unit = 1024;
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        double pre = 0;
        if (bytes > 1024) {
            pre = bytes / Math.pow(unit, exp);
        } else {
            pre = (double) bytes / (double) unit;
        }
        if (needUnits) {
            if (exp == 0) {//如果是0，直接拿byte
                return String.format(Locale.ENGLISH, "%d%s", bytes, units[(int) exp]);
            } else {
                return String.format(Locale.ENGLISH, "%.1f%s", pre, units[(int) exp]);
            }
        }
        return String.format(Locale.ENGLISH, "%.1f", pre);
    }

    /**
     * 根据字节码输出文件大小
     * @param bytes 转换的字节
     * @return 输出的文件大小，以MB为单位
     */
    public static String byteFormat(long bytes) {
        int unit = 1024 * 1024;
        double exp = (double) bytes / unit;
        if (exp < 0.1) exp = 0.1;
        return String.format("%.1fMB", exp);
    }

//    /**
//     * 根据后缀名获取本地文件Mime类型
//     * @param path
//     * @param defaultType
//     * @return
//     */
//    public static String getLocalFileMimeTypeFromExtension(String path, @Nullable String defaultType) {
//        String type = null;
//        //使用系统API，获取URL路径中文件的后缀名（扩展名）
//        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
//        if (TextUtils.isEmpty(extension)) {
//            int index = path.lastIndexOf(".");
//            if (index < path.length() && index > -1) {
//                extension = path.substring(index+1);
//            }
//        }
//        if (!TextUtils.isEmpty(extension)) {
//            //使用系统API，获取MimeTypeMap的单例实例，然后调用其内部方法获取文件后缀名（扩展名）所对应的MIME类型
//            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
//        }
//        if (TextUtils.isEmpty(type)) {
//            try {
//                type = Magic.getMagicMatch(new File(path), false, true).getMimeType();
//            } catch (MagicParseException | MagicMatchNotFoundException | MagicException e) {
//                LogUtil.e("获取mimeType出错");
//            }
//        }
//        if (TextUtils.isEmpty(type)) {
//            if (!TextUtils.isEmpty(defaultType))
//                type = defaultType;
//            else
//                type = "application/octet-stream";
//        }
//        return type;
//    }

    /**
     * 获取图片类型文件的后缀
     * @param mimeType 文件的类型，如果没有则为jpg
     * @return
     */
    public static String getSuffix(String mimeType) {
        if (TextUtils.isEmpty(mimeType)) {
            return "jpg";
        }
        if (mimeType.startsWith("image")) return getSuffix(mimeType, "jpg");
        else if (mimeType.startsWith("video")) return getSuffix(mimeType, "mp4");
        else return "jpg";
    }

    /**
     * 获取指定类型文件的后缀
     * @param mimeType 文件的类型，如果没有则为defaultType
     * @return
     */
    public static String getSuffix(String mimeType, String defaultType) {
        if (TextUtils.isEmpty(mimeType)) {
            return defaultType;
        }
        String suffix = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType);
        if (TextUtils.isEmpty(suffix)) {
            return defaultType;
        }
        return suffix;
    }

    public static File getThumbnailCache(Context context, String name) {
        File dirFile = context.getExternalFilesDir("cache_thumbnail");
        File file = new File(dirFile, name);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }

    /**
     * 将字符串写入到文本文件中
     * @param string
     */
    public static void outputString(String string, File file) {
        String strContent = string + "\r\n";
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
//            raf.seek(file.length());
            raf.seek(0);   // 覆盖原文本内容
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成文件
     * @param filePath
     * @param fileName
     * @return
     */
    public static File createNewFile(String filePath, String fileName) {
        File file = null;
        makeDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 生成文件夹
     * @param filePath
     */
    public static void makeDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取指定目录下的所有TXT文件的文件内容
     * @param file
     * @return
     */
    public static String inputString(File file) {
        String content = "";
        if (!file.isDirectory()) { //检查此路径名的文件是否是一个目录(文件夹)
//            if (file.getName().endsWith("txt")) {//文件格式为""文件
            try {
                InputStream inputStream = new FileInputStream(file);
                if (inputStream != null) {
                    InputStreamReader inputStreamReader
                            = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line;
                    //分行读取
                    while ((line = bufferedReader.readLine()) != null) {
                        content += line + "\n";
                    }
                    inputStream.close();//关闭输入流
                }
            } catch (java.io.FileNotFoundException e) {
                Log.e(TAG, "The File doesn't not exist.");
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
//            }
        }
        return content;
    }

    /**
     * 递归计算某个文件夹下面的文件大小
     * 耗时计算，建议使用子线程
     * @param file
     * @return
     */
    public static long getFileSize(File file, List<String> filter) {
        if (file.isFile())
            return file.length();
        final File[] children = file.listFiles();
        long total = 0;
        if (children != null) {
            for (final File child : children) {
                if (filter != null && filter.contains(child.getAbsolutePath()))
                    continue;
                total += getFileSize(child, filter);
            }
        }
        return total;
    }

    /**
     * 递归删除文件,过滤某个文件夹
     * @param file
     */
    public static void deleteFile(File file, List<String> filter) {
        if (file.isFile()) {
            file.delete();
        }
        final File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                if (filter != null && filter.contains(child.getAbsolutePath()))
                    continue;
                deleteFile(child, filter);
            }
        }
    }

    /**
     * 递归删除文件
     * @param path 文件路径
     */
    public static void deleteFile(String path) {
        if (TextUtils.isEmpty(path)) return;
        File file = new File(path);
        if (file.exists()) deleteFile(file);
    }

    /**
     * 递归删除文件
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.isFile())
            file.delete();
        final File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                deleteFile(child);
            }
        }
    }

    /**
     * 文件是否存在
     * @param url             下载超链接
     * @param storageDir      下载目录
     * @param storageFileName 文件名
     * @return
     */
    public static boolean isExists(String url,
                                   String storageDir,
                                   String storageFileName) {
        if (TextUtils.isEmpty(storageFileName))
            storageFileName = url.substring(url.lastIndexOf("/"));
        File file = new File(storageDir, storageFileName);
        return file.exists();
    }
//    /**
//     * 打开文件
//     */
//    public static void openFile(Context context, String storageDir, String storageFileName) {
//        File file = new File(storageDir, storageFileName);
//        String mimeType = FileUtil.getLocalFileMimeTypeFromExtension(file.getAbsolutePath(), null);
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
//            intent.setDataAndType(contentUri, mimeType);
//        } else {
//            intent.setDataAndType(Uri.fromFile(file), mimeType);
//        }
//        context.startActivity(intent);
//    }

    /**
     * 解压文件
     * @param archive       压缩文件路径
     * @param decompressDir 解压目标路径
     * @param isDeleteZip   解压完毕是否删除压缩文件
     * @throws IOException
     */
    public static void unZipFile(String archive, String decompressDir, boolean isDeleteZip) {
        if(TextUtils.isEmpty(archive) || TextUtils.isEmpty(decompressDir)) return;
        File zipFile = new File(archive);
        File pathFile = new File(decompressDir);
        if(!zipFile.exists()) return;
        if(!pathFile.exists()) pathFile.mkdirs();
        try {
            unZipFiles(zipFile, pathFile);
            /*BufferedInputStream bi;
            ZipFile zf = new ZipFile(archive);
            Enumeration e = zf.entries();
            while (e.hasMoreElements()) {
                ZipEntry ze2 = (ZipEntry) e.nextElement();
                String entryName = ze2.getName();
                String path = decompressDir + "/" + entryName;
                if (ze2.isDirectory()) {
                    File decompressDirFile = new File(path);
                    if (!decompressDirFile.exists()) {
                        decompressDirFile.mkdirs();
                    }
                } else {
                    String fileDir = path.substring(0, path.lastIndexOf("/"));
                    if (decompressDir.endsWith(".zip")) {
                        decompressDir = decompressDir.substring(0, decompressDir.lastIndexOf(".zip"));
                    }
                    File fileDirFile = new File(decompressDir);
                    if (!fileDirFile.exists()) {
                        fileDirFile.mkdirs();
                    }
                    String substring = entryName.substring(entryName.lastIndexOf("/") + 1, entryName.length());
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(decompressDir + "/" + substring));
                    bi = new BufferedInputStream(zf.getInputStream(ze2));
                    byte[] readContent = new byte[1024];
                    int readCount = bi.read(readContent);
                    while (readCount != -1) {
                        bos.write(readContent, 0, readCount);
                        readCount = bi.read(readContent);
                    }
                    bos.close();
                }
            }
            zf.close();
            if (isDeleteZip) {
                File zipFile = new File(archive);
                if (zipFile.exists() && zipFile.getName().endsWith(".zip")) {
                    zipFile.delete();
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            //解压失败，删除
            pathFile.delete();
        }
    }

    private static void unZipFiles(File zipFile, File pathFile)throws IOException
    {
        String fileEncode = EncodeUtil.getEncode(zipFile.getAbsolutePath(),true);
        ZipFile zip = new ZipFile(zipFile);
        for(Enumeration entries = zip.entries(); entries.hasMoreElements();)
        {
            ZipEntry entry = (ZipEntry)entries.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);
            String outPath = (pathFile.getPath()+"/"+zipEntryName).replaceAll("\\*", "/");
            //判断路径是否存在,不存在则创建文件路径
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if(!file.exists())
            {
                file.mkdirs();
            }
            //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if(new File(outPath).isDirectory())
            {
                continue;
            }
            //输出文件路径信息
            System.out.println(outPath);
            OutputStream out = new FileOutputStream(outPath);
            byte[] buf1 = new byte[1024];
            int len;
            while((len=in.read(buf1))>0)
            {
                out.write(buf1,0,len);
            }
            in.close();
            out.close();
            zipFile.delete();
        }
    }
}
