package com.moonights.download.two;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author annegu
 * @since 2009-07-16
 * 
 */
public class DownloadTask {

	// private static transient
	// 分段下载的线程个数
	private int threadNum = 5;
	private URL url = null;
	private long threadLength = 0;
	// 目标文件路径与名字
	public String fileDir = "E:/concurrent/";
	public String fileName = "test";
	public boolean statusError = false;
	private String charset;

	public long sleepSeconds = 5;

	public String download(String urlStr, String charset) {
		statusError = false;
		this.charset = charset;
		long contentLength = 0;
		CountDownLatch latch = new CountDownLatch(threadNum);
		ChildThread[] childThreads = new ChildThread[threadNum];
		long[] startPos = new long[threadNum];
		long[] endPos = new long[threadNum];

		try {
			// 从url中获得下载的文件格式与名字
			this.fileName = urlStr.substring(urlStr.lastIndexOf("/") + 1,
					urlStr.lastIndexOf("?") > 0 ? urlStr.lastIndexOf("?")
							: urlStr.length());
			if ("".equalsIgnoreCase(this.fileName)) {
				this.fileName = UUID.randomUUID().toString();
			}

			File file = new File(fileDir + fileName);
			File tempFile = new File(fileDir + fileName + "_temp");
			
			this.url = new URL(urlStr);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			setHeader(con);
			// 得到content的长度
			contentLength = con.getContentLength();
			// 把context分为threadNum段的话，每段的长度。
			this.threadLength = contentLength / threadNum;

			// 第一步，分析已下载的临时文件，设置断点，如果是新的下载任务，则建立目标文件。
			setThreadBreakpoint(file, tempFile, contentLength, startPos, endPos);

			// 第二步，分多个线程下载文件
			ExecutorService exec = Executors.newCachedThreadPool();
			for (int i = 0; i < threadNum; i++) {

				// 开启子线程，并执行。
				ChildThread thread = new ChildThread(this, latch, i,
						startPos[i], endPos[i]);
				childThreads[i] = thread;
				exec.execute(thread);
			}

			try {
				// 等待CountdownLatch信号为0，表示所有子线程都结束。
				latch.await();
				exec.shutdown();

				// 删除临时文件
				long downloadFileSize = file.length();
				if (downloadFileSize == contentLength) {
					tempFile.delete();
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileDir + fileName;
	}

	private void setThreadBreakpoint(File file, File tempFile,
			long contentLength, long[] startPos, long[] endPos) {
		RandomAccessFile tempFileFos = null;
		try {
			if (file.exists()) {
				System.out.println("file " + fileName + " has exists!");

				long localFileSize = file.length();
				// 下载的目标文件已存在，判断目标文件是否完整
				if (localFileSize < contentLength) {
					System.out.println("Now download continue ... ");

					tempFileFos = new RandomAccessFile(tempFile, "rw");
					// 遍历目标文件的所有临时文件，设置断点的位置，即每个临时文件的长度
					for (int i = 0; i < threadNum; i++) {
						tempFileFos.seek(4 + 24 * i + 8);
						endPos[i] = tempFileFos.readLong();

						tempFileFos.seek(4 + 24 * i + 16);
						startPos[i] = tempFileFos.readLong();
					}
				} else {
					System.out.println("This file has download complete!");
				}

			} else {
				// 如果下载的目标文件不存在，则创建新文件
				file.createNewFile();
				tempFile.createNewFile();
				tempFileFos = new RandomAccessFile(tempFile, "rw");
				tempFileFos.writeInt(threadNum);

				for (int i = 0; i < threadNum; i++) {

					// 创建子线程来负责下载数据，每段数据的起始位置为(threadLength * i)
					startPos[i] = threadLength * i;
					tempFileFos.writeLong(startPos[i]);

					/*
					 * 设置子线程的终止位置，非最后一个线程即为(threadLength * (i + 1) - 1)
					 * 最后一个线程的终止位置即为下载内容的长度
					 */
					if (i == threadNum - 1) {
						endPos[i] = contentLength;
					} else {
						endPos[i] = threadLength * (i + 1) - 1;
					}
					// end position
					tempFileFos.writeLong(endPos[i]);
					// current position
					tempFileFos.writeLong(startPos[i]);
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				tempFileFos.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @author annegu
	 * @since 2009-07-16
	 * 
	 */
	public class ChildThread extends Thread {
		private DownloadTask task;
		private int id;
		private long startPosition;
		private long endPosition;
		private final CountDownLatch latch;
		private RandomAccessFile file = null;
		private RandomAccessFile tempFile = null;

		public ChildThread(DownloadTask task, CountDownLatch latch, int id,
				long startPos, long endPos) {
			super();
			this.task = task;
			this.id = id;
			this.startPosition = startPos;
			this.endPosition = endPos;
			this.latch = latch;

			try {
				file = new RandomAccessFile(this.task.fileDir
						+ this.task.fileName, "rw");
				tempFile = new RandomAccessFile(this.task.fileDir
						+ this.task.fileName + "_temp", "rw");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			System.out.println("Thread " + id + " run ...");
			HttpURLConnection con = null;
			InputStream inputStream = null;
			long count = 0;

			try {
				System.out.println(id + "===1 ====" + tempFile.readInt());
				tempFile.seek(4 + 24 * id);
				System.out.println(id + "===2 ====" + tempFile.readLong());
				System.out.println(id + "===3 ====" + tempFile.readLong());
				System.out.println(id + "===4 ====" + tempFile.readLong());
			} catch (IOException e2) {
				e2.printStackTrace();
			}

			for (;;) {
				try {
					// 打开URLConnection
					con = (HttpURLConnection) task.url.openConnection();
					setHeader(con);
					// 设置连接超时时间为10000ms
					con.setConnectTimeout(10000);
					// 设置读取数据超时时间为10000ms
					con.setReadTimeout(10000);

					if (startPosition < endPosition) {
						// 设置下载数据的起止区间
						con.setRequestProperty("Range", "bytes="
								+ startPosition + "-" + endPosition);
						System.out.println("Thread " + id
								+ " startPosition is " + startPosition
								+ ", and endPosition is " + endPosition);

						file.seek(startPosition);

						// 判断http status是否为HTTP/1.1 206 Partial Content或者200 OK
						// 如果不是以上两种状态，把status改为STATUS_HTTPSTATUS_ERROR
						if (con.getResponseCode() != HttpURLConnection.HTTP_OK
								&& con.getResponseCode() != HttpURLConnection.HTTP_PARTIAL) {
							System.out.println("Thread " + id + ": code = "
									+ con.getResponseCode() + ", status = "
									+ con.getResponseMessage());
							this.task.statusError = true;
							file.close();
							con.disconnect();
							System.out.println("Thread " + id + " finished.");
							latch.countDown();
							break;
						}

						inputStream = con.getInputStream();
						int len = 0;
						byte[] b = new byte[1024];
						while (!this.task.statusError
								&& (len = inputStream.read(b)) != -1) {
							file.write(b, 0, len);

							count += len;
							startPosition += len;

							// set tempFile now position
							tempFile.seek(4 + 24 * id + 16);
							tempFile.writeLong(startPosition);
						}

						file.close();
						tempFile.close();
						inputStream.close();
						con.disconnect();
					}

					System.out.println("Thread " + id + " finished.");
					latch.countDown();
					break;
				} catch (IOException e) {
					try {
						// outputStream.flush();
						TimeUnit.SECONDS.sleep(getSleepSeconds());
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					continue;
				}
			}
		}
	}

	private void setHeader(URLConnection con) {
		con
				.setRequestProperty(
						"User-Agent",
						"Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.3) Gecko/2008092510 Ubuntu/8.04 (hardy) Firefox/3.0.3");
		con.setRequestProperty("Accept-Language", "en-us,en;q=0.7,zh-cn;q=0.3");
		con.setRequestProperty("Accept-Encoding", "aa");
		con.setRequestProperty("Accept-Charset",
				"ISO-8859-1,utf-8;q=0.7,*;q=0.7");
		con.setRequestProperty("Keep-Alive", "300");
		con.setRequestProperty("Connection", "keep-alive");
		con.setRequestProperty("If-Modified-Since",
				"Fri, 02 Jan 2009 17:00:05 GMT");
		con.setRequestProperty("If-None-Match", "\"1261d8-4290-df64d224\"");
		con.setRequestProperty("Cache-Control", "max-age=0");
		con.setRequestProperty("Referer",
				"http://www.skycn.com/soft/14857.html");
	}

	public long getSleepSeconds() {
		return sleepSeconds;
	}

	public void setSleepSeconds(long sleepSeconds) {
		this.sleepSeconds = sleepSeconds;
	}

}
