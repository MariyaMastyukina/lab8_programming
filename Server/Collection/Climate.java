package Server.Collection;

import java.io.Serializable;

/**
 * Класс типа enum, содержащий тип климата города
 */

public enum Climate implements Serializable{
    /**
     * Влажный континентальный климат
     */
    HUMIDCONTINENTAL,
    /**
     * Субарктический климат
     */
    SUBARCTIC,
    /**
     * Климат- тундра
     */
    TUNDRA;
}
