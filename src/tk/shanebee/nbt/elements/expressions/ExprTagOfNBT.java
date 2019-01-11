package tk.shanebee.nbt.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import tk.shanebee.nbt.NBeeT;
import tk.shanebee.nbt.nms.NBTApi;

import javax.annotation.Nullable;

@Name("NBT - Tag")
@Description("Returns the value of the specified tag of the specified NBT (Currently only supports get. Set may be available in the future")
@Examples({"set {_tag} to tag \"Invulnerable\" of targeted entity's nbt",
        "send \"Tag: %tag \"\"CustomName\"\" of nbt of target entity%\" to player",
        "set {_tag} to \"Enchantments\" tag of nbt of player's tool"})
@Since("1.0.0")
public class ExprTagOfNBT extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprTagOfNBT.class, String.class, ExpressionType.SIMPLE, "tag %string% of %string%", "" +
                "%string% tag of %string%");
    }

    private Expression<String> a;
    private Expression<String> b;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
        this.a = (Expression<String>) expressions[0];
        this.b = (Expression<String>) expressions[1];
        return true;
    }

    @Override
    @Nullable
    protected String[] get(Event e) {
        NBTApi api = NBeeT.getNBTApi();
        String t = a.getSingle(e);
        String n = b.getSingle(e);
        return api.getNBTTag(t, n);
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "Tag of NBT";
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

}