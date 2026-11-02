package ly.logic.resource;

import ly.logic.player.ModuleEnum;
import ly.logic.resource.module.ResourceModule;
import ly.net.GameHandlerContext;
import ly.net.IGameController;
import ly.proto.Cmd;
import ly.proto.Resource;

public final class ResourceController implements IGameController {
    @Override public void registerHandlerRouter() {
        gameHandlerRegister(Cmd.CMD.CS_ResourceQuery, this::query);
    }

    private void query(GameHandlerContext context, Resource.CS_ResourceQuery request) {
        ResourceModule resources = (ResourceModule) context.player().getPlayerData().getModule(ModuleEnum.RESOURCE_MODULE);
        Resource.SC_ResourceQuery.Builder response = Resource.SC_ResourceQuery.newBuilder();
        for (int type : request.getResourceTypesList()) response.putResources(type, resources.getResource(type));
        context.player().sendMsg(Cmd.CMD.SC_ResourceQuery, response.build());
    }
}
