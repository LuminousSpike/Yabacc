package com.bituser.yabacc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class EntityCollection extends Entity {
    private List<Entity> _entities= new ArrayList<Entity>();
	private int _columns;
	private float _spacing;

    public EntityCollection (float x, float y, float width, float height) {
        super(x, y, width, height);
        
        // Defaults for now
        _columns = 8;
        _spacing = 10;
    }

    public int size () {
        return _entities.size();
    }
    
    public Iterator<Entity> iterator () { return _entities.iterator(); }

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
        _entities.add(entity);
    }

    protected void add (Entity[] entities) {
        for(Entity entity : entities) {
            add(entity);
        }
    }
    
    protected void addAll (ArrayList<Entity> entities) { 
    	_entities.addAll(entities);
    }
    
    protected void remove (Entity entity) {
    	_entities.remove(entity);
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
    
    private Vector2 calculateEntityPosition (Entity entity, int placeInHand) {
        int row = placeInHand / _columns;
        float xPos = _position.x + (entity.getWidth() + _spacing) * (placeInHand % _columns);
        
        // This might need to be configurable rather than a magic number
        xPos -= _width / 2.5f;
        
        float yPos = _position.y - (entity.getHeight() + _spacing) * row;
        return new Vector2(xPos, yPos);
    }

    private void positionEntity (Entity entity, int placeInHand) {
        entity.moveToPosition(calculateEntityPosition(entity, placeInHand));
    }

    private boolean positionEntity (Entity entity, int placeInHand, float deltaTime) {
        return entity.moveToPosition(calculateEntityPosition(entity, placeInHand), deltaTime);
    }
    
	private boolean repositionEntities (float deltaTime) {
		boolean entitiesMoved = false;
		int index = 0;
		for (Entity entity : _entities) {
			if (positionEntity(entity, index, deltaTime)) {
				entitiesMoved = true;
			}
			index++;
		}
        return entitiesMoved;
    }
}
