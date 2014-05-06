package com.moonights.utils;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Description: ��ָ����HTTP������Դ�ڱ������ļ���ʽ���
 */
public class HttpGet {

	public final static boolean DEBUG = true;// ������

	private static int BUFFER_SIZE = 8096;// ��������С

	private Vector vDownLoad = new Vector();// URL�б�

	private Vector vFileList = new Vector();// ���غ�ı����ļ����б�

	/**
	 * ���췽��
	 */
	public HttpGet() {

	}

	/**
	 * ��������б�
	 */
	public void resetList() {
		vDownLoad.clear();
		vFileList.clear();
	}

	/**
	 * ���������б���
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
	 * �����б�������Դ
	 */
	public void downLoadByList() {
		String url = null;
		String filename = null;

		// ���б�˳�򱣴���Դ
		for (int i = 0; i < vDownLoad.size(); i++) {
			url = (String) vDownLoad.get(i);
			filename = (String) vFileList.get(i);

			try {
				saveToFile(url, filename);
			} catch (IOException err) {
				if (DEBUG) {
					System.out.println("��Դ[" + url + "]����ʧ��!!!");
				}
			}
			/*if(i>0 && i%5==0){ 
				 // ÿѭ��5�κ���Ϣ2���ٽ�������, ������ܱ��������繥�� 
				try { 
					//Thread.sleep(2000); 
					//System.out.println(">>>>>>>>>>>>>>>>>>>��ͣ����,2������<<<<<<<<<<<<<<<<<<<<<<");
				}catch (InterruptedException e) { 
					  e.printStackTrace(); 
			    }
			}*/
		}

		if (DEBUG) {
			System.out.println("�������!!!");

		}
	}

	/**
	 * ��HTTP��Դ���Ϊ�ļ�
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

		// ��������
		url = new URL(destUrl);
		httpUrl = (HttpURLConnection) url.openConnection();
		// ����ָ������Դ
		httpUrl.connect();
		// ��ȡ����������
		bis = new BufferedInputStream(httpUrl.getInputStream());
		// �����ļ�
		fos = new FileOutputStream(fileName);
        //�ж��Ƿ���ڸ��ļ���������ڽ��ļ������޸�Ϊ����һ����������
		if (this.DEBUG)
			System.out.println("���ڻ�ȡ����[" + destUrl + "]������...\n���䱣��Ϊ�ļ�["
					+ fileName + "]");

		// �����ļ�
		while ((size = bis.read(buf)) != -1)
			fos.write(buf, 0, size);

		fos.close();
		bis.close();
		httpUrl.disconnect();
	}

	/**
	 * ���ô��������
	 * 
	 * @param proxy
	 *            String
	 * @param proxyPort
	 *            String
	 */
	public void setProxyServer(String proxy, String proxyPort) {
		// ���ô��������
		System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", proxy);
		System.getProperties().put("proxyPort", proxyPort);

	}

	/**
	 * ������֤�û���������
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
	 * ������(���ڲ���)
	 * 
	 * @param argv
	 *            String[]
	 */
	public static void main(String argv[]) {

		HttpGet oInstance = new HttpGet();
		try {
			// ���������б��˴��û�����д���Լ����������������б�
			oInstance.addItem("http://xiazai.xiazaiba.com/0905/0504/2k3IIS6_XiaZaiBa.rar","e:\\temp\\iis6_2.rar");
			// ��ʼ����
			oInstance.downLoadByList();
		} catch (Exception err) {
			System.out.println(err.getMessage());
		}

	}

}
