package Client.Resources;

import java.util.ListResourceBundle;

public class ButtonResources extends ListResourceBundle {
    private static final Object[][] buttons = {
            {"Добавить", "add"},
            {"Очистить", "clear"},
            {"Сгруппировать", "group_counting_by_population"},
            {"История", "history"},
            {"Информация", "info"},
            {"Удалить города с уровнем", "remove_all_by_meters_above_sea_level"},
            {"Удалить по ID", "remove_by_id"},
            {"Удалить последний", "remove_last"},
            {"Обновить", "update"},
            {"Выполнить скрипт", "execute_script"},

            {"Lisätä", "add"},
            {"Asia selvä", "clear"},
            {"Ryhmitellä", "group_counting_by_population"},
            {"Tarina", "history"},
            {"Tiedot", "info"},
            {"Poista kaupungit tasolla", "remove_all_by_meters_above_sea_level"},
            {"Poista tunnuksella ID", "remove_by_id"},
            {"Poista viimeinen", "remove_last"},
            {"Virkistää", "update"},
            {"Suorita komentosarja", "execute_script"},

            {"Добави", "add"},
            {"Ясно", "clear"},
            {"Да групирам", "group_counting_by_population"},
            {"История", "history"},
            {"Информация", "info"},
            {"Изтрийте градовете с ниво", "remove_all_by_meters_above_sea_level"},
            {"Изтриване по идентификатор", "remove_by_id"},
            {"Изтриване последно", "remove_last"},
            {"Обновяване", "update"},
            {"Изпълнете скрипт", "execute_script"},

            {"Añadir", "add"},
            {"Claro", "clear"},
            {"Al grupo", "group_counting_by_population"},
            {"Historia", "history"},
            {"Información", "info"},
            {"Eliminar ciudades con nivel", "remove_all_by_meters_above_sea_level"},
            {"Eliminar por ID", "remove_by_id"},
            {"Eliminar último", "remove_last"},
            {"Actualizar", "update"},
            {"Ejecutar script", "execute_script"},
    };

    @Override
    protected Object[][] getContents() {
        return buttons;
    }
}
