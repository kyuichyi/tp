package meditracker.argument;

public class Argument {
    private final ArgumentName name;
    private final String flag;
    private final String prompt;
    private final String help;
    private final boolean isOptional;

    public Argument(ArgumentName name, String flag, String prompt, String help, boolean isOptional) {
        this.name = name;
        this.flag = flag;
        this.prompt = prompt;
        this.help = help;
        this.isOptional = isOptional;
    }

    public ArgumentName getName() {
        return name;
    }

    public String getFlag() {
        return flag;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getHelp() {
        return help;
    }

    public boolean isOptional() {
        return isOptional;
    }
}
