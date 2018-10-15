package xml;

//: xml/People.java
// {Requires: nu.xom.Node; You must install
// the XOM library from http://www.xom.nu }
// {RunFirst: Person}
import nu.xom.*;

import java.io.File;
import java.util.*;

public class People extends ArrayList<Person> {
	public People(String fileName) throws Exception {
		File file = new File(fileName);//可以防止路径包含中文字符时报错的情况
		Document doc = new Builder().build(file);
		Elements elements = doc.getRootElement().getChildElements();
		for (int i = 0; i < elements.size(); i++)
			add(new Person(elements.get(i)));
	}

	public static void main(String[] args) throws Exception {
		People p = new People("ioTest/People.xml");
		System.out.println(p);
	}
} /*
 * Output: [Dr. Bunsen Honeydew, Gonzo The Great, Phillip J. Fry]
 */// :~
