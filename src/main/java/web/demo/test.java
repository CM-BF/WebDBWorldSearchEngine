package web.demo;

import java.util.ArrayList;
import java.util.List;

public class test {
	public static void main(String[] args) throws Exception{
		List<String> s = new ArrayList<>();
		s.add("abc");
		s.add("def");
		System.out.println(s.get(s.size()-1));
	}
}
