package net.cubespace.lib.Chat.MessageBuilder.ChatAPI;

import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonDeserializationContext;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonDeserializer;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonElement;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonObject;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonParseException;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonSerializationContext;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonSerializer;
import net.cubespace.lib.Chat.MessageBuilder.ChatAPI.Component.BaseComponent;
import net.cubespace.lib.Chat.MessageBuilder.ChatAPI.Component.TextComponent;
import net.cubespace.lib.Chat.MessageBuilder.ChatAPI.Component.TranslatableComponent;

import java.lang.reflect.Type;

public class ComponentSerializer implements JsonSerializer<BaseComponent>, JsonDeserializer<BaseComponent>
{

    private final static Gson gson = new GsonBuilder().
            registerTypeAdapter( BaseComponent.class, new ComponentSerializer() ).
            registerTypeAdapter( TextComponent.class, new TextComponentSerializer() ).
            registerTypeAdapter( TranslatableComponent.class, new TranslatableComponentSerializer() ).
            create();

    public static BaseComponent[] parse(String json)
    {
        if ( json.startsWith( "[" ) )
        { //Array
            return gson.fromJson( json, BaseComponent[].class );
        }
        return new BaseComponent[]
                {
                        gson.fromJson( json, BaseComponent.class )
                };
    }

    public static String toString(BaseComponent component)
    {
        return gson.toJson( component );
    }

    public static String toString(BaseComponent... components)
    {
        return gson.toJson( new TextComponent( components ) );
    }

    @Override
    public BaseComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if ( json.isJsonPrimitive() )
        {
            return new TextComponent( json.getAsString() );
        }
        JsonObject object = json.getAsJsonObject();
        if ( object.has( "translate" ) )
        {
            return context.deserialize( json, TranslatableComponent.class );
        }
        return context.deserialize( json, TextComponent.class );
    }

    @Override
    public JsonElement serialize(BaseComponent src, Type typeOfSrc, JsonSerializationContext context)
    {
        return context.serialize( src, src.getClass() );
    }
}
