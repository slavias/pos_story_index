package main;

import java.util.List;

import dto.CapabilityIndexDto;

public class Start {

    public static void main(String[] args) {
        Indexes ind = new Indexes(0 == args.length ? "" : args[0]);
        List<CapabilityIndexDto> capabilityList = ind.getStoryIndexes();
        Utils.printCapability(capabilityList);
    }
}
