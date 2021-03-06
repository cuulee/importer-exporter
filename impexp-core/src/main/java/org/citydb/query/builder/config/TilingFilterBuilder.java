package org.citydb.query.builder.config;

import java.sql.SQLException;

import org.citydb.config.geometry.BoundingBox;
import org.citydb.config.project.database.DatabaseSrs;
import org.citydb.config.project.kmlExporter.KmlTilingMode;
import org.citydb.config.project.kmlExporter.KmlTilingOptions;
import org.citydb.database.adapter.AbstractDatabaseAdapter;
import org.citydb.query.builder.QueryBuildException;
import org.citydb.query.filter.FilterException;
import org.citydb.query.filter.tiling.Tiling;

public class TilingFilterBuilder {
	private final AbstractDatabaseAdapter databaseAdapter;

	protected TilingFilterBuilder(AbstractDatabaseAdapter databaseAdapter) {
		this.databaseAdapter = databaseAdapter;
	}

	public Tiling buildTilingFilter(org.citydb.config.project.query.filter.tiling.Tiling tilingConfig) throws QueryBuildException {		
		try {
			// adapt tiling settings in case of KML exports
			if (tilingConfig.getTilingOptions() instanceof KmlTilingOptions) {
				KmlTilingOptions tilingOptions = (KmlTilingOptions)tilingConfig.getTilingOptions();

				// calculate tile size if required
				if (tilingOptions.getMode() == KmlTilingMode.AUTOMATIC) {
					BoundingBox extent = tilingConfig.getExtent();
					double autoTileSideLength = tilingOptions.getAutoTileSideLength();			

					// transform extent into the database srs if required
					DatabaseSrs dbSrs = databaseAdapter.getConnectionMetaData().getReferenceSystem();
					DatabaseSrs extentSrs = extent.isSetSrs() ? extent.getSrs() : databaseAdapter.getConnectionMetaData().getReferenceSystem();			
					if (extentSrs.getSrid() != dbSrs.getSrid()) {
						try {
							extent = databaseAdapter.getUtil().transformBoundingBox(extent, extent.getSrs(), dbSrs);
						} catch (SQLException e) {
							throw new QueryBuildException("Failed to automatically calculate tile size.", e);
						}
					}

					tilingConfig.setRows((int)((extent.getUpperCorner().getY() - extent.getLowerCorner().getY()) / autoTileSideLength) + 1);
					tilingConfig.setColumns((int)((extent.getUpperCorner().getX() - extent.getLowerCorner().getX()) / autoTileSideLength) + 1);
				} 

				// internally map no tiling to manual tiling mode
				else if (tilingOptions.getMode() == KmlTilingMode.NO_TILING) {
					tilingOptions.setMode(KmlTilingMode.MANUAL);
					tilingConfig.setRows(1);
					tilingConfig.setColumns(1);
				}
			}

			Tiling tiling = new Tiling(tilingConfig.getExtent(), tilingConfig.getRows(), tilingConfig.getColumns());
			tiling.setTilingOptions(tilingConfig.getTilingOptions());

			return tiling;
		} catch (FilterException e) {
			throw new QueryBuildException("Failed to build tiling filter.", e);
		}
	}
}
