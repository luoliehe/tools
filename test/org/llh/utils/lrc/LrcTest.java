package org.llh.utils.lrc;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class LrcTest {

	public static void main(String[] args) throws IOException {
		
		URL url = LrcTest.class.getResource("/com/llh/utils/lrc");
		
		File file = new File(new File(url.getFile()), "海阔天空.lrc");

		LrcEntity entity = LrcUtil.analysisLrc(file);
		
		System.out.println(entity);
	}

}
