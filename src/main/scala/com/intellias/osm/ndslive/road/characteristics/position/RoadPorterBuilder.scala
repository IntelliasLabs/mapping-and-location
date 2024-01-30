package com.intellias.osm.ndslive.road.characteristics.position

import com.intellias.osm.compiler.road.characteristic.position.{RoadPositionCheckpointExtractor, RoadPositionPorterExtractor}
import com.intellias.osm.model.road.RoadPosition
import nds.characteristics.attributes.{CharacsRoadPositionAttributeType, CharacsRoadPositionAttributeValue}
import nds.characteristics.instantiations.CharacsProperty
import nds.characteristics.properties.{CharacsPropertyType, CharacsPropertyValue, PropertyValue}
import nds.characteristics.properties.PropertyType.STATION_STOP_TYPE
import nds.characteristics.types.{StationStopType, StationaryObjectType}
import play.api.libs.json.Json

object RoadPorterBuilder extends PositionAttributeValueBuilder {
  override def getPositions(tags: Map[String, String]): Seq[RoadPosition] = {
      tags
        .get(RoadPositionPorterExtractor.tag)
        .map( json => Json.parse(json).as[Seq[RoadPosition]])
        .getOrElse(Seq.empty)
  }

  val attrType: CharacsRoadPositionAttributeType =  CharacsRoadPositionAttributeType.STATION

  val attributeValue: CharacsRoadPositionAttributeValue = {
    val attrVal = new CharacsRoadPositionAttributeValue(attrType)
    attrVal.setStationaryObjectType(StationaryObjectType.PORTER)
    attrVal
  }

  override protected def attributeProperties: Array[CharacsProperty] = {
    val propertyType = new CharacsPropertyType(STATION_STOP_TYPE, null)
    val propertyValue = new PropertyValue(STATION_STOP_TYPE)
    propertyValue.setStationStopType(StationStopType.STOP)
    Array(new CharacsProperty(propertyType, new CharacsPropertyValue(propertyType, propertyValue, null)))
  }
}
