package net.aluink.blowfish;



import org.junit.Assert;
import org.junit.Test;

public class BlowfishTest {

	static long [] keys = {
		0x0000000000000000L,
		0xFFFFFFFFFFFFFFFFL,
		0x3000000000000000L,
		0x1111111111111111L,
		0x0123456789ABCDEFL,
		0x1111111111111111L,
		0x0000000000000000L,
		0xFEDCBA9876543210L,
		0x7CA110454A1A6E57L,
		0x0131D9619DC1376EL,
		0x07A1133E4A0B2686L,
		0x3849674C2602319EL,
		0x04B915BA43FEB5B6L,
		0x0113B970FD34F2CEL,
		0x0170F175468FB5E6L,
		0x43297FAD38E373FEL,
		0x07A7137045DA2A16L,
		0x04689104C2FD3B2FL,
		0x37D06BB516CB7546L,
		0x1F08260D1AC2465EL,
		0x584023641ABA6176L,
		0x025816164629B007L,
		0x49793EBC79B3258FL,
		0x4FB05E1515AB73A7L,
		0x49E95D6D4CA229BFL,
		0x018310DC409B26D6L,
		0x1C587F1C13924FEFL,
		0x0101010101010101L,
		0x1F1F1F1F0E0E0E0EL,
		0xE0FEE0FEF1FEF1FEL,
		0x0000000000000000L,
		0xFFFFFFFFFFFFFFFFL,
		0x0123456789ABCDEFL,
		0xFEDCBA9876543210L
	};
	
	static long [] clearBytes = {
		0x0000000000000000L,
		0xFFFFFFFFFFFFFFFFL,
		0x1000000000000001L,
		0x1111111111111111L,
		0x1111111111111111L,
		0x0123456789ABCDEFL,
		0x0000000000000000L,
		0x0123456789ABCDEFL,
		0x01A1D6D039776742L,
		0x5CD54CA83DEF57DAL,
		0x0248D43806F67172L,
		0x51454B582DDF440AL,
		0x42FD443059577FA2L,
		0x059B5E0851CF143AL,
		0x0756D8E0774761D2L,
		0x762514B829BF486AL,
		0x3BDD119049372802L,
		0x26955F6835AF609AL,
		0x164D5E404F275232L,
		0x6B056E18759F5CCAL,
		0x004BD6EF09176062L,
		0x480D39006EE762F2L,
		0x437540C8698F3CFAL,
		0x072D43A077075292L,
		0x02FE55778117F12AL,
		0x1D9D5C5018F728C2L,
		0x305532286D6F295AL,
		0x0123456789ABCDEFL,
		0x0123456789ABCDEFL,
		0x0123456789ABCDEFL,
		0xFFFFFFFFFFFFFFFFL,
		0x0000000000000000L,
		0x0000000000000000L,
		0xFFFFFFFFFFFFFFFFL
	};
	
	static long [] cipherBytes = {
		0x4EF997456198DD78L,
		0x51866FD5B85ECB8AL,
		0x7D856F9A613063F2L,
		0x2466DD878B963C9DL,
		0x61F9C3802281B096L,
		0x7D0CC630AFDA1EC7L,
		0x4EF997456198DD78L,
		0x0ACEAB0FC6A0A28DL,
		0x59C68245EB05282BL,
		0xB1B8CC0B250F09A0L,
		0x1730E5778BEA1DA4L,
		0xA25E7856CF2651EBL,
		0x353882B109CE8F1AL,
		0x48F4D0884C379918L,
		0x432193B78951FC98L,
		0x13F04154D69D1AE5L,
		0x2EEDDA93FFD39C79L,
		0xD887E0393C2DA6E3L,
		0x5F99D04F5B163969L,
		0x4A057A3B24D3977BL,
		0x452031C1E4FADA8EL,
		0x7555AE39F59B87BDL,
		0x53C55F9CB49FC019L,
		0x7A8E7BFA937E89A3L,
		0xCF9C5D7A4986ADB5L,
		0xD1ABB290658BC778L,
		0x55CB3774D13EF201L,
		0xFA34EC4847B268B2L,
		0xA790795108EA3CAEL,
		0xC39E072D9FAC631DL,
		0x014933E0CDAFF6E4L,
		0xF21E9A77B71C49BCL,
		0x245946885754369AL,
		0x6B5C5A9C5D9E0A5AL
	};
	
	@Test
	public void runTest(){
		byte [] key = new byte[8];
		int [] in = new int[2];
		int [] out = new int[2];
		System.out.println("Key                ClearBytes         Expected CipherBytes   Received Cipherbytes");
		for(int i = 0;i < keys.length;i++){
			long tmp = keys[i];
			for(int j = 7;j >= 0;j--){
				key[j] = (byte) (tmp & 0xFF);
				tmp >>= 8;
			}
			
			System.out.printf("0x%016X 0x%016X 0x%016X ...", keys[i], clearBytes[i], cipherBytes[i]);
			runTest(key, clearBytes[i], cipherBytes[i]);
			System.out.println("     Passed!");
		}
	}

	private void runTest(byte[] key, long clear, long cipher) {
		int [] in = new int[2];
		int [] out = new int[2];
		in[1] = (int) (clear & 0xFFFFFFFFL);
		in[0] = (int) (clear >> 32 & 0xFFFFFFFF);
		out[1] = (int) (cipher & 0xFFFFFFFFL);
		out[0] = (int) (cipher >> 32 & 0xFFFFFFFF);
		
		Blowfish bf = new Blowfish(key);
		int [] v = bf.encipher(in[0], in[1]);
		long vL = bf.encipher(clear);
		System.out.printf(" 0x%08X%08X 0x%016X", v[0], v[1], vL);
		Assert.assertEquals(v[0], out[0]);
		Assert.assertEquals(v[1], out[1]);
		Assert.assertEquals(vL, cipher);
		
		v = bf.decipher(v[0], v[1]);
		vL = bf.decipher(vL);
		Assert.assertEquals(v[0], in[0]);
		Assert.assertEquals(v[1], in[1]);
		Assert.assertEquals(vL, clear);
	}
	
}
