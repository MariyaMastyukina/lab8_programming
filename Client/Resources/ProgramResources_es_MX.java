package Client.Resources;

import java.io.Serializable;
import java.util.ListResourceBundle;

public class ProgramResources_es_MX extends ListResourceBundle implements Serializable {
    private static final Object[][] city= {
            {"owner", "Usuario"},
            {"id", "Eid"},
            {"name", "Nombre"},
            {"x", "X"},
            {"y", "Y"},
            {"area", "Zona"},
            {"population", "Población"},
            {"meters", "Metros sobre el mar"},
            {"capital", "Capital"},
            {"climate", "Clima"},
            {"government", "Gobierno"},
            {"governor", "Gobernador"},
            {"time", "fecha de creación"},
            {"applyChanges", "Agregar cambios"},
            {"removeElement", "Eliminar"},
            {"confirm", "Confirmar"},
            {"changeUser", "cambiar usuario"},
            {"enteredAs", "Su nombre de usuario"},
            {"requireSimple", "Introduzca el parámetro para el comando"},
            {"table", "Mesa"},
            {"visual", "Visual"},
            {"add", "Añadir"},
            {"clear", "Claro"},
            {"group_counting_by_population", "Al grupo"},
            {"history", "Historia"},
            {"info", "Información"},
            {"remove_all_by_meters_above_sea_level", "Eliminar ciudades con nivel"},
            {"remove_by_id", "Eliminar por ID"},
            {"remove_last", "Eliminar último"},
            {"update", "Actualizar"},
            {"execute_script", "Ejecutar script"},
            {"input","Entrada de parámetros"},
    };
    @Override
    protected Object[][] getContents() {
        return city;
    }
}
