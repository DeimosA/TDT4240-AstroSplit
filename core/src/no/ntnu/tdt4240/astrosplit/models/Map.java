package no.ntnu.tdt4240.astrosplit.models;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

public class Map {


	private int TILE_WIDTH_PX;
	private int TILE_HEIGHT_PX;

	static final float renderWidth = Configuration.getInstance().getViewPortRenderWidth();
	static final float renderHeight = Configuration.getInstance().viewPortRenderHeight;

	private TiledMap map;
	private TiledMapTileLayer layer;

	private OrthographicCamera camera;
	private MapRenderer mapRenderer;


	/*
		TODO
		Make singleton?
	 */
	public Map()
	{
		this.map = new TiledMap(); //should load map from assets
		this.layer = (TiledMapTileLayer) map.getLayers().get(0);

		this.TILE_WIDTH_PX = (int) renderWidth/layer.getWidth();
		this.TILE_HEIGHT_PX = (int) renderHeight/layer.getHeight();

		this.mapRenderer = new MapRenderer() {
			@Override
			public void setView(OrthographicCamera camera) {

			}

			@Override
			public void setView(Matrix4 projectionMatrix, float viewboundsX, float viewboundsY, float viewboundsWidth, float viewboundsHeight) {

			}

			@Override
			public void render() {

			}

			@Override
			public void render(int[] layers) {

			}
		};

	}


	public Cell getCell(Vector2 position)
	{
		return layer.getCell((int) position.x/TILE_WIDTH_PX, (int) position.y/TILE_HEIGHT_PX);
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public void render()
	{
		if(camera == null)
		{
			System.out.println("please set camera first");
		}
		mapRenderer.setView(camera);
		mapRenderer.render();
	}

	public TiledMap getMap()
	{
		return map;
	}
	


}