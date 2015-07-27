/**
 * 
 */
package xmlparser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

/**
 * @author CSH 2015-7-27 （更换实体对象City可重用该工具解析xml数据）
 */
public class XmlPullParserTools {

	/**
	 * 根据输入的输入流按照一定的编码格式去解析XML数据
	 * 
	 * @param inputStream
	 *            输入流
	 * @param encode
	 *            字符编码
	 * @return
	 */
	public static List<City> ParserXml(InputStream inputStream, String encode) {
		List<City> list = null;
		City city = null;

		try {
			// 创建一个Xml解析工厂
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			// 获得一个parser类的实例引用
			XmlPullParser parser = factory.newPullParser();
			// 为解析类设置需要解析的输入流
			parser.setInput(inputStream, encode);
			// 获得事件的类型
			int evenType = parser.getEventType();
			while (evenType != XmlPullParser.END_DOCUMENT) {
				switch (evenType) {
				case XmlPullParser.START_DOCUMENT:
					// 当是文档开始标签的时候，初始化list列表
					list = new ArrayList<City>();
					break;
				case XmlPullParser.START_TAG:
					if ("city".equals(parser.getName())) {
						city = new City();
						// city标签节点下取出第一个属性值，一个标签结点可有多个属性值
						String quName = parser.getAttributeValue(0);
						city.setQuName(quName);
					} else if ("cityname".equals(parser.getName())) {
						String cityname = parser.nextText();
						city.setCityname(cityname);
					} else if ("tem1".equals(parser.getName())) {
						int tem1 = Integer.parseInt(parser.nextText());
						city.setTem1(tem1);
					} else if ("windState".equals(parser.getName())) {
						String windState = parser.nextText();
						city.setWindState(windState);
					}
					break;
				case XmlPullParser.END_TAG:
					Log.i("VolleyActivity", "add(city)");
					list.add(city);
					// city = null;
					break;
				default:
					break;
				}
				evenType = parser.next();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * 结合vorry使用的解析含多个标签结点的XML数据
	 * 
	 * @param parser
	 *            XmlPullParser解析器
	 * @return 一个List集合
	 */
	public static List<City> ParserXml(XmlPullParser parser) {
		List<City> list = null;
		City city = null;
		try {
			// 获得事件的类型
			int evenType = parser.getEventType();
			while (evenType != XmlPullParser.END_DOCUMENT) {
				switch (evenType) {
				case XmlPullParser.START_DOCUMENT:
					// 当是文档开始标签的时候，初始化list列表
					list = new ArrayList<City>();
					break;
				case XmlPullParser.START_TAG:
					if ("city".equals(parser.getName())) {
						city = new City();
						// city标签节点下取出第一个属性值，一个标签结点可有多个属性值
						String quName = parser.getAttributeValue(0);
						city.setQuName(quName);
					} else if ("cityname".equals(parser.getName())) {
						String cityname = parser.nextText();
						city.setCityname(cityname);
					} else if ("tem1".equals(parser.getName())) {
						int tem1 = Integer.parseInt(parser.nextText());
						city.setTem1(tem1);
					} else if ("windState".equals(parser.getName())) {
						String windState = parser.nextText();
						city.setWindState(windState);
					}
					break;
				case XmlPullParser.END_TAG:
					Log.i("VolleyActivity", "add(city)");
					list.add(city);
					// city = null;
					break;
				default:
					break;
				}
				evenType = parser.next();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * 结合vorry，解析一种不算标准的xml格式的数据,一个标签里面包含多个属性
	 * 
	 * @param parser
	 * @return
	 */
	public static List<City> ParserXmlSpecial(XmlPullParser parser) {
		List<City> list = null;
		City city = null;
		try {
			// 获得事件的类型
			int evenType = parser.getEventType();
			while (evenType != XmlPullParser.END_DOCUMENT) {
				switch (evenType) {
				case XmlPullParser.START_DOCUMENT:
					// 当是文档开始标签的时候，初始化list列表
					list = new ArrayList<City>();
					break;
				case XmlPullParser.START_TAG:
					// 这个中国天气网返回来的xml数据，只有以city为标签的tab,这个city标签里有不同的的属性：quName、pyName等
					if ("city".equals(parser.getName())) {
						city = new City();
						// 获取第一个属性值
						String quName = parser.getAttributeValue(0);
						city.setQuName(quName);
						// 获取第二个属性值
						String pyName = parser.getAttributeValue(1);
						city.setPyName(pyName);
						// 获取第三个属性值
						String cityname = parser.getAttributeValue(2);
						city.setCityname(cityname);
						// 获取第六个属性值
						String stateDetailed = parser.getAttributeValue(5);
						city.setStateDetailed(stateDetailed);
						// 获取第七个属性值
						int tem1 = Integer
								.parseInt(parser.getAttributeValue(6));
						city.setTem1(tem1);
						// 获取第九个属性值
						String windState = parser.getAttributeValue(8);
						city.setWindState(windState);
					}
					break;
				case XmlPullParser.END_TAG:
					// list添加数据后，city变量指向谁？可否置空了
					list.add(city);
					// city = null;
					break;
				default:
					break;
				}
				evenType = parser.next();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}
}
