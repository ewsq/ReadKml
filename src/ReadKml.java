
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
	public static boolean addSampleSuccess = false; // �ж϶�ȡKML�Ƿ�ɹ�
	private static Coordinate coordinate = null; // �洢��KML�ļ��ж�ȡ����������ֵ��name
	private static List<Placemark> placemarkList = new ArrayList();// �洢ÿ��ʵ������Coordinate����,ÿ��Coordinate�������Ų�ͬ��x,y,name
	private static String aaaaa;
	private static String hh;

	public static void main(String[] args) throws Exception {
		String
		// filePath =
		// "E://eclipseworkspace//lw_tracing//WebRoot//kml//test.kml";

		 filePath = "C://Users//888//Desktop//����GIS//��ͼ�ļ�//JZSG-8���ļ�.kml";

		// String
		// filePath =
		// "C://Users//888//Desktop//����GIS//��ͼ�ļ�//·�������·�����ߵ��ļ�.kml";// mark
		// String
		// filePath ="C://Users//888//Desktop//����GIS//��ͼ�ļ�//�������ļ�.kml";// mark

		// String
		// filePath="C://Users//888//Desktop//����GIS//��ͼ�ļ�//�������ļ�.kml";//error
		// String
		// filePath="C://Users//888//Desktop//����GIS//��ͼ�ļ�//��1���.kml";
		// String
		//
		//

		// String
		// filePath="C://Users//888//Desktop//����GIS//��ͼ�ļ�//������׮���ļ�.kml";

		// String
		// filePath="C://Users//888//Desktop//����GIS//��ͼ�ļ�//һ�ֲ��ص��Ǩ����20160625.kml";
		// String
		//filePath = "C://Users//888//Desktop//����GIS//��ͼ�ļ�//1����.kml";
		filePath = "F:\\WebstormProjects\\RoutePlanning\\web\\kml\\5A����.kml";
		System.out.println("main:");
		System.out.println(filePath);
		parseXmlWithDom4j(filePath);
		System.out.println("placemarkList Value :" + placemarkList.toString());

		System.out.println("------------------------------------------------");
		String jsonString = JSON.toJSONString(placemarkList);

		System.out.println(jsonString);

		try { // ��ֹ�ļ��������ȡʧ�ܣ���catch��׽���󲢴�ӡ��Ҳ����throw

			/* ����TXT�ļ� */
			String pathname = "D:\\KMLjson.txt"; //
			// ����·�������·�������ԣ������Ǿ���·����д���ļ�ʱ��ʾ���·��

			// /* д��Txt�ļ� */
			// File writename = new File(pathname); // ���·�������û����Ҫ����һ���µġ�txt�ļ�
			// writename.createNewFile(); // �������ļ�
			// BufferedWriter out = new BufferedWriter(new
			// FileWriter(writename));
			// out.write(jsonString); // \r\n��Ϊ����
			// out.flush(); // �ѻ���������ѹ���ļ�
			// out.close(); // ���ǵùر��ļ�

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
		Element root = document.getRootElement();// ��ȡdoc.kml�ļ��ĸ����
		listNodes(root);
		addSampleSuccess = true;
		// ѡ��sd���е�kml�ļ��������ɹ��󼴵���MainActivity�е����marker�ķ������ͼ���������marker
		return addSampleSuccess;
	}

	/**
	 * ����һ��KML�ļ�
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

	// ������ǰ�ڵ��µ����нڵ�
	public static void listNodes(Element node) {

		String name = "";// Placemark�ڵ��е�name����
		String href = "";// Placemark�ڵ��е�name����
		String x = "";// ����x
		String y = "";// ����y
		String alt = "";// �߳�alt
		double d_x = 0.0;// ��x��string to double
		double d_y = 0.0;
		double d_alt = 0.0;
		List<Coordinate> coordinateList = new ArrayList();
		try {
			
//			if ("IconStyle".equals(node.getName())) {// �����ǰ�ڵ���Placemark�ͽ������ӽڵ�
//				List<Element> placemarkSons = node.elements();// �õ�Placemark�ڵ����е��ӽڵ�
//				for (Element element : placemarkSons) { // �������е��ӽڵ�
//					if ("href".equals(element.getName())) {
//						href = element.getText();
//					}
//				}
//			}
			if ("Placemark".equals(node.getName())) {// �����ǰ�ڵ���Placemark�ͽ������ӽڵ�
				List<Element> placemarkSons = node.elements();// �õ�Placemark�ڵ����е��ӽڵ�
				for (Element element : placemarkSons) { // �������е��ӽڵ�
					if ("name".equals(element.getName())) {
						name = element.getText();
						System.out.println("name :" + name  );
					}
				}
				Element pointSon;// Point�ڵ���ӽڵ�
				Iterator i = node.elementIterator("Point");// ����Point�ڵ�������ӽڵ�
				while (i.hasNext()) {
					pointSon = (Element) i.next();
					String nodeContent = "";
					nodeContent = pointSon.elementText("coordinates");// �õ�coordinates�ڵ�Ľڵ�����
					coordinate = makeCoordinate(nodeContent);
					coordinateList.add(coordinate);// ��ÿһ��ʵ�����Ķ���洢��list��
				}
				if (coordinateList.size() > 0)
					placemarkList.add(new Placemark(coordinateList, name, "point"));
				coordinateList.clear();

				// try {
				Element LineSon;// �߽ڵ���ӽڵ�
				Iterator j = node.elementIterator("LineString");// �����߽ڵ�������ӽڵ�
				while (j.hasNext()) {
					LineSon = (Element) j.next();
					String nodeContent = "";
					nodeContent = LineSon.elementText("coordinates");// �õ�coordinates�ڵ�Ľڵ�����
					System.out.println("beforeRepace :" + nodeContent);
					nodeContent = nodeContent.replaceAll("\t|\n", " ");
					System.out.println("afterRepace :" + nodeContent);
					String nodeContentSplit[] = null;
					nodeContentSplit = nodeContent.split(" ");
					for (int it = 0; it < nodeContentSplit.length; it++) {
						String coordinateStr = nodeContentSplit[it];
						coordinate = makeCoordinate(coordinateStr);
						coordinateList.add(coordinate);// ��ÿһ��ʵ�����Ķ���洢��list��
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
				// Element LineSon;// �߽ڵ���ӽڵ�
				// Iterator j = node.elementIterator("LineString");//
				// �����߽ڵ�������ӽڵ�
				// while (j.hasNext()) {
				// LineSon = (Element) j.next();
				// String nodeContent = "";
				// nodeContent = LineSon.elementText("coordinates");//
				// �õ�coordinates�ڵ�Ľڵ�����
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
				// coordinateList.add(coordinate);// ��ÿһ��ʵ�����Ķ���洢��list��
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
			Element PolygonSon;// ����νڵ���ӽڵ�
			Iterator k = node.elementIterator("Polygon");// ����Polygon�ڵ�������ӽڵ�
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
					coordinateList.add(coordinate);// ��ÿһ��ʵ�����Ķ���洢��list��
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
		// ͬʱ������ǰ�ڵ�����������ӽڵ�
		// ʹ�õݹ�
		Iterator<Element> iterator = node.elementIterator();
		while (iterator.hasNext()) {
			Element e = iterator.next();
			listNodes(e);
		}
	}

	private static Coordinate makeCoordinate(String point) {
		Coordinate coordinate = null;
		String name = "";// Placemark�ڵ��е�name����
		String x = "";// ����x
		String y = "";// ����y
		String alt = "";// �߳�alt
		double d_x = 0.0;// ��x��string to double
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