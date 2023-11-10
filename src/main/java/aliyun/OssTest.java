package aliyun;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class OssTest {


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
            System.out.println("oss need params: toolCode endpoint ak sk token bucketName uploadFile");
            System.out.println("example: java -jar cloud-test-tool.jar 1 oss-cn-shenzhen.aliyuncs.com STS.NSxxxxxM FjxxxxxxxxxxxUM CAxxxxxxxxxxxxxxxxxxxxxxAA pxxxe 404.png");
            return;
        }

        String endPoint = args[0];
        String ak = args[1];
        String sk = args[2];
        String token = args[3];
        String bucketName = args[4];
        String uploadFile = args[5];

        // 创建ObsClient实例
        DefaultCredentialProvider credentialProvider = new DefaultCredentialProvider(ak, sk, token);
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        OSSClient ossClient = new OSSClient(endPoint, credentialProvider, clientConfiguration);

        // 使用访问OBS
        try {
            int i = uploadFile.lastIndexOf(".");
            String objKey = UUID.randomUUID().toString();
            if (i > 0 && i < uploadFile.length() - 1) {
                objKey = objKey + "." + uploadFile.substring(i + 1);
            }

            // 上传
            System.out.println("upload file " + uploadFile + " to bucket " + bucketName + " object " + objKey);
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, objKey, Files.newInputStream(Paths.get(uploadFile)));
            System.out.println("upload completed");

            // 下载
            OSSObject ossObject = ossClient.getObject(bucketName, objKey);
            InputStream content = ossObject.getObjectContent();
            String fileBase = System.getProperty("user.dir");
            File out = new File(fileBase, objKey);
            System.out.println("download file to " + out.getCanonicalPath());
            if (content != null) {
                writeToFile(out, content, false);
            }
        } finally {
            // 关闭obsClient，全局使用一个ObsClient客户端的情况下，不建议主动关闭ObsClient客户端
            ossClient.shutdown();
        }
    }
}
