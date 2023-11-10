import aliyun.KmsCmTest;
import aliyun.OssTest;
import com.aliyuncs.exceptions.ClientException;
import huaweiyun.CsmsTest;
import huaweiyun.ObsTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

public class App {

    enum ToolEnum {
        /**
         *
         */
        ALI_OSS("1", "oss", "aliyun oss testing"),
        HUAWEI_OBS("2", "obs", "huaweiyun obs testing"),
        ALI_KMS_CREDENTAIL("3", "kms-cm", "aliyun credentail manager"),
        HUAWEI_CSMS("4", "csms", "huaweiyun dew csms"),

        ;

        final String index;
        final String code;
        final String desc;

        ToolEnum(String index, String code, String desc) {
            this.index = index;
            this.code = code;
            this.desc = desc;
        }

        public static ToolEnum from(String key) {
            if (key == null) {
                return null;
            }
            key = key.toLowerCase(Locale.ROOT);
            ToolEnum[] values = values();
            for (ToolEnum value : values) {
                String v1 = value.index.toLowerCase(Locale.ROOT);
                String v2 = value.code.toLowerCase(Locale.ROOT);
                if (v1.equals(key)) {
                    return value;
                }
                if (v2.equals(key)) {
                    return value;
                }
            }
            return null;
        }
    }

    public static void main(String[] args) throws Exception {

        ToolEnum toolEnum;
        if (args.length == 0 || (toolEnum = ToolEnum.from(args[0])) == null) {
            System.out.println("invalid params.");
            System.out.println("need params: toolCode toolParams...");
            System.out.println("toolCode:");
            ToolEnum[] values = ToolEnum.values();
            for (ToolEnum value : values) {
                System.out.println("\t  " + value.index + " or " + value.code + " for " + value.desc);
            }
            return;
        }

        String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
        switch (toolEnum) {
            case ALI_OSS:
                OssTest.main(newArgs);
                break;
            case HUAWEI_OBS:
                ObsTest.main(newArgs);
                break;
            case ALI_KMS_CREDENTAIL:
                KmsCmTest.main(newArgs);
                break;
            case HUAWEI_CSMS:
                CsmsTest.main(newArgs);
                break;
            default:
        }
    }

}
