import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import javax.management.ValueExp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Display {
    public static Scanner scan = new Scanner(System.in);
    public static String ch;
    public static final String nameRegex = "^[A-Za-z\\s'-]+$";
    private static final String BONUS_REGEX = "^(?:0|[1-9][0-9]{0,5})(?:\\.[0-9]{1,2})?$";// Bonus validation
    public static final String SALARY_REGEX = "^(?:[2-9][0-9]{2,6}|1[0-9]{7})(?:\\.[0-9]{1,2})?$"; // Salary validation
    public static final String HOUR_REGEX = "^(?:1[0-6][0-8]|[0-9]|[1-9][0-9])$"; // Hour validation
    public static final String RATE_REGEX = "^(?:[0-9]|[1-9][0-9]{0,2})(?:\\.[0-9]{1,2})?$";

    // colors
    public static String RESET = "\u001B[0m";
    public static String RED = "\u001B[31m";
    public static String GREEN = "\u001B[32m";
    public static String YELLOW = "\u001B[33m";
    public static String BLUE = "\\u001B[34m\n";


    public static void show(){
        ArrayList<StaffMember> staffMembers = new ArrayList<>();
        staffMembers.add(new Volunteer( "San Neasa","PP",400.0));
        staffMembers.add(new Volunteer("Kimlong","BTB",330.0));
        staffMembers.add(new SalariesEmployee("Bunath","SR",300.0,10));
        staffMembers.add(new SalariesEmployee("Tha von","SR",240.0,10));
        staffMembers.add(new HourlySalaryEmployee("Deap Reaksmey","BTB",8,9));
        staffMembers.add(new HourlySalaryEmployee("Deap lina","BTB",9,10));

        do{
            CellStyle tableStyle = new CellStyle(CellStyle.HorizontalAlign.center);
            Table t = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
            t.setColumnWidth(0, 30, 30);
            t.addCell("STAFF MANAGEMENT SYSTEM",new CellStyle(CellStyle.HorizontalAlign.center));
            t.addCell("1. Insert Employee",new CellStyle(CellStyle.HorizontalAlign.center));
            t.addCell("2. Update Employee",new CellStyle(CellStyle.HorizontalAlign.center));
            t.addCell("3. Display Employee",new CellStyle(CellStyle.HorizontalAlign.center));
            t.addCell("4. Remove Employee",new CellStyle(CellStyle.HorizontalAlign.center));
            t.addCell("5. Exist",new CellStyle(CellStyle.HorizontalAlign.center));

            System.out.println(t.render());
            System.out.print("-> Choose an option():");
            ch= scan.nextLine();
            switch (ch){
                case "1":
                    insertEmployee(staffMembers);
                     break;
                case "2":
                    updateEmployee(staffMembers);
                    break;
                case "3":
                    displayAll(staffMembers);
                    break;
                case "4":
                    deleteFiled(staffMembers);
                    break;
                case "5":
                    System.out.println("Thanks you (^-^) Good Bye! (^-^)!!");
           }
        }while (!ch.equals("5"));
    }

    public static void displayAll(ArrayList<StaffMember> staffMembers){
        if(staffMembers == null || staffMembers.isEmpty()){
            System.out.println(RED + "No staff members to display" + RESET);
            return ;
        }
        CellStyle tableStyle = new CellStyle(CellStyle.HorizontalAlign.center);
        Table t = new Table(9, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
        t.setColumnWidth(0, 25, 25);
        t.setColumnWidth(1, 20, 20);
        t.setColumnWidth(2, 20, 20);
        t.setColumnWidth(3, 20, 20);
        t.setColumnWidth(4, 20, 20);
        t.setColumnWidth(5, 20, 20);
        t.setColumnWidth(6, 20, 20);
        t.setColumnWidth(7, 20, 20);
        t.setColumnWidth(8, 20, 20);
        t.addCell("TYPE",new CellStyle(CellStyle.HorizontalAlign.center));
        t.addCell("ID",new CellStyle(CellStyle.HorizontalAlign.center));
        t.addCell("NAME",new CellStyle(CellStyle.HorizontalAlign.center));
        t.addCell("ADDRESS",new CellStyle(CellStyle.HorizontalAlign.center));
        t.addCell("SALARY",new CellStyle(CellStyle.HorizontalAlign.center));
        t.addCell("BONUS",new CellStyle(CellStyle.HorizontalAlign.center));
        t.addCell("HOUR",new CellStyle(CellStyle.HorizontalAlign.center));
        t.addCell("RATE",new CellStyle(CellStyle.HorizontalAlign.center));
        t.addCell("PAY",new CellStyle(CellStyle.HorizontalAlign.center));

        staffMembers.forEach((staff) -> {
            t.addCell(staff.getClass().getName(), new CellStyle(CellStyle.HorizontalAlign.center));
            t.addCell(staff.getId() + "", new CellStyle(CellStyle.HorizontalAlign.center));
            t.addCell(staff.getName() , new CellStyle(CellStyle.HorizontalAlign.center));
            t.addCell(staff.getAddress() , new CellStyle(CellStyle.HorizontalAlign.center));
            if(staff instanceof Volunteer){
                t.addCell(((Volunteer) staff).getSalary() + "", new CellStyle(CellStyle.HorizontalAlign.center));
                t.addCell( "---", new CellStyle(CellStyle.HorizontalAlign.center));
                t.addCell( "---", new CellStyle(CellStyle.HorizontalAlign.center));
                t.addCell( "---", new CellStyle(CellStyle.HorizontalAlign.center));
                t.addCell( staff.pay()+ "", new CellStyle(CellStyle.HorizontalAlign.center));
            }if(staff instanceof SalariesEmployee){
                t.addCell(((SalariesEmployee) staff).getSalary() + "", new CellStyle(CellStyle.HorizontalAlign.center));
                t.addCell( ((SalariesEmployee) staff).getBonus()+ "", new CellStyle(CellStyle.HorizontalAlign.center));
                t.addCell( "---", new CellStyle(CellStyle.HorizontalAlign.center));
                t.addCell( "---", new CellStyle(CellStyle.HorizontalAlign.center));
                t.addCell( staff.pay() +"", new CellStyle(CellStyle.HorizontalAlign.center));
            }if(staff instanceof HourlySalaryEmployee){
                t.addCell( "---", new CellStyle(CellStyle.HorizontalAlign.center));
                t.addCell( "---", new CellStyle(CellStyle.HorizontalAlign.center));
                t.addCell( ((HourlySalaryEmployee) staff).getHourWorked()+"", new CellStyle(CellStyle.HorizontalAlign.center));
                t.addCell( ((HourlySalaryEmployee) staff).getRate() +"", new CellStyle(CellStyle.HorizontalAlign.center));
                t.addCell( staff.pay() +"", new CellStyle(CellStyle.HorizontalAlign.center));

            }
        });
        System.out.println(t.render());

    }

    public static void updateEmployee(ArrayList<StaffMember> staffMembers){
        do{
            System.out.println("Choose one column to update :");
            System.out.print("1. Name  ");
            System.out.print("2. Address  ");
            System.out.print("3. Salary  ");
            System.out.print("4. Bonus  ");
            System.out.print("5. Hours  ");
            System.out.print("6. Rate  ");
            System.out.print("0. Cancel  \n");
            System.out.print(" => Select Column Number :");
            ch = scan.nextLine();
            switch (ch){
                case "1":
                    updateFiled(staffMembers,"name");
                    break;
                case "2":
                    updateFiled(staffMembers,"address");
                    break;
                case "3":
                    updateFiled(staffMembers,"salary");
                case "4":
                    updateFiled(staffMembers,"bonus");
                case "5":
                    updateFiled(staffMembers,"hour");
                case "6":
                    updateFiled(staffMembers,"rate");
                    break;
                case "0":
                    System.out.println("Update cancelled.");
                    return;
                default:
                    System.out.println(RED + "Invalid option! Please enter 0-6." + RESET);
            }
        }while (!ch.equals("0"));
    }
    public static StaffMember searchById(ArrayList<StaffMember> staffMembers,int targetID, Class<?> targetType){
         if(staffMembers == null || staffMembers.isEmpty()){
             return null;
         }
        Optional<StaffMember> result = staffMembers.stream()
                .filter(staffMember -> staffMember.getId()== targetID)
                .filter(staffMember -> targetType == null || targetType.isInstance(staffMember))
                .findFirst();
         return result.orElse(null);
    }


    // Deleted
    public static void deleteFiled(ArrayList<StaffMember> staffMembers){
        if (staffMembers == null || staffMembers.isEmpty()) {
            System.out.println(RED + "No staff members found to delete" + RESET);
            return;
        }
        System.out.print("=> Enter the ID of the staff member to delete: ");
        int targetId;
        try {
            targetId = Integer.parseInt(scan.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(RED + "Invalid ID! Please enter a valid integer." + RESET);
            return;
        }

        StaffMember memberToDelete = searchById(staffMembers, targetId, null);
        if (memberToDelete == null) {
            return;
        }

        System.out.println("Found: " + memberToDelete.toString());
        int sizeBefore = staffMembers.size();
        staffMembers.removeIf(staff -> staff.getId() == targetId);
        int sizeAfter = staffMembers.size();
        if (sizeBefore > sizeAfter) {
            System.out.println("Staff member with ID " + targetId + " deleted successfully.");
        } else {
            System.out.println(RED + "Failed to delete staff member with ID: " + targetId + " (possible duplicate or error)" + RESET);
        }

    }

    // update Method
    public static void updateFiled(ArrayList<StaffMember> staffMembers ,String field){
            if(staffMembers == null || staffMembers.isEmpty()){
                System.out.println(RED+"No staff numbers found to update "+RESET);
                return ;
            }
            System.out.print("=> Enter the ID of the staff member to update: ");
            int targetId;
        try {
            targetId = Integer.parseInt(scan.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(RED + "Invalid ID! Please enter a valid integer." + RESET);
            return;
        }
        StaffMember memberToUpdate = searchById(staffMembers, targetId, null);
        if(memberToUpdate==null){
            System.out.println(RED + "No staff member found with ID: " + targetId + RESET);
            return;
        }
        switch (field.toLowerCase()){
            case "name":
                System.out.println("Current name: " + memberToUpdate.getName());
                System.out.print(" => Enter new name :");
                String newName = scan.nextLine().trim();
                if(!newName.isEmpty()){
                    String nameRegex = "^[a-zA-Z\\s'-]{2,50}$";
                    if(newName.matches(nameRegex)){
                        memberToUpdate.setName(newName);
                        System.out.println("Name updated successfully to : " + memberToUpdate.getName());

                    }else{
                        System.out.println(RED + "Invalid name! Use only letters, spaces, hyphens, or apostrophes (2-50 characters)" + RESET);
                    }
                }else{
                    System.out.println("No changes made.");
                }

                break;
            case "address":
                System.out.print(" => Please enter new address :");
                String newAddress = scan.nextLine();
                if(!newAddress.isEmpty()){
                    String addressRegex = "^[a-zA-Z\\s'-]{2,50}$";
                    if(newAddress.matches(addressRegex)){
                        memberToUpdate.setAddress(newAddress);
                        System.out.println("Address updated successfully to : " + memberToUpdate.getAddress());
                    }else{
                        System.out.println(RED + "Invalid address! Use only letters, spaces, hyphens, or apostrophes (2-50 characters)" + RESET);
                    }
                }else{
                    System.out.println("No changes made.");
                }
                break;

            case"salary":
                if(memberToUpdate instanceof SalariesEmployee || memberToUpdate instanceof Volunteer){
                    double currentSalary = (memberToUpdate instanceof  Volunteer)
                            ?((Volunteer) memberToUpdate).getSalary()
                            :((SalariesEmployee) memberToUpdate).getSalary();
                    System.out.println("Current Salary :"+currentSalary);
                    System.out.print("=> Enter new salary :");
                    String newSalaryStr = scan.nextLine();
                    if(!newSalaryStr.isEmpty()){
                        if(newSalaryStr.matches(SALARY_REGEX)){
                            double newSalary =Double.parseDouble(newSalaryStr);
                            if(newSalary > 200 &&  newSalary <= 10000000){
                                if(memberToUpdate instanceof  Volunteer){
                                    ((Volunteer) memberToUpdate).setSalary(newSalary);
                                }else {
                                    ((SalariesEmployee)memberToUpdate).setSalary(newSalary);
                                }
                                System.out.println("Salary updated successfully to: " + newSalary);
                            }else{
                                System.out.println(RED + "Salary must be between $200 and $10,000,000" + RESET);
                            }
                        }else{
                            System.out.println(RED + "Invalid salary format!" + RESET);
                        }
                    }else{
                        System.out.println("No changes made.");
                    }
                }else{
                    System.out.println(RED + " Salary update not applicable for this type." + RESET);
                }
                break;

            case"bonus":
                if(memberToUpdate instanceof  SalariesEmployee){
                    System.out.println( ((SalariesEmployee)memberToUpdate).getBonus());
                    System.out.print(" => Enter new bonus :");
                    String newBonusStr =scan.nextLine().trim();
                    if(!newBonusStr.isEmpty()){
                        if(newBonusStr.matches(BONUS_REGEX)){
                            double newBonus = Double.parseDouble(newBonusStr);
                            if(newBonus > 0 &&  newBonus <= 999999.0){
                                ((SalariesEmployee) memberToUpdate).setBonus(newBonus);
                                System.out.println("Bonus updated successfully to: " + newBonus);
                            }else{
                                System.out.println(RED + "Bonus must be between 0 and 999999.99" + RESET);
                            }
                        }else{
                            System.out.println(RED + "Invalid bonus format!" + RESET);
                        }

                        }else{
                        System.out.println("No changes made.");
                    }

                }else{
                    System.out.println(RED + "Bonus update not applicable for this type." + RESET);
                }
                break;

            case "hour":
                if(memberToUpdate instanceof  HourlySalaryEmployee){
                    System.out.println(" Current Hour :" +((HourlySalaryEmployee) memberToUpdate).getHour());
                    System.out.print("=> Enter new hour :");
                    String  newHourStr = scan.nextLine().trim();
                    if(!newHourStr.isEmpty()){
                        if(newHourStr.matches(HOUR_REGEX)){
                            int  newHour = Integer.parseInt(newHourStr);
                            ((HourlySalaryEmployee) memberToUpdate).setHourWorked(newHour);
                            System.out.println("Hours updated successfully to: " + newHour);
                        }else{
                            System.out.println(RED + "Invalid hours! Must be between 0 and 168" + RESET);
                        }
                    }else{
                        System.out.println("No changes made.");
                    }

                }else{
                    System.out.println(RED + "Hour update not applicable for this type." + RESET);
                }
                break;

            case"rate":
                if(memberToUpdate instanceof  HourlySalaryEmployee){
                    System.out.println("Current Rate :"+((HourlySalaryEmployee) memberToUpdate).getRate());
                    System.out.print("=> Enter new rate :");
                    String newRateStr = scan.nextLine();
                    if(!newRateStr.isEmpty()){
                        if(newRateStr.matches(RATE_REGEX)){
                            int newRate = Integer.parseInt(newRateStr);
                            if(newRate > 0 && newRate < 900000.0){
                                ((HourlySalaryEmployee) memberToUpdate).setRate(newRate);
                                System.out.println("Rate updated successfully to: " + newRate);
                            }
                        }else{
                            System.out.println(RED + "Rate must be between 0.01 and 999.99" + RESET);
                        }
                    }else{
                        System.out.println(RED + "Invalid rate! Must be between 0 and 168" + RESET);
                    }
                }else{
                    System.out.println("No changes made.");
                }
                break;
            default:
                System.out.println(RED + "Invalid field selection!" + RESET);
        }
    }

    public static void insertEmployee(ArrayList<StaffMember> staffMembers){
        do {
            System.out.println(" => Choose Employee Type ");
            System.out.println("==========================* Insert Employee *==========================");
            CellStyle tableStyle = new CellStyle(CellStyle.HorizontalAlign.center);
            Table t = new Table(4, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
            t.setColumnWidth(0, 25, 25);
            t.setColumnWidth(1, 20, 20);
            t.setColumnWidth(2, 20, 20);
            t.setColumnWidth(3, 20, 20);
            t.addCell("1.Volunteer",new CellStyle(CellStyle.HorizontalAlign.center));
            t.addCell("2.Salary Employee",new CellStyle(CellStyle.HorizontalAlign.center));
            t.addCell("3.Hourly Employee",new CellStyle(CellStyle.HorizontalAlign.center));
            t.addCell("4.Back",new CellStyle(CellStyle.HorizontalAlign.center));

            System.out.println(t.render());
            System.out.print("Please enter type number : ");
            ch = scan.nextLine();
            switch (ch) {
                case "1":
                    displayVolunteer(staffMembers);
                    break;
                case "2":
                    displaySalaryEmployee(staffMembers);
                    break;
                case "3":
                    displayHourlyEmployee(staffMembers);
                    break;
                case "4":
                      return;
            }
        }while (!ch.equals("4"));
    }

    public static void displayVolunteer( ArrayList<StaffMember> staffMembers){
        String name ;
        String address ;
        double salary ;
        Volunteer volunteer = new Volunteer("","",0.0);
        System.out.println("ID : "+volunteer.getId());

        // valid name
        while (true){
            try{
                System.out.print("=> Enter name :");
                name = scan.nextLine();
                Pattern namePattern = Pattern.compile(nameRegex);
                Matcher nameMatcher = namePattern.matcher(name);
                if(!nameMatcher.matches()){
                    throw new IllegalArgumentException(RED + "Name should contain only letters, spaces, hyphens, or apostrophes" + RESET);
                }
                if(name.trim().isEmpty()){
                    throw new IllegalArgumentException(RED + "Name cannot be empty" + RESET);
                }
                break;

            }catch (IllegalArgumentException e){
                System.out.println("Error :" + e.getMessage());
            }

        }

        // valid address
        while (true){
            try{
                System.out.print("=> Enter Address :");
                address = scan.nextLine();
                Pattern addressPattern = Pattern.compile(nameRegex);
                Matcher addressMatcher = addressPattern.matcher(address);
                if(!addressMatcher.matches()){
                    throw new IllegalAccessException(RED+"Address contains invalid characters"+RESET);
                }
                if(address.trim().isEmpty()){
                    throw  new IllegalAccessException(RED+"Address cannot be empty"+RESET);
                }
                break;
            }catch (IllegalAccessException e){
                System.out.println("Error :"+ e.getMessage());
            }
        }

        // Valid Salary
        while (true){
            try {
                System.out.print("=> Enter Salary :");
                salary = scan.nextDouble();
                if(salary <200 || salary >10000000){
                    throw new IllegalArgumentException(RED+"Salary must be between $2,00 and $10,000,000"+RESET);
                }
                break;

            }catch (IllegalArgumentException e){
                System.out.println("Error :" + e.getMessage());
            }

        }
        System.out.println("* You added "+name+" of type " +volunteer.getClass().getName() + " Successfully!");
        volunteer.setName(name);
        volunteer.setAddress(address);
        volunteer.setSalary(salary);
        staffMembers.add(volunteer);

    }

    public static void displaySalaryEmployee( ArrayList<StaffMember> staffMembers){
        String nameSalariesEmployee;
        String addressSalariesEmployee;
        double salaryEmployee;
        double bonusSalariesEmployee;
        SalariesEmployee salariesEmployee = new SalariesEmployee("","",0.0,0.0);
        System.out.print("ID :"+salariesEmployee.getId());

        // Valid name
        while (true){
            try{
                System.out.print(" => Enter name :");
                nameSalariesEmployee = scan.nextLine();
                Pattern namePattern = Pattern.compile(nameRegex);
                Matcher nameMatcher = namePattern.matcher(nameSalariesEmployee);
                if(!nameMatcher.matches()){
                    throw new IllegalArgumentException(RED + "Name should contain only letters, spaces, hyphens, or apostrophes" + RESET);
                }if(nameSalariesEmployee.trim().isEmpty()){
                    throw new IllegalArgumentException(RED + "Name cannot be empty" + RESET);
                }
                break;
            }catch (IllegalArgumentException e){
                System.out.println("Error :"+e.getMessage());
            }
        }

        //valid address
        while (true){
            try{
                System.out.print("=> Enter address :");
                addressSalariesEmployee = scan.nextLine();
                Pattern addressPattern = Pattern.compile(nameRegex);
                Matcher nameMatcher = addressPattern.matcher(addressSalariesEmployee);
                if(!nameMatcher.matches()){
                    throw new IllegalArgumentException(RED+"Address contains invalid characters"+RESET);
                }if(addressSalariesEmployee.trim().isEmpty()){
                    throw  new IllegalArgumentException(RED+"Address cannot be empty"+RESET);
                }
                break;
            }catch (IllegalArgumentException e){
                System.out.println("Error :"+e.getMessage());
            }
        }

        // Valid bonus
        while (true) {
            try {
                System.out.print("=> Enter bonus :");
                String bonusInput = scan.nextLine();
                Pattern bonusPattern = Pattern.compile(BONUS_REGEX);
                Matcher bonusMatcher = bonusPattern.matcher(bonusInput);
                if (!bonusMatcher.matches()) {
                    throw new IllegalArgumentException(
                            RED + "Bonus must be between 0 and 999999.99" + RESET
                    );
                }
                bonusSalariesEmployee = Double.parseDouble(bonusInput);
                if (bonusSalariesEmployee < 0 || bonusSalariesEmployee > 999999.99) {
                    throw new IllegalArgumentException(
                            RED + "Bonus must be between 0 and 999999.99" + RESET
                    );
                }
                salariesEmployee.setBonus(bonusSalariesEmployee); // Update object
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        // Valid Salary
        while (true) {
            try {
                System.out.print("=> Enter Salary: ");
                String salaryInput = scan.nextLine();
                Pattern salaryPattern = Pattern.compile("^(?:[2-9][0-9]{2,6}|1[0-9]{7})(?:\\.[0-9]{1,2})?$");
                Matcher salaryMatcher = salaryPattern.matcher(salaryInput);
                if (!salaryMatcher.matches()) {
                    throw new IllegalArgumentException(
                            RED + "Salary must be between $200 and $10,000,000" + RESET
                    );
                }
                salaryEmployee = Double.parseDouble(salaryInput);
                if (salaryEmployee < 200 || salaryEmployee > 10000000) {
                    throw new IllegalArgumentException(
                            RED + "Salary must be between $200 and $10,000,000" + RESET
                    );
                }
                salariesEmployee.setSalary(salaryEmployee);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("* You added "+nameSalariesEmployee+" of type " +salariesEmployee.getClass().getName() + " Successfully!");

        salariesEmployee.setName(nameSalariesEmployee);
        salariesEmployee.setAddress(addressSalariesEmployee);
        salariesEmployee.setSalary(salaryEmployee);
        salariesEmployee.setBonus(bonusSalariesEmployee);
        staffMembers.add(salariesEmployee);


    }

    public static void displayHourlyEmployee(ArrayList<StaffMember> staffMembers){
        String nameHourly;
        String addressHourly;
        int hourWorks;
        double rate;
        String hourRegex = "^(?:1[0-6][0-8]|[0-9]|[1-9][0-9])$";

        HourlySalaryEmployee hourlySalaryEmployee = new HourlySalaryEmployee("","",0,0.0);
        System.out.print("ID :"+hourlySalaryEmployee.getId());

        // valid name
        while (true){
            try{
                System.out.print("=> Enter name :");
                nameHourly = scan.nextLine();
                Pattern namePattern = Pattern.compile(nameRegex);
                Matcher nameMatcher = namePattern.matcher(nameHourly);
                if(!nameMatcher.matches()){
                    throw new IllegalArgumentException(RED + "Name should contain only letters, spaces, hyphens, or apostrophes" + RESET);
                }if(nameHourly.trim().isEmpty()){
                    throw new IllegalArgumentException(RED + "Name cannot be empty" + RESET);
                }
                break;
            }catch (IllegalArgumentException e){
                System.out.println("Error :"+e.getMessage());
            }
        }
        // valid address
        while (true){
            try{
                System.out.print("=> Enter address :");
                addressHourly = scan.nextLine();
                Pattern addressPattern = Pattern.compile(nameRegex);
                Matcher nameMatcher = addressPattern.matcher(addressHourly);
                if(!nameMatcher.matches()){
                    throw new IllegalArgumentException(RED+"Address contains invalid characters"+RESET);
                }if(addressHourly.trim().isEmpty()){
                    throw  new IllegalArgumentException(RED+"Address cannot be empty"+RESET);
                }
                break;
            }catch (IllegalArgumentException e){
                System.out.println("Error :"+e.getMessage());
            }
        }

        // Valid hour work
        while (true){
            try{
                System.out.print("=> Enter hour :");
                hourWorks = scan.nextInt();
                scan.nextLine();
                String hourStr = String.valueOf(hourWorks);
                Pattern  hourPattern = Pattern.compile(hourRegex);
                Matcher  hourmatcher = hourPattern.matcher(hourStr);
                if(!hourmatcher.matches()){
                    throw new IllegalArgumentException(RED+"Invalid hours! Must be between 0 and 168"+RESET);
                }
                break;
            }catch (IllegalArgumentException e){
                System.out.println("Error :"+e.getMessage());
            }
        }

        // Valid rate
        while (true){
            try{
                System.out.print("=> Enter rate :");
                rate = scan.nextDouble();
                String rateStr = String.valueOf(rate);
                if(isValidRate(rateStr)){
                    break;
                }else{
                    System.out.println(RED+"Invalid rate! Enter a positive number between 0.01 and 999.99"+RESET);
                }
            }catch (Exception e){
                System.out.println(RED+"Invalid input! Please enter a valid number :"+e.getMessage()+RESET);
            }
        }

        System.out.println("* You added "+hourlySalaryEmployee+" of type " +hourlySalaryEmployee.getClass().getName() + " Successfully!");

        hourlySalaryEmployee.setName(nameHourly);
        hourlySalaryEmployee.setAddress(addressHourly);
        hourlySalaryEmployee.setHourWorked(hourWorks);
        hourlySalaryEmployee.setRate(rate);
        staffMembers.add(hourlySalaryEmployee);
    }
    public static boolean isValidRate(String rate){
        String rateRegex = "^(?:[0-9]|[1-9][0-9]{0,2})(?:\\.[0-9]{1,2})?$";
        Pattern pattern = Pattern.compile(rateRegex);
        Matcher matcher = pattern.matcher(rate);
        boolean matches = matcher.matches();
        if(matches){
            double value = Double.parseDouble(rate);
            return  value >=0.01 && value <= 999.99;
        }
        return  false;
    }



}
