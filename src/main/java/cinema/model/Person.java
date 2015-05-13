package cinema.model;

/**
 * Created by Daniel on 07.05.2015.
 */
public class Person {

    public Person(){}

    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
/*
    @Override
    public String toString(){
        return "{" +
                "firstName= " + firstName.toString() + "," +
                "lastName= " + lastName.toString() +
                "}";

    }*/
}
