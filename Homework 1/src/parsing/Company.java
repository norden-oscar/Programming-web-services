package parsing;

import java.util.ArrayList;
import java.util.List;

public class Company {

	private String name;
	
	private ArrayList<Office> officeList;
	public Company(String name, ArrayList<Office> officeList) {
	
		this.name = name;

		this.officeList = officeList;
	}
	public String getName() {
		return name;
	}

	public ArrayList<Office> getOfficeList() {
		return officeList;
	}
	


	
}
