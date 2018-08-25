package com.future.link.utils;

import com.jfinal.kit.PropKit;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhengbingbing on 2016/3/22.
 */
public class FileUtil {

    public static void upload(File src, File target) throws Exception {
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            fos = new FileOutputStream(target);
            fis = new FileInputStream(src);

            byte[] buffer = new byte[1024*1024];
            int len=0;
            while((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0 , len);
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            fos.close();
            fis.close();
        }
    }

    public static String uploadFile(File file, String dir, String fileName) throws Exception {
        File rootDir = new File( dir);
        if(!rootDir.exists()) {
            rootDir.mkdirs();
        }
        File targetFile = new File(rootDir.getAbsolutePath() + File.separator + fileName);
        if(!targetFile.exists()) {
            targetFile.createNewFile();
        }
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            fos = new FileOutputStream(targetFile);
            fis = new FileInputStream(file);

            byte[] buffer = new byte[1024*1024];
            int len=0;
            while((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0 , len);
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            fos.close();
            fis.close();
        }
        return targetFile.getAbsolutePath();
    }


    //新建文件夹
    public static void createFolder(String folderPath) {
        try {
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建文件，如果没有父目录，会创建父目录
     * @param file
     * @return 如果指定的文件不存在并成功地创建，则返回 true；如果指定的文件已经存在，则返回 false,否则抛出异常
     */
    public static boolean createFile(File file){
        if(file.exists()) {
            return false;
        } else {
            if(!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                file.createNewFile();
                return true;
            } catch (IOException e) {
                throw new RuntimeException("create file meet IOException", e);
            }

        }
    }



    /**
     * @param srcFile
     * @param desFile
     * @param isCreate   if the file(include directory) not exists, is create a new file(include dir) or not
     * @return
     */
    public static long copyFile(File srcFile, File desFile, boolean isCreate){
        long copySizes = 0;
        if(srcFile==null || !srcFile.exists()){
            System.err.println(FileUtil.class+"要复制的文件不存在！");
            return -1;
        }
        if(desFile==null || (!desFile.exists() && !isCreate) ){
            System.err.println(FileUtil.class+"接收文件不存在！");
            return -1;
        }else{
            try {
                if(!desFile.getParentFile().exists()){
                    desFile.getParentFile().mkdirs();
                }

                if(!desFile.exists()){
                    desFile.createNewFile();
                }

                FileChannel fcin = new FileInputStream(srcFile).getChannel();
                FileChannel fcout = new FileOutputStream(desFile).getChannel();

                long size = fcin.size();
                fcin.transferTo(0,fcin.size(),fcout);

                fcin.close();
                fcout.close();
                copySizes = size;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return copySizes;
        }
    }

    public static long copyFile(File srcFile,File desFile){
        return copyFile(srcFile,desFile,true);
    }

    /**
     *
     * @return
     */
    public static long copyFile(String srcFile,String desFile){
        File src=new File(srcFile);
        File des=new File(desFile);
        return  copyFile(src,des,true);

    }

    /**
     *
     * @return
     */
    public static long copyFile(File srcFile,String desFile){
        File des=new File(desFile);
        return  copyFile(srcFile,des,true);

    }

    /**
     *
     * @return
     */
    public static long copyFile(String srcFile,File desFile){
        File src=new File(srcFile);
        return  copyFile(src,desFile,true);

    }

    public static long copyFile(File srcFile,String desDir,String newName){
        File des=new File(desDir,newName);
        return copyFile(srcFile,des,true);
    }

    /**
     * copy all file and directory under srcDir to desDir
     * 把srcDir目录下的文件和目录考到desDir下面
     * @param srcDir
     * @param desDir
     * @param isCreate
     * @return
     */
    public static long copyDirectory(File srcDir,File desDir,boolean isCreate){
        long size=0;
        if(srcDir==null || !srcDir.exists()){
            return -1;
        }
        if(desDir==null || ( !desDir.exists() && !isCreate ) ){
            return -1;
        }else{
            desDir.mkdirs();
            File[] fileList = srcDir.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isFile()) {
                    size=size+copyFile(fileList[i],new File(desDir,fileList[i].getName()),isCreate);
                } else {
                    size+=copyDirectory(fileList[i],new File(desDir.getPath()+File.separator+fileList[i].getName()),isCreate);
                }

            }
        }
        return size;
    }

    public static long copyDirectory(String srcDir,String desDir){
        File src=new File(srcDir);
        File des=new File(desDir);
        return  copyDirectory(src,des,true);
    }

    public static void delete(File target){
        if(target==null || !target.exists()){
            return;
        }
        if(target.isDirectory()){
            return ;
			/*if(!target.delete()){
				File[] fileList=target.listFiles();
				for(int i=0;i<fileList.length;i++){
					delete(fileList[i]);
				}
			}*/
        }else{
            target.delete();
        }
    }

    public static void deleteByUrl(String targetUrl){
        File target = new File(FileUtil.transformPath(targetUrl));
        if(target==null || !target.exists()){
            return;
        }
        if(target.isDirectory()){
            return ;
        }else{
            target.delete();
        }
    }

    //重命名
    public static boolean renameTo(File src,File des){
        return src.renameTo(des);
    }

    public static boolean renameTo(File src,String newName){
        File des=new File(src.getParent(),newName);
        return src.renameTo(des);
    }

    public static boolean renameTo(String srcPath,String newName){
        File srcFile=new File(srcPath);
        return renameTo(srcFile,newName);
    }


    //用于转化各个操作系统文件分割符不相同，以及空格的转化
    public static String transformPath(String path){
        try {
            if(path.startsWith("file:")){
                path=path.substring(6);
            }
            path=path= URLDecoder.decode(path,"utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException("UnsupportedEncodingException,转化编码错误：不支持的编码！");
        }
        if('/'==File.separatorChar){
            path=path.replace('\\', File.separatorChar);
        }else{
            path=path.replace('/', File.separatorChar);
        }
        return path;
    }

    /**
     * 用于转化各个操作系统文件分割符不相同，以及空格的转化
     * @param path
     * @param
     * @return
     */
    public static String transformPath(String path, int type) {
        if (type == 2) {
            path = path.replace('\\', '/');
        } else {
            path = path.replace('/', '\\');
        }
        return path;
    }

    public static BufferedReader getReader(String path) throws Exception {
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        return reader;
    }

    //下载
    public static void download(String weburl, String target) throws Exception {
        URL url = new URL(weburl);
        File outFile = new File(target);
        OutputStream os = new FileOutputStream(outFile);
        InputStream is = url.openStream();
        byte[] buff = new byte[1024];
        while (true) {
            int readed = is.read(buff);
            if (readed == -1) {
                break;
            }
            byte[] temp = new byte[readed];
            System.arraycopy(buff, 0, temp, 0, readed);
            os.write(temp);
        }
        is.close();
        os.close();
    }

    public static String getFileType(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }

    public static String getFileName(String qfileName) {
        return qfileName.substring(0, qfileName.lastIndexOf("."));
    }

    /**
     * 上声音
     * @param files
     * @return
     * @throws Exception
     */
    public static String uploadVoice(File[] files,String name[]) throws Exception

    {
        if (files != null && files.length != 0) {

            File file = files[0];

            String fileName = CommonUtil.getUUID() + "."
                    + FileUtil.getFileType(name[0]);
            String dirSource = getByTimeRootVoice(File.separator + "voiceSource",
                    new Date());

            File target = new File(dirSource
                    + fileName);

            FileUtil.upload(file, target);

            return dirSource + fileName;

        }else
        {
            return null;
        }

    }


    /**
     * 声音目录的组织方式
     *
     * @param dir
     * @param date
     * @return
     */
    public static String getByTimeRootVoice(String dir, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int mounth = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String timeRoot = dir + File.separator + year + "" + mounth
                + File.separator + day + File.separator + hour + File.separator;
        File rootDir = new File(timeRoot);
        if (!rootDir.exists()) {
            rootDir.mkdirs();
        }
        return timeRoot;
    }

    /**
     * 格式化目录的路径，主要是：1.把最后一个字符变成文件分隔符
     * @param path
     * @return
     */
    public static String formatPath(String path){
        String rst = path;
        if(!path.endsWith("/")) {
            rst = path+ "/";
        }
        return rst;
    }
    /**
     * 上传本地文件
     *
     * @param file 文件路径
     * @param dir 子路径
     * @param fileName 文件名称
     * @return
     * @throws Exception
     */
    public static String[] uploadLocalAPP( File file, String key,String dir, String fileName) throws Exception {

        //保存地址
        String saveUrl = PropKit.use("fileUploadConfig.properties").get(key);
        if(!saveUrl.endsWith("/")){
            saveUrl +='/';
        }
        //app本地访问地址
        String systemUrl = PropKit.use("fileUploadConfig.properties").get("system.app.url");

        String rst[] = new String[2];

        //本地问访问文件
		/* File rootDir = new File("D:/resource/" + dir);*/
        //版本 app 223 服务器
        File rootDir = new File(saveUrl + dir);


        if(!rootDir.exists()) {
            rootDir.mkdirs();
        }
        File targetFile = new File(rootDir.getAbsolutePath() + "/" + fileName);
        if(!targetFile.exists()) {
            targetFile.createNewFile();
        }
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            fos = new FileOutputStream(targetFile);
            fis = new FileInputStream(file);

            byte[] buffer = new byte[(int)file.length()];
            int len=0;
            while((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0 , len);
            }
        } catch(Exception e) {
            throw new Exception(e);
        } finally {
            fos.close();
            fis.close();
        }
        String relUrl = dir + "/" + fileName;//返回前台图片相对url

        if(relUrl.indexOf("/") == 0){
            relUrl = relUrl.substring(1, relUrl.length());
        }

        if(!systemUrl.endsWith("/")) {
            systemUrl+= '/';
        }
        systemUrl+= PropKit.use("fileUploadConfig.properties").get("system.file.appUrlPrefix");

        if(!systemUrl.endsWith("/")) {
            systemUrl+= '/';
        }

        String absUrl = systemUrl + relUrl;//返回前台图片绝对url
        rst[0] = relUrl;
        rst[1] = absUrl;

        return rst ;
    }

    /**
     * 提供网页下载
     * @param response
     * @throws IOException
     */
    public static void downloadToWeb(String path, HttpServletResponse response, String fileName) throws IOException {
        // 获取下载文件
        File downloadFile = new File(path);
        // 以流的形式下载文件
        InputStream fis = new BufferedInputStream(new FileInputStream(
                path));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();

        // 设置response
        response.reset();
        response.setCharacterEncoding("UTF-8");
        //解决中文显示乱码
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(),"ISO-8859-1"));
        response.addHeader("Content-Length", "" + path.length());
        // response.setContentType("application/octet-stream");
        response.setContentType("application/x-msdownload");
        OutputStream os = new BufferedOutputStream(response.getOutputStream());
        os.write(buffer);
        os.flush();
        os.close();
    }

    public String getUploadUrl() {
        String systemUrl = PropKit.use("fileUploadConfig.properties").get("system.image.url");
        String prefix = PropKit.use("fileUploadConfig.properties").get("system.file.showUrlPrefix");
        String rst = null;
        if(!systemUrl.endsWith("/") && !prefix.startsWith("/")) {
            rst= systemUrl+ '/'+ prefix;
        } else {
            rst = systemUrl+ prefix;
        }

        return rst;
    }
}
