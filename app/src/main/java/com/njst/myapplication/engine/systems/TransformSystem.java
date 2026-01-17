package com.njst.myapplication.engine.systems;

import com.njst.myapplication.engine.data.ComponentStores;
import com.njst.myapplication.engine.data.EntityManager;

import java.util.List;

public class TransformSystem {
    
    public void update() {
        List<Integer> entities = EntityManager.getActiveEntities();
        for (Integer entityId : entities) {
            if (ComponentStores.transforms.containsKey(entityId)) {
                // Logic to update matrices would go here
                float[] position = ComponentStores.transforms.get(entityId);
                // Example: just validation or hierarchy updates
            }
        }
    }
}
