package me.henry.lib_base.others.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.core.content.FileProvider;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class UriUtil {

    public static final String authority = "com.litalk.messager.fileprovider";

    /**
     * 将File转换为Uri
     *
     * @param context
     * @param file
     * @return
     */
    public static Uri getUriFromFile(Context context, File file) {
        if (Build.VERSION.SDK_INT >= 24) {
            return FileProvider.getUriForFile(context, authority, file);
        } else {
            return Uri.fromFile(file);
        }
    }



    public static Uri getUriFromFile(Context context,String filePath) {
        return getUriFromFile(context, new File(filePath));
    }

    /**
     * 将Uri转换为File，File名称不变
     *
     * @param uri
     * @return
     */
    public static File getFileFromUri(Context context,Uri uri) {
        return getFileFromUri(uri,context);
    }

    public static File getFileFromUri(Uri uri, Context context) {
        if (uri == null || uri.getScheme() == null) {
            return null;
        }
        switch (uri.getScheme()) {
            case "content":
                return getFileFromContentUri(uri, context);
            case "file":
                return new File(uri.getPath());
            default:
                return null;
        }
    }

    /**
     * Gets the corresponding path to a file from the given content:// URI
     *
     * @param contentUri The content:// URI to find the file path from
     * @param context    Context
     * @return the file path as a string
     */

    private static File getFileFromContentUri(Uri contentUri, Context context) {
        String path = getPath(context, contentUri);
        if (TextUtils.isEmpty(path)) {
            path = getPathFromInputStreamUri(context, contentUri);
        }
        if (TextUtils.isEmpty(path))
            return null;
        return new File(path);
    }

    /**
     * 用流拷贝文件一份到自己APP目录下
     *
     * @param context
     * @param uri
     * @return
     */
    private static String getPathFromInputStreamUri(Context context, Uri uri) {
        String fileName = getNameColumn(context, uri);
        if (TextUtils.isEmpty(fileName)) {
            fileName = uri.toString();
            int index;
            if (fileName.contains("/") && (index = fileName.lastIndexOf("/")) > -1) {
                if (index < fileName.length())
                    fileName = fileName.substring(index + 1);
            }
            if (TextUtils.isEmpty(fileName)) {
                fileName = "unknow";
            }
        }
        InputStream inputStream = null;
        String filePath = null;

        if (TextUtils.isEmpty(fileName))
            return null;

        if (uri.getAuthority() != null) {
            try {
                inputStream = context.getContentResolver().openInputStream(uri);
                File file = createTemporalFileFrom(context, inputStream, fileName);
                filePath = file.getPath();

            } catch (Exception e) {
                //todo log exception
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (Exception e) {
                    //todo log exception
                }
            }
        }

        return filePath;
    }

    private static File createTemporalFileFrom(Context context, InputStream inputStream, String fileName)
            throws IOException {
        File targetFile = null;

        if (inputStream != null) {
            int read;
            byte[] buffer = new byte[8 * 1024];
            //自己定义拷贝文件路径
            targetFile = new File(context.getCacheDir(), fileName);
            if (targetFile.exists()) {
                targetFile.delete();
            }
            OutputStream outputStream = new FileOutputStream(targetFile);

            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();

            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return targetFile;
    }

    private static String getPath(Context context, Uri uri) {
        String path;
        try {
            // 以 file:// 开头的
            if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
                return uri.getPath();
            }
            // 4.4及之后的
            if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
                if (DocumentsContract.isDocumentUri(context, uri)) {
                    String idStr = DocumentsContract.getDocumentId(uri);
                    String[] split = idStr.split(":");
                    if (isExternalStorageDocument(uri)) {
                        // ExternalStorageProvider
                        if("primary".equalsIgnoreCase(split[0])) {
                            return Environment.getExternalStorageDirectory() + "/" + split[1];
                        }
                    } else if (isDownloadsDocument(uri)) {
                        // DownloadsProvider
                        boolean isId = idStr.matches("[0-9]+");
                        if(!isId && "raw".equalsIgnoreCase(split[0])) {
                            return split[1];
                        }else if(!isId && "msf".equalsIgnoreCase(split[0])){
                            idStr = split[1];
                        }
                        Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                                Long.parseLong(idStr));
                        return getPathColumn(context, contentUri, null, null);
                    } else if (isMediaDocument(uri)) {
                        // MediaProvider
                        Uri contentUri = null;
                        if ("image".equalsIgnoreCase(split[0])) {
                            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        } else if ("video".equalsIgnoreCase(split[0])) {
                            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        } else if ("audio".equalsIgnoreCase(split[0])) {
                            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        }
                        return getPathColumn(context, contentUri, "_id=?", new String[]{split[1]});
                    }
                } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                    if (isAppDocument(uri)) {
                        path = uri.getPath();
                        if (TextUtils.isEmpty(path)) return null;
                        int index = path.indexOf("/", 1);
                        if (index < 1) return null;
                        String scheme = path.substring(1, index);
                        switch (scheme) {
                            case "files":
                                path = context.getFilesDir() + path.substring(path.indexOf("/", 1));
                                break;
                            case "cache":
                                path = context.getCacheDir() + path.substring(path.indexOf("/", 1));
                                break;
                            case "external":
                                path = Environment.getExternalStorageDirectory() + path.substring(path.indexOf("/", 1));
                                break;
                            case "externalcache":
                                path = context.getExternalCacheDir() + path.substring(path.indexOf("/", 1));
                                break;
                        }
                        return path;
                    }
                    return getPathColumn(context, uri, null, null);
                } else if ("file".equalsIgnoreCase(uri.getScheme())) {// File
                    return uri.getPath();
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getPathColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        return getColumn(context, uri, "_data", selection, selectionArgs);
    }

    private static String getNameColumn(Context context, Uri uri) {
        return getColumn(context, uri, "_display_name", null, null);
    }

    public static String getMimeTypeColumn(Context context, Uri uri) {
        return getColumn(context, uri, "mime_type", null, null);
    }

    public static long getSizeColumn(Context context, Uri uri) {
        return getLongColumn(context, uri, "_size", null, null);
    }

    public static long getDurationColumn(Context context, Uri uri) {
        return getLongColumn(context, uri, "duration", null, null);
    }

    private static String getColumn(Context context, Uri uri, String column, String selection, String[] selectionArgs) {
        try (Cursor cursor = context.getContentResolver().query(uri, null, selection, selectionArgs, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                String[] columnNames = cursor.getColumnNames();
                if (columnNames.length > 0) {
                    if (Arrays.asList(columnNames).contains(column))
                        return cursor.getString(cursor.getColumnIndexOrThrow(column));
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long getLongColumn(Context context, Uri uri, String column, String selection, String[] selectionArgs) {
        try (Cursor cursor = context.getContentResolver().query(uri, null, selection, selectionArgs, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                String[] columnNames = cursor.getColumnNames();
                if (columnNames.length > 0) {
                    if (Arrays.asList(columnNames).contains(column))
                        return cursor.getLong(cursor.getColumnIndexOrThrow(column));
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isAppDocument(Uri uri) {
        return authority.equals(uri.getAuthority());
    }


}
