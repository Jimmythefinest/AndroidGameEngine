package com.njst.myapplication.engine.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class EntityManager {
    private static int nextEntityId = 0;
    private static final Stack<Integer> recycledIds = new Stack<>();
    private static final List<Integer> activeEntities = new ArrayList<>();

    public static int createEntity() {
        int id;
        if (!recycledIds.isEmpty()) {
            id = recycledIds.pop();
        } else {
            id = nextEntityId++;
        }
        activeEntities.add(id);
        return id;
    }

    public static void destroyEntity(int entityId) {
        if (activeEntities.contains(entityId)) {
            activeEntities.remove(Integer.valueOf(entityId));
            recycledIds.push(entityId);
            // TODO: Notify component stores to clear data for this entity
        }
    }

    public static List<Integer> getActiveEntities() {
        return activeEntities;
    }
}
