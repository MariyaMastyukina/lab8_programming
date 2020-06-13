package Client.Resources;

import java.io.Serializable;
import java.util.ListResourceBundle;

public class ProgramResources_fi extends ListResourceBundle implements Serializable {
    private static final Object[][] city={
            {"owner", "Käyttäjä"},
            {"id", "Eid"},
            {"name", "Nimi"},
            {"x", "X"},
            {"y", "Y"},
            {"area", "Alue"},
            {"population", "Väestö"},
            {"meters", "Metriä merenpinnan yläpuolella"},
            {"capital", "Iso alkukirjain"},
            {"climate", "Ilmasto"},
            {"government", "Hallitus"},
            {"governor", "Kuvernööri"},
            {"time", "Luomispäivä"},
            {"applyChanges", "Lisää muutokset"},
            {"removeElement", "Poistaa"},
            {"confirm", "Vahvistaa"},
            {"changeUser", "vaihda käyttäjä"},
            {"enteredAs", "Sinun käyttäjä nimesi"},
            {"requireSimple","Kirjoita komennon parametri"},
            {"table", "Pöytä"},
            {"visual", "Visuaalinen"},
            {"add","Lisätä"},
            {"clear","Asia selvä"},
            {"group_counting_by_population","Ryhmitellä"},
            {"history","Tarina"},
            {"info","Tiedot"},
            {"remove_all_by_meters_above_sea_level","Poista kaupungit tasolla"},
            {"remove_by_id","Poista tunnuksella ID"},
            {"remove_last","Poista viimeinen"},
            {"update","Virkistää"},
            {"execute_script","Suorita komentosarja"},
            {"input","Parametrien syöttö"},
    };

    @Override
    protected Object[][] getContents() {
        return city;
    }
}
