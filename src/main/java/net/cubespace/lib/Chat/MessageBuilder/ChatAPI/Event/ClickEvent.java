package net.cubespace.lib.Chat.MessageBuilder.ChatAPI.Event;

final public class ClickEvent
{

    /**
     * The type of action to preform on click
     */
    private final Action action;
    /**
     * Depends on action
     *
     * @see net.md_5.bungee.api.chat.ClickEvent.Action
     */
    private final String value;

    public ClickEvent(Action action, String value) {
        this.action = action;
        this.value = value;
    }

    public Action getAction() {
        return action;
    }

    public String getValue() {
        return value;
    }

    public enum Action
    {

        /**
         * Open a url at the path given by
         */
        OPEN_URL,
        /**
         * Open a file at the path given by
         */
        OPEN_FILE,
        /**
         * Run the command given by
         */
        RUN_COMMAND,
        /**
         * Inserts the string given by
         * getValue() into the
         * players text box
         */
        SUGGEST_COMMAND
    }

    @Override
    public String toString()
    {
        return String.format( "ClickEvent{action=%s, value=%s}", action, value );
    }
}
