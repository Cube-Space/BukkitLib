package net.cubespace.lib.Chat.MessageBuilder.ClickEvent;


/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class BungeeClickEvent implements IClickEvent {
    private String value;
    private ClickAction action;

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void setAction(ClickAction action) {
        this.action = action;
    }

    @Override
    public Object get() {
        net.cubespace.lib.Chat.MessageBuilder.ChatAPI.Event.ClickEvent.Action eventAction = net.cubespace.lib.Chat.MessageBuilder.ChatAPI.Event.ClickEvent.Action.SUGGEST_COMMAND;

        if(action.equals(ClickAction.OPEN_FILE)) {
            eventAction = net.cubespace.lib.Chat.MessageBuilder.ChatAPI.Event.ClickEvent.Action.OPEN_FILE;
        }

        if(action.equals(ClickAction.OPEN_URL)) {
            eventAction = net.cubespace.lib.Chat.MessageBuilder.ChatAPI.Event.ClickEvent.Action.OPEN_URL;
        }

        if(action.equals(ClickAction.RUN_COMMAND)) {
            eventAction = net.cubespace.lib.Chat.MessageBuilder.ChatAPI.Event.ClickEvent.Action.RUN_COMMAND;
        }

        if(action.equals(ClickAction.SUGGEST_COMMAND)) {
            eventAction = net.cubespace.lib.Chat.MessageBuilder.ChatAPI.Event.ClickEvent.Action.SUGGEST_COMMAND;
        }

        return new net.cubespace.lib.Chat.MessageBuilder.ChatAPI.Event.ClickEvent(eventAction, value);
    }
}
