package streams.part1.exercise;

import lambda.data.Employee;
import lambda.data.JobHistoryEntry;
import lambda.data.Person;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.averagingDouble;
import static org.hamcrest.MatcherAssert.assertThat;

@SuppressWarnings({"ConstantConditions", "unused"})
class Exercise2 {

    @Test
    void calcAverageAgeOfEmployees() {
        List<Employee> employees = getEmployees();

        Double expected = employees.stream()
                .map(Employee::getPerson)
                .collect(averagingDouble(Person::getAge));

        assertThat(expected, Matchers.closeTo(33.66, 0.1));
    }

    @Test
    void findPersonWithLongestFullName() {
        List<Employee> employees = getEmployees();

        Person expected = employees.stream()
                .map(Employee::getPerson)
                .max(comparing(person -> person.getFullName().length()))
                .orElseThrow(IllegalStateException::new);

        assertThat(expected, Matchers.is(employees.get(1).getPerson()));
    }

    @Test
    void findEmployeeWithMaximumDurationAtOnePosition() {
        List<Employee> employees = getEmployees();

        Employee expected = employees.stream()
                .max(comparing(value -> value.getJobHistory().stream()
                        .map(JobHistoryEntry::getDuration)
                        .max(Integer::compareTo)
                        .orElseThrow(IllegalStateException::new)))
                .orElseThrow(IllegalStateException::new);

        assertThat(expected, Matchers.is(employees.get(4)));
    }

    /**
     * Вычислить общую сумму заработной платы для сотрудников.
     * Базовая ставка каждого сотрудника составляет 75_000.
     * Если на текущей позиции (последняя в списке) он работает больше трех лет - ставка увеличивается на 20%
     */
    @Test
    void calcTotalSalaryWithCoefficientWorkExperience() {
        List<Employee> employees = getEmployees();

        Double base = 75_000d;
        Double expected =
                employees.stream()
                        .map(Employee::getJobHistory)
                        .map(jobHistoryEntries -> jobHistoryEntries.get(jobHistoryEntries.size() - 1))
                        .map(JobHistoryEntry::getDuration)
                        .mapToDouble(duration -> duration > 3 ? base * 1.2 : base)
                        .sum();

        assertThat(expected, Matchers.closeTo(465000.0, 0.001));
    }

    private static List<Employee> getEmployees() {
        return Arrays.asList(
                new Employee(
                        new Person("Иван", "Мельников", 30),
                        Arrays.asList(
                                new JobHistoryEntry(2, "dev", "EPAM"),
                                new JobHistoryEntry(1, "dev", "google")
                        )),
                new Employee(
                        new Person("Александр", "Дементьев", 28),
                        Arrays.asList(
                                new JobHistoryEntry(1, "tester", "EPAM"),
                                new JobHistoryEntry(1, "dev", "EPAM"),
                                new JobHistoryEntry(1, "dev", "google")
                        )),
                new Employee(
                        new Person("Дмитрий", "Осинов", 40),
                        Arrays.asList(
                                new JobHistoryEntry(3, "QA", "yandex"),
                                new JobHistoryEntry(1, "QA", "mail.ru"),
                                new JobHistoryEntry(1, "dev", "mail.ru")
                        )),
                new Employee(
                        new Person("Анна", "Светличная", 21),
                        Collections.singletonList(
                                new JobHistoryEntry(1, "tester", "T-Systems")
                        )),
                new Employee(
                        new Person("Игорь", "Толмачёв", 50),
                        Arrays.asList(
                                new JobHistoryEntry(5, "tester", "EPAM"),
                                new JobHistoryEntry(6, "QA", "EPAM")
                        )),
                new Employee(
                        new Person("Иван", "Александров", 33),
                        Arrays.asList(
                                new JobHistoryEntry(2, "QA", "T-Systems"),
                                new JobHistoryEntry(3, "QA", "EPAM"),
                                new JobHistoryEntry(1, "dev", "EPAM")
                        ))
        );
    }
}