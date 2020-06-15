package Client.Resources;

import java.io.Serializable;
import java.util.ListResourceBundle;

public class ProgramResources_ru extends ListResourceBundle implements Serializable {
    private static final Object[][] city={
            {"owner", "Пользователь"},
            {"id", "Id"},
            {"name", "Имя"},
            {"x", "X"},
            {"y", "Y"},
            {"area", "Площадь"},
            {"population", "Население"},
            {"meters", "Метров над морем"},
            {"capital", "Столица"},
            {"climate", "Климат"},
            {"government", "Правительство"},
            {"governor", "Губернатор"},
            {"time", "Дата создания"},
            {"applyChanges", "Добавить измненения"},
            {"removeElement","Удалить"},
            {"confirm","Подтвердить"},
            {"changeUser","сменить пользователя"},
            {"enteredAs", "Ваше имя пользователя"},
            {"requireSimple", "Введите параметр для команды"},
            {"input","Ввод параметра"},
            {"table", "Таблица"},
            {"visual", "Визуал"},
            {"add","Добавить"},
            {"clear","Очистить"},
            {"group_counting_by_population","Сгруппировать"},
            {"history","История"},
            {"info","Информация"},
            {"remove_all_by_meters_above_sea_level","Удалить города с уровнем"},
            {"remove_by_id","Удалить по ID"},
            {"remove_last","Удалить последний"},
            {"update","Обновить"},
            {"execute_script","Выполнить скрипт"},
            {"sort","Сортировка"}


    };
    @Override
    protected Object[][] getContents() {
        return city;
    }
}
