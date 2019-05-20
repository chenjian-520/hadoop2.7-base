package com.ghgj;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class IoTestHdfs2 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		List<Integer> oneFile4 = getOneFile("G:\\QMDownload\\04.txt");
		List<Integer> oneFile5 = getOneFile("G:\\QMDownload\\05.txt");
		List<Integer> oneFile6 = getOneFile("G:\\QMDownload\\06.txt");
		
		List<Integer> mergeResult = mergeResult(oneFile4,oneFile5,oneFile6);
		System.out.println(mergeResult);
		int sum = 0 ,i=0;
		for(int a:mergeResult){
			sum +=a;
			i++;
		}
		System.out.println(sum/i+" , "+mergeResult.get(0)+" , "+mergeResult.get(mergeResult.size()-1));
		
	}
	
	

	private static List<Integer> mergeResult(List<Integer> ...oneFile1) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<>();
		for(List<Integer> ma : oneFile1){
			for(Integer a : ma){
				list.add(a);
			}
		}
		list.sort(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				return o1-o2;
			}
		});
		return list;
	}



	public static List<Integer> getOneFile(String path) throws FileNotFoundException, IOException {
		// G:\\QMDownload\\01.txt
		BufferedReader br = new BufferedReader(new FileReader(path));
		List<Integer> list = new ArrayList<>();
		String line = null;
		while ((line = br.readLine()) != null) {
			String[] split = line.split(",");
			for (String a : split) {
				Integer c = Integer.valueOf(a);
				list.add(c);
			}
		}
		System.out.println(list);
		br.close();
/*		list.forEach(new Consumer<Integer>() {
			@Override
			public void accept(Integer t) {
				// TODO Auto-generated method stub
				System.out.println(t);
			}
		});*/
		return list;
	}

}
