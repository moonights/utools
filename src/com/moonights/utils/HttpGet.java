package com.moonights.utils;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Description: 将指定的HTTP网络资源在本地以文件形式存放
 */
public class HttpGet {

	public final static boolean DEBUG = true;// 调试用

	private static int BUFFER_SIZE = 8096;// 缓冲区大小

	private Vector vDownLoad = new Vector();// URL列表

	private Vector vFileList = new Vector();// 下载后的保存文件名列表

	/**
	 * 构造方法
	 */
	public HttpGet() {

	}

	/**
	 * 清除下载列表
	 */
	public void resetList() {
		vDownLoad.clear();
		vFileList.clear();
	}

	/**
	 * 增加下载列表项
	 * 
	 * @param url
	 *            String
	 * @param filename
	 *            String
	 */
	public void addItem(String url, String filename) {
		vDownLoad.add(url);
		vFileList.add(filename);
	}

	/**
	 * 根据列表下载资源
	 */
	public void downLoadByList() {
		String url = null;
		String filename = null;

		// 按列表顺序保存资源
		for (int i = 0; i < vDownLoad.size(); i++) {
			url = (String) vDownLoad.get(i);
			filename = (String) vFileList.get(i);

			try {
				saveToFile(url, filename);
			} catch (IOException err) {
				if (DEBUG) {
					System.out.println("资源[" + url + "]下载失败!!!");
				}
			}
			/*if(i>0 && i%5==0){ 
				 // 每循环5次后休息2秒再进行请求, 否则可能被当作网络攻击 
				try { 
					//Thread.sleep(2000); 
					//System.out.println(">>>>>>>>>>>>>>>>>>>暂停下载,2秒后继续<<<<<<<<<<<<<<<<<<<<<<");
				}catch (InterruptedException e) { 
					  e.printStackTrace(); 
			    }
			}*/
		}

		if (DEBUG) {
			System.out.println("下载完成!!!");

		}
	}

	/**
	 * 将HTTP资源另存为文件
	 * 
	 * @param destUrl
	 *            String
	 * @param fileName
	 *            String
	 * @throws Exception
	 */
	public void saveToFile(String destUrl, String fileName) throws IOException {
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;

		// 建立链接
		url = new URL(destUrl);
		httpUrl = (HttpURLConnection) url.openConnection();
		// 连接指定的资源
		httpUrl.connect();
		// 获取网络输入流
		bis = new BufferedInputStream(httpUrl.getInputStream());
		// 建立文件
		fos = new FileOutputStream(fileName);
        //判断是否存在该文件，如果存在将文件名称修改为另外一个。。。。
		if (this.DEBUG)
			System.out.println("正在获取链接[" + destUrl + "]的内容...\n将其保存为文件["
					+ fileName + "]");

		// 保存文件
		while ((size = bis.read(buf)) != -1)
			fos.write(buf, 0, size);

		fos.close();
		bis.close();
		httpUrl.disconnect();
	}

	/**
	 * 设置代理服务器
	 * 
	 * @param proxy
	 *            String
	 * @param proxyPort
	 *            String
	 */
	public void setProxyServer(String proxy, String proxyPort) {
		// 设置代理服务器
		System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", proxy);
		System.getProperties().put("proxyPort", proxyPort);

	}

	/**
	 * 设置认证用户名与密码
	 * 
	 * @param uid
	 *            String
	 * @param pwd
	 *            String
	 */
	/*public void setAuthenticator(String uid, String pwd) {
		Authenticator.setDefault(new MyAuthenticator(uid, pwd));
	}*/

	/**
	 * 主方法(用于测试)
	 * 
	 * @param argv
	 *            String[]
	 */
	public static void main(String argv[]) {

		HttpGet oInstance = new HttpGet();
		try {
			// 增加下载列表（此处用户可以写入自己代码来增加下载列表）
			oInstance.addItem("http://xiazai.xiazaiba.com/0905/0504/2k3IIS6_XiaZaiBa.rar","e:\\temp\\iis6_2.rar");
			// 开始下载
			oInstance.downLoadByList();
		} catch (Exception err) {
			System.out.println(err.getMessage());
		}

	}

}
