package uz.pdp;

import lombok.SneakyThrows;
import uz.pdp.exceptions.ForcedAppException;
import uz.pdp.ui.Entrance;

public class Main {
    public static void main(String[] args) {
        Entrance.mainMenu();
    }

    @SneakyThrows
    public static void stopApp(String msg) {
        System.out.println(msg);
        throw new ForcedAppException(msg);
    }
}
