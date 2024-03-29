package com.bituser.yabacc.droplets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public abstract class GenericCollection <T extends com.bituser.yabacc.util.Entity> extends com.bituser.yabacc.util.Entity {
    Array<T> _entities;
	private int _columns;
	private float _spacing;
    private float _offsetX, _offsetY;
	private boolean _repositionEntities = false;
	private T _selectedEntity;

    GenericCollection(Vector2 position, float width, float height) {
        super(position, width, height);

        // Defaults for now
        _columns = 8;
        _spacing = 10;

        _entities = new Array<T>();
    }
public GenericCollection (float x, float y, float width, float height) {
        super(new Vector2(x, y), width, height);
    }

    int size() {
        return _entities.size;
    }

    Iterator<T> iterator() { return _entities.iterator(); }

    void setSelectedEntity(T selectedEntity) { _selectedEntity = selectedEntity; }

    void setColumns(int columns) { _columns = columns;}

    void setSpacing(float spacing) { _spacing = spacing; }

    void setOffsetX(float offsetX) { _offsetX = offsetX; }

    void setOffsetY(float offsetY) { _offsetY = offsetY; }

    void repositionEntities() {
        _repositionEntities = true;
    }

    // TODO remove this method.
    T getRandom() {
        T entity = null;

        int entityCount = _entities.size;

        if (entityCount > 0) {
            entity = _entities.get(_rand.nextInt(entityCount));
            _entities.removeValue(entity, true);
        }

        return entity;
    }

    void add(T entity) {
        _entities.add(entity);
        _repositionEntities = true;
    }

    protected void add (T[] entities) {
        for(T entity : entities) {
            add(entity);
        }
    }

    void addAll(Array<? extends T> entities) {
    	_entities.addAll(entities);
        _repositionEntities = true;
    }

    void remove(T entity) {
    	_entities.removeValue(entity, true);
        _repositionEntities = true;
    }

    void removeAll(Array<T> entities) {
        _entities.removeAll(entities, true);
        _repositionEntities = true;
    }

    @Override
    public void update(float deltaTime) {
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
    public void render(ShapeRenderer shapeRenderer) {
        _color.a = 0.5f;
        shapeRenderer.setColor(_color);
        shapeRenderer.rect(_rect.x, _rect.y, _width, _height);
    }

    @Override
    public void render(SpriteBatch batch) {
    }

    private Vector2 calculateEntityPosition (T entity, int placeInHand) {
        int row = placeInHand / _columns;
        float xPos = _position.x + _offsetX + (entity.getWidth() + _spacing) * (placeInHand % _columns);

        // This might need to be configurable rather than a magic number
        xPos -= _width / 2.5f;

        float yPos = _position.y + _offsetY - (entity.getHeight() + _spacing) * row;
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
