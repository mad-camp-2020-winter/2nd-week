package com.example.rentalservice;

public class ListViewItem {
    private String institution_name;
    private String institution_number;
    private String institution_location;
    private String institution_id;

    public void setInstitution_name(String name){ institution_name = name ; }
    public void setInstitution_number(String number){ institution_number = number ; }
    public void setInstitution_location(String location) { institution_location = location ; }
    public void setInstitution_id(String id){ institution_id = id ; }

    public String getInstitution_name(){ return this.institution_name ; }
    public String getInstitution_number(){return this.institution_number ; }
    public String getInstitution_location(){return this.institution_location ; }
    public String getInstitution_id(){return this.institution_id ; }
}
