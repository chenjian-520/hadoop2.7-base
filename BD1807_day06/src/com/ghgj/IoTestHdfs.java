package com.ghgj;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IoTestHdfs {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Map<String, Integer> oneFile1 = getOneFile("G:\\QMDownload\\01.txt");
		Map<String, Integer> oneFile2 = getOneFile("G:\\QMDownload\\02.txt");
		Map<String, Integer> oneFile3 = getOneFile("G:\\QMDownload\\03.txt");
		Map<String, Integer> oneFile4 = getOneFile("G:\\QMDownload\\04.txt");
		Map<String, Integer> oneFile5 = getOneFile("G:\\QMDownload\\05.txt");
		Map<String, Integer> oneFile6 = getOneFile("G:\\QMDownload\\06.txt");
		
		Map<String, Integer> mergeResult = mergeResult(oneFile1,oneFile2,oneFile3,oneFile4,oneFile5,oneFile6);
		System.out.println(mergeResult);
	}
	
	

	private static Map<String, Integer> mergeResult(Map<String, Integer> ...oneFile1) {
		// TODO Auto-generated method stub
		Map<String, Integer> map = new HashMap<>();
		for(Map<String, Integer> ma : oneFile1){
			Set<String> keySet = ma.keySet();
			for(String se : keySet){
				if(!map.containsKey(se)){
					map.put(se, ma.get(se));
				}else{
					int newv=ma.get(se)+map.get(se);
					map.put(se, newv);
				}
			}
		}
		return map;
	}



	public static Map<String, Integer> getOneFile(String path) throws FileNotFoundException, IOException {
		// G:\\QMDownload\\01.txt
		BufferedReader br = new BufferedReader(new FileReader(path));
		Map<String, Integer> map = new HashMap<>();
		String line = null;
		while ((line = br.readLine()) != null) {
			String[] split = line.split("\t");
			for (String a : split) {
				if (!map.containsKey(a)) {
					map.put(a, 1);
				} else {
					int chen = map.get(a) + 1;
					map.put(a, chen);
				}
			}
		}
		System.out.println(map);
		br.close();
		return map;
	}

}
