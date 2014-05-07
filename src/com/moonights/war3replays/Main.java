package com.moonights.war3replays;

import java.io.IOException;

/**
 * 锟斤拷锟斤拷锟斤拷凇锟�
 * @author moonights
 *
 */
public class Main {
	public static void main(String[] args) throws IOException {
		War3ReplaysDowner war3_Downer = new War3ReplaysDowner();
		war3_Downer.downWar3Replays_WAY2();
//		war3_Downer.downWar3Replays_WAY1();
		Runtime.getRuntime().exec("cmd.exe /c start c:\\temp-output");
	}
}
