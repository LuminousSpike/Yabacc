package com.bituser.yabacc;

import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class GenericCollection <T extends Entity> extends Entity {
    protected Array<T> _entities;
	private int _columns;
	private float _spacing;
	private boolean _repositionEntities = false;
	private T _selectedEntity;

    public GenericCollection (Vector2 position, float width, float height) {
        super(position, width, height);

        // Defaults for now
        _columns = 8;
        _spacing = 10;

        _entities = new Array<T>();
    }
public GenericCollection (float x, float y, float width, float height) {
        super(new Vector2(x, y), width, height);
    }

    public int size () {
        return _entities.size;
    }

    public Iterator<T> iterator () { return _entities.iterator(); }

    protected void setSelectedEntity (T selectedEntity) { _selectedEntity = selectedEntity; }

    protected void repositionEntities() {
        _repositionEntities = true;
    }

    // TODO remove this method.
    protected T get () {
        T entity = null;

        int entityCount = _entities.size;

        if (entityCount > 0) {
            entity = _entities.get(_rand.nextInt(entityCount));
            _entities.removeValue(entity, true);
        }

        return entity;
    }

    protected void add (T entity) {
        _entities.add(entity);
        _repositionEntities = true;
    }

    protected void add (T[] entities) {
        for(T entity : entities) {
            add(entity);
        }
    }

    protected void addAll (Array<T> entities) {
    	_entities.addAll(entities);
        _repositionEntities = true;
    }

    protected void remove (T entity) {
    	_entities.removeValue(entity, true);
        _repositionEntities = true;
    }

    @Override
    public void update (float deltaTime) {
        _rect.setX(_position.x - (_width / 2));
        _rect.setY(_position.y - (_height / 2));
        if (_repositionEntities) {
            _repositionEntities = repositionEntities(deltaTime);
        }

        for (T entity : _entities) {
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

    private Vector2 calculateEntityPosition (T entity, int placeInHand) {
        int row = placeInHand / _columns;
        float xPos = _position.x + (entity.getWidth() + _spacing) * (placeInHand % _columns);

        // This might need to be configurable rather than a magic number
        xPos -= _width / 2.5f;

        float yPos = _position.y - (entity.getHeight() + _spacing) * row;
        return new Vector2(xPos, yPos);
    }

    private void positionEntity (T entity, int placeInHand) {
        entity.moveToPosition(calculateEntityPosition(entity, placeInHand));
    }

    private boolean positionEntity (T entity, int placeInHand, float deltaTime) {
        return entity.moveToPosition(calculateEntityPosition(entity, placeInHand), deltaTime);
    }

	private boolean repositionEntities (float deltaTime) {
		boolean entitiesMoved = false;
		int index = 0;
		for (T entity : _entities) {
		    if (entity != _selectedEntity) {
                if (positionEntity(entity, index, deltaTime)) {
                    entitiesMoved = true;
                }
            }
			index++;
		}
        return entitiesMoved;
    }
}
