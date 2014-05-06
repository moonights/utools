package com.moonights.war3replays;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	public static final String url_regexp_resources = "<a href=\"(\\w+://[^/:]+[^#\\s]*)\" target=\"_blank\">";      
	
	public static final String url_regexp_download = "<a href=\"(.*?)\">Download REP</a>";    
	
	public static final String url_regexp_downloadFileName ="<h3><span id=\"ctl00_Content_labTitle\">(.*?)</span></h3>";
			                                               // "<span id=\"ctl00_Content_labDown\" class=\"download\"><a href=\"(.*?)\">Download REP</a>";
	
    //°æ±¾
	public static final String url_regexp_downloadVersion ="<span id=\"ctl00_Content_labVer\">(.*?)</span>";
	
	public String getHtml(){
		War3ReplaysDowner w=new War3ReplaysDowner();
		String s=w.getHtmlCode("http://w3g.replays.net/doc/cn/2006-6-2/11492116200000085860.html");
		//System.out.println(s);
		return s;
	}
	
	public void test(String reg_str){
		String s_resourcs=this.getHtml();
		Pattern p = Pattern.compile(reg_str);	
		Matcher m = p.matcher(s_resourcs);
		while(m.find()){
			System.out.println("m.group():"+m.group()); 
			System.out.println("m.group(1):"+m.group(1));
		}
	}	
	public static void main(String[] args){
		
		Test t=new Test();
		t.test(url_regexp_downloadVersion);
		t.test(url_regexp_downloadFileName);
	}		
	
	public static War3Replays getWar3Replays(String fileUrl) {
		///Download.aspx?ReplayID=41162&41162&File=%2fReplayFile%2f2010-1-26%2f100126_%5bUD%5dfantafiction_VS_%5bORC%5dmmmgbp_TwistedMeadows_RN.w3g
		War3Replays war3Replays=new War3Replays();
		war3Replays.setFileUrl(fileUrl);		
		String regex="&File=(.*?).w3g"; 
		String fileName=getMatcher(regex, fileUrl, 1);
		fileName=fileName.replaceAll("(%2f|%5b|%5d)","_");   
		war3Replays.setFileName(fileName);
		return war3Replays;
	}
	
	public static String getMatcher(String regex, String source,int group) {   
        String result = "";   
        Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);   
        Matcher matcher = pattern.matcher(source);   
        while (matcher.find()) {   
            result = matcher.group(group);
        }   
        return result;
	}
}
