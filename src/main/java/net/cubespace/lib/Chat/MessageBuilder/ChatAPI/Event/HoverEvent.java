package net.cubespace.lib.Chat.MessageBuilder.ChatAPI.Event;

import net.cubespace.lib.Chat.MessageBuilder.ChatAPI.Component.BaseComponent;

final public class HoverEvent
{

    private final Action action;
    private final BaseComponent[] value;

    public HoverEvent(Action action, BaseComponent[] value) {
        this.action = action;
        this.value = value;
    }

    public Action getAction() {
        return action;
    }

    public BaseComponent[] getValue() {
        return value;
    }

    public enum Action
    {

        SHOW_TEXT,
        SHOW_ACHIEVEMENT,
        SHOW_ITEM
    }

    @Override
    public String toString()
    {
        return String.format( "HoverEvent{action=%s, value=%s}", action, value );
    }
}
