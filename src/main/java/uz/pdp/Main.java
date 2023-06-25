package uz.pdp;

import lombok.SneakyThrows;
import uz.pdp.exceptions.ForcedAppException;
import uz.pdp.ui.Entrance;

public class Main {
    public static void main(String[] args) {
        /*AdService adService=new AdServiceImpl();
        adService.addAdvert(new Ad("Iphone 12", "brand new iphone 12 for sale",  Category.GADGETS, 1, 0,8));*/
        Entrance.mainMenu();
    }

    @SneakyThrows
    public static void stopApp(String msg) {
        System.out.println(msg);
        throw new ForcedAppException(msg);
    }
}
