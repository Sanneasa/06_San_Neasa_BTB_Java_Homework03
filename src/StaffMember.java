public abstract class StaffMember {
    private static int idCount = 0;
    private int id;
    private  String name ;
    private String address;
    public StaffMember( String name,String address) {
        this.id=++idCount;
        this.name=name;
        this.address=address;
    }
    public int getId(){
        return  id;
    }
    public String getName(){
        return  name;
    }
    public  void setName(String name){
        this.name=name;
    }
    public String getAddress(){
        return  address;
    }
    public  void setAddress(String address){
        this.address=address;
    }
    public double pay(){
        return 0;
    }
    public String toString(){
        return  "Name :"+name +"Address :"+address+"id"+id;
    }

}
