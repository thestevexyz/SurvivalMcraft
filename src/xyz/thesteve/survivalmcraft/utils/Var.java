package xyz.thesteve.survivalmcraft.utils;

public class Var {

    public static final String PREFIX = "§8[§3Survival§8] §7";

    public static final String NO_PERMISSION = PREFIX + "§cYou don't have enough permission, to execute this command!";

    public static final String NO_PLAYER = PREFIX + "§cOnly players can execute that command!";

    public static final String CANNOT_EXECUTE_AS_PLAYER = PREFIX + "§cYou cannot execute this command with the target of yourself! Please include a different user.";

    public static String playerNotFound(String name) {
        return PREFIX + "§cYour given player §e" + name + " §cwas not found!";
    }
}
