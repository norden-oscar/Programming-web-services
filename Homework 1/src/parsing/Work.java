/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsing;

/**
 *
 * @author norde_000
 */
public class Work {
    private String start;
    private String end;
    private String company;

    Work(String start, String end, String company){
        this.start = start;
        this.end = end;
        this.company = company;
        
    }
    /**
     * @return the start
     */
    public String getStart() {
        return start;
    }

    /**
     * @return the end
     */
    public String getEnd() {
        return end;
    }

    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }
}
