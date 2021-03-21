package ua.com.alevel.view;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.controller.ApplicationController;

public class ApplicationView {
    @Getter
    @Setter
    private boolean flag = true;
    private ApplicationController applicationController = new ApplicationController();

    public void applicationView() {
        ConsoleHelperUtil.userGreeting();
        while (flag) {
            int menu = ConsoleHelperUtil.menu();
            String output = applicationController.runApplication(menu);
            ConsoleHelperUtil.output(output);
        }
    }
}
