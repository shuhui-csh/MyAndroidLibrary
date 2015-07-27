/**
 * 
 */
package xmlparser;

/**
 * @author CSH 2015-7-27
 * 
 */
public class City {
	private String quName;
	private String pyName;
	private String cityname;
	private String stateDetailed;
	private int tem1;
	private String windState;

	public String getQuName() {
		return quName;
	}

	public void setQuName(String quName) {
		this.quName = quName;
	}

	public String getPyName() {
		return pyName;
	}

	public void setPyName(String pyName) {
		this.pyName = pyName;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getStateDetailed() {
		return stateDetailed;
	}

	public void setStateDetailed(String stateDetailed) {
		this.stateDetailed = stateDetailed;
	}

	public int getTem1() {
		return tem1;
	}

	public void setTem1(int tem1) {
		this.tem1 = tem1;
	}

	public String getWindState() {
		return windState;
	}

	public void setWindState(String windState) {
		this.windState = windState;
	}

	@Override
	public String toString() {
		return "City [quName=" + quName + ", pyName=" + pyName + ", cityname="
				+ cityname + ", stateDetailed=" + stateDetailed + ", tem1="
				+ tem1 + ", windState=" + windState + "]";
	}

}
