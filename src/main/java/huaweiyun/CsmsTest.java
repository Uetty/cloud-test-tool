package huaweiyun;

import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.csms.v1.CsmsClient;
import com.huaweicloud.sdk.csms.v1.model.ShowSecretVersionRequest;
import com.huaweicloud.sdk.csms.v1.model.ShowSecretVersionResponse;
import com.huaweicloud.sdk.csms.v1.model.Version;

public class CsmsTest {

//    华北-北京二	cn-north-2	kms.cn-north-2.myhuaweicloud.com	HTTPS
//    华北-北京四	cn-north-4	kms.cn-north-4.myhuaweicloud.com	HTTPS
//    华北-北京一	cn-north-1	kms.cn-north-1.myhuaweicloud.com	HTTPS
//    华北-乌兰察布一	cn-north-9	kms.cn-north-9.myhuaweicloud.com	HTTPS
//    华东-上海二	cn-east-2	kms.cn-east-2.myhuaweicloud.com	HTTPS
//    华东-上海一	cn-east-3	kms.cn-east-3.myhuaweicloud.com	HTTPS
//    华南-广州	cn-south-1	kms.cn-south-1.myhuaweicloud.com	HTTPS
//    华南-深圳	cn-south-2	kms.cn-south-2.myhuaweicloud.com	HTTPS
//    拉美-墨西哥城二	la-north-2	kms.la-north-2.myhuaweicloud.com	HTTPS
//    拉美-圣地亚哥	la-south-2	kms.la-south-2.myhuaweicloud.com	HTTPS
//    南非-约翰内斯堡	af-south-1	kms.af-south-1.myhuaweicloud.com	HTTPS
//    欧洲-都柏林	eu-west-101	kms.eu-west-101.myhuaweicloud.com	HTTPS
//    土耳其-伊斯坦布尔	tr-west-1	kms.tr-west-1.myhuaweicloud.com	HTTPS
//    西南-贵阳一	cn-southwest-2	kms.cn-southwest-2.myhuaweicloud.com	HTTPS
//    亚太-曼谷	ap-southeast-2	kms.ap-southeast-2.myhuaweicloud.com	HTTPS
//    亚太-新加坡	ap-southeast-3	kms.ap-southeast-3.myhuaweicloud.com	HTTPS
//    中东-阿布扎比-OP5	ae-ad-1	kms.ae-ad-1.myhuaweicloud.com	HTTPS
//    中东-利雅得	me-east-1	kms.me-east-1.myhuaweicloud.com	HTTPS
//    中国-香港	ap-southeast-1	kms.ap-southeast-1.myhuaweicloud.com	HTTPS

    public static void main(String[] args) {
        if (args.length < 6) {
            System.out.println("invalid params.");
            System.out.println("csms need params: toolCode endpoint ak sk token projectId secretName");
            return;
        }

        String endPoint = args[0];
        String ak = args[1];
        String sk = args[2];
        String token = args[3];
        String projectId = args[4];
        String secretName = args[5];

        BasicCredentials auth = new BasicCredentials()
                .withAk(ak)
                .withSk(sk)
                .withSecurityToken(token)
                .withProjectId(projectId);

        CsmsClient csmsClient = CsmsClient.newBuilder().withCredential(auth).withEndpoint(endPoint).build();

        ShowSecretVersionRequest showSecretVersionRequest = new ShowSecretVersionRequest().withSecretName(secretName)
                .withVersionId("latest");

        ShowSecretVersionResponse version = csmsClient.showSecretVersion(showSecretVersionRequest);

        Version latestVersion = version.getVersion();
        System.out.println();
        System.out.println("get credential of name " + secretName);
        System.out.printf("credential latest version id: %s%n", latestVersion.getVersionMetadata().getId());
        System.out.printf("credential value: %s%n", latestVersion.getSecretString());

    }
}
