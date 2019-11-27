
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.alibaba.fastjson.JSON;

public class ReadKml {
	public static boolean addSampleSuccess = false; // 判断读取KML是否成功
	private static Coordinate coordinate = null; // 存储从KML文件中读取出来的坐标值和name
	private static List<Placemark> placemarkList = new ArrayList();// 存储每次实例化的Coordinate对象,每个Coordinate都保存着不同的x,y,name
	private static String aaaaa;
	private static String hh;

	public static void main(String[] args) throws Exception {
		String
		// filePath =
		// "E://eclipseworkspace//lw_tracing//WebRoot//kml//test.kml";

		 filePath = "C://Users//888//Desktop//王总GIS//地图文件//JZSG-8标文件.kml";

		// String
		// filePath =
		// "C://Users//888//Desktop//王总GIS//地图文件//路基隧道线路中心线点文件.kml";// mark
		// String
		// filePath ="C://Users//888//Desktop//王总GIS//地图文件//桥梁点文件.kml";// mark

		// String
		// filePath="C://Users//888//Desktop//王总GIS//地图文件//涵洞点文件.kml";//error
		// String
		// filePath="C://Users//888//Desktop//王总GIS//地图文件//宣1变更.kml";
		// String
		//
		//

		// String
		// filePath="C://Users//888//Desktop//王总GIS//地图文件//京张中桩线文件.kml";

		// String
		// filePath="C://Users//888//Desktop//王总GIS//地图文件//一分部重点拆迁区域20160625.kml";
		// String
		//filePath = "C://Users//888//Desktop//王总GIS//地图文件//1号线.kml";
		filePath = "F:\\WebstormProjects\\RoutePlanning\\web\\kml\\5A景区.kml";
		System.out.println("main:");
		System.out.println(filePath);
		parseXmlWithDom4j(filePath);
		System.out.println("placemarkList Value :" + placemarkList.toString());

		System.out.println("------------------------------------------------");
		String jsonString = JSON.toJSONString(placemarkList);

		System.out.println(jsonString);

		try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw

			/* 读入TXT文件 */
			String pathname = "D:\\KMLjson.txt"; //
			// 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径

			// /* 写入Txt文件 */
			// File writename = new File(pathname); // 相对路径，如果没有则要建立一个新的。txt文件
			// writename.createNewFile(); // 创建新文件
			// BufferedWriter out = new BufferedWriter(new
			// FileWriter(writename));
			// out.write(jsonString); // \r\n即为换行
			// out.flush(); // 把缓存区内容压入文件
			// out.close(); // 最后记得关闭文件

			FileOutputStream writerStream = new FileOutputStream(pathname);

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "utf-8"));
			writer.write(jsonString);
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Boolean parseXmlWithDom4j(String input) throws Exception {

		Document document = null;
		document = loadFile(input);
		Element root = document.getRootElement();// 获取doc.kml文件的根结点
		listNodes(root);
		addSampleSuccess = true;
		// 选择sd卡中的kml文件，解析成功后即调用MainActivity中的添加marker的方法向地图上添加样点marker
		return addSampleSuccess;
	}

	/**
	 * 载入一个KML文件
	 * 
	 * @param fileName
	 * @return
	 */
	private static Document loadFile(String fileName) {
		Document document = null;
		SAXReader reader = null;
		try {
			reader = new SAXReader();

			FileInputStream in = new FileInputStream(new File(fileName));
			InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
			BufferedReader bufReader = new BufferedReader(inReader);
			document = reader.read(bufReader);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return document;
	}

	// 遍历当前节点下的所有节点
	public static void listNodes(Element node) {

		String name = "";// Placemark节点中的name属性
		String href = "";// Placemark节点中的name属性
		String x = "";// 坐标x
		String y = "";// 坐标y
		String alt = "";// 高程alt
		double d_x = 0.0;// 对x作string to double
		double d_y = 0.0;
		double d_alt = 0.0;
		List<Coordinate> coordinateList = new ArrayList();
		try {
			
//			if ("IconStyle".equals(node.getName())) {// 如果当前节点是Placemark就解析其子节点
//				List<Element> placemarkSons = node.elements();// 得到Placemark节点所有的子节点
//				for (Element element : placemarkSons) { // 遍历所有的子节点
//					if ("href".equals(element.getName())) {
//						href = element.getText();
//					}
//				}
//			}
			if ("Placemark".equals(node.getName())) {// 如果当前节点是Placemark就解析其子节点
				List<Element> placemarkSons = node.elements();// 得到Placemark节点所有的子节点
				for (Element element : placemarkSons) { // 遍历所有的子节点
					if ("name".equals(element.getName())) {
						name = element.getText();
						System.out.println("name :" + name  );
					}
				}
				Element pointSon;// Point节点的子节点
				Iterator i = node.elementIterator("Point");// 遍历Point节点的所有子节点
				while (i.hasNext()) {
					pointSon = (Element) i.next();
					String nodeContent = "";
					nodeContent = pointSon.elementText("coordinates");// 得到coordinates节点的节点内容
					coordinate = makeCoordinate(nodeContent);
					coordinateList.add(coordinate);// 将每一个实例化的对象存储在list中
				}
				if (coordinateList.size() > 0)
					placemarkList.add(new Placemark(coordinateList, name, "point"));
				coordinateList.clear();

				// try {
				Element LineSon;// 线节点的子节点
				Iterator j = node.elementIterator("LineString");// 遍历线节点的所有子节点
				while (j.hasNext()) {
					LineSon = (Element) j.next();
					String nodeContent = "";
					nodeContent = LineSon.elementText("coordinates");// 得到coordinates节点的节点内容
					System.out.println("beforeRepace :" + nodeContent);
					nodeContent = nodeContent.replaceAll("\t|\n", " ");
					System.out.println("afterRepace :" + nodeContent);
					String nodeContentSplit[] = null;
					nodeContentSplit = nodeContent.split(" ");
					for (int it = 0; it < nodeContentSplit.length; it++) {
						String coordinateStr = nodeContentSplit[it];
						coordinate = makeCoordinate(coordinateStr);
						coordinateList.add(coordinate);// 将每一个实例化的对象存储在list中
					}
				}

				Placemark heh = new Placemark(coordinateList, name, "LineString");
				System.out.println("Placemark  Value :" + "name=" + heh.getName() + "type=" + heh.getType() + "points="
						+ heh.getPoints());
				if (coordinateList.size() > 0)
					placemarkList.add(new Placemark(coordinateList, name, "LineString"));
				// } catch (Exception e) {
				// System.out.println("LLLLLLLLLLLLLLLLLLLLLL:" + "\n" + e);
				// System.out.println("LLLL");
				// coordinateList.clear();
				// Element LineSon;// 线节点的子节点
				// Iterator j = node.elementIterator("LineString");//
				// 遍历线节点的所有子节点
				// while (j.hasNext()) {
				// LineSon = (Element) j.next();
				// String nodeContent = "";
				// nodeContent = LineSon.elementText("coordinates");//
				// 得到coordinates节点的节点内容
				// System.out.println("#########catchCoordinates :" +
				// nodeContent);
				//
				// String nodeContentSplit[] = null;
				// nodeContentSplit = nodeContent.split("\\r?\\n");
				// for (int it = 0; it < nodeContentSplit.length; it++) {
				// String coordinateStr = nodeContentSplit[it];
				// System.out.println("NNNNNN :" + coordinateStr);
				// if(!coordinateStr.isEmpty())
				// {
				// coordinate = makeCoordinate(coordinateStr);
				// System.out.println("CCCCCCC :"+coordinate);
				// coordinateList.add(coordinate);// 将每一个实例化的对象存储在list中
				// }
				//
				// }
				// }

				Placemark heh0 = new Placemark(coordinateList, name, "LineString");
				System.out.println("Placemark  Value :" + "name=" + heh0.getName() + "type=" + heh0.getType() + "points="
						+ heh0.getPoints());
				if (coordinateList.size() > 0)
					placemarkList.add(new Placemark(coordinateList, name, "LineString"));

			}
			coordinateList.clear();
			Element PolygonSon;// 多边形节点的子节点
			Iterator k = node.elementIterator("Polygon");// 遍历Polygon节点的所有子节点
			while (k.hasNext()) {
				PolygonSon = (Element) k.next();
				String nodeContent = "";
				nodeContent = PolygonSon.getStringValue();
				nodeContent = nodeContent.replaceAll("\t|\n", "");
				String nodeContentSplit[] = null;
				nodeContentSplit = nodeContent.split(" ");
				for (int it = 0; it < nodeContentSplit.length; it++) {
					String coordinateStr = nodeContentSplit[it];
					coordinate = makeCoordinate(coordinateStr);
					coordinateList.add(coordinate);// 将每一个实例化的对象存储在list中
				}
			}

			Placemark heh = new Placemark(coordinateList, name, "Polygon");
			System.out.println("Placemark  Value :" + "name=" + heh.getName() + "type=" + heh.getType() + "points="
					+ heh.getPoints());
			if (coordinateList.size() > 0)
				placemarkList.add(new Placemark(coordinateList, name, "Polygon"));
			coordinateList.clear();

			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 同时迭代当前节点下面的所有子节点
		// 使用递归
		Iterator<Element> iterator = node.elementIterator();
		while (iterator.hasNext()) {
			Element e = iterator.next();
			listNodes(e);
		}
	}

	private static Coordinate makeCoordinate(String point) {
		Coordinate coordinate = null;
		String name = "";// Placemark节点中的name属性
		String x = "";// 坐标x
		String y = "";// 坐标y
		String alt = "";// 高程alt
		double d_x = 0.0;// 对x作string to double
		double d_y = 0.0;
		double d_alt = 0.0;
		String nodeContentSplit[] = null;
		System.out.println("makeCoordinate :" + point);
		nodeContentSplit = point.split(",");
		System.out.println("nodeContentSplit :" + nodeContentSplit);
		y = nodeContentSplit[0];
		x = nodeContentSplit[1];
		alt = nodeContentSplit[2];

		d_x = Double.valueOf(x.trim());
		d_y = Double.valueOf(y.trim());
		d_alt = Double.valueOf(alt.trim());
		coordinate = new Coordinate(d_x, d_y, d_alt);
		System.out.println("coordinate  Value :" + "x=" + coordinate.getX() + "y=" + coordinate.getY() + "alt="
				+ coordinate.getAlt());

		return coordinate;
	}

	public List<Placemark> getCoordinateList() {
		return ReadKml.placemarkList;
	}
}