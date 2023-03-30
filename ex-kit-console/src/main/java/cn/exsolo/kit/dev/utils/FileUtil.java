package cn.exsolo.kit.dev.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 *
 * @author prestolive
 */

public class FileUtil {

    /**
     * 下载流
     * @param response
     * @param inputStream
     * @param fileName
     * @param file_type
     */
    public static void download(HttpServletResponse response,InputStream inputStream, String fileName, String file_type) {
        InputStream in = null;
        OutputStream out = null;
        try {
            // 以流的形式下载文件。
            in = new BufferedInputStream(inputStream);
            // 清空response
            response.reset();
            // 设置编码格式
            response.setCharacterEncoding("UTF-8");
            // 设置输出的格式
            if ("image/jpeg".equals(file_type)) {
                response.setContentType("image/jpeg");
            } else {
                response.setContentType(file_type);
                // 设置response的Header
                response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1") + "");
            }
            out = new BufferedOutputStream(response.getOutputStream());
            // 循环取出流中的数据
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                //do nothing
            }
        }
    }

    /**
     * 下载文件
     *
     * @param response
     * @param file_path
     * @param fileName
     * @param file_type
     */
    public static void downloadFile(HttpServletResponse response, String file_path, String fileName, String file_type) {
        try {
            download(response,new FileInputStream(file_path),fileName,file_type);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }
}
