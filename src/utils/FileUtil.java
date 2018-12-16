package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


/**
 * @author chenhuan001
 *
 */
public class FileUtil {
    String newline = "\r\n";//windows

    /**
     * 写入文件,末尾自动添加\r\n
     * utf-8  追加
     * @param path
     * @param str
     */
    public static void writeLog(String path, String str)
    {
        try
        {
            File file = new File(path);
            if(!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(file); //true表示追加
            StringBuffer sb = new StringBuffer();
            sb.append(str + "\r\n");
            out.write(sb.toString().getBytes("utf-8"));//
            out.close();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getStackTrace());
        }
    }

    public static void writeFile(String path, String str){
        try {
            FileWriter out = new FileWriter(path,true);
            out.write(str);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入文件,末尾自动添加\r\n
     * @param path
     * @param str
     */
    public static void writeLog(String path, String str, boolean is_append, String encode)
    {
        try
        {
            File file = new File(path);
            if(!file.exists())
                file.createNewFile();
            FileOutputStream out = new FileOutputStream(file, is_append); //true表示追加
            StringBuffer sb = new StringBuffer();
            sb.append(str + "\r\n");
            out.write(sb.toString().getBytes(encode));//
            out.close();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getStackTrace());
        }
    }
    /**
     * 整个文件以string放回，添加\r\n换行
     * @param path
     * @return
     */
    public static String readLogByString(String path)
    {
        StringBuffer sb=new StringBuffer();
        String tempstr=null;
        try {
            File file=new File(path);
            if(!file.exists())
                throw new FileNotFoundException();
            FileInputStream fis=new FileInputStream(file);
            BufferedReader br=new BufferedReader(new InputStreamReader(fis, "utf-8"));
            while((tempstr=br.readLine())!=null) {
                sb.append(tempstr + "\r\n");
            }
        } catch(IOException ex) {
            System.out.println(ex.getStackTrace());
        }
        return sb.toString();
    }

    /**
     * 加入编码
     * 整个文件以string放回，添加\r\n换行
     * @param path
     * @return
     */
    public static String readLogByStringAndEncode(String path, String encode)
    {
        StringBuffer sb=new StringBuffer();
        String tempstr=null;
        try {
            File file=new File(path);
            if(!file.exists())
                throw new FileNotFoundException();
            FileInputStream fis=new FileInputStream(file);
            BufferedReader br=new BufferedReader(new InputStreamReader(fis, encode));
            while((tempstr=br.readLine())!=null) {
                sb.append(tempstr + "\r\n");
            }
        } catch(IOException ex) {
            System.out.println(ex.getStackTrace());
        }
        return sb.toString();
    }

    /**
     * 按行读取文件，以list<String>的形式返回
     * @param path
     * @return
     */
    public static List<String> readLogByList(String path, HashSet<String> set) {
        List<String> lines = new ArrayList<String>();
        String tempstr = null;
        try {
            File file = new File(path);
            if(!file.exists()) {
                throw new FileNotFoundException();
            }
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, "gbk"));
            while((tempstr = br.readLine()) != null) {
                if(tempstr != null && tempstr.length() > 0) {
                    lines.add(tempstr.toString());
                    set.add(tempstr.toString());
                }
            }
        } catch(IOException ex) {
            System.out.println(ex.getStackTrace());
        }
        return lines;
    }


    /**
     * 创建目录
     * @param dir_path
     */
    public static void mkDir(String dir_path) {
        File myFolderPath = new File(dir_path);
        try {
            if (!myFolderPath.exists()) {
                myFolderPath.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建文件
     * @param file_path
     */
    public static void createNewFile(String file_path) throws IOException {
        File myFilePath = new File(file_path);
        if (!myFilePath.exists()) {
            myFilePath.createNewFile();
        }
        FileWriter fileWriter =new FileWriter(myFilePath);
        fileWriter.write("");
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * 递归删除文件或者目录
     * @param file_path
     */
    public static void deleteEveryThing(String file_path) {
        try{
            File file=new File(file_path);
            if(!file.exists()){
                return ;
            }
            if(file.isFile()){
                file.delete();
            }else{
                File[] files = file.listFiles();
                for(int i=0;i<files.length;i++){
                    String root=files[i].getAbsolutePath();//得到子文件或文件夹的绝对路径
                    deleteEveryThing(root);
                }
                file.delete();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 得到一个文件夹下所有文件
     */
    public static List<String> getAllFileNameInFold(String fold_path) {
        List<String> file_paths = new ArrayList<String>();

        LinkedList<String> folderList = new LinkedList<String>();
        folderList.add(fold_path);
        while (folderList.size() > 0) {
            File file = new File(folderList.peekLast());
            folderList.removeLast();
            File[] files = file.listFiles();
            ArrayList<File> fileList = new ArrayList<File>();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    folderList.add(files[i].getPath());
                } else {
                    fileList.add(files[i]);
                }
            }
            for (File f : fileList) {
                file_paths.add(f.getAbsoluteFile().getPath());
            }
        }
        return file_paths;
    }

    public static void main(String[] args) {
//        String path = "C:\\Users\\chenhuan001\\workspace\\CrawlSinaBySelenium\\src";
//        List<String> file_paths = getAllFileNameInFold(path);
//        for(String file_path : file_paths) {
//            System.out.println(file_path);
//        }
//        deleteEveryThing("C:\\Users\\chenhuan001\\Desktop\\testDelete.txt");
        // TODO Auto-generated method stub
//        List<Document> docs = readDocsFromFile("Data/user_program_data.txt");
//        System.out.println(docs.size());
//        for (int i = 0; i < docs.size(); i++) {
//            System.out.println(docs.toString());
//        }
        //mkDir("tmp_dir");
        //createNewFile("tmp_dir/new_file1.txt");
        //deleteEveryThing("save.arff");
        File directory = new File("");//设定为当前文件夹
        try{
            System.out.println(directory.getCanonicalPath());//获取标准的路径
            System.out.println(directory.getAbsolutePath());//获取绝对路径
        }catch(Exception e){

        }
    }

}
