package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dto.CapabilityIndexDto;

public class CapabilityTableBuilder {

    private CapabilityIndexDto capability;
    private int maxCapability;
    private int maxFunctionality;
    private List<String> lines;

    public CapabilityTableBuilder(CapabilityIndexDto capability) {
        this(capability, Utils.getMaxCap(Arrays.asList(capability)), Utils.getMaxFunc(Arrays.asList(capability)));
    }

    public CapabilityTableBuilder(CapabilityIndexDto capability, int maxCap, int maxFunc) {
        this.capability = capability;
        this.maxCapability = maxCap;
        this.maxFunctionality = maxFunc;
        if (null != capability) {
            tableBuilder();
        }
    }

    private CapabilityTableBuilder tableBuilder() {
        lines = new ArrayList<>();
        lines.add(printLine());
        for (String storyName : capability.getStoryList().keySet()) {
            StringBuilder sb = new StringBuilder("|");
            if (lines.size() == 1) {
                sb.append(capability.getCapName()).append(printSpace(maxCapability - capability.getCapName().length()))
                        .append("|");
            } else {
                sb.append(printSpace(maxCapability)).append("|");
            }
            sb.append(storyName).append(printSpace(maxFunctionality - storyName.length())).append("|");
            sb.append(capability.getStoryList().get(storyName)).append("|");
            lines.add(sb.toString());
        }
        return this;
    }

    public void printCap() {
        StringBuilder sb = new StringBuilder("");
        for (String line : lines) {
            sb.append(line).append("\n");
        }
        System.out.print(sb.toString());
    }

    private String printSpace(int count) {
        String spaces = "";
        for (int i = 0; i < count; i++) {
            spaces += " ";
        }
        return spaces;
    }

    private String printLine() {
        String lines = "";
        int maxInd = 5;
        int maxDelimiters = 4;
        for (int i = 0; i < (maxCapability + maxFunctionality + maxInd + maxDelimiters); i++) {
            lines += "-";
        }
        return lines;
    }

    public static void printLine(int maxCap, int maxFunc) {
        System.out.print(new CapabilityTableBuilder(null, maxCap, maxFunc).printLine());
    }
}
