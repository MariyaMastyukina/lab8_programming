package Server.Commands;

import Server.Launch.CityService;
import Server.Launch.ControlUnit;
import Server.Model.*;
import Utils.ConnectionUtils.Request;
import Utils.DataUtils.CommandUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class AddCommand implements Command {
    private CityService cityService;
    private String name = "add";
    private int argsSize = 0;
    private boolean isButton = true;

    public AddCommand(ControlUnit controlUnit, CityService cityService) {
        controlUnit.addCommand(name, this);
        this.cityService = cityService;
    }

    @Override
    public Request execute(CommandUtils commandUtils) throws IOException, SQLException {
        City city = createCity(commandUtils.getArgs(), commandUtils.getLogin());
        city.setUser(commandUtils.getLogin());
        return cityService.add(city, commandUtils.getLogin());
    }

    private City createCity(List<String> args, String login) {
        City city = new City();
        city.setName(args.get(0));
        city.setCoordinates(new Coordinates(args.subList(1, 3)));
        city.setCreationDate(LocalDateTime.now());
        city.setArea(Double.parseDouble(args.get(3)));
        city.setPopulation(Integer.parseInt(args.get(4)));
        city.setMetersAboveSeaLevel(Integer.parseInt(args.get(5)));
        city.setCapital(Boolean.parseBoolean(args.get(6)));
        city.setClimate(Climate.valueOf(args.get(7)));
        city.setGovernment(Government.valueOf(args.get(8)));
        city.setGovernor(new Human(args.get(9)));
        city.setUser(login);
        return city;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getArgsSize() {
        return argsSize;
    }

    @Override
    public boolean isButton() {
        return isButton;
    }
}
