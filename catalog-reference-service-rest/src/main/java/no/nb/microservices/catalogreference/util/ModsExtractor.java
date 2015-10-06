package no.nb.microservices.catalogreference.util;

import no.nb.microservices.catalogmetadata.model.mods.v3.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModsExtractor {
    private final Mods mods;

    public ModsExtractor(Mods mods) {
        this.mods = mods;
    }

    public List<String> extractCreators() {
        List<String> result = new ArrayList<>();
        if (mods.getNames() == null) {
            return result;
        }
        List<Name> creators = mods.getNames().stream()
                .filter(name -> isCreator(name))
                .collect(Collectors.toList());

        for (Name creator : creators) {
            String add = "";
            for (Namepart namepart : creator.getNameParts()) {
                if (namepart.getType() == null) {
                    add += namepart.getValue() + " ";
                } else {
                    add += "(" + namepart.getValue() + ")";
                }
            }
            result.add(add.trim());
        }
        return result;
    }

    public List<String> extractPersons() {
        List<String> persons = new ArrayList<>();
        if (mods.getNames() == null) {
            return persons;
        }
        for (Name name : mods.getNames()) {
            if ("personal".equalsIgnoreCase(name.getType())) {
                String add = "";
                for (Namepart namepart : name.getNameParts()) {
                    add += namepart.getValue() + " ";
                }
                persons.add(add.trim());
            }
        }

        return persons;
    }

    private boolean isCreator(Name name) {
        if (name.getRole() == null) {
            return false;
        }
        for (Role role : name.getRole()) {
            if (role.getRoleTerms() == null) {
                continue;
            }
            for (String roleTerm : role.getRoleTerms()) {
                if ("creator".equalsIgnoreCase(roleTerm)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String extractDateIssued() {
        if (mods.getOriginInfo() != null) {
            return mods.getOriginInfo().getDateIssued();
        }
        return "";
    }

    public String extractEdition() {
        if (mods.getOriginInfo() == null) {
            return "";
        }
        return mods.getOriginInfo().getEdition();
    }

    public List<String> extractTitles() {
        List<String> titles = new ArrayList<>();
        if (mods.getTitleInfos() == null && mods.getRecordInfo() != null) {
            titles.add(mods.getRecordInfo().getRecordContentSource());
            return titles;
        }

        for (TitleInfo titleInfo : mods.getTitleInfos()) {
            if (titleInfo.getType() != null) {
                continue;
            }
            String title = "";
            if (titleInfo.getNonSort() != null) {
                title += titleInfo.getNonSort();
            }
            title += titleInfo.getTitle();
            if (titleInfo.getSubTitle() != null && !titleInfo.getSubTitle().isEmpty()) {
                title += ": " + titleInfo.getSubTitle();
            }
            titles.add(title);
        }
        return titles;
    }

    public List<Identifier> extractIdentifiers(String type) {
        List<Identifier> identifiers = new ArrayList<>();
        if (mods.getIdentifierMap() == null) {
            return identifiers;
        }
        List<Identifier> ids = mods.getIdentifierMap().get(type);
        if (ids != null) {
            identifiers.addAll(ids);
        }
        return identifiers;
    }

    public String extractPlace() {
        if (mods.getOriginInfo() == null || mods.getOriginInfo().getPlace() == null) {
            return "";
        }
        return mods.getOriginInfo().getPlace().getPlaceTerm();
    }

    public String extractPublisher() {
        if (mods.getOriginInfo() == null) {
            return "";
        }
        return  mods.getOriginInfo().getPublisher();
    }

    public String extractTypeOfResource() {
        String result = "GEN";
        String typeOfResource = mods.getTypeOfResource();
        if(typeOfResource != null && !typeOfResource.isEmpty()) {
            result = checkBookType(typeOfResource,result);
            result = checkMapType(typeOfResource,result);
            result = checkVideoType(typeOfResource,result);
            result = checkSoundType(typeOfResource,result);
        } else {
            String issuance = mods.getOriginInfo().getIssuance();
            result = checkBookType(issuance,result);
            result = checkMapType(issuance,result);
        }
        return result;
    }

    private String checkBookType(String type, String check) {
        List<String> book = Arrays.asList("monographic", "text");
        if (book.contains(type.toLowerCase())) {
            return "BOOK";
        }
        return check;
    }

    private String checkMapType(String type, String check) {
        List<String> map = Arrays.asList("cartographic");
        if (map.contains(type.toLowerCase())) {
            return "MAP";
        }
        return check;
    }

    private String checkVideoType(String type, String check) {
        List<String> video = Arrays.asList("moving image");
        if (video.contains(type.toLowerCase())) {
            return "VIDEO";
        }
        return check;
    }

    private String checkSoundType(String type, String check) {
        List<String> sound = Arrays.asList("sound recording");
        if (sound.contains(type.toLowerCase())) {
            return "SOUND";
        }
        return check;
    }

    public List<String> extractNotes() {
        List<String> notes = new ArrayList<>();
        if (mods.getNotes() == null) {
            return notes;
        }
        notes.addAll(mods.getNotes().stream()
                .filter(note -> note.getType() == null)
                .map(note -> note.getValue().trim())
                .collect(Collectors.toList()));
        return notes;
    }

    public List<String> extractTOC() {
        List<String> toc = new ArrayList<>();
        if (mods.getTableOfContents() == null) {
            return toc;
        }
        toc.addAll(mods.getTableOfContents().stream()
                .map(tableOfContents -> "Innhold: " + tableOfContents.getValue())
                .collect(Collectors.toList()));
        return toc;
    }

    public String extractExtent() {
        if (mods.getPhysicalDescription() == null) {
            return "";
        }
        return mods.getPhysicalDescription().getExtent();
    }

    public List<String> extractSubjects() {
        List<String> subjects = new ArrayList<>();
        if (mods.getSubjects() == null) {
            return subjects;
        }
        for (Subject subject : mods.getSubjects()) {
            String authority = subject.getAuthority();
            if (authority != null && !authority.isEmpty()) {
                subject.getGenres().forEach(genre -> subjects.add(genre));
            }
            if (subject.getTopic() != null) {
                subject.getTopic().forEach(topic -> subjects.add(topic.getValue()));
            }
        }
        return subjects;
    }


}
