package companyJaxRes;

import java.util.ArrayList;
import java.util.List;

public class Company {

	private String name;
	private String founded;
	private ArrayList<Office> officeList;
	public Company(String name, String founded, ArrayList<Office> officeList) {
	
		this.name = name;
		this.founded = founded;
		this.officeList = officeList;
	}
	public String getName() {
		return name;
	}

	public ArrayList<Office> getOfficeList() {
		return officeList;
	}
	
	public String getFounded(){
		return founded;
	}


	
}
