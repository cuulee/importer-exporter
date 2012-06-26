-- DROP_DB.sql
--
-- Authors:     Prof. Dr. Thomas H. Kolbe <kolbe@igg.tu-berlin.de>
--              Gerhard König <gerhard.koenig@tu-berlin.de>
--              Claus Nagel <nagel@igg.tu-berlin.de>
--              Alexandra Stadler <stadler@igg.tu-berlin.de>
--
-- Conversion:	Felix Kunde <felix-kunde@gmx.de>
--
-- Copyright:   (c) 2007-2012  Institute for Geodesy and Geoinformation Science,
--                             Technische Universit�t Berlin, Germany
--                             http://www.igg.tu-berlin.de
--
--              This skript is free software under the LGPL Version 2.1.
--              See the GNU Lesser General Public License at
--              http://www.gnu.org/copyleft/lgpl.html
--              for more details.
-------------------------------------------------------------------------------
-- About:
--
--
-------------------------------------------------------------------------------
--
-- ChangeLog:
--
-- Version | Date       | Description                               | Author | Conversion
-- 2.0.2     2008-06-28   disable versioning before dropping          TKol
-- 2.0.1     2008-06-28   also drop planning manager tables           GKoe
-- 2.0.0     2011-12-11   PostGIS version                             CNag     FKun
--                                                                    ASta
--

SET client_min_messages TO WARNING;

-- Disable versioning (if it was enabled before)
--\i DISABLE_VERSIONING.sql

--//DROP FOREIGN KEYS

ALTER TABLE ADDRESS_TO_BUILDING DROP CONSTRAINT ADDRESS_TO_BUILDING_FK;
ALTER TABLE ADDRESS_TO_BUILDING DROP CONSTRAINT ADDRESS_TO_BUILDING_ADDRESS_FK;

ALTER TABLE APPEARANCE DROP CONSTRAINT APPEARANCE_CITYMODEL_FK;
ALTER TABLE APPEARANCE DROP CONSTRAINT APPEARANCE_CITYOBJECT_FK;

ALTER TABLE APPEAR_TO_SURFACE_DATA DROP CONSTRAINT APPEAR_TO_SURFACE_DATA_FK1;
ALTER TABLE APPEAR_TO_SURFACE_DATA DROP CONSTRAINT APPEAR_TO_SURFACE_DATA_FK;

ALTER TABLE BREAKLINE_RELIEF DROP CONSTRAINT BREAKLINE_RELIEF_FK;

ALTER TABLE BUILDING DROP CONSTRAINT BUILDING_SURFACE_GEOMETRY_FK;
ALTER TABLE BUILDING DROP CONSTRAINT BUILDING_SURFACE_GEOMETRY_FK3;
ALTER TABLE BUILDING DROP CONSTRAINT BUILDING_CITYOBJECT_FK;
ALTER TABLE BUILDING DROP CONSTRAINT BUILDING_SURFACE_GEOMETRY_FK1;
ALTER TABLE BUILDING DROP CONSTRAINT BUILDING_SURFACE_GEOMETRY_FK2;
ALTER TABLE BUILDING DROP CONSTRAINT BUILDING_BUILDING_FK;
ALTER TABLE BUILDING DROP CONSTRAINT BUILDING_BUILDING_FK1;

ALTER TABLE BUILDING_FURNITURE DROP CONSTRAINT BUILDING_FURNITURE_FK1;
ALTER TABLE BUILDING_FURNITURE DROP CONSTRAINT BUILDING_FURNITURE_FK2;
ALTER TABLE BUILDING_FURNITURE DROP CONSTRAINT BUILDING_FURNITURE_FK;
ALTER TABLE BUILDING_FURNITURE DROP CONSTRAINT BUILDING_FURNITURE_ROOM_FK;

ALTER TABLE BUILDING_INSTALLATION DROP CONSTRAINT BUILDING_INSTALLATION_FK3;
ALTER TABLE BUILDING_INSTALLATION DROP CONSTRAINT BUILDING_INSTALLATION_FK;
ALTER TABLE BUILDING_INSTALLATION DROP CONSTRAINT BUILDING_INSTALLATION_ROOM_FK;
ALTER TABLE BUILDING_INSTALLATION DROP CONSTRAINT BUILDING_INSTALLATION_FK4;
ALTER TABLE BUILDING_INSTALLATION DROP CONSTRAINT BUILDING_INSTALLATION_FK1;
ALTER TABLE BUILDING_INSTALLATION DROP CONSTRAINT BUILDING_INSTALLATION_FK2;

