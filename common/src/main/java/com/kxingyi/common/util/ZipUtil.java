package com.kxingyi.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {  
  /** 
   * 添加到压缩文件中 
   * @param out 
   * @param f 
   * @param base 
   * @throws Exception 
   */  
   private void directoryZip(ZipOutputStream out, File f, String base) throws Exception {  
       // 如果传入的是目录  
       if (f.isDirectory()) {  
           File[] fl = f.listFiles();  
           // 创建压缩的子目录  
           out.putNextEntry(new ZipEntry(base + "/"));  
           if (base.length() == 0) {  
               base = "";  
           } else {  
               base = base + "/";  
           }  
           for (int i = 0; i < fl.length; i++) {  
               directoryZip(out, fl[i], base + fl[i].getName());  
           }  
       } else {  
           // 把压缩文件加入rar中  
    	   FileInputStream in = null;
    	   try{
    		   out.putNextEntry(new ZipEntry(base));  
               in = new FileInputStream(f);  
               byte[] bb = new byte[10240];  
               int aa = 0;  
               while ((aa = in.read(bb)) != -1) {  
                   out.write(bb, 0, aa);  
               }  
               out.flush();
    	   }catch(Exception ex){
    		   ex.printStackTrace();
    	   }finally{
    		   if(in!=null){
    			   in.close();
    		   }
    	   }
           
       }  
   }  
 
   /** 
    * 压缩文件 
    *  
    * @param zos 
    * @param file 
    * @throws Exception 
    */  
   private void fileZip(ZipOutputStream zos, File file) throws Exception {  
       if (file.isFile()) {
    	   FileInputStream fis = null;
    	   try{
    		   zos.putNextEntry(new ZipEntry(file.getName()));  
               fis = new FileInputStream(file);  
               byte[] bb = new byte[10240];  
               int aa = 0;  
               while ((aa = fis.read(bb)) != -1) {  
                   zos.write(bb, 0, aa);  
               }  
               zos.flush(); 
    	   }catch(Exception ex){
    		   ex.printStackTrace();
    	   }finally{
    		   if(fis!=null){
    			   fis.close();
    		   }
    	   }
           
       } else {  
           directoryZip(zos, file, "");  
       }  
   }  
 
   /** 
    * 解压缩文件 
    *  
    * @param zis 
    * @param file 
    * @throws Exception 
    */  
   private void fileUnZip(ZipInputStream zis, File file) throws Exception {  
       ZipEntry zip = zis.getNextEntry();  
       if (zip == null)  
           return;  
       String name = zip.getName();  
       File f = new File(file.getAbsolutePath() + "/" + name);  
       if (zip.isDirectory()) {  
           f.mkdirs();  
           fileUnZip(zis, file);  
       } else {  
           f.createNewFile();  
           FileOutputStream fos = null;
           try{
        	   fos = new FileOutputStream(f);  
               byte b[] = new byte[10240];  
               int aa = 0;  
               while ((aa = zis.read(b)) != -1) {  
                   fos.write(b, 0, aa);  
               }  
               fos.flush();
               fileUnZip(zis, file);  
           }catch(Exception ex){
        	   ex.printStackTrace();
           }finally{
        	   if(fos!=null){
        		   fos.close();
        	   }
           }
       }  
   }  
     
   /** 
    * 根据filePath创建相应的目录 
    * @param filePath 
    * @return 
    * @throws IOException 
    */  
   private File mkdirFiles(String filePath) throws IOException{  
       File file = new File(filePath);  
       if(!file.getParentFile().exists()){  
           file.getParentFile().mkdirs();  
       }  
       file.createNewFile();  
         
       return file;  
   }  
 
   /** 
    * 对zipBeforeFile目录下的文件压缩，保存为指定的文件zipAfterFile 
    *  
    * @param zipBeforeFile     压缩之前的文件 
    * @param zipAfterFile      压缩之后的文件 
    */  
   public void zip(String zipBeforeFile, String zipAfterFile) {  
	   FileOutputStream fout = null;
	   ZipOutputStream zout = null;
       try {  
    	   fout =  new FileOutputStream(mkdirFiles(zipAfterFile));
    	   zout = new ZipOutputStream(fout);  
           fileZip(zout, new File(zipBeforeFile));  
       } catch (Exception e) {  
           e.printStackTrace();  
       } finally{
    	   try{
    		   if(zout!=null){
    			   zout.close();
        	   }
        	   if(fout!=null){
        		   fout.close();
        	   }
    	   }catch(Exception ex){
    		   
    	   }
       } 
   }  
 
   /** 
    * 解压缩文件unZipBeforeFile保存在unZipAfterFile目录下 
    *  
    * @param unZipBeforeFile       解压之前的文件 
    * @param unZipAfterFile        解压之后的文件 
    */  
   public void unZip(String unZipBeforeFile, String unZipAfterFile) {  
	   FileInputStream fin = null;
	   ZipInputStream zis = null;
       try {  
    	   fin = new FileInputStream(unZipBeforeFile);
           zis = new ZipInputStream(fin);  
           File f = new File(unZipAfterFile);  
           f.mkdirs();  
           fileUnZip(zis, f);  
           zis.close();  
       } catch (Exception e) {  
           e.printStackTrace();  
       } finally{
    	   try{
    		   if(zis!=null){
        		   zis.close();
        	   }
        	   if(fin!=null){
        		   zis.close();
        	   }
    	   }catch(Exception ex){
    		   
    	   }
       }
   }  
   
   public static void main(String[] args){
	   ZipUtil zip = new ZipUtil();
	   try{
		   //zip.zip("D:\\data\\backup\\4a_db_backup\\cfb\\20150717\\4a_20150717144318533.dbk", "D:\\workspace\\ScheduleServer\\bin\\temp\\4a_20150924145757099.dbk");
		   zip.zip("D:\\workspace\\ScheduleServer\\bin\\test", "D:\\workspace\\ScheduleServer\\bin\\temp\\4a_20150924145757099.dbk");
		   //zip.zip("D:\\data\\backup\\4a_db_backup\\cfb\\20150717\\4a_20150717144318533.dbk", "D:\\workspace\\ScheduleServer\\bin\\temp\\4a_20150924145757099.dbk");
		   zip.unZip("D:\\workspace\\ScheduleServer\\bin\\temp\\4a_20150924145757099.dbk", "D:\\workspace\\ScheduleServer\\bin\\temp\\newDir");
	   }catch(Exception ex){
		   ex.printStackTrace();
	   }
	   
   }
}  

