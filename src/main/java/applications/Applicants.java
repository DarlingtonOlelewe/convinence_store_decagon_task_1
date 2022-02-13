package applications;

import enums.Gender;
import enums.Qualifications;
import enums.Role;
import model.Person;

public class Applicants extends Person {
    private Role role;
    private Qualifications cert;

    public Applicants(String firstName, String lastName, String email, Gender gender, Role role, Qualifications cert) {
        super(firstName, lastName, email, gender);
        this.role = role;
        this.cert = cert;
    }

    public Role getRole() {
        return role;
    }
    public Qualifications getCert(){
        return cert;
    }

    public void setRole(Role role) {
        this.role = role;
    }



    @Override
    public String toString() {
        return "Applicant {" +
                " firstName: "+ getFirstName() +
                " lastName: "+ getLastName() +
                " email: "+ getEmail() +
                " role: "+ getRole() +
                "qualification: "+ getCert() +
                '}';
    }
}