ALTER TABLE CITYOBJECT DROP CONSTRAINT CITYOBJECT_OBJECTCLASS_FK;

ALTER TABLE CITYOBJECTGROUP DROP CONSTRAINT CITYOBJECT_GROUP_FK;
ALTER TABLE CITYOBJECTGROUP DROP CONSTRAINT CITYOBJECTGROUP_CITYOBJECT_FK;
ALTER TABLE CITYOBJECTGROUP DROP CONSTRAINT CITYOBJECTGROUP_CITYOBJECT_FK1;

ALTER TABLE CITYOBJECT_GENERICATTRIB DROP CONSTRAINT CITYOBJECT_GENERICATTRIB_FK;
ALTER TABLE CITYOBJECT_GENERICATTRIB DROP CONSTRAINT CITYOBJECT_GENERICATTRIB_FK1;

ALTER TABLE CITYOBJECT_MEMBER DROP CONSTRAINT CITYOBJECT_MEMBER_CITYMODEL_FK;
ALTER TABLE CITYOBJECT_MEMBER DROP CONSTRAINT CITYOBJECT_MEMBER_FK;

ALTER TABLE CITY_FURNITURE DROP CONSTRAINT CITY_FURNITURE_FK;
ALTER TABLE CITY_FURNITURE DROP CONSTRAINT CITY_FURNITURE_FK1;
ALTER TABLE CITY_FURNITURE DROP CONSTRAINT CITY_FURNITURE_FK2;
ALTER TABLE CITY_FURNITURE DROP CONSTRAINT CITY_FURNITURE_FK3;
ALTER TABLE CITY_FURNITURE DROP CONSTRAINT CITY_FURNITURE_FK4;
ALTER TABLE CITY_FURNITURE DROP CONSTRAINT CITY_FURNITURE_FK5;
ALTER TABLE CITY_FURNITURE DROP CONSTRAINT CITY_FURNITURE_FK6;
ALTER TABLE CITY_FURNITURE DROP CONSTRAINT CITY_FURNITURE_FK7;
ALTER TABLE CITY_FURNITURE DROP CONSTRAINT CITY_FURNITURE_CITYOBJECT_FK;

ALTER TABLE EXTERNAL_REFERENCE DROP CONSTRAINT EXTERNAL_REFERENCE_FK;

ALTER TABLE GENERALIZATION DROP CONSTRAINT GENERALIZATION_FK1;
ALTER TABLE GENERALIZATION DROP CONSTRAINT GENERALIZATION_FK;

ALTER TABLE GENERIC_CITYOBJECT DROP CONSTRAINT GENERIC_CITYOBJECT_FK;
ALTER TABLE GENERIC_CITYOBJECT DROP CONSTRAINT GENERIC_CITYOBJECT_FK1;
ALTER TABLE GENERIC_CITYOBJECT DROP CONSTRAINT GENERIC_CITYOBJECT_FK2;
ALTER TABLE GENERIC_CITYOBJECT DROP CONSTRAINT GENERIC_CITYOBJECT_FK3;
ALTER TABLE GENERIC_CITYOBJECT DROP CONSTRAINT GENERIC_CITYOBJECT_FK4;
ALTER TABLE GENERIC_CITYOBJECT DROP CONSTRAINT GENERIC_CITYOBJECT_FK5;
ALTER TABLE GENERIC_CITYOBJECT DROP CONSTRAINT GENERIC_CITYOBJECT_FK6;
ALTER TABLE GENERIC_CITYOBJECT DROP CONSTRAINT GENERIC_CITYOBJECT_FK7;
ALTER TABLE GENERIC_CITYOBJECT DROP CONSTRAINT GENERIC_CITYOBJECT_FK8;
ALTER TABLE GENERIC_CITYOBJECT DROP CONSTRAINT GENERIC_CITYOBJECT_FK9;
ALTER TABLE GENERIC_CITYOBJECT DROP CONSTRAINT GENERIC_CITYOBJECT_FK10;

