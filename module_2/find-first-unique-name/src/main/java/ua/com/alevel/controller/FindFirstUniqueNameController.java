package ua.com.alevel.controller;

import ua.com.alevel.ParserJson;
import ua.com.alevel.util.NameConsoleUtil;
import ua.com.alevel.util.NameUtil;

public class FindFirstUniqueNameController {
    private static final String file = "find-first-unique-name/src/main/resources/name.json";

    public void findFirstUniqueNameController() {
        String uniqueName = NameUtil.findFirstUniqueName(file);
        NameConsoleUtil.outputUniqueName(ParserJson.readJson(file), uniqueName);
    }
}
