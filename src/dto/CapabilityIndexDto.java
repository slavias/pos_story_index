package dto;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CapabilityIndexDto {

    private String capName;
    private Map<String, String> storyList;

    public CapabilityIndexDto(String capName, Map<String, String> storyList) {
        this.capName = capName;
        this.storyList = storyList;
        mergeStories();
        sortByIndex();
    }

    public CapabilityIndexDto() {
        this.capName = "Capability";
        this.storyList = new HashMap<>();
        this.storyList.put("Functionality", "Index");
    }

    public String getCapName() {
        return capName;
    }

    public void setCapName(String capName) {
        this.capName = capName;
    }

    public Map<String, String> getStoryList() {
        return storyList;
    }

    public void setStoryList(Map<String, String> storyList) {
        this.storyList = storyList;
    }

    public Set<String> getStoryNameList() {
        return storyList.keySet();
    }

    public String getIndexByName(String storyName) {
        return storyList.get(storyName);
    }

    private void mergeStories() {
        Map<String, String> tempStoryList = new HashMap<>();
        for (String story : storyList.keySet()) {
            String realName = story.split("\\\\|\\.")[0];
            if (null == tempStoryList.get(realName)) {
                tempStoryList.put(realName, storyList.get(story));
            } else if (!tempStoryList.get(realName).equals(storyList.get(story))) {
                System.err.println("double feature indexes in files " + realName);
            }
        }
        setStoryList(tempStoryList);
    }

    private void sortByIndex() {
        storyList = sortByValue(storyList);

    }

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

}
