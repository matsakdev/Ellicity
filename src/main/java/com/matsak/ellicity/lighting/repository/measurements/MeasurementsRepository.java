package com.matsak.ellicity.lighting.repository.measurements;

import com.matsak.ellicity.lighting.entity.measurements.MeasurementRecord;
import com.matsak.ellicity.lighting.entity.sections.Circuit;
import org.springframework.data.mongodb.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface MeasurementsRepository extends MongoRepository<MeasurementRecord, String> {
    @Query("{'circuitId' : ?0, 'measurement.time' :  {$gt :  ?1}}")
    List<MeasurementRecord> findAllByCircuitIdAndDate(Circuit circuit, LocalDateTime date);

    @Aggregation(pipeline = {
            "{ '$match' : { 'circuitId' :  ?0 } }",
            "{ '$sort' :  { 'measurement.time' : -1}}", //-1 важливо todo notion
            "{ '$limit' :  ?1 }"
    })
    List<MeasurementRecord> findLastMeasurements(Long circuitId, int amount);

//    @Query("{ 'circuitId' :  ?0, 'measurement.time' :  {" +
//            "$gte :  ?1," +
//            " $lt : ?2}" +
//            "}")
    @Query("{ 'circuitId' :  ?0, 'measurement.time' :  {" +
            "$gte : ?1" +
            ", $lt : ?2" +
            "}}")
    List<MeasurementRecord> findMeasurementsInRange(Long circuitId, Date dateFrom, Date dateTo);
}
