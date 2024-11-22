import java.util.ArrayList;
import java.util.List;

class RoomBookingSystem {
    public void bookRoom() { System.out.println("Room has been booked."); }
    public void checkAvailability() { System.out.println("Checking room availability..."); }
    public void cancelBooking() { System.out.println("Room booking has been canceled."); }
}

class RestaurantSystem {
    public void bookTable() { System.out.println("Table has been booked at the restaurant."); }
    public void orderFood() { System.out.println("Food order has been placed."); }
    public void callTaxi() { System.out.println("Taxi has been called."); }
}

class EventManagementSystem {
    public void bookConferenceRoom() { System.out.println("Conference room has been booked."); }
    public void orderEquipment() { System.out.println("Equipment has been ordered for the event."); }
}

class CleaningService {
    public void scheduleCleaning() { System.out.println("Room cleaning has been scheduled."); }
    public void performCleaning() { System.out.println("Room cleaning is in progress."); }
}

class HotelFacade {
    private RoomBookingSystem roomBooking;
    private RestaurantSystem restaurant;
    private EventManagementSystem eventManagement;
    private CleaningService cleaningService;

    public HotelFacade() {
        roomBooking = new RoomBookingSystem();
        restaurant = new RestaurantSystem();
        eventManagement = new EventManagementSystem();
        cleaningService = new CleaningService();
    }

    public void bookRoomWithServices() {
        System.out.println("Booking room with restaurant and cleaning services...");
        roomBooking.bookRoom();
        restaurant.orderFood();
        cleaningService.scheduleCleaning();
    }

    public void organizeEvent() {
        System.out.println("Organizing event with conference room and equipment booking...");
        roomBooking.bookRoom();
        eventManagement.bookConferenceRoom();
        eventManagement.orderEquipment();
    }

    public void bookTableWithTaxi() {
        System.out.println("Booking a table with taxi service...");
        restaurant.bookTable();
        restaurant.callTaxi();
    }

    public void cancelRoomBooking() {
        System.out.println("Cancelling room booking and cleaning services...");
        roomBooking.cancelBooking();
        cleaningService.performCleaning();
    }

    public void requestCleaning() {
        System.out.println("Requesting cleaning service...");
        cleaningService.performCleaning();
    }
}


abstract class OrganizationComponent {
    protected String name;
    protected String position;
    protected double salary;

    public OrganizationComponent(String name, String position, double salary) {
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    public abstract void display(int depth);
    public abstract double calculateBudget();
    public abstract int countEmployees();

    public void add(OrganizationComponent component) { throw new UnsupportedOperationException(); }
    public void remove(OrganizationComponent component) { throw new UnsupportedOperationException(); }
    public OrganizationComponent getChild(int index) { throw new UnsupportedOperationException(); }

    public String getName() { return name; }
    public void setSalary(double newSalary) { this.salary = newSalary; }
    public double getSalary() { return salary; }
}

class Employee extends OrganizationComponent {
    public Employee(String name, String position, double salary) {
        super(name, position, salary);
    }

    @Override
    public void display(int depth) {
        System.out.println(" ".repeat(depth) + "- " + position + ": " + name + ", Salary: " + salary);
    }

    @Override
    public double calculateBudget() { return salary; }

    @Override
    public int countEmployees() { return 1; }
}

class Contractor extends Employee {
    public Contractor(String name, String position, double salary) {
        super(name, position, salary);
    }

    @Override
    public double calculateBudget() { return 0; }
}

class Department extends OrganizationComponent {
    private List<OrganizationComponent> components = new ArrayList<>();

    public Department(String name) {
        super(name, "Department", 0);
    }

    @Override
    public void add(OrganizationComponent component) {
        components.add(component);
    }

    @Override
    public void remove(OrganizationComponent component) {
        components.remove(component);
    }

    @Override
    public OrganizationComponent getChild(int index) {
        return components.get(index);
    }

    @Override
    public void display(int depth) {
        System.out.println(" ".repeat(depth) + "- " + name + " Department:");
        for (OrganizationComponent component : components) component.display(depth + 2);
    }

    @Override
    public double calculateBudget() {
        double totalBudget = 0;
        for (OrganizationComponent component : components) {
            totalBudget += component.calculateBudget();
        }
        return totalBudget;
    }

    @Override
    public int countEmployees() {
        int totalEmployees = 0;
        for (OrganizationComponent component : components) {
            totalEmployees += component.countEmployees();
        }
        return totalEmployees;
    }

    public void displayAllEmployees() {
        for (OrganizationComponent component : components) {
            component.display(2);
        }
    }

    public OrganizationComponent findEmployee(String employeeName) {
        for (OrganizationComponent component : components) {
            if (component instanceof Employee && component.getName().equalsIgnoreCase(employeeName)) {
                return component;
            } else if (component instanceof Department) {
                OrganizationComponent found = ((Department) component).findEmployee(employeeName);
                if (found != null) return found;
            }
        }
        return null;
    }
}

 class Main {
    public static void main(String[] args) {
        // Facade Pattern Usage
        HotelFacade hotelFacade = new HotelFacade();
        hotelFacade.bookRoomWithServices();
        System.out.println();
        hotelFacade.organizeEvent();
        System.out.println();
        hotelFacade.bookTableWithTaxi();
        System.out.println();
        hotelFacade.cancelRoomBooking();
        System.out.println();
        hotelFacade.requestCleaning();

        System.out.println("\n--- Corporate Hierarchy ---");


        Employee emp1 = new Employee("Alice", "Engineer", 70000);
        Employee emp2 = new Employee("Bob", "Analyst", 65000);
        Employee emp3 = new Employee("Charlie", "Manager", 90000);
        Contractor contractor1 = new Contractor("David", "Consultant", 50000);

        Department engineering = new Department("Engineering");
        engineering.add(emp1);
        engineering.add(emp2);
        engineering.add(contractor1);

        Department hr = new Department("HR");
        hr.add(emp3);

        Department company = new Department("Headquarters");
        company.add(engineering);
        company.add(hr);

        company.display(1);
        System.out.println("Total Budget: " + company.calculateBudget());
        System.out.println("Total Employees: " + company.countEmployees());

        OrganizationComponent foundEmployee = company.findEmployee("Alice");
        if (foundEmployee != null) {
            System.out.println("\nEmployee found: " + foundEmployee.getName() + ", Position: " + foundEmployee.position + ", Salary: " + foundEmployee.getSalary());
        }

        System.out.println("\nAll Employees in Engineering Department:");
        engineering.displayAllEmployees();
    }
}
