package main;

import java.util.List;

import dto.CapabilityIndexDto;

public class Utils {
    public static int getMaxCap(List<CapabilityIndexDto> capability) {
        int max = 0;
        for (CapabilityIndexDto cap : capability) {
            max = max < cap.getCapName().length() ? cap.getCapName().length() : max;
        }
        return max;
    }

    public static int getMaxFunc(List<CapabilityIndexDto> capability) {
        int max = 0;
        for (CapabilityIndexDto cap : capability) {
            for (String func : cap.getStoryNameList()) {
                max = max < func.length() ? func.length() : max;
            }
        }
        return max;
    }

    public static void printCapability(List<CapabilityIndexDto> capability) {
        int maxCap = getMaxCap(capability);
        int maxFunc = getMaxFunc(capability);
        capability.add(0, new CapabilityIndexDto());
        for (CapabilityIndexDto cap : capability) {
            new CapabilityTableBuilder(cap, maxCap, maxFunc).printCap();
        }
        CapabilityTableBuilder.printLine(maxCap, maxFunc);

    }
}
