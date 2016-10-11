package hellogit;

/****************************************************************
 * Copyright(C),2009-2010, 浙江维尔生物识别技术股份有限公司
 * File name:
 * Author: kf		Version: 1.0.0.1		Date: 2010-03-26
 * Description: JZT998系列指纹设备驱动JNI接口
 * History:
 * 1. Date:
 *    Author:
 *    Modification:  
 ****************************************************************/


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class TestFinger {
	String encoding = "gbk";
	public static final String type = "1001";// 指纹特征
	private static String msg; // 返回信息
	private String serverIp;
	private int serverProt;

	private String pkgSend;
	private String pkgReceive;

	public String getMsg() {
		return msg;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public void setServerProt(int serverProt) {
		this.serverProt = serverProt;
	}

	// /**
	// * 指纹调用且比对接口
	// *
	// * @param trade
	// * @param orgid
	// * @param tellerid
	// * @return
	// * @throws Exception
	// */
	// public static boolean fingerValidate(String orgid, String tellerid)
	// throws Exception {
	// String psif = trade.pushInfoWithoutButton("请输入指纹...");
	// String finger = "";
	// try {
	// IFp fp = trade.getDeviceManager().getFp();
	// finger = fp.getFingerPrint();
	// trade.closeInfo(psif);
	// } catch (Exception e) {
	// trade.closeInfo(psif);
	// trade.pushError("错误:" + e, true);
	// return false;
	// }
	// trade.putStoreData("VTZW", finger);
	// LogUtil.debug(trade, "机构号：" + orgid + "\n" + "柜员号：" + tellerid + "\n"
	// + "当前柜员的指纹信息:" + finger);
	// Map fingerMap = getConfigMap(trade, "FINGER");
	// int iRet = -1;
	// FingerComm JztDev;
	// JztDev = new FingerComm();
	// JztDev.setServerIp((String) fingerMap.get("ipaddress"));
	// JztDev
	// .setServerProt(Integer.parseInt((String) fingerMap
	// .get("ipport")));
	// /* 先查询柜员状态 */
	// iRet = JztDev.wlGetTellerState(type, orgid, tellerid);
	// if (iRet != 0) {
	// return false;
	// }
	// /* 指纹比对 */
	// iRet = JztDev.wlFingerMatch(type, orgid, tellerid, finger.substring(8,
	// finger.length()));
	// if (iRet != 0) {
	//
	// return false;
	// } else {
	// return true;
	// }
	//
	// }

	public int tcpComm() throws UnsupportedEncodingException {
		Socket cSocket = null;
		InputStream in = null;
		OutputStream out = null;
		byte pkgsend[], pkgrev[], ctmp[];
		int ilen;
		String strTmp;
		pkgsend = new byte[1024];
		pkgrev = new byte[512];

		for (int i = 0; i < 1024; i++)
			pkgsend[i] = 0;
		for (int i = 0; i < 512; i++)
			pkgrev[i] = 0;
		ctmp = pkgSend.getBytes(encoding);
		ilen = ctmp.length;
		pkgsend[0] = 'F';
		pkgsend[1] = 'P';
		String slen = "0000" + (ilen + 7);
		pkgsend[2] = slen.getBytes(encoding)[slen.getBytes(encoding).length - 4];
		pkgsend[3] = slen.getBytes(encoding)[slen.getBytes(encoding).length - 3];
		pkgsend[4] = slen.getBytes(encoding)[slen.getBytes(encoding).length - 2];
		pkgsend[5] = slen.getBytes(encoding)[slen.getBytes(encoding).length - 1];
		pkgsend[6] = 'C';

		for (int i = 0; i < ilen; i++)
			pkgsend[7 + i] = ctmp[i];

		try {
			cSocket = new Socket(serverIp, serverProt);
			cSocket.setSoTimeout(5000);
			in = cSocket.getInputStream();
			out = cSocket.getOutputStream();
			for (int i = 0; i < ilen + 7; i++)
				out.write(pkgsend[i]);
			for (int i = 0; i < 7; i++)
				pkgrev[i] = (byte) in.read();
			strTmp = new String(pkgrev, encoding);
			ilen = Integer.parseInt(strTmp.substring(2, 6)) - 7;
			for (int i = 0; i < ilen; i++)
				pkgrev[i] = (byte) (in.read());
			// in.close();
			// out.close();
			// cSocket.close();
			strTmp = new String(pkgrev, encoding);
		} catch (Exception e) {
			msg = "与服务器通讯失败!";
			return -1;
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				msg = "与服务器通讯失败!";
				return -1;
			}
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				msg = "与服务器通讯失败!";
				return -1;
			}
			try {
				if (cSocket != null)
					cSocket.close();
			} catch (IOException e) {
				msg = "与服务器通讯失败!";
				return -1;
			}
		}
		try {
			if (!strTmp.substring(0, 4).equals("0000")) {
				msg = strCut(strTmp, 4, 44);
				return -2;
			}
		} catch (Exception e) {
			msg = "与服务器通讯失败";
			return -1;
		}
		pkgReceive = strCut(strTmp, 0, ilen);
		return 0;
	}

	public String strSet(String s, int len) throws UnsupportedEncodingException {
		String strA = s;
		String strB = "";
		int i = 0;
		int iLen = s.getBytes(encoding).length;
		for (int k = 0; k < len - iLen; k++) {
			strA = strA + " ";
		}
		while (strB.getBytes(encoding).length < len) {
			strB += strA.substring(i, i + 1);
			if (strB.getBytes(encoding).length > len) {
				strB = strA.substring(0, i);
				strB += " ";
				break;
			}
			i++;
		}
		return strB;
	}

	public String strCut(String s, int beginIndex, int endIndex) throws UnsupportedEncodingException {
		byte b[];
		int i = 0, l;

		if (s.getBytes(encoding).length < endIndex || beginIndex >= endIndex)
			return "";
		l = endIndex - beginIndex;
		b = new byte[l];
		for (i = 0; i < l; i++)
			b[i] = s.getBytes(encoding)[beginIndex + i];
		return (new String(b, encoding)).trim();
	}

	public int wlGetTellerState(String SysCode, String OrgCode, String userCode) throws UnsupportedEncodingException {
		int iret;

		pkgSend = "2610" + strSet(SysCode, 10) + strSet(OrgCode, 20)
				+ strSet(userCode, 20);
		iret = tcpComm();
		if (iret != 0)
			return iret;
		if (strCut(pkgReceive, 44, 45).equals("1")) {
			msg = "柜员信息不存在";
			return -2;
		} else if (strCut(pkgReceive, 44, 45).equals("2")) {
			msg = "柜员机构不符";
			return -2;
		} else if (strCut(pkgReceive, 44, 45).equals("0")) {
			if (strCut(pkgReceive, 45, 46).equals("2")) {
				// msg = "需要密码比对";
				return 0;
			}
		} else {
			msg = "未知错误";
			return -2;
		}
		return 0;
	}

	public int wlFingerMatch(String SysCode, String OrgCode, String userCode,
			String tzString) throws UnsupportedEncodingException {
		int iret;
		// 指纹比对
		// String tzStrig =
		// "67=8;0>7:34?2887=0;4017769767;>5725:;;:>>>;6=18<=;5?>:5<421=108>1931=0<5851=7:716<:819:>;0>?>27<>;<32237772?483165>?9:46<5777<;<2<02;0<;771=2621>1:7=1141=0?02<75>309<>2?7<9:2:4>66?16??;>5:1:=?4=<<3<423>22=2<06283:;661=243?<:;:3><8;62;571325172941>870:>26>4?413;;<753=9245120:8<?45<?5><61:1931=0<585==;:>7;034813729767;>5725:;;:>>>;6=18<=;5?>:5<421=108>1931=0<585==;:>7;034813729767;>5725:;;:>>>;6=18<=;5?>:5<421=108>1931=0<585==;:>7;034813729767;>5725:;;:>>>;6=18<=;5?>:5<421=108>1931=0<585==;:>7;034813729767;29";
		pkgSend = "1009" + strSet(SysCode, 10) + strSet(OrgCode, 20)
				+ strSet(userCode, 20) + strSet("1", 1) + strSet(tzString, 512)
				+ strSet("passwd", 32) + strSet("0000000", 25);
		iret = tcpComm();
		if (iret != 0)
			return iret;
		msg = "指纹比对通过";
		return 0;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {

		int iRet = -1;
		String tzStrig = "0102000067=8?:>7;76?0817408431<7=9<6<;1582>:0;5>1>06617<2;>?5::<;2:=:07>>98362;5?54?283;66?<49?=?7:8:53;:<84657030680?5648<7;:661470;=49=:2908737?<87?68002545:=4<:;?:1>><==:0=;7=2;=:?=><73<41=1?;:?7364>56642>2;23==<?6785><31717726?73735004>:3?95=1:72;10?;3538<<=59=1=0;=86<:<;:;?8?99466=:=;:57:<>34255<5386??5?0=3><;?;62:8<><540;250754;:=;94<1:725=2?<1=885=81>8=40;:>6:9>90?4:>;86;70<2<;0;46<357?5=0?1;90?4>>9?136?<;;:;4;=311;?6=9980=<=:<?08307;2041:4548=64169889===85>2;?>86<=96?712>23;=2:02>3?6;6>>89=48307;2041:45481:47";
		TestFinger JztDev;
		JztDev = new TestFinger();
		/*********************** 指纹设备接口调用示例 **************************/
		/*** USB设备 ***/
		JztDev.setServerIp("132.1.40.172");
		JztDev.setServerProt(9000);
		/* 先查询柜员状态 */
		iRet = JztDev.wlGetTellerState("1001", "3052249", "3131");
		if (iRet != 0) {
			System.out.println("返 回 值：" + iRet);
			System.out.println("返回信息：" + JztDev.getMsg());
			System.out.println("++++++ End ++++++++++");
			return;
		} else {
			System.out.println("返 回 值1：" + iRet);
			System.out.println("返回信息1：" + JztDev.getMsg());
			System.out.println("++++++ End ++++++++++");
		}
		/* 指纹比对 */
		iRet = JztDev.wlFingerMatch("1001", "3052249", "3131", tzStrig
				.substring(8, tzStrig.length()));
		System.out.println("返 回 值：" + iRet);
		System.out.println("返回信息：" + JztDev.getMsg());
		System.out.println("++++++ End ++++++++++");
		
//		//右补零  
//		System.out.println(JztDev.strSet("1234", 3));
//		
//		//截取位数，不符合时返回空字符串
//		System.out.println(JztDev.strCut("1", 1, 3));
	}
}