package Server.Collection;

import java.io.Serializable;

/**
 * Класс типа enum, содержащий тип правительства города
 */

public enum Government implements Serializable{
    /**
     * Корпоратократия
     */
    CORPORATOCRACY,
    /**
     * Меритократия
     */
    MERITOCRACY,
    /**
     * Олигархия
     */
    OLIGARCHY;
}
