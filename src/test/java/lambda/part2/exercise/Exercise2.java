package lambda.part2.exercise;

import lambda.data.Person;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings({"ConstantConditions", "unused"})
class Exercise2 {

    @Test
    void personHasNotEmptyLastNameAndFirstName() {
        // TODO предикат Person -> boolean, проверяющий что имя и фамилия человека не пусты
        Predicate<Person> validate = p -> !p.getFirstName().equals("") && !p.getLastName().equals("");

        assertThat(validate.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(validate.test(new Person("Николай", "", 30)), is(false));
        assertThat(validate.test(new Person("", "Мельников", 20)), is(false));
    }

    // TODO метод (Person -> boolean) -> (Person -> boolean)
    // TODO - возвращает новый предикат, являющийся отрицанием исходного
    // TODO - при реализации использовать логический оператор !
    private Predicate<Person> negateUsingLogicalOperator(Predicate<Person> predicate) {
        return p -> !predicate.test(p);
    }

    // TODO метод (Person -> boolean, Person -> boolean) -> (Person -> boolean)
    // TODO - возвращает новый предикат, объединяющий исходные с помощью операции "AND"
    // TODO - при реализации использовать логический оператор &&
    private Predicate<Person> andUsingLogicalOperator(Predicate<Person> left, Predicate<Person> right) {
        return p -> !left.test(p) && !right.test(p);
    }

    @Test
    void personHasNotEmptyLastNameAndFirstNameUsingLogicalOperators() {
        Predicate<Person> personHasEmptyFirstName = p -> p.getFirstName().equals("");
        Predicate<Person> personHasEmptyLastName = p -> p.getLastName().equals("");

        Predicate<Person> personHasNotEmptyFirstName = negateUsingLogicalOperator(personHasEmptyFirstName);
        Predicate<Person> personHasNotEmptyLastName = negateUsingLogicalOperator(personHasEmptyLastName);

        Predicate<Person> personHasNotEmptyLastNameAndFirstName = andUsingLogicalOperator(personHasNotEmptyFirstName, personHasNotEmptyLastName);

        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)), is(false));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)), is(false));
    }

    // TODO метод (T -> boolean) -> (T -> boolean)
    // TODO - возвращает новый предикат, являющийся отрицанием исходного
    // TODO - при реализации использовать логический оператор !
    private <T> Predicate<T> negate(Predicate<T> predicate) {
        return t -> !predicate.test(t);
    }

    // TODO метод (T -> boolean, T -> boolean) -> (T -> boolean)
    // TODO - возвращает новый предикат, объединяющий исходные с помощью операции "AND"
    // TODO - при реализации использовать логический оператор &&
    private <T> Predicate<T> and(Predicate<T> left, Predicate<T> right) {
        return t -> left.test(t) && right.test(t);
    }

    @Test
    void personHasNotEmptyLastNameAndFirstNameUsingGenericPredicates() {
        Predicate<Person> personHasEmptyFirstName = p -> p.getFirstName().isEmpty();
        Predicate<Person> personHasEmptyLastName = p -> p.getLastName().isEmpty();

        Predicate<Person> personHasNotEmptyFirstName = negate(personHasEmptyFirstName);
        Predicate<Person> personHasNotEmptyLastName = negate(personHasEmptyLastName);

        Predicate<Person> personHasNotEmptyLastNameAndFirstName = and(personHasNotEmptyFirstName, personHasNotEmptyLastName);

        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)), is(false));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)), is(false));
    }

    @Test
    void personHasNotEmptyLastNameAndFirstNameUsingStandardMethods() {
        Predicate<Person> personHasEmptyFirstName = p -> p.getFirstName().isEmpty();
        Predicate<Person> personHasEmptyLastName = p -> p.getLastName().isEmpty();

        // TODO использовать Predicate.negate
        Predicate<Person> personHasNotEmptyFirstName = personHasEmptyFirstName.negate();
        Predicate<Person> personHasNotEmptyLastName = personHasEmptyLastName.negate();

        // TODO использовать Predicate.and
        Predicate<Person> personHasNotEmptyLastNameAndFirstName = personHasNotEmptyFirstName.and(personHasNotEmptyLastName);

        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Алексей", "Доренко", 40)), is(true));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("Николай", "", 30)), is(false));
        assertThat(personHasNotEmptyLastNameAndFirstName.test(new Person("", "Мельников", 20)), is(false));
    }
}