ALTER TABLE GROUP_TO_CITYOBJECT DROP CONSTRAINT GROUP_TO_CITYOBJECT_FK;
ALTER TABLE GROUP_TO_CITYOBJECT DROP CONSTRAINT GROUP_TO_CITYOBJECT_FK1;

ALTER TABLE IMPLICIT_GEOMETRY DROP CONSTRAINT IMPLICIT_GEOMETRY_FK;

ALTER TABLE LAND_USE DROP CONSTRAINT LAND_USE_CITYOBJECT_FK;
ALTER TABLE LAND_USE DROP CONSTRAINT LAND_USE_SURFACE_GEOMETRY_FK;
ALTER TABLE LAND_USE DROP CONSTRAINT LAND_USE_SURFACE_GEOMETRY_FK1;
ALTER TABLE LAND_USE DROP CONSTRAINT LAND_USE_SURFACE_GEOMETRY_FK2;
ALTER TABLE LAND_USE DROP CONSTRAINT LAND_USE_SURFACE_GEOMETRY_FK3;
ALTER TABLE LAND_USE DROP CONSTRAINT LAND_USE_SURFACE_GEOMETRY_FK4;

ALTER TABLE MASSPOINT_RELIEF DROP CONSTRAINT MASSPOINT_RELIEF_FK;

ALTER TABLE OBJECTCLASS DROP CONSTRAINT OBJECTCLASS_OBJECTCLASS_FK;

ALTER TABLE OPENING DROP CONSTRAINT OPENING_SURFACE_GEOMETRY_FK1;
ALTER TABLE OPENING DROP CONSTRAINT OPENING_CITYOBJECT_FK;
ALTER TABLE OPENING DROP CONSTRAINT OPENING_SURFACE_GEOMETRY_FK;
ALTER TABLE OPENING DROP CONSTRAINT OPENING_ADDRESS_FK;

ALTER TABLE OPENING_TO_THEM_SURFACE DROP CONSTRAINT OPENING_TO_THEMATIC_SURFACE_FK;
ALTER TABLE OPENING_TO_THEM_SURFACE DROP CONSTRAINT OPENING_TO_THEMATIC_SURFAC_FK1;

ALTER TABLE PLANT_COVER DROP CONSTRAINT PLANT_COVER_FK;
ALTER TABLE PLANT_COVER DROP CONSTRAINT PLANT_COVER_FK1;
ALTER TABLE PLANT_COVER DROP CONSTRAINT PLANT_COVER_FK3;
ALTER TABLE PLANT_COVER DROP CONSTRAINT PLANT_COVER_FK2;
ALTER TABLE PLANT_COVER DROP CONSTRAINT PLANT_COVER_CITYOBJECT_FK;

ALTER TABLE RASTER_RELIEF DROP CONSTRAINT RASTER_RELIEF_FK;

ALTER TABLE RELIEF_COMPONENT DROP CONSTRAINT RELIEF_COMPONENT_CITYOBJECT_FK;

ALTER TABLE RELIEF_FEATURE DROP CONSTRAINT RELIEF_FEATURE_CITYOBJECT_FK;

ALTER TABLE RELIEF_FEAT_TO_REL_COMP DROP CONSTRAINT RELIEF_FEAT_TO_REL_COMP_FK;
ALTER TABLE RELIEF_FEAT_TO_REL_COMP DROP CONSTRAINT RELIEF_FEAT_TO_REL_COMP_FK1;

ALTER TABLE ROOM DROP CONSTRAINT ROOM_BUILDING_FK;
ALTER TABLE ROOM DROP CONSTRAINT ROOM_SURFACE_GEOMETRY_FK;
ALTER TABLE ROOM DROP CONSTRAINT ROOM_CITYOBJECT_FK;

