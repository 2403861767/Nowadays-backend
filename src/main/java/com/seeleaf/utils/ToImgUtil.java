package com.seeleaf.utils;
 
import sun.misc.BASE64Decoder;
 
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
 
public class ToImgUtil {
    private String base;
 
    public ToImgUtil() {
    }
 
    public ToImgUtil(String base) {
        this.base = base;
    }
 
    public String getBase() {
        return base;
    }
 
    public void setBase(String base) {
        this.base = base;
    }
 
    /**
     * 为文件重新命名，命名规则为当前系统时间毫秒数
     *
     * @return string
     */
    private static String getFileNameNew() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return fmt.format(new Date());
    }
    public static ToImgUtil ToImg(String base){
        //获取年月日
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //将年、月、日存入数组
        String[] time = sdf.format(new Date()).split("-");
        // 文件保存地址 默认地址为xxxx/年/月/日
        String destDir = "xxxx/"+time[0]+"/"+time[1]+"/"+time[2];;
        /* response.setHeader("Access-Control-Allow-Origin", "*");
         response.setHeader("Access-Control-Allow-Methods", "POST");*/
        base=base.replaceAll("data:image/png;base64,","");
        base=base.replaceAll("data:image/jpg;base64,","");
        base=base.replaceAll("data:image/jpeg;base64,","");
        BASE64Decoder decoder =  new BASE64Decoder();
        byte[] imageByte = null;
        try{
            imageByte = decoder.decodeBuffer(base);
            for (int i = 0; i < imageByte.length; ++i) {
                if (imageByte[i] < 0) {// 调整异常数据
                    imageByte[i] += 256;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (imageByte.length>0) {
            try {
                String filepath = destDir;
                File destfile = new File( filepath);
                if (!destfile.exists()) {
                    destfile.mkdirs();
                }
                //文件新名称
                String fileNameNew = getFileNameNew() + ".png";
                File f = new File(destfile.getAbsoluteFile() + File.separator + fileNameNew);
                // 将字符串转换成二进制，用于显示图片
                // 将上面生成的图片格式字符串 imgStr，还原成图片显示
                InputStream in = new ByteArrayInputStream(imageByte);
                FileOutputStream fos = new FileOutputStream(f);
                // BufferedOutputStream bos = new BufferedOutputStream(fos);
                byte[] buf = new byte[1024];
                int length;
                length = in.read(buf, 0, buf.length);
 
                while (length != -1) {
                    fos.write(buf,0,length);
                    length = in.read(buf);
                }
                fos.flush();
                fos.close();
                in.close();
                return new ToImgUtil("图片地址："+destDir+",图片名称"+fileNameNew);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
            }
        }
        return null;
    }
 
 
 
}