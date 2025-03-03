public class Volunteer extends StaffMember {
    private double salary;
    public Volunteer( String name, String address, double salary) {
        super( name, address);
        this.salary = salary;
    }
    public  double getSalary(){
        return  salary;
    }
    public void setSalary(double salary){
        this.salary=salary;
    }
    @Override
    public double pay() {
        return getSalary();
    }
    public String getBonus(){
        return "...";
    }

    public String getRate(){
        return "...";
    }
    public  String getHour(){
        return "...";
    }
}
