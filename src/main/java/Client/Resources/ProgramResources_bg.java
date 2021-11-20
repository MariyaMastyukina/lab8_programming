package Client.Resources;

import java.io.Serializable;
import java.util.ListResourceBundle;

public class ProgramResources_bg extends ListResourceBundle implements Serializable {
    private static final Object[][] city = {
            {"owner", "потребител"},
            {"id", "Ейд"},
            {"name", "име"},
            {"x", "X"},
            {"y", "Y"},
            {"area", "площ"},
            {"population", "население"},
            {"meters", "Метри над морето"},
            {"capital", "капитал"},
            {"climate", "Климат"},
            {"government", "правителство"},
            {"governor", "Губернатор"},
            {"time", "дата на създаване"},
            {"applyChanges", "Добавяне на промени"},
            {"removeElement", "Изтрий"},
            {"confirm", "Потвърждение"},
            {"changeUser", "промяна на потребителя"},
            {"enteredAs", "Вашето потребителско име"},
            {"requireSimple", "Въведете параметър за команда"},
            {"table", "маса"},
            {"visual", "зрителен"},
            {"add", "Добави"},
            {"clear", "Ясно"},
            {"group_counting_by_population", "Да групирам"},
            {"history", "История"},
            {"info", "Информация"},
            {"remove_all_by_meters_above_sea_level", "Изтрийте градовете с ниво"},
            {"remove_by_id", "Изтриване по идентификатор"},
            {"remove_last", "Изтриване последно"},
            {"update", "Обновяване"},
            {"execute_script", "Изпълнете скрипт"},
            {"input", "Въвеждане на параметри"},
            {"help", "Помощ"},
            {"sort", "сортиране"}
    };

    @Override
    protected Object[][] getContents() {
        return city;
    }
}
