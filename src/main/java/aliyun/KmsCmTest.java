package aliyun;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.BasicCredentials;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.kms.model.v20160120.GetSecretValueRequest;
import com.aliyuncs.kms.model.v20160120.GetSecretValueResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class KmsCmTest {

    public static void main(String[] args) throws ClientException {
        if (args.length < 5) {
            System.out.println("invalid params.");
            System.out.println("kms-cm need params: toolCode regionId ak sk token secretName");
            return;
        }

        String regionId = args[0];
        String ak = args[1];
        String sk = args[2];
        String token = args[3];
        String secretName = args[4];

        IClientProfile profile = DefaultProfile.getProfile(regionId, ak, sk, token);
        DefaultAcsClient kmsClient = new DefaultAcsClient(profile);

        GetSecretValueRequest request = new GetSecretValueRequest();
        request.setProtocol(ProtocolType.HTTPS);
        request.setAcceptFormat(FormatType.JSON);
        request.setMethod(MethodType.POST);
        request.setSecretName(secretName);
        GetSecretValueResponse response = kmsClient.getAcsResponse(request);


        System.out.println();
        System.out.println("get credential of name " + response.getSecretName());
        System.out.printf("credential version id: %s%n", response.getVersionId());
        System.out.printf("credential value: %s%n", response.getSecretData());

    }
}
