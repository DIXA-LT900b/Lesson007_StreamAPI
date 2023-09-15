import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    final static int COUNT = 20;          // Количество строк для вывода на экран

    public static void main(String[] args) {

        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();

        for (int i = 0; i < 1_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        Stream less18 = persons.stream().filter(age -> age.getAge() < 18);
        System.out.println("Количество несовершеннолетних = " + less18.count());

        Predicate<Person> men = sex -> sex.getSex() == Sex.MAN;
        Predicate<Person> women = sex -> sex.getSex() == Sex.WOMAN;

        List<String> combatants = persons.stream()
                .filter(men)
                .filter(age -> age.getAge() >= 18 && age.getAge() <= 27)
                .map(Person::toString)
                .toList();
        System.out.println();

        System.out.println("Количество военнообязанных = " + combatants.size());
        System.out.println("Первые " + COUNT + " из них :");
        combatants.stream().limit(COUNT).forEach(System.out::println);
        System.out.println();

        Predicate<Person> workableMen = age -> age.getAge() >= 18 && age.getAge() <= 65;
        Predicate<Person> workableWomen = age -> age.getAge() >= 18 && age.getAge() <= 60;

        List<String> workable = persons.stream()
                .filter(education -> education.getEducation() == Education.HIGHER)
                .filter(men.and(workableMen).or(women.and(workableWomen)))
                .sorted(Comparator.comparing(Person::getFamily))
                .map(Person::toString)
                .toList();

        System.out.println("Количество трудоспособных = ");
        System.out.println("Первые " + COUNT + " из них :");
        workable.stream().limit(COUNT).forEach(System.out::println);
        System.out.println();
    }
}
