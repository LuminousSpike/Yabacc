package com.bituser.yabacc;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class EntityCollection extends Entity {
    List<Entity> _entities= new ArrayList<Entity>();

    public EntityCollection (float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public int size () {
        return _entities.size();
    }

    protected Entity get () {
        Entity entity = null;

        int entityCount = _entities.size();

        if (entityCount > 0) {
            entity = _entities.get(_rand.nextInt(entityCount));
            _entities.remove(entity);
        }

        return entity;
    }

    protected void add (Entity entity) {
        entity.moveToPosition(_position.x, _position.y);
        _entities.add(entity);
    }

    protected void add (Entity[] entities) {
        for(Entity entity : entities) {
            add(entity);
        }
    }

    @Override
    public void update (float deltaTime) {
        _rect.setX(_position.x - (_width / 2));
        _rect.setY(_position.y - (_height / 2));
        for (Entity entity : _entities) {
            entity.update(deltaTime);
        }
    }

    @Override
    public void render (ShapeRenderer shapeRenderer) {
        _color.a = 0.5f;
        shapeRenderer.setColor(_color);
        shapeRenderer.rect(_rect.x, _rect.y, _width, _height);
    }

    @Override
    public void render (SpriteBatch batch) {
    }
}
