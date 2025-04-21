import * as crypto from "crypto-js";
import { environment } from "src/environment/environment";

export class CryptoUtils {

    private static readonly cryptoAlgorithm = 'aes-128-ecb';
    private static readonly cryptoKey = environment.cryptoKey;

    public static encodeText(value: string): string {
        let resultString: string = "";

        // Ensure the key is of the correct length (16 bytes for AES-128)
        const key = crypto.enc.Utf8.parse(CryptoUtils.cryptoKey.padEnd(16, ' '));

        // Encrypt
        let tempResult = crypto.AES.encrypt(value, key,
            {
                mode: crypto.mode.ECB,
                padding: crypto.pad.Pkcs7 // PKCS#5 is equivalent to PKCS#7 in CryptoJS,

            }
        );
        resultString = tempResult.toString();

        // Decrypt
        // var bytes = crypto.AES.decrypt(ciphertext, 'secret key 123');
        // var originalText = bytes.toString(crypto.enc.Utf8);

        return resultString;
    }

}