package huaweiyun;

import com.obs.services.ObsClient;
import com.obs.services.model.ObsObject;
import com.obs.services.model.PutObjectResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class ObsTest {

//    非洲-约翰内斯堡	af-south-1	obs.af-south-1.myhuaweicloud.com	HTTPS/HTTP
//    华北-北京四	cn-north-4	obs.cn-north-4.myhuaweicloud.com	HTTPS/HTTP
//    华北-北京一	cn-north-1	obs.cn-north-1.myhuaweicloud.com	HTTPS/HTTP
//    华北-乌兰察布一	cn-north-9	obs.cn-north-9.myhuaweicloud.com	HTTPS/HTTP
//    华东-上海二	cn-east-2	obs.cn-east-2.myhuaweicloud.com	HTTPS/HTTP
//    华东-上海一	cn-east-3	obs.cn-east-3.myhuaweicloud.com	HTTPS/HTTP
//    华南-广州	cn-south-1	obs.cn-south-1.myhuaweicloud.com	HTTPS/HTTP
//    华南-广州-友好用户环境	cn-south-4	obs.cn-south-4.myhuaweicloud.com	HTTPS/HTTP
//    拉美-墨西哥城二	la-north-2	obs.la-north-2.myhuaweicloud.com	HTTPS/HTTP
//    拉美-墨西哥城一	na-mexico-1	obs.na-mexico-1.myhuaweicloud.com	HTTPS/HTTP
//    拉美-圣保罗一	sa-brazil-1	obs.sa-brazil-1.myhuaweicloud.com	HTTPS/HTTP
//    拉美-圣地亚哥	la-south-2	obs.la-south-2.myhuaweicloud.com	HTTPS/HTTP
//    土耳其-伊斯坦布尔	tr-west-1	obs.tr-west-1.myhuaweicloud.com	HTTPS/HTTP
//    西南-贵阳一	cn-southwest-2	obs.cn-southwest-2.myhuaweicloud.com	HTTPS/HTTP
//    亚太-曼谷	ap-southeast-2	obs.ap-southeast-2.myhuaweicloud.com	HTTPS/HTTP
//    亚太-新加坡	ap-southeast-3	obs.ap-southeast-3.myhuaweicloud.com	HTTPS/HTTP
//    中东-利雅得	me-east-1	obs.me-east-1.myhuaweicloud.com	HTTPS/HTTP
//    中国-香港	ap-southeast-1	obs.ap-southeast-1.myhuaweicloud.com	HTTPS/HTTP


    public static void writeToFile(File file, InputStream inputStream, boolean append) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        try (FileOutputStream fis = new FileOutputStream(file, append)) {
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes, 0, bytes.length)) != -1) {
                fis.write(bytes, 0, len);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 6) {
            System.out.println("invalid params.");
            System.out.println("obs need params: toolCode endpoint ak sk token bucketName uploadFile");
            return;
        }

        String endPoint = args[0];
        String ak = args[1];
        String sk = args[2];
        String token = args[3];
        String bucketName = args[4];
        String uploadFile = args[5];

        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, token, endPoint);

        // 使用访问OBS
        try {
            int i = uploadFile.lastIndexOf(".");
            String objKey = UUID.randomUUID().toString();
            if (i > 0 && i < uploadFile.length() - 1) {
                objKey = objKey + "." + uploadFile.substring(i + 1);
            }

            // 上传
            System.out.println("upload file " + uploadFile + " to bucket " + bucketName + " object " + objKey);
            PutObjectResult putObjectResult = obsClient.putObject(bucketName, objKey, Files.newInputStream(Paths.get(uploadFile)));
            System.out.println("upload completed");

            // 下载
            ObsObject obsObject = obsClient.getObject(bucketName, objKey);
            InputStream content = obsObject.getObjectContent();
            String fileBase = System.getProperty("user.dir");
            File out = new File(fileBase, objKey);
            System.out.println("download file to " + out.getCanonicalPath());
            if (content != null) {
                writeToFile(out, content, false);
            }
        } finally {
            // 关闭obsClient，全局使用一个ObsClient客户端的情况下，不建议主动关闭ObsClient客户端
            obsClient.close();
        }
    }
}
