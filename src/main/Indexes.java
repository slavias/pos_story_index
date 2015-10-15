package main;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dto.CapabilityIndexDto;

public class Indexes {

    private String baseDir = "D:\\git\\CTCO_POS1\\src\\test\\resources\\stories\\pos";
    
    public Indexes(String baseDir){
        if (!baseDir.isEmpty()){
            this.baseDir = baseDir;
        }
    }
    
    public List<CapabilityIndexDto> getStoryIndexes() {
        List<CapabilityIndexDto> data = new ArrayList<>();

        for (String cap : getCapabilities()) {
            Map<String, String> storyInd = new HashMap<>();
            for (File file : getStories(baseDir + File.separator + cap, new ArrayList<File>())) {
                String storyName = file.getAbsolutePath().split(cap + "\\\\")[1];
                storyInd.put(storyName, getIndex(file.getAbsolutePath()));
            }
            data.add(new CapabilityIndexDto(cap, storyInd));

        }
        return data;
    }

    public List<File> getStories(final String baseDirPath, final List<File> baseFileList) {
        File tempFile = new File(baseDirPath);
        for (File file : tempFile.listFiles()) {
            if (file.isDirectory()) {
                getStories(file.getPath(), baseFileList);
            } else if (file.getAbsolutePath().endsWith(".story")) {
                baseFileList.add(file);
            }
        }
        return baseFileList;
    }

    public List<String> getCapabilities() {
        List<String> capList = new ArrayList<>();
        File tempFile = new File(baseDir);
        for (File file : tempFile.listFiles()) {
            if (file.isDirectory()) {
                capList.add(file.getName());
            }
        }
        return capList;
    }

    private String getIndex(String basedir) {
        Set<String> ind = new HashSet<>();
        try {
            for (String line : Files.readAllLines(Paths.get(basedir), Charset.defaultCharset())) {
                if (line.startsWith("Scenario:")) {
                    Pattern p = Pattern.compile("[0-9]{2}.[0-9]{2}.[0-9]{2}");
                    Matcher m = p.matcher(line);
                    if (m.find()) {
                        ind.add(m.group(0).substring(0, 5));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> index = new ArrayList<>(ind);
        if (index.size() > 1) {
            System.err.println("double feature indexes in file " + basedir);
            return null;
        }
        return !index.isEmpty()?index.get(0):"empty";
    }

}
