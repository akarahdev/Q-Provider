package dev.akarah.provider.objective;

import dev.akarah.APIProvider;
import dev.akarah.datatypes.server.Identifier;
import dev.akarah.objective.Objective;
import dev.akarah.objective.ObjectiveSlot;
import dev.akarah.provider.item.ItemImpl;
import dev.akarah.provider.parse.ComponentSerializer;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.ScoreHolder;

public class ObjectiveImpl extends Objective {
    Identifier<Objective> name;
    net.minecraft.world.scores.Objective objective;

    @Override
    public Identifier<Objective> name() {
        return name;
    }

    @Override
    public Objective name(Identifier<Objective> name) {
        this.name = name;
        return this;
    }

    @Override
    public String displayName() {
        return objective.getDisplayName().getString();
    }

    @Override
    public Objective displayName(String displayName) {
        this.objective.setDisplayName(new ComponentSerializer(displayName).parseTag());
        return this;
    }

    @Override
    public Objective setScore(String scoreName, int value) {
        var scoreHolder = ScoreHolder.forNameOnly(scoreName);
        var scoreAccess = APIProvider.SERVER_INSTANCE.getScoreboard()
                .getOrCreatePlayerScore(scoreHolder, this.objective);
        scoreAccess.set(value);
        scoreAccess.display(new ComponentSerializer(scoreName).parseTag());
        return this;
    }

    @Override
    public int getScore(String scoreName) {
        var scoreHolder = ScoreHolder.forNameOnly(scoreName);
        var scoreAccess = APIProvider.SERVER_INSTANCE.getScoreboard()
                .getOrCreatePlayerScore(scoreHolder, this.objective);
        return scoreAccess.get();
    }

    public static ObjectiveSlot of(DisplaySlot displaySlot) {
        return switch (displaySlot) {
            case BELOW_NAME -> ObjectiveSlot.BELOW_NAME;
            case SIDEBAR -> ObjectiveSlot.SIDEBAR;
            default -> null;
        };
    }

    public static DisplaySlot of(ObjectiveSlot slot) {
        return switch (slot) {
            case SIDEBAR -> DisplaySlot.SIDEBAR;
            case BELOW_NAME -> DisplaySlot.BELOW_NAME;
            case NONE -> null;
        };
    }
    @Override
    public ObjectiveSlot slot() {
        throw new RuntimeException("TODO");
    }

    @Override
    public Objective slot(ObjectiveSlot slot) {
        var scoreboard = APIProvider.SERVER_INSTANCE.getScoreboard();
        if(of(slot) == null) {
            if(scoreboard.getDisplayObjective(DisplaySlot.SIDEBAR) == this.objective) {
                scoreboard.setDisplayObjective(DisplaySlot.SIDEBAR, null);
            }
            if(scoreboard.getDisplayObjective(DisplaySlot.BELOW_NAME) == this.objective) {
                scoreboard.setDisplayObjective(DisplaySlot.BELOW_NAME, null);
            }
        } else {
            scoreboard.setDisplayObjective(of(slot), this.objective);
        }

        return this;
    }
}
