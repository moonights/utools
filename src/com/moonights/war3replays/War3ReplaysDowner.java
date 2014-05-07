package com.moonights.war3replays;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

import com.moonights.utils.Configuration;
import com.moonights.utils.HttpGet;

/**
 * http://w3g.replays.net ��������war3Replays�ļ��Ĺ��ߣ�����ϲ������ħ��¼�����Աʹ��.
 * Ŀǰֻʵ�ֵ�����������Ŀ¼�µ�ҳ�棬�����ӵݹ�ʵ�ֶ༶ҳ�����....
 * 
 * <h3>
	<span id="ctl00_Content_labTitle">
	<img src='http://w3g.replays.net/images/2.gif' alt='' /> 
	[Zcup]HawK vs HPE.Yumiko #2
	</span>
	</h3>
	<span id="ctl00_Content_labDown" class="download">
	<a href="/Download.aspx?ReplayID=56413&File=Yumiko_LastRefuge1.3_RN.w3g">
	Download REP
	</a>
	</span>
 * @author moonights
 * 
 */
public class War3ReplaysDowner {
	private static Configuration config = new Configuration("config/war3.properties");
	private static String DOWNLOAD_INDEX_URL = "";
	private static String DOWNLOAD_MAIN_URL ="";// "http://w3g.replays.net/Default.aspx?PageNo=2";
	private static String FILE_VEISON = "";// "1.20\1.24\1.21";
	private static String FILE_SAVE_PATH = "";// "E:\\temp";
    private static String FILE_SAVE_TYPE = "";// ".w3g";
	
	public War3ReplaysDowner(){
		DOWNLOAD_INDEX_URL = config.getValue("DOWNLOAD_INDEX_URL");
		DOWNLOAD_MAIN_URL = config.getValue("DOWNLOAD_MAIN_URL");// "http://w3g.replays.net/Default.aspx?PageNo=2";		
		FILE_VEISON = config.getValue("FILE_VEISON");// "1.20\1.24\1.21";		
	    FILE_SAVE_PATH = config.getValue("FILE_SAVE_PATH");// "E:\\temp";
		FILE_SAVE_TYPE = config.getValue("FILE_SAVE_TYPE");// ".w3g";
	}
	
	
	public War3ReplaysDowner(String indexURL,String mainURL,String saveVersion,String saveType,String savePath){
		DOWNLOAD_INDEX_URL = indexURL;
		DOWNLOAD_MAIN_URL = mainURL;// "http://w3g.replays.net/Default.aspx?PageNo=2";		
		FILE_VEISON = saveVersion;// "1.20\1.24\1.21";		
	    FILE_SAVE_PATH = savePath;// "E:\\temp";
		FILE_SAVE_TYPE = saveType;// ".w3g";
	}
	//��ȡ�����ļ������õ�����
	/**
	 * * ֻ����������͵�url:<a
	 * href="http://w3g.replays.net/doc/cn/2010-1-25/12644174774532572561.html"
	 * target="_blank">*
	 */
	private static final String url_regexp_resources = "<a href=\"(\\w+://[^/:]+[^#\\s]*)\" target=\"_blank\">";

	private static final String url_regexp_downloadFile = "<a href=\"(.*?)\">Download REP</a>";

	private static final String url_regexp_downloadFileName = "<h3><span id=\"ctl00_Content_labTitle\">(.*?)</span></h3>";

