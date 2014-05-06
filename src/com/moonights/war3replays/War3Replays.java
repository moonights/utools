package com.moonights.war3replays;


/**
 * 
 * @author moonights
 *
 */
public class War3Replays {     
           
    /**录像文件名称*/      
    private String fileName;   
           
    /**录像文件大小*/      
    private String fileSize;       
           
    /**录像文件格式*/      
    private String fileFormat=".w3g";       
    
    /**下载版本*/      
    private String fileVeison;
    
    /**下载地址*/      
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
