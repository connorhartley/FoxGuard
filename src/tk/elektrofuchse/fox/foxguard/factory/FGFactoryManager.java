package tk.elektrofuchse.fox.foxguard.factory;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.world.World;
import tk.elektrofuchse.fox.foxguard.commands.util.CommandParseHelper;
import tk.elektrofuchse.fox.foxguard.commands.util.InternalCommandState;
import tk.elektrofuchse.fox.foxguard.flags.IFlagSet;
import tk.elektrofuchse.fox.foxguard.regions.IRegion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fox on 10/22/2015.
 */
public class FGFactoryManager {

    private final List<IRegionFactory> regionFactories;
    private final List<IFlagSetFactory> flagSetFactories;


    private static FGFactoryManager ourInstance = new FGFactoryManager();

    public static FGFactoryManager getInstance() {
        return ourInstance;
    }

    private FGFactoryManager() {
        regionFactories = new ArrayList<>();
        flagSetFactories = new ArrayList<>();
    }


    public IRegion createRegion(String type, String name, String args, InternalCommandState state, World world, CommandSource source) {
        for (IRegionFactory rf : regionFactories){
            if(CommandParseHelper.contains(rf.getAliases(), type)){
                return rf.createRegion(type, name, args, state, world, source);
            }
        }
        return null;
    }


    public IFlagSet createFlagSet(String type, String name, String args, InternalCommandState state, CommandSource source) {
        for (IFlagSetFactory rf : flagSetFactories) {
            if (CommandParseHelper.contains(rf.getAliases(), type)) {
                return rf.createFlagSet(type, name, args, state, source);
            }
        }
        return null;
    }

    public boolean registerRegionFactory(IRegionFactory factory) {
        if (regionFactories.contains(factory)) return false;
        regionFactories.add(factory);
        return true;
    }

    public boolean registerFlagSetFactory(IFlagSetFactory factory) {
        if (flagSetFactories.contains(factory)) return false;
        flagSetFactories.add(factory);
        return true;
    }

    public boolean unregister(Object factory) {
        if (factory instanceof IRegionFactory)
            return regionFactories.remove(factory);
        else if (factory instanceof IFlagSetFactory)
            return flagSetFactories.remove(factory);
        return false;
    }
}