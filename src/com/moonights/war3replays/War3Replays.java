package com.moonights.war3replays;


/**
 * 
 * @author moonights
 *
 */
public class War3Replays {     
           
    /**¼���ļ�����*/      
    private String fileName;   
           
    /**¼���ļ���С*/      
    private String fileSize;       
           
    /**¼���ļ���ʽ*/      
    private String fileFormat=".w3g";       
    
    /**���ذ汾*/      
    private String fileVeison;
    
    /**���ص�ַ*/      
    private String fileUrl;
    
    private String savePath;     
  
    
	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getFileVeison() {
		return fileVeison;
	}

	public void setFileVeison(String fileVeison) {
		this.fileVeison = fileVeison;
	}    
}