ALTER TABLE SOLITARY_VEGETAT_OBJECT DROP CONSTRAINT SOLITARY_VEGETAT_OBJECT_FK;
ALTER TABLE SOLITARY_VEGETAT_OBJECT DROP CONSTRAINT SOLITARY_VEGETAT_OBJECT_FK1;
ALTER TABLE SOLITARY_VEGETAT_OBJECT DROP CONSTRAINT SOLITARY_VEGETAT_OBJECT_FK2;
ALTER TABLE SOLITARY_VEGETAT_OBJECT DROP CONSTRAINT SOLITARY_VEGETAT_OBJECT_FK3;
ALTER TABLE SOLITARY_VEGETAT_OBJECT DROP CONSTRAINT SOLITARY_VEGETAT_OBJECT_FK4;
ALTER TABLE SOLITARY_VEGETAT_OBJECT DROP CONSTRAINT SOLITARY_VEGETAT_OBJECT_FK5;
ALTER TABLE SOLITARY_VEGETAT_OBJECT DROP CONSTRAINT SOLITARY_VEGETAT_OBJECT_FK6;
ALTER TABLE SOLITARY_VEGETAT_OBJECT DROP CONSTRAINT SOLITARY_VEGETAT_OBJECT_FK7;
ALTER TABLE SOLITARY_VEGETAT_OBJECT DROP CONSTRAINT SOLITARY_VEGETAT_OBJECT_FK8;

ALTER TABLE SURFACE_GEOMETRY DROP CONSTRAINT SURFACE_GEOMETRY_FK;
ALTER TABLE SURFACE_GEOMETRY DROP CONSTRAINT SURFACE_GEOMETRY_FK1;

ALTER TABLE TEXTUREPARAM DROP CONSTRAINT TEXTUREPARAM_SURFACE_GEOM_FK;
ALTER TABLE TEXTUREPARAM DROP CONSTRAINT TEXTUREPARAM_SURFACE_DATA_FK;

ALTER TABLE THEMATIC_SURFACE DROP CONSTRAINT THEMATIC_SURFACE_ROOM_FK;
ALTER TABLE THEMATIC_SURFACE DROP CONSTRAINT THEMATIC_SURFACE_BUILDING_FK;
ALTER TABLE THEMATIC_SURFACE DROP CONSTRAINT THEMATIC_SURFACE_FK;
ALTER TABLE THEMATIC_SURFACE DROP CONSTRAINT THEMATIC_SURFACE_CITYOBJECT_FK;
ALTER TABLE THEMATIC_SURFACE DROP CONSTRAINT THEMATIC_SURFACE_FK2;
ALTER TABLE THEMATIC_SURFACE DROP CONSTRAINT THEMATIC_SURFACE_FK1;

ALTER TABLE TIN_RELIEF DROP CONSTRAINT TIN_RELIEF_SURFACE_GEOMETRY_FK;
ALTER TABLE TIN_RELIEF DROP CONSTRAINT TIN_RELIEF_RELIEF_COMPONENT_FK;

ALTER TABLE TRAFFIC_AREA DROP CONSTRAINT TRAFFIC_AREA_CITYOBJECT_FK;
ALTER TABLE TRAFFIC_AREA DROP CONSTRAINT TRAFFIC_AREA_FK;
ALTER TABLE TRAFFIC_AREA DROP CONSTRAINT TRAFFIC_AREA_FK1;
ALTER TABLE TRAFFIC_AREA DROP CONSTRAINT TRAFFIC_AREA_FK2;
ALTER TABLE TRAFFIC_AREA DROP CONSTRAINT TRAFFIC_AREA_FK3;

ALTER TABLE TRANSPORTATION_COMPLEX DROP CONSTRAINT TRANSPORTATION_COMPLEX_FK;
ALTER TABLE TRANSPORTATION_COMPLEX DROP CONSTRAINT TRANSPORTATION_COMPLEX_FK1;
ALTER TABLE TRANSPORTATION_COMPLEX DROP CONSTRAINT TRANSPORTATION_COMPLEX_FK2;
ALTER TABLE TRANSPORTATION_COMPLEX DROP CONSTRAINT TRANSPORTATION_COMPLEX_FK3;
ALTER TABLE TRANSPORTATION_COMPLEX DROP CONSTRAINT TRANSPORTATION_COMPLEX_FK4;

ALTER TABLE WATERBODY DROP CONSTRAINT WATERBODY_CITYOBJECT_FK;
ALTER TABLE WATERBODY DROP CONSTRAINT WATERBODY_SURFACE_GEOMETRY_FK1;
ALTER TABLE WATERBODY DROP CONSTRAINT WATERBODY_SURFACE_GEOMETRY_FK2;
ALTER TABLE WATERBODY DROP CONSTRAINT WATERBODY_SURFACE_GEOMETRY_FK3;
ALTER TABLE WATERBODY DROP CONSTRAINT WATERBODY_SURFACE_GEOMETRY_FK4;
ALTER TABLE WATERBODY DROP CONSTRAINT WATERBODY_SURFACE_GEOMETRY_FK5;
ALTER TABLE WATERBODY DROP CONSTRAINT WATERBODY_SURFACE_GEOMETRY_FK;

