package no.nb.microservices.catalogreference.util;

import no.nb.microservices.catalogitem.rest.model.ItemResource;
import no.nb.microservices.catalogitem.rest.model.Person;
import no.nb.microservices.catalogitem.rest.model.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemExtractor {

    private ItemExtractor() {

    }

    public static List<String> extractCreators(ItemResource item) {
        List<String> result = new ArrayList<>();
        if (item.getMetadata().getPeople() == null) {
            return result;
        }
        List<Person> creators = item.getMetadata().getPeople().stream()
                .filter(person -> isCreator(person))
                .collect(Collectors.toList());

        for (Person creator : creators) {
            if ("".equalsIgnoreCase(creator.getDate())) {
                result.add(creator.getName() + " (" + creator.getDate() + ")");
            } else {
                result.add(creator.getName());
            }
        }
        return result;
    }

    public static List<String> extractPersons(ItemResource item) {
        List<String> result = new ArrayList<>();
        if (item.getMetadata().getPeople() == null) {
            return result;
        }
        result.addAll(item.getMetadata().getPeople().stream()
                .map(Person::getName)
                .collect(Collectors.toList()));

        return result;
    }

    private static boolean isCreator(Person person) {
        if (person.getRoles() == null) {
            return false;
        }
        for (Role role : person.getRoles()) {
            if ("creator".equalsIgnoreCase(role.getName())) {
                return true;
            }
        }
        return false;
    }

    public static String extractDateIssued(ItemResource item) {
        if (item.getMetadata().getOriginInfo().getIssued() == null) {
            return "";
        }
        if (DateUtils.isValidDate(item.getMetadata().getOriginInfo().getIssued())) {
            return item.getMetadata().getOriginInfo().getIssued();
        } else {
            return "";
        }
    }

    public static String extractEdition(ItemResource item) {
        if (item.getMetadata().getOriginInfo().getEdition() == null) {
            return "";
        }
        return item.getMetadata().getOriginInfo().getEdition();
    }

    public static List<String> extractTitles(ItemResource item) {
        List<String> result = new ArrayList<>();
        if (item.getMetadata().getTitleInfos() == null) {
            return result;
        }
        List<String> titles = item.getMetadata().getTitleInfos().stream()
            .filter(t -> t.getType() == null)
            .map(t -> t.getTitle())
            .collect(Collectors.toList());

        if (!titles.isEmpty()) {
            result.add(titles.get(0));
        }
        return result;
    }

    public static List<String> extractIsbn(ItemResource item) {
        List<String> result = new ArrayList<>();
        if (item.getMetadata().getIdentifiers().getIsbn13() == null) {
            return result;
        }
        result.addAll(item.getMetadata().getIdentifiers().getIsbn13().stream().collect(Collectors.toList()));
        return result;
    }

    public static List<String> extractIssn(ItemResource item) {
        List<String> result = new ArrayList<>();
        if (item.getMetadata().getIdentifiers().getIssn() == null) {
            return result;
        }
        result.addAll(item.getMetadata().getIdentifiers().getIssn());
        return result;
    }

    public static String extractPublisher(ItemResource item) {
        if (item.getMetadata().getOriginInfo().getPublisher() == null) {
            return "";
        }
        return item.getMetadata().getOriginInfo().getPublisher();
    }

    public static String extractPlace(ItemResource item) {
        if (item.getMetadata().getGeographic() == null) {
            return "";
        }
        return item.getMetadata().getGeographic().getPlaceString();
    }

    public static String extractTypeOfResource(ItemResource item) {
        String result = "GEN";
        String typeOfResource = item.getMetadata().getTypeOfResource();
        if (typeOfResource != null && !typeOfResource.isEmpty()) {
            result = checkBookType(typeOfResource, result);
            result = checkMapType(typeOfResource, result);
            result = checkVideoType(typeOfResource, result);
            result = checkSoundType(typeOfResource, result);
        }

        return result;
    }

    private static String checkBookType(String type, String check) {
        List<String> book = Arrays.asList("monographic", "text");
        if (book.contains(type.toLowerCase())) {
            return "BOOK";
        }
        return check;
    }

    private static String checkMapType(String type, String check) {
        List<String> map = Arrays.asList("cartographic");
        if (map.contains(type.toLowerCase())) {
            return "MAP";
        }
        return check;
    }

    private static String checkVideoType(String type, String check) {
        List<String> video = Arrays.asList("moving image");
        if (video.contains(type.toLowerCase())) {
            return "VIDEO";
        }
        return check;
    }

    private static String checkSoundType(String type, String check) {
        List<String> sound = Arrays.asList("sound recording");
        if (sound.contains(type.toLowerCase())) {
            return "SOUND";
        }
        return check;
    }

    public static List<String> extractNotes(ItemResource item) {
        List<String> result = new ArrayList<>();
        if (item.getMetadata().getNotes() == null) {
            return result;
        }
        result.addAll(item.getMetadata().getNotes().stream().collect(Collectors.toList()));

        return result;
    }

    public static List<String> extractSubjects(ItemResource item) {
        List<String> result = new ArrayList<>();
        if (item.getMetadata().getSubject() == null || item.getMetadata().getSubject().getTopics() == null) {
            return result;
        }
        result.addAll(item.getMetadata().getSubject().getTopics().stream().collect(Collectors.toList()));

        return result;
    }
}