	private static final String url_regexp_downloadVersion ="<span id=\"ctl00_Content_labVer\">(.*?)</span>";
	/**
	 * ���� URL ��ȡӦ��ҳ���HTMLԴ��
	 * 
	 * @param url
	 *            �ļ���URL
	 * @return String URLӦ��ҳ���HTMLԴ��, ������ӵ�ָ��URL, �򷵻�һ�����ַ���("")
	 */
	public String getHtmlCode(String url) {
		try {
			URL u = new URL(url);
			URLConnection urlConnection = u.openConnection();
			urlConnection.setAllowUserInteraction(false);
			// ʹ��openStream�õ�һ���������ɴ˹���һ��BufferedReader����
			BufferedReader in = new BufferedReader(new InputStreamReader(u
					.openStream()));
			String inputLine;
			StringBuffer tempHtml = new StringBuffer();
			while ((inputLine = in.readLine()) != null) { // �����������ϵĶ����ݣ�ֱ������Ϊֹ
				tempHtml.append(inputLine).append("\n");
			}
			return tempHtml.toString();
		} catch (IOException e) {
			return "";
		}
	}
	/**
	 * ���� URL ��ȡӦ��ҳ���HTMLԴ��
	 * 
	 * @param url
	 *            �ļ���URL
	 * @return String URLӦ��ҳ���HTMLԴ��, ������ӵ�ָ��URL, �򷵻�һ�����ַ���("")
	 */
	public static String getHtml(String urlString) {
		try {
			StringBuffer html = new StringBuffer();
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStreamReader isr = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(isr);
			String temp;
			while ((temp = br.readLine()) != null) {
				html.append(temp).append("\n");
			}
			br.close();
			isr.close();
			return html.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ����url��ȡ��urlҳ���е����а���������Դ��url����() ֻ����������͵�url:<a
	 * href="http://w3g.replays.net/doc/cn/2010-1-25/12644174774532572561.html"
	 * target="_blank">
	 * 
	 * @param �ļ���URL
	 * @return List
	 *         URLӦ��ҳ���е�����ָ��URL:http://w3g.replays.net/doc/cn/2010-1-25/12644174774532572561.html
	 */
	public List<String> getUrlByURLPage(String url) {
		List<String> list = new ArrayList<String>();
		String htmlCode = getHtmlCode(url);
		Pattern p = Pattern.compile(url_regexp_resources,Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(htmlCode);
		String id = null;
		while (matcher.find()) {
			id = matcher.group(1);
			if (!list.contains(id)) {
				list.add(id);
			}
		}
		return list;
	}

	/**
	 * ���ݻ�ȡ�İ���������Դ��url��ѯ������Դurl�����Լ��ļ����� ���ļ������£�[SW]GaB.RohJinWook vs ieS.Check
	 * #1.w3g,����ҳ���еı����ȡ�����������룬���ֶ����ص����Ʋ�һ�µ����ƣ�
	 * 
	 * @param List
	 * @return Vector
	 *         {("http://w3g.replays.net/Download.aspx?ReplayID=41162&File=%2fReplayFile%2f2010-1-26%2f100126_%5bUD%5dfantafiction_VS_%5bORC%5dmmmgbp_TwistedMeadows_RN.w3g",
	 *         "e:\\temp\\dmmmgbp_TwistedMeadows_RN.w3g")}
	 */
	public Vector getWar3ReplaysByTitle(List url_list) {
		Vector vector = new Vector();
		for (int i = 0; i < url_list.size(); i++) {
			String temp_url = url_list.get(i).toString();
			String temp_htmlCode = this.getHtmlCode(temp_url);//����ҳ��Դ�ļ��ַ�
			String fileUrl = getMatcher(url_regexp_downloadFile, temp_htmlCode,1);
			String fileName = getMatcher(url_regexp_downloadFileName,temp_htmlCode, 1);
			String fileVision= getMatcher(url_regexp_downloadVersion,temp_htmlCode, 1);
			System.out.println(">>>>>>>>>>>>>>>>>>>fileUrl="+fileUrl+">>>>>>>>>>>>>>>>>>>");
			System.out.println(">>>>>>>>>>>>>>>>>>>fileName="+fileVision+">>>>>>>>>>>>>>>>>>>");
			//System.out.println(">>>>>>>>>>>>>>>>>>>fileVision="+fileVision+">>>>>>>>>>>>>>>>>>>");
			if (!fileUrl.equals("") && !fileName.equals("")&&fileVision.equals(FILE_VEISON)) {
				
			}
			War3Replays war3Replays = new War3Replays();
			fileUrl = DOWNLOAD_INDEX_URL + fileUrl;// �����URL�޸�Ϊ����URL
			// fileName = FILE_SAVE_PATH+"\\"+fileName+FILE_SAVE_TYPE;//
			war3Replays.setFileName(fileName);
			war3Replays.setFileUrl(fileUrl);
			war3Replays.setSavePath(this.FILE_SAVE_PATH);
			war3Replays.setFileFormat(this.FILE_SAVE_TYPE);
			vector.add(war3Replays);
		
			if (i > 0 && i % 10 == 0) {
				// ÿѭ��10�κ���Ϣ2���ٽ�������, ������ܱ��������繥��
				try {
					Thread.sleep(2000);
					System.out
							.println(">>>>>>>>>>>>>>>>>>>��ͣҳ��ץȡ,2������<<<<<<<<<<<<<<<<<<<<<<");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return vector;
	}

	/**
	 * ���ݻ�ȡ�İ���������Դ��url���Ӳ�ѯ����War3Replays�ļ�
	 * ���ļ������£�_ReplayFile_2010-1-24_100124__NE_mTw.DIDI8_VS__HM_VeryB1gman._AncientIsles_RN.w3g������url��ȡ�ģ�
	 * ���Ż���������һЩ..
	 * 
	 * @param url_list
	 * @return
	 */
	public Vector getWar3ReplaysByUrl(List url_list) {
		Vector vector = new Vector();
		for (int i = 0; i < url_list.size(); i++) {
			String temp_url = url_list.get(i).toString();
			String temp_htmlCode = this.getHtmlCode(temp_url);
			String fileUrl = getMatcher(url_regexp_downloadFile, temp_htmlCode,1);
			String fileVision= getMatcher(url_regexp_downloadVersion,temp_htmlCode, 1);
			//
			System.out
			.println(">>>>>>>>>>>>>>>>>>>fileVision="+fileVision+">>>>>>>>>>>>>>>>>>>");
			if (!fileUrl.equals("") &&fileVision.equals(FILE_VEISON)) {
				War3Replays war3Replays = new War3Replays();
				fileUrl = DOWNLOAD_INDEX_URL + fileUrl;// �����URL�޸�Ϊ����URL
				war3Replays.setFileUrl(fileUrl);
				String regex = "&File=(.*?).w3g";
				String fileName = getMatcher(regex, fileUrl, 1);
				fileName = fileName.replaceAll("(%2f|%5b|%5d)", "_");
				war3Replays.setFileName(fileName);
				war3Replays.setSavePath(this.FILE_SAVE_PATH);
				war3Replays.setFileFormat(this.FILE_SAVE_TYPE);				
				vector.add(war3Replays);
				System.out.println("<<<<<<<<<<<<<<<<<<<<<<��ȡһ��¼���ļ���url��"+war3Replays.getFileUrl());
			}
			if (i > 0 && i % 10 == 0) {
				// ÿѭ��10�κ���Ϣ2���ٽ�������, ������ܱ��������繥��
				try {
					Thread.sleep(2000);
					System.out
							.println(">>>>>>>>>>>>>>>>>>>��ͣҳ��ץȡ,2������<<<<<<<<<<<<<<<<<<<<<<");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return vector;
	}

	public static String getMatcher(String regex, String source, int group) {
		String result = "";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(source);
		while (matcher.find()) {
			result = matcher.group(group);
		}
		return result;
	}

	/**
	 * ���ػ�ȡ����war3replays¼���ļ� ����һ
	 */
	public void downWar3Replays_WAY1() {
		List url_list = this.getUrlByURLPage(DOWNLOAD_MAIN_URL);
		Vector vector = this.getWar3ReplaysByUrl(url_list);
		HttpGet downer = new HttpGet();
		if (vector.size() > 0) {
			for (int i = 0; i < vector.size(); i++) {
				War3Replays war3Replays = (War3Replays) vector.get(i);
				try {
					// ���������б��˴��û�����д���Լ����������������б�
					downer.addItem(war3Replays.getFileUrl(), war3Replays
							.getSavePath()
							+ "\\"
							+ war3Replays.getFileName()
							+ war3Replays.getFileFormat());
					// ��ʼ����
				} catch (Exception err) {
					System.out.println(err.getMessage());
				}
			}
			System.out.println("��ʼ����.");
			downer.downLoadByList();
			System.out.println("�������.");
		}
	}

	/**
	 * ���ػ�ȡ����war3replays¼���ļ� ������
	 */
	public void downWar3Replays_WAY2() {
		List url_list = this.getUrlByURLPage(DOWNLOAD_MAIN_URL);
		Vector vector = this.getWar3ReplaysByTitle(url_list);
		HttpGet downer = new HttpGet();
		if (vector.size() > 0) {
			for (int i = 0; i < vector.size(); i++) {
				War3Replays war3Replays = (War3Replays) vector.get(i);
				try {
					// ���������б��˴��û�����д���Լ����������������б�
					downer.addItem(war3Replays.getFileUrl(), war3Replays
							.getSavePath()
							+ "\\"
							+ war3Replays.getFileName()
							+ war3Replays.getFileFormat());
					// ��ʼ����
				} catch (Exception err) {
					System.out.println(err.getMessage());
				}
			}
			System.out.println("��ʼ����.");
			downer.downLoadByList();
			System.out.println("�������.");
		}
	}
}
