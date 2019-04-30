package no.ntnu.tdt4240.astrosplit.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import no.ntnu.tdt4240.astrosplit.models.Configuration;

public class Map {


	private int TILE_WIDTH_PX;
	private int TILE_HEIGHT_PX;

	static final float renderWidth = Configuration.getInstance().getViewPortRenderWidth();
	static final float renderHeight = Configuration.getInstance().viewPortRenderHeight;

	private TiledMap map;
	private MapProperties properties;
	private TiledMapTileLayer layer;

	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer mapRenderer;


	/*
		TODO
		Make singleton?
	 */
	public Map()
	{
		this.map = new TmxMapLoader().load("map/map_01.tmx");
		this.properties = map.getProperties();
		this.layer = (TiledMapTileLayer) map.getLayers().get(0);


		this.TILE_WIDTH_PX = (int) renderWidth/layer.getWidth();
		this.TILE_HEIGHT_PX = (int) renderHeight/layer.getHeight();

		this.mapRenderer = new OrthogonalTiledMapRenderer(map) {
			@Override
			public void setView(OrthographicCamera camera) {
				super.setView(camera);
			}

			@Override
			public void setView(Matrix4 projectionMatrix, float viewboundsX, float viewboundsY, float viewboundsWidth, float viewboundsHeight) {

			}

			@Override
			public void render() {
				super.render();
				//super.renderTileLayer(layer);
			}

			@Override
			public void render(int[] layers) {

			}
		};

	}

	/*
		Return cell from position
	 */
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
			return;
		}

		mapRenderer.setView(camera);
		mapRenderer.render();
	}

	public TiledMap getMap()
	{
		return map;
	}

	public MapProperties getProperties() {
		return properties;
	}

	public int getMapWidthInPixels()
	{
		return properties.get("tilewidth",Integer.class)* properties.get("width", Integer.class);
	}
	public int getMapHeightInPixels()
	{
		return properties.get("tileheight", Integer.class)* properties.get("height", Integer.class);
	}

	public void dispose() {
		map.dispose();
	}
}
