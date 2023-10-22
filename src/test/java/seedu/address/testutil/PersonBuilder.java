package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Ethnicity;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_GENDER = "M";
    public static final int DEFAULT_AGE = 21;
    public static final String DEFAULT_ETHNIC = "Chinese";
    public static final String DEFAULT_NRIC = "T0123456E";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final Set<Tag> DEFAULT_TAG = Collections.emptySet();

    private Name name;
    private Phone phone;
    private Email email;
    private Gender gender;
    private Age age;
    private Ethnicity ethnic;
    private Nric nric;
    private Address address;
    private Set<Tag> tags;
    private ArrayList<Appointment> appointments;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        gender = new Gender(DEFAULT_GENDER);
        age = new Age(DEFAULT_AGE);
        ethnic = new Ethnicity(DEFAULT_ETHNIC);
        nric = new Nric(DEFAULT_NRIC);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>(DEFAULT_TAG);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        gender = personToCopy.getGender();
        age = personToCopy.getAge();
        ethnic = personToCopy.getEthnic();
        nric = personToCopy.getNric();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
        appointments = new ArrayList<>(personToCopy.getAppointments());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code appointments} into a {@code ArrayList<Appointment>}
     * and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withAppointments(String ... appointments) {
        this.appointments = TypicalAppointments.getTypicalAppointments();
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }
    /**
     * Sets the {@code Gender} of the {@code Person} that we are building.
     */
    public PersonBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }
    /**
     * Sets the {@code Age} of the {@code Person} that we are building.
     */
    public PersonBuilder withAge(int age) {
        this.age = new Age(age);
        return this;
    }
    /**
     * Sets the {@code Ethnic} of the {@code Person} that we are building.
     */
    public PersonBuilder withEthnic(String ethnic) {
        this.ethnic = new Ethnicity(ethnic);
        return this;
    }


    /**
     * Sets the {@code NRIC} of the {@code Person} that we are building.
     */
    public PersonBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, gender, age, ethnic, nric, address, tags);
    }

}