ALTER TABLE WATERBOD_TO_WATERBND_SRF DROP CONSTRAINT WATERBOD_TO_WATERBND_FK;
ALTER TABLE WATERBOD_TO_WATERBND_SRF DROP CONSTRAINT WATERBOD_TO_WATERBND_FK1;

ALTER TABLE WATERBOUNDARY_SURFACE DROP CONSTRAINT WATERBOUNDARY_SRF_CITYOBJ_FK;
ALTER TABLE WATERBOUNDARY_SURFACE DROP CONSTRAINT WATERBOUNDARY_SURFACE_FK;
ALTER TABLE WATERBOUNDARY_SURFACE DROP CONSTRAINT WATERBOUNDARY_SURFACE_FK1;
ALTER TABLE WATERBOUNDARY_SURFACE DROP CONSTRAINT WATERBOUNDARY_SURFACE_FK2;

--//DROP TABLES

DROP TABLE ADDRESS CASCADE;
DROP TABLE ADDRESS_TO_BUILDING CASCADE;
DROP TABLE APPEARANCE CASCADE;
DROP TABLE APPEAR_TO_SURFACE_DATA CASCADE;
DROP TABLE BREAKLINE_RELIEF CASCADE;
DROP TABLE BUILDING CASCADE;
DROP TABLE BUILDING_FURNITURE CASCADE;
DROP TABLE BUILDING_INSTALLATION CASCADE;
DROP TABLE CITYMODEL CASCADE;
DROP TABLE CITYOBJECT CASCADE;
DROP TABLE CITYOBJECTGROUP CASCADE;
DROP TABLE CITYOBJECT_GENERICATTRIB CASCADE;
DROP TABLE CITYOBJECT_MEMBER CASCADE;
DROP TABLE CITY_FURNITURE CASCADE;
DROP TABLE DATABASE_SRS CASCADE;
DROP TABLE EXTERNAL_REFERENCE CASCADE;
DROP TABLE GENERALIZATION CASCADE;
DROP TABLE GENERIC_CITYOBJECT CASCADE;
DROP TABLE GROUP_TO_CITYOBJECT CASCADE;
DROP TABLE IMPLICIT_GEOMETRY CASCADE;
DROP TABLE LAND_USE CASCADE;
DROP TABLE MASSPOINT_RELIEF CASCADE;
DROP TABLE OBJECTCLASS CASCADE;
DROP TABLE OPENING CASCADE;
DROP TABLE OPENING_TO_THEM_SURFACE CASCADE;
DROP TABLE ORTHOPHOTO CASCADE;
DROP TABLE PLANT_COVER CASCADE;
DROP TABLE RASTER_RELIEF CASCADE;
DROP TABLE RELIEF CASCADE;
DROP TABLE RELIEF_COMPONENT CASCADE;
DROP TABLE RELIEF_FEATURE CASCADE;
DROP TABLE RELIEF_FEAT_TO_REL_COMP CASCADE;
DROP TABLE ROOM CASCADE;
DROP TABLE SOLITARY_VEGETAT_OBJECT CASCADE;
DROP TABLE SURFACE_DATA CASCADE;
DROP TABLE SURFACE_GEOMETRY CASCADE;
DROP TABLE TEXTUREPARAM CASCADE;
DROP TABLE THEMATIC_SURFACE CASCADE;
DROP TABLE TIN_RELIEF CASCADE;
DROP TABLE TRAFFIC_AREA CASCADE;
DROP TABLE TRANSPORTATION_COMPLEX CASCADE;
DROP TABLE WATERBODY CASCADE;
DROP TABLE WATERBOD_TO_WATERBND_SRF CASCADE;
DROP TABLE WATERBOUNDARY_SURFACE CASCADE;
DROP TABLE IMPORT_PROCEDURES CASCADE;

--//DROP SCHEMAS

DROP SCHEMA GEODB_PKG CASCADE;
-- DROP SCHEMA PLANNING_MANAGER CASCADE;