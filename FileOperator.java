package fileReader;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import org.omg.CORBA.PUBLIC_MEMBER;

public class FileOperator
{
    public static int ENCODE_MODE = 1;
    public static int DECODE_MODE = 2;
    static String readFilePath = "C:\\Users\\zWX459597\\Pictures\\test.png";
    static String writeFilePath = "C:\\Users\\zWX459597\\Pictures\\outputFile.png";
    static String decodeFilePath = "C:\\Users\\zWX459597\\Pictures\\outputFile_result.txt";

    public static void main( String[] args )
    {
        
        BufferedImage bi = new BufferedImage( 15, 10, BufferedImage.TYPE_USHORT_565_RGB );
       
        byte[] b = readFile( readFilePath);
        String s = bytes2HexString( b ); //编码
        System.out.println(s.length());
        writeFile( s.getBytes(), decodeFilePath); //解码
        byte[] b1 = hexString2Bytes( s );
        writeFile( b1, writeFilePath );
        
    }
    
    public static byte[] readFile(String filePath)
    {
        StringBuffer sbBuffer = new StringBuffer();
        File f = new File(filePath);
        System.out.println("------------------------------------file length:" + f.length());
        byte[] b = new byte[(int)f.length()]; 
        try
        {
            FileInputStream fis = new FileInputStream( f );
            BufferedInputStream biStream = new BufferedInputStream( fis );
            int l = 0 ;
            int count = 0;
            while((l=biStream.read(b))!= -1){     
                sbBuffer.append(new String(b));
                System.out.println(sbBuffer.capacity() +  " -- " +  count + "  " + b[0] + b[1]+ b[2]);
            }
            biStream.close();
            fis.close();
        } catch ( Exception e )
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return b;
    }
    
    
    /*
     * 字节数组转16进制字符串
     */
    public static String bytes2HexString(byte[] b) {
        String r = "";
        
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            r += hex.toUpperCase();
        }
        return r;
    }
    
    public static void writeFile(byte[] s,String fPath)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream( fPath );
            fos.write( s ,0,s.length);
            fos.close();
        } catch ( Exception e )
        {
            // TODO: handle exception
        }
    }
    
    /*
     * 16进制字符串转字节数组
     */
    public static byte[] hexString2Bytes(String hex) {
        if ((hex == null) || (hex.equals(""))){
            return null;
        }
        else if (hex.length()%2 != 0){
            return null;
        }
        else{                
            hex = hex.toUpperCase();
            int len = hex.length()/2;
            byte[] b = new byte[len];
            char[] hc = hex.toCharArray();
            for (int i=0; i<len; i++){
                int p=2*i;
                b[i] = (byte) (charToByte(hc[p]) << 4 | charToByte(hc[p+1]));
            }
          return b;
        }           
    }
    
    /*
     * 字符转换为字节
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
     }
}
