package Model;

public class Renters {

    String Address, ContactNo, Name, State,HomeId;

    public Renters(String address) {
        Address = address;
    }

    public Renters() {
    }

    public Renters(String address, String contactNo, String homeid,String name,String state) {
        Address = address;
        ContactNo = contactNo;
        Name = name;
        State = state;
        HomeId=homeid;


    }

    public String getAddress() {
        return Address;
    }
    public String getHomeId() {
        return HomeId;
    }


    public void setAddress(String address) {
        Address = address;
    }
    public void setHomeId(String homeid) {
        HomeId = homeid;
    }


    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }
}
