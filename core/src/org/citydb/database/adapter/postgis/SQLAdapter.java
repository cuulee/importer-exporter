/*
 * 3D City Database - The Open Source CityGML Database
 * http://www.3dcitydb.org/
 * 
 * Copyright 2013 - 2017
 * Chair of Geoinformatics
 * Technical University of Munich, Germany
 * https://www.gis.bgu.tum.de/
 * 
 * The 3D City Database is jointly developed with the following
 * cooperation partners:
 * 
 * virtualcitySYSTEMS GmbH, Berlin <http://www.virtualcitysystems.de/>
 * M.O.S.S. Computer Grafik Systeme GmbH, Taufkirchen <http://www.moss.de/>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.citydb.database.adapter.postgis;

import java.sql.Connection;
import java.sql.SQLException;

import org.citydb.config.geometry.GeometryObject;
import org.citydb.database.adapter.AbstractDatabaseAdapter;
import org.citydb.database.adapter.AbstractSQLAdapter;
import org.citydb.database.adapter.BlobExportAdapter;
import org.citydb.database.adapter.BlobImportAdapter;
import org.citydb.database.adapter.BlobType;
import org.citydb.query.filter.selection.operator.spatial.SpatialOperatorName;

import vcs.sqlbuilder.expression.DoubleLiteral;
import vcs.sqlbuilder.expression.PlaceHolder;
import vcs.sqlbuilder.expression.StringLiteral;
import vcs.sqlbuilder.schema.Column;
import vcs.sqlbuilder.select.PredicateToken;
import vcs.sqlbuilder.select.operator.comparison.BinaryComparisonOperator;
import vcs.sqlbuilder.select.operator.comparison.ComparisonFactory;
import vcs.sqlbuilder.select.operator.logical.LogicalOperationFactory;
import vcs.sqlbuilder.select.projection.Function;

public class SQLAdapter extends AbstractSQLAdapter {

	protected SQLAdapter(AbstractDatabaseAdapter databaseAdapter) {
		super(databaseAdapter);
	}
	
	@Override
	public String getInteger() {
		return "INTEGER";
	}

	@Override
	public String getSmallInt() {
		return "SMALLINT";
	}

	@Override
	public String getBigInt() {
		return "BIGINT";
	}

	@Override
	public String getNumeric() {
		return "NUMERIC";
	}

	@Override
	public String getNumeric(int precision) {
		return "NUMERIC(" + precision + ")";
	}

	@Override
	public String getNumeric(int precision, int scale) {
		return "NUMERIC(" + precision + "," + scale + ")";
	}

	@Override
	public String getReal() {
		return "REAL";
	}

	@Override
	public String getDoublePrecision() {
		return "DOUBLE PRECISION";
	}

	@Override
	public String getCharacter(int nrOfChars) {
		return "CHAR(" + nrOfChars + ")";
	}

	@Override
	public String getCharacterVarying(int nrOfChars) {
		return "VARCHAR(" + nrOfChars + ")";
	}

	@Override
	public String getPolygon2D() {
		return "GEOMETRY(POLYGON)";
	}

	@Override
	public String getCreateUnloggedTable(String tableName, String columns) {
		StringBuilder builder = new StringBuilder()
		.append("create unlogged table ")
		.append(tableName).append(" ")
		.append(columns);

		return builder.toString();
	}

	@Override
	public String getCreateUnloggedTableAsSelectFrom(String targetTableName, String sourceTableName) {
		StringBuilder builder = new StringBuilder()
		.append("create unlogged table ")
		.append(targetTableName).append(" ")
		.append("as select * from ")
		.append(sourceTableName);

		return builder.toString();
	}
	
	@Override
	public String getNextSequenceValue(String sequence, String schema) {
		return new StringBuilder("nextval('").append(schema).append(".").append(sequence).append("')").toString();
	}
	
	@Override
	public String getCurrentSequenceValue(String sequence, String schema) {
		return new StringBuilder("currval('").append(schema).append(".").append(sequence).append("')").toString();
	}
	
	@Override
	public String getNextSequenceValuesQuery(String sequence) {
		return new StringBuilder("select ")
		.append(resolveDatabaseOperationName("citydb_util.get_seq_values")).append("(")
		.append("'").append(sequence).append("'").append(",")
		.append("?").append(",").append("?").append(")").toString();
	}

	@Override
	public String getUnloggedIndexProperty() {
		return "";
	}
	
	@Override
	public boolean requiresPseudoTableInSelect() {
		return false;
	}

	@Override
	public String getPseudoTableName() {
		return "";
	}
	
	@Override
	public int getMaximumNumberOfItemsForInOperator() {
		return 1000;
	}
	
	@Override
	public boolean spatialPredicateRequiresNoIndexHint() {
		return false;
	}

	@Override
	public String getHierarchicalGeometryQuery(String schema) {
		StringBuilder query = new StringBuilder()
		.append("WITH RECURSIVE geometry_rec (id, gmlid, parent_id, root_id, is_solid, is_composite, is_triangulated, is_xlink, is_reverse, geometry, implicit_geometry, solid_geometry, cityobject_id, level) ")
		.append("AS (SELECT sg.id, sg.gmlid, sg.parent_id, sg.root_id, sg.is_solid, sg.is_composite, sg.is_triangulated, sg.is_xlink, sg.is_reverse, sg.geometry, sg.implicit_geometry, sg.solid_geometry, sg.cityobject_id, 1 AS level ")
		.append("FROM ").append(schema).append(".surface_geometry sg WHERE sg.id=? UNION ALL ")
		.append("SELECT sg.id, sg.gmlid, sg.parent_id, sg.root_id, sg.is_solid, sg.is_composite, sg.is_triangulated, sg.is_xlink, sg.is_reverse, sg.geometry, sg.implicit_geometry, sg.solid_geometry, sg.cityobject_id, g.level + 1 AS level ")
		.append("FROM ").append(schema).append(".surface_geometry sg, geometry_rec g WHERE sg.parent_id=g.id) ")
		.append("SELECT id, gmlid, parent_id, root_id, is_solid, is_composite, is_triangulated, is_xlink, is_reverse, geometry, implicit_geometry, ST_AsEWKT(solid_geometry) as solid_geometry, cityobject_id, level FROM geometry_rec");
				
		return query.toString();
	}

	@Override
	public BlobImportAdapter getBlobImportAdapter(Connection connection, BlobType type, String schema) throws SQLException {
		return new BlobImportAdapter(connection, type, schema);
	}

	@Override
	public BlobExportAdapter getBlobExportAdapter(Connection connection, BlobType type) {
		return new BlobExportAdapter(connection, type);
	}

	@Override
	public PredicateToken getBinarySpatialPredicate(SpatialOperatorName operator, Column targetColumn, GeometryObject geometry, boolean negate) {
		PlaceHolder<GeometryObject> placeHolder = new PlaceHolder<GeometryObject>(geometry);
		StringLiteral trueLiteral = new StringLiteral("TRUE");
		
		switch (operator) {
		case BBOX:
			return !negate ? new BinaryComparisonOperator(targetColumn, "&&", placeHolder) :
				LogicalOperationFactory.NOT(new BinaryComparisonOperator(targetColumn, "&&", placeHolder));
		case INTERSECTS:
			return ComparisonFactory.equalTo(new Function("ST_Intersects", targetColumn, placeHolder), trueLiteral, negate);
		case EQUALS:
			return ComparisonFactory.equalTo(new Function("ST_Equals", targetColumn, placeHolder), trueLiteral, negate);			
		case DISJOINT:
			return ComparisonFactory.equalTo(new Function("ST_Disjoint", targetColumn, placeHolder), trueLiteral, negate);
		case TOUCHES:
			return ComparisonFactory.equalTo(new Function("ST_Touches", targetColumn, placeHolder), trueLiteral, negate);			
		case WITHIN:
			return ComparisonFactory.equalTo(new Function("ST_Within", targetColumn, placeHolder), trueLiteral, negate);
		case OVERLAPS:
			return ComparisonFactory.equalTo(new Function("ST_Overlaps", targetColumn, placeHolder), trueLiteral, negate);
		case CONTAINS:
			return ComparisonFactory.equalTo(new Function("ST_Contains", targetColumn, placeHolder), trueLiteral, negate);
		default:
			break;
		}
		
		return null;
	}
	
	@Override
	public PredicateToken getDistancePredicate(SpatialOperatorName operator, Column targetColumn, GeometryObject geometry, double distance, boolean negate) {
		PlaceHolder<GeometryObject> placeHolder = new PlaceHolder<GeometryObject>(geometry);
		StringLiteral trueLiteral = new StringLiteral("TRUE");
		DoubleLiteral distanceLiteral = new DoubleLiteral(distance);
		
		switch (operator) {
		case DWITHIN:
			return ComparisonFactory.equalTo(new Function("ST_DWithin", targetColumn, placeHolder, distanceLiteral), trueLiteral, negate);			
		case BEYOND:
			return ComparisonFactory.equalTo(new Function("ST_DWithin", targetColumn, placeHolder, distanceLiteral), trueLiteral, !negate);
		default:
			break;
		}		
		
		return null;
	}

	@Override
	public Function getAggregateExtentFunction(Column envelope) {
		return new Function("st_extent", envelope);
	}
	
}