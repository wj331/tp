package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Ethnicity;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_GENDER = "Female";
    private static final int INVALID_AGE = -5;
    private static final String INVALID_ETHNIC = "American";
    private static final String INVALID_NRIC = "A1B2C3D4E";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "";
    private static final String[] INVALID_APPOINTMENT = new String[]{"hii", "01 01 2019"};

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_GENDER = BENSON.getGender().toString();
    private static final int VALID_AGE = BENSON.getAge().age;
    private static final String VALID_ETHNIC = BENSON.getEthnic().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_NRIC = BENSON.getNric().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedAppointment> VALID_APPOINTMENT = BENSON.getAppointments().stream()
            .map(JsonAdaptedAppointment::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_GENDER, VALID_AGE, VALID_ETHNIC,
                        VALID_NRIC, VALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_GENDER,
                VALID_AGE, VALID_ETHNIC, VALID_NRIC, VALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_GENDER, VALID_AGE, VALID_ETHNIC,
                        VALID_NRIC, VALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_GENDER, VALID_AGE,
                VALID_ETHNIC, VALID_NRIC, VALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_GENDER, VALID_AGE, VALID_ETHNIC,
                        VALID_NRIC, VALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_GENDER, VALID_AGE,
                VALID_ETHNIC, VALID_NRIC, VALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
    @Test
    public void toModelType_invalidGender_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_GENDER, VALID_AGE, VALID_ETHNIC,
                        VALID_NRIC, VALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = Gender.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullGender_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                null, VALID_AGE, VALID_ETHNIC, VALID_NRIC, VALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
    @Test
    public void toModelType_invalidAge_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_GENDER, INVALID_AGE, VALID_ETHNIC,
                        VALID_NRIC, VALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = Age.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEthnic_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_GENDER, VALID_AGE, INVALID_ETHNIC,
                        VALID_NRIC, VALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = Ethnicity.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEthnic_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_GENDER, VALID_AGE, null, VALID_NRIC, VALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Ethnicity.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_GENDER, VALID_AGE, VALID_ETHNIC,
                        INVALID_NRIC, VALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = Nric.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullNric_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_GENDER, VALID_AGE, VALID_ETHNIC, null, VALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_GENDER, VALID_AGE, VALID_ETHNIC,
                        VALID_NRIC, INVALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE,
                VALID_EMAIL, VALID_GENDER, VALID_AGE, VALID_ETHNIC,
                VALID_NRIC, null, VALID_TAGS, VALID_APPOINTMENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_GENDER, VALID_AGE, VALID_ETHNIC,
                        VALID_NRIC, VALID_ADDRESS, invalidTags, VALID_APPOINTMENT);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidAppointments_throwsDateTimeParseException() {
        Executable executable = () -> {
            List<JsonAdaptedAppointment> invalidAppointments = new ArrayList<>();
            invalidAppointments.add(new JsonAdaptedAppointment(INVALID_APPOINTMENT[0], INVALID_APPOINTMENT[1]));
            JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_GENDER,
                    VALID_AGE, VALID_ETHNIC, VALID_NRIC, VALID_ADDRESS,
                    VALID_TAGS, invalidAppointments);
            person.toModelType();
        };
        assertThrows(DateTimeParseException.class, executable);

    }

}
